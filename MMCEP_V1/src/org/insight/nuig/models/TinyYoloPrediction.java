package org.insight.nuig.models;

/**
 * @author piyush
 * @date 4 Nov 2018
 * @time 13:11:26
 * @Institute Insight Centre for Data Analytics- NUIG
 * @Project: This file is part of the MMPS project owned by Sunstainable IT
 *           Group NUIG
 * 
 * @ClassDefinition: The class is about Tiny Yolo model. The Yolo model code is borrowed from Ramo Kelvis Github code
 * 
 */

import static org.bytedeco.javacpp.opencv_core.FONT_HERSHEY_DUPLEX;
import static org.bytedeco.javacpp.opencv_highgui.imshow;
import static org.bytedeco.javacpp.opencv_imgcodecs.imwrite;
import static org.bytedeco.javacpp.opencv_imgproc.putText;
import static org.bytedeco.javacpp.opencv_imgproc.rectangle;
import static org.bytedeco.javacpp.opencv_imgproc.circle;
import static org.bytedeco.javacpp.opencv_imgproc.cvtColor;
import  org.bytedeco.javacpp.opencv_core.*;
import  static org.bytedeco.javacpp.opencv_core.CV_32SC4;
import org.bytedeco.javacpp.indexer.UByteRawIndexer;
import java.awt.Rectangle;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.IntBuffer;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.Scalar;
import org.datavec.image.loader.NativeImageLoader;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.nn.layers.objdetect.DetectedObject;
import org.deeplearning4j.nn.layers.objdetect.Yolo2OutputLayer;
import org.deeplearning4j.zoo.PretrainedType;
import org.deeplearning4j.zoo.model.TinyYOLO;
import org.deeplearning4j.zoo.model.YOLO2;
import org.insight.nuig.coredomainmodel.eventcalculus.spatial.BooleanSpatialTopology;
import org.insight.nuig.publisher.messageobject.VertexAttributes;
import org.insight.nuig.publisher.messageobject.VertexObject;
import org.insight.nuig.streampreprocessor.MultimediaStreamReader;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.nd4j.linalg.api.blas.params.MMulTranspose;
import org.nd4j.linalg.api.buffer.DataBuffer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.api.ndarray.SparseFormat;
import org.nd4j.linalg.dataset.api.preprocessor.ImagePreProcessingScaler;
import org.nd4j.linalg.indexing.INDArrayIndex;
import org.nd4j.linalg.indexing.ShapeOffsetResolution;
import org.nd4j.linalg.indexing.conditions.Condition;
import org.nd4j.linalg.ops.transforms.Transforms;


import com.google.flatbuffers.FlatBufferBuilder;

public class TinyYoloPrediction {

	private ComputationGraph preTrained;
	private List<DetectedObject> predictedObjects;
	private HashMap<Integer, String> map;
	int i = 1;

