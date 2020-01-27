package jchess.common;

import java.util.Map;

/**
 * This interface ensures the required activity is generated and stored against a move.
 * 
 * @author	Sajad Karim
 * @since	29th Dec 2019
 */

public interface IBoardActivity {
	public IPlayerAgent getPlayer();
	public void setPlayer(IPlayerAgent oPlayer);
	public IMoveCandidate getMoveCandidate();
	public Map<IPositionAgent, IPieceAgent> getPriorMoveDetails();
	public void addPriorMoveEntry(IPositionAgent oPosition, IPieceAgent oPiece);
	public Map<IPositionAgent, IPieceAgent> getPostMoveDetails();
	public void addPostMoveEntry(IPositionAgent oPosition, IPieceAgent oPiece);
	public String toLog();
}
