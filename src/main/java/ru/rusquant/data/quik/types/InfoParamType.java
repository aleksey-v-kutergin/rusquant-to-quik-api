package ru.rusquant.data.quik.types;

/**
 *    List of available arguments of the getInfoParamFunction() from QLUA specification.
 *    Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *    Company: Rusquant
 */
public enum InfoParamType
{
	/** Version pf terminal app **/
	VERSION,

	/** Date of trade **/
	TRADEDATE,

	/** Time of QUIK (Broker) server **/
	SERVERTIME,

	/** Time of last record **/
	LASTRECORDTIME,

	/** Number of records **/
	NUMRECORDS,

	/** Last record **/
	LASTRECORD,

	/** Remaining record **/
	LATERECORD,

	/** Connection **/
	CONNECTION,

	/** IP-adress of QUIK (Broker) server **/
	IPADDRESS,

	/** Port of QUIK (Broker) server **/
	IPPORT,

	/** Connection description **/
	IPCOMMENT,

	/** Description of QUIK (Broker) server **/
	SERVER,

	/** ID of the current session **/
	SESSIONID,

	/** User **/
	USER,

	/** ID of the current user **/
	USERID,

	/** Name of the organization **/
	ORG,

	/** Occupied RAM **/
	MEMORY,

	/** Time of local machine **/
	LOCALTIME,

	/** Duration of current connection **/
	CONNECTIONTIME,

	/** Count of messages sent **/
	MESSAGESSENT,

	/** Count of bytes sent **/
	ALLSENT,

	/** Count of "usefull" bytes sent **/
	BYTESSENT,

	/** Send-rate: count of bytes per-second **/
	BYTESPERSECSENT,

	/** Count of received messages **/
	MESSAGESRECV,

	/** Count of "usefull" bytes received **/
	BYTESRECV,

	/** Count of bytes received **/
	ALLRECV,

	/** Receive-rate: count of bytes per-second **/
	BYTESPERSECRECV,

	/** Average rate of outcoming bytes traffic **/
	AVGSENT,

	/** Average rate of incoming bytes traffic **/
	AVGRECV,

	/** Time of last check of the connection **/
	LASTPINGTIME,

	/** Latency of data exchange between QUIK-terminal and QUIK (Broker) server **/
	LASTPINGDURATION,

	/** Average latency of data exchange between QUIK-terminal and QUIK (Broker) server **/
	AVGPINGDURATION,

	/** Max latency of data exchange between QUIK-terminal and QUIK (Broker) server **/
	MAXPINGTIME,

	/** Min latency of data exchange between QUIK-terminal and QUIK (Broker) server **/
	MAXPINGDURATION;

	public static boolean contains(String parameter)
	{
		for(InfoParamType type : InfoParamType.values())
		{
			if( type.toString().equalsIgnoreCase(parameter) )
			{
				return true;
			}
		}
		return false;
	}

	public static String getAvailableParameters()
	{
		String availableParameters = "[ ";
		int counter = 1;
		for(InfoParamType type : InfoParamType.values())
		{
			availableParameters += "\t\t" + type.toString().toUpperCase();
			if(counter < InfoParamType.values().length) { availableParameters += ", "; }
			counter++;
		}
		availableParameters += " ]";
		return availableParameters;
	}
}
