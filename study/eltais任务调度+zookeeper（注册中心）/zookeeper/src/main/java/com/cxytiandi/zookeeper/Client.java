package com.cxytiandi.zookeeper;

import java.util.List;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class Client {
	public static void main(String[] args) {
		// client.get("http://localhost/get");
		// client.get("http://localhost/get2");
		// client.get("nginx地址"); --->   get./get2
		
		// 从zk中获取服务地址列表，选择一个进行请求，本地执行负载均衡
		
		// 1 重试策略：初试时间为1s 重试10次
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 10);
		// 2 通过工厂创建连接
		CuratorFramework cf = CuratorFrameworkFactory.builder()
				.connectString("localhost:2181")
				.sessionTimeoutMs(1000 * 10)
				.retryPolicy(retryPolicy)
				.build();
		// 3 开启连接
		cf.start();
		
		try {
			final PathChildrenCache childrenCache = new PathChildrenCache(cf, "/urls", true);
	        childrenCache.start(StartMode.POST_INITIALIZED_EVENT);
	        childrenCache.getListenable().addListener(
	            new PathChildrenCacheListener() {
	                @Override
	                public void childEvent(CuratorFramework client, PathChildrenCacheEvent event)
	                        throws Exception {
	                        switch (event.getType()) {
	                        case CHILD_ADDED:
	                            System.out.println("CHILD_ADDED: " + event.getData().getPath());
	                            break;
	                        case CHILD_REMOVED:
	                            System.out.println("CHILD_REMOVED: " + event.getData().getPath());
	                            break;
	                        case CHILD_UPDATED:
	                            System.out.println("CHILD_UPDATED: " + event.getData().getPath());
	                            break;
	                        default:
	                            break;
	                    }
	                }
	            }
	        );
			List<String> urls = cf.getChildren().forPath("/urls");
			for (String url : urls) {
				System.out.println(url);
			}
			Thread.sleep(200000000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
