package jchess.gamelogic;

import jchess.common.IBoardAgent;
import jchess.common.IPositionAgent;

public interface IGame {
	public void init(IBoardAgent oBoard);
	public void addListener(IGameListener oListerner);
	public void onBoardActivity(IPositionAgent oPosition);
}
