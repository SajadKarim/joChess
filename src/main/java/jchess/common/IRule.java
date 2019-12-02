package jchess.common;

import java.util.List;
import java.util.Map;

import jchess.common.enumerator.Direction;
import jchess.common.enumerator.Family;
import jchess.common.enumerator.File;
import jchess.common.enumerator.Manoeuvre;
import jchess.common.enumerator.Rank;
import jchess.common.enumerator.RuleType;

/**
 * IRule.java
 * 
 * This interface exposes necessary details to data manipulation classes.
 *
 */

public interface IRule{
	public RuleType getRuleType();
	public Direction getDirection();
	public Manoeuvre getManoeuvreStrategy();
	public int getMaxRecurrenceCount();
	public Family getFamily();
	public File getFile();
	public Rank getRank();
	public Map<String, Rule> getAllRules(); //to remove this from interfcae
}
