package jchess.ruleengine;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

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
import jchess.gamelogic.BoardActivity;
import jchess.gamelogic.BoardAgentFactory;
import jchess.gamelogic.MoveCandidate;

/**
 * This is a custom class specific to process the custom rules that are specific to the King piece only.
 * This class defines all the custom rules that at the moment are not supportable in XML.
 *  
 * @author	Kenneth Allan
 * @since	21 Jan 2020
 */

public final class KingRulesProcessor {
	private static final IBoardFactory m_oBoardFactory = new BoardAgentFactory();
	
	/**
	 * This method checks the eligibility of the long and short king castling moves and writes the possible move candidates into the passed map.
	 * 
	 * @param IBoardAgent
	 * @param IPieceAgent
	 * @param Map<String, IMoveCandidate>
	 */
	public static void tryKingCastlingRules(IRuleProcessor oRuleProcessor, IBoardAgent oBoard, IPieceAgent oPiece, Map<String, IMoveCandidate> mpCandidateMovePositions) {
		if (oPiece.getPositionHistoryCount() > 0) {
			return;
		}
		tryCastlingRule(oRuleProcessor, File.BACKWARD, oBoard, oPiece, mpCandidateMovePositions);
		tryCastlingRule(oRuleProcessor, File.FORWARD, oBoard, oPiece, mpCandidateMovePositions);	
	}
	
	public static boolean isFieldEndangered(IPositionAgent field, IRuleProcessor oRuleProcessor, IBoardAgent oCurrentBoard, IPlayerAgent oCurrentPlayer) {

		for(Map.Entry<String,IPositionAgent> oPositionPiece : oCurrentBoard.getAllPositionAgents().entrySet()) {
			IPieceAgent oRandomPiece = oPositionPiece.getValue().getPiece();
			if(oRandomPiece != null && oRandomPiece.getPlayer() != oCurrentPlayer)
			{
				Map<String, IMoveCandidate> mpCandidateMovePosition = new HashMap<String, IMoveCandidate>();
				oRuleProcessor.tryEvaluateAllRules(oCurrentBoard, oRandomPiece, mpCandidateMovePosition);
				for(Map.Entry<String, IMoveCandidate> oCandidateMovePostion : mpCandidateMovePosition.entrySet())
				{				
					System.out.println("File and Rank of Piece:");
					System.out.println(oCandidateMovePostion.getValue().getCandidatePosition().getFile());
					System.out.println(oCandidateMovePostion.getValue().getCandidatePosition().getRank());
					int pieceRank = oCandidateMovePostion.getValue().getCandidatePosition().getRank();
					int pieceFile = oCandidateMovePostion.getValue().getCandidatePosition().getFile();
					if(pieceRank == field.getRank() && pieceFile == field.getFile())
					{
						return true;
					}						
				}
			}
		}
		return false;
	}	

	/**
	 * This method checks the eligibility of the king castling rule and writes the possible move candidates into the passed map.
	 * 
	 * @param IBoardAgent
	 * @param IPieceAgent
	 * @param Map<String, IMoveCandidate>
	 */
	public static void tryCastlingRule(IRuleProcessor oRuleProcessor, File fDirection, IBoardAgent oBoard, IPieceAgent oPiece, Map<String, IMoveCandidate> mpCandidateMovePositions) {
		
		// Initialise target positions at Kings position to begin with
		IPositionAgent possibleKingTarget = oPiece.getPosition();
		IPositionAgent possibleRookTarget = oPiece.getPosition();
		
		IPlayerAgent oCurrentPlayer = oPiece.getPlayer();
		
		// the path is stored here
		LinkedList<IPositionAgent> pathToRook = new LinkedList<IPositionAgent>();

		// find the next field in direction of edge
		Map<String, IPositionAgent> mpNextField = oPiece.getPosition().getAllPathAgents(oCurrentPlayer.getBoardMapping(), Direction.EDGE, Family.IGNORE, fDirection, Rank.SAME);

		while(true)
		{
			IPositionAgent posNextField = mpNextField.values().stream().findFirst().get();
			IPieceAgent pcNextField = posNextField.getPiece();
						
			// if its empty then set king and rook target positions
			if (posNextField.getPiece() == null) {
				possibleRookTarget = possibleKingTarget; // use the previous king target as the new rook target
				possibleKingTarget = posNextField;
				pathToRook.add(posNextField);
				
				// go to next field
				mpNextField = posNextField.getAllPathAgents(oCurrentPlayer.getBoardMapping(), Direction.EDGE, Family.IGNORE, fDirection, Rank.SAME);
				
				continue;
			}
			boolean bSameName = pcNextField.getName().equals("Rook" + oPiece.getFamily());
			// otherwise If he's a rook and he has never moved
			if(bSameName && pcNextField.getPositionHistoryCount() == 0) 
			{		
				IRule oRule = m_oBoardFactory.createRule();		
				oRule.getRuleData().setRuleType(RuleType.CUSTOM);
				oRule.getRuleData().setDirection(Direction.EDGE);
				oRule.getRuleData().setMaxRecurrenceCount(Integer.MAX_VALUE);
				oRule.getRuleData().setFile(fDirection);
				oRule.getRuleData().setRank(Rank.SAME);
				oRule.getRuleData().setFamily(Family.IGNORE);
				oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
				oRule.getRuleData().setCustomName("MOVE[KING_CASTLING]");
				((IRuleAgent)oRule).reset();
				
				for (IPositionAgent moveCandidateEntry : pathToRook) {
					if(isFieldEndangered(moveCandidateEntry, oRuleProcessor, oBoard, oCurrentPlayer)) {
						return;
					}
				}
				
				// if we got this far the path is safe and we can add the move candidate to the map of possible move candidates.
				IMoveCandidate oCastlingMoveKingCandidate = new MoveCandidate((IRuleAgent)oRule, oPiece, oPiece.getPosition(), possibleKingTarget);
				IMoveCandidate oCastlingMoveRookCandidate = new MoveCandidate(null, posNextField.getPiece(), posNextField, possibleRookTarget);
				
				oCastlingMoveKingCandidate.addSecondaryMove(oCastlingMoveRookCandidate);
				mpCandidateMovePositions.put(possibleKingTarget.getName(), oCastlingMoveKingCandidate);
				
				break;				
			
			}
			else {
				// no rook was found.
				break;
			}
		}
	}
	
