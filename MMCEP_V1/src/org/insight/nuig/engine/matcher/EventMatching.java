/**
 * 
 */
package org.insight.nuig.engine.matcher;

import java.awt.Event;
import java.awt.Rectangle;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.function.Supplier;

import org.insight.nuig.configurator.ConfigurationParameters;
import org.insight.nuig.coredomainmodel.eventcalculus.logical.LogicalOperators;
import org.insight.nuig.coredomainmodel.eventcalculus.spatial.BooleanSpatialDirection;
import org.insight.nuig.coredomainmodel.eventcalculus.spatial.BooleanSpatialTopology;
import org.insight.nuig.engine.messageobject.AtomicQueryObject;
import org.insight.nuig.engine.messageobject.StateObject;
import org.insight.nuig.engine.querymodel.QueryBuilder;
import org.insight.nuig.publisher.messageobject.PublicationGraphEvent;
import org.insight.nuig.publisher.messageobject.VertexObject;
import org.jgrapht.generate.CompleteGraphGenerator;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.util.SupplierUtil;
import org.nd4j.linalg.api.ops.impl.accum.distances.CosineDistance;
import org.nd4j.linalg.ops.transforms.Transforms;

import jdk.internal.dynalink.linker.LinkerServices.Implementation;

/**
 * @author piyush
 * @date 17 Nov 2018 12:38:44
 * @Institute: Insight Centre for Data Analytics
 * @Project: MMCEP: Multimedia Complex Event Processing
 * @CLassDefinition
 */
public class EventMatching {

	// class to do event matching.

	public static void domatching(StateObject state) {

		String[] subquery = queryparserforstate(state.getQuery_name());
		int subquer_num = Integer.valueOf(subquery[1]);
		int win_num = windowparseforstate(state.getWin_name());

		/*
		 * This need to be taken care of It need to be query object not atomic query
		 * object this is a narrow view to present Implementation where we are
		 * considering only one atomic event
		 */

		AtomicQueryObject atomic_event = QueryBuilder.subscriber_query.get(subquery[0])
				.get(Integer.valueOf((subquer_num - 1))).getAtomicquery().get(win_num - 1);
		List<PublicationGraphEvent> graphstate = new ArrayList<>();
		graphstate = state.getState();
		//long statetime = state.getStatesend_matcher_time()-state.getStatesend_matcher_time(); // waiting time in the state
		long statetime = state.getStatesend_matcher_time(); // time when sate is send to the state matcher
		parseatomicquery(graphstate, atomic_event, statetime);

	}

