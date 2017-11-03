package com.btx.config;

import java.util.List;
import java.util.Properties;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ZookeeperClient {
	private CuratorFramework client;
	private TreeCache treeCache;

	private String zkServers;
	private String zkPath;
	private int sessionTimeout;
	private Properties props;
	
	
	public ZookeeperClient(String zkServers,String zkPath,int sessionTimeout) throws Exception
	{
		this.zkServers = zkServers;
		this.zkPath = zkPath;
		this.sessionTimeout = sessionTimeout;
		this.props = new Properties();
		initClient();
		getConfigData();
		addListener();
	}
	
	private void getConfigData() throws Exception {
		List<String> list = client.getChildren().forPath(zkPath);
		for(String key : list)
		{
			String value = new String(client.getData().forPath(zkPath+"/"+key));
			if (value != null && value.length() > 0) {
				props.put(key, value);
			}
			else
			{
				throw new Exception("从zk中获取的"+key+"为空");
			}
		}
		
	}

	private void initClient()
	{
		client = CuratorFrameworkFactory.builder().connectString(zkServers).sessionTimeoutMs(sessionTimeout)
				.retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
		client.start();
	}
	
	private void addListener() throws Exception
	{
		treeCache = new TreeCache(client, zkPath);
		treeCache.getListenable().addListener(new TreeCacheListener() {
			
			@Override
			public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
				if (event.getType() == TreeCacheEvent.Type.NODE_UPDATED) {
					getConfigData();
					WebApplicationContext wc = ContextLoader.getCurrentWebApplicationContext();
					ComboPooledDataSource dataSource = (ComboPooledDataSource) wc.getBean("dataSource");
					System.out.println("================"+props.getProperty("url")+"============="+props.getProperty("password"));
					dataSource.setJdbcUrl(props.getProperty("url"));
//					dataSource.setDriverClassName((props.getProperty("driver")));
//					dataSource.setUsername(props.getProperty("username"));
					dataSource.setUser(props.getProperty("user"));
					dataSource.setPassword(props.getProperty("password"));
				}
				
			}
		});
		treeCache.start();
	}

	public String getZkServers() {
		return zkServers;
	}

	public void setZkServers(String zkServers) {
		this.zkServers = zkServers;
	}

	public String getZkPath() {
		return zkPath;
	}

	public void setZkPath(String zkPath) {
		this.zkPath = zkPath;
	}

	public Properties getProps() {
		return props;
	}

	public void setProps(Properties props) {
		this.props = props;
	}
	
}
