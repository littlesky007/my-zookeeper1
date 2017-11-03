package com.btx.config;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;


public class ZookeeperPropertiesholderConfigure extends PropertyPlaceholderConfigurer{
	
	private ZookeeperClient zookeeperClient;
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
			throws BeansException {
		super.processProperties(beanFactoryToProcess, zookeeperClient.getProps());
	}
	public ZookeeperClient getZookeeperClient() {
		return zookeeperClient;
	}
	public void setZookeeperClient(ZookeeperClient zookeeperClient) {
		this.zookeeperClient = zookeeperClient;
	}

	
}
