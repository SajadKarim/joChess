package jchess.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jchess.common.enumerator.Direction;
import jchess.common.enumerator.Family;
import jchess.common.enumerator.File;
import jchess.common.enumerator.Manoeuvre;
import jchess.common.enumerator.Rank;
import jchess.common.enumerator.RuleType;

/**
 * Rule.java
 * 
 * This is data class to store a Rule.
 *
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
	private Map<String, IRuleData> m_mpRule;
		
	RuleData() {
		m_mpRule = new HashMap<String, IRuleData>();
	}
	
	RuleData(String stName,
			RuleType enRuleType, 
			Direction enDirection, 
			Manoeuvre enManoeuvre, 
			int nRecurrence, 
			Family enFamily, 
			File enFile, 
			Rank enRank, 
			Boolean bIgnorePiece, 
			Map<String, IRuleData> mpRule) {
		m_stName = stName;
		m_enRuleType = enRuleType;
		m_enDirection = enDirection;
		m_nMaxRecurrenceAllowed = nRecurrence;
		m_enFamily = enFamily;
		m_enFile = enFile;
		m_enRank = enRank;
		//m_bIgnorePiece = bIgnorePiece;
		m_mpRule = mpRule;
		m_enManoeuvreStratgy = enManoeuvre;
		
		m_mpRule = new HashMap<String, IRuleData>();
	}
	
	RuleData(RuleData rule) {
		m_enRuleType = rule.m_enRuleType;
		m_enDirection = rule.m_enDirection;
		m_nMaxRecurrenceAllowed = rule.m_nMaxRecurrenceAllowed;
		m_enFamily = rule.m_enFamily;
		m_enFile = rule.m_enFile;
		m_enRank = rule.m_enRank;
		//m_bIgnorePiece = rule.m_bIgnorePiece;
		m_mpRule = rule.m_mpRule;
		m_enManoeuvreStratgy = rule.m_enManoeuvreStratgy;
	}
	
	public String getName() {
		return m_stName;
	}
	public void setName(String stName) {
		m_stName = stName;
	}
	
	public RuleType getRuleType() {
		return m_enRuleType;		
	}
	public void setRuleType(RuleType enRuleType) {
		m_enRuleType = enRuleType;		
	}
	
	public Direction getDirection() {
		return m_enDirection;
	}
	public void setDirection(Direction enDirection) {
		m_enDirection = enDirection;
	}
	
	public Manoeuvre getManoeuvreStrategy() {
		return m_enManoeuvreStratgy;
	}
	public void setManoeuvreStrategy(Manoeuvre enManoeuvre) {
		m_enManoeuvreStratgy = enManoeuvre;
	}
	
	public int getMaxRecurrenceCount() {
		return m_nMaxRecurrenceAllowed;
	}
	public void setMaxRecurrenceCount(int nMaxRecurrenceCount) {
		m_nMaxRecurrenceAllowed = nMaxRecurrenceCount;
	}
	
	public Family getFamily() {
		return m_enFamily;
	}
	public void setFamily(Family enFamily) {
		m_enFamily = enFamily;
	}
	
	public File getFile() {
		return m_enFile;
	}
	public void setFile(File enFile) {
		m_enFile = enFile;
	}
	
	public Rank getRank() {
		return m_enRank;
	}
	public void setRank(Rank enRank) {
		m_enRank = enRank;
	}
	
	public List<IRuleData> getAllRules(){
		return new ArrayList<IRuleData>(m_mpRule.values());
	}
	public void addRule(IRuleData oRule){
		m_mpRule.put(oRule.getName(), oRule);
	}
	
	public RuleData getRuleData() {
		return this;
	}
}