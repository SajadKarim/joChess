package jchess.ruleengine;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;

import jchess.common.IBoardAgent;
import jchess.common.IMoveCandidate;
import jchess.common.IPieceAgent;
import jchess.common.IPlayerAgent;
import jchess.common.IPositionAgent;
import jchess.common.IRule;

/**
 * This interface provides abstraction to different RuleProcessors.
 * It is designed to keep Rule loosely coupled of the main game logic
 * and developer can create different code files for different boards.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public interface IRuleProcessor {
	public void tryEvaluateAllRules(IBoardAgent oBoard, IPieceAgent oPiece, Map<String, IMoveCandidate> mpCandidatePositions);
	public void tryFindPossibleCandidateMovePositions(IPieceAgent oPieceToMove, IPositionAgent oSourcePosition, IPlayerAgent oPlayer, Queue<RuleProcessorData> qData, Map<String, IMoveCandidate> mpCandidatePositions);	
	public void checkForPositionMoveCandidacyAndContinuity(IPlayerAgent oPlayer, IRule oRule, IPositionAgent oCandidacyPosition, AtomicReference<Boolean> bIsValidMode, AtomicReference<Boolean> bCanContinue);
}
