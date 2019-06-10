/**
 * 
 */
package org.insight.nuig.streampreprocessor;

import static org.bytedeco.javacpp.opencv_highgui.destroyAllWindows;
import static org.bytedeco.javacpp.opencv_highgui.waitKey;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.ThreadLocalRandom;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.insight.nuig.engine.matcher.MatchingOperations;
import org.insight.nuig.models.TinyYoloPrediction;
import org.insight.nuig.publisher.ConfigurePublisher;
import org.insight.nuig.publisher.messageobject.PublicationGraphEvent;
import org.insight.nuig.publisher.messageobject.VertexObject;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import static org.bytedeco.javacpp.opencv_highgui.imshow;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import javafx.collections.ObservableList;

/**
 * @author piyush
 * @date 4 Nov 2018 14:43:31
 * @Institute: Insight Centre for Data Analytics
 * @Project: MMCEP: Multimedia Complex Event Processing
 * @CLassDefinition
 */
public class MultimediaStreamReader {

	private static final String PIYUSH_YOLO = "PiyushYolo";
	private volatile Frame[] videoFrame = new Frame[1];
	private volatile Mat[] v = new Mat[1];
	private Thread thread;
	private volatile boolean stop = false;
	private String winname;
	int i = 1;
	private static final String FILENAME1 = "/Dataset/results/preprocessing1.csv";
	// this is to get the point coodridantes which u will show for tracking.......
	public static ArrayList<VertexObject> x_points = new ArrayList<VertexObject>();


	public void preprocessmultimediadata(String videoFileName, ObservableList<PublicationGraphEvent> publisherqueue, int pubID)
			throws java.lang.Exception {

		File f = new File(videoFileName);

		FFmpegFrameGrabber grabber;
		grabber = new FFmpegFrameGrabber(f);
		grabber.start();
		Set<VertexObject> prevertexset = new HashSet<>(); // store the pregraph value
		TinyYoloPrediction yolo = new TinyYoloPrediction();
		
		while (!stop) {

			// create a graphevent
			PublicationGraphEvent graph_event = new PublicationGraphEvent();
			// grab the video frames
			videoFrame[0] = grabber.grab();
			// add timestamp of frame generation....
			//System.out.println(genTime);
			//System.out.println(date.getTime());
			graph_event.setEvent_gen_timeStamp(ZonedDateTime.now().toInstant().toEpochMilli());
			graph_event.setEventID(i);
			graph_event.setPublisherID(String.valueOf(pubID));

			if(i%50==0) {
				System.out.println("Frame: " + i );
			}
			
			
			if (videoFrame[0] == null) {
				stop();
				break;
			}

			// convert frame to a matrix array
			v[0] = new OpenCVFrameConverter.ToMat().convert(videoFrame[0]);
			if (v[0] == null) {
				continue;
			}
			if (winname == null) {
				winname = PIYUSH_YOLO + ThreadLocalRandom.current().nextInt();
			}

			/**************************************
			 * CALL THE MODEL /
			 ***************************************/
			
			SimpleGraph<VertexObject, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
			// set video decode time.........
			graph_event.setEvent_video_decode_time(ZonedDateTime.now().toInstant().toEpochMilli());
			
			graph = yolo.markWithBoundingBox(v[0], videoFrame[0].imageWidth,
					videoFrame[0].imageHeight, true, winname, i,prevertexset);
			/*graph = TinyYoloPrediction.getINSTANCE().markWithBoundingBox(v[0], videoFrame[0].imageWidth,
					videoFrame[0].imageHeight, true, winname, i,prevertexset);*/
			// set the value of pregraph object

			if(i==1) {
				for(VertexObject obj: graph.vertexSet()) {
					prevertexset.add(obj);
				}
				
			}
			else {
				prevertexset.clear();
				for(VertexObject obj: graph.vertexSet()) {
					prevertexset.add(obj);
				}
			}
			
			// set event preprocess time

			graph_event.setEvent_preprocess_timestamp(ZonedDateTime.now().toInstant().toEpochMilli());
			graph_event.setPubGraph(graph);
			//Thread.sleep(10);
			// check the null condition
//			if(i==2) {
//				StringBuilder csv = new StringBuilder();
//				csv.append("System after warmup time:"+ ZonedDateTime.now().toInstant().toEpochMilli());
//				//MatchingOperations.writetoFile(csv);
//			}
			if(!graph.vertexSet().isEmpty()) {
				publisherqueue.add(graph_event);
			}
			
			// function to log time 
/*			StringBuilder csv = new StringBuilder();
			csv.append(i);
			csv.append(",");
			csv.append(graph_event.getEvent_gen_timeStamp());
			csv.append(",");
			csv.append(graph_event.getEvent_preprocess_timestamp());
			csv.append(",");
			csv.append(graph_event.getEvent_preprocess_timestamp()- graph_event.getEvent_gen_timeStamp());
			MatchingOperations.writetoFile(csv, FILENAME1);*/
			
			
			
/*			for(PublicationGraphEvent ge: ConfigurePublisher.publisher_queue_map.get(pubID)) {
				System.out.println("WHen Graph added to main queue: "+ ge.getPubGraph().toString()+ "Eventid: "+ ge.getEventID());
			}*/
			/**To print size for windows **/
			//System.out.println("Publisher " + String.valueOf(pubID) + " : " + ConfigurePublisher.publisher_queue_map.get(pubID).size());

			i = i + 1;
			if(i==3) {
				System.out.println("Publisher Start Time: " +pubID+ " " +ZonedDateTime.now().toInstant().toEpochMilli());
			}

			//imshow(winname, v[0]);

			char key = (char) waitKey(20);
			// Exit this loop on escape:
			if (key == 27) {
				stop();
				break;
			}
		}
		
	}

	public void stop() {
		if (!stop) {
			stop = true;
			destroyAllWindows();
		}
	}

}
