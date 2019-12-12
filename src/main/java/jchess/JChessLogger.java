package jchess;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JChessLogger {
	
	static JChessLogger object = new JChessLogger();
	protected final static Logger logger = LogManager.getLogger(JChessLogger.class);
	private JChessLogger() {
		
	}
	
	public static JChessLogger getInstance(){           
		return  object;
    }
		
	public void log(level logLevel, String message) {
		if (logLevel == level.INFO) logger.info(message);;
		if (logLevel == level.ERROR) logger.error(message);;
	}
}
