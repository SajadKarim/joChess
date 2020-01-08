package jchess.gui.model.gamewindow;

import java.util.Map;

import jchess.common.IBoardActivity;
import jchess.common.IBoardAgent;
import jchess.common.IPlayer;
import jchess.common.IPlayerAgent;
import jchess.common.gui.IModel;

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
	public void setPlayer(IPlayerAgent oPlayer);
	public Map<String, IPlayerAgent > getAllPlayerAgents();
	public String getRuleEngineName();

	public void addBoardActivity(IBoardActivity oActivity);
	public IBoardActivity tryUndoBoardActivity();
	public IBoardActivity tryRedoBoardActivity();
	
	public void updatePlayerNames(Map<String, IPlayerAgent> mpPlayer);		
}
