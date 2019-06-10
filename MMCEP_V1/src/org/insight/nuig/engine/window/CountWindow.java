/**
 * 
 */
package org.insight.nuig.engine.window;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.insight.nuig.engine.matcher.CreateQueryStateStore;
import org.insight.nuig.engine.messageobject.StateObject;
import org.insight.nuig.publisher.messageobject.PublicationEvent;
import org.insight.nuig.publisher.messageobject.PublicationGraphEvent;
import org.insight.nuig.publisher.messageobject.VertexObject;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 * @author piyush
 * @date 17 Jul 2018
 * @time 15:36:17
 * @Institute Insight Centre for Data Analytics- NUIG
 * @Project: This file is part of the MMPS project owned by Sunstainable IT
 *           Group NUIG
 * 
 * @ClassDefinition this class defines the count window.
 * 
 */
public class CountWindow implements Runnable {
	public ObservableList<? extends PublicationGraphEvent> items;
	public int pub_id;
	public String window_name;
	public int window_count_len;
	/**
	 * @param items
	 * @param id
	 */
	public CountWindow(ObservableList<? extends PublicationGraphEvent> items, int pub_id, String window_name,
			int window_count_len) {
		super();
		this.items = items;
		this.pub_id = pub_id;
		this.window_name = window_name;
		this.window_count_len = window_count_len;

	}

	public static void trigger() {

		// send the value to the matcher.....
		// it depends on matcher how it will start processing the state of the
		// events.....
	}

	// method to add value to the window
	public void tumblecountwindow(ObservableList<? extends PublicationGraphEvent> items,
			List<PublicationGraphEvent> window, int pub_id, String window_name, int window_count) {
		if (items != null) {
			items.addListener((ListChangeListener<PublicationGraphEvent>) c -> {
				while (c.next()) {
					if (c.wasAdded()) {
						window.addAll(c.getAddedSubList());
						/*System.out.println("Publisher_" + pub_id + " CountWindow_" + window_name + " :size= "
								+ window.size() + "   Time: " + Instant.now().getEpochSecond());*/
						//System.out.println("VALUE ADDED TO WINDOW");
						/*
						 * try { Thread.sleep(10); } catch (InterruptedException e1) { // TODO
						 * Auto-generated catch block e1.printStackTrace(); }
						 */
						/*
						 * for(PublicationGraphEvent ge: window) { for(VertexObject vertex:
						 * ge.getPubGraph().vertexSet()) {
						 * System.out.println("When Graph aaded to window: "+
						 * vertex.getVertex_label()+"  Vertex_id:  "+ vertex.getVertex_id()
						 * +"Eventid: "+ ge.getEventID()); }
						 * 
						 * 
						 * }
						 */
						/*
						 * System.out.println( "Publisher_"+pub_id + " Window_" + window_name +
						 * " :size= "+ window.size() +"   Time: " + Instant.now().getEpochSecond()); //
						 * System.out.println("Window Added"+ Instant.now().getEpochSecond());
						 */ // logic for count window both tumbling and sliding
						if (window.size() == window_count) {
							// Trigger function
							StateObject win_state = new StateObject();
							win_state.setState(cloneList(window));
							win_state.setWin_name(window_name.split("\\*")[0]);
							win_state.setQuery_name(window_name.split("\\*")[1]);
							win_state.setStatesend_matcher_time(ZonedDateTime.now().toInstant().toEpochMilli());

							
							// sendtomatcher();
							try {
								CreateQueryStateStore.querystatestore.get(window_name.split("\\*")[1]).put(win_state);
								/************CAUTION: Concurrent modification exception
								 * if it comes make thread sleep for 10 ms**********/
								//Thread.sleep(10);
								/*
								 * for(StateObject obj :
								 * CreateQueryStateStore.querystatestore.get(window_name.split("\\*")[1])) {
								 * for(PublicationGraphEvent ge: obj.getState()) { for(VertexObject vertex:
								 * ge.getPubGraph().vertexSet()) {
								 * System.out.println("When STate added to blocking queue: "+
								 * vertex.getVertex_label()+"  Vertex_id:  "+ vertex.getVertex_id()
								 * +"Eventid: "+ ge.getEventID()); }
								 * 
								 * 
								 * } }
								 */
/*								System.out
										.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOO" + CreateQueryStateStore.querystatestore
												.get(window_name.split("\\*")[1]).size());*/
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							window.clear();// this for tumbling window
							// items.removeAll(window);// if you want to remove items from queue too...
						}
					}
					if (c.wasRemoved()) {
						window.removeAll(c.getRemoved());
						System.out.println(window.toString());
					}
				}
			});

			for (PublicationGraphEvent item : items) {
				window.add(item);
			}

		}

	}
	
	// function to clone arraylist of publication event
	public static ArrayList<PublicationGraphEvent> cloneList(List<PublicationGraphEvent> window) {
		ArrayList<PublicationGraphEvent> clone = new ArrayList<PublicationGraphEvent>(window.size());
	    for (PublicationGraphEvent item : window)
			try {
				clone.add(item.clone());
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    return clone;
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */

	@Override
	public void run() {

		System.out.println("Publisher_" + pub_id + " Window_" + window_name + " Started");
		List<PublicationGraphEvent> window = new ArrayList<>();
		this.tumblecountwindow(items, window, pub_id, window_name, window_count_len);

	}

}
