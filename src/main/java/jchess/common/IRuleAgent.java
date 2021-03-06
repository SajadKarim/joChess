package jchess.common;

/**
 * IRuleAgent provides interface for the module where the all the decisions (game) are taken. It ensures that user
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
	public void markUsage();	
	public Boolean isAlive();
	public void markRuleUsage();
	public Boolean canProceedWithThisRule();
	public IRuleAgent getNextChildRule();
}
