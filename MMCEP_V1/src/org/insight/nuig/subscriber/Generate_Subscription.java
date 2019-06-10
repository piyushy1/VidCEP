/**
 * 
 */
package org.insight.nuig.subscriber;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

/**
 * @author Asra
 *
 */
public class Generate_Subscription {

	/**
	 * 
	 */
	String Random_Subscription;
	public Generate_Subscription(int max_subscriptions, String Subscriptions_Path) 
	{
		// TODO Auto-generated constructor stub
		String classnames[] = new String[100+max_subscriptions];
		//System.out.println("Current Path Asra is " + Subscriptions_Path);
		int total_classes = 0;
		int k=0;
		//-----------------------reading possible subscription's file starts-----------------------------------
		BufferedReader br = null;
		FileReader fr = null;
		try 
		{
			fr = new FileReader(Subscriptions_Path);
			br = new BufferedReader(fr);
			String sCurrentLine;
			k=0;
			while ((sCurrentLine = br.readLine()) != null) 
			{
				//System.out.println(sCurrentLine);
				classnames[k] = ""+sCurrentLine;
				k++;
			}
			total_classes = k;

		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			try 
			{
				if (br != null)	br.close();
				if (fr != null)	fr.close();
			} 
			catch (IOException ex) 
			{
				ex.printStackTrace();
			}
		}
		//-----------------------reading possible subscription's file ends-----------------------------------------
		//-----------------------generate random subscription starts-----------------------
		int random_index;
		Random randomnum = new Random();
		random_index = randomnum.nextInt(max_subscriptions);
		Random_Subscription = ""+classnames[random_index];
		//-----------------------generate random subscription ends-------------------------
	}
}
