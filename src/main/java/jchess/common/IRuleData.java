package jchess.common;

import java.util.List;

import jchess.common.enumerator.*;

/**
 * IRuleData provides interface for the cache module.
 * It expose getters and setters and does not support
 * methods that are linked to logic of the game.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public interface IRuleData extends IRule {
	public void setName(String stName);
	public void setRuleType(RuleType enRuleType);
	public void setDirection(Direction enDirection);
	public void setManoeuvreStrategy(Manoeuvre enManoeuvre);
	public void setMaxRecurrenceCount(int nMaxRecurrenceCount);
	public void setFamily(Family enFamily);
	public void setFile(File enFile);
	public void setRank(Rank enRank);
	public List<IRule> getAllRules();
	public void addRule(IRule oRule);
	public IRuleData clone();	
	public void setCustomName(String stName);
}
