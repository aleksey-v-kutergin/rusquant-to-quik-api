package ru.rusquant.messages.response;

/**
 *    The special type of response.
 *    Used to unlock responses.take() call if MessagesGrinder thread has been emergency aborted.
 *    Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *    Company: Rusquant
 */
public class EndOfStreamResponse extends Response
{

}