	/**
	 * @param state
	 * @param atomic_event
	 * @param fout
	 * @param statetime
	 */
	public static void parseatomicquery(List<PublicationGraphEvent> state, AtomicQueryObject query,
			long statetime) {

		ArrayList<ArrayList<String>> object_retrieved_list = new ArrayList<>();
		HashMap<Integer, String> mapofvertex = new HashMap<>();
		// value to store in file
		
		// if query contains only object and no OO relations
		if (query.getObject().size() != 0 && query.getOOrelation() == null) {
			MatchingOperations.performObjectMatching(state, query, statetime);
		}
		
		// if query contains only OO Relation
		if (query.getObject().size() == 0 && query.getOOrelation() != null) {
			//check relation.. is it temporal relation or spatial relation or spatio temporal relation
			if(ConfigurationParameters.SPATIAL_DIRECTION_RELAION.contains(query.getOOrelation().getRelation().get(0))) {
				MatchingOperations.performSpatialDirectionMatching(state, query, statetime);
				//System.out.println("SPATIAL DIRECTION");
			}
			if(ConfigurationParameters.SPATIAL_TOPOLOGY_RELAION.contains(query.getOOrelation().getRelation().get(0))) {
				System.out.println("SPATIAL TOPOLOGY");
			}
			if(ConfigurationParameters.TEMPORAL_RELATION.contains(query.getOOrelation().getRelation().get(0))) {
				MatchingOperations.performTemporalMatching(state, query,statetime);
				
			}
			if(ConfigurationParameters.COUNT_RELATION.contains(query.getOOrelation().getRelation().get(0))) {
				MatchingOperations.performCountMatching(state, query,statetime);
				
			}
			
			//SpatialMatcher.performspatialmatching(state, query);

		}
/*		// if query contains only OO relation and no objects...
		if (query.getObject().size() == 0 && query.getOOrelation() != null) {

		}
		// if query contains only object and no OO relation
		if (query.getObject().size() != 0 && query.getOOrelation() == null) {

			for (PublicationGraphEvent ge : state) {
				// BufferedWriter writer = new BufferedWriter(new FileWriter(fout, true));
				StringBuilder csv = new StringBuilder();
				csv.append(ge.getEventID());
				csv.append(",");
				csv.append(ge.getEvent_gen_timeStamp());
				csv.append(",");
				csv.append(ge.getEvent_preprocess_timestamp());
				for (VertexObject vertex : ge.getPubGraph().vertexSet()) {
					
					 * if(vertex.getVertex_label().contains("Car")) { // presently send notification
					 * System.out.println("");
					 * 
					 * }
					 

				}
				// notification time
				csv.append(",");
				csv.append(statetime);
				csv.append(",");
				csv.append(ZonedDateTime.now().toInstant().toEpochMilli());
				// System.out.println(csv.toString());
				// Files.write(Paths.get("C:\\Users\\piyush\\Desktop\\ESWC
				// DATA\\Countcar\\count.csv"),
				// (csv.toString()+System.lineSeparator()).getBytes(),StandardOpenOption.APPEND);
				
				 * writer.newLine(); writer.write(csv.toString()); writer.close();
				 

			}

			System.out.println("Time for state:" + ZonedDateTime.now().toInstant().toEpochMilli());

		}

		if (query.getOOrelation() != null) {
			// parse oo relation..
			// the graph formulation totally depends on query
			// if the query requires spatio temporal graph then make it that like for
			// follows by and overtake
			// else only do what is require like count for traffic....
			// this condition need to be properly finalised as you cant add every relation
			// under if condition
			// so better to map it with domain model....
			if (query.getOOrelation().getRelation().get(0).contains("Followsby")) {
				createspatialgraph(state);
				System.out.println("OKOKOKOKOKOKOK");
			}
			if (query.getOOrelation().getRelation().get(0).contains("Overtake")) {
				System.out.println("Overtake");
				HashMap<String, ArrayList<Boolean>> previous_frame = new HashMap<>();
				for (PublicationGraphEvent ge : state) {
					StringBuilder csv = new StringBuilder();
					csv.append(ge.getEventID());
					csv.append(",");
					csv.append(ge.getEvent_gen_timeStamp());
					csv.append(",");
					csv.append(ge.getEvent_preprocess_timestamp());
					int i = 1;
					int j = 1;
					ArrayList<Boolean> obj_pos_val = new ArrayList<>();
					ArrayList<VertexObject> objlist = new ArrayList<>();
					for (VertexObject vertex : ge.getPubGraph().vertexSet()) {
						if (i >= 1) {
							obj_pos_val.add(false);
							previous_frame.put(vertex.getVertex_label(), obj_pos_val);
							i++;
							objlist.add(vertex);
						} else {
							// Get position value only for two objects....
							// this not correct way
							boolean val = BooleanSpatialDirection.front(
									vertex.getVertex_attributes().getBounding_box().x,
									objlist.get(0).getVertex_attributes().getBounding_box().x);
							obj_pos_val.add(val);
							previous_frame.put(vertex.getVertex_label(), obj_pos_val);
						}
						if (j >= 1) {
							HashMap<String, ArrayList<Boolean>> new_frame = new HashMap<>();
							ArrayList<Boolean> new_val = new ArrayList<>();
							for (VertexObject prevframeob : objlist) {
								if (prevframeob.getVertex_label() == vertex.getVertex_label()) {
									new_val.add(false);
									new_frame.put(vertex.getVertex_label(), new_val);
								} else {
									boolean new_val1 = BooleanSpatialDirection.front(
											vertex.getVertex_attributes().getBounding_box().x,
											objlist.get(0).getVertex_attributes().getBounding_box().x);
								}
							}
							Iterator it = previous_frame.entrySet().iterator();
							while (it.hasNext()) {
								Map.Entry pair = (Map.Entry) it.next();
								ArrayList<Boolean> prevframeval = (ArrayList<Boolean>) pair.getValue();
								ArrayList<Boolean> newframeval = new_frame.get(pair.getKey());
								for (i = 0; i < prevframeval.size(); i++) {
									LogicalOperators.xnor(prevframeval.get(i), newframeval.get(i));
								}

								// it.remove(); // avoids a ConcurrentModificationException
							}

						}

					}
					j++;
					csv.append(",");
					csv.append(statetime);
					csv.append(",");
					csv.append(ZonedDateTime.now().toInstant().toEpochMilli());
					System.out.println(csv.toString());
					
					 * try { Files.write(Paths.
					 * get("C:\\Users\\piyush\\Desktop\\ESWC DATA\\Countcar\\count.csv"),
					 * (csv.toString()+System.lineSeparator()).getBytes(),StandardOpenOption.APPEND)
					 * ; } catch (IOException e) { // TODO Auto-generated catch block
					 * e.printStackTrace(); }
					 

				}

			}

			
			 * if (atomic_event.getOOrelation().getRelation().get(0).contains("Overtake")) {
			 * for (PublicationGraphEvent ge : state) {
			 * 
			 * for (VertexObject vertex : ge.getPubGraph().vertexSet()) {
			 * System.out.println("When STate fetehed from blocking queue: " +
			 * vertex.getVertex_label() + "  Vertex_id:  " + vertex.getVertex_id() +
			 * "Eventid: " + ge.getEventID() + "YES"); }
			 * 
			 * }
			 * 
			 * HashMap<Integer, ArrayList<VertexObject>> sameobj = new HashMap<>(); int
			 * count = 0; for (PublicationGraphEvent ge : state) { ArrayList<VertexObject>
			 * localgraphvertex = new ArrayList<>(); if (ge.getPubGraph().vertexSet() !=
			 * null) { for (VertexObject vertex : ge.getPubGraph().vertexSet()) { if (count
			 * == 0) { vertex.setVertex_label(vertex.getVertex_label() +
			 * vertex.getVertex_id()); localgraphvertex.add(vertex);
			 * 
			 * }
			 * 
			 * else { ArrayList<VertexObject> preframegraphnodes = sameobj.get(count); if
			 * (preframegraphnodes != null) { for (VertexObject prevertexobj :
			 * preframegraphnodes) { System.out.println("COSINE VALUE: " +
			 * Transforms.cosineDistance(vertex.getVertex_attributes().getFeatures(),
			 * prevertexobj.getVertex_attributes().getFeatures())); if
			 * (Transforms.cosineDistance(vertex.getVertex_attributes().getFeatures(),
			 * prevertexobj.getVertex_attributes().getFeatures()) > 0.8) {
			 * vertex.setVertex_label(prevertexobj.getVertex_label());
			 * localgraphvertex.add(vertex); // sameobj.put(ge.getEventID(),
			 * localgraphvertex); } } }
			 * 
			 * }
			 * 
			 * sameobj.put(ge.getEventID(), localgraphvertex); }
			 * 
			 * }
			 * 
			 * count++;
			 * 
			 * } Iterator it = sameobj.entrySet().iterator(); while (it.hasNext()) {
			 * Map.Entry pair = (Map.Entry) it.next(); System.out.println(pair.getKey() +
			 * " = " + pair.getValue()); // it.remove(); // avoids a
			 * ConcurrentModificationException }
			 * 
			 * }
			 
			if (query.getOOrelation().getRelation().get(0).contains("Traffic")) {

			}
			if (query.getOOrelation().getRelation().get(0).contains("ParkingFull")) {
				// presently maually providing slot value
				Rectangle slot = new Rectangle(237, 61, 166, 40); // slot value for VIRAT Darking LOT 1
				// Rectangle slot = new Rectangle(247, 251,407,360); // slot value for VIRAT
				// Darking LOT 1
				// System.out.println("Goin in Parking Lot...");
				for (PublicationGraphEvent ge : state) {
					StringBuilder csv = new StringBuilder();
					csv.append(ge.getEventID());
					csv.append(",");
					csv.append(ge.getEvent_gen_timeStamp());
					csv.append(",");
					csv.append(ge.getEvent_preprocess_timestamp());

					for (VertexObject vertex : ge.getPubGraph().vertexSet()) {

						if (vertex.getVertex_label().contains(query.getOOrelation().getObj1().getVertex_label())
								&& BooleanSpatialTopology.Overlap(vertex.getVertex_attributes().getBounding_box(),
										slot) == true) {
							System.out.println("Parking Occupied");
						} else {
							// System.out.println("Parking NOT Occupied");
						}

						// System.out.println("X: "+vertex.getVertex_attributes().getBounding_box().x+
						// "y:"+ vertex.getVertex_attributes().getBounding_box().y+"w: "+
						// vertex.getVertex_attributes().getBounding_box().width+ "h: "+
						// vertex.getVertex_attributes().getBounding_box().height);

					}
					// notification time
					csv.append(",");
					csv.append(statetime);
					csv.append(",");
					csv.append(ZonedDateTime.now().toInstant().toEpochMilli());
					// System.out.println(csv.toString());
					try {
						Files.write(Paths.get("C:\\Users\\piyush\\Desktop\\ESWC DATA\\Countcar\\count.csv"),
								(csv.toString() + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					
					 * for (VertexObject vertex : ge.getPubGraph().vertexSet()) {
					 * System.out.println("When STate fetehed from blocking queue: " +
					 * vertex.getVertex_label() + "  Vertex_id:  " + vertex.getVertex_id() +
					 * "Eventid: " + ge.getEventID()+ "YES"); }
					 
				}

			}

		}*/

	}

