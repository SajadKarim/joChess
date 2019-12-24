package jchess.ruleengine;

import java.util.Map;

import jchess.common.IBoardAgent;
import jchess.common.IMove;
import jchess.common.IMoveCandidacy;
import jchess.common.IPositionAgent;

/**
 * This interface provides abstraction to different RuleEngines.
 * It is designed to keep Rule loosely coupled of the main game logic
 * and developer can create different code files for different boards.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public interface IRuleEngine {
	public Map<String,IMoveCandidacy> tryEvaluateAllRules(IBoardAgent oBoard, IPositionAgent oSelectedPosition);
	public IMove tryExecuteRule(IBoardAgent oBoard, IMoveCandidacy oMoveCandidate);
}
