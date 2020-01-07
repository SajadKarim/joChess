
package jchess.ruleengine;

import java.util.HashMap;

import java.util.Map;

import com.google.inject.Inject;

import jchess.common.*;
import jchess.gamelogic.Move;
import jchess.gui.IGUIHandle;
import jchess.util.IAppLogger;
import jchess.util.LogLevel;

/**
 * The default logic to execute a Rule.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public class DefaultRuleEngine implements IRuleEngine{
	protected IRuleProcessor m_oRuleProcessor;
	protected IGUIHandle m_oGUIHandler;
	protected IAppLogger m_oLogger;
	
	@Inject
	public DefaultRuleEngine(IRuleProcessor oRuleProcessor, IGUIHandle oGUIHandler, IAppLogger oLogger) {
		m_oRuleProcessor = oRuleProcessor;
		m_oGUIHandler = oGUIHandler;
		m_oLogger = oLogger;
		
		m_oLogger.writeLog(LogLevel.INFO, "Instantiating DefaultRuleEngine.", "DefaultRuleEngine", "DefaultRuleEngine");
	}
		
	public Map<String, IMoveCandidacy> tryEvaluateAllRules(IBoardAgent oBoard, IPositionAgent oSelectedPosition) {	
		m_oLogger.writeLog(LogLevel.INFO, String.format("Evaluating move candidates for selected position [%s].", oSelectedPosition.toLog()), "tryEvaluateAllRules", "DefaultRuleEngine");

		Map<String, IMoveCandidacy> mpCandidateMovePositions = new HashMap<String, IMoveCandidacy>();

		m_oRuleProcessor.tryEvaluateAllRules(oBoard, oSelectedPosition, mpCandidateMovePositions);
		
		return mpCandidateMovePositions;
    }

	public IMove tryExecuteRule(IBoardAgent oBoard, IMoveCandidacy oMoveCandidate) {
		m_oLogger.writeLog(LogLevel.INFO, String.format("Executing the rule for move candidate [%s].", oMoveCandidate.toLog()), "tryExecuteRule", "DefaultRuleEngine");

		IMove oMove = new Move(oMoveCandidate);
		
		switch( oMoveCandidate.getRule().getRuleType()) {
			case MOVE:{
				if( oMoveCandidate.getCandidatePosition().getPiece() != null)
					break;
								
				IPieceAgent oPiece = oMoveCandidate.getSourcePosition().getPiece();
				oMoveCandidate.getCandidatePosition().setPiece(oPiece);
				oMoveCandidate.getSourcePosition().setPiece(null);
				
				oPiece.markRun();
				
				oMove.addPriorMoveEntry(oMoveCandidate.getSourcePosition().getName(), oPiece);
				oMove.addPriorMoveEntry(oMoveCandidate.getCandidatePosition().getName(), null);
				oMove.addPostMoveEntry(oMoveCandidate.getSourcePosition().getName(), null);
				oMove.addPostMoveEntry(oMoveCandidate.getCandidatePosition().getName(), oPiece);
				oMove.setPlayer(oPiece.getPlayer());
				oMove.setMoveSuccessState(true);
			}
				break;
			case MOVE_AND_CAPTURE:{
				IPieceAgent oPiece = oMoveCandidate.getSourcePosition().getPiece();
				oMoveCandidate.getCandidatePosition().setPiece((jchess.gamelogic.PieceAgent)oPiece);
				oMoveCandidate.getSourcePosition().setPiece(null);

				oPiece.markRun();

				oMove.addPriorMoveEntry(oMoveCandidate.getSourcePosition().getName(), oPiece);
				oMove.addPriorMoveEntry(oMoveCandidate.getCandidatePosition().getName(), null);
				oMove.addPostMoveEntry(oMoveCandidate.getSourcePosition().getName(), null);
				oMove.addPostMoveEntry(oMoveCandidate.getCandidatePosition().getName(), oPiece);
				oMove.setPlayer(oPiece.getPlayer());
				oMove.setMoveSuccessState(true);
			}
				break;
			case MOVE_IFF_CAPTURE_POSSIBLE:{
				IPieceAgent oPiece = oMoveCandidate.getSourcePosition().getPiece();
				oMoveCandidate.getCandidatePosition().setPiece((jchess.gamelogic.PieceAgent)oPiece);
				oMoveCandidate.getSourcePosition().setPiece(null);				

				oPiece.markRun();

				oMove.addPriorMoveEntry(oMoveCandidate.getSourcePosition().getName(), oPiece);
				oMove.addPriorMoveEntry(oMoveCandidate.getCandidatePosition().getName(), null);
				oMove.addPostMoveEntry(oMoveCandidate.getSourcePosition().getName(), null);
				oMove.addPostMoveEntry(oMoveCandidate.getCandidatePosition().getName(), oPiece);
				oMove.setPlayer(oPiece.getPlayer());
				oMove.setMoveSuccessState(true);
			}
				break;
			case MOVE_TRANSIENT:{
			}
				break;
			case CUSTOM:
			default:
				break;
		}
		
		m_oLogger.writeLog(LogLevel.INFO, String.format("Returning.. [%s].", oMove.toLog()), "tryExecuteRule", "DefaultRuleEngine");

		return oMove;
	}
}
