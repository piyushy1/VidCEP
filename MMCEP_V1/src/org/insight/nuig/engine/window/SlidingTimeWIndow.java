/**
 * 
 */
package org.insight.nuig.engine.window;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.insight.nuig.engine.matcher.CreateQueryStateStore;
import org.insight.nuig.engine.messageobject.StateObject;
import org.insight.nuig.publisher.messageobject.PublicationEvent;
import org.insight.nuig.publisher.messageobject.PublicationGraphEvent;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 * @author piyush
 * @date 18 Jul 2018
 * @time 15:52:26
 * @Institute Insight Centre for Data Analytics- NUIG
 * @Project: This file is part of the MMPS project owned by Sunstainable IT
 *           Group NUIG
 * 
 * @ClassDefinition
 * 
 */
public class SlidingTimeWIndow implements Runnable {

	// define parameters which a time window will have....
	public ObservableList<? extends PublicationGraphEvent> items;
	public List<PublicationGraphEvent> window = new ArrayList<>();
	int pub_id;
	String window_name;
	long start_time = Instant.now().getEpochSecond(); // initial start time of window
	int slide_index; // this is for having the index of window to keep the copy for slide.
	long shift_time; // shift time of window
	long end_time; // end time of window
	long window_time_len; // length of window.....
	long window_time_slide;

	// generate the constructor
	/**
	 * @param items
	 * @param pub_id
	 * @param window_id
	 * @param window_time_len
	 * @param window_time_slide
	 */
	public SlidingTimeWIndow(ObservableList<? extends PublicationGraphEvent> items, int pub_id, String window_name,
			long window_time_len, long window_time_slide) {
		super();
		this.items = items;
		this.pub_id = pub_id;
		this.window_name = window_name;
		this.window_time_len = window_time_len;
		this.window_time_slide = window_time_slide;
	}

	// trigger function....
	public static void trigger() {
		// send the value to the matcher.....
		// it depends on matcher how it will start processing the state of the
		// events.....
	}

	// sliding count window method
	public void slidetimewindow(ObservableList<? extends PublicationGraphEvent> items, int pub_id, String window_name,
			long window_len_time, long window_slide) {
		List<PublicationGraphEvent> window = new ArrayList<PublicationGraphEvent>();
		if (items != null) {
			items.addListener((ListChangeListener<PublicationGraphEvent>) c -> {
				while (c.next()) {
					if (c.wasAdded()) {
						window.addAll(c.getAddedSubList());
						System.out.println("Publisher_" + pub_id + " SLidingTimeWindow_" + window_name + " :size= "
								+ window.size() + "   Time: " + Instant.now().getEpochSecond());

						// logic for count window both tumbling and sliding
						shift_time = start_time + window_slide;
						if (Instant.now().getEpochSecond() == shift_time) {
							// mid_tym = Instant.now().getEpochSecond();
							slide_index = window.size();
						}
						end_time = start_time + window_len_time;
						if (Instant.now().getEpochSecond() == end_time) {
							// call trigger function
							// Trigger function
							StateObject win_state= new StateObject();
							win_state.setState(window);
							win_state.setWin_name(window_name.split("\\*")[0]);
							win_state.setQuery_name(window_name.split("\\*")[1]);
							try {
								CreateQueryStateStore.querystatestore.get(window_name.split("\\*")[1]).put(win_state);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							window.subList(0, slide_index - 1).clear();
							start_time = shift_time;
							System.out.println("New Window:");
							// flag =0;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		System.out.println("Publisher_" + pub_id + " Window_" + window_name + " Started");

		this.slidetimewindow(items, pub_id, window_name, window_time_len, window_time_slide);

	}

}
