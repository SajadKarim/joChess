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
 * This interface aims to provide abstraction to classes that should provide functionality to process and execute no just the rules
 * that can be defined in the XML, but also entertain the custom rules that are not XML supportable. 
 * The classes should evaluate the pieces and, with the help of the rules defined against them, 
 * the should finds out all the possible move candidates the piece can perform. They should also facilitate the game engine 
 * in the execution of the rules.
 * 
 * This interface provides abstraction to different Rule Engines.
 * 
 * It is designed to keep the rule processing functionality loosely coupled to the main game logic therefore developer 
 * can create different code files for different boards.
 * 
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public interface IRuleProcessor {
	public void tryEvaluateAllRules(IBoardAgent oBoard, IPieceAgent oPiece, Map<String, IMoveCandidate> mpCandidatePositions);
	public void tryFindPossibleCandidateMovePositions(IPieceAgent oPieceToMove, IPositionAgent oSourcePosition, IPlayerAgent oPlayer, Queue<RuleProcessorData> qData, Map<String, IMoveCandidate> mpCandidatePositions);	
	public void checkForPositionMoveCandidacyAndContinuity(IPlayerAgent oPlayer, IRule oRule, IPositionAgent oCandidacyPosition, AtomicReference<Boolean> bIsValidMode, AtomicReference<Boolean> bCanContinue);
	public IPlayerAgent tryCheckIfPlayerEndengered(IBoardAgent oBoard, IPlayerAgent oPlayer);
}
