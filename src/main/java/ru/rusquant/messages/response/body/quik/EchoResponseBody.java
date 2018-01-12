package ru.rusquant.messages.response.body.quik;

import ru.rusquant.data.quik.QuikEcho;
import ru.rusquant.messages.response.body.ResponseBody;

public class EchoResponseBody extends ResponseBody {

    private QuikEcho echo;

    public EchoResponseBody() {

    }

    public EchoResponseBody(QuikEcho echo) {
        this.echo = echo;
    }

    public QuikEcho getEcho() {
        return echo;
    }
}
