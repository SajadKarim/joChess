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
 * This class extends DefaultRuleEngine class and provides extended functionalities, in addition to those provided by
 * DefaultRuleEngine, that all common to all the boards.
 * It process all the custom operations that are common to all the boards available in the system. It includes 
 * finding out possible move candidates for custom pieces and the execution of these rules.
 * It helps the engine in facilitating the rules that are not supported by XML.
 *  
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public class ExtendedRuleProcessor extends DefaultRuleProcessor {
	public ExtendedRuleProcessor(IAppLogger oLogger) {
		super(oLogger);
		
		m_oLogger.writeLog(LogLevel.INFO, "Instantiating ExtendedRuleProcessor.", "ExtendedRuleProcessor", "ExtendedRuleProcessor");
	}

	/**
	 * This method finds out whether the provided position can be a candidate position to make a move, and it also (with the help of the rule)
	 * deduces whether algorithm should proceed with the position to find out the next possible candidate moves.
	 * 
	 * @param IBoardAgent
	 * @param IPieceAgent
	 * @param Map<String, IMoveCandidate>
	 */
	@Override
	public void tryEvaluateAllRules(IBoardAgent oBoard, IPieceAgent oPiece, Map<String, IMoveCandidate> mpCandidatePositions) {
		m_oLogger.writeLog(LogLevel.DETAILED, "Evaluating selected move candidate: " + oPiece.getName(), "tryEvaluateAllRules", "ExtendedRuleProcessor");

		super.tryEvaluateAllRules(oBoard, oPiece, mpCandidatePositions);
		//System.out.println("Evaluating selected move candidate: " + oPiece.getName());
		switch (oPiece.getName()) {
			case "PawnWhite":
			case "PawnBlack":
			case "PawnRed": {
				PawnRulesProcessor.tryPawnPromotionRule(oBoard, oPiece, mpCandidatePositions);
	
				PawnRulesProcessor.tryPawnFirstMoveException(this, oBoard, oPiece, mpCandidatePositions);
	
				PawnRulesProcessor.tryPawnEnPassantRule(oBoard, oPiece, mpCandidatePositions);
			}
			case "KingWhite": 
			case "KingBlack":
			case "KingRed": {
				KingRulesProcessor.tryKingCastlingRules(oBoard, oPiece, mpCandidatePositions);
				//System.out.println("Evaluating selected move candidate: KING CASTLING");

			}
				break;

			default:
				break;
		}
		
    }

	/**
	 * This method checks whether the position meets the requirements defined in the rule to be a possible move candidate, and it
	 * also finds out whether to seize the search process or not.
	 * 
	 * @param IPlayerAgent
	 * @param IRule
	 * @param IPositionAgent
	 * @param AtomicReference<Boolean>
	 * @param AtomicReference<Boolean>
	 */
	@Override
	public void checkForPositionMoveCandidacyAndContinuity(IPlayerAgent oPlayer, IRule oRule, IPositionAgent oCandidacyPosition, AtomicReference<Boolean> bIsValidMode, AtomicReference<Boolean> bCanContinue) {
		m_oLogger.writeLog(LogLevel.DETAILED, "Verifying if position can be a candidate move and can continue as the next position.", "checkForPositionMoveCandidacyAndContinuity", "ExtendedRuleProcessor");

		super.checkForPositionMoveCandidacyAndContinuity(oPlayer, oRule, oCandidacyPosition, bIsValidMode, bCanContinue);

		switch (oRule.getRuleType()) {
			case CUSTOM: {
				switch (oRule.getCustomName()) {
					case "MOVE_TRANSIENT[PAWN_FIRST_MOVE_EXCEPTION]":
						bIsValidMode.set(false);
						bCanContinue.set(true);
					break;
					case "MOVE[PAWN_FIRST_MOVE_EXCEPTION]": {
						if (oCandidacyPosition.getPiece() != null) {
							bIsValidMode.set(false);
							bCanContinue.set(false);
						} else {
							bIsValidMode.set(true);
							bCanContinue.set(true);
						}
					}
					break;
					case "MOVE_IFF_CAPTURE_POSSIBLE[PAWN_ENPASSANT]": {
						bIsValidMode.set(true);
						bCanContinue.set(false);
					}
				}
			}
			break;
			default:
				break;
		}
	}
}