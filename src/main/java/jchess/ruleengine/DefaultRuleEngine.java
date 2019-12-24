package jchess.ruleengine;

import java.util.HashMap;

import java.util.Map;

import org.javatuples.Pair;

import jchess.common.*;
import jchess.gamelogic.Move;
import jchess.gui.IGUIHandle;

/**
 * The default logic to execute a Rule.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

class DefaultRuleEngine implements IRuleEngine{
	protected IRuleProcessor m_oRuleProcessor;
	protected IGUIHandle m_oGUIHandler;
	
	public DefaultRuleEngine(IRuleProcessor oRuleProcessor) {
		m_oRuleProcessor = oRuleProcessor;
	}
	
	public void setGUIHandle(IGUIHandle oGUIHandler) {
		m_oGUIHandler = oGUIHandler;
	}
	
	public void setRuleProcessor(IRuleProcessor oRuleProcessor) {
		m_oRuleProcessor = oRuleProcessor;
	}
	
	public Map<String, IMoveCandidacy> tryEvaluateAllRules(IBoardAgent oBoard, IPositionAgent oSelectedPosition) {	
		Map<String, IMoveCandidacy> mpCandidateMovePositions = new HashMap<String, IMoveCandidacy>();

		m_oRuleProcessor.tryEvaluateAllRules(oSelectedPosition, mpCandidateMovePositions);
		
		return mpCandidateMovePositions;
    }

	public IMove tryExecuteRule(IBoardAgent oBoard, IMoveCandidacy oMoveCandidate) {
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
		
		return oMove;
	}
}
