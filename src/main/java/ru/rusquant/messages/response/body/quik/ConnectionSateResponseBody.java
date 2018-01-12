package ru.rusquant.messages.response.body.quik;

import ru.rusquant.data.quik.ConnectionState;
import ru.rusquant.messages.response.body.ResponseBody;

public class ConnectionSateResponseBody extends ResponseBody {

    private ConnectionState connectionState;

    public ConnectionSateResponseBody() {

    }

    public ConnectionSateResponseBody(ConnectionState connectionState) {
        this.connectionState = connectionState;
    }

    public ConnectionState getConnectionState() {
        return connectionState;
    }
}
