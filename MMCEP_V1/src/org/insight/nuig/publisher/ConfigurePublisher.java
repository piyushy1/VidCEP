/**
 * 
 */
package org.insight.nuig.publisher;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.insight.nuig.configurator.ConfigurationParameters;
import org.insight.nuig.engine.messageobject.GenericWindowObject;
import org.insight.nuig.engine.window.WindowConfigurationParsing;
import org.insight.nuig.publisher.messageobject.PublicationGraphEvent;

import javafx.collections.ObservableList;

/**
 * @author piyush
 * @date 2 Nov 2018 18:47:00
 * @Institute: Insight Centre for Data Analytics
 * @Project: MMCEP: Multimedia Complex Event Processing
 * @CLassDefinition
 */
public class ConfigurePublisher {

	// observable list is used as it provides the facility of listener.......
	public static Map<Integer, ObservableList<PublicationGraphEvent>> publisher_queue_map = new ConcurrentHashMap<Integer, ObservableList<PublicationGraphEvent>>();

	/*****************************************************************
	 * Publisher Configuration to MMPS System
	 * @param window_conf2 
	 *****************************************************************/

	public void configureandstartpublisher(HashMap<Integer, ArrayList<GenericWindowObject>> window_conf) {
		// read windows configuration
		//ConcurrentHashMap<String, String> window_conf = WindowConfigurationParsing.parsewindowconfiguration();
		/*
		 * each publisher is connected to a source wwhich is a file here. check file
		 * present in data set folder or not , if present fetch the file name and store
		 * then in a list
		 */
		File folder = new File(ConfigurationParameters.PUBLISHER_FOLDER_PATH);
		// function to get list of files...
		File[] listOfFiles = ReadPublisherFolder.getfile(folder);

		/* Create a thread pool for number of Publishers which you want to initialise */

		// CountWindow.addColection(RunEngine.publisher_queue_map.get(pub_id),win1,
		// pub_id);

		ThreadPoolExecutor executor = new ThreadPoolExecutor(ConfigurationParameters.PUBLISHER_NUMBER,
				ConfigurationParameters.PUBLISHER_NUMBER + 3, 100, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(), new ThreadPoolExecutor.CallerRunsPolicy());

		for (int i = 1; i <= ConfigurationParameters.PUBLISHER_NUMBER; i++) {

			// connect to publisher
			// <Disclaimer keep the name of publisher in ascending order.....>
			ConnectPublisher conn = new ConnectPublisher(
					ConfigurationParameters.PUBLISHER_FOLDER_PATH + "/" + listOfFiles[i - 1].getName(),
					ConfigurationParameters.PUBLISHER_DISTRIBUTION_RANDOM, i, window_conf.get(i));
			executor.execute(conn);

		}
		executor.shutdown();

	}

}
