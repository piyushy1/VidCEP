/**
 * 
 */
package org.insight.nuig.publisher.messageobject;

/**
 * @author piyush
 * @date 4 Nov 2018 19:55:40
 * @Institute: Insight Centre for Data Analytics
 * @Project: MMCEP: Multimedia Complex Event Processing
 * @CLassDefinition : This is vertex class of graph
 */
public class VertexObject {
	private int vertex_id;
	private String vertex_label;
	private double confidence;
	private VertexAttributes vertex_attributes;

	

	/**
	 * @param vertex_id
	 * @param vertex_label
	 * @param confidence
	 * @param vertex_attributes
	 */
	public VertexObject(int vertex_id, String vertex_label, double confidence, VertexAttributes vertex_attributes) {
		super();
		this.vertex_id = vertex_id;
		this.vertex_label = vertex_label;
		this.confidence = confidence;
		this.vertex_attributes = vertex_attributes;
	}


	/**
	 * 
	 */
	public VertexObject() {
		super();
		// TODO Auto-generated constructor stub
	}


	/**
	 * @return the vertex_label
	 */
	public String getVertex_label() {
		return vertex_label;
	}
	/**
	 * @param vertex_label the vertex_label to set
	 */
	public void setVertex_label(String vertex_label) {
		this.vertex_label = vertex_label;
	}
	/**
	 * @return the vertex_attributes
	 */
	public VertexAttributes getVertex_attributes() {
		return vertex_attributes;
	}
	/**
	 * @param vertex_attributes the vertex_attributes to set
	 */
	public void setVertex_attributes(VertexAttributes vertex_attributes) {
		this.vertex_attributes = vertex_attributes;
	}

	/**
	 * @return the vertex_id
	 */
	public int getVertex_id() {
		return vertex_id;
	}

	/**
	 * @param vertex_id the vertex_id to set
	 */
	public void setVertex_id(int vertex_id) {
		this.vertex_id = vertex_id;
	}
	
	/**
	 * @return the confidence
	 */
	public double getConfidence() {
		return confidence;
	}


	/**
	 * @param confidence the confidence to set
	 */
	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}

	
	

}

