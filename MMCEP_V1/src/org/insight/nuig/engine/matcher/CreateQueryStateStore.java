/**
 * 
 */
package org.insight.nuig.engine.matcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

import org.insight.nuig.engine.messageobject.GenericWindowObject;
import org.insight.nuig.engine.messageobject.QueryObject;
import org.insight.nuig.engine.messageobject.StateObject;
import org.insight.nuig.engine.querymodel.QueryBuilder;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

/**
 * @author piyush
 * @date 13 Nov 2018 19:18:10
 * @Institute: Insight Centre for Data Analytics
 * @Project: MMCEP: Multimedia Complex Event Processing
 * @CLassDefinition
 */
public class CreateQueryStateStore {
	public static Map<String, LinkedBlockingQueue<StateObject>> querystatestore = new ConcurrentHashMap<String, LinkedBlockingQueue<StateObject>>();
	public static LinkedBlockingQueue<StateObject> state_queue;
	
	// <Need to think about it>. Its the implementation with Hashmap which is fucking stupid...
	// switching to blocking queue. 
	
/*	public static void createstatestore(HashMap<Integer, ArrayList<GenericWindowObject>> window_conf) {
		// get the each subscriber.....
		// the query they have and create a hashtable H<Q,<State........>>........
		// so creating a concurrent hashmap which is storing all queries related to that
		// subscriber

		for (Map.Entry<Integer, ArrayList<GenericWindowObject>> eachsubscriberquery : window_conf.entrySet()) {
			ArrayList<GenericWindowObject> value = eachsubscriberquery.getValue();
			for (GenericWindowObject win : value) {
				String[] splitsubscriberquery = win.getWin_query_name().split("\\*");
				if (!querystatestore.containsKey(splitsubscriberquery[1])) {
					querystatestore.computeIfAbsent(splitsubscriberquery[1], s-> new ConcurrentHashMap<String,ArrayList<GenericWindowObject>>());
					ArrayList<GenericWindowObject> state = new ArrayList<>();
					querystatestore.get(splitsubscriberquery[1]).put(splitsubscriberquery[0],state);

				}
				else{
					if(!querystatestore.get(splitsubscriberquery[1]).containsKey(splitsubscriberquery[0])) {
						ArrayList<GenericWindowObject> state = new ArrayList<>();
						querystatestore.get(splitsubscriberquery[1]).put(splitsubscriberquery[0],state);
					}
					
				}

				
			}

		}
		System.out.println("HashStore!!!!!!!!!!");

	}*/
	
	public static void createstatestore(HashMap<Integer, ArrayList<GenericWindowObject>> window_conf) {

		for (Map.Entry<Integer, ArrayList<GenericWindowObject>> eachsubscriberquery : window_conf.entrySet()) {
			ArrayList<GenericWindowObject> value = eachsubscriberquery.getValue();
			for (GenericWindowObject win : value) {
				String[] splitsubscriberquery = win.getWin_query_name().split("\\*");
				if (!querystatestore.containsKey(splitsubscriberquery[1])) {
					state_queue = new LinkedBlockingQueue<>(); 
					// assumption that the size is this much. Arrayblockingqueue is bounded
					// LinkedBlockingDeque is unbounded thus...cn store large capacity...
					// but incurs cost of creating nodes and deleting it....
					// so if arrayblocking queue is interrupting thread its better to use LinkedBlockingDeque
					querystatestore.put(splitsubscriberquery[1], state_queue);

				}

			}

		}
		System.out.println("HashStore!!!!!!!!!!");

	}

	/**
	 * @param x
	 * @return
	 */
	private static void createhashmap() {
		// TODO Auto-generated method stub
		
	}
}
