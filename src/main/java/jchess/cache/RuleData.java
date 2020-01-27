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
 * This is a data class to store "Rule" related details in cache.
 * It actually maps XML or DB structure for "Rule" to memory.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public final class RuleData implements IRuleData {
	/**
	 * File enumerator of rule.
	 */
	private File m_enFile;
	/**
	 * Rank enumerator of rule.
	 */
	private Rank m_enRank;
	/**
	 * Name of rule.
	 */
	private String m_stName;
	/**
	 * Family enumerator of rule.
	 */
	private Family m_enFamily;
	/**
	 * RuleType enumerator of rule.
	 */
	private RuleType m_enRuleType;
	/**
	 * Direction enumerator of rule.
	 */
	private Direction m_enDirection;
	/**
	 * Manoeuvre enumerator of rule.
	 */
	private Manoeuvre m_enManoeuvreStratgy;
	/**
	 * Integer value to maintain the times the rule is required to be repeated.
	 */
	private int	m_nMaxRecurrenceAllowed;
	/**
	 * Hashmap fo child rules.
	 */
	private Map<String, IRule> m_mpRule;
	/**
	 * Custom name rule.
	 */
	private String m_stCustomName;
	private int m_nLifespan;
	
	/**
	 * Default constructor.
	 */
	public RuleData() {
		m_mpRule = new HashMap<String, IRule>();
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param oRule RuleData
	 */
	public RuleData(RuleData oRule) {
		m_stName = oRule.m_stName;
		m_enFile = oRule.m_enFile;
		m_enRank = oRule.m_enRank;
		m_mpRule = oRule.m_mpRule;
		m_enFamily = oRule.m_enFamily;
		m_nLifespan = oRule.m_nLifespan;
		m_enRuleType = oRule.m_enRuleType;
		m_enDirection = oRule.m_enDirection;
		m_stCustomName = oRule.m_stCustomName;
		m_enManoeuvreStratgy = oRule.m_enManoeuvreStratgy;
		m_nMaxRecurrenceAllowed = oRule.m_nMaxRecurrenceAllowed;

		// TODO: I tired HashMap's clone, but it does not call Object's copy constructor.
		// For the time being I am manually copying all the objects. Need to do it proper way to do deep copy.
		m_mpRule = new HashMap<String, IRule>();
		for (Map.Entry<String, IRule> it : oRule.m_mpRule.entrySet()) {
			m_mpRule.put(it.getKey(), it.getValue().clone());
		}
	}
	
	// region: Implements IRule
	/**
	 * Getter for rule name.
	 */
	public String getName() {
		return m_stName;
	}
	
	/**
	 * Getter for RuleType.
	 */
	public RuleType getRuleType() {
		return m_enRuleType;		
	}

	/**
	 * Getter for Direction.
	 */
	public Direction getDirection() {
		return m_enDirection;
	}
	
	/** 
	 * Getter for Manoeuvre.
	 */
	public Manoeuvre getManoeuvreStrategy() {
		return m_enManoeuvreStratgy;
	}
	
	/**
	 * Getter for MaxRecurrenceCount.
	 */
	public int getMaxRecurrenceCount() {
		return m_nMaxRecurrenceAllowed;
	}

	/**
	 * Getter for Family.
	 */
	public Family getFamily() {
		return m_enFamily;
	}
	
	/**
	 * Getter for File.
	 */
	public File getFile() {
		return m_enFile;
	}

	/**
	 * Getter for Rank.
	 */
	public Rank getRank() {
		return m_enRank;
	}
	
	/**
	 * Returns child rules.
	 */
	public List<IRule> getAllRules() {
		return new ArrayList<IRule>(m_mpRule.values());
	}
	
	/**
	 * Return code RuleData object.
	 */
	public IRuleData getRuleData() {
		return this;
	}
	// endregion
	
	// region: Implements IRuleData
	/**
	 * Setter for rule name.
	 */
	public void setName(String stName) {
		m_stName = stName;
	}
	
	/**
	 * Setter for RuleType.
	 */
	public void setRuleType(RuleType enRuleType) {
		m_enRuleType = enRuleType;		
	}
	
	/**
	 * Setter for Direction.
	 */
	public void setDirection(Direction enDirection) {
		m_enDirection = enDirection;
	}
	
	/**
	 * Setter for Manoeuvre.
	 */
	public void setManoeuvreStrategy(Manoeuvre enManoeuvre) {
		m_enManoeuvreStratgy = enManoeuvre;
	}
	
	/**
	 * Setter for MaxRecurrence count.
	 */
	public void setMaxRecurrenceCount(int nMaxRecurrenceCount) {
		m_nMaxRecurrenceAllowed = nMaxRecurrenceCount;
	}
	
	/**
	 * Setter for Family.
	 */
	public void setFamily(Family enFamily) {
		m_enFamily = enFamily;
	}

	/**
	 * Setter for File.
	 */
	public void setFile(File enFile) {
		m_enFile = enFile;
	}
	
	/**
	 * Setter for Rank.
	 */
	public void setRank(Rank enRank) {
		m_enRank = enRank;
	}
	
	/**
	 * Add a new Rule.
	 */
	public void addRule(IRule oRule) {
		m_mpRule.put(oRule.getName(), oRule);
	}
		
	@Override
	/**
	 * Create a duplice rule instance.
	 */
	public IRuleData clone() {
		return new RuleData(this);
	}

	/**
	 * Getter for custom name.
	 */
	public String getCustomName() {
		return m_stCustomName;
	}
	
	/**
	 * Setter for custom name.
	 */
	public void setCustomName(String stName) {
		m_stCustomName = stName;
	}
	
	public int getLifespan() {
		return m_nLifespan;
	}
	
	public void setLifespan(int nLifespan) {
		m_nLifespan = nLifespan;
	}
	
	public void markUsage() {
		m_nLifespan++;
	}
}