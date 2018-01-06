package ru.rusquant.client;

import ru.rusquant.channel.DataTransferChannel;
import ru.rusquant.channel.ChannelType;
import ru.rusquant.channel.socket.SocketChannel;
import ru.rusquant.client.exceptions.MessageGrinderEmergencyAbortException;
import ru.rusquant.client.timing.TimingManager;
import ru.rusquant.channel.pipe.WindowsNamedPipe;
import ru.rusquant.messages.MessagesManager;
import ru.rusquant.messages.request.Request;
import ru.rusquant.messages.request.body.RequestSubject;
import ru.rusquant.messages.response.EndOfStreamResponse;
import ru.rusquant.messages.response.Response;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Class implements all client-side logic of Windows Named Pipe server-to-client process
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
public class Client {

    /**
     * Managers
     **/
    private final MessagesManager messagesManager = MessagesManager.getInstance();
    private final TimingManager timingManager = TimingManager.getInstance();

    /**
     * Client's state flags
     **/
    private Boolean isStopped = Boolean.FALSE;
    private Boolean isConnected = Boolean.FALSE;


    /**
     * Queues for double producer - consumer scheme.
     * Client acts as producer with respect to requests, while MessageGrinder acts as consumer of requests.
     **/
    private final LinkedBlockingQueue<Request> requests = new LinkedBlockingQueue<Request>();

    /**
     * Queues for double producer - consumer scheme.
     * MessageGrinder acts as producer with respect to responses, while Client acts as consumer of responses.
     **/
    private final LinkedBlockingQueue<Response> responses = new LinkedBlockingQueue<Response>();

    /**
     * Channel for data transfer. Available implementation: WNP or socket
     **/
    private final Object channelLocker = new Object();
    private DataTransferChannel channel;

    /**
     * Inner request-response processor. Working as separate thread.
     **/
    private MessageGrinder grinder;


    /**
     * Control layer
     **/
    public Client(ChannelType channelType) {
        switch (channelType) {
            case PIPE: {
                this.channel = new WindowsNamedPipe();
                break;
            }
            case SOCKET: {
                this.channel = new SocketChannel("localhost", 8989);
                break;
            }
            default: {
                this.channel = new WindowsNamedPipe();
                break;
            }
        }
    }

    public Boolean isConnected() {
        return isConnected;
    }

    public String getError() {
        String errorMessage;
        synchronized (channelLocker) {
            errorMessage = channel.getErrorMessage();
        }
        return errorMessage;
    }


    public void connect() {
        synchronized (channelLocker) {
            this.isConnected = channel.open();
        }
    }


    public void disconnect() throws IOException {
        synchronized (channelLocker) {
            channel.close();
            this.isConnected = Boolean.FALSE;
        }
    }


    public void run() {
        timingManager.reset();
        grinder = new MessageGrinder();
        grinder.setName("MessageGrinder");
        grinder.start();
        isStopped = Boolean.FALSE;
    }


    /**
     * Causes stop of client to server message exchange process.
     **/
    public void stop() throws InterruptedException {
        grinder.interrupt();
        grinder.join();
        isStopped = Boolean.TRUE;
    }


    /**
     * Producer's method to populate queue of requests
     **/
    public void postRequest(Request request) throws MessageGrinderEmergencyAbortException, InterruptedException {
        if (grinder.isEmergencyAborted()) {
            Exception error = grinder.getError();
            throw new MessageGrinderEmergencyAbortException(error.getMessage(), error);
        }
        requests.put(request);
    }


    /**
     * Producer's method to get the result of the request.
     * Actually, in this case Client class acts as consumer of responses.
     **/
    public Response getResponse() throws MessageGrinderEmergencyAbortException, InterruptedException {
        Response response = responses.take();
        if (response instanceof EndOfStreamResponse) {
            Exception error = grinder.getError();
            throw new MessageGrinderEmergencyAbortException(error.getMessage(), error);
        }
        timingManager.addTimingDataRow(response);
        return response;
    }


    public Boolean isStopped() {
        return isStopped;
    }


    /**
     * Access to time statistics of response-request data flow process.
     **/
    public Float getAvgShippingDurationOfRequest(RequestSubject subject) {
        return timingManager.getAvgShippingDurationOfRequest(subject);
    }

    public Float getAvgDurationOfRequestProcessing(RequestSubject subject) {
        return timingManager.getAvgDurationOfRequestProcessing(subject);
    }

    public Float getAvgShippingDurationOfResponse(RequestSubject subject) {
        return timingManager.getAvgShippingDurationOfResponse(subject);
    }

    public Float getAvgRequestResponseLatency(RequestSubject subject) {
        return timingManager.getAvgRequestResponseLatency(subject);
    }


