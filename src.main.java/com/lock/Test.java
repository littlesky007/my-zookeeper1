package com.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {
	public static void main(String[] args) {
		ExecutorService service = Executors.newFixedThreadPool(2);
		service.submit(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println(Thread.currentThread().getName());
				ImproveLock improveLock = new ImproveLock("47.94.229.142:2181", null);
				System.out.println(improveLock.tryLock());
				
			}

		});

		service.submit(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println(Thread.currentThread().getName());
				ImproveLock improveLock = new ImproveLock("47.94.229.142:2181", null);
				System.out.println(improveLock.tryLock());
			}

		});
		
		service.shutdown();
	}
}
