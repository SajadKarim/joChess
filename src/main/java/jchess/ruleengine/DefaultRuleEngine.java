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
	 * @param oBoard IBoardAgent the instance of active Board
	 * @param oPiece IPieceAgent the instance of the input Piece
	 * return Map<String, IMoveCandidate> possible move candidate
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
	/**
	 * This method Check if the player King is Endangered 
	 * 
	 * @param oBoard IBoardAgent instance of the active Board
	 * @param oPlayer IPlayerAgent instance of the Player that we want to check
	 * @return IPlayerAgent return the rival Player that check the input Player
	 */
	public IPlayerAgent tryCheckIfPlayerEndengered(IBoardAgent oBoard, IPlayerAgent oPlayer)
	{
		return m_oRuleProcessor.tryCheckIfPlayerEndengered(oBoard, oPlayer);
	
	}
	
	
	public Boolean checkStalemate(IBoardAgent oBoard, IPlayerAgent oPlayer) {
		long t1 = System.nanoTime();
		Boolean b = m_oRuleProcessor.checkStalemate(oBoard, oPlayer);
		long t2 = System.nanoTime();
		
		long timeElapsed  = t2 - t1;
		System.out.println("Execution time in milliseconds : " + 
				timeElapsed / 1000000);
		return b;
	}
	/**
	 * This method executes the rule provided with the move candidate.
	 * 
	 * @param oBoard IBoardAgent the instance of active Board
	 * @param oMoveCandidate IMoveCandidate the instance of the move candidate
	 * return IBoardActivity the Board activity
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
				
				setPositionsAndUpdateActivity(oMoveCandidate.getSourcePosition(), oMoveCandidate.getCandidatePosition(), oActivity);
			}
				break;
			case MOVE_AND_CAPTURE: {
				oActivity = new BoardActivity(oMoveCandidate);

				setPositionsAndUpdateActivity(oMoveCandidate.getSourcePosition(), oMoveCandidate.getCandidatePosition(), oActivity);
			}
			break;
			case MOVE_IFF_CAPTURE_POSSIBLE: {
				oActivity = new BoardActivity(oMoveCandidate);

				setPositionsAndUpdateActivity(oMoveCandidate.getSourcePosition(), oMoveCandidate.getCandidatePosition(), oActivity);
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
	/**
	 * This method set the a new Position for the Piece
	 * 
	 * @param oCurrentPosition IPositionAgent the current position that hold the Piece
	 * @param oCandidatePosition IPositionAgent the next position that the Piece will move to 
	 * @param oActivity IBoardActivity the current activity of the Board
	 */
	protected void setPositionsAndUpdateActivity(IPositionAgent oCurrentPosition, IPositionAgent oCandidatePosition, IBoardActivity oActivity) {
		IPieceAgent oPieceLinkedToCurrentPosition = oCurrentPosition.getPiece();
		IPieceAgent oPieceLinkedToCandidatePosition = oCandidatePosition.getPiece();
		
		if (oCandidatePosition != null) {
			oCandidatePosition.setPiece(oPieceLinkedToCurrentPosition);
		}
		
		if (oCurrentPosition != null) {
			oCurrentPosition.setPiece(null);
		}

		if (oPieceLinkedToCurrentPosition != null) {
			oPieceLinkedToCurrentPosition.setPosition(oCandidatePosition);
			oPieceLinkedToCurrentPosition.enqueuePositionHistory(oCurrentPosition);
		}
		
		if (oPieceLinkedToCandidatePosition != null) {
			oPieceLinkedToCandidatePosition.setPosition(null);
		}
		
		oActivity.addPriorMoveEntry(oCurrentPosition, oPieceLinkedToCurrentPosition);
		oActivity.addPriorMoveEntry(oCandidatePosition, oPieceLinkedToCandidatePosition);
		oActivity.addPostMoveEntry(oCurrentPosition, null);
		oActivity.addPostMoveEntry(oCandidatePosition, oPieceLinkedToCurrentPosition);
		oActivity.setPlayer(oPieceLinkedToCurrentPosition.getPlayer());
	}
	/**
	 * This method check if the Piece is in Endangered position
	 * 
	 * @param oBoard IBoardAgent instance of the active Board
	 * @param oPiece IPieceAgent instance of the Piece that we want to check
	 * @return IPieceAgent return the rival Piece that can attack the input Piece
	 */
	public IPieceAgent isPieceEndangered(IBoardAgent oBoard, IPieceAgent oPiece) {
		return m_oRuleProcessor.isPieceEndangered(oBoard, oPiece);
	}
}
