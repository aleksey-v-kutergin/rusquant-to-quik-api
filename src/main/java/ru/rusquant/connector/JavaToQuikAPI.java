package ru.rusquant.connector;

import ru.rusquant.data.quik.QuikDataObject;
import ru.rusquant.data.quik.Transaction;


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


	/**
	 *    Returns the state of connection between QUIK terminal and QUIK (Broker) server.
	 *    @return:
	 *    1 - if terminal is connected
	 *    0 - otherwise
	 **/
	QuikDataObject isConnected();


	/**
	 *    Returns values of the parameters listed in QUIK-terminal info window (System/About/Information window)
	 *    Available parameters can be found in QLUA specification and in InfoParamType class.
	 *    If value of some parameter is not a available in terminal (for instance terminal is not connected),
	 *    function set value of such parameter to empty string.
	 **/
	QuikDataObject getInfoParam(String paramName);


	/**
	 *    Java-implementation of sendTransaction() function, specified in QLua docs
	 **/
	QuikDataObject sendTransaction(Transaction transaction);


	/** Returns object, received in OnOrder(order) callback  **/
	QuikDataObject getOrder(Long orderNumber);


	/** Return trades for order **/
	QuikDataObject getTrades(Long orderNumber);


	/**
	 *   Implementation of getNumberOf(STRING TableName) function.
	 *   Returns the number of rows for table <tableName>
	 **/
	QuikDataObject getNumberOfRows(String tableName);


    /**
     *   Implementation of TABLE getItem (STRING TableName, DOUBLE Index) function.
     *   Returns a row with rowNumber = index and name = tableName.
     *   index belongs to range [0, rowsCount], where rowsCount - result of getNumberOfRows(tableName).
     **/
    QuikDataObject getItem(String tableName, Integer index);
}
