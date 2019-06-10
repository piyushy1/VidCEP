/**
 * 
 */
package org.insight.nuig.publisher.messageobject;

import java.io.Serializable;
import java.util.Date;

/**
 * @author piyush
 * @date 11 Jul 2018
 * @time 09:48:59
 * @Institute Insight Centre for Data Analytics- NUIG
 
 * @ClassDefinition
 * The class defines the publication event which are published by the event source.
 
 */


public class PublicationEvent implements Serializable {
	
	private String pubID;
	private long timeStamp;
	private Serializable payload;

	/**
	 * @param pubID
	 * @param timeStamp
	 * @param payload
	 */
	public PublicationEvent(String pubID, long timeStamp, Serializable payload) {
		super();
		this.pubID = pubID;
		this.timeStamp = timeStamp;
		this.payload = payload;
	}
	
	
	/**
	 * 
	 */
	public PublicationEvent() {
		super();
		// TODO Auto-generated constructor stub
	}


	/**
	 * @return the pubID
	 */
	
	public String getPubID() {
		return pubID;
	}
	/**
	 * @param pubID the pubID to set
	 */
	public void setPubID(String pubID) {
		this.pubID = pubID;
	}
	/**
	 * @return the payload
	 */
	public Serializable getPayload() {
		return payload;
	}
	/**
	 * @param payload the payload to set
	 */
	public void setPayload(Serializable payload) {
		this.payload = payload;
	}
	/**
	 * @return the timeStamp
	 */
	public long getTimeStamp() {
		return timeStamp;
	}
	/**
	 * @param timeStamp the timeStamp to set
	 */
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	
}
