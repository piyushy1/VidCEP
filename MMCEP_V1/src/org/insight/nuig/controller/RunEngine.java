/**
 * 
 */
package org.insight.nuig.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.insight.nuig.configurator.ConfigurationParameters;
import org.insight.nuig.engine.matcher.CreateQueryStateStore;
import org.insight.nuig.engine.matcher.Matcher;
import org.insight.nuig.engine.messageobject.GenericWindowObject;
import org.insight.nuig.engine.messageobject.StateObject;
import org.insight.nuig.engine.querymodel.QueryBuilder;
import org.insight.nuig.engine.window.WindowConfigurationParsing;
import org.insight.nuig.publisher.ConfigurePublisher;
import org.insight.nuig.publisher.ConnectPublisher;
import org.insight.nuig.publisher.ReadPublisherFolder;
import org.insight.nuig.publisher.messageobject.PublicationEvent;
import org.opencv.core.Mat;

import javafx.collections.ObservableList;

/**
 * @author piyush
 * @date 11 Jul 2018
 * @time 11:08:28
 * @Institute Insight Centre for Data Analytics- NUIG
 * @Project: This file is part of the MMPS project owned by Sunstainable IT
 *           Group NUIG
 * 
 * @ClassDefinition This is the main class to configure and run the MMPS Engine
 * 
 */
public class RunEngine {

	/**
	 * @param args
	 */

	// public static List<PublicationEvent> win1 = new ArrayList<>();
	public static void main(String[] args) {

		/*
		 * 1 Configure your configuration file in configurator 2.Initiate Publisher
		 * 3.Initiate Subscriber 4.Initiate Engine
		 */

		// <----Configure and start Subscriber---->

		// create the subscriber query
		QueryBuilder.createsubscriberquerymap(ConfigurationParameters.Subscriptions_Path);
		// configure the window from each query to the publisher
		HashMap<Integer, ArrayList<GenericWindowObject>> window_conf = WindowConfigurationParsing
				.parsewindowfromquery();

		// create state store matcher for each query
		CreateQueryStateStore.createstatestore(window_conf);

		// <----Configure and start Publisher---->

		ConfigurePublisher conpub = new ConfigurePublisher();
		conpub.configureandstartpublisher(window_conf);
		
		// <----Configure and start Matcher---->

		// start matcher service....
		Matcher.runmatcher();

	}

}