    /**
     * MessageGrinder class takes care about request - response processing between client and server.
     * <p>
     * It executes client's main loop:
     * 1. Take request from queue
     * 2. Serialize request into json and send it to server
     * 3. Receive response from server and deserialize from json it into java-object
     * 4. Put response into queue and go to next request
     * <p>
     * Execution of MessageGrinder makes sense only when client is connected to server.
     * <p>
     * So, scenario is as follows:
     * 1. If client successfully connects to server - start MessageGrinder thread
     * 2. MessageGrinder processes incoming requests
     * 3. If client looses channel or instructed to stop or end session:
     * 3.1. Interrupt thread
     * 3.2. Main thread MUST wait until MessageGrinder thread finishes all operations
     * <p>
     * <p>
     * Exception's politics:
     * The hard link between request and response is considered.
     * The situations when client cannot write\read\serialize\deserialize request or response,
     * cannot receive valid (in sense of id) response for given request are considered as serious errors in message flow process.
     * In such situations client aborts all operations, shut down the channel and clean channel descriptor, notify at api's level about error.
     **/
    private class MessageGrinder extends Thread {
        private static final String END_OF_SESSION_MESSAGE = "CLIENT_OFF";

        /**
         * Client switches between two modes:
         * 1 - writing message to sever
         * 2 - reading answer from server
         **/
        private int clientMode = 1;

        private boolean isInterrupted = false;

        /**
         * Flag for emergency stop of the grinder.
         **/
        private Boolean isEmergencyAborted = Boolean.FALSE;

        /**
         * In order to know what's happens.
         **/
        private Exception error;

        /**
         * For each response the corresponding request has to obtained.
         * If client has been instructed to shut down and response for last sent request has not been received,
         * then MessageGrinder has to work until this response will be received.
         **/
        private Boolean isLastResponseReceived = Boolean.TRUE;


        public synchronized Boolean isEmergencyAborted() {
            return isEmergencyAborted;
        }


        public synchronized Exception getError() {
            return new Exception(error);
        }


        public String readMessage() throws IOException, InterruptedException {
            synchronized (channelLocker) {
                return channel.readMessage();
            }
        }


        public void writeMessage(String request) throws IOException {
            synchronized (channelLocker) {
                channel.writeMessage(request);
            }
        }


        /**
         * The hard link between request and response is considered.
         **/
        private boolean validateResponse(Request request, Response response) {
            if (request == null) return false;
            if (request.getId().equals(response.getRequestId())) return true;
            return false;
        }


        private Boolean needContinueWork() {
            if (isEmergencyAborted) return false;
            if (isInterrupted && isLastResponseReceived) return false;
            return true;
        }


        private Request executeWriteStep() throws InterruptedException, IOException {
            Request request;
            request = requests.take();
            if (request == null) return null;

            request.fixSendingTime();
            String rawJSONRequest = messagesManager.serializeRequest(request);

            if (rawJSONRequest != null && !rawJSONRequest.isEmpty()) {
                writeMessage(rawJSONRequest);
                isLastResponseReceived = Boolean.FALSE;
                clientMode = 2;
            }

            return request;
        }


        private void executeReadStep(Request request) throws InterruptedException, IOException {
            String rawJSONResponse = readMessage();
            Response response = messagesManager.deserializeResponse(rawJSONResponse);
            if (response != null) {
                if (validateResponse(request, response)) {
                    response.setTimeOfReceiptOfResponseAtClient(System.currentTimeMillis());
                    responses.put(response);
                    isLastResponseReceived = Boolean.TRUE;
                    clientMode = 1;
                }
            }
        }


        /**
         * ACHTUNG!!!
         * <p>
         * This is important!
         * One need to handle sudden server's shut down in some way.
         * This cause exceptions on the MessageGrinder side, which break execution of read-write loop.
         * If this happens before client posts the request, there is no reason to worry.
         * postRequest() method of client throws exception in this case.
         * if server stops working after received the request, the response might not yet be prepared.
         * Then, due to the empty responses queue, call of responses.take() at client side will waite forever.
         * <p>
         * In order to notify responses.take() at client side about termination of the MessageGrinder thread execution (no responses anymore)
         * docs for BlockingQueue suggest to add some king of end-of-stream object to awake take() from waiting.
         * <p>
         * signalEmergencyAbort() method add special subtype of Response class to queue in situation of emergency termination of MessageGrinder.
         * Since, this is the situation of emergency termination there is no need to care about InterruptedException
         **/
        private void signalEmergencyAbort() {
            try {
                responses.put(new EndOfStreamResponse());
            } catch (InterruptedException ignore) { }
        }


        @Override
        public void run() {
            MessageGrinder.this.isInterrupted = false;
            Request request = null;
            try {
                while (needContinueWork()) {
                    try {
                        if (clientMode == 1) {
                            request = executeWriteStep();
                        } else if (clientMode == 2) {
                            executeReadStep(request);
                        }
                    } catch (InterruptedException e) {
                        MessageGrinder.this.isInterrupted = true;
                    }
                }
                writeMessage(END_OF_SESSION_MESSAGE);
            } catch (Exception e) {
                MessageGrinder.this.isEmergencyAborted = Boolean.TRUE;
                error = e;
                signalEmergencyAbort();
            }
        }
    }
}