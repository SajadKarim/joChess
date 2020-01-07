package jchess.ruleengine;

import java.util.Map;

import jchess.common.IBoardActivity;
import jchess.common.IBoardAgent;
import jchess.common.IMoveCandidate;
import jchess.common.IPieceAgent;

/**
 * This interface provides abstraction to different RuleEngines.
 * It is designed to keep Rule loosely coupled of the main game logic
 * and developer can create different code files for different boards.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public interface IRuleEngine {
	public Map<String,IMoveCandidate> tryEvaluateAllRules(IBoardAgent oBoard, IPieceAgent oPiece);
	public IBoardActivity tryExecuteRule(IBoardAgent oBoard, IMoveCandidate oMoveCandidate);
}
