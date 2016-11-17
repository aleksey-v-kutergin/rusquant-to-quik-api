package ru.rusquant.connector;

import ru.rusquant.data.quik.QuikDataObject;

/**
 *    Interface contains prototypes for all methods to work with QUIK terminal.
 *    Each class, willing to act as connector to terminal has to implement this interface.
 *    Each method of api returns corresponding subtype of base quik data object or error data object if error occurs during operation.
 *    Such hierarchy of data object allows to provide user with valid result or report about error.
 *    All users of the API are free to decide how to map api data objects for their purposes.
 *
 *    Example:
 *
 *    String someUserMethod()
 *    {
 *        QuikDataObject result connector.getEcho("hello world!")
 *        if(result instanceof ErrorObject)
 *        {
 *            // report about error
 *        }
 *        else
 *        {
 *            // Do something useful :)
 *        }
 *    }
 *
 *    This all is because i wanna handle all exceptions from client at connector level.
 *    User of the connector gets a valid result or error. Nothing more, nothing less.
 *
 *    Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *    Company: Rusquant
 */
public interface JavaToQuikAPI
{
	/**
	 *    Just say hello world!:)
	 **/
	QuikDataObject getEcho(String message);


	QuikDataObject isConnected();
}
