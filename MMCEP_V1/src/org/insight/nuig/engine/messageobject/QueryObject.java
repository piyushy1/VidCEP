/**
 * 
 */
package org.insight.nuig.engine.messageobject;

import java.util.ArrayList;

import org.insight.nuig.coredomainmodel.eventcalculus.spatial.BooleanSpatialTopology;
import org.insight.nuig.coredomainmodel.eventcalculus.temporal.TemporalAllenIntervals;
import org.insight.nuig.coredomainmodel.messageobject.EventCalculusObject;

/**
 * @author piyush
 * @date 9 Nov 2018 14:26:55
 * @Institute: Insight Centre for Data Analytics
 * @Project: MMCEP: Multimedia Complex Event Processing
 * @CLassDefinition 
 */
public class QueryObject {
	
	private ArrayList<AtomicQueryObject> atomicquery;
	
	private ArrayList<EventCalculusObject> queryrelation;
	
	private int notification_time;

	/**
	 * 
	 */
	public QueryObject() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param atomicquery
	 * @param queryrelation
	 */
	public QueryObject(ArrayList<AtomicQueryObject> atomicquery, ArrayList<EventCalculusObject> queryrelation, int notification_time) {
		super();
		this.atomicquery = atomicquery;
		this.queryrelation = queryrelation;
		this.notification_time = notification_time;
	}

	/**
	 * @return the notification_time
	 */
	public int getNotification_time() {
		return notification_time;
	}

	/**
	 * @param notification_time the notification_time to set
	 */
	public void setNotification_time(int notification_time) {
		this.notification_time = notification_time;
	}

	/**
	 * @return the atomicquery
	 */
	public ArrayList<AtomicQueryObject> getAtomicquery() {
		return atomicquery;
	}

	/**
	 * @param atomicquery the atomicquery to set
	 */
	public void setAtomicquery(ArrayList<AtomicQueryObject> atomicquery) {
		this.atomicquery = atomicquery;
	}

	/**
	 * @return the queryrelation
	 */
	public ArrayList<EventCalculusObject> getQueryrelation() {
		return queryrelation;
	}

	/**
	 * @param queryrelation the queryrelation to set
	 */
	public void setQueryrelation(ArrayList<EventCalculusObject> queryrelation) {
		this.queryrelation = queryrelation;
	}
	

	
	
}

