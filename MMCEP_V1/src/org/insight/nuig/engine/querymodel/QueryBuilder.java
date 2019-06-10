/**
 * 
 */
package org.insight.nuig.engine.querymodel;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.stream.Collectors;

import org.insight.nuig.configurator.ConfigurationParameters;
import org.insight.nuig.coredomainmodel.eventcalculus.logical.LogicalOperators;
import org.insight.nuig.coredomainmodel.messageobject.EventCalculusObject;
import org.insight.nuig.coredomainmodel.messageobject.Obj2ObjRelation;
import org.insight.nuig.engine.messageobject.AtomicQueryObject;
import org.insight.nuig.engine.messageobject.GenericWindowObject;
import org.insight.nuig.engine.messageobject.QueryObject;
import org.insight.nuig.publisher.messageobject.VertexAttributes;
import org.insight.nuig.publisher.messageobject.VertexObject;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

/**
 * @author piyush
 * @date 9 Nov 2018 15:16:24
 * @Institute: Insight Centre for Data Analytics
 * @Project: MMCEP: Multimedia Complex Event Processing
 * @CLassDefinition
 */
public class QueryBuilder {
	public static HashMap<String, ArrayList<QueryObject>> subscriber_query = new HashMap<>();

	// get the value from the subscription and convert it into internal query....
	// create a hash map for the list of subsriber and subscription
	// <Subscriber, <Query List>>

