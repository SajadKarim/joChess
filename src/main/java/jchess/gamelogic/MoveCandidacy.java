package jchess.gamelogic;

import jchess.common.IMoveCandidacy;
import jchess.common.IPositionAgent;
import jchess.common.IRuleAgent;

/**
 * This class holds the information regarding a candidate move.
 * It helps Game in displaying data regarding move candidates.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public class MoveCandidacy implements IMoveCandidacy {
	private IPositionAgent m_oSourcePosition;
	private IPositionAgent m_oCandidatePosition;
	private IRuleAgent m_oRule;

	public MoveCandidacy(IPositionAgent oSourcePosition, IPositionAgent oCandidatePosition, IRuleAgent oRule) {
		m_oSourcePosition = oSourcePosition;
		m_oCandidatePosition = oCandidatePosition;
		m_oRule = oRule;
	}
	
	public IPositionAgent getSourcePosition() {
		return m_oSourcePosition;
	}

	public void setSourcePosition(IPositionAgent oPosition) {
		m_oSourcePosition = oPosition;
	}

	public IPositionAgent getCandidatePosition() {
		return m_oCandidatePosition;
	}
	
	public IRuleAgent getRule() {
		return m_oRule;
	}
	
	public String toLog() {
		return String.format("SourcePosition=%s, DestinationPosition=%s, RuleType=%s"
				, m_oSourcePosition.getName()
				, m_oCandidatePosition.getName()
				, m_oRule.getRuleType().toString());
	}
}
