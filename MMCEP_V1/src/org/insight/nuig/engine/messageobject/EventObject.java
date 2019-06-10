/**
 * 
 */
package org.insight.nuig.engine.messageobject;

import java.util.ArrayList;

import org.insight.nuig.coredomainmodel.messageobject.Obj2ObjRelation;
import org.insight.nuig.publisher.messageobject.VertexObject;

/**
 * @author piyush
 * @date 17 Nov 2018 12:10:51
 * @Institute: Insight Centre for Data Analytics
 * @Project: MMCEP: Multimedia Complex Event Processing
 * @CLassDefinition 
 */
public class EventObject {
	
	public VertexObject object;
	public Obj2ObjRelation OOrelation;
	public Integer time;
	/**
	 * 
	 */
	public EventObject() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param object
	 * @param oOrelation
	 * @param time
	 */
	public EventObject(VertexObject object, Obj2ObjRelation oOrelation, Integer time) {
		super();
		this.object = object;
		OOrelation = oOrelation;
		this.time = time;
	}

	/**
	 * @return the time
	 */
	public Integer getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(Integer time) {
		this.time = time;
	}

	/**
	 * @return the object
	 */
	public VertexObject getObject() {
		return object;
	}
	/**
	 * @param object the object to set
	 */
	public void setObject(VertexObject object) {
		this.object = object;
	}
	/**
	 * @return the oOrelation
	 */
	public Obj2ObjRelation getOOrelation() {
		return OOrelation;
	}
	/**
	 * @param oOrelation the oOrelation to set
	 */
	public void setOOrelation(Obj2ObjRelation oOrelation) {
		OOrelation = oOrelation;
	}
	
}