	/**
	 * @param graph
	 * @param mapofvertex
	 */
	private static void createcompletegraph(PublicationGraphEvent graph, HashMap<Integer, String> mapofvertex) {

		// create vertex supplier.........
		Set<VertexObject> vertexSet = graph.getPubGraph().vertexSet();
		List<VertexObject> vertexList = new ArrayList<>(vertexSet);
		// there is no need to create a complete graph of single node
		if (vertexList.size() > 1) {

			Supplier<VertexObject> vSupplier = new Supplier<VertexObject>() {
				int id = 0;

				@Override
				public VertexObject get() {
					// TODO Auto-generated method stub
					return vertexList.get(id++);
				}
			};

			// create the complete graph

			SimpleWeightedGraph<VertexObject, DefaultWeightedEdge> completeGraph = new SimpleWeightedGraph<>(vSupplier,
					SupplierUtil.createDefaultWeightedEdgeSupplier());

			// Create the CompleteGraphGenerator object
			CompleteGraphGenerator<VertexObject, DefaultWeightedEdge> completeGenerator = new CompleteGraphGenerator<>(
					vertexList.size());
			// Use the CompleteGraphGenerator object to make completeGraph a
			// complete graph with [size] number of vertices

			completeGenerator.generateGraph(completeGraph);
			Iterator<VertexObject> iter = new DepthFirstIterator<>(completeGraph);
			while (iter.hasNext()) {
				VertexObject vertex = iter.next();
				String vertex_id = vertex.getVertex_label() + vertex.getVertex_id();
				System.out.println(
						"Vertex " + vertex_id + " is connected to: " + completeGraph.edgesOf(vertex).toString());
			}

		}

		/*
		 * // Print out the graph to be sure it's really complete Iterator<VertexObject>
		 * iter = new DepthFirstIterator<>(completeGraph); while (iter.hasNext()) {
		 * VertexObject vertex = iter.next(); System.out.println( "Vertex " + vertex +
		 * " is connected to: " + completeGraph.edgesOf(vertex).toString()); }
		 */
	}