	public TinyYoloPrediction() {
		try {

			//preTrained = (ComputationGraph) tinyYOLOZoo.initPretrained();
			preTrained = (ComputationGraph)TinyYOLO.builder().build().initPretrained();
			//preTrained = (ComputationGraph)new TinyYOLO().initPretrained();
			//preTrained= (ComputationGraph) YOLO2.builder().build().initPretrained();
			prepareLabels();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

/*	private static final TinyYoloPrediction INSTANCE = new TinyYoloPrediction();

	public static TinyYoloPrediction getINSTANCE() {
		return INSTANCE;
	}*/

	public SimpleGraph<VertexObject, DefaultEdge> markWithBoundingBox(Mat file, int imageWidth, int imageHeight,
			boolean newBoundingBOx, String winName, int i,Set<VertexObject> pregraphvertex) throws Exception {
/*		FAST("Real-Time but low accuracy", 224, 224, 7, 7),
		MEDIUM("Almost Real-time and medium accuracy", 416, 416, 13, 13),
		SLOW("Slowest but high accuracy", 608, 608, 19, 19);*/
		int width = 608;  
		int height = 608;
		int gridWidth = 19;
		int gridHeight = 19;
		double detectionThreshold = 0.5;

		Yolo2OutputLayer outputLayer = (Yolo2OutputLayer) preTrained.getOutputLayer(0);
		SimpleGraph<VertexObject, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
		if (newBoundingBOx) {
			
			INDArray indArray = prepareImage(file, width, height);
			
			if (indArray == null) {
                System.out.println("RESULTS ARE NULLLLLLLLLLLLLLLL");
            }
			
			INDArray results = preTrained.outputSingle(indArray);
			// preTrained.output(indArray);
            if (results == null) {
                System.out.println("RESULTS ARE NULLLLLLLLLLLLLLLL");
            }
			predictedObjects = outputLayer.getPredictedObjects(results, detectionThreshold);
			// System.out.println("results = "+i+" " + predictedObjects);
			// create the graph
			graph = markWithBoundingBox(file, gridWidth, gridHeight, imageWidth, imageHeight, graph,pregraphvertex,i);

		}

		//imshow(winName, file);
		//imwrite("/home/piyush/Desktop/output/"+winName+i + ".png", file);
		return graph;

	}

	private static INDArray prepareImage(Mat file, int width, int height) throws IOException {
		NativeImageLoader loader = new NativeImageLoader(height, width, 3);
		ImagePreProcessingScaler imagePreProcessingScaler = new ImagePreProcessingScaler(0, 1);
		INDArray indArray = loader.asMatrix(file);
		imagePreProcessingScaler.transform(indArray);
		return indArray;
	}

	private void prepareLabels() {
		if (map == null) {
			String s = "aeroplane\n" + "bicycle\n" + "bird\n" + "boat\n" + "bottle\n" + "bus\n" + "car\n" + "cat\n"
					+ "chair\n" + "cow\n" + "diningtable\n" + "dog\n" + "horse\n" + "motorbike\n" + "person\n"
					+ "pottedplant\n" + "sheep\n" + "sofa\n" + "train\n" + "tvmonitor";
			String[] split = s.split("\\n");
			int i = 0;
			map = new HashMap<>();
			for (String s1 : split) {
				map.put(i++, s1);
			}
		}
	}

	private SimpleGraph<VertexObject, DefaultEdge> markWithBoundingBox(Mat file, int gridWidth, int gridHeight, int w,
			int h, SimpleGraph<VertexObject, DefaultEdge> graph, Set<VertexObject> pregraphvertex, int i) {

		int vertex_id = 1; // set vertex id

		// check the null condition
		if (predictedObjects == null) {
			// since the model unable to detect the object
			VertexObject nullobj = new VertexObject();
			nullobj.setVertex_label("null");
			nullobj.setVertex_id(0);
			graph.addVertex(nullobj);
			return graph;
		}

		ArrayList<DetectedObject> detectedObjects = new ArrayList<>(predictedObjects);

		while (!detectedObjects.isEmpty()) {
			Optional<DetectedObject> max = detectedObjects.stream()
					.max((o1, o2) -> ((Double) o1.getConfidence()).compareTo(o2.getConfidence()));
			if (max.isPresent()) {
				DetectedObject maxObjectDetect = max.get();
				removeObjectsIntersectingWithMax(detectedObjects, maxObjectDetect);
				detectedObjects.remove(maxObjectDetect);
				markWithBoundingBox(file, gridWidth, gridHeight, w, h, maxObjectDetect, graph, vertex_id,pregraphvertex,i);
			}
			vertex_id = vertex_id + 1;
		}

		return graph;
	}

	private static void removeObjectsIntersectingWithMax(ArrayList<DetectedObject> detectedObjects,
			DetectedObject maxObjectDetect) {
		double[] bottomRightXY1 = maxObjectDetect.getBottomRightXY();
		double[] topLeftXY1 = maxObjectDetect.getTopLeftXY();
		List<DetectedObject> removeIntersectingObjects = new ArrayList<>();
		for (DetectedObject detectedObject : detectedObjects) {
			double[] topLeftXY = detectedObject.getTopLeftXY();
			double[] bottomRightXY = detectedObject.getBottomRightXY();
			double iox1 = Math.max(topLeftXY[0], topLeftXY1[0]);
			double ioy1 = Math.max(topLeftXY[1], topLeftXY1[1]);

			double iox2 = Math.min(bottomRightXY[0], bottomRightXY1[0]);
			double ioy2 = Math.min(bottomRightXY[1], bottomRightXY1[1]);

			double inter_area = (ioy2 - ioy1) * (iox2 - iox1);

			double box1_area = (bottomRightXY1[1] - topLeftXY1[1]) * (bottomRightXY1[0] - topLeftXY1[0]);
			double box2_area = (bottomRightXY[1] - topLeftXY[1]) * (bottomRightXY[0] - topLeftXY[0]);

			double union_area = box1_area + box2_area - inter_area;
			double iou = inter_area / union_area;

			if (iou > 0.5) {
				removeIntersectingObjects.add(detectedObject);
			}

		}
		detectedObjects.removeAll(removeIntersectingObjects);
	}

	private void markWithBoundingBox(Mat file, int gridWidth, int gridHeight, int w, int h, DetectedObject obj,
			SimpleGraph<VertexObject, DefaultEdge> graph, int vertex_id, Set<VertexObject> pregraphvertex, int i) {

		// create the vertex object
		VertexObject vertex_node = new VertexObject();
		// create the vertex attributes
		/////////////////////////////////////ATTRIBUTES/////////////////////////////////////////////////////
     	VertexAttributes vertex_attrib = new VertexAttributes();

		// get the bounding box values
		double[] xy1 = obj.getTopLeftXY();
		double[] xy2 = obj.getBottomRightXY();
		int predictedClass = obj.getPredictedClass();
		int x1 = (int) Math.round(w * xy1[0] / gridWidth);
		// the zero condition is put because sometime bounding box prection is coming outside image
		// this then throws error when need to access bounding box from matrix as the matrix does not have -ve value
		// to understand more check frame 7 o f video of cycle and car..
		if(x1<0) {
			x1=0;
		}
		int y1 = (int) Math.round(h * xy1[1] / gridHeight);
		if(y1<0) {
			y1=0;
		}
		int x2 = (int) Math.round(w * xy2[0] / gridWidth);
		if(x2<0) {
			x2=0;
		}
		if(x2>file.cols()) {
			x2 =file.cols();
		}
		int y2 = (int) Math.round(h * xy2[1] / gridHeight);
		if(y2<0) {
			y2=0;
		}
		if(y2>file.rows()) {
			y2= file.rows();
		}

		Rectangle bounding_box = new Rectangle(x1, y1, (x2-x1), (y2-y1));
		// for object tracking visualisation
		//MultimediaStreamReader.x_points.add((x2-x1)/2);
		//MultimediaStreamReader.y_points.add((y2-y1)/2);
	
	
		// this code block is get the feature matrix of the bounding box
		// this is require for object tracking
		// we will use the feature matrix cosine similarity and bounding box overlap to
		// detect the object similarity...
		Mat feature_matrix = new Mat();
		//System.out.println("rows: "+ file.rows()+" col: "+file.cols()+" x1:" +x1+" y1: "+y1+" x2:"+ x2+" y2: "+y2 +" x2-x1: "+(x2-x1)+" y2-y1: "+(y2-y1));

		file.apply(new Rect(x1, y1, ((x2) - (x1)), ((y2) - (y1)))).copyTo(feature_matrix);
		
		//**************adding color filter// need to work on it...//
		vertex_attrib.setPre_color_computetime(ZonedDateTime.now().toInstant().toEpochMilli());
		Mat hsv = new Mat();
		Mat mask = new Mat();
		cvtColor(feature_matrix, hsv, org.bytedeco.javacpp.opencv_imgproc.COLOR_RGB2HSV);
		//Scalar lower = new Scalar(0, 0, 150, 0);//(BGRA) where A is transparent
        //Scalar upper = new Scalar(100, 60, 255, 0);
		
		//Scalar lower = new Scalar(0, 0, 0, 0);//(BGRA) where A is transparent
        //Scalar upper = new Scalar(192, 192, 192, 0);
		
		Scalar lower = new Scalar(0, 0, 0, 0);//(BGRA) where A is transparent
        Scalar upper = new Scalar(200, 200, 200, 0);
		
        //imwrite("C:\\Users\\piyush\\Desktop\\test\\test1\\" + i+"_before"+(int)(Math.random() * 100 + 50) + ".png", hsv);
        org.bytedeco.javacpp.opencv_core.inRange(hsv, new Mat(1, 1,CV_32SC4 , lower), new Mat(1, 1, CV_32SC4, upper), mask);
        
		//imwrite("C:\\Users\\piyush\\Desktop\\test\\test1\\" + i+"_after"+(int)(Math.random() * 100 + 50) + ".png", mask);
		int n = org.bytedeco.javacpp.opencv_core.countNonZero(mask);
		int totalpixels = mask.rows()*mask.cols();
		if (((double)n/totalpixels)*100 >=70) {
			vertex_attrib.setColor("Black");
		}
		else {
			vertex_attrib.setColor("OtherColor");
		}
		vertex_attrib.setPost_color_computetime(ZonedDateTime.now().toInstant().toEpochMilli());
		//System.out.println("COunt non zero"+ n + "ROws COL"+ mask.rows()*mask.cols() );
		//System.out.println("VALUE**************"+ ((double)n/totalpixels)*100);
		// to get value from each pixel

		INDArray indArray;
		INDArray results1 = null;
	try {
			// presently creating a vector length of 200*200
			indArray = prepareImage(feature_matrix, 200, 200);
			// this feature can be passed to another model to detect attributes.....
			results1 = preTrained.outputSingle(indArray);

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		// double sim = Transforms.cosineSim(results1, results2); double sim2 =
		// Transforms.cosineDistance(results1, results2);
		 //System.out.println("The COSINE SIMILAIRTY IS: "+ sim +"   "+ sim2);
		 

		//imwrite("C:\\Users\\piyush\\Desktop\\test\\" + "test" + vertex_id + ".png", feature_matrix);
		//imwrite("C:\\Users\\piyush\\Desktop\\test\\" + "testhsv" + vertex_id + ".png", hsv);
		rectangle(file, new Point(x1, y1), new Point(x2, y2), Scalar.RED);
		putText(file, map.get(predictedClass), new Point(x1 + 2, y2 - 2), FONT_HERSHEY_DUPLEX, 1, Scalar.GREEN);

		// set the values of bounding box and other attributes to vertexattribute object
		vertex_attrib.setBounding_box(bounding_box);
		if(results1 !=null) {
			vertex_attrib.setFeatures(results1);
		}
		
		////////////////////////////////////////////ATTRIBUTE END////////////////////////////////////////////////////////////
		
		/////////////////////////////////////////////////TRACKING///////////////////////////////////////////////////////////
		// set the color value
		 //vertex_attrib.setColor(color); 

		// set the values to vertexobject
		
		// before seeting the object node map it it with previous object
		
		if(i!=1 && !pregraphvertex.isEmpty()) {
			for(VertexObject prevertexobj: pregraphvertex) {
				if(Transforms.cosineSim(results1, prevertexobj.getVertex_attributes().getFeatures())>=0.8 && 
						BooleanSpatialTopology.Overlap(bounding_box,prevertexobj.getVertex_attributes().getBounding_box()) &&
						prevertexobj.getVertex_label().contains(map.get(predictedClass))) {
					
					vertex_node.setVertex_id(vertex_id);
					vertex_node.setVertex_attributes(vertex_attrib);
					vertex_node.setVertex_label(prevertexobj.getVertex_label());
					rectangle(file, new Point(x1, y1), new Point(x2, y2), Scalar.RED);
					putText(file, vertex_node.getVertex_label()+vertex_attrib.getColor(), new Point(x1 + 2, y2 - 2), FONT_HERSHEY_DUPLEX, 1, Scalar.GREEN);
					
				}
				

				
			}
			// if doesnt matches with any of previous graph node means a new node
			if(vertex_node.getVertex_label()== null) {
				vertex_node.setVertex_id(vertex_id);
				vertex_node.setVertex_attributes(vertex_attrib);
				vertex_node.setVertex_label(map.get(predictedClass)+(int)(Math.random() * 100 + 50));
				rectangle(file, new Point(x1, y1), new Point(x2, y2), Scalar.RED);
				putText(file, vertex_node.getVertex_label()+vertex_attrib.getColor(), new Point(x1 + 2, y2 - 2), FONT_HERSHEY_DUPLEX, 1, Scalar.GREEN);
			}
		}
		if(i==1) {
			vertex_node.setVertex_id(vertex_id);
			vertex_node.setVertex_attributes(vertex_attrib);
			vertex_node.setVertex_label(map.get(predictedClass)+vertex_id);
			rectangle(file, new Point(x1, y1), new Point(x2, y2), Scalar.RED);
			putText(file, vertex_node.getVertex_label()+vertex_attrib.getColor(), new Point(x1 + 2, y2 - 2), FONT_HERSHEY_DUPLEX, 1, Scalar.GREEN);
		}
		if (pregraphvertex.isEmpty() || vertex_node.getVertex_label() == null) {
			vertex_node.setVertex_id(vertex_id);
			vertex_node.setVertex_attributes(vertex_attrib);
			vertex_node.setVertex_label(map.get(predictedClass)+(int)(Math.random() * 100 + 50));
			rectangle(file, new Point(x1, y1), new Point(x2, y2), Scalar.RED);
			putText(file, vertex_node.getVertex_label()+vertex_attrib.getColor(), new Point(x1 + 2, y2 - 2), FONT_HERSHEY_DUPLEX, 1, Scalar.GREEN);
		}
		
/////////////////////////////////////////////////TRACKING END///////////////////////////////////////////////////////////
		// code for drawing tracking positions over an image....
/*	if (i%10 <=10) {
			String diff_vertex = "";
			int k1 =1;
			for (VertexObject obj1: MultimediaStreamReader.x_points) {
				if(k1==1) {
					diff_vertex = obj1.getVertex_label();
					if(obj1.getVertex_label().contains("car")) {
						int centre_x = obj1.getVertex_attributes().getBounding_box().x+ ((obj1.getVertex_attributes().getBounding_box().width)/2);
						int centre_y = obj1.getVertex_attributes().getBounding_box().y+ ((obj1.getVertex_attributes().getBounding_box().height)/2);
						circle(file, new Point(centre_x, centre_y), 6, Scalar.GREEN, 6, 0, 0);
					}
					else {
						int centre_x = obj1.getVertex_attributes().getBounding_box().x;
						int centre_y = obj1.getVertex_attributes().getBounding_box().y+ ((obj1.getVertex_attributes().getBounding_box().height));
						circle(file, new Point(centre_x, centre_y), 6, Scalar.RED, 6, 0, 0);
						
					}
					//rectangle(file, new Point(237, 61), new Point(403, 85), Scalar.GREEN,2,0,0);
					//circle(file, new Point(centre_x, centre_y),10, Scalar.RED);
					k1++;
				}
				else {					
					
					if(obj1.getVertex_label().contains("car")) {
					int centre_x = obj1.getVertex_attributes().getBounding_box().x+ ((obj1.getVertex_attributes().getBounding_box().width)/2);
					int centre_y = obj1.getVertex_attributes().getBounding_box().y+ ((obj1.getVertex_attributes().getBounding_box().height)/2);
					circle(file, new Point(centre_x, centre_y), 6, Scalar.GREEN, 6, 0, 0);
				}
				else {
					int centre_x = obj1.getVertex_attributes().getBounding_box().x;
					int centre_y = obj1.getVertex_attributes().getBounding_box().y+ ((obj1.getVertex_attributes().getBounding_box().height));
					circle(file, new Point(centre_x, centre_y), 6, Scalar.RED, 6, 0, 0);
					
				}

					
				}
				
			}
		for(int loop =0; loop< MultimediaStreamReader.x_points.size(); loop++) {
				circle(file, new Point(MultimediaStreamReader.x_points.get(loop),MultimediaStreamReader.y_points.get(loop)),8, Scalar.GREEN);
				//org.bytedeco.javacpp.opencv_imgproc.circle(file, new Point(MultimediaStreamReader.x_points.get(loop),MultimediaStreamReader.x_points.get(loop)),1, Scalar.GREEN,1);
			}
			imwrite("C:\\Users\\piyush\\Desktop\\test\\test1\\" + "Tracker_new"+i + ".png", file);
			System.out.println("FILE WRITTEN");
		}
*/

		// set the vertex to graph
		//MultimediaStreamReader.x_points.add(vertex_node);
		if (i==125) {
			System.out.println("Frame:"+i);
		}
		// only for objct detection....
		/*vertex_node.setVertex_id(vertex_id);
		vertex_node.setVertex_label(map.get(obj.getPredictedClass())+(int)(Math.random() * 100 + 50));
		vertex_node.setVertex_attributes(vertex_attrib);*/
		
		// add confidence score
		vertex_node.setConfidence(obj.getConfidence());
		graph.addVertex(vertex_node);
		

		// System.out.println("Graph: "+graph.toString());

		// presently we are lthe null graph....... relationships can be created later on

	}
	
	
	public static void getobjectattributes(){
		
	}

}