package zob.election;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class ZooKeeperDemo {
	
	private static final Logger LOG = Logger.getLogger(ZooKeeperDemo.class);
	
	public static void main(String[] args) throws IOException {
		
		final ZooKeeper zooKeeper = new ZooKeeper("10.0.1.4:2181", 5000, new ZooKeeperWatcher());
		
		try {
			//create a node with path "/demo", data as blank, ACL giving everyone rights on this node and create mode as Ephemeral
			final String createdNodePath = zooKeeper.create("/demo", new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

			LOG.info("Node created with path: " + createdNodePath);
			
			//Delete a node with path "/demo" and version as -1 means don't care for current version
			//zooKeeper.delete(createdNodePath, -1);
			while(true) {
				Thread.sleep(1000);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static class ZooKeeperWatcher implements Watcher {

		public void process(WatchedEvent event) {
			LOG.info("###### Event Whatched " + event);
		}
		
	}
}
