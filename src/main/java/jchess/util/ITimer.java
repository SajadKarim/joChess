package jchess.util;

/**
 * 
 * @author 	Sajad Karim
 * @since	7 Dec 2019
 */

public interface ITimer {
    public void start(int nTimerRemainingSeconds);
    public void addListener(final ITimerListener listener);
	public void stop();
    public int getTimerRemainingSeconds();
}
