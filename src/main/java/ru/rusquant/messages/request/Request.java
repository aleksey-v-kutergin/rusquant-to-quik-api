package ru.rusquant.messages.request;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.rusquant.messages.request.body.RequestBody;
import ru.rusquant.messages.request.body.RequestSubject;

/**
 * Class encapsulated data, which are common for all Client requests.
 * Client sends requests in json format.
 * Logic of request: GET\POST (KIND OF ACTION) ECHO\ORDER (WHAT?) parameters (RequestBody)
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
@JsonAutoDetect
public class Request {
    /**
     * Unit identifier within single client's session
     * This is service parameter.
     **/
    private Long id;

    /**
     * The time when the request was sent in UNIX format.
     * This is service parameter.
     **/
    private Long time;


    /**
     * The type of request:
     * 1. GET - get some data from server
     * 2. POST - execute some operation at server side
     **/
    private RequestType type;


    /**
     * Subject of request: subject of get or post operation.
     * For example: GET ECHO, GET TIME, POST ORDER .. etc.
     **/
    private RequestSubject subject;


    /**
     * Parameters of the request.
     * Particular object depends on type and subject of the request.
     **/
    private RequestBody body;


    public Request() {

    }


    public Request(Long id, RequestType type, RequestSubject subject, RequestBody body) {
        this.id = id;
        this.type = type;
        this.subject = subject;
        this.body = body;
    }

    public void fixSendingTime() {
        this.time = System.currentTimeMillis();
    }

    public Long getId() {
        return id;
    }

    public Long getTime() {
        return time;
    }

    public RequestBody getBody() {
        return body;
    }

    public RequestType getType() {
        return type;
    }

    public RequestSubject getSubject() {
        return subject;
    }
}
