package jchess;
/**
 * This a singleton class for creating single instance.
 * log4j is wrapped in this class and utilized.
 * 
 * @author	Savio jojo
 * @since	6 Dec 2019
 */
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jchess.common.enumerator.LogLevel;

public class JChessLogger {
	
	static JChessLogger m_oInstance;
	private final Logger m_oLogger;
	private JChessLogger() {
		m_oLogger = LogManager.getLogger(JChessLogger.class);
	}
	
	public static JChessLogger getInstance(){ 
		if(m_oInstance!=null) {
			m_oInstance = new JChessLogger();
		}
		return  m_oInstance;
    }
	
	public void writeLog(LogLevel m_enLogLevel, String message) {
		switch(m_enLogLevel) {
		case INFO:
			m_oLogger.info(message);
		case ERROR:
			m_oLogger.error(message);
		}
	}
}