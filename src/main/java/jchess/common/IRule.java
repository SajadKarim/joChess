package jchess.common;

import java.util.List;

import jchess.common.enumerator.Direction;
import jchess.common.enumerator.Family;
import jchess.common.enumerator.File;
import jchess.common.enumerator.Manoeuvre;
import jchess.common.enumerator.Rank;
import jchess.common.enumerator.RuleType;

/**
 * IRule provides abstraction to IRuleData and IRuleAgent.
 * It fulfills functionality of Abstract Factory Pattern, and it is mainly built for Cache module to make both the
 * type compatible with its implementation (data population logic). 
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 *
 */

public interface IRule {
	public File getFile();
	public Rank getRank();
	public String getName();
	public RuleType getRuleType();
	public Direction getDirection();
	public Manoeuvre getManoeuvreStrategy();	
	public int getMaxRecurrenceCount();
	public Family getFamily();
	public IRuleData getRuleData();
	public List<IRule> getAllRules();
	public String getCustomName();
	public int getLifespan();
	public IRule clone();
}
