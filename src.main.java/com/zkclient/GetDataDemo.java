package com.zkclient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

/**
 * 
 * @author littlesky
 *
 */
public class GetDataDemo {
	public static void main(String[] args) throws InterruptedException {
		ZkClient zkClient = new ZkClient("47.94.229.142:2181",5000);
		String path="/zk-client";
		zkClient.createEphemeral(path, "123");
		
		zkClient.subscribeDataChanges(path, new IZkDataListener() {

			@Override
			public void handleDataChange(String dataPath, Object data) throws Exception {
				System.out.println(dataPath+" changed: "+data);
				
			}

			@Override
			public void handleDataDeleted(String dataPath) throws Exception {
				System.out.println(dataPath+" deleted");
				
			}
			
		});
		
		System.out.println(zkClient.readData(path).toString());
		zkClient.writeData(path, "456");
		Thread.sleep(1000);
		zkClient.delete(path);
		Thread.sleep(Integer.MAX_VALUE);
	}
}
