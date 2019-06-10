/**
 * 
 */
package org.insight.nuig.coredomainmodel;

import java.util.ArrayList;

import org.insight.nuig.coredomainmodel.messageobject.Obj2ObjRelation;
import org.insight.nuig.publisher.messageobject.VertexObject;

/**
 * @author piyush
 * @date 6 Nov 2018 14:08:34
 * @Institute: Insight Centre for Data Analytics
 * @Project: MMCEP: Multimedia Complex Event Processing
 * @CLassDefinition 
 */
public class SemanticKnowledgeLayer {

	// object collection layer
	// object to object relation
	
	public static ArrayList<VertexObject> getobjectsforrelation(ArrayList<VertexObject> detectedobjects,
			ArrayList<String> interestedlabels) {

		ArrayList<VertexObject> interestedobject = new ArrayList<>();
		for (VertexObject object : detectedobjects) {
			if (interestedlabels.contains(object.getVertex_label())) {
				interestedobject.add(object);
				// create
			}
		}
		return detectedobjects;

	}
	
		
	public static Obj2ObjRelation create_object_object_relation(VertexObject obj1, VertexObject Obj2, ArrayList<String> relationname) {
		
		Obj2ObjRelation obj_rel = new Obj2ObjRelation();
		obj_rel.setO2OLabel(obj1.getVertex_label()+"-"+Obj2.getVertex_label());
		obj_rel.setRelation(relationname);
		
		return obj_rel;
		
		
	}
	
	
	
	
	
}

