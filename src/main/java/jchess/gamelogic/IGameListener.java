package jchess.gamelogic;

import jchess.common.IBoardActivity;
import jchess.common.IPlayerAgent;

/**
 * This facilitates linked classes to call GamePersenter methods.
 * 
 * @author 	Sajad Karim
 * @since	7 Dec 2019
 */

public interface IGameListener {
	public void onTimerUpdate_SecondsElapsed(int nRemainingSeconds);
	public void onTimerUpdate_TimerElapsed(IPlayerAgent oPlayer);
	public void onCurrentPlayerChanged(IPlayerAgent oPlayer);
	public void onMoveMadeByPlayer(IPlayerAgent oPlayer, IBoardActivity oMove);	
	public void endCurrentGame();
	public void displayConfirmDialog(String stConfirmDialogMessage, String stConfirmDialogTitle);
}
