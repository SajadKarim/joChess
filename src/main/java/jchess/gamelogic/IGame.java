package jchess.gamelogic;

import jchess.common.IPlayerAgent;
import jchess.common.IPositionAgent;

/**
 * This provides abstraction to the Game class.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public interface IGame {
	public void init();
	public void addListener(IGameListener oListerner);
	public void onBoardActivity(IPositionAgent oPosition);
	public void tryUndoMove(IPlayerAgent oPlayer);
	public void tryRedoMove(IPlayerAgent oPlayer);
}
