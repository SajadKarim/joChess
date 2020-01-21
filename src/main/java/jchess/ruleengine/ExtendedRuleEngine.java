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
 * This class extends DefaultRuleEngine class and provides extended functionalities, in addition to those provided by
 * DefaultRuleEngine, that all common to all the boards.
 * It process all the custom operations that are common to all the boards available in the system. It includes 
 * finding out possible move candidates for custom pieces and the execution of these rules.
 * It helps the engine in facilitating the rules that are not supported by XML.
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
	 * This method evaluates the rules defined for the piece and finds all the possible positions the piece can make.
	 * 
	 * @param IBoardAgent
	 * @param IPieceAgent
	 * return Map<String, IMoveCandidate>
	 */
	@Override
	public Map<String, IMoveCandidate> tryEvaluateAllRules(IBoardAgent oBoard, IPieceAgent oPiece) {	
		m_oLogger.writeLog(LogLevel.INFO, String.format("Evaluating rules attaches to the Position [%s].", oPiece.toLog()), "tryEvaluateAllRules", "ExtendedRuleEngine");

		return super.tryEvaluateAllRules(oBoard, oPiece);
	}

	/**
	 * This method executes the move candidate provided.
	 * 
	 * @param IBoardAgent
	 * @param IMoveCandidate
	 * return IBoardActivity
	 */
	@Override
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

	/**
	 * This method executes the rule provided with the move candidate.
	 * 
	 * @param IBoardAgent
	 * @param IMoveCandidate
	 * return IBoardActivity
	 */
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
