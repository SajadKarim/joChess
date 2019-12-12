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
	RULEENGINE_5PLAYER
}
