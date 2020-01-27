package jchess.common;

import java.awt.Image;
import java.util.List;

/**
 * IPieceAgent provides interface for the module where the all the decisions (game) are taken. It ensures that user
 * does not make any changes to underlying data object.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public interface IPieceAgent extends IPiece {
	public List<IRuleAgent> getRules();
	public IRule getRule(String stRuleName);
	public IPlayerAgent getPlayer();
	public void setPlayer(IPlayerAgent oPlayer);
	public int getRuns();
	public Image getImage();
	
	public IPositionAgent getPosition();
	public void setPosition(IPositionAgent oPosition);
	public String toLog();
	
	public void enqueuePositionHistory(IPositionAgent oPosition);
	public IPositionAgent dequeuePositionHistory();
	public int getPositionHistoryCount();
	
	public Object getCustomData();
	public void setCustomData(Object oCustomData);	
	public void updateImage(String stImageFileName);
}
