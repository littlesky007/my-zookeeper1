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

	private volicite boolean flage=false;
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
				System.out.println(dataPath+"节点已经释放锁了");
				if(cdl != null) {
					cdl.countDown();
					//标记现有线程释放锁了。。。。
					flage=true;
				}
				
			}

			
		};
		this.client.subscribeDataChanges(beforePath, listener);
		//如果前面有节点存在，就等待
		if (this.client.exists(beforePath)) {
			cdl = new CountDownLatch(1);
			try {
				//如果先在阻塞前，有线程释放了锁，这里就不阻塞了。。。
				if(!falge)
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
			//让当前节点放在child的第一位置
			lock();
		}
		else
		{
			System.out.println("获取锁");
		}

	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean tryLock() {
		//如果当前路径不存在节点，就创建顺序节点
		if(currentPath == null)
		{
			currentPath = client.createEphemeralSequential(LOCK_PATH+SPLIT, "lock");
		}
		List<String> childPath = client.getChildren(LOCK_PATH);
		Collections.sort(childPath);

		//如果当节点是第一个节点，就获取锁
		if(currentPath.equals(LOCK_PATH+SPLIT + childPath.get(0))) {
			return true;
		}
		//不然就指向前面一个节点
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
