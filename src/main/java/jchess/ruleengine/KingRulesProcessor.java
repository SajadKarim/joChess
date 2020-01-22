package jchess.ruleengine;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import jchess.common.IBoardActivity;
import jchess.common.IBoardAgent;
import jchess.common.IBoardFactory;
import jchess.common.IMoveCandidate;
import jchess.common.IPieceAgent;
import jchess.common.IPlayerAgent;
import jchess.common.IPositionAgent;
import jchess.common.IRule;
import jchess.common.IRuleAgent;
import jchess.common.enumerator.Direction;
import jchess.common.enumerator.Family;
import jchess.common.enumerator.File;
import jchess.common.enumerator.Manoeuvre;
import jchess.common.enumerator.Rank;
import jchess.common.enumerator.RuleType;
import jchess.common.gui.IPresenter;
import jchess.gamelogic.BoardActivity;
import jchess.gamelogic.BoardAgentFactory;
import jchess.gamelogic.MoveCandidate;
import jchess.gui.IGUIHandle;
import jchess.util.LogLevel;

/**
 * This is a custom class specific to process the custom rules that are peculiar to the King piece only.
 * This class defines all the custom rules that at the moments are not supportable in XML.
 *  
 * @author	Kenneth Allan
 * @since	21 Jan 2020
 */

public final class KingRulesProcessor {
	private static final IBoardFactory m_oBoardFactory = new BoardAgentFactory();
	
	/**
	 * This method checks the eligibility of the long and short king castling rules.
	 * 
	 * @param IBoardAgent
	 * @param IPieceAgent
	 * @param Map<String, IMoveCandidate>
	 */
	public static void tryKingCastlingRules(IBoardAgent oBoard, IPieceAgent oPiece, Map<String, IMoveCandidate> mpCandidateMovePositions) {
		System.out.println("Trying king castling moves., tryKingCastlingRules, KingRulesProcessor");

		if (oPiece.getPositionHistoryCount() > 0) {
			return;
		}
		tryShortCastlingRule(oBoard, oPiece, mpCandidateMovePositions);
		tryLongCastlingRule(oBoard, oPiece, mpCandidateMovePositions);
		
	
	}
	
	public static boolean isAnyFieldEndangeredOrVacant(IPositionAgent[] fields, IBoardAgent oCurrentBoard, IPlayerAgent oCurrentPlayer) {
		// if any of the fields is vacant return true
		for (IPositionAgent field : fields) {
			if (field.getPiece() != null) {
				return true;
			}
		}
		for(Map.Entry<String,IPositionAgent> oPositionPiece : oCurrentBoard.getAllPositionAgents().entrySet()) {
			IPieceAgent oRandomPiece = oPositionPiece.getValue().getPiece();
			if(oRandomPiece != null)
			{
				if(oRandomPiece.getPlayer() != oCurrentPlayer)
				{
					Map<String, IMoveCandidate> mpCandidateMovePosition = m_oRuleProcessor.tryEvaluateAllRules(m_oGameModel.getBoard(), oRandomPiece);
					for(Map.Entry<String, IMoveCandidate> oCandidateMovePostion : mpCandidateMovePosition.entrySet())
					{
						// iteratiion over the fields of interest is done here to save iterating over the players own pieces again
						for (IPositionAgent field : fields)
						{
								
							System.out.println("File and Rank of Piece:");
							System.out.println(oCandidateMovePostion.getValue().getCandidatePosition().getFile());
							System.out.println(oCandidateMovePostion.getValue().getCandidatePosition().getRank());
							System.out.println("File and rank of King:");
							System.out.println(kingRank);
							System.out.println(kingFile);
							int pieceRank = oCandidateMovePostion.getValue().getCandidatePosition().getRank();
							int pieceFile = oCandidateMovePostion.getValue().getCandidatePosition().getFile();
							if(pieceRank == field.getRank() && pieceFile == field.getFile())
							{
								return true;
							}
						}
					}
					
				}
			}
		}
		return false;
		
	}
	
	
	/**
	 * This method checks the eligibility of the short king castling rule.
	 * 
	 * @param IBoardAgent
	 * @param IPieceAgent
	 * @param Map<String, IMoveCandidate>
	 */
	public static void tryShortCastlingRule(IBoardAgent oBoard, IPieceAgent oPiece, Map<String, IMoveCandidate> mpCandidateMovePositions) {

		IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
    	oRule.getRuleData().setRuleType(RuleType.CUSTOM);
    	oRule.getRuleData().setCustomName("MOVE_CASTLING_SHORT[KING]");
    	String oPlayerColor = oPiece.getFamily();
    	IPieceAgent rook = oPiece.getPlayer().getPiece("Rook" + oPlayerColor);
    	if (rook.getPositionHistoryCount() > 0) {
			return;
		}
    	// find path to get to rook
		// if path is clear
		// and path is safe
		// then display the possible move
	}
	
