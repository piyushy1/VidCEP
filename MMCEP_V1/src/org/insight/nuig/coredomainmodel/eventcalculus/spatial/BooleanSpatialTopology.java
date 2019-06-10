/**
 * 
 */
package org.insight.nuig.coredomainmodel.eventcalculus.spatial;

import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * @author piyush
 * @date 9 Nov 2018 14:29:51
 * @Institute: Insight Centre for Data Analytics
 * @Project: MMCEP: Multimedia Complex Event Processing
 * @CLassDefinition 
 */
public class BooleanSpatialTopology {
	
	public static boolean Overlap(Rectangle obj_bb1, Rectangle obj_bb2){

		if (obj_bb1.intersects(obj_bb2) == true) {
			// for parking lot
			Rectangle overlap_bb = obj_bb1.intersection(obj_bb2);
			int area_overlap_bb = (overlap_bb.height*overlap_bb.width);
		    int area_obj_bb1 = (obj_bb1.width*obj_bb1.height);
		    if((area_obj_bb1/area_overlap_bb)<=5) {
				//System.out.println("INtersects................."+ area_obj_bb1/area_overlap_bb );
				return true;
		    }
		    else {
		    	//System.out.println(" Not INtersects................."+ area_obj_bb1/area_overlap_bb );
		    	return false;
		    }

		}
		
		else {
			//System.out.println("NOTTTTTTTT......INtersects.................");
			return false;
		}

	}

}

