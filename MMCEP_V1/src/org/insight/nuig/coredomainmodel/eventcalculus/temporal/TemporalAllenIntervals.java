/**
 * 
 */
package org.insight.nuig.coredomainmodel.eventcalculus.temporal;

/**
 * @author piyush
 * @date 6 Nov 2018 17:39:19
 * @Institute: Insight Centre for Data Analytics
 * @Project: MMCEP: Multimedia Complex Event Processing
 * @CLassDefinition 
 */

public class TemporalAllenIntervals {

	// so if you treat only time points then put the values which u require and made other values 0
	// presently code logic looks little confusing... update it later on
	public boolean before(long event_t1_start, long event_t1_end, long event_t2_start, long event_t2_end) {
		
		if(event_t1_end < event_t2_start) {
			return true;
		
		}
		else {
			return false;
		}		
	}
	
	public boolean after(long event_t1_start, long event_t1_end, long event_t2_start, long event_t2_end) {
		if(event_t1_start > event_t2_end) {
			return true;
		
		}
		else {
			return false;
		}	
		
	}
	

}