	/**
	 * @param graph
	 * @param query_object
	 * @return
	 * 
	 */
	private static ArrayList<String> matchObject(ArrayList<String> query_object, PublicationGraphEvent graph) {
		ArrayList<String> object_find = new ArrayList<>();
		for (VertexObject vertex : graph.getPubGraph().vertexSet()) {
			// presently matching on lower case
			if (query_object.contains(vertex.getVertex_label())) {
				object_find.add(vertex.getVertex_label());
			}
		}
		return object_find;

	}

	public static String[] queryparserforstate(String sub_query) {

		String[] subquery = sub_query.split("Q");
		return subquery;

	}

	public static int windowparseforstate(String window) {

		String[] win = window.split("P");
		int win_num = Integer.valueOf(win[0].replace("W", ""));
		return win_num;

	}

	public static void createspatialgraph(List<PublicationGraphEvent> state) {

		for (PublicationGraphEvent graph : state) {
			createcompltegraph(graph);
		}

	}

	public static void createcompltegraph(PublicationGraphEvent state) {

		Supplier<VertexObject> vSupplier = (Supplier<VertexObject>) state.getPubGraph().getVertexSupplier();
		// System.out.println(vSupplier);
		// Create the graph object
		SimpleWeightedGraph<VertexObject, DefaultWeightedEdge> completeGraph = new SimpleWeightedGraph<>(vSupplier,
				SupplierUtil.createDefaultWeightedEdgeSupplier());

	}

}
