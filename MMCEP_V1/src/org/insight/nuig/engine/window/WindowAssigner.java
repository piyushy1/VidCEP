/**
 * 
 */
package org.insight.nuig.engine.window;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.insight.nuig.configurator.ConfigurationParameters;
import org.insight.nuig.controller.RunEngine;
import org.insight.nuig.engine.messageobject.GenericWindowObject;
import org.insight.nuig.engine.messageobject.WindowAssignObject;
import org.insight.nuig.publisher.ConfigurePublisher;
import org.insight.nuig.publisher.messageobject.PublicationGraphEvent;

/**
 * @author piyush
 * @date 24 Jul 2018
 * @time 11:31:05
 * @Institute Insight Centre for Data Analytics- NUIG
 * @Project: This file is part of the MMPS project owned by Sunstainable IT
 *           Group NUIG
 * 
 * @ClassDefinition the class assign windows to each publisher... a single
 *                  publisher can have multiple windows each window will be a
 *                  separate thread
 * 
 */
public class WindowAssigner {

	public int num_windows; // no. of windows that need to be initialised
	public int publisher_id; // the publisher for which the window is defined
	
	// public List<String> window_config; // the configuration of each windows

	/**
	 * @param num_windows
	 * @param publisher_id
	 * @param window_config
	 */

	// generating the constructor
	public WindowAssigner(int num_windows, int publisher_id) {
		super();
		this.num_windows = num_windows;
		this.publisher_id = publisher_id;
		// this.window_config = window_config;
	}

	// generating constructor for super class

	/**
	 * 
	 */
	public WindowAssigner() {
		super();
		// TODO Auto-generated constructor stub
	}

	// assign windows.. each window will be a separate thread maintaining its own
	// state

	public void assignwindow(int publisher_id, ArrayList<GenericWindowObject> window_conf) {

		//WindowAssignObject win_param = WindowConfigurationParsing.parsepublisherwindowconfiguration(window_conf); // this was old parsing method
		
		WindowAssignObject win_param = WindowConfigurationParsing.parsepublisherwindowconfigurationnew(window_conf);

		num_windows = win_param.getNum_windows();

		ExecutorService executor = Executors.newFixedThreadPool(num_windows);

		for (int i = 0; i < num_windows; i++) {

			int window_id = i;
			

			if (win_param.getWinobj().get(i).getWin_name().matches(ConfigurationParameters.TUMBLING_COUNT_WINDOW)) {
				//List<PublicationGraphEvent> window = new ArrayList<PublicationGraphEvent>();
				CountWindow cntwin = new CountWindow(ConfigurePublisher.publisher_queue_map.get(publisher_id), publisher_id,
						win_param.getWinobj().get(i).getWin_query_name(), win_param.getWinobj().get(i).getLen_count());
				executor.execute(cntwin);

			}

			if (win_param.getWinobj().get(i).getWin_name().matches(ConfigurationParameters.SLIDING_COUNT_WINDOW)) {

				SlidingCountWindow slidingcntwin = new SlidingCountWindow(
						ConfigurePublisher.publisher_queue_map.get(publisher_id), publisher_id, win_param.getWinobj().get(i).getWin_query_name(),
						win_param.getWinobj().get(i).getLen_count(), win_param.getWinobj().get(i).getSlide_count());
				executor.execute(slidingcntwin);

			}

			if (win_param.getWinobj().get(i).getWin_name().matches(ConfigurationParameters.TUMBLING_TIME_WINDOW)) {

				TimeWindow tymwin = new TimeWindow(ConfigurePublisher.publisher_queue_map.get(publisher_id), publisher_id,
						win_param.getWinobj().get(i).getWin_query_name(), win_param.getWinobj().get(i).getLen_time());
				executor.execute(tymwin);

			}

			if (win_param.getWinobj().get(i).getWin_name().matches(ConfigurationParameters.SLIDING_TIME_WINDOW)) {

				SlidingTimeWIndow slidingtymwin = new SlidingTimeWIndow(ConfigurePublisher.publisher_queue_map.get(publisher_id),
						publisher_id, win_param.getWinobj().get(i).getWin_query_name(), win_param.getWinobj().get(i).getLen_time(),
						win_param.getWinobj().get(i).getSlide_time());

				executor.execute(slidingtymwin);
			}

		}
		executor.shutdown();
		try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
