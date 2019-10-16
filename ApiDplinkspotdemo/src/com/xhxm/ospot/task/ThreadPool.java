package com.xhxm.ospot.task;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;

public class ThreadPool {

	private static ThreadPool threadPool;

	public static ThreadPool getThreadPool() {
		if (threadPool == null) {
			threadPool = new ThreadPool();
		}
		return threadPool;
	}

	public static ThreadPoolExecutor executor = null;

	public static final int corePoolSize = 1;
	public static final int maximumPoolSize = 10;
	public static final int keepAliveTime = 180;// 180

	public ThreadPool() {
		super();
	}

	/**
	 * 线程池：就是把一堆线程放在一起来管理。 1.通过一定的管理机制。来处理线程额执行顺序 2.管理最多可以同时执行的线程数。
	 * 3.其他线程通过队列的形式，也就是排队的形式来管理线程的并发数。
	 * 
	 * @param runnable
	 */
	public void execute(Runnable runnable) {
		if (runnable == null) {
			return;
		}

		if (executor == null) {
			executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,
					TimeUnit.SECONDS,// 时间单位
					new LinkedBlockingQueue<Runnable>(),// 线程队列
					Executors.defaultThreadFactory(),// 线程工厂
					new AbortPolicy());
		}
		// 给线程池里面添加一个线程
		executor.execute(runnable);
	}

}