	public static void createsubscriberquerymap(String filepath) {

		// create subscription parsing
		/*1. Subscriber can issue multiple queries.
		2. Each subsriber and his query will be maintained using hashmap <S1, {Q1,Q2}>
		3. Each Q1 is a query object which consist of atomic query and spatial and temporal relation witht them*/
		
		ArrayList<String> subscription_list = new ArrayList<>();
		BufferedReader br;
		try {
			br = Files.newBufferedReader(Paths.get(filepath));
			subscription_list = (ArrayList<String>) br.lines().collect(Collectors.toList());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Iterator<String> iteratorlist = subscription_list.iterator();
		while (iteratorlist.hasNext()) {
			String query = iteratorlist.next();

			ArrayList<QueryObject> querylist = new ArrayList<>();
			// remove the subscriber from query and add the subscriber to the query map
			String[] subsriber_query = query.split("\\=");
			// now the subscriber query will have two array
			// first contains subscriber and second contains query list for that subscriber
			if (subsriber_query[1].contains("*")) {
				String[] queries = subsriber_query[1].split("\\*");
				for (int i = 0; i < queries.length; i++) {
					QueryObject queryobj = new QueryObject();
					queryobj = parsesub2query(queries[i]);
					querylist.add(queryobj);

				}

			} else {
				QueryObject queryobj = new QueryObject();
				queryobj = parsesub2query(subsriber_query[1]);
				querylist.add(queryobj);

			}

			subscriber_query.put(subsriber_query[0], querylist);

		}
			System.out.println("Subscriber query created!!!!");
	}

	public static QueryObject parsesub2query(String subquery1) {
		// create atomic query
		ArrayList<AtomicQueryObject> atomicquery2 = new ArrayList<>();
		ArrayList<EventCalculusObject> calcobj = new ArrayList<>();
		QueryObject query = new QueryObject();
		//AtomicQueryObject atomicquery = new AtomicQueryObject();
		String[] subquery = subquery1.split("\\%");
		query.setNotification_time(Integer.valueOf(subquery[1]));
		String[] modify_query = subquery[0].replaceAll("\\[|\\]", "").split("\\; ");
		int i=0;
		for (int k = 0; k < (modify_query.length / 4); k++) {
			AtomicQueryObject atomicquery = new AtomicQueryObject();
			if (k > 0) {
				EventCalculusObject calculusobj = new EventCalculusObject();
				// logical operation list
				if (modify_query[k * 4].contains("AND") || modify_query[k * 4].contains("OR")) {
					calculusobj.setLogicoperations(modify_query[k * 4]);
					calcobj.add(calculusobj);
				}
				// temporal operation list
				if (modify_query[k * 4].contains("BEFORE") || modify_query[k * 4].contains("AFTER")) {
					calculusobj.setTemporalrelations(modify_query[k * 4]);
					calcobj.add(calculusobj);
				}
				// spatial operator list
				if (modify_query[k * 4].contains("TOUCH") || modify_query[k * 4].contains("DISJOINT")) {
					calculusobj.setSpatialtopologyoperations(modify_query[k * 4]);
					calcobj.add(calculusobj);
				}
				if (modify_query[k * 4].contains("LEFT") || modify_query[k * 4].contains("RIGHT")
						|| modify_query[k * 4].contains("BACK") || modify_query[k * 4].contains("FRONT")) {
					calculusobj.setSpatialtopologyoperations(modify_query[k * 4]);
					calcobj.add(calculusobj);
				}
			}
			if(k>0) {
				int j = i+k; 
				for (i = j; i < (j+4); i++) {
					String queryparse = modify_query[i];
					if (i % (4+k) == 0) {
						ArrayList<VertexObject> obj = parseobject(queryparse);
						atomicquery.setObject(obj);
					}
					if (i % (4+k) == 1) {
						queryparse = queryparse.replaceAll("\\bObject2ObjectRelation\\b|\\:|\\{|\\}", "");
						if (queryparse.length() > 0) {
							Obj2ObjRelation oorel = parseOOrelation(queryparse);
							atomicquery.setOOrelation(oorel);

						}
						System.out.println(queryparse);

					}
					if (i % (4+k) == 2) {
						queryparse = queryparse.replaceAll("\\bPublisher\\b|\\:|\\{|\\}", "");
						atomicquery.setPublisher(Integer.valueOf(queryparse));
						System.out.println(queryparse);
					}
					if (i % (4+k) == 3) {
						queryparse = queryparse.replaceAll("\\bWindow\\b|\\:|\\{|\\}", "");
						GenericWindowObject window = parsewindow(queryparse);
						atomicquery.setWindow(window);
						System.out.println(queryparse);
					}

				}
			}
			else
			{
				for (i = 0; i < 4; i++) {
					String queryparse = modify_query[i];
					if (i % 4 == 0) {
						ArrayList<VertexObject> obj = parseobject(queryparse);
						atomicquery.setObject(obj);
					}
					if (i % 4 == 1) {
						queryparse = queryparse.replaceAll("\\bObject2ObjectRelation\\b|\\:|\\{|\\}", "");
						if (queryparse.length() > 0) {
							Obj2ObjRelation oorel = parseOOrelation(queryparse);
							atomicquery.setOOrelation(oorel);

						}
						System.out.println(queryparse);

					}
					if (i % 4 == 2) {
						queryparse = queryparse.replaceAll("\\bPublisher\\b|\\:|\\{|\\}", "");
						atomicquery.setPublisher(Integer.valueOf(queryparse));
						System.out.println(queryparse);
					}
					if (i % 4 == 3) {
						queryparse = queryparse.replaceAll("\\bWindow\\b|\\:|\\{|\\}", "");
						GenericWindowObject window = parsewindow(queryparse);
						atomicquery.setWindow(window);
						System.out.println(queryparse);
					}

				}
			}

			atomicquery2.add(atomicquery);

		}
		query.setAtomicquery(atomicquery2);
		query.setQueryrelation(calcobj);
		return query;

	}

	public static ArrayList<VertexObject> parseobject(String queryparse) {
		ArrayList<VertexObject> object = new ArrayList<>();
		queryparse = queryparse.replaceAll("\\bObject\\b|\\:|\\{|\\}", "");
		if (queryparse.length() > 0) {
			if (queryparse.contains(",")) {
				String[] queryparse1 = queryparse.split("\\,");
				for (int i = 0; i < queryparse1.length; i++) {
					// setting attribute value
					if(queryparse1[i].contains("&")) {
						String[] queryparse2 = queryparse1[i].split("\\&");
						VertexObject obj = new VertexObject();
						VertexAttributes attr = new VertexAttributes();
						// seet color value of attribute.... presently handling color.... for more...just write the parsing
						attr.setColor(queryparse2[1]);
						obj.setVertex_label(queryparse2[0]);
						obj.setVertex_attributes(attr);
						object.add(obj);
					}
					else {
						VertexObject obj = new VertexObject();
						obj.setVertex_label(queryparse1[i]);
						object.add(obj);
					}

				}
			} else {
				if(queryparse.contains("&")) {
					String[] queryparse2 = queryparse.split("\\&");
					VertexObject obj = new VertexObject();
					VertexAttributes attr = new VertexAttributes();
					// seet color value of attribute.... presently handling color.... for more...just write the parsing
					attr.setColor(queryparse2[1]);
					obj.setVertex_label(queryparse2[0]);
					obj.setVertex_attributes(attr);
					object.add(obj);
				}
				else {
					VertexObject obj = new VertexObject();

					obj.setVertex_label(queryparse);

					object.add(obj);
				}

			}

		}
		return object;

	}

	public static Obj2ObjRelation parseOOrelation(String queryparse) {

		Obj2ObjRelation relation = new Obj2ObjRelation();

		ArrayList<String> relationname = new ArrayList<>();
		if(queryparse.contains("COUNT")) {
			relation= callcountparse(relation,relationname,queryparse);
			return relation;
			
		}
		else {
		queryparse = queryparse.replace("(", ",");
		queryparse = queryparse.replaceAll("\\)", "");
		String[] queryparse1 = queryparse.split(",");
		relationname.add(queryparse1[0]);
		VertexObject obj1 = new VertexObject();
		VertexObject obj2 = new VertexObject();
		// presently  attribute functionality fxn need to be wrapped in a method
		if(queryparse1[1].contains("&")) {
			String[] queryparse_attr = queryparse1[1].split("&");
			obj1.setVertex_label(queryparse_attr[0]);
			VertexAttributes color = new VertexAttributes();
			color.setColor(queryparse_attr[1]);
			obj1.setVertex_attributes(color);
			
		}
		else
		{
			obj1.setVertex_label(queryparse1[1]);
		}
		if(queryparse1[2].contains("&")) {
			String[] queryparse_attr = queryparse1[2].split("&");
			obj2.setVertex_label(queryparse_attr[0]);
			VertexAttributes color = new VertexAttributes();
			color.setColor(queryparse_attr[1]);
			obj2.setVertex_attributes(color);
		}
		else
		{
			obj2.setVertex_label(queryparse1[2]);
		}		
		relation.setObj1(obj1);
		relation.setObj2(obj2);
		relation.setRelation(relationname);

		return relation;
	}
	}

	/**
	 * @param queryparse 
	 * @param relation 
	 * @param relationname 
	 * @return 
	 * 
	 */
	private static Obj2ObjRelation callcountparse(Obj2ObjRelation relation, ArrayList<String> relationname, String queryparse) {
		queryparse = queryparse.replace("(", ",");
		queryparse = queryparse.replaceAll("\\)", "");
		String[] queryparse1 = queryparse.split(",");
		relationname.add(queryparse1[0]);
		VertexObject obj1 = new VertexObject();
		// presently  attribute functionality fxn need to be wrapped in a method
		if(queryparse1[1].contains("&")) {
			String[] queryparse_attr = queryparse1[1].split("&");
			obj1.setVertex_label(queryparse_attr[0]);
			VertexAttributes color = new VertexAttributes();
			color.setColor(queryparse_attr[1]);
			obj1.setVertex_attributes(color);
			
		}
		else
		{
			obj1.setVertex_label(queryparse1[1]);
		}
		relation.setObj1(obj1);
		relation.setRelation(relationname);
		return relation;
		// TODO Auto-generated method stub
		
	}

	public static GenericWindowObject parsewindow(String queryparse) {
		GenericWindowObject win_obj = new GenericWindowObject();

		if (queryparse.contains(ConfigurationParameters.SLIDING_TIME_WINDOW)) {
			queryparse = queryparse.replaceAll("\\bSlidingTimeWindow\\b|\\(|\\)", "");
			String[] queryparse1 = queryparse.split(",");
			
			win_obj.setWin_name(ConfigurationParameters.SLIDING_TIME_WINDOW);
			win_obj.setLen_time(Long.valueOf(queryparse1[0]));
			win_obj.setSlide_time(Long.valueOf(queryparse1[1]));
		}
		if (queryparse.contains(ConfigurationParameters.TUMBLING_TIME_WINDOW)) {
			queryparse = queryparse.replaceAll("\\bTumblingTimeWindow\\b|\\(|\\)", "");
			win_obj.setWin_name(ConfigurationParameters.TUMBLING_TIME_WINDOW);
			win_obj.setLen_time(Long.valueOf(queryparse));
		}
		if (queryparse.contains(ConfigurationParameters.SLIDING_COUNT_WINDOW)) {
			queryparse = queryparse.replaceAll("\\bSlidingCountWindow\\b|\\(|\\)", "");
			String[] queryparse1 = queryparse.split(",");
			win_obj.setWin_name(ConfigurationParameters.SLIDING_COUNT_WINDOW);
			win_obj.setLen_count(Integer.valueOf(queryparse1[0]));
			win_obj.setSlide_count(Integer.valueOf(queryparse1[1]));
		}
		if (queryparse.contains(ConfigurationParameters.TUMBLING_COUNT_WINDOW)) {
			queryparse = queryparse.replaceAll("\\bTumblingCountWindow\\b|\\(|\\)", "");
			win_obj.setWin_name(ConfigurationParameters.TUMBLING_COUNT_WINDOW);
			win_obj.setLen_count(Integer.valueOf(queryparse));
		}
		return win_obj;

	}

}
