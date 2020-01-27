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
 * This class extends ExtendedRuleProcessor class and provides specialized functionalities, in addition to those provided by
 * ExtendedRuleProcess, peculiar to 3-Players (Hexagon) Chess-board.
 * It process all the custom operations that are solely related to 3-Players (Hexagon) Chess-board. It includes 
 * finding out possible move candidates for custom pieces and the execution of these rules.
 * It helps the engine in facilitating the rules that are not supported by XML.
 *  
 * @author	Sajad Karim
 * @since	19 Jan 2020
 */

public class ThreePlayersHexBoardRuleProcessor extends ExtendedRuleProcessor {
	public ThreePlayersHexBoardRuleProcessor(IAppLogger oLogger) {
		super(oLogger);
		
		m_oLogger.writeLog(LogLevel.INFO, "Instantiating ThreePlayersHexBoardRuleProcessor.", "ThreePlayersHexBoardRuleProcessor", "ThreePlayersHexBoardRuleProcessor");
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
		m_oLogger.writeLog(LogLevel.DETAILED, "Evaluating selected move candidate.", "tryEvaluateAllRules", "ThreePlayersHexBoardRuleProcessor");

		super.tryEvaluateAllRules(oBoard, oPiece, mpCandidatePositions);
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
		m_oLogger.writeLog(LogLevel.DETAILED, "Verifying if position can be a candidate move and can continue as the next position.", "checkForPositionMoveCandidacyAndContinuity", "ThreePlayersHexBoardRuleProcessor");
		
		super.checkForPositionMoveCandidacyAndContinuity(oPlayer, oRule, oCandidacyPosition, bIsValidMode, bCanContinue);

		switch (oRule.getRuleType()) {
			case CUSTOM: {
				switch (oRule.getCustomName()) {
					case "BOMB[CANNON]": {
						if (oCandidacyPosition.getPiece() == null) {
							bIsValidMode.set(true);
							bCanContinue.set(false);
						} else if (oCandidacyPosition.getPiece().getPlayer() == oPlayer) {
							bIsValidMode.set(false);
							bCanContinue.set(false);
						} else if (oCandidacyPosition.getPiece().getPlayer() != oPlayer) {
							bIsValidMode.set(true);
							bCanContinue.set(false);
						}
					}
						break;
					default:
						break;
				}
			}
			break;
			default:
				break;
		}
	}
}
