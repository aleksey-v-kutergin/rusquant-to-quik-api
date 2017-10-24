package ru.rusquant.data.quik;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.rusquant.data.quik.types.*;

/**
 * According to Qlua doccs, java representation of the sendTransaction() argument.
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction extends QuikDataObject
{
    @JsonProperty("TRANS_ID")
    private Long                transId;        /** User's defined id of the transaction. **/

    @JsonProperty("ACTION")
    private ActionType          action;         /** Aim of the transaction **/

    @JsonProperty("STATUS")
    private TransactionStatus   status;         /** Resulting status of the transaction **/

    @JsonProperty("RESULT_MSG")
    private String              resultMsg;      /** Message **/

    @JsonProperty("TIME")
    private Long                time;           /** Date-Time in Unix-format **/

    @JsonProperty("UID")
    private Long                uid;

    @JsonProperty("FLAGS")
    private Integer             flags;          /** Flags of the transaction (temporally not used). **/

    @JsonProperty("SERVER_TRANS_ID")
    private Long                serverTransId;  /** Id of the transaction, assigned at server **/

    @JsonProperty("ORDER_NUM")
    private Long                orderNum;       /** Number of order **/

    @JsonProperty("PRICE")
    private Double              price;

    @JsonProperty("QUANTITY")
    private Double              quantity;

    @JsonProperty("BALANCE")
    private Double              balance;

    @JsonProperty("FIRM_ID")
    private String              firmId;         /** Id of the company **/

    @JsonProperty("ACCOUNT")
    private String              account;        /** Trading account **/

    @JsonProperty("CLIENT_CODE")
    private String              clientCode;

    @JsonProperty("BROKERREF")
    private String              brokerRef;      /** Assignment **/

    @JsonProperty("CLASSCODE")
    private String              classCode;      /** Code of security class **/

    @JsonProperty("SECCODE")
    private String              secCode;        /** Security code **/

    @JsonProperty("OPERATION")
    private OperationType       operation;      /** Type of operation. For order Buy or Sell  **/

    @JsonProperty("TYPE")
    private OrderType           type;           /** Market or Limit **/

    @JsonProperty("EXCHANGE_CODE")
    private String              exchangeCode;   /** Exchange number of order **/

    @JsonProperty("COMMENT")
    private String              comment;

    @JsonProperty("MODE")
    private TransactionMode     mode;           /** Execution mode for sendTransaction() server-side wrapper **/

    @JsonProperty("DATETIME")
    private DateTime dateTime;

    public Transaction()
    {

    }

    @Override
    public String toString()
    {
        String str = "Replay for transaction: ";
        str += "\n\t\tTransaction id: " 		+ transId;
        str += "\n\t\tAction: " 				+ action;
        str += "\n\t\tStatus: " 				+ status;
        str += "\n\t\tResult message: " 		+ resultMsg;
        str += "\n\t\tTime: " 					+ time;
        str += "\n\t\tUID: " 					+ uid;
        str += "\n\t\tServer trans id: " 		+ serverTransId;
        str += "\n\t\tOrder num: " 				+ orderNum;
        str += "\n\t\tPrice: " 					+ price;
        str += "\n\t\tQuantity: " 				+ quantity;
        str += "\n\t\tBalance: " 				+ balance;
        str += "\n\t\tFirm id: " 				+ firmId;
        str += "\n\t\tAccount: " 				+ account;
        str += "\n\t\tClient code: " 			+ clientCode;
        str += "\n\t\tBroker ref: " 			+ brokerRef;
        str += "\n\t\tClass code: " 			+ classCode;
        str += "\n\t\tSec code: " 				+ secCode;
        str += "\n\t\tExchange code: " 			+ exchangeCode;
        str += "\n\t\tOperation: " 				+ operation;
        str += "\n\t\tType: " 					+ type;
        str += "\n\t\tComment: " 				+ comment;
        str += "\n\t\tMode: " 					+ mode;
        return str;
    }

    public Long getTransId()
    {
        return transId;
    }

    public void setTransId(Long transId)
    {
        this.transId = transId;
    }

    public ActionType getAction()
    {
        return action;
    }

    public void setAction(ActionType action)
    {
        this.action = action;
    }

    public TransactionStatus getStatus()
    {
        return status;
    }

    public void setStatus(TransactionStatus status)
    {
        this.status = status;
    }

    public String getResultMsg()
    {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg)
    {
        this.resultMsg = resultMsg;
    }

    public Long getTime()
    {
        return time;
    }

    public void setTime(Long time)
    {
        this.time = time;
    }

    public Long getUid()
    {
        return uid;
    }

    public void setUid(Long uid)
    {
        this.uid = uid;
    }

    public Integer getFlags()
    {
        return flags;
    }

    public void setFlags(Integer flags)
    {
        this.flags = flags;
    }

    public Long getServerTransId()
    {
        return serverTransId;
    }

    public void setServerTransId(Long serverTransId)
    {
        this.serverTransId = serverTransId;
    }

    public Long getOrderNum()
    {
        return orderNum;
    }

    public void setOrderNum(Long orderNum)
    {
        this.orderNum = orderNum;
    }

    public Double getPrice()
    {
        return price;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

    public Double getQuantity()
    {
        return quantity;
    }

    public void setQuantity(Double quantity)
    {
        this.quantity = quantity;
    }

    public Double getBalance()
    {
        return balance;
    }

    public void setBalance(Double balance)
    {
        this.balance = balance;
    }

    public String getFirmId()
    {
        return firmId;
    }

    public void setFirmId(String firmId)
    {
        this.firmId = firmId;
    }

    public String getAccount()
    {
        return account;
    }

    public void setAccount(String account)
    {
        this.account = account;
    }

    public String getClientCode()
    {
        return clientCode;
    }

    public void setClientCode(String clientCode)
    {
        this.clientCode = clientCode;
    }

    public String getBrokerRef()
    {
        return brokerRef;
    }

    public void setBrokerRef(String brokerRef)
    {
        this.brokerRef = brokerRef;
    }

    public String getClassCode()
    {
        return classCode;
    }

    public void setClassCode(String classCode)
    {
        this.classCode = classCode;
    }

    public String getSecCode()
    {
        return secCode;
    }

    public void setSecCode(String secCode)
    {
        this.secCode = secCode;
    }

    public String getExchangeCode()
    {
        return exchangeCode;
    }

    public void setExchangeCode(String exchangeCode)
    {
        this.exchangeCode = exchangeCode;
    }

    public OperationType getOperation()
    {
        return operation;
    }

    public void setOperation(OperationType operation)
    {
        this.operation = operation;
    }

    public OrderType getType()
    {
        return type;
    }

    public void setType(OrderType type)
    {
        this.type = type;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public TransactionMode getMode()
    {
        return mode;
    }

    public void setMode(TransactionMode mode)
    {
        this.mode = mode;
    }

    public DateTime getDateTime()
    {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime)
    {
        this.dateTime = dateTime;
    }
}
