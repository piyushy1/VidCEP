/**
 * 
 */
package org.insight.nuig.engine.matcher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

import org.insight.nuig.coredomainmodel.eventcalculus.spatial.BooleanSpatialDirection;
import org.insight.nuig.engine.messageobject.AtomicQueryObject;
import org.insight.nuig.engine.messageobject.EventObject;
import org.insight.nuig.publisher.messageobject.PublicationGraphEvent;
import org.insight.nuig.publisher.messageobject.VertexObject;

/**
 * @author piyush
 * @date 21 Feb 2019 16:27:15
 * @Institute: Insight Centre for Data Analytics
 * @Project: MMCEP: Multimedia Complex Event Processing
 * @CLassDefinition
 */
public class MatchingOperations {
	private static final String FILENAME = "/Dataset/results/preprocessing1.csv";
	private static final String STATE_FILENAME = "\\Dataset\\results\\state_preprocessing.csv";
	private static final String Matcher_FILENAME = "/Dataset/results/matcherconfidence.csv";
	
	public static void performTemporalMatching(List<PublicationGraphEvent> recievedstate, AtomicQueryObject query, long statetime) {

		// take the query predicates from atomic query
		ArrayList<VertexObject> query_predicate_rel = new ArrayList<>();
		// presently considering only two objects..
		query_predicate_rel.add(query.getOOrelation().getObj1());
		query_predicate_rel.add(query.getOOrelation().getObj2());
		// create a temporal map.....
		HashMap<VertexObject, ArrayList<EventObject>> temporal_map = new HashMap<>();
		ArrayList<EventObject> event_list_object1 = new ArrayList<>();
		ArrayList<EventObject> event_list_object2 = new ArrayList<>();
		ArrayList<Long> avg_event_latency = new ArrayList<>();
		for (PublicationGraphEvent graph : recievedstate) {

			for (VertexObject graphObj : graph.getPubGraph().vertexSet()) {
				// need to match label
				// need to match attributes..
				if (matchVartexObject(query.getOOrelation().getObj1(), graphObj) == true) {
					EventObject event = new EventObject();
					event.setObject(graphObj);
					event.setTime((int) System.currentTimeMillis());
					event_list_object1.add(event);
					temporal_map.put(query.getOOrelation().getObj1(), event_list_object1);
				}
				if (matchVartexObject(query.getOOrelation().getObj2(), graphObj) == true) {
					EventObject event = new EventObject();
					event.setObject(graphObj);
					event.setTime((int) System.currentTimeMillis());
					event_list_object2.add(event);
					temporal_map.put(query.getOOrelation().getObj2(), event_list_object2);

				}

			}
			if (temporal_map.size() >= 1) {
				// call temporal matcher function....
				temporalmatcher(temporal_map, query);

			}
			// log each event latency
			avg_event_latency.add(ZonedDateTime.now().toInstant().toEpochMilli()-graph.getEvent_gen_timeStamp());

		}
		
		System.out.println("Matching End time:"+ ZonedDateTime.now().toInstant().toEpochMilli());
		double avglatency = logavgeventlatency(avg_event_latency);
		logevent_matcherlatency(statetime, avglatency);

	}

	// function to match vertex object with its attributes
	public static boolean matchVartexObject(VertexObject Vqueryobj, VertexObject Vgraphobj) {

		// matching vertex label and attributes
		if (Vqueryobj.getVertex_label() != null && Vqueryobj.getVertex_attributes() != null) {
			if (Vgraphobj.getVertex_label().contains(Vqueryobj.getVertex_label()) && Vgraphobj.getVertex_attributes()
					.getColor().contains(Vqueryobj.getVertex_attributes().getColor())) {

				/*System.out.println(
						Vqueryobj.getVertex_attributes().getColor() + " " + Vqueryobj.getVertex_label() + " FOUND");*/

				return true;

			}

		} else {
			// matching only label
			if (Vgraphobj.getVertex_label().contains(Vqueryobj.getVertex_label())) {
				//System.out.println(Vqueryobj.getVertex_label() + " FOUND");
				return true;
			}

		}
		return false;

	}

