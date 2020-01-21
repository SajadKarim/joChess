package jchess.util;
/**
 * This class is responsible to write log using log4j.
 * log4j is wrapped in this class and utilized.
 * 
 * @author	Savio jojo
 * @since	6 Dec 2019
 */
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public final class AppLogger implements IAppLogger {
	private final Logger m_oLogger;

	@Inject
	public AppLogger() {
		m_oLogger = LogManager.getLogger(AppLogger.class);
	}
	
	public void writeLog(LogLevel enLogLevel, Exception oException, String stMethodName, String stClassName) {
		String stText = String.format("[%s::%s] %s", stMethodName, stClassName, oException.getMessage());
		writeLog(enLogLevel, stText);
	}

	public void writeLog(LogLevel enLogLevel, String stMessage, String stMethodName, String stClassName) {
		String stText = String.format("[%s::%s] %s", stMethodName, stClassName, stMessage);
		writeLog(enLogLevel, stText);
	}

	public void writeLog(LogLevel enLogLevel, String stMessage) {
		switch(enLogLevel) {
			case INFO:
				m_oLogger.info(stMessage);
				break;
			case ERROR:
				m_oLogger.error(stMessage);
				break;
			case DETAILED:
				m_oLogger.info(stMessage);
				break;
			default:
				break;
		}
	}
}