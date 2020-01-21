package jchess.common;

import java.util.List;

import jchess.common.enumerator.Direction;
import jchess.common.enumerator.Family;
import jchess.common.enumerator.File;
import jchess.common.enumerator.Manoeuvre;
import jchess.common.enumerator.Rank;
import jchess.common.enumerator.RuleType;

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
	public void setCustomName(String stName);
	public void setLifespan(int nLifespan);
}
