package com.curator;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * 
 * @author littlesky
 *
 */
public class CreateNodeAsyncDemo {
	static CountDownLatch cdl = new CountDownLatch(2);
	static ExecutorService es = Executors.newFixedThreadPool(2);
	public static void main(String[] args) throws Exception {
		String path = "/zk-curator";
		CuratorFramework client = CuratorFrameworkFactory.builder().connectString("47.94.229.142:2181").
				sessionTimeoutMs(5000).retryPolicy(new ExponentialBackoffRetry(1000,3)).build();
		client.start();

		//“Ï≤Ω
		client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).inBackground(new BackgroundCallback() {

			@Override
			public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
				System.out.println("event code:" + event.getResultCode() +", tyep :"+ event.getType());
				cdl.countDown();
			}
		},es).forPath(path,"test2".getBytes());


		client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).inBackground(new BackgroundCallback() {

			@Override
			public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
				System.out.println("event code:" + event.getResultCode() +", tyep :"+ event.getType());
				cdl.countDown();
			}
		}).forPath(path,"tes2t".getBytes());
		
		
		cdl.await();
		es.shutdown();
	}
}
