package jchess.ruleengine;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;

import org.javatuples.Pair;

import jchess.common.IPieceAgent;
import jchess.common.IPlayerAgent;
import jchess.common.IPositionAgent;
import jchess.common.IRule;
import jchess.common.IRuleAgent;

public interface IRuleProcessor {
	public void tryEvaluateAllRules(IPositionAgent oPosition, Map<String,Pair<IPositionAgent, IRuleAgent>> mpCandidatePositions);
	public void tryFindPossibleCandidateMovePositions(IPlayerAgent oPlayer, IPieceAgent oPiece, Queue<RuleProcessorData> qData, Map<String,Pair<IPositionAgent, IRuleAgent>> mpCandidatePositions);	
	public void checkForPositionMoveCandidacyAndContinuity(IPlayerAgent oPlayer, IPieceAgent oPiece, IRule oRule, IPositionAgent oCandidacyPosition, AtomicReference<Boolean> bIsValidMode, AtomicReference<Boolean> bCanContinue);
}
