/**
 * 
 */
package org.insight.nuig.streampreprocessor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.insight.nuig.configurator.ConfigurationParameters;
import org.insight.nuig.publisher.ConfigurePublisher;
import org.insight.nuig.publisher.messageobject.PublicationGraphEvent;
import org.insight.nuig.publisher.messageobject.VertexAttributes;
import org.insight.nuig.publisher.messageobject.VertexObject;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import javafx.collections.ObservableList;

/**
 * @author piyush
 * @date 5 Nov 2018 13:32:56
 * @Institute: Insight Centre for Data Analytics
 * @Project: MMCEP: Multimedia Complex Event Processing
 * @CLassDefinition
 */
public class CSVStreamReader {

	/**
	 * @param filepath
	 * @param publisherqueue
	 * @param pubID
	 * @throws InterruptedException 
	 */
	public void preprocess_csv_data(String filepath, ObservableList<PublicationGraphEvent> publisherqueue, int pubID, String distribution_constant) throws InterruptedException {

		List<String> event_list = new ArrayList<>();
		// read the file
		try {
			BufferedReader br = Files.newBufferedReader(Paths.get(filepath));
			// br returns as stream and convert it into a List
			event_list = br.lines().collect(Collectors.toList());

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Iterator<String> iterator = event_list.iterator();
		int event_id =1;
		while (iterator.hasNext()) {
			
			if (distribution_constant.matches(ConfigurationParameters.PUBLISHER_DISTRIBUTION_GAUSSIAN)) {
				// TO DO
				// do guassian distribution
			}
			
			if (distribution_constant.matches(ConfigurationParameters.PUBLISHER_DISTRIBUTION_RANDOM)) {
				// TO DO
				stream_csv_data(event_id,pubID,iterator, publisherqueue);
				Random rand = new Random();
				int value = rand.nextInt(1000); // add a random seed for streaming
				Thread.sleep(value); // sleep the process for that value
				System.out.println("Publisher " + String.valueOf(pubID) + " : " + ConfigurePublisher.publisher_queue_map.get(pubID).size());

			}
			
			if (distribution_constant.matches(ConfigurationParameters.PUBLISHER_DISTRIBUTION_ZIPF)) {
				// TO DO
				// do zipf distribution
			}
			
		}

	}

	/**
	 * @param publisherqueue 
	 * @param iterator 
	 * @param pubID 
	 * @param event_id 
	 * 
	 */
	private void stream_csv_data(int event_id, int pubID, Iterator<String> iterator, ObservableList<PublicationGraphEvent> publisherqueue) {
		// create the publication graph event.......
		PublicationGraphEvent event = new PublicationGraphEvent();
		// Create Graph
		SimpleGraph<VertexObject, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
    	//create the vertex object
    	VertexObject vertex_node = new VertexObject();
    	// create the vertex attributes
    	VertexAttributes  vertex_attrib = new VertexAttributes();
    	
		event.setEvent_gen_timeStamp(System.currentTimeMillis());
		event.setEventID(event_id);
		event.setPublisherID(String.valueOf(pubID));
		
		//<DISCLAIMER> presently only adding the value to vertex attrib payload data
		// this can be change later
		
		vertex_node.setVertex_id(event_id); // foolish work just to make things work here...
		vertex_attrib.setPayload((Serializable) iterator.next());
		// add the vertex node and its attributes value to graph
		graph.addVertex(vertex_node);
		// presently no edges have been formed. that depends on scenario what type of edge you require
		// add graph to publication event
		event.setPubGraph(graph);
		//now add this event to publisher queue
		publisherqueue.add(event);
		
		
	}

}