	// check for temporal reasoning....

	public static void temporalmatcher(HashMap<VertexObject, ArrayList<EventObject>> temporal_map,
			AtomicQueryObject query) {
		
		ArrayList<Double> conf_frame_obj = new ArrayList<>();
		

		if (query.getOOrelation().getRelation().get(0).matches("SEQ")) {
			if (temporal_map.size() > 1) {
				ArrayList<EventObject> refObj = new ArrayList<>();
				ArrayList<EventObject> Obj2 = new ArrayList<>();
				refObj = temporal_map.get(query.getOOrelation().getObj1());
				Obj2 = temporal_map.get(query.getOOrelation().getObj2());

				for (EventObject refevent : refObj) {
					for (EventObject event2 : Obj2) {
						if (refevent.getTime() < event2.getTime()) {
							System.out.println("SEQUENCE FOUND");
							// calculate matcher confidence
							conf_frame_obj.add(refevent.getObject().getConfidence());
							conf_frame_obj.add(event2.getObject().getConfidence());
							//getMatcherConfidenceEntropy(conf_frame_obj);
						}
					}
				}

			}

		}

		if (query.getOOrelation().getRelation().get(0).matches("CONJ")) {
			if (temporal_map.size() > 1) {
				
				// method to calculate matcher confidence
                 //getCONJconfidence(temporal_map,conf_frame_obj);
				
				System.out.println("CONJUNCTION FOUND");
			}

		}
		if (query.getOOrelation().getRelation().get(0).matches("DISJ")) {
			if (temporal_map.size() >= 1) {
				System.out.println("DISJUNCTION FOUND");
			}

		}

		if (query.getOOrelation().getRelation().get(0).matches("EQ")) {
			if (temporal_map.size() > 1) {
				ArrayList<EventObject> refObj = new ArrayList<>();
				ArrayList<EventObject> Obj2 = new ArrayList<>();
				refObj = temporal_map.get(query.getOOrelation().getObj1());
				Obj2 = temporal_map.get(query.getOOrelation().getObj2());

				for (EventObject refevent : refObj) {
					for (EventObject event2 : Obj2) {
						if (refevent.getTime().equals(event2.getTime())) {
							System.out.println("EQ FOUND");
						}
					}
				}

			}

		}

	}

