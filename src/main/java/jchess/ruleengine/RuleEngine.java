package jchess.ruleengine;

import jchess.common.enumerator.RuleEngineType;

/**
 * This class acts as Factory Pattern which gives instance of
 * a RuleProcessor depending on the type of the Board passed.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public class RuleEngine {
	public static IRuleProcessor getRuleEngine(RuleEngineType enRuleEngine) {
		IRuleProcessor oProcessor;
		switch(enRuleEngine) {
			case RULEENGINE_2PLAYER:{
				oProcessor = new DefaultProcesssor(); 
			}
			break;
			case RULEENGINE_3PLAYER:{
				oProcessor = new RuleProcessor_3PlayerBoard(); 
			}
			break;
			case RULEENGINE_DEFAULT:
			default:
			{
				oProcessor = new DefaultProcesssor(); 
			}
			break;
		}
		
		return oProcessor;
	}
}
