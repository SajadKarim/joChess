package jchess.common.enumerator;

/**
 * This datastructure give developer room to develop/extends Rule execution functionality.
 * For every type of board developer can provide different logic to process the rule.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public enum RuleEngineType {
	RULEENGINE_DEFAULT,
	RULEENGINE_2PLAYER,
	RULEENGINE_3PLAYER,
	RULEENGINE_4PLAYER,
	RULEENGINE_6PLAYER;
	
	public static RuleEngineType convertStringToEnum(String stValue) {
		switch (stValue) {
			case "RULEENGINE_2PLAYER":
				return RULEENGINE_2PLAYER;
			case "RULEENGINE_3PLAYER":
				return RULEENGINE_3PLAYER;
			case "RULEENGINE_4PLAYER":
				return RULEENGINE_4PLAYER;
			case "RULEENGINE_6PLAYER":
				return RULEENGINE_6PLAYER;
			case "RULEENGINE_DEFAULT":
			default:
				return RULEENGINE_DEFAULT;
		}
	}
}
