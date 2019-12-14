package jchess.util;

public interface IAppLogger {
	public void writeLog(LogLevel enLogLevel, Exception oException, String stMethodName, String stClassName);
	public void writeLog(LogLevel enLogLevel, String stMessage, String stMethodName, String stClassName);
	public void writeLog(LogLevel enLogLevel, String stMessage);
}
