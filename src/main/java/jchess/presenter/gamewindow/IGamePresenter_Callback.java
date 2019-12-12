package jchess.presenter.gamewindow;

import jchess.common.IPlayer;

/**
 * This facilitates linked classes to call GamePersenter methods.
 * 
 * @author 	Sajad Karim
 * @since	7 Dec 2019
 */

public interface IGamePresenter_Callback {
	public void onTimerUpdate_SecondsElapsed(int nRemainingSeconds);
	public void onTimerUpdate_TimerElapsed(IPlayer oPlayer);
	public void updateCurrentPlayer(IPlayer oPlayer);
}
