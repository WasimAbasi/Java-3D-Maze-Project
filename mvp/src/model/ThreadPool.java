package model;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <h1>ThreadPool</h1> The ThreadPool class implements Callable<String> and is
 * creating our parralel threadpool
 * 
 * @author Waism, Roaa
 */
public abstract class ThreadPool implements Callable<String> {
	public final static ExecutorService executor = Executors.newFixedThreadPool(7);
}// newCachedThreadPool();
