package ru.rusquant.data.quik;

import ru.rusquant.data.quik.types.*;

/**
 * According to Qlua doccs, java representation of the sendTransaction() argument.
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
public class Transaction extends QuikDataObject
{
    private Long                transId;        /** User's defined id of the transaction. **/
    private ActionType          action;         /** Aim of the transaction **/
    private TransactionStatus   status;         /** Resulting status of the transaction **/
    private String              resultMsg;      /** Message **/
    private Long                time;           /** Date-Time in Unix-format **/
    private Long                uid;
    private Integer             flags;          /** Flags of the transaction (temporally not used). **/
    private Long                serverTransId;  /** Id of the transaction, assigned at server **/
    private Long                orderNum;       /** Number of order **/
    private Double              price;
    private Double              quantity;
    private Double              balance;
    private String              firmId;         /** Id of the company **/
    private String              account;        /** Trading account **/
    private String              clientCode;
    private String              brokerRef;      /** Assignment **/
    private String              classCode;      /** Code of security class **/
    private String              secCode;        /** Security code **/
    private OperationType       operation;      /** Type of operation. For order Buy or Sell  **/
    private OrderType           type;           /** Market or Limit **/
    private String              exchangeCode;   /** Exchange number of order **/
    private String              comment;
    private TransactionMode mode;               /** Execution mode for sendTransaction() server-side wrapper **/

    public Transaction()
    {

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
}
