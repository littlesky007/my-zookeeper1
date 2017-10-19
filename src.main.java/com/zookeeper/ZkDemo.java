package com.zookeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

public class ZkDemo implements Watcher{
	private static final CountDownLatch cdl = new CountDownLatch(1);
	
	public static void main(String[] args) throws IOException, InterruptedException {
		ZooKeeper zk = new ZooKeeper("xxxxx",5000,new ZkDemo());
		System.out.println(zk.getState());
		
		cdl.await();
	}
	@Override
	public void process(WatchedEvent event) {
		System.out.println(event);
		if(event.getState() == KeeperState.SyncConnected)
		{
			cdl.countDown();
		}
		
	}

}
