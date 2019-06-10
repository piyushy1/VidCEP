/**
 * 
 */
package org.insight.nuig.configurator;

/**
 * @author piyush
 * @date 11 Jul 2018
 * @time 13:11:26
 * @Institute Insight Centre for Data Analytics- NUIG
 * @Project: This file is part of the MMPS project owned by Sunstainable IT
 *           Group NUIG
 * 
 * @ClassDefinition
 * 
 */
public class ConfigurationParameters {

	/***************************************************
	 * Publisher Configuration Parameter to MMPS System
	 ***************************************************/
	public static String curpath = System.getProperty("user.dir");
	public static String PUBLISHER_FOLDER_PATH = curpath + "/Dataset/Publications";
	public static String PUBLISHER_DISTRIBUTION_ZIPF = "Zipf";
	public static String PUBLISHER_DISTRIBUTION_GAUSSIAN = "Gaussian";
	public static String PUBLISHER_DISTRIBUTION_RANDOM = "Random";
	public static int PUBLISHER_NUMBER =1;
	// define each publisher starting and end time with the system execution
	

	/***************************************************
	 * Subscriber Configuration Parameter to MMPS System
	 ***************************************************/
	
	public final static int Max_Subscribers = 2;
	public final static int Max_Subscriptions_Per_Subscriber = 50;	
	public final static String Subscriptions_Path = curpath + "/Dataset/Subscriptions/possible_subscriptions.txt";

	/***************************************************
	 * Engine Configuration Parameter to MMPS System
	 ***************************************************/
	
	//TODO
	
	/***************************************************
	 * Models Configuration Parameter to MMPS System
	 ***************************************************/
	public static String MODEL_ANNOTATION_FOLDER_PATH = curpath + "\\Dataset\\ModelTrainingAnnotation";
	
	/***************************************************
	 * Evaluation Configuration Parameter to MMPS System
	 ***************************************************/
	public static String EVALUATION_STORAGE_FOLDER_PATH = curpath + "\\Results";
	public static String EVALUATION_STORAGE_NAME = "Results.txt";
	public static String EVALUATION_METRICS = "latency,throughput";
	
	/***************************************************
	 * Communication Interface Parameter to MMPS System
	 ***************************************************/
	
	public final static String PUBLISHER_QUEUE = "Publisher_Queue";
	public final static String SUBSCRIBER_QUEUE = "Subscriber_Queue";
	public final static String NOTIFICATION_QUEUE = "Notification_Queue";
	public final static String CONTROL_QUEUE = "Control_Queue";
	public final static String HOST_NAME = "localhost";

	// TODO

	/***************************************************
	 * Log Configuration Parameter to MMPS System
	 ***************************************************/

	public static String LOG_FOLDER_PATH = curpath + "\\Log\\";
	
	
	/***************************************************
	 * Window Configuration Parameter to MMPS System
	 ***************************************************/
	
	public final static String SLIDING_TIME_WINDOW = "SlidingTimeWindow";
	public final static String TUMBLING_TIME_WINDOW = "TumblingTimeWindow";
	public final static String SLIDING_COUNT_WINDOW = "SlidingCountWindow";
	public final static String TUMBLING_COUNT_WINDOW = "TumblingCountWindow";
	
	
	/***************************************************
	 * SPATIAL and TEMPORAL RELATION Parameter to MMPS System
	 ***************************************************/
	
	public final static String SPATIAL_DIRECTION_RELAION = "Left,Right,Front,Back" ;
	public final static String SPATIAL_TOPOLOGY_RELAION = "Overlap,Touch" ;
	public final static String TEMPORAL_RELATION = "SEQ,CONJ,EQ,DISJ";
	public final static String COUNT_RELATION = "COUNT";

	
	
/*	Now each publisher can have one or more window so we have to define convention to define parameter for window
	
	Parameters to define window
	
	1. SlidingTimeWindow(Window_Length, Sliding_Length) where Window_Lenthe and Sliding_Length are Time in seconds
	e.g SlidingTimeWindow(1000,500) i.e window will consume datastream for 1000 sec and then slide for 500 sec.
	
	2. SlidingTimeWindow(Window_Length) where Window_Lenght is in Time in seconds
	
	3. SlidingCountWindow(Window_Length, Sliding_Length) where Window_Lenght and Sliding_Length are counts of data elements in streams
	
	4. TimeCountWindow(Window_Length) where Window_Lenght  counts of data elements in streams
	
	*  Window configuration format.
	*  <Publisher,No. of Windows,TimeWindow(),COuntWIndow(),........> 
	*  E.g 
	*  Suppose you want to create two windows for Publisher 1 . First window is a SlidingTimeWindow(1000,200) and second TumblingCount
	*  Window(100) the it will be <Publisher1=2%TimeWIndow,1000,200,*,CountWindow, 100|Publisher2=1%TimeWIndow,1000,200>
	*  here | used for separating publishers and * is used for separating multiple windows within a publisher and % is used to separate number of windows
	*  within a publisher
	*  
	*/
	
	//public final static String WINDOW_CONF = "1=2%"+ SLIDING_COUNT_WINDOW +",7,4*"+TUMBLING_COUNT_WINDOW +",10"+"|"+"2=2%"+ TUMBLING_TIME_WINDOW +",10*"+SLIDING_TIME_WINDOW+",5,2";
	//public final static String WINDOW_CONF = "1=2%"+ SLIDING_COUNT_WINDOW +",7,4*"+TUMBLING_COUNT_WINDOW +",10";		
	public final static String WINDOW_CONF = "1=1%"+ SLIDING_COUNT_WINDOW +",7,4"+"|"+"2=1%"+ TUMBLING_COUNT_WINDOW +",5";
	//public final static String WINDOW_CONF = "1=2%"+ TUMBLING_TIME_WINDOW +",7*"+TUMBLING_COUNT_WINDOW +",10";
	//public final static String WINDOW_CONF = "1=2%"+ TUMBLING_TIME_WINDOW +",7";
	

}
