package jchess.ruleengine;

import jchess.common.IPositionAgent;
import jchess.common.IRuleAgent;

public class RuleProcessorData {
	public IRuleAgent m_oRule;
	public IPositionAgent m_oLastPosition;
	public IPositionAgent m_oCurrentPosition;
	
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
