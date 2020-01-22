package jchess.gamelogic;

import java.util.List;

import jchess.common.IMoveCandidate;
import jchess.common.IPieceAgent;
import jchess.common.IPositionAgent;
import jchess.common.IRuleAgent;

/**
 * This is a data class that stores move information that a Piece can make.
 * 
 * @author	Sajad Karim
 * @since	29th Dec 2019
 */

public final class MoveCandidate implements IMoveCandidate {
	private IPieceAgent m_oPieceToMove;
	private IPositionAgent m_oSourcePosition;
	private IPositionAgent m_oCandidatePosition;
	private IRuleAgent m_oRule;
	private List<IMoveCandidate> m_lstSecondaryMoves;

	public MoveCandidate(IRuleAgent oRule, IPieceAgent oPieceToMove, IPositionAgent oSourcePosition, IPositionAgent oCandidatePosition) {
		m_oRule = oRule;
		m_oPieceToMove = oPieceToMove;
		m_oSourcePosition = oSourcePosition;
		m_oCandidatePosition = oCandidatePosition;
	}

	public IRuleAgent getRule() {
		return m_oRule;
	}
	
	public IPieceAgent getPieceToMove() {
		return m_oPieceToMove;
	}

	public IPositionAgent getSourcePosition() {
		return m_oSourcePosition;
	}

	public IPositionAgent getCandidatePosition() {
		return m_oCandidatePosition;
	}	
	
	public String toLog() {
		return String.format("Piece=%s, SourcePosition=%s, DestinationPosition=%s, RuleName=%s"
				, m_oPieceToMove.getName()
				, m_oSourcePosition.getName()
				, m_oCandidatePosition.getName()
				, m_oRule.getName());
	}

	public List<IMoveCandidate> getSecondaryMoves() {
		return m_lstSecondaryMoves;
	}

	public void addSecondaryMove(IMoveCandidate secondaryMove) {
		this.m_lstSecondaryMoves.add(secondaryMove);
	}
}
