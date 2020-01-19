package jchess.ruleengine;

import java.util.Map;

import com.google.inject.Inject;

import jchess.common.IBoardActivity;
import jchess.common.IBoardAgent;
import jchess.common.IMoveCandidate;
import jchess.common.IPieceAgent;
import jchess.gui.IGUIHandle;
import jchess.util.IAppLogger;
import jchess.util.LogLevel;

/**
 * This class extends ExtendedRuleEngine class and provides specialized functionalities, in addition to those provided by
 * ExtendedRuleEngine, peculiar to 3-Players (Hexagon) Chess-board.
 * It process all the custom operations that are solely related to 3-Players (Hexagon) Chess-board. It includes 
 * finding out possible move candidates for custom pieces and the execution of these rules.
 * It helps the engine in facilitating the rules that are not supported by XML.
 *  
 * @author	Sajad Karim
 * @since	19 Jan 2020
 */

public class ThreePlayersHexBoardRuleEngine extends ExtendedRuleEngine {
	@Inject
	public ThreePlayersHexBoardRuleEngine(IRuleProcessor oRuleProcessor, IGUIHandle oGUIHandler, IAppLogger oLogger) {
		super(oRuleProcessor, oGUIHandler, oLogger);
		
		m_oLogger.writeLog(LogLevel.INFO, "Instantiating ThreePlayersHexBoardRuleEngine.", "ThreePlayersHexBoardRuleEngine", "ThreePlayersHexBoardRuleEngine");
	}

	/**
	 * This method evaluates the rules defined for the piece and finds all the possible positions the piece can make.
	 * 
	 * @param IBoardAgent
	 * @param IPieceAgent
	 * return Map<String, IMoveCandidate>
	 */
	@Override
	public Map<String, IMoveCandidate> tryEvaluateAllRules(IBoardAgent oBoard, IPieceAgent oPiece) {	
		m_oLogger.writeLog(LogLevel.INFO, String.format("Evaluating rules attaches to the Position [%s].", oPiece.toLog()), "tryEvaluateAllRules", "ThreePlayersHexBoardRuleEngine");

		return super.tryEvaluateAllRules(oBoard, oPiece);
	}

	/**
	 * This method executes the rule provided with the move candidate.
	 * 
	 * @param IBoardAgent
	 * @param IMoveCandidate
	 * return IBoardActivity
	 */
	@Override
	public IBoardActivity tryExecuteRule(IBoardAgent oBoard, IMoveCandidate oMoveCandidate) {
		m_oLogger.writeLog(LogLevel.INFO, "Finalizing selected move candidate.", "tryExecuteRule", "ThreePlayersHexBoardRuleEngine");

		IBoardActivity oActivity = super.tryExecuteRule(oBoard, oMoveCandidate);
		
		if( oActivity == null) {
			switch( oMoveCandidate.getRule().getRuleType()) {
				case CUSTOM: {
					oActivity = tryExecuteCustomRules(oBoard, oMoveCandidate);
				}
				default:
					break;
			}
		}
		
		return oActivity;
	}

	/**
	 * This method executes the custom rules that are only specific to 3-Players (Hexagon) Chess-board.
	 * 
	 * @param IBoardAgent
	 * @param IMoveCandidate
	 * return IBoardActivity
	 */
	private IBoardActivity tryExecuteCustomRules(IBoardAgent oBoard, IMoveCandidate oMoveCandidate) {
		m_oLogger.writeLog(LogLevel.INFO, "Finalizing selected custom move candidate.", "tryExecuteCustomRules", "ThreePlayersHexBoardRuleEngine");

		IBoardActivity oMove = null;
		 
		switch( oMoveCandidate.getRule().getCustomName()) {
		case "BOMB[CANNON]":
			oMove = CannonRulesProcessor.tryExecuteBombAndPromotionRule(oBoard, oMoveCandidate);
			break;
		default:
			break;
		}
		
		return oMove;
	}

}
