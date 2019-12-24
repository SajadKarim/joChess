package jchess.common;

import java.util.Map;

/**
 * IMove
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public interface IMove {
	public Boolean IsMoveSuccessful();
	public void setMoveSuccessState(Boolean bIsMoveSuccessful);
	//public String getMoveDisplayString();
	public void setMoveDisplayString(String stMoveDisplayString);
	public void setPlayer(IPlayerAgent oPlayer);
	public IPlayerAgent getPlayer();
	public Map<String, IPieceAgent> getPriorMoveDetails();
	public void addPriorMoveEntry(String stPositionId, IPieceAgent oPiece);
	public Map<String, IPieceAgent> getPostMoveDetails();
	public void addPostMoveEntry(String stPositionId, IPieceAgent oPiece);
	public String toLog();
}