	/**
	 * This method executes the castling move.
	 * 
	 * @param IBoardAgent
	 * @param IMoveCandidate
	 * @return IBoardActivity
	 */
	public static IBoardActivity tryExecuteCastlingRule(IBoardAgent oBoard, IMoveCandidate oMoveCandidate) {
		// move the king
		IBoardActivity oActivity = new BoardActivity(oMoveCandidate);
		
		IPieceAgent oPieceLinkedToCurrentPosition = oMoveCandidate.getSourcePosition().getPiece();
		IPositionAgent oCurrentPosition = oMoveCandidate.getSourcePosition();
		IPositionAgent oNewPosition = oMoveCandidate.getCandidatePosition();
		
		oPieceLinkedToCurrentPosition.setPosition(oNewPosition);
		oNewPosition.setPiece(oPieceLinkedToCurrentPosition);
		oCurrentPosition.setPiece(null);
		
		oPieceLinkedToCurrentPosition.enqueuePositionHistory(oCurrentPosition);
		
		oActivity.addPriorMoveEntry(oCurrentPosition, oPieceLinkedToCurrentPosition);
		oActivity.addPriorMoveEntry(oNewPosition, null);
		oActivity.addPostMoveEntry(oCurrentPosition, null);
		oActivity.addPostMoveEntry(oNewPosition, oPieceLinkedToCurrentPosition);		
		
		// move the rook
		IMoveCandidate oSecondaryMoveCandidate = oMoveCandidate.getSecondaryMove(0);
		IPieceAgent pcSecondaryMoveCandidate = oSecondaryMoveCandidate.getPieceToMove();
		IPositionAgent posSecondaryMoveCandidateSource = oSecondaryMoveCandidate.getSourcePosition();
		IPositionAgent posSecondaryMoveCandidateTarget = oSecondaryMoveCandidate.getCandidatePosition();
		
		pcSecondaryMoveCandidate.setPosition(oSecondaryMoveCandidate.getCandidatePosition());
		oSecondaryMoveCandidate.getCandidatePosition().setPiece(pcSecondaryMoveCandidate);
		oSecondaryMoveCandidate.getSourcePosition().setPiece(null);
		
		pcSecondaryMoveCandidate.enqueuePositionHistory(oCurrentPosition);
		oActivity.addPriorMoveEntry(posSecondaryMoveCandidateSource, pcSecondaryMoveCandidate);
		oActivity.addPriorMoveEntry(oSecondaryMoveCandidate.getCandidatePosition(), posSecondaryMoveCandidateTarget.getPiece());

		oActivity.addPostMoveEntry(posSecondaryMoveCandidateSource, null);
		oActivity.addPostMoveEntry(posSecondaryMoveCandidateTarget, pcSecondaryMoveCandidate);
	
		oActivity.setPlayer(oPieceLinkedToCurrentPosition.getPlayer());

		return oActivity;
	}	
}
