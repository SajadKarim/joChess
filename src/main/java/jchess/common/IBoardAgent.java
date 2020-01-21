package jchess.common;

import java.awt.Image;
import java.util.Map;

/**
 * IBoardAgent provides interface for the module where the
 * all the decisions (game) are taken. It ensures that user
 * does not make any changes to underlying data object.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public interface IBoardAgent extends IBoard {
	public Image getBoardImage();
	public Image getActivCellImage();
	public Image getMarkedCellImage();
	
	public IPositionAgent getPositionAgent(String stName);
	public Map<String, IPositionAgent> getAllPositionAgents();
	
	public IPlayerAgent getPlayerAgent(String stName);
	public Map<String, IPlayerAgent> getAllPlayerAgents();
	
	public IRuleAgent getRuleAgent(String stName);
	public  Map<String, IRuleAgent>  getAllRuleAgents();
	
	public IPieceAgent getUnlinkedPieceAgent(String stName);
	public  Map<String, IPieceAgent>  getAllUnlinkedPieceAgents();
	
	public void addActivity(IBoardActivity oActivity);
	public IBoardActivity undoLastActivity();
	public IBoardActivity redoLastActivity();

	public IBoardActivity getLastActivityByPlayer(IPlayerAgent oPlayer);
}
