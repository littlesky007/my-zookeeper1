package com.zkclient;

import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
/**
 * 
 * @author littlesky
 *
 */
public class NodeListener {
	public static void main(String[] args) throws InterruptedException {
		String path = "/zk-client";
		ZkClient zkClient = new ZkClient("47.94.229.142:2181",5000);
		zkClient.subscribeChildChanges(path, new IZkChildListener() {
			
			@Override
			public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
				
				System.out.println(parentPath+"的子节点发生变化："+currentChilds);
			}
		});
		
		
		zkClient.createPersistent(path,true);
		Thread.currentThread().sleep(1000);
		System.out.println(zkClient.getChildren(path));
	
		zkClient.createPersistent(path+"/c1",true);
		Thread.currentThread().sleep(1000);
		System.out.println(zkClient.getChildren(path));
		
		
		zkClient.delete(path+"/c1");
		Thread.currentThread().sleep(1000);
		System.out.println(zkClient.getChildren(path));
		
		zkClient.delete(path);
		Thread.currentThread().sleep(1000);
		
	}
}
