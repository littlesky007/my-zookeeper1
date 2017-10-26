package com.lock;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
/**
 * 
 * @author littlesky
 *
 */
public class ImproveLock implements Lock{

	private CountDownLatch cdl;
	private String beforePath;
	private String currentPath;
	private static String LOCK_PATH = "/zk-lock";
	private ZkClient client;
	private final String SPLIT ="/";

	public ImproveLock(String ipPort,String lockPath)
	{ 
		client = new ZkClient(ipPort,5000);
		if(lockPath!=null && lockPath.length()>0)
		{
			LOCK_PATH = lockPath;
		}
		if(!client.exists(LOCK_PATH))
		{
			client.createPersistent(LOCK_PATH, true);
		}
	}

	private void waitForLock() {

		IZkDataListener listener = new IZkDataListener() {

	
			@Override
			public void handleDataChange(String dataPath, Object data) throws Exception {
		
				
			}

			@Override
			public void handleDataDeleted(String dataPath) throws Exception {
				System.out.println(dataPath+"�ڵ��Ѿ��ͷ�����");
				if(cdl != null) {
					cdl.countDown();
				}
				
			}

			
		};
		this.client.subscribeDataChanges(beforePath, listener);
		//���ǰ���нڵ���ڣ��͵ȴ�
		if (this.client.exists(beforePath)) {
			cdl = new CountDownLatch(1);
			try {
				cdl.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.client.unsubscribeDataChanges(beforePath, listener);
	}

	@Override
	public void lock() {
		if(!tryLock())
		{
			waitForLock();
			//�õ�ǰ�ڵ����child�ĵ�һλ��
			lock();
		}
		else
		{
			System.out.println("��ȡ��");
		}

	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean tryLock() {
		//�����ǰ·�������ڽڵ㣬�ʹ���˳��ڵ�
		if(currentPath == null)
		{
			currentPath = client.createEphemeralSequential(LOCK_PATH+SPLIT, "lock");
		}
		List<String> childPath = client.getChildren(LOCK_PATH);
		Collections.sort(childPath);

		//������ڵ��ǵ�һ���ڵ㣬�ͻ�ȡ��
		if(currentPath.equals(LOCK_PATH+SPLIT + childPath.get(0))) {
			return true;
		}
		//��Ȼ��ָ��ǰ��һ���ڵ�
		else
		{
			int index = Collections.binarySearch(childPath, currentPath.substring(LOCK_PATH.length()+1));
			beforePath = LOCK_PATH + SPLIT + childPath.get(index-1);
		}
		return false;
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void unlock() {
		client.delete(currentPath);	
	}

	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}

}
