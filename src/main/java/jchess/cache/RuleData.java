package jchess.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jchess.common.IRule;
import jchess.common.IRuleData;
import jchess.common.enumerator.Direction;
import jchess.common.enumerator.Family;
import jchess.common.enumerator.File;
import jchess.common.enumerator.Manoeuvre;
import jchess.common.enumerator.Rank;
import jchess.common.enumerator.RuleType;

/**
 * This is a container class to store "Rule" related details in cache.
 * It actually maps XML or DB structure for "Rule" to memory.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public class RuleData implements IRuleData {
	private File m_enFile;
	private Rank m_enRank;	
	private String m_stName;
	private Family m_enFamily;
	private RuleType m_enRuleType;
	private Direction m_enDirection;
	private Manoeuvre m_enManoeuvreStratgy;
	private int	m_nMaxRecurrenceAllowed;
	private Map<String, IRule> m_mpRule;
		
	public RuleData() {
		m_mpRule = new HashMap<String, IRule>();
	}
	
	public RuleData(RuleData oRule) {
		m_stName = oRule.m_stName;
		m_enFile = oRule.m_enFile;
		m_enRank = oRule.m_enRank;
		m_mpRule = oRule.m_mpRule;
		m_enFamily = oRule.m_enFamily;
		m_enRuleType = oRule.m_enRuleType;
		m_enDirection = oRule.m_enDirection;
		m_nMaxRecurrenceAllowed = oRule.m_nMaxRecurrenceAllowed;
		m_enManoeuvreStratgy = oRule.m_enManoeuvreStratgy;
		m_mpRule = new HashMap<String, IRule>(oRule.m_mpRule);
	}
	
	// region: Implements IRule
	public String getName() {
		return m_stName;
	}
	
	public RuleType getRuleType() {
		return m_enRuleType;		
	}

	public Direction getDirection() {
		return m_enDirection;
	}
	
	public Manoeuvre getManoeuvreStrategy() {
		return m_enManoeuvreStratgy;
	}
	
	public int getMaxRecurrenceCount() {
		return m_nMaxRecurrenceAllowed;
	}

	public Family getFamily() {
		return m_enFamily;
	}
	
	public File getFile() {
		return m_enFile;
	}

	public Rank getRank() {
		return m_enRank;
	}
	
	public List<IRule> getAllRules(){
		return new ArrayList<IRule>(m_mpRule.values());
	}
	
	public IRuleData getRuleData() {
		return this;
	}
	// endregion
	
	// region: Implements IRuleData
	public void setName(String stName) {
		m_stName = stName;
	}
	
	public void setRuleType(RuleType enRuleType) {
		m_enRuleType = enRuleType;		
	}
	
	public void setDirection(Direction enDirection) {
		m_enDirection = enDirection;
	}
	
	public void setManoeuvreStrategy(Manoeuvre enManoeuvre) {
		m_enManoeuvreStratgy = enManoeuvre;
	}
	
	public void setMaxRecurrenceCount(int nMaxRecurrenceCount) {
		m_nMaxRecurrenceAllowed = nMaxRecurrenceCount;
	}
	
	public void setFamily(Family enFamily) {
		m_enFamily = enFamily;
	}

	public void setFile(File enFile) {
		m_enFile = enFile;
	}
	
	public void setRank(Rank enRank) {
		m_enRank = enRank;
	}
	
	public void addRule(IRule oRule){
		m_mpRule.put(oRule.getName(), oRule);
	}
		
	public IRuleData clone() {
		return new RuleData(this);
	}

}