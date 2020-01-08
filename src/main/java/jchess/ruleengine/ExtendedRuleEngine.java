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
 * This class provides functionality to process extended (custom) rules.
 * It evaluates the possible move candidates using the Rules acceptable in XML.
 * It also facilitate in executing the Rules.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public class ExtendedRuleEngine extends DefaultRuleEngine {	
	@Inject
	public ExtendedRuleEngine(IRuleProcessor oRuleProcessor, IGUIHandle oGUIHandler, IAppLogger oLogger) {
		super(oRuleProcessor, oGUIHandler, oLogger);
		
		m_oLogger.writeLog(LogLevel.INFO, "Instantiating ExtendedRuleEngine.", "ExtendedRuleEngine", "ExtendedRuleEngine");
	}

	/**
	 * This method is an extension to one defined in DefaultRuleEngine.
	 * It entertains custom Rules and adds further move candidates in addition to those that DefaultRuleEngine provides.
	 */
	public Map<String, IMoveCandidate> tryEvaluateAllRules(IBoardAgent oBoard, IPieceAgent oPiece) {	
		m_oLogger.writeLog(LogLevel.INFO, String.format("Evaluating rules attaches to the Position [%s].", oPiece.toLog()), "tryEvaluateAllRules", "ExtendedRuleEngine");

		return super.tryEvaluateAllRules(oBoard, oPiece);
	}

	/**
	 * This method executes custom (user defined) rules.
	 */	
	public IBoardActivity tryExecuteRule(IBoardAgent oBoard, IMoveCandidate oMoveCandidate) {
		m_oLogger.writeLog(LogLevel.INFO, "Finalizing selected move candidate.", "tryExecuteRule", "ExtendedRuleEngine");

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

	private IBoardActivity tryExecuteCustomRules(IBoardAgent oBoard, IMoveCandidate oMoveCandidate) {
		m_oLogger.writeLog(LogLevel.INFO, "Finalizing selected custom move candidate.", "tryExecuteCustomRules", "ExtendedRuleEngine");

		IBoardActivity oMove = null;
		 
		switch( oMoveCandidate.getRule().getCustomName()) {
		case "MOVE_AND_CAPTURE[PAWN_PROMOTION]": 
			oMove = PawnRulesProcessor.tryExecutePawnPromotionRule(oBoard, m_oGUIHandler, oMoveCandidate);			
			break;
		case "MOVE[PAWN_FIRST_MOVE_EXCEPTION]":
			oMove = PawnRulesProcessor.tryExecutePawnFirstMoveException(oBoard, oMoveCandidate);
			break;
		case "MOVE_IFF_CAPTURE_POSSIBLE[PAWN_ENPASSANT]":
			oMove = PawnRulesProcessor.tryExecutePawnEnPassantRule(oBoard, oMoveCandidate);
			break;
		default:
			break;
		}
		
		return oMove;
	}
}
