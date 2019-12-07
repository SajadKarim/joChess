package jchess.common;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import jchess.common.enumerator.Direction;
import jchess.common.enumerator.Family;
import jchess.common.enumerator.File;
import jchess.common.enumerator.Manoeuvre;
import jchess.common.enumerator.Rank;
import jchess.common.enumerator.RuleType;

public interface IRule {
	public File getFile();
	public Rank getRank();
	public String getName();
	public RuleType getRuleType();
	public Direction getDirection();
	public Manoeuvre getManoeuvreStrategy();
	public int getMaxRecurrenceCount();
	public Family getFamily();
	//public List<IRule> getRules(); //to remove this from interfcae
	public void makeRuleDead();	
	
	public void isValidMove( IPosition oPosition, AtomicReference<Boolean> bIsValidMode, AtomicReference<Boolean> bCanContinue);
	
	public void canContinue( IPosition oPosition);

	public void reset();
	public IRule clone();
	public IRule getNextRule();
}
