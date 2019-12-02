package jchess.common;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;

import jchess.common.enumerator.Direction;
import jchess.common.enumerator.Family;
import jchess.common.enumerator.File;
import jchess.common.enumerator.Manoeuvre;
import jchess.common.enumerator.Rank;
import jchess.common.enumerator.RuleType;

public class RuleAgent implements IRule {
	private Rule m_oRule;	// this should be rule
	private int m_nRepeatCount;
	private Queue<Rule> m_qRules;

	public RuleAgent(Rule oRule) {
		m_oRule = oRule;		
		m_qRules = new LinkedList<Rule>();
		
		init();
	}
	
	public RuleAgent(RuleAgent oRuleAgent) {
		m_nRepeatCount = oRuleAgent.m_nRepeatCount;
		m_oRule = new Rule(oRuleAgent.m_oRule);		
		m_qRules = new LinkedList<Rule>(oRuleAgent.m_qRules);
	}

	//region: IRule Implementation
	public RuleType getRuleType() {
		return m_oRule.getRuleType();		
	}

	public Direction getDirection() {
		return m_oRule.getDirection();		
	}
	
	public Manoeuvre getManoeuvreStrategy() {
		return m_oRule.getManoeuvreStrategy();		
	}
	
	public int getMaxRecurrenceCount() {
		return m_oRule.getMaxRecurrenceCount();		
	}
	
	public Family getFamily() {
		return m_oRule.getFamily();		
	}
	
	public File getFile() {
		return m_oRule.getFile();		
	}
	
	public Rank getRank() {
		return m_oRule.getRank();		
	}
	
	public Map<String, Rule> getAllRules(){
		return m_oRule.getAllRules();		
	}
	//endregion
	
	//region: IRuleAgent Implementation
	public RuleAgent getNextRule() {
		if( m_oRule.getMaxRecurrenceCount() > 0 && ++m_nRepeatCount < m_oRule.getMaxRecurrenceCount()) {
			return this;
		}
		
		if( m_qRules.size() <= 0)
			return null;

		Rule oRule = m_qRules.remove(); 
		if( oRule != null) {
			return new RuleAgent(oRule);
		}
		
		return null;
	}
	
	public void makeRuleDead() {
		m_nRepeatCount = m_oRule.getMaxRecurrenceCount();
		m_qRules.clear();
	}
	
	public void reset() {
		m_qRules.clear();
		
		init();
	}
	
	private void init() {
		m_nRepeatCount = 0;

		if( m_oRule.getAllRules() == null)
			return;
		
		for (Map.Entry<String, Rule> entry2 : m_oRule.getAllRules().entrySet()) {
			
		//Iterator<Rule> it = m_oRule.getAllRules().iterator();
    	//while( it.hasNext()) {
    		//m_qRules.add(it.next());
			m_qRules.add(entry2.getValue());
    	}
	}

	
	public void isValidMove( PositionAgent oPosition, AtomicReference<Boolean> bIsValidMode, AtomicReference<Boolean> bCanContinue) {
		bIsValidMode.set(false);
		bCanContinue.set(false);
		
		switch( m_oRule.getRuleType()) {
			case MOVE_TRANSIENT:
				bIsValidMode.set(false);
				bCanContinue.set(true);
				break;
			case MOVE:
				if( oPosition.getPiece() != null) {
					bIsValidMode.set(false);
					bCanContinue.set(false);
				} else {
					bIsValidMode.set(true);
					bCanContinue.set(true);
				}
				break;
			case MOVE_AND_CAPTURE:
				bIsValidMode.set(true);
				if( oPosition.getPiece() == null) {
					bCanContinue.set(true);
				}
				break;
			case MOVE_IFF_CAPTURE_POSSIBLE:
				if( oPosition.getPiece() == null) {
					bCanContinue.set(true);
				}
				else
				{
					bIsValidMode.set(true);					
					bCanContinue.set(false);
				}
				break;
		}
	}
	
	public void canContinue( IPosition oPosition) {
		
	}
	//endregion
}
