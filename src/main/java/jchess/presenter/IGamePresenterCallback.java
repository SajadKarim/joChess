package jchess.presenter;

import jchess.common.IPlayer;

public interface IGamePresenterCallback {
	public void onTimerUpdate_SecondsElapsed(int nRemainingSeconds);
	public void onTimerUpdate_TimerElapsed(IPlayer oPlayer);
	public void updateCurrentPlayer(IPlayer oPlayer);
}
