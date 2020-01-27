package jchess.ruleengine;

import jchess.common.IPositionAgent;
import jchess.common.IRuleAgent;

/**
 * 
 * @author 	Sajad Karim
 * @since	7 Dec 2019
 */

public final class RuleProcessorData {
	private IRuleAgent m_oRule;
	private IPositionAgent m_oLastPosition;
	private IPositionAgent m_oCurrentPosition;
	
	public RuleProcessorData(IRuleAgent oRule, IPositionAgent oCurrentPosition, IPositionAgent oLastPosition) {
		m_oRule = oRule;
		m_oCurrentPosition = oCurrentPosition;
		m_oLastPosition = oLastPosition;
	}
	
	public IRuleAgent getRule() {
		return m_oRule;
	}
	
	public IPositionAgent getCurrentPosition() {
		return m_oCurrentPosition;
	}
	
	public IPositionAgent getLastPosition() {
		return m_oLastPosition;
	}
}
