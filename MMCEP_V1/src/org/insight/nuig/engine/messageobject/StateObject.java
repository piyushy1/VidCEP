/**
 * 
 */
package org.insight.nuig.engine.messageobject;

import java.util.List;

import org.insight.nuig.publisher.messageobject.PublicationGraphEvent;

/**
 * @author piyush
 * @date 15 Nov 2018 19:37:11
 * @Institute: Insight Centre for Data Analytics
 * @Project: MMCEP: Multimedia Complex Event Processing
 * @CLassDefinition 
 */

// this state object is directly transfered to the query store.... 
public class StateObject {

	public List<PublicationGraphEvent> state;
	public String win_name;
	public String query_name;
	public long statesend_matcher_time;
	public long state_recieve_matcher_time;
	/**
	 * @return the state_recieve_matcher_time
	 */
	public long getState_recieve_matcher_time() {
		return state_recieve_matcher_time;
	}

	/**
	 * @param state_recieve_matcher_time the state_recieve_matcher_time to set
	 */
	public void setState_recieve_matcher_time(long state_recieve_matcher_time) {
		this.state_recieve_matcher_time = state_recieve_matcher_time;
	}

	/**
	 * 
	 */
	public StateObject() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the statesend_matcher_time
	 */
	public long getStatesend_matcher_time() {
		return statesend_matcher_time;
	}

	/**
	 * @param statesend_matcher_time the statesend_matcher_time to set
	 */
	public void setStatesend_matcher_time(long statesend_matcher_time) {
		this.statesend_matcher_time = statesend_matcher_time;
	}

	/**
	 * @param state
	 * @param win_name
	 * @param query_name
	 * @param statesend_matcher_time
	 */
	public StateObject(List<PublicationGraphEvent> state, String win_name, String query_name,
			long statesend_matcher_time) {
		super();
		this.state = state;
		this.win_name = win_name;
		this.query_name = query_name;
		this.statesend_matcher_time = statesend_matcher_time;
	}

	/**
	 * @return the state
	 */
	public List<PublicationGraphEvent> getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(List<PublicationGraphEvent> state) {
		this.state = state;
	}
	/**
	 * @return the win_name
	 */
	public String getWin_name() {
		return win_name;
	}
	/**
	 * @param win_name the win_name to set
	 */
	public void setWin_name(String win_name) {
		this.win_name = win_name;
	}
	/**
	 * @return the query_name
	 */
	public String getQuery_name() {
		return query_name;
	}
	/**
	 * @param query_name the query_name to set
	 */
	public void setQuery_name(String query_name) {
		this.query_name = query_name;
	}
	
	
	
}

