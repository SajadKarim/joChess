package jchess.common;

import java.util.concurrent.atomic.AtomicReference;

/**
 * IRuleAgent provides interface for the module where the
 * all the decisions (game) are taken. It ensures that user
 * does not make any changes to underlying data object.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public interface IRuleAgent extends IRule {
	public void reset();
	public void makeRuleDead();		
	public IRuleAgent getNextRule();
	public IRuleAgent clone();
}
