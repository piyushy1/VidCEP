/**
 * 
 */
package org.insight.nuig.publisher.messageobject;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

/**
 * @author piyush
 * @date 3 Nov 2018 14:27:59
 * @Institute: Insight Centre for Data Analytics
 * @Project: MMCEP: Multimedia Complex Event Processing
 * @CLassDefinition This class defines the publication graph object which will be our core representation for MMCEP
 */
public class PublicationGraphEvent implements Cloneable {
	
	public String publisherID;
	public int eventID;
	public long event_gen_timeStamp;
	public long event_preprocess_timestamp;
	public long event_video_decode_time;
	public long object_detection_time;
	public long object_tracking_time;
	public long attribute_detection_time;
	public long graph_construction_time;
	/**
	 * @return the event_video_decode_time
	 */
	public long getEvent_video_decode_time() {
		return event_video_decode_time;
	}
	/**
	 * @param event_video_decode_time the event_video_decode_time to set
	 */
	public void setEvent_video_decode_time(long event_video_decode_time) {
		this.event_video_decode_time = event_video_decode_time;
	}
	/**
	 * @return the object_detection_time
	 */
	public long getObject_detection_time() {
		return object_detection_time;
	}
	/**
	 * @param object_detection_time the object_detection_time to set
	 */
	public void setObject_detection_time(long object_detection_time) {
		this.object_detection_time = object_detection_time;
	}
	/**
	 * @return the object_tracking_time
	 */
	public long getObject_tracking_time() {
		return object_tracking_time;
	}
	/**
	 * @param object_tracking_time the object_tracking_time to set
	 */
	public void setObject_tracking_time(long object_tracking_time) {
		this.object_tracking_time = object_tracking_time;
	}
	/**
	 * @return the attribute_detection_time
	 */
	public long getAttribute_detection_time() {
		return attribute_detection_time;
	}
	/**
	 * @param attribute_detection_time the attribute_detection_time to set
	 */
	public void setAttribute_detection_time(long attribute_detection_time) {
		this.attribute_detection_time = attribute_detection_time;
	}
	/**
	 * @return the graph_construction_time
	 */
	public long getGraph_construction_time() {
		return graph_construction_time;
	}
	/**
	 * @param graph_construction_time the graph_construction_time to set
	 */
	public void setGraph_construction_time(long graph_construction_time) {
		this.graph_construction_time = graph_construction_time;
	}

	public long event_windowadd_time;
	public long event_matcheradd_time;
	public long event_notification_time;
	public SimpleGraph<VertexObject, DefaultEdge> pubGraph;
	public String test_str;
	/**
	 * @param publisherID
	 * @param eventID
	 * @param event_gen_timeStamp
	 * @param event_preprocess_timestamp
	 * @param event_windowadd_time
	 * @param event_matcheradd_time
	 * @param event_notification_time
	 * @param pubGraph
	 * @param test_str
	 */
	public PublicationGraphEvent(String publisherID, int eventID, long event_gen_timeStamp,
			long event_preprocess_timestamp, long event_windowadd_time, long event_matcheradd_time,
			long event_notification_time, SimpleGraph<VertexObject, DefaultEdge> pubGraph, String test_str) {
		super();
		this.publisherID = publisherID;
		this.eventID = eventID;
		this.event_gen_timeStamp = event_gen_timeStamp;
		this.event_preprocess_timestamp = event_preprocess_timestamp;
		this.event_windowadd_time = event_windowadd_time;
		this.event_matcheradd_time = event_matcheradd_time;
		this.event_notification_time = event_notification_time;
		this.pubGraph = pubGraph;
		this.test_str = test_str;
	}
	/**
	 * @return the publisherID
	 */
	public String getPublisherID() {
		return publisherID;
	}
	/**
	 * @param publisherID the publisherID to set
	 */
	public void setPublisherID(String publisherID) {
		this.publisherID = publisherID;
	}
	/**
	 * @return the eventID
	 */
	public int getEventID() {
		return eventID;
	}
	/**
	 * @param eventID the eventID to set
	 */
	public void setEventID(int eventID) {
		this.eventID = eventID;
	}
	/**
	 * @return the event_gen_timeStamp
	 */
	public long getEvent_gen_timeStamp() {
		return event_gen_timeStamp;
	}
	/**
	 * @param event_gen_timeStamp the event_gen_timeStamp to set
	 */
	public void setEvent_gen_timeStamp(long event_gen_timeStamp) {
		this.event_gen_timeStamp = event_gen_timeStamp;
	}
	/**
	 * @return the event_preprocess_timestamp
	 */
	public long getEvent_preprocess_timestamp() {
		return event_preprocess_timestamp;
	}
	/**
	 * @param event_preprocess_timestamp the event_preprocess_timestamp to set
	 */
	public void setEvent_preprocess_timestamp(long event_preprocess_timestamp) {
		this.event_preprocess_timestamp = event_preprocess_timestamp;
	}
	/**
	 * @return the event_windowadd_time
	 */
	public long getEvent_windowadd_time() {
		return event_windowadd_time;
	}
	/**
	 * @param event_windowadd_time the event_windowadd_time to set
	 */
	public void setEvent_windowadd_time(long event_windowadd_time) {
		this.event_windowadd_time = event_windowadd_time;
	}
	/**
	 * @return the event_matcheradd_time
	 */
	public long getEvent_matcheradd_time() {
		return event_matcheradd_time;
	}
	/**
	 * @param event_matcheradd_time the event_matcheradd_time to set
	 */
	public void setEvent_matcheradd_time(long event_matcheradd_time) {
		this.event_matcheradd_time = event_matcheradd_time;
	}
	/**
	 * @return the event_notification_time
	 */
	public long getEvent_notification_time() {
		return event_notification_time;
	}
	/**
	 * @param event_notification_time the event_notification_time to set
	 */
	public void setEvent_notification_time(long event_notification_time) {
		this.event_notification_time = event_notification_time;
	}
	/**
	 * @return the pubGraph
	 */
	public SimpleGraph<VertexObject, DefaultEdge> getPubGraph() {
		return pubGraph;
	}
	/**
	 * @param pubGraph the pubGraph to set
	 */
	public void setPubGraph(SimpleGraph<VertexObject, DefaultEdge> pubGraph) {
		this.pubGraph = pubGraph;
	}
	/**
	 * @return the test_str
	 */
	public String getTest_str() {
		return test_str;
	}
	/**
	 * 
	 */
	public PublicationGraphEvent() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param test_str the test_str to set
	 */
	public void setTest_str(String test_str) {
		this.test_str = test_str;
	}

  // clone method
	public PublicationGraphEvent clone()throws CloneNotSupportedException{  
		PublicationGraphEvent clonedMyClass = (PublicationGraphEvent)super.clone();
		return clonedMyClass;  
		} 

}

