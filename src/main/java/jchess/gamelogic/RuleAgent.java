package jchess.gamelogic;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import jchess.cache.RuleData;
import jchess.common.IRule;
import jchess.common.IRuleAgent;
import jchess.common.IRuleData;
import jchess.common.enumerator.Direction;
import jchess.common.enumerator.Family;
import jchess.common.enumerator.File;
import jchess.common.enumerator.Manoeuvre;
import jchess.common.enumerator.Rank;
import jchess.common.enumerator.RuleType;

/**
 * This class is responsible to manage underlying "Rule" (only) related data.
 * It keeps the state of the Rule and ensures validity and expiry of the Rule.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public class RuleAgent implements IRuleAgent {
	private int m_nAge;
	private IRuleData m_oRule;
	private int m_nRepeatCount;
	private Queue<IRuleAgent> m_qRules;
	
	RuleAgent() {
		m_nAge = 0;
		m_nRepeatCount = 0;
		m_oRule = new RuleData();	
		m_qRules = new LinkedList<IRuleAgent>();
	}
	
	RuleAgent(RuleAgent oRuleAgent) {
		m_nAge = oRuleAgent.m_nAge;
		m_nRepeatCount = oRuleAgent.m_nRepeatCount;
		m_oRule = new RuleData( (RuleData)oRuleAgent.m_oRule);		
		m_qRules = new LinkedList<IRuleAgent>(oRuleAgent.m_qRules);
		
		// TODO: I tired HashMap's clone, but it does not call Object's copy constructor.
		// For the time being I am manually copying all the objects. Need to do it proper way to do deep copy.
		m_qRules = new LinkedList<IRuleAgent>();
		for(IRuleAgent oRule: oRuleAgent.m_qRules) {
			m_qRules.add(oRule.clone());
		}
	}

	// region: Implements IRule
	public String getName() {
		return m_oRule.getName();
	}
	
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
	
	public List<IRule> getAllRules(){
		return m_oRule.getAllRules();
	}
	
	public IRuleData getRuleData() {
		return m_oRule.getRuleData();
	}
	//endregion
	
	//region: IRuleAgent Implementation
	public IRuleAgent getNextRule() {
		if( m_oRule.getMaxRecurrenceCount() > 0 && ++m_nRepeatCount < m_oRule.getMaxRecurrenceCount()) {
			return this;
		}
		
		if( m_qRules.size() <= 0)
			return null;

		IRule oRule = m_qRules.remove(); 
		if( oRule != null) {
			return (RuleAgent)(oRule);
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
		
		Iterator<IRule> it = m_oRule.getAllRules().iterator();
    	while( it.hasNext()) {
    		m_qRules.add((IRuleAgent)it.next());
    	}
	}
		
	@Override
	public IRuleAgent clone() {
		return new RuleAgent(this);
	}
	
	public String getCustomName() {
		return m_oRule.getCustomName();
	}
//endregion

	public int getLifespan() {
		return m_oRule.getLifespan();
	}
	
	public void markUsage() {
		m_nAge++;
	}
	
	public Boolean isAlive() {
		return m_nAge < m_oRule.getLifespan();
	}
}
