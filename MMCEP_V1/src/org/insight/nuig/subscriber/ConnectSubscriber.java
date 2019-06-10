/**
 * 
 */
package org.insight.nuig.subscriber;

import org.insight.nuig.subscriber.messageobject.Subscription;
/**
 * @author Asra
 *
 */
public class ConnectSubscriber implements Runnable {

	/**
	 * 
	 */
	private String Subscriber_ID;
	private int max_subscriptions;
	private String Subscriptions_Path;
	private String Subscription;
	
	public ConnectSubscriber(String Subscriber_ID, int max_subscriptions, String Subscriptions_Path) {
		// TODO Auto-generated constructor stub
		super();
		this.Subscriber_ID=Subscriber_ID;
		this.max_subscriptions=max_subscriptions;
		this.Subscriptions_Path=Subscriptions_Path;
	}
	//-------------code to configure subscriber ends-------------------
	//-------------code to run the Subscriber thread starts-------------------
	 public void run()
	    {
	        try
	        {
	        	for (int i = 0; i <= max_subscriptions; i++)
		        {
	        	long start_subscription = System.currentTimeMillis(); // get present system time-------
	        	
	            //System.out.println(Subscriber_ID + " is running... for his/her Subscription Number: "+i);
	            Generate_Subscription gen_sub_obj = new Generate_Subscription(max_subscriptions, Subscriptions_Path);
	            
	            setSubscription(gen_sub_obj.Random_Subscription);
	            //System.out.println("and Generated Subscription is: " + Subscription);
	            
	            
	            Subscription sub = new Subscription(Subscriber_ID, start_subscription, Subscription);
	            //System.out.println(sub.getSubscriber_ID() + " is running... for his/her Subscription Number: "+i+" and Generated Subscription is: " + sub.getPayload_sub());
	            System.out.println(sub.getSubscriber_ID() + " is subscribing.. and Generated Subscription is: " + sub.getPayload_sub());
	            Thread.sleep(1000); // sleep the process for that value
		        }
	        }
	        catch (InterruptedException e)
	        {
	            e.printStackTrace();
	        }
	    }
	//-------------code to run the subscriber thread ends---------------------
	public String getSubscription() {
		return Subscription;
	}
	public void setSubscription(String subscription) {
		Subscription = subscription;
	}
	

}
