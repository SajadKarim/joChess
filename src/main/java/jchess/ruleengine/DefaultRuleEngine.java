package jchess.ruleengine;

import java.util.HashMap;

import java.util.Map;

import com.google.inject.Inject;

import jchess.common.IBoardActivity;
import jchess.common.IBoardAgent;
import jchess.common.IMoveCandidate;
import jchess.common.IPieceAgent;
import jchess.common.IPlayerAgent;
import jchess.common.IPositionAgent;
import jchess.gamelogic.BoardActivity;
import jchess.gui.IGUIHandle;
import jchess.util.IAppLogger;
import jchess.util.LogLevel;

/**
 * This class provides functionality to process all the basic rules that can be provided in XML.
 * It evaluates the pieces and with the help of the rules defined against them, it finds out all the possible move 
 * candidates the piece can perform. It also facilitates the game engine in the execution of the rules.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public class DefaultRuleEngine implements IRuleEngine {
	protected IRuleProcessor m_oRuleProcessor;
	protected IGUIHandle m_oGUIHandler;
	protected IAppLogger m_oLogger;
	
	/**
	 * Constructor for DefaultRuleEngine.
	 * 
	 * @param IRuleProcessor
	 * @param IGUIHandler
	 * @param IAppLogger
	 */
	@Inject
	public DefaultRuleEngine(IRuleProcessor oRuleProcessor, IGUIHandle oGUIHandler, IAppLogger oLogger) {
		m_oRuleProcessor = oRuleProcessor;
		m_oGUIHandler = oGUIHandler;
		m_oLogger = oLogger;
		
		m_oLogger.writeLog(LogLevel.INFO, "Instantiating DefaultRuleEngine.", "DefaultRuleEngine", "DefaultRuleEngine");
	}
		
	/**
	 * This method evaluates the rules defined for the piece and finds all the possible positions the piece can make.
	 * 
	 * @param IBoardAgent
	 * @param IPieceAgent
	 * return Map<String, IMoveCandidate>
	 */
	public Map<String, IMoveCandidate> tryEvaluateAllRules(IBoardAgent oBoard, IPieceAgent oPiece) {	
		m_oLogger.writeLog(LogLevel.INFO, String.format("Evaluating move candidates for selected position [%s].", oPiece.toLog()), "tryEvaluateAllRules", "DefaultRuleEngine");

		/**
		 * This map holds the Position details to which the Selected piece can be moved.
		 */
		Map<String, IMoveCandidate> mpCandidateMovePositions = new HashMap<String, IMoveCandidate>();

		m_oRuleProcessor.tryEvaluateAllRules(oBoard, oPiece, mpCandidateMovePositions);
		
		return mpCandidateMovePositions;
    }
	
	public IPlayerAgent tryCheckIfPlayerEndengered(IBoardAgent oBoard, IPlayerAgent oPlayer)
	{
		return m_oRuleProcessor.tryCheckIfPlayerEndengered(oBoard, oPlayer);
	
	}
	
	
	public Boolean checkStalemate(IBoardAgent oBoard, IPlayerAgent oPlayer) {
		return m_oRuleProcessor.checkStalemate(oBoard, oPlayer);
	}
	/**
	 * This method executes the rule provided with the move candidate.
	 * 
	 * @param IBoardAgent
	 * @param IPieceAgent
	 * return IBoardActivity
	 */
	public IBoardActivity tryExecuteRule(IBoardAgent oBoard, IMoveCandidate oMoveCandidate) {
		m_oLogger.writeLog(LogLevel.INFO, String.format("Executing the rule for move candidate [%s].", oMoveCandidate.toLog()), "tryExecuteRule", "DefaultRuleEngine");

		IBoardActivity oActivity = null;
		
		switch (oMoveCandidate.getRule().getRuleType()) {
			case MOVE: {
				if (oMoveCandidate.getCandidatePosition().getPiece() != null) {
					break;
				}

				oActivity = new BoardActivity(oMoveCandidate);
				
				IPieceAgent oPieceLinkedToCurrentPosition = oMoveCandidate.getSourcePosition().getPiece();
				IPieceAgent oPieceLinkedToNewPosition = oMoveCandidate.getCandidatePosition().getPiece();
				IPositionAgent oCurrentPosition = oMoveCandidate.getSourcePosition();
				IPositionAgent oNewPosition = oMoveCandidate.getCandidatePosition();
				
				oPieceLinkedToCurrentPosition.setPosition(oNewPosition);
				oNewPosition.setPiece(oPieceLinkedToCurrentPosition);
				oCurrentPosition.setPiece(null);
				
				oPieceLinkedToCurrentPosition.enqueuePositionHistory(oCurrentPosition);
				
				oActivity.addPriorMoveEntry(oCurrentPosition, oPieceLinkedToCurrentPosition);
				oActivity.addPriorMoveEntry(oNewPosition, oPieceLinkedToNewPosition);
				oActivity.addPostMoveEntry(oCurrentPosition, null);
				oActivity.addPostMoveEntry(oNewPosition, oPieceLinkedToCurrentPosition);
				oActivity.setPlayer(oPieceLinkedToCurrentPosition.getPlayer());
			}
			break;
			case MOVE_AND_CAPTURE: {
				oActivity = new BoardActivity(oMoveCandidate);

				IPieceAgent oPieceLinkedToCurrentPosition = oMoveCandidate.getSourcePosition().getPiece();
				IPieceAgent oPieceLinkedToNewPosition = oMoveCandidate.getCandidatePosition().getPiece();
				IPositionAgent oCurrentPosition = oMoveCandidate.getSourcePosition();
				IPositionAgent oNewPosition = oMoveCandidate.getCandidatePosition();
				
				oPieceLinkedToCurrentPosition.setPosition(oNewPosition);
				oNewPosition.setPiece(oPieceLinkedToCurrentPosition);
				oCurrentPosition.setPiece(null);
				
				oPieceLinkedToCurrentPosition.enqueuePositionHistory(oCurrentPosition);
				
				oActivity.addPriorMoveEntry(oCurrentPosition, oPieceLinkedToCurrentPosition);
				oActivity.addPriorMoveEntry(oNewPosition, oPieceLinkedToNewPosition);
				oActivity.addPostMoveEntry(oCurrentPosition, null);
				oActivity.addPostMoveEntry(oNewPosition, oPieceLinkedToCurrentPosition);
				oActivity.setPlayer(oPieceLinkedToCurrentPosition.getPlayer());
			}
			break;
			case MOVE_IFF_CAPTURE_POSSIBLE: {
				oActivity = new BoardActivity(oMoveCandidate);

				IPieceAgent oPieceLinkedToCurrentPosition = oMoveCandidate.getSourcePosition().getPiece();
				IPieceAgent oPieceLinkedToNewPosition = oMoveCandidate.getCandidatePosition().getPiece();
				IPositionAgent oCurrentPosition = oMoveCandidate.getSourcePosition();
				IPositionAgent oNewPosition = oMoveCandidate.getCandidatePosition();
				
				oPieceLinkedToCurrentPosition.setPosition(oNewPosition);
				oNewPosition.setPiece(oPieceLinkedToCurrentPosition);
				oCurrentPosition.setPiece(null);
				
				oPieceLinkedToCurrentPosition.enqueuePositionHistory(oCurrentPosition);
				
				oActivity.addPriorMoveEntry(oCurrentPosition, oPieceLinkedToCurrentPosition);
				oActivity.addPriorMoveEntry(oNewPosition, oPieceLinkedToNewPosition);
				oActivity.addPostMoveEntry(oCurrentPosition, null);
				oActivity.addPostMoveEntry(oNewPosition, oPieceLinkedToCurrentPosition);
				oActivity.setPlayer(oPieceLinkedToCurrentPosition.getPlayer());
			}
				break;
			case MOVE_TRANSIENT:{
			}
			break;
			case CUSTOM:
			default:
				break;
		}
		
		if (oActivity != null) {
			m_oLogger.writeLog(LogLevel.INFO, String.format("Returning.. [%s].", oActivity.toLog()), "tryExecuteRule", "DefaultRuleEngine");
		}
		
		return oActivity;
	}

	public IPieceAgent isPieceEndangered(IBoardAgent oBoard, IPieceAgent oPiece) {
		return m_oRuleProcessor.isPieceEndangered(oBoard, oPiece);
	}
}
