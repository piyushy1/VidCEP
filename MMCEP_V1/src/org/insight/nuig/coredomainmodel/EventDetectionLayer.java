/**
 * 
 */
package org.insight.nuig.coredomainmodel;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import org.insight.nuig.coredomainmodel.messageobject.Obj2ObjRelation;
import org.insight.nuig.publisher.messageobject.VertexObject;

/**
 * @author piyush
 * @date 6 Nov 2018 14:08:22
 * @Institute: Insight Centre for Data Analytics
 * @Project: MMCEP: Multimedia Complex Event Processing
 * @CLassDefinition 
 */
public class EventDetectionLayer {
	
	// what objects the system can detect....
	//  get the annotation list
	
	//
	public static ArrayList<VertexObject> getdetectedobjectlist(String filepath) {

		// connect with the model annotation repository
		ArrayList<String> object_list = new ArrayList<>();
		try {
			BufferedReader br = Files.newBufferedReader(Paths.get(filepath));
			object_list = (ArrayList<String>) br.lines().collect(Collectors.toList());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<VertexObject> object_list1 = new ArrayList<>();
		Iterator<String> iterator = object_list.iterator();
		while (iterator.hasNext()) {
			VertexObject obj = new VertexObject();
			obj.setVertex_label(iterator.toString());
			object_list1.add(obj);
		}
		
		return object_list1;
	}
	

	

}

