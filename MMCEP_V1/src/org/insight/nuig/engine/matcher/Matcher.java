/**
 * 
 */
package org.insight.nuig.engine.matcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.insight.nuig.configurator.ConfigurationParameters;
import org.insight.nuig.engine.messageobject.StateObject;
import org.insight.nuig.engine.querymodel.QueryBuilder;

/**
 * @author piyush
 * @date 27 Aug 2018
 * @time 14:49:15
 * @Institute Insight Centre for Data Analytics- NUIG
 * @Project: This file is part of the MMPS project owned by Sunstainable IT
 *           Group NUIG
 * 
 * @ClassDefinition
 * 
 */
public class Matcher {

	public static void runmatcher() {

		List<String> keyset = new ArrayList<String>(CreateQueryStateStore.querystatestore.keySet());

		int query_store_size = CreateQueryStateStore.querystatestore.size();
		ThreadPoolExecutor executor = new ThreadPoolExecutor(query_store_size,
				query_store_size + 3, 100, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(), new ThreadPoolExecutor.CallerRunsPolicy());
		//ExecutorService executor = Executors.newFixedThreadPool(query_store_size);


		for (int i = 0; i < query_store_size; i++) {
			ExecuteMatcher matcher = new ExecuteMatcher(keyset.get(i));
			executor.execute(matcher);

		}

		executor.shutdown();
		try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
