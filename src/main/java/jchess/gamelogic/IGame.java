package jchess.gamelogic;

import jchess.common.IPlayerAgent;
import jchess.common.IPositionAgent;

public interface IGame {
	public void init();
	public void addListener(IGameListener oListerner);
	public void onBoardActivity(IPositionAgent oPosition);
	public void setPlayerAsActivePlayer(IPlayerAgent oPlayer);
	public void setPlayerTurnAsLast(IPlayerAgent oPlayer);
}
