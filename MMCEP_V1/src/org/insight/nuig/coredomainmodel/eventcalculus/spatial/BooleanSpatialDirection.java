/**
 * 
 */
package org.insight.nuig.coredomainmodel.eventcalculus.spatial;

/**
 * @author piyush
 * @date 6 Nov 2018 16:05:47
 * @Institute: Insight Centre for Data Analytics
 * @Project: MMCEP: Multimedia Complex Event Processing
 * @CLassDefinition 
 */
public class BooleanSpatialDirection {
	
	/*	Here the convention are set are per following rule
		The origin of image is from top left corner. Thus the reference point (0,0) is
		set on the top left corner of the image.
		1. So for north(back) and south(front)
		Object in north (x1) < object in south (x2)
		2. For east(right) west(left) Object West (y1) < Object east (y2)
		3. Presently we are treating only centre point of bounding box
		4. This is boolean spatial function and just return value in term of 0 and 1 
					    ^back
					    |
			   left-----|------right
					    |
		                front 
		*/
	
	public static boolean back(int obj1_centre_x, int obj2_centrex){
		
		if(obj1_centre_x > obj2_centrex ) {
			return true;
		}
		else
		{
			return false;
		}
		// not checked for similar conditions
	}
	
	public static boolean front(int obj1_centre_x, int obj2_centrex){
		
		if(obj1_centre_x < obj2_centrex ) {
			return true;
		}
		else
		{
			return false;
		}		
	}
	
	public static boolean left(int obj1_centre_y, int obj2_centrey){
		
		if(obj1_centre_y > obj2_centrey ) {
			return true;
		}
		else
		{
			return false;
		}		
	}
	public static boolean right(int obj1_centre_y, int obj2_centrey){
		
		if(obj1_centre_y < obj2_centrey ) {
			return true;
		}
		else
		{
			return false;
		}		
	}

}