	/**
	 * This method checks the eligibility of the long king castling rule.
	 * 
	 * @param IBoardAgent
	 * @param IPieceAgent
	 * @param Map<String, IMoveCandidate>
	 */
	public static void tryLongCastlingRule(IBoardAgent oBoard, IPieceAgent oPiece, Map<String, IMoveCandidate> mpCandidateMovePositions) {

		IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
    	oRule.getRuleData().setRuleType(RuleType.CUSTOM);
    	oRule.getRuleData().setCustomName("MOVE_CASTLING_LONG[KING]");
    	String oPlayerColor = oPiece.getFamily();
    	IPieceAgent rook = oPiece.getPlayer().getPiece("Rook" + oPlayerColor);
    	if (rook.getPositionHistoryCount() > 0) {
			return;
		}
    	// find path to get to rook
		// if path is clear
		// and path is safe
		// then display the possible move
	}
	

	/**
	 * This method executes the kings move for the castling rules.
	 * 
	 * @param IBoardAgent
	 * @param IMoveCandidate
	 * @return IBoardActivity
	 */
	public static IBoardActivity tryExecuteKingsMoveForCastlingRule(IMoveCandidate oMoveCandidate) {
		IBoardActivity oActivity = new BoardActivity(oMoveCandidate);
		
		IPieceAgent oPieceLinkedToCurrentPosition = oMoveCandidate.getSourcePosition().getPiece();
		IPieceAgent oPieceLinkedToNewPosition = oMoveCandidate.getCandidatePosition().getPiece();
		IPositionAgent oCurrentPosition = oMoveCandidate.getSourcePosition();
		IPositionAgent oNewPosition = oMoveCandidate.getCandidatePosition();
		
		oPieceLinkedToCurrentPosition.setPosition(oNewPosition);
		oPieceLinkedToNewPosition.setPosition(null);
		oNewPosition.setPiece(oPieceLinkedToCurrentPosition);
		oCurrentPosition.setPiece(null);
		
		oPieceLinkedToCurrentPosition.enqueuePositionHistory(oCurrentPosition);
		
		oActivity.addPriorMoveEntry(oCurrentPosition, oPieceLinkedToCurrentPosition);
		oActivity.addPriorMoveEntry(oNewPosition, oPieceLinkedToNewPosition);
		oActivity.addPostMoveEntry(oCurrentPosition, null);
		oActivity.addPostMoveEntry(oNewPosition, oPieceLinkedToCurrentPosition);
		
		oActivity.setPlayer(oPieceLinkedToCurrentPosition.getPlayer());
		
		return oActivity;
	}
	/**
	 * This method executes the short castling rule.
	 * 
	 * @param IBoardAgent
	 * @param IMoveCandidate
	 * @return IBoardActivity
	 */
	public static IBoardActivity tryExecuteShortCastlingRule(IBoardAgent oBoard, IMoveCandidate oMoveCandidate) {
		// move the king
		IBoardActivity oActivity = tryExecuteKingsMoveForCastlingRule(oMoveCandidate);

		
		// TODO: move the rook respectively.
		
		
		return oActivity;
	}

	/**
	 * This method executes the long castling rule.
	 * 
	 * @param IBoardAgent
	 * @param IMoveCandidate
	 * @return IBoardActivity
	 */
	public static IBoardActivity tryExecuteLongCastlingRule(IBoardAgent oBoard, IMoveCandidate oMoveCandidate) {
		// move the king
		IBoardActivity oActivity = tryExecuteKingsMoveForCastlingRule(oMoveCandidate);
		
		// TODO: move the rook respectively.
		

		return null;
	}
}
