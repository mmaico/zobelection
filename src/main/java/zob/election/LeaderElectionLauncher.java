/**
 * 
 */
package zob.election;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

/**
 * @author Sain Technology Solutions
 *
 */
public class LeaderElectionLauncher {
	
	private static final Logger LOG = Logger.getLogger(LeaderElectionLauncher.class);
	
	private static Map<Integer, Config> configs = new HashMap<>();
	static {
		configs.put(1, new Config(1111, "172.19.32.234:2181"));
		configs.put(2, new Config(2222, "172.19.32.234:2181"));
		configs.put(3, new Config(3333, "172.19.32.234:2181"));
		configs.put(4, new Config(4444, "172.19.32.234:2181"));
		configs.put(5, new Config(5555, "172.19.32.234:2181"));
	}
	
	public static void main(String[] args) throws IOException {
		
//		if(args.length < 2) {
//			System.err.println("Usage: java -jar <jar_file_name> <process id integer> <zkhost:port pairs>");
//			System.exit(2);
//		}
		
		for(int i = 1; i <= 5; i++) {
			final int id = configs.get(i).pid;
			final String zkURL = configs.get(i).host;
			
			final ExecutorService service = Executors.newSingleThreadExecutor();
			
			final Future<?> status = service.submit(new ProcessNode(id, zkURL));
			
			try {
				status.get();
			} catch (Exception e) {
				LOG.fatal(e.getMessage(), e);
				service.shutdown();
			}
		}
	}

	public static class Config {
		private int pid;
		private String host;
		public Config(int pid, String host) {
			this.pid = pid;
			this.host = host;
		}
	}
}
