package jchess.util;

/**
 * 
 * @author 	Sajad Karim
 * @since	7 Dec 2019
 */

public interface ITimer extends Runnable {
    public void start(int nTimerLengthInSeconds, int nTimerRecurrenceCount, int nPauseTimerBeforeNextRecurrence, Boolean bNotifyEverySecond, Boolean bNotifyWhenTimerEnds);
    public void addListener(final ITimerListener listener);
	public void stop();
}
