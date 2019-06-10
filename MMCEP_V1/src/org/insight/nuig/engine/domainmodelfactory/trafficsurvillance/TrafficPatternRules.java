/**
 * 
 */
package org.insight.nuig.engine.domainmodelfactory.trafficsurvillance;

import org.insight.nuig.coredomainmodel.PatternDefinitionLayer;
import org.insight.nuig.coredomainmodel.eventcalculus.logical.LogicalOperators;
import org.insight.nuig.coredomainmodel.eventcalculus.spatial.BooleanSpatialDirection;

/**
 * @author piyush
 * @date 6 Nov 2018 18:12:19
 * @Institute: Insight Centre for Data Analytics
 * @Project: MMCEP: Multimedia Complex Event Processing
 * @CLassDefinition
 */
public class TrafficPatternRules extends PatternDefinitionLayer{
	// the overtake function is defined as per below convention
	// 𝒃𝒂𝒄𝒌((𝒐_𝟏,𝒐_𝟐 )^(𝒕_𝒊 ))→〖 𝒇𝒓𝒐𝒏𝒕((𝒐_𝟏,𝒐_𝟐 )〗^(𝒕_(𝒊+𝟏) )
	// )
	// ∃(𝑡_𝑖 )∈𝑇 𝑖𝑓 [𝒃𝒔𝒇(𝑺𝑫(𝒐_𝟏,𝒐_𝟐 )𝒙^(𝒕_𝒊 ) 〖) ʘ
	// 𝒃𝒔𝒇(𝑺𝑫(𝒐_𝟏,𝒐_𝟐 )𝒙〗^(𝒕_(𝒊+𝟏) ) )]^(⊞[𝒕_𝒎,𝒕_𝒏 ] ) 𝑤ℎ𝑒𝑟𝑒
	// 𝑥∈𝑏𝑓 𝑑𝑖𝑟𝑒𝑐𝑡𝑖𝑜𝑛 𝑎𝑛𝑑 𝑡𝑚≤𝑡𝑖𝑎𝑛𝑑 𝑡𝑖+1≤𝑡𝑛
	// 𝑂 ⅈ𝑓 𝑜𝑣𝑒𝑟𝑡𝑎𝑘𝑒
	// 1 ⅈ𝑓 𝑛𝑜 𝑜𝑣𝑒𝑟𝑡𝑎𝑘𝑒
	// we are considering ovetake in reference of NS direction where motion of
	// object is from north to south
	
	

	public static int checkovertake(int object1_bb_t1, int object2_bb_t1, int object1_bb_t2, int object2_bb_t2) {

		boolean relative_pos = LogicalOperators.xnor(BooleanSpatialDirection.back(object1_bb_t1, object2_bb_t1),
				BooleanSpatialDirection.back(object1_bb_t1, object2_bb_t1));

		if (relative_pos) {
			return 1; // no overtake
		}

		else {
			return 0; // overtake
		}

	}
	
	public static int checkfollowsby(int object1_bb_t1, int object2_bb_t1, int object1_bb_t2, int object2_bb_t2) {

		boolean relative_pos = LogicalOperators.xnor(BooleanSpatialDirection.back(object1_bb_t1, object2_bb_t1),
				BooleanSpatialDirection.back(object1_bb_t1, object2_bb_t1));

		if (relative_pos) {
			return 1; // follows by
		}

		else {
			return 0; // no followsby
		}

	}
	
	
	

}
