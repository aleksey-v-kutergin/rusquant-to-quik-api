package ru.rusquant.data.mql;

/**
 *    Echo data object.
 *    Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *    Company: Rusquant
 */
public class MqlEcho extends MqlDataObject
{
    private String echoAnswer;

    public MqlEcho()
    {

    }

    public MqlEcho(String echoAnswer)
    {
        this.echoAnswer = echoAnswer;
    }

    public String getEchoAnswer()
    {
        return echoAnswer;
    }

    public void setEchoAnswer(String echoAnswer)
    {
        this.echoAnswer = echoAnswer;
    }
}