	/**
	 * @param conf_frame_obj 
	 * @param temporal_map 
	 * 
	 */
	private static void getCONJconfidence(HashMap<VertexObject, ArrayList<EventObject>> temporal_map, ArrayList<Double> conf_frame_obj) {
		// TODO Auto-generated method stub
		// iterate hasmap..
		Iterator it = temporal_map.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        for(EventObject obj: (ArrayList<EventObject>)pair.getValue()) {
	        	conf_frame_obj.add(obj.getObject().getConfidence());
	        }
	        //System.out.println(pair.getKey() + " = " + pair.getValue());
	        it.remove(); // avoids a ConcurrentModificationException
	    }
		//getMatcherConfidenceavg(conf_frame_obj);
	    getMatcherConfidenceEntropy(conf_frame_obj);
	}

	// function for only object(with attributes..) matching........
	public static void performObjectMatching(List<PublicationGraphEvent> recievedstate, AtomicQueryObject query, long statetime) {

		// take the query predicates from atomic query
		ArrayList<VertexObject> query_predicate = query.getObject();
        ArrayList<Double> conf_frame_obj = new ArrayList<>();
        ArrayList<Long> avg_event_latency = new ArrayList<>();
        int temp_id = 0;
		for (PublicationGraphEvent graph : recievedstate) {
			//StringBuilder csv = new StringBuilder();
			temp_id = graph.getEventID();
/*			csv.append(graph.getEventID());
			csv.append(",");
			csv.append(graph.getEvent_gen_timeStamp());
			csv.append(",");
			csv.append(graph.getEvent_preprocess_timestamp());*/
			for (VertexObject graphObj : graph.getPubGraph().vertexSet()) {
				// need to match label
				// need to match attributes..
				for (VertexObject querObj : query_predicate) {
					if (matchVartexObject(querObj, graphObj) == true) {
						conf_frame_obj.add(graphObj.getConfidence());
						//System.out.println("EVENT FOUND");
					}
				}

			}
			
			// log each event latency
			avg_event_latency.add(ZonedDateTime.now().toInstant().toEpochMilli()-graph.getEvent_gen_timeStamp());

		}
	
		//getMatcherConfidenceEntropy(conf_frame_obj);
		
		//below code snippet is to log the latency of the Matcher and the event
		
		double avglatency = logavgeventlatency(avg_event_latency);
		logevent_matcherlatency(statetime, avglatency);
		

		

	}


	/**
	 * @param state
	 * @param query
	 *            function to perform spatial matching...
	 * @param statetime 
	 */
	public static void performSpatialDirectionMatching(List<PublicationGraphEvent> recievedstate,
			AtomicQueryObject query, long statetime) {
		// take the query predicates from atomic query
		ArrayList<VertexObject> query_predicate_rel = new ArrayList<>();
		ArrayList<Long> avg_event_latency = new ArrayList<>();
		// presently considering only two objects..
		query_predicate_rel.add(query.getOOrelation().getObj1());
		query_predicate_rel.add(query.getOOrelation().getObj2());
		for (PublicationGraphEvent graph : recievedstate) {
			// for latency
/*			StringBuilder csv = new StringBuilder();
			csv.append(graph.getEventID());
			csv.append(",");
			csv.append(graph.getEvent_gen_timeStamp());
			csv.append(",");
			csv.append(graph.getEvent_preprocess_timestamp());*/
			//end of latency//
			ArrayList<VertexObject> number_objects = new ArrayList<>();
			for (VertexObject graphObj : graph.getPubGraph().vertexSet()) {
				// there can be multiple possibility....from which car to understand that
				// another car is left..
				// if two different objects are there then its fine
				// here in future we will bring concept of event adajaceny graph....and event
				// adajaceny matrix...
				// presently we have assumption that the first object we detect will be
				// considered as reference object...
				// and we will consider all other object direction from this reference object..
				// need to match label and attributes..
				for (VertexObject querObj : query_predicate_rel) {
					if (matchVartexObject(querObj, graphObj) == true) {
						if (!number_objects.contains(graphObj)) {
							number_objects.add(graphObj);
						}

					}
				}

			}
			// to perform spatial direction relation atleast two objects need to be present
			if (number_objects.size() >= 2) {
				// if both objects are different...
				if (matchVartexObject(query.getOOrelation().getObj1(), query.getOOrelation().getObj2()) == false) {
					for (int i = 1; i < number_objects.size(); i++) {
						// swap the reference object to first location
						int indx = lookupObjectLabelindex(query.getOOrelation().getObj1(), number_objects);
						if (indx != 0 || indx < 0) {
							Collections.swap(number_objects, 0, indx);
						}

						call_SpatialOperation(query.getOOrelation().getRelation().get(0), number_objects.get(0),
								number_objects.get(i));

					}
				} else {
					// if both objects are same..
					for (int i = 1; i < number_objects.size(); i++) {
						call_SpatialOperation(query.getOOrelation().getRelation().get(0), number_objects.get(0),
								number_objects.get(i));
					}

				}

			}
			// log each event latency
			avg_event_latency.add(ZonedDateTime.now().toInstant().toEpochMilli()-graph.getEvent_gen_timeStamp());
	

		}
		
		//below code snippet is to log the latency of the Matcher
		double avglatency = logavgeventlatency(avg_event_latency);
		logevent_matcherlatency(statetime, avglatency);
		

	}
	// perform count to determine traffic flow....
	public static void performCountMatching(List<PublicationGraphEvent> recievedstate,
			AtomicQueryObject query, long statetime) {
		
		// take the query predicates from atomic query
		ArrayList<VertexObject> query_predicate_rel = new ArrayList<>();
		// presently considering only two objects..
		query_predicate_rel.add(query.getOOrelation().getObj1());
        ArrayList<Integer> count = new ArrayList<>();
        ArrayList<Double> conf_frame_obj = new ArrayList<>();
        ArrayList<Long> avg_event_latency = new ArrayList<>();
        
		for (PublicationGraphEvent graph : recievedstate) {
			
			ArrayList<String> count_frame_obj = new ArrayList<>();
			
			for (VertexObject graphObj : graph.getPubGraph().vertexSet()) {
				// need to match label
				// need to match attributes..
				for (VertexObject querObj : query_predicate_rel) {
					if (matchVartexObject(querObj, graphObj) == true) {
						count_frame_obj.add(querObj.getVertex_label());
						conf_frame_obj.add(graphObj.getConfidence());
						//System.out.println("EVENT FOUND");
					}
				}

			}
			count.add(count_frame_obj.size());
			// log each event latency
			avg_event_latency.add(ZonedDateTime.now().toInstant().toEpochMilli()-graph.getEvent_gen_timeStamp());
						 

		}
		
		if(checkcount(count)== true) {
			
			System.out.println("High Traffic Flow");
			// send for confidence score...
			//getMatcherConfidenceavg(conf_frame_obj);
			//getMatcherConfidenceEntropy(conf_frame_obj);
			
		}

		//below code snippet is to log the latency of the Matcher
		double avglatency = logavgeventlatency(avg_event_latency);
		logevent_matcherlatency(statetime, avglatency);
	}

	// lookup index of object label function
	public static int lookupObjectLabelindex(VertexObject query_label, ArrayList<VertexObject> listofobjects) {
		for (VertexObject object : listofobjects) {
			// Access properties of person, usage of getter methods would be good
			if (matchVartexObject(query_label, object) == true) {
				// Found matching person
				return listofobjects.indexOf(object);
			}
		}
		// Traversed whole list but did not find a matching person
		return -1;
	}

	// get the centre x corrdinate of bounding box
	public static int getBoundingboxcentre_XCordinate(VertexObject object) {
		int centre_x = object.getVertex_attributes().getBounding_box().x
				+ ((object.getVertex_attributes().getBounding_box().width) / 2);
		return centre_x;
	}

	// get the centre y corrdinate of bounding box
	public static int getBoundingboxcentre_YCordinate(VertexObject object) {
		int centre_y = object.getVertex_attributes().getBounding_box().y
				+ ((object.getVertex_attributes().getBounding_box().height) / 2);
		return centre_y;

	}

	public static void call_SpatialOperation(String relation, VertexObject refObj, VertexObject Obj) {
		ArrayList<Double> conf_frame_obj = new ArrayList<>();
		if (relation.matches("Left")) {
			if (BooleanSpatialDirection.left(getBoundingboxcentre_YCordinate(refObj),
					getBoundingboxcentre_YCordinate(Obj)) == true) {
				// add code for matcher confidence
				conf_frame_obj.add(refObj.getConfidence());
				conf_frame_obj.add(Obj.getConfidence());
				//getMatcherConfidenceEntropy(conf_frame_obj);
				System.out.println("Left Event found");
			}
		}
		if (relation.matches("Right")) {
			if (BooleanSpatialDirection.right(getBoundingboxcentre_YCordinate(refObj),
					getBoundingboxcentre_YCordinate(Obj)) == true) {
				// add code for matcher confidence
				conf_frame_obj.add(refObj.getConfidence());
				conf_frame_obj.add(Obj.getConfidence());
				//getMatcherConfidenceavg(conf_frame_obj);
				System.out.println("Right Event found");
			}

		}
		if (relation.matches("Front")) {
			if (BooleanSpatialDirection.front(getBoundingboxcentre_XCordinate(refObj),
					getBoundingboxcentre_XCordinate(Obj)) == true) {
				System.out.println("Front Event found");
			}

		}
		if (relation.matches("Back")) {
			if (BooleanSpatialDirection.back(getBoundingboxcentre_XCordinate(refObj),
					getBoundingboxcentre_XCordinate(Obj)) == true) {
				System.out.println("Back Event found");
			}

		}

	}
	
	public static boolean checkcount(ArrayList<Integer> count) {
		// check thresold
		for(Integer cnt: count) {
			// set the count thresold.... in future pass the value from query
			if(cnt<=1) {
				return false;
			}

		}
		return true;
	}
	
	public static void writetoFile(StringBuilder csv, String filename) {
		
		 try {
				Files.write(Paths.get(System.getProperty("user.dir")+filename),(csv.toString()+System.lineSeparator()).getBytes(),StandardOpenOption.APPEND);
			} catch (IOException e) {
				e.printStackTrace();
			} 
		
	}
	
	public static void getMatcherConfidence(ArrayList<Double> confidence) {
		
		// get the geometric mean....
		double weight = 0.5;
		ArrayList<Double> p = new ArrayList<>();
		ArrayList<Double> oneminusp = new ArrayList<>();
		double minus =1.0;
		for(double conf: confidence) {
			
			p.add(Math.pow(conf, weight));
			oneminusp.add(Math.pow((minus-conf),weight));
		}
		double num=1.0;
		double denom =1.0;
		for(double con:p) {
			num= num*con;
		}
		for(double con_d:oneminusp) {
			denom= denom*con_d;
		}
		
		double matcher_conf = ((num)/(num+denom));
		System.out.println("Matcher_Confidence"+ matcher_conf);
	}
	
	public static void getMatcherConfidenceavg(ArrayList<Double> confidence) {
		double sum = 0.0;
		for(double conf: confidence) {
			sum = sum+conf;
		}
		double avg = sum/confidence.size();
		StringBuilder csv_matcher = new StringBuilder();
		csv_matcher.append(avg);
		writetoFile(csv_matcher, Matcher_FILENAME);
		System.out.println("Matcher_Confidence"+ avg);
		
	}
	
	// get matcher confidence using information content technique
	// this is the weighted average of probability
	
	public static void getMatcherConfidenceEntropy(ArrayList<Double> confidence) {
		double sum = 0.0;
		double base = 2;
		ArrayList<Double> numerator_sum = new ArrayList<>();
		// iterate to get weighted entropy
		for(double conf: confidence) {
			double info_content = logb(conf,base);
			numerator_sum.add(conf*(-info_content));
			sum = sum+(-info_content);
		}
		
		double doublesSum = numerator_sum.stream()
	            .mapToDouble(Double::doubleValue)
	            .sum();
		
		double entropy = doublesSum/sum;
		
		StringBuilder csv_matcher = new StringBuilder();
		csv_matcher.append(entropy);
		writetoFile(csv_matcher, Matcher_FILENAME);
		System.out.println("Matcher_Confidence"+ entropy);
		
	}
	
	public static double logb( double a, double b ){
		return Math.log(a) / Math.log(b);
	}
	
	// below code snippet is to log the latency of the Matcher and event latency
	// format event latency, matcher latency
	public static void logevent_matcherlatency(long statetime, double avgeventlatency) {
		StringBuilder csv_state = new StringBuilder();
		csv_state.append(avgeventlatency);
		csv_state.append(",");
		csv_state.append(ZonedDateTime.now().toInstant().toEpochMilli() - statetime);
		writetoFile(csv_state, Matcher_FILENAME);

	}
	
	/**
	 * @param avg_event_latency
	 * @return 
	 */
	private static double logavgeventlatency(ArrayList<Long> avg_event_latency) {
		OptionalDouble avg = avg_event_latency.stream().mapToLong(a->a).average();
		return avg.getAsDouble();
		
	}

}
