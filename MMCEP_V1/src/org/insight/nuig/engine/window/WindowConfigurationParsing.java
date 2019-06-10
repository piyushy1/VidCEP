/**
 * 
 */
package org.insight.nuig.engine.window;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

import org.insight.nuig.configurator.ConfigurationParameters;
import org.insight.nuig.engine.messageobject.AtomicQueryObject;
import org.insight.nuig.engine.messageobject.GenericWindowObject;
import org.insight.nuig.engine.messageobject.QueryObject;
import org.insight.nuig.engine.messageobject.WindowAssignObject;
import org.insight.nuig.engine.querymodel.QueryBuilder;

/**
 * @author piyush
 * @date 25 Jul 2018
 * @time 11:16:14
 * @Institute Insight Centre for Data Analytics- NUIG
 * @Project: This file is part of the MMPS project owned by Sunstainable IT
 *           Group NUIG
 * 
 * @ClassDefinition this class is used to parse the parameters which have been
 *                  provided in the configuration parameters file
 * 
 */
public class WindowConfigurationParsing {
	
	//get the values of the window of and set it to publishers.....
	public static HashMap<Integer, ArrayList<GenericWindowObject>> parsewindowfromquery() {
		HashMap<Integer, ArrayList<GenericWindowObject>> window_map = new HashMap<Integer, ArrayList<GenericWindowObject>>();
		for(Entry<String, ArrayList<QueryObject>> entry: QueryBuilder.subscriber_query.entrySet()) {
			ArrayList<GenericWindowObject> window = new ArrayList<>();
			int j=1;
			for (QueryObject query : entry.getValue()) {
				int i=1;
				for(AtomicQueryObject atomicquery : query.getAtomicquery()) {
					
					GenericWindowObject win = atomicquery.getWindow();
					win.setWin_query_name("W"+String.valueOf(i)+"P"+String.valueOf(atomicquery.getPublisher())+"*"+entry.getKey()+"Q"+String.valueOf(j));
					System.out.println("Window Query: "+ win.getWin_query_name());
					if(window_map.containsKey(atomicquery.getPublisher())) {
						window_map.get(atomicquery.getPublisher()).add(win);
					}
					else {
						window.add(win);
						window_map.put(atomicquery.getPublisher(),window );
					}

					i=i+1;
				}
				j=j+1;
				
			}
			
		}
		 
		return window_map;
		
	}
	
	

	// Initial regex to parse the window configuration.....
	public static ConcurrentHashMap<String, String> parsewindowconfiguration() {

		ConcurrentHashMap<String, String> window_conf_param = new ConcurrentHashMap<String, String>();
		String window_param = ConfigurationParameters.WINDOW_CONF; // read value of window configuration
		StringTokenizer tokenizer = new StringTokenizer(window_param, "=|"); // tokenize as per piping delimiter
		List<String> tokens = new ArrayList<>();
		while (tokenizer.hasMoreElements()) {
			tokens.add(tokenizer.nextToken());
		}
		// put value in hashmap
		for (int indx = 0; indx < tokens.size(); indx += 2) {
			window_conf_param.put(tokens.get(indx), tokens.get(indx + 1));

		}

		return window_conf_param;

	}
	
	// new code
	public static WindowAssignObject parsepublisherwindowconfigurationnew(ArrayList<GenericWindowObject> gen_win) {
		WindowAssignObject win_assign_obj = new WindowAssignObject();
		win_assign_obj.setNum_windows(gen_win.size());
		win_assign_obj.setWinobj(gen_win);
		return win_assign_obj;
		
	}
	
	
	// <old code >parsing of windows for each publisher. 
	public static WindowAssignObject parsepublisherwindowconfiguration(String conf) {

		// write the window conf regex function here
		WindowAssignObject win_assign_obj = new WindowAssignObject();
		List<GenericWindowObject> gen_win = new ArrayList<>();
		StringTokenizer tokenizer = new StringTokenizer(conf, "%*"); // tokenize as per piping delimiter
		List<String> tokens = new ArrayList<>();
		while (tokenizer.hasMoreElements()) {
			tokens.add(tokenizer.nextToken());
		}
		// assign values
		win_assign_obj.setNum_windows(Integer.valueOf(tokens.get(0)));
		for(int i=1; i< tokens.size();i++) {
			StringTokenizer tokenizer1 = new StringTokenizer(tokens.get(i), ",");
			List<String> tokens1 = new ArrayList<>();
			while (tokenizer1.hasMoreElements()) {
				tokens1.add(tokenizer1.nextToken());
			}
			GenericWindowObject win_obj = new GenericWindowObject();
				win_obj.setWin_name(tokens1.get(0));
				if (win_obj.getWin_name().matches(ConfigurationParameters.SLIDING_COUNT_WINDOW)) {
					
					win_obj.setLen_count(Integer.valueOf(tokens1.get(1)));
					win_obj.setSlide_count(Integer.valueOf(tokens1.get(2)));
					gen_win.add(win_obj);
					
				}
				if (win_obj.getWin_name().matches(ConfigurationParameters.SLIDING_TIME_WINDOW)) {
					
					win_obj.setLen_time(Long.valueOf(tokens1.get(1)));
					win_obj.setSlide_time(Long.valueOf(tokens1.get(2)));
					gen_win.add(win_obj);
				}
				if (win_obj.getWin_name().matches(ConfigurationParameters.TUMBLING_COUNT_WINDOW)) {
					win_obj.setLen_count(Integer.valueOf(tokens1.get(1)));
					gen_win.add(win_obj);
					
				}
				if (win_obj.getWin_name().matches(ConfigurationParameters.TUMBLING_TIME_WINDOW)) {
					win_obj.setLen_time(Long.valueOf(tokens1.get(1)));
					gen_win.add(win_obj);
					
				}
				

		}
		
		
		win_assign_obj.setWinobj(gen_win);
	
		
		return win_assign_obj;
	}
	

}
