/**
 * 
 */
package org.insight.nuig.engine.messageobject;

import java.util.List;

/**
 * @author piyush
 * @date 25 Jul 2018
 * @time 13:22:00
 * @Institute Insight Centre for Data Analytics- NUIG
 * @Project: This file is part of the MMPS project owned by Sunstainable IT Group NUIG
 
 * @ClassDefinition
 
 */
public class WindowAssignObject {
	
	int num_windows;
	List<GenericWindowObject> winobj;
	/**
	 * @param num_windows
	 * @param winobj
	 */
	public WindowAssignObject(int num_windows, List<GenericWindowObject> winobj) {
		super();
		this.num_windows = num_windows;
		this.winobj = winobj;
	}
	
	
	/**
	 * 
	 */
	public WindowAssignObject() {
		super();
		// TODO Auto-generated constructor stub
	}


	/**
	 * @return the num_windows
	 */
	public int getNum_windows() {
		return num_windows;
	}
	/**
	 * @param num_windows the num_windows to set
	 */
	public void setNum_windows(int num_windows) {
		this.num_windows = num_windows;
	}
	/**
	 * @return the winobj
	 */
	public List<GenericWindowObject> getWinobj() {
		return winobj;
	}
	/**
	 * @param winobj the winobj to set
	 */
	public void setWinobj(List<GenericWindowObject> winobj) {
		this.winobj = winobj;
	}
	
	
	

}
