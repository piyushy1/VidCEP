/**
 * 
 */
package org.insight.nuig.engine.matcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import org.insight.nuig.engine.messageobject.EventObject;
import org.insight.nuig.engine.messageobject.StateObject;
import org.insight.nuig.publisher.messageobject.PublicationGraphEvent;
import org.insight.nuig.publisher.messageobject.VertexObject;

/**
 * @author piyush
 * @date 16 Nov 2018 08:18:47
 * @Institute: Insight Centre for Data Analytics
 * @Project: MMCEP: Multimedia Complex Event Processing
 * @CLassDefinition
 */
public class ExecuteMatcher implements Runnable {
	// public BlockingQueue<StateObject> state = new LinkedBlockingDeque<>();
	public BlockingQueue<StateObject> state;
	public String queryname;
	private static final String FILENAME1 = "\\Dataset\\results\\preprocessing.csv";
	// public volatile boolean Finished;

	/**
	 * @param state
	 * @param queryname
	 */
	public ExecuteMatcher(String queryname) {
		super();
		this.queryname = queryname;
		// Finished = false;
	}


	public void executematcher(String queryname2) {


		while (true) {

			
			try {
				// file to log values...
				// File fout = new File("C:\\Users\\piyush\\Desktop\\ESWC
				// DATA\\Countcar\\count.csv");
				// obj_state = state1.take();
				if (!CreateQueryStateStore.querystatestore.get(queryname2).isEmpty()) {
					StateObject obj_state = new StateObject();
					obj_state = CreateQueryStateStore.querystatestore.get(queryname2).take();
					if (obj_state != null)// Checking if job is to be processed then processing it first and then
											// checking for return
					{
						obj_state.setState_recieve_matcher_time(ZonedDateTime.now().toInstant().toEpochMilli()); // check waiting time
						StringBuilder csv = new StringBuilder();
						csv.append(obj_state.getQuery_name());
						csv.append(",");
						csv.append(obj_state.getStatesend_matcher_time());
						csv.append(",");
						csv.append(ZonedDateTime.now().toInstant().toEpochMilli());
					    //Files.write(Paths.get(System.getProperty("user.dir")+FILENAME1),(csv.toString()+System.lineSeparator()).getBytes(),StandardOpenOption.APPEND); 
						System.out.println("State Send*****************************");
						EventMatching.domatching(obj_state);
						// Thread.sleep(100);
						// printstate(object);
						// Thread.sleep(100);

					}

				}

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} /*catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/

		}

	}

	public static void printstate(StateObject state1) {

		for (PublicationGraphEvent ge : state1.getState()) {
			for (VertexObject vertex : ge.getPubGraph().vertexSet()) {
				System.out.println("When STate fetehed from blocking queue: " + vertex.getVertex_label()
						+ "  Vertex_id:  " + vertex.getVertex_id() + "Eventid: " + ge.getEventID());
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
		// TODO Auto-generated method stub

		this.executematcher(queryname);

	}

}
