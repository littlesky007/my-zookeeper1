package com.zkclient;

import org.I0Itec.zkclient.ZkClient;

/**
 * 
 * @author littlesky
 *
 */
public class CreateNodeDemo {
	public static void main(String[] args) {
		ZkClient zkClient = new ZkClient("47.94.229.142:2181",5000);
		String path = "/zk-client/121";
		zkClient.createPersistent(path, true);
	}
}
