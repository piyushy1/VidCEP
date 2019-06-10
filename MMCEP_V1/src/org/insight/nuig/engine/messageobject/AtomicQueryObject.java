/**
 * 
 */
package org.insight.nuig.engine.messageobject;

import java.util.ArrayList;

import org.insight.nuig.coredomainmodel.messageobject.Obj2ObjRelation;
import org.insight.nuig.publisher.messageobject.VertexObject;

/**
 * @author piyush
 * @date 9 Nov 2018 14:18:26
 * @Institute: Insight Centre for Data Analytics
 * @Project: MMCEP: Multimedia Complex Event Processing
 * @CLassDefinition
 */
public class AtomicQueryObject {

	private ArrayList<VertexObject> object;
	private Obj2ObjRelation OOrelation;
	private int publisher;
	private GenericWindowObject window;

	/**
	 * 
	 */
	public AtomicQueryObject() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param object
	 * @param oOrelation
	 * @param publisher
	 * @param window
	 */

	public AtomicQueryObject(ArrayList<VertexObject> object, Obj2ObjRelation oOrelation, int publisher, GenericWindowObject window) {
		super();
		this.object = object;
		OOrelation = oOrelation;
		this.publisher = publisher;
		this.window = window;
	}

	/**
	 * @return the object
	 */
	public ArrayList<VertexObject> getObject() {
		return object;
	}

	/**
	 * @param object
	 *            the object to set
	 */
	public void setObject(ArrayList<VertexObject> object) {
		this.object = object;
	}

	/**
	 * @return the oOrelation
	 */
	public Obj2ObjRelation getOOrelation() {
		return OOrelation;
	}

	/**
	 * @param oOrelation
	 *            the oOrelation to set
	 */
	public void setOOrelation(Obj2ObjRelation oOrelation) {
		OOrelation = oOrelation;
	}

	/**
	 * @return the publisher
	 */
	public int getPublisher() {
		return publisher;
	}

	/**
	 * @param publisher
	 *            the publisher to set
	 */
	public void setPublisher(int publisher) {
		this.publisher = publisher;
	}

	/**
	 * @return the window
	 */
	public GenericWindowObject getWindow() {
		return window;
	}

	/**
	 * @param window
	 *            the window to set
	 */
	public void setWindow(GenericWindowObject window) {
		this.window = window;
	}

}
