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
		@JsonSubTypes.Type(value = DateTime.class, name = "DateTime"),
		@JsonSubTypes.Type(value = Order.class, name = "Order"),
		@JsonSubTypes.Type(value = Trade.class, name = "Trade"),
		@JsonSubTypes.Type(value = TradesDataFrame.class, name = "TradesDataFrame"),
		@JsonSubTypes.Type(value = QuikTableInfo.class, name = "QuikTableInfo"),
		@JsonSubTypes.Type(value = QuikDataObject.class, name = "QuikDataObject"),
		@JsonSubTypes.Type(value = Firm.class, name = "Firm"),
		@JsonSubTypes.Type(value = SecurityClass.class, name = "SecurityClass"),
		@JsonSubTypes.Type(value = Security.class, name = "Security"),
		@JsonSubTypes.Type(value = TradingAccount.class, name = "TradingAccount"),
		@JsonSubTypes.Type(value = AnonymousTrade.class, name = "AnonymousTrade"),
		@JsonSubTypes.Type(value = AccountPosition.class, name = "AccountPosition"),
		@JsonSubTypes.Type(value = MoneyLimit.class, name = "MoneyLimit"),
		@JsonSubTypes.Type(value = DepoLimit.class, name = "DepoLimit"),
		@JsonSubTypes.Type(value = FirmHolding.class, name = "FirmHolding"),
		@JsonSubTypes.Type(value = AccountBalance.class, name = "AccountBalance"),
		@JsonSubTypes.Type(value = CppPosition.class, name = "CppPosition"),
		@JsonSubTypes.Type(value = ErrorObject.class, name = "ErrorObject")
})
public abstract class QuikDataObject
{

}
