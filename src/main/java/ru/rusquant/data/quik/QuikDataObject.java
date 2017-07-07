package ru.rusquant.data.quik;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 *    Base class for all QUIK data structures
 *    Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *    Company: Rusquant
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = Echo.class, name = "Echo"),
		@JsonSubTypes.Type(value = ConnectionState.class, name = "ConnectionState"),
		@JsonSubTypes.Type(value = InfoParameter.class, name = "InfoParameter"),
		@JsonSubTypes.Type(value = Transaction.class, name = "Transaction"),
		@JsonSubTypes.Type(value = ErrorObject.class, name = "ErrorObject")
})
public abstract class QuikDataObject
{

}
