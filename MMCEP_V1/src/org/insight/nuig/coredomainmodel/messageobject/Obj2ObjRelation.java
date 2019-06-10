/**
 * 
 */
package org.insight.nuig.coredomainmodel.messageobject;

import java.util.ArrayList;

import org.insight.nuig.publisher.messageobject.VertexObject;

/**
 * @author piyush
 * @date 8 Nov 2018 14:58:40
 * @Institute: Insight Centre for Data Analytics
 * @Project: MMCEP: Multimedia Complex Event Processing
 * @CLassDefinition 
 */
public class Obj2ObjRelation {
	
	private VertexObject obj1;
	private VertexObject obj2;
	private String O2OLabel;
	private ArrayList<String> relation;
	/**
	 * @param obj1
	 * @param obj2
	 * @param relation
	 */
	public Obj2ObjRelation(VertexObject obj1, VertexObject obj2,String O2OLabel, ArrayList<String> relation) {
		super();
		this.obj1 = obj1;
		this.obj2 = obj2;
		this.O2OLabel =O2OLabel;
		this.relation = relation;
	}
	/**
	 * 
	 */
	public Obj2ObjRelation() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the obj1
	 */
	public VertexObject getObj1() {
		return obj1;
	}
	/**
	 * @param obj1 the obj1 to set
	 */
	public void setObj1(VertexObject obj1) {
		this.obj1 = obj1;
	}
	/**
	 * @return the obj2
	 */
	public VertexObject getObj2() {
		return obj2;
	}
	/**
	 * @param obj2 the obj2 to set
	 */
	public void setObj2(VertexObject obj2) {
		this.obj2 = obj2;
	}
	/**
	 * @return the relation
	 */
	public ArrayList<String> getRelation() {
		return relation;
	}
	/**
	 * @param relation the relation to set
	 */
	public void setRelation(ArrayList<String> relation) {
		this.relation = relation;
	}
	/**
	 * @return the o2OLabel
	 */
	public String getO2OLabel() {
		return O2OLabel;
	}
	/**
	 * @param o2oLabel the o2OLabel to set
	 */
	public void setO2OLabel(String o2oLabel) {
		O2OLabel = o2oLabel;
	}
	
	
	
	
	

}

