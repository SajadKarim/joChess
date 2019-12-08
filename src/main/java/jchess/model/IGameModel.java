package jchess.model;

import jchess.common.IBoardAgent;
import jchess.common.IPlayer;

public interface IGameModel {
	public IBoardAgent getBoard();	
	public void setBoard(IBoardAgent oBoard);	
	public String getClockText();
	public void setClockText(String stClockText);
	public IPlayer getPlayer();
	public void setPlayer(IPlayer oPlayer);
}
