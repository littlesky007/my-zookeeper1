package com.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * 
 * @author littlesky
 *
 */
public class CreateSessionDemo {
	public static void main(String[] args) throws Exception {
		String path = "/zk-curator/ll";
		RetryPolicy policy = new ExponentialBackoffRetry(1000,3);
		CuratorFramework client = CuratorFrameworkFactory.builder().connectString("47.94.229.142:2181").
				sessionTimeoutMs(5000).retryPolicy(policy).build();
		client.start();
		
		client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path, "test".getBytes());
	}
}
