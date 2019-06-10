/**
 * 
 */
package org.insight.nuig.coredomainmodel.messageobject;

import org.insight.nuig.coredomainmodel.eventcalculus.logical.LogicalOperators;
import org.insight.nuig.coredomainmodel.eventcalculus.spatial.BooleanSpatialDirection;
import org.insight.nuig.coredomainmodel.eventcalculus.spatial.BooleanSpatialTopology;
import org.insight.nuig.coredomainmodel.eventcalculus.temporal.TemporalAllenIntervals;

/**
 * @author piyush
 * @date 10 Nov 2018 15:45:25
 * @Institute: Insight Centre for Data Analytics
 * @Project: MMCEP: Multimedia Complex Event Processing
 * @CLassDefinition 
 */
public class EventCalculusObject {
	
	private String logicoperations;
	private String spatialtopologyoperations;
	private String spatialdirectionrelations;
	private String temporalrelations;
	/**
	 * 
	 */
	public EventCalculusObject() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param logicoperations
	 * @param spatialtopologyoperations
	 * @param spatialdirectionrelations
	 * @param temporalrelations
	 */
	public EventCalculusObject(String logicoperations, String spatialtopologyoperations,
			String spatialdirectionrelations, String temporalrelations) {
		super();
		this.logicoperations = logicoperations;
		this.spatialtopologyoperations = spatialtopologyoperations;
		this.spatialdirectionrelations = spatialdirectionrelations;
		this.temporalrelations = temporalrelations;
	}
	/**
	 * @return the logicoperations
	 */
	public String getLogicoperations() {
		return logicoperations;
	}
	/**
	 * @param logicoperations the logicoperations to set
	 */
	public void setLogicoperations(String logicoperations) {
		this.logicoperations = logicoperations;
	}
	/**
	 * @return the spatialtopologyoperations
	 */
	public String getSpatialtopologyoperations() {
		return spatialtopologyoperations;
	}
	/**
	 * @param spatialtopologyoperations the spatialtopologyoperations to set
	 */
	public void setSpatialtopologyoperations(String spatialtopologyoperations) {
		this.spatialtopologyoperations = spatialtopologyoperations;
	}
	/**
	 * @return the spatialdirectionrelations
	 */
	public String getSpatialdirectionrelations() {
		return spatialdirectionrelations;
	}
	/**
	 * @param spatialdirectionrelations the spatialdirectionrelations to set
	 */
	public void setSpatialdirectionrelations(String spatialdirectionrelations) {
		this.spatialdirectionrelations = spatialdirectionrelations;
	}
	/**
	 * @return the temporalrelations
	 */
	public String getTemporalrelations() {
		return temporalrelations;
	}
	/**
	 * @param temporalrelations the temporalrelations to set
	 */
	public void setTemporalrelations(String temporalrelations) {
		this.temporalrelations = temporalrelations;
	}


}

