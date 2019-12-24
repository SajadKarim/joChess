package jchess.gamelogic;

import jchess.common.IBoardAgent;
import jchess.common.IPlayerAgent;
import jchess.common.IPositionAgent;

public interface IGame {
	public void init();
	public void addListener(IGameListener oListerner);
	public void onBoardActivity(IPositionAgent oPosition);
	public void tryUndoMove(IPlayerAgent oPlayer);
	public void tryRedoMove(IPlayerAgent oPlayer);
}
