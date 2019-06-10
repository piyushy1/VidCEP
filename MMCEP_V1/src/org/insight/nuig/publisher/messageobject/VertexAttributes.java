/**
 * 
 */
package org.insight.nuig.publisher.messageobject;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.nd4j.linalg.api.ndarray.INDArray;

/**
 * @author piyush
 * @date 4 Nov 2018 20:24:41
 * @Institute: Insight Centre for Data Analytics
 * @Project: MMCEP: Multimedia Complex Event Processing
 * @CLassDefinition This class defines the attributes for any vertex. Presently
 * the attributes of any vertex node is bounding box, features, color, and other payload 
 */
public class VertexAttributes {
	
	//private ArrayList<Integer> bounding_box;   //value should be stored like <x1,y1,x2,y2,centre> where <x1,y1>= top left and <x2,y2> right bottom
	private Rectangle bounding_box;
	private INDArray features;
	private String color;
	private long pre_color_computetime;
	private long post_color_computetime;
	private Serializable payload;

		
	/**
	 * @return the pre_color_computetime
	 */
	public long getPre_color_computetime() {
		return pre_color_computetime;
	}

	/**
	 * @param pre_color_computetime the pre_color_computetime to set
	 */
	public void setPre_color_computetime(long pre_color_computetime) {
		this.pre_color_computetime = pre_color_computetime;
	}

	/**
	 * @return the post_color_computetime
	 */
	public long getPost_color_computetime() {
		return post_color_computetime;
	}

	/**
	 * @param post_color_computetime the post_color_computetime to set
	 */
	public void setPost_color_computetime(long post_color_computetime) {
		this.post_color_computetime = post_color_computetime;
	}

	/**
	 * @param bounding_box
	 * @param features
	 * @param color
	 * @param pre_color_computetime
	 * @param post_color_computetime
	 * @param payload
	 */
	public VertexAttributes(Rectangle bounding_box, INDArray features, String color, long pre_color_computetime,
			long post_color_computetime, Serializable payload) {
		super();
		this.bounding_box = bounding_box;
		this.features = features;
		this.color = color;
		this.pre_color_computetime = pre_color_computetime;
		this.post_color_computetime = post_color_computetime;
		this.payload = payload;
	}

	/**
	 * 
	 */
	public VertexAttributes() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the bounding_box
	 */
	public Rectangle getBounding_box() {
		return bounding_box;
	}
	/**
	 * @param bounding_box the bounding_box to set
	 */
	public void setBounding_box(Rectangle bounding_box) {
		this.bounding_box = bounding_box;
	}
	/**
	 * @return the features
	 */
	public INDArray getFeatures() {
		return features;
	}
	/**
	 * @param features the features to set
	 */
	public void setFeatures(INDArray features) {
		this.features = features;
	}
	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
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
	
	

}

