/**
 * 
 */
package org.insight.nuig.engine.window;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.insight.nuig.engine.matcher.CreateQueryStateStore;
import org.insight.nuig.engine.matcher.Matcher;
import org.insight.nuig.engine.messageobject.StateObject;
import org.insight.nuig.publisher.messageobject.PublicationEvent;
import org.insight.nuig.publisher.messageobject.PublicationGraphEvent;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 * @author piyush
 * @date 18 Jul 2018
 * @time 15:27:24
 * @Institute Insight Centre for Data Analytics- NUIG
 * @Project: This file is part of the MMPS project owned by Sunstainable IT Group NUIG
 
 * @ClassDefinition
 * This class defines the time window 
 
 */

/**
 * A {@link Window} that represents a time interval from {@code start}
 * (inclusive) to {@code end} (exclusive).
 */

public class TimeWindow implements Runnable {

	// define parameters which a time window will have....
	public ObservableList<? extends PublicationGraphEvent> items;
	//public List<PublicationGraphEvent> window = new ArrayList<>();
	int pub_id;
	String window_name;
	long start_time = Instant.now().getEpochSecond(); // initial start time of window
	long window_time_len; // length of window.....

	/**
	 * @param items
	 * @param window
	 * @param pub_id
	 * @param window_id
	 * @param window_time_len
	 */
	public TimeWindow(ObservableList<? extends PublicationGraphEvent> items, int pub_id,
			String window_name, long window_time_len) {
		super();
		this.items = items;
		this.pub_id = pub_id;
		this.window_name = window_name;
		this.window_time_len = window_time_len;
	}

	/**
	 * 
	 */
	public TimeWindow() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static void trigger() {

		// send the value to the matcher.....
		// it depends on matcher how it will start processing the state of the
		// events.....
	}

	// method to add value to the window
	public void tumbletimewindow(ObservableList<? extends PublicationGraphEvent> items, int pub_id, String window_name,
			long window_len_time, List<PublicationGraphEvent> window) {
		

		if (items != null) {
			items.addListener((ListChangeListener<PublicationGraphEvent>) c -> {
				while (c.next()) {
					if (c.wasAdded()) {
						window.addAll(c.getAddedSubList());
						/*System.out.println("Publisher_" + pub_id + " TimeWindow_" + window_name + " :size= "
								+ window.size() + "   Time: " + Instant.now().getEpochSecond());*/
						// logic for count window both tumbling and sliding
						if (Instant.now().getEpochSecond()-start_time >= window_len_time) {
							// call trigger fxn**************
							StateObject win_state= new StateObject();
							win_state.setState(cloneList(window));
							win_state.setWin_name(window_name.split("\\*")[0]);
							win_state.setQuery_name(window_name.split("\\*")[1]);
							win_state.setStatesend_matcher_time(ZonedDateTime.now().toInstant().toEpochMilli());
							try {
								CreateQueryStateStore.querystatestore.get(window_name.split("\\*")[1]).put(win_state);
								//System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOO"+ CreateQueryStateStore.querystatestore.get(window_name.split("\\*")[1]).size());
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							//Matcher trigger_val = new Matcher();
							//trigger_val.gettriggervalue(window);
							window.clear();// this for tumbling window
							start_time = Instant.now().getEpochSecond();
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
		this.tumbletimewindow(items, pub_id, window_name, window_time_len, window);

	}

}
