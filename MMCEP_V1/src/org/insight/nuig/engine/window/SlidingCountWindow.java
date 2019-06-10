/**
 * 
 */
package org.insight.nuig.engine.window;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.insight.nuig.engine.matcher.CreateQueryStateStore;
import org.insight.nuig.engine.messageobject.StateObject;
import org.insight.nuig.publisher.messageobject.PublicationEvent;
import org.insight.nuig.publisher.messageobject.PublicationGraphEvent;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 * @author piyush
 * @date 23 Jul 2018
 * @time 10:29:14
 * @Institute Insight Centre for Data Analytics- NUIG
 * @Project: This file is part of the MMPS project owned by Sunstainable IT Group NUIG
 
 * @ClassDefinition
 
 */
public class SlidingCountWindow implements Runnable{
	
	public ObservableList<? extends PublicationGraphEvent> items;
	
	int pub_id;
	String window_name;
	int window_count_len;
	int window_count_slide;
	
    // constructor using superclass

	/**
	 * 
	 */
	public SlidingCountWindow() {
		super();
		// TODO Auto-generated constructor stub
	}

	// constructor using fields
	
	/**
	 * @param items
	 * @param window
	 * @param pub_id
	 * @param window_count_len
	 * @param window_count_slide
	 */
	public SlidingCountWindow(ObservableList<? extends PublicationGraphEvent> items,
			int pub_id,String window_name, int window_count_len, int window_count_slide) {
		super();
		this.items = items;
		this.pub_id = pub_id;
		this.window_name= window_name;
		this.window_count_len = window_count_len;
		this.window_count_slide = window_count_slide;
	}

	public static void trigger() {

		// send the value to the matcher.....
		// it depends on matcher how it will start processing the state of the
		// events.....
	}
	
	// sliding method
	public void slidecountwindow(ObservableList<? extends PublicationGraphEvent> items, List<PublicationGraphEvent> window, int pub_id, String window_name,int window_count_len,int window_count_slide) {

		if (items != null) {
			items.addListener((ListChangeListener<PublicationGraphEvent>) c -> {
				while (c.next()) {
					if (c.wasAdded()) {
						window.addAll(c.getAddedSubList());
						System.out.println(
								"Publisher_"+pub_id + " SlidingCountWindow_" + window_name + " :size= "+ window.size() +"   Time: " + Instant.now().getEpochSecond());
						// System.out.println("Window Added"+ Instant.now().getEpochSecond());
						// logic for count window both tumbling and sliding
						if (window.size() == window_count_len) {	
							// Trigger function
							StateObject win_state= new StateObject();
							win_state.setState(cloneList(window));
							win_state.setWin_name(window_name.split("\\*")[0]);
							win_state.setQuery_name(window_name.split("\\*")[1]);
							win_state.setStatesend_matcher_time(ZonedDateTime.now().toInstant().toEpochMilli());
							//sendtomatcher();
							try {
								CreateQueryStateStore.querystatestore.get(window_name.split("\\*")[1]).put(win_state);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							ListIterator listIterator = window.listIterator();
							
							for (int i = 0; i <= window_count_slide; i++) {
								listIterator.next();
								listIterator.remove();
							}
							// window.clear();// this for tumbling window
							// items.removeAll(window);
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
	

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		System.out.println("Publisher_"+pub_id + " Window_" + window_name + " Started");
		List<PublicationGraphEvent> window = new ArrayList<>();
		this.slidecountwindow(items, window, pub_id,window_name, window_count_len,window_count_slide);
		
	}


}
