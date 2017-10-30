package ru.rusquant.messages.request;

/**
 *   Subject of the request.
 *   Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *   Company: Rusquant
 */
public enum RequestSubject
{
	ECHO,
	ORDER,
	CONNECTION_SATE,
	INFO_PARAMETER,
	TRANSACTION,
	TRADE,
	TABLE_INFO,
    TABLE_ITEM,
    TABLE_ITEMS
}
