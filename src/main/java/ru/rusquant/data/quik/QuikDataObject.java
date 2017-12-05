package ru.rusquant.data.quik;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.rusquant.data.quik.dataframe.OhlcDataFrame;
import ru.rusquant.data.quik.dataframe.TradesDataFrame;
import ru.rusquant.data.quik.descriptor.DatasourceDescriptor;
import ru.rusquant.data.quik.descriptor.Descriptor;
import ru.rusquant.data.quik.descriptor.ParameterDescriptor;
import ru.rusquant.data.quik.descriptor.QuotesDescriptor;
import ru.rusquant.data.quik.table.*;

/**
 *    Base class for all QUIK data structures
 *    Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *    Company: Rusquant
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = QuikEcho.class, name = "Echo"),
		@JsonSubTypes.Type(value = Descriptor.class, name = "Descriptor"),
		@JsonSubTypes.Type(value = ConnectionState.class, name = "ConnectionState"),
		@JsonSubTypes.Type(value = InfoParameter.class, name = "InfoParameter"),
		@JsonSubTypes.Type(value = Transaction.class, name = "Transaction"),
		@JsonSubTypes.Type(value = DateTime.class, name = "DateTime"),
		@JsonSubTypes.Type(value = Order.class, name = "Order"),
		@JsonSubTypes.Type(value = Trade.class, name = "Trade"),
		@JsonSubTypes.Type(value = TradesDataFrame.class, name = "TradesDataFrame"),
		@JsonSubTypes.Type(value = OhlcDataFrame.class, name = "OhlcDataFrame"),
		@JsonSubTypes.Type(value = QuikTableInfo.class, name = "QuikTableInfo"),
		@JsonSubTypes.Type(value = QuikDataObject.class, name = "QuikDataObject"),
		@JsonSubTypes.Type(value = Firm.class, name = "Firm"),
		@JsonSubTypes.Type(value = SecurityClass.class, name = "SecurityClass"),
		@JsonSubTypes.Type(value = Security.class, name = "Security"),
		@JsonSubTypes.Type(value = TradingAccount.class, name = "TradingAccount"),
		@JsonSubTypes.Type(value = ClientCode.class, name = "ClientCode"),
		@JsonSubTypes.Type(value = AnonymousTrade.class, name = "AnonymousTrade"),
		@JsonSubTypes.Type(value = AccountPosition.class, name = "AccountPosition"),
		@JsonSubTypes.Type(value = MoneyLimit.class, name = "MoneyLimit"),
		@JsonSubTypes.Type(value = DepoLimit.class, name = "DepoLimit"),
		@JsonSubTypes.Type(value = FirmHolding.class, name = "FirmHolding"),
		@JsonSubTypes.Type(value = AccountBalance.class, name = "AccountBalance"),
		@JsonSubTypes.Type(value = CppPosition.class, name = "CppPosition"),
		@JsonSubTypes.Type(value = CppHolding.class, name = "CppHolding"),
		@JsonSubTypes.Type(value = StopOrder.class, name = "StopOrder"),
		@JsonSubTypes.Type(value = NegDeal.class, name = "NegDeal"),
		@JsonSubTypes.Type(value = NegTrade.class, name = "NegTrade"),
		@JsonSubTypes.Type(value = NegDealReport.class, name = "NegDealReport"),
		@JsonSubTypes.Type(value = FuturesClientHolding.class, name = "FuturesClientHolding"),
		@JsonSubTypes.Type(value = FuturesClientLimit.class, name = "FuturesClientLimit"),
		@JsonSubTypes.Type(value = Candle.class, name = "Candle"),
		@JsonSubTypes.Type(value = TradingParameter.class, name = "TradingParameter"),
		@JsonSubTypes.Type(value = DatasourceDescriptor.class, name = "DatasourceDescriptor"),
		@JsonSubTypes.Type(value = TradeDate.class, name = "TradeDate"),
		@JsonSubTypes.Type(value = CountOfLots.class, name = "CountOfLots"),
		@JsonSubTypes.Type(value = CodesArray.class, name = "CodesArray"),
		@JsonSubTypes.Type(value = ParameterDescriptor.class, name = "ParameterDescriptor"),
		@JsonSubTypes.Type(value = QuotesDescriptor.class, name = "QuotesDescriptor"),
		@JsonSubTypes.Type(value = BooleanResult.class, name = "BooleanResult"),
		@JsonSubTypes.Type(value = OrderBookLevel.class, name = "OrderBookLevel"),
		@JsonSubTypes.Type(value = OrderBook.class, name = "OrderBook"),
		@JsonSubTypes.Type(value = LongResult.class, name = "LongResult"),
		@JsonSubTypes.Type(value = ErrorObject.class, name = "ErrorObject")
})
public abstract class QuikDataObject { }
