package com.shixun;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class MyZookeeper implements Watcher {

	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
	private static ZooKeeper zk = null;
	private static Stat stat = new Stat();

	@Override
	public void process(WatchedEvent event) {
		// TODO Auto-generated method stub
		if (KeeperState.SyncConnected == event.getState()) { // zk连接成功通知事件
			if (EventType.None == event.getType() && null == event.getPath()) {
				connectedSemaphore.countDown();
			} else if (event.getType() == EventType.NodeDataChanged) { // zk目录节点数据变化通知事件
				try {
					System.out.println("配置已修改，新值为：" + new String(zk.getData(event.getPath(), true, stat)));
				} catch (Exception e) {
				}
			}
		}
	}

	public static void main(String[] args) {
		try {
			// zookeeper配置数据存放路径
			String path = "/";
			// 连接zookeeper并且注册一个默认的监听器
			zk = new ZooKeeper(":2181", 5000, //
					new MyZookeeper());
			// 等待zk连接成功的通知
			connectedSemaphore.await();
			System.out.println(new String(zk.getData(path, true, stat)));
			Thread.sleep(Integer.MAX_VALUE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 获取path目录节点的配置数据，并注册默认的监听器

	}

}
