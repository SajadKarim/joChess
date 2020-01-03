package jchess.ruleengine;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import jchess.common.IBoardAgent;
import jchess.common.IMoveCandidate;
import jchess.common.IPieceAgent;
import jchess.common.IPlayerAgent;
import jchess.common.IPositionAgent;
import jchess.common.IRule;
import jchess.util.IAppLogger;
import jchess.util.LogLevel;

/**
 * DefaultRuleProcessor
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public class ExtendedRuleProcessor extends DefaultRuleProcessor {
	public ExtendedRuleProcessor(IAppLogger oLogger) {
		super(oLogger);
		
		m_oLogger.writeLog(LogLevel.INFO, "Instantiating ExtendedRuleProcessor.", "ExtendedRuleProcessor", "ExtendedRuleProcessor");
	}

	@Override
	public void tryEvaluateAllRules(IBoardAgent oBoard, IPieceAgent oPiece, Map<String, IMoveCandidate> mpCandidatePositions) {
		m_oLogger.writeLog(LogLevel.DETAILED, "Evaluating selected move candidate.", "tryEvaluateAllRules", "ExtendedRuleProcessor");

		super.tryEvaluateAllRules(oBoard, oPiece, mpCandidatePositions);

		switch(oPiece.getName()) {
		case "PawnWhite":
		case "PawnBlack":
		case "PawnRed": {
			PawnRulesProcessor.tryPawnPromotionRule(oBoard, oPiece, mpCandidatePositions);

			PawnRulesProcessor.tryPawnFirstMoveException(this, oBoard, oPiece, mpCandidatePositions);

			PawnRulesProcessor.tryPawnEnPassantRule(oBoard, oPiece, mpCandidatePositions);
		}
			break;
		default:
			break;
		}
		
    }

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
					case "MOVE_IFF_CAPTURE_POSSIBLE[PAWN_ENPASSANT]":{
						bIsValidMode.set(true);
						bCanContinue.set(false);
					}
					break;
				}
			}
				break;
		}
	}
}