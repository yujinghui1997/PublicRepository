package com.shixun;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.hibernate.validator.internal.util.privilegedactions.NewInstance;

public class TestZookeeper {
	
	private Stat stat = new Stat();

	public static void main(String[] args) {
		try {
			ZooKeeper zooKeeper = new ZooKeeper("localhost:2181", 1000*10,null);
			//获取节点数据
			//byte [] resultBytes =  zooKeeper.getData("/zkPro", false, stat);
			//删除节点
			//zooKeeper.delete("/yjh0000000006", 0);
			
			
			//CreateMode.EPHEMERAL 临时节点
			//CreateMode.PERSISTENT 持久节点
			//String path1 =  zooKeeper.create("/yjh", "18733553312".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			//CreateMode.PERSISTENT_SEQUENTIAL 持久顺序
			//String path2 =  zooKeeper.create("/yjh", "18733553312".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
			//System.out.println(path2);
			zooKeeper.create("/yjh/test", "哈哈".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
