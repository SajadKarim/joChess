package jchess.ruleengine;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;

import jchess.common.IBoardAgent;
import jchess.common.IMoveCandidacy;
import jchess.common.IPlayerAgent;
import jchess.common.IPositionAgent;
import jchess.common.IRule;

public interface IRuleProcessor {
	public void tryEvaluateAllRules(IBoardAgent oBoard, IPositionAgent oPosition, Map<String, IMoveCandidacy> mpCandidatePositions);
	public void tryFindPossibleCandidateMovePositions(IPlayerAgent oPlayer, Queue<RuleProcessorData> qData, Map<String, IMoveCandidacy> mpCandidatePositions);	
	public void checkForPositionMoveCandidacyAndContinuity(IPlayerAgent oPlayer, IRule oRule, IPositionAgent oCandidacyPosition, AtomicReference<Boolean> bIsValidMode, AtomicReference<Boolean> bCanContinue);
}
