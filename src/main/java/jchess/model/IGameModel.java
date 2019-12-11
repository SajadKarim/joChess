package jchess.model;

import jchess.common.IBoardAgent;
import jchess.common.IPlayer;

/**
 * 
 * @author 	Sajad Karim
 * @since	7 Dec 2019
 */

public interface IGameModel extends IModel  {
	public IBoardAgent getBoard();	
	public void setBoard(IBoardAgent oBoard);	
	public String getClockText();
	public void setClockText(String stClockText);
	public IPlayer getPlayer();
	public void setPlayer(IPlayer oPlayer);
}
