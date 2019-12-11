package jchess.util;

/**
 * This interface faciliates call back functionality.
 * 
 * @author 	Sajad Karim
 * @since	7 Dec 2019
 */

public interface ITimerListener {
	public void onSecondElapsed(int nRemainingSeconds);
	public void onTimerElapsed();
}
