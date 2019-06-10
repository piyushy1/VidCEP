/**
 * 
 */
package org.insight.nuig.engine.domainmodelfactory.trafficsurvillance;

import java.util.ArrayList;

import org.insight.nuig.configurator.ConfigurationParameters;
import org.insight.nuig.coredomainmodel.EventDetectionLayer;
import org.insight.nuig.coredomainmodel.SemanticKnowledgeLayer;
import org.insight.nuig.coredomainmodel.messageobject.Obj2ObjRelation;
import org.insight.nuig.publisher.messageobject.VertexObject;

/**
 * @author piyush
 * @date 8 Nov 2018 19:06:07
 * @Institute: Insight Centre for Data Analytics
 * @Project: MMCEP: Multimedia Complex Event Processing
 * @CLassDefinition
 */

// create domain using the define existing layers
public class TrafficSurvillanceDomain {

	/*
	 * 1. create the event detection layer
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	
	// Event Detection Layer Configuration
	// get list of object which system can detect....
	
	public void createdomainontology() {
		
		/***************************************************
		 * Event Detection Layer COnfiguration
		 ***************************************************/
		ArrayList<VertexObject> systemdetectedobjectlist = EventDetectionLayer.getdetectedobjectlist(
				ConfigurationParameters.MODEL_ANNOTATION_FOLDER_PATH + "\\" + "TinyYoloTrainingAnnotation.txt");
		
		/***************************************************
		 * Semantic Detection Layer COnfiguration
		 ***************************************************/
		
		// interested objects list to create relation
		ArrayList<String> interestedobjects = new ArrayList<>();
		interestedobjects.add("car");
		interestedobjects.add("person");
		
		ArrayList<VertexObject> relationobjects = SemanticKnowledgeLayer.getobjectsforrelation(systemdetectedobjectlist, interestedobjects);
		
		// create relation
		// presently handling single relation else write arraylist of <Obj2ObjRelation> 
		ArrayList<String> relationname = new ArrayList<>();
		relationname.add("overtake");
		relationname.add("followsby");
		
		Obj2ObjRelation o2orelatin = SemanticKnowledgeLayer.create_object_object_relation(relationobjects.get(0),relationobjects.get(0),relationname);
		
		// if query ask about overtake
		// if ask about followsby call the function
		
		if(o2orelatin.getRelation().contains("query//overtake")) {
			//pattern rule function for overtake
		}
		else {
			//pattern rule function for 
		}
	


	}


}
