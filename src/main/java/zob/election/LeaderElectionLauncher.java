/**
 * 
 */
package zob.election;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Sain Technology Solutions
 *
 */
public class LeaderElectionLauncher {
	
	private static final Logger LOG = Logger.getLogger(LeaderElectionLauncher.class);
	
	public static void main(String[] args) throws IOException {
		
		if(args.length < 2) {
			System.err.println("Usage: java -jar <jar_file_name> <process id integer> <zkhost:port pairs>");
			System.exit(2);
		}
		
		final int id = Integer.valueOf(args[0]);
		final String zkURL = args[1];
		
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