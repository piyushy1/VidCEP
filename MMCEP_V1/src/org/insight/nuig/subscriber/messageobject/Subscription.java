/**
 * 
 */
package org.insight.nuig.subscriber.messageobject;

import java.io.Serializable;

/**
 * @author Asra
 *
 */
public class Subscription implements Serializable {

	/**
	 * 
	 */
	private String Subscriber_ID;
	private long SendingTime_sub;
	private Serializable payload_sub;
	
	public Subscription(String Subscriber_ID, long SendingTime_sub, Serializable payload_sub) {
		// TODO Auto-generated constructor stub
		super();
		this.Subscriber_ID = Subscriber_ID;
		this.SendingTime_sub = SendingTime_sub;
		this.payload_sub = payload_sub;
	}

	public String getSubscriber_ID() {
		return Subscriber_ID;
	}
	/**
	 * @param SubscriberID the SubscriberID to set
	 */
	public void setSubscriber_ID(String Subscriber_ID) {
		this.Subscriber_ID = Subscriber_ID;
	}
	
	public Serializable getPayload_sub() {
		return payload_sub;
	}
	/**
	 * @param payload the payload to set
	 */
	public void setPayload_sub(Serializable payload_sub) {
		this.payload_sub = payload_sub;
	}
	/**
	 * @return the timeStamp
	 */
	public long getSendingTime_sub() {
		return SendingTime_sub;
	}
	/**
	 * @param timeStamp the timeStamp to set
	 */
	public void setSendingTime_sub(long SendingTime_sub) {
		this.SendingTime_sub = SendingTime_sub;
	}
}
