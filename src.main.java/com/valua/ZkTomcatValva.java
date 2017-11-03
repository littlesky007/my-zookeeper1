package com.valua;

/*import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;*/


public class ZkTomcatValva/* extends ValveBase*/{
//	private CuratorFramework curator;
//	private final static String path = "/zk-tomcat/ActiveLock";
//	@Override
//	public void invoke(Request request, Response response) throws IOException, ServletException {
//		curator = CuratorFrameworkFactory.builder().connectString("47.94.229.142:2181")
//				.connectionTimeoutMs(1000).retryPolicy(new ExponentialBackoffRetry(1000,3))
//				.build();
//		curator.start();
//		try {
//			createNode(path);
//		} catch (Exception e) {
//			System.out.println("抢节点失败，监听节点");
//			try {
//				addListener();
//			} catch (Exception e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//		}
//	}
//	
//	//创建节点
//	private void createNode(String path) throws Exception
//	{
//		curator.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
//		System.out.println("创建节点........");
//	}
//	
//	//监听节点
//	private void addListener() throws Exception
//	{
//		
//		TreeCache treeCache = new TreeCache(curator, path);
//		treeCache.start();
//		treeCache.getListenable().addListener(new TreeCacheListener() {
//			
//			@Override
//			public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
//				if(client.getData() != null && event.getType() == TreeCacheEvent.Type.NODE_REMOVED)
//				{
//					createNode(path);
//					System.out.println("上个节点挂了，赶紧强着去创建");
//				}
//			}
//			
//		});
//		System.out.println("监听事件。。。。。。。。。");
//	}
//	
//	public static void main(String[] args) throws IOException, ServletException {
//		new ZkTomcatValva().invoke(null, null);
//	}
}
