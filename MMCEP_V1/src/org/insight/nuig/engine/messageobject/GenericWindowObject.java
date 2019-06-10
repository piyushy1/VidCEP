/**
 * 
 */
package org.insight.nuig.engine.messageobject;

/**
 * @author piyush
 * @date 25 Jul 2018
 * @time 13:21:45
 * @Institute Insight Centre for Data Analytics- NUIG
 * @Project: This file is part of the MMPS project owned by Sunstainable IT Group NUIG
 
 * @ClassDefinition
 
 */
public class GenericWindowObject {
	String win_query_name;   // for which query this window was created
	String win_name;
	int len_count;
	int slide_count;
	long len_time;
	long slide_time;
	/**
	 * 
	 */
	public GenericWindowObject() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param win_name
	 * @param len_count
	 * @param slide_count
	 * @param len_time
	 * @param slide_time
	 */
	public GenericWindowObject(String win_query_name, String win_name, int len_count, int slide_count, long len_time, long slide_time) {
		super();
		this.win_query_name = win_query_name;
		this.win_name = win_name;
		this.len_count = len_count;
		this.slide_count = slide_count;
		this.len_time = len_time;
		this.slide_time = slide_time;
	}
	/**
	 * @return the win_name
	 */
	public String getWin_name() {
		return win_name;
	}
	/**
	 * @param win_name the win_name to set
	 */
	public void setWin_name(String win_name) {
		this.win_name = win_name;
	}
	/**
	 * @return the len_count
	 */
	public int getLen_count() {
		return len_count;
	}
	/**
	 * @param len_count the len_count to set
	 */
	public void setLen_count(int len_count) {
		this.len_count = len_count;
	}
	/**
	 * @return the slide_count
	 */
	public int getSlide_count() {
		return slide_count;
	}
	/**
	 * @param slide_count the slide_count to set
	 */
	public void setSlide_count(int slide_count) {
		this.slide_count = slide_count;
	}
	/**
	 * @return the len_time
	 */
	public long getLen_time() {
		return len_time;
	}
	/**
	 * @param len_time the len_time to set
	 */
	public void setLen_time(long len_time) {
		this.len_time = len_time;
	}
	/**
	 * @return the slide_time
	 */
	public long getSlide_time() {
		return slide_time;
	}
	/**
	 * @param slide_time the slide_time to set
	 */
	public void setSlide_time(long slide_time) {
		this.slide_time = slide_time;
	}
	/**
	 * @return the win_query_name
	 */
	public String getWin_query_name() {
		return win_query_name;
	}
	/**
	 * @param win_query_name the win_query_name to set
	 */
	public void setWin_query_name(String win_query_name) {
		this.win_query_name = win_query_name;
	}
	
	

}
