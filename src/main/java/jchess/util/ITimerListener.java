package jchess.util;

public interface ITimerListener {
	public void onSecondElapsed(int nRemainingSeconds);
	public void onTimerEnds();
}
