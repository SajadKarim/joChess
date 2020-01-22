package jchess.common;

import java.util.List;

/**
 * IMoveCandidate
 * 
 * @author	Sajad Karim
 * @since	29th Dec 2019
 */

public interface IMoveCandidate {
	public IRuleAgent getRule();
	public IPieceAgent getPieceToMove();
	public IPositionAgent getSourcePosition();
	public IPositionAgent getCandidatePosition();
	
	public List<IMoveCandidate> getSecondaryMoves();
	public void addSecondaryMove(IMoveCandidate secondaryMove);
	
	public String toLog();
}
