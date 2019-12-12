package jchess.ruleengine;

import jchess.common.enumerator.RuleEngineType;

/**
 * This class acts as Factory Pattern which gives instance of
 * a RuleProcessor depending on the type of the Board passed.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public class RuleEngineFactory {
	public static IRuleEngine getRuleEngine(RuleEngineType enRuleEngine) {
		IRuleEngine oProcessor;
		switch(enRuleEngine) {
			case RULEENGINE_2PLAYER:{
				oProcessor = new DefaultRuleEngine(); 
			}
				break;
			case RULEENGINE_3PLAYER:{
				oProcessor = new RuleEngine_3PlayersBoard(); 
			}
				break;
			case RULEENGINE_DEFAULT:
			default:
			{
				oProcessor = new DefaultRuleEngine(); 
			}
				break;
		}
		
		return oProcessor;
	}
}
