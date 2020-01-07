package jchess.ruleengine;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import jchess.common.IBoardAgent;
import jchess.common.IMoveCandidacy;
import jchess.common.IPlayerAgent;
import jchess.common.IPositionAgent;
import jchess.common.IRule;
import jchess.util.IAppLogger;
import jchess.util.LogLevel;

public class ExtendedRuleProcessor extends DefaultRuleProcessor {
	public ExtendedRuleProcessor(IAppLogger oLogger) {
		super(oLogger);
		
		m_oLogger.writeLog(LogLevel.INFO, "Instantiating ExtendedRuleProcessor.", "ExtendedRuleProcessor", "ExtendedRuleProcessor");
	}
	/**
	 * try evaluate all the rules of the piece
	 * @param oBoard the chess board
	 * @param oPosition the position of the piece
	 * @param mpCandidatePositions the candidate positions
	 */
	@Override
	public void tryEvaluateAllRules(IBoardAgent oBoard, IPositionAgent oPosition, Map<String, IMoveCandidacy> mpCandidatePositions) {
		m_oLogger.writeLog(LogLevel.DETAILED, "Evaluating selected move candidate.", "tryEvaluateAllRules", "ExtendedRuleProcessor");

		super.tryEvaluateAllRules(oBoard, oPosition, mpCandidatePositions);

		switch(oPosition.getPiece().getName()) {
		case "PawnWhite":
		case "PawnBlack":
		case "PawnRed": {
			PawnRulesProcessor.tryPawnPromotionRule(oBoard, oPosition, mpCandidatePositions);

			PawnRulesProcessor.tryPawnFirstMoveException(this, oBoard, oPosition, mpCandidatePositions);
		}
			break;
		default:
			break;
		}
		
    }
	/**
	 * Verifying if position can be a candidate move and the piece can continue to the next position
	 * @param oPlayer the current player
	 * @param oRule the rules of the piece
	 * @param oCandidacyPosition the candidate position
	 * @param bIsValidMode can capture or not
	 * @param bCanContinue can continue or not
	 */
	@Override
	public void checkForPositionMoveCandidacyAndContinuity(IPlayerAgent oPlayer, IRule oRule, IPositionAgent oCandidacyPosition, AtomicReference<Boolean> bIsValidMode, AtomicReference<Boolean> bCanContinue) {
		m_oLogger.writeLog(LogLevel.DETAILED, "Verifying if position can be a candidate move and can continue as the next position.", "checkForPositionMoveCandidacyAndContinuity", "ExtendedRuleProcessor");
		super.checkForPositionMoveCandidacyAndContinuity(oPlayer, oRule, oCandidacyPosition, bIsValidMode, bCanContinue);

		switch(oRule.getRuleType()) {
			case CUSTOM: {
				switch(oRule.getCustomName()) {
					case "MOVE_TRANSIENT[PAWN_FIRST_MOVE_EXCEPTION]":
						bIsValidMode.set(false);
						bCanContinue.set(true);
					break;
					case "MOVE[PAWN_FIRST_MOVE_EXCEPTION]":{
						if( oCandidacyPosition.getPiece() != null) {
							bIsValidMode.set(false);
							bCanContinue.set(false);
						} else {
							bIsValidMode.set(true);
							bCanContinue.set(true);
						}
					}
					break;
				}
			}
				break;
		}
	}
}