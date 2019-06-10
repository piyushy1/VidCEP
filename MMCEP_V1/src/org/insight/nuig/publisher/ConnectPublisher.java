/**
 * 
 */
package org.insight.nuig.publisher;

import java.time.ZonedDateTime;
import java.util.ArrayList;

import org.insight.nuig.engine.matcher.MatchingOperations;
import org.insight.nuig.engine.messageobject.GenericWindowObject;
import org.insight.nuig.engine.window.WindowAssigner;
import org.insight.nuig.publisher.messageobject.PublicationGraphEvent;
import org.insight.nuig.streampreprocessor.CSVStreamReader;
import org.insight.nuig.streampreprocessor.MultimediaStreamReader;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author piyush
 * @date 11 Jul 2018
 * @time 11:20:03
 * @Institute Insight Centre for Data Analytics- NUIG
 * @Project: This file is part of the MMPS project owned by Sunstainable IT
 *           Group NUIG
 * 
 * @ClassDefinition
 * 
 */
public class ConnectPublisher implements Runnable {

	private String file_path;
	private String distribution_constant;
	private int pub_id;
	private ArrayList<GenericWindowObject> window_conf;
	//public static ObservableList<PublicationGraphEvent> publisherqueue = getsynchronisedList();// FXCollections.observableList(new
	// ArrayList<PublicationEvent>());

	/**
	 * @param file_path
	 * @param distribution_constant
	 */
	public ConnectPublisher(String file_path, String distribution_constant, int pub_id, ArrayList<GenericWindowObject> window_conf) {
		super();
		this.file_path = file_path;
		this.distribution_constant = distribution_constant;
		this.pub_id = pub_id;
		this.window_conf = window_conf;
	}

	// create a synchronise observable list

	public static ObservableList<PublicationGraphEvent> getsynchronisedList() {
		ObservableList<PublicationGraphEvent> result = FXCollections
				.observableList(new ArrayList<PublicationGraphEvent>());
		return FXCollections.synchronizedObservableList(result);
	}

	// 1. function to connect to source file
	// 2. preprocess the source file and send it to publisherqueue

	public void connectsource(String filepath, int pubID, ArrayList<GenericWindowObject> window_conf, String distribution_constant) {

		/**
		 * check whether the file is multimedia (unstrucutred data) or csv
		 * file(strucutred) this is initial extensions for file format which can be
		 * extended later.
		 **/
		 

		// condition for multimedia format
		if (filepath.contains(".mp4") || filepath.contains(".mov") || filepath.contains(".wmv")) {

			// create publisher queue for a given publisher
			ObservableList<PublicationGraphEvent> publisherqueue = getsynchronisedList();// FXCollections.observableList(new
																							// ArrayList<PublicationEvent>());
			// put the publisher queue and publisher id in hashmap
			ConfigurePublisher.publisher_queue_map.put(pubID, publisherqueue);

			/****************************************/
			/* code for windowing */
			/****************************************/
			WindowAssigner winassig = new WindowAssigner();
			winassig.assignwindow(pubID, window_conf);

			/****************************************/
			/* Initilaise Publisher */
			/****************************************/

			MultimediaStreamReader mm_stream = new MultimediaStreamReader();
			try {
				mm_stream.preprocessmultimediadata(filepath, publisherqueue, pubID); // process the multimedia stream
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		else if (filepath.contains(".csv")) {

			// create publisher queue for a given strucutred data
			ObservableList<PublicationGraphEvent> publisherqueue = getsynchronisedList();// FXCollections.observableList(new
																							// ArrayList<PublicationEvent>());
			// put the publisher queue and publisher id in hashmap
			ConfigurePublisher.publisher_queue_map.put(pubID, publisherqueue);

			/****************************************/
			/* code for windowing */
			/****************************************/
			WindowAssigner winassig = new WindowAssigner();
			winassig.assignwindow(pubID, window_conf);

			/****************************************/
			/* Initilaise Publisher */
			/****************************************/

			CSVStreamReader csv_stream = new CSVStreamReader();
			try {
				csv_stream.preprocess_csv_data(filepath, publisherqueue, pubID, distribution_constant); // process the
																										// multimedia
																										// stream
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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

		/*****
		 * 1.The data source can stream strucutre of unstrucutred data.
		 * 
		 * 2.If the data is unstrucutred then we need to pass it to Preprocessing Model
		 * 
		 * 3.The extracted content need to be then store in terms of Graphs
		 * 
		 * 4. SO the data is either strucutre or unstrucutre we need to convert it into
		 * graphs while passing to preporcessing step
		 ******/
		StringBuilder csv = new StringBuilder();
		csv.append("System Start time:"+ ZonedDateTime.now().toInstant().toEpochMilli());
		//MatchingOperations.writetoFile(csv);
		//System.out.println("System Start time:"+ ZonedDateTime.now().toInstant().toEpochMilli());
		this.connectsource(file_path, pub_id, window_conf, distribution_constant); // connect to source

	}

}
