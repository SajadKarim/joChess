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
 * This class provides functionality to process all the basic rules (defined in XML).
 * It evaluates the possible move candidates using the Rules acceptable in XML.
 * It also facilitate in executing the Rules.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public class DefaultRuleEngine implements IRuleEngine{
	protected IRuleProcessor m_oRuleProcessor;
	protected IGUIHandle m_oGUIHandler;
	protected IAppLogger m_oLogger;
	
	/**
	 * Constructor for DefaultRuleEngine.
	 * @param oRuleProcessor
	 * @param oGUIHandler
	 * @param oLogger
	 */
	@Inject
	public DefaultRuleEngine(IRuleProcessor oRuleProcessor, IGUIHandle oGUIHandler, IAppLogger oLogger) {
		m_oRuleProcessor = oRuleProcessor;
		m_oGUIHandler = oGUIHandler;
		m_oLogger = oLogger;
		
		m_oLogger.writeLog(LogLevel.INFO, "Instantiating DefaultRuleEngine.", "DefaultRuleEngine", "DefaultRuleEngine");
	}
	
	/**
	 * This method (with the help of provided IRuleProcessor) searches and returns all the possible moves-
	 * that the selected piece can take.
	 */
	public Map<String, IMoveCandidacy> tryEvaluateAllRules(IBoardAgent oBoard, IPositionAgent oSelectedPosition) {	
		m_oLogger.writeLog(LogLevel.INFO, String.format("Evaluating move candidates for selected position [%s].", oSelectedPosition.toLog()), "tryEvaluateAllRules", "DefaultRuleEngine");

		/**
		 * This map holds the Position details to which the Selected piece can be moved.
		 */
		Map<String, IMoveCandidacy> mpCandidateMovePositions = new HashMap<String, IMoveCandidacy>();

		m_oRuleProcessor.tryEvaluateAllRules(oSelectedPosition, mpCandidateMovePositions);
		
		return mpCandidateMovePositions;
    }

	/**
	 * This method (with the help of provided IRuleProcessor) executes the provided move candidacy that IRuleEngine
	 * provide in its initial iteration of finding the move candidates.
	 */	
	public IMove tryExecuteRule(IBoardAgent oBoard, IMoveCandidacy oMoveCandidate) {
		m_oLogger.writeLog(LogLevel.INFO, String.format("Executing the rule for move candidate [%s].", oMoveCandidate.toLog()), "tryExecuteRule", "DefaultRuleEngine");

		IMove oMove = new Move(oMoveCandidate);
		
		switch( oMoveCandidate.getRule().getRuleType()) {
			case MOVE:{
				if( oMoveCandidate.getCandidatePosition().getPiece() != null) {
					m_oLogger.writeLog(LogLevel.ERROR, "Candidate position has a piece attached to it.", "tryExecuteRule", "DefaultRuleEngine");
					break;
				}
				
				// Detaching Piece from current position to the new position.
				IPieceAgent oPiece = oMoveCandidate.getSourcePosition().getPiece();
				oMoveCandidate.getCandidatePosition().setPiece(oPiece);
				oMoveCandidate.getSourcePosition().setPiece(null);

				// Recording that Piece has made a move.
				oPiece.markRun();
				
				// Recording necessary details to handle Undo/Redo operations.
				oMove.addPriorMoveEntry(oMoveCandidate.getSourcePosition().getName(), oPiece);
				oMove.addPriorMoveEntry(oMoveCandidate.getCandidatePosition().getName(), null);
				oMove.addPostMoveEntry(oMoveCandidate.getSourcePosition().getName(), null);
				oMove.addPostMoveEntry(oMoveCandidate.getCandidatePosition().getName(), oPiece);
				oMove.setPlayer(oPiece.getPlayer());
				oMove.setMoveSuccessState(true);
			}
				break;
			case MOVE_AND_CAPTURE:{
				// Detaching Piece from current position to the new position.
				IPieceAgent oPiece = oMoveCandidate.getSourcePosition().getPiece();
				oMoveCandidate.getCandidatePosition().setPiece((jchess.gamelogic.PieceAgent)oPiece);
				oMoveCandidate.getSourcePosition().setPiece(null);

				// Recording that Piece has made a move.
				oPiece.markRun();

				// Recording necessary details to handle Undo/Redo operations.
				oMove.addPriorMoveEntry(oMoveCandidate.getSourcePosition().getName(), oPiece);
				oMove.addPriorMoveEntry(oMoveCandidate.getCandidatePosition().getName(), null);
				oMove.addPostMoveEntry(oMoveCandidate.getSourcePosition().getName(), null);
				oMove.addPostMoveEntry(oMoveCandidate.getCandidatePosition().getName(), oPiece);
				oMove.setPlayer(oPiece.getPlayer());
				oMove.setMoveSuccessState(true);
			}
				break;
			case MOVE_IFF_CAPTURE_POSSIBLE:{
				// Detaching Piece from current position to the new position.
				IPieceAgent oPiece = oMoveCandidate.getSourcePosition().getPiece();
				oMoveCandidate.getCandidatePosition().setPiece((jchess.gamelogic.PieceAgent)oPiece);
				oMoveCandidate.getSourcePosition().setPiece(null);				

				// Recording that Piece has made a move.
				oPiece.markRun();

				// Recording necessary details to handle Undo/Redo operations.
				oMove.addPriorMoveEntry(oMoveCandidate.getSourcePosition().getName(), oPiece);
				oMove.addPriorMoveEntry(oMoveCandidate.getCandidatePosition().getName(), null);
				oMove.addPostMoveEntry(oMoveCandidate.getSourcePosition().getName(), null);
				oMove.addPostMoveEntry(oMoveCandidate.getCandidatePosition().getName(), oPiece);
				oMove.setPlayer(oPiece.getPlayer());
				oMove.setMoveSuccessState(true);
			}
				break;
			case MOVE_TRANSIENT:
			default:
				m_oLogger.writeLog(LogLevel.ERROR, "Invalid Rule Type.", "tryExecuteRule", "DefaultRuleEngine");
				break;
		}
		
		m_oLogger.writeLog(LogLevel.INFO, String.format("Returning.. [%s].", oMove.toLog()), "tryExecuteRule", "DefaultRuleEngine");

		return oMove;
	}
}
