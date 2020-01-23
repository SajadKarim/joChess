package jchess.ruleengine;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
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
	public static void tryKingCastlingRules(IRuleProcessor oRuleProcessor, IBoardAgent oBoard, IPieceAgent oPiece, Map<String, IMoveCandidate> mpCandidateMovePositions) {
		System.out.println("Trying king castling moves., tryKingCastlingRules, KingRulesProcessor");

		if (oPiece.getPositionHistoryCount() > 0) {
			return;
		}
		tryCastlingRule(oRuleProcessor, File.BACKWARD, oBoard, oPiece, mpCandidateMovePositions);
		tryCastlingRule(oRuleProcessor, File.FORWARD, oBoard, oPiece, mpCandidateMovePositions);
		
	
	}
	
	public static boolean isFieldEndangered(IPositionAgent field, IBoardAgent oCurrentBoard, IPlayerAgent oCurrentPlayer) {

		for(Map.Entry<String,IPositionAgent> oPositionPiece : oCurrentBoard.getAllPositionAgents().entrySet()) {
			IPieceAgent oRandomPiece = oPositionPiece.getValue().getPiece();
			if(oRandomPiece != null)
			{
				if(oRandomPiece.getPlayer() != oCurrentPlayer)
				{
					Map<String, IMoveCandidate> mpCandidateMovePosition = m_oRuleProcessor.tryEvaluateAllRules(oCurrentBoard, oRandomPiece);
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
	 * This method checks the eligibility of the long king castling rule.
	 * 
	 * @param IBoardAgent
	 * @param IPieceAgent
	 * @param Map<String, IMoveCandidate>
	 */
	public static void tryCastlingRule(IRuleProcessor oRuleProcessor, File fDirection, IBoardAgent oBoard, IPieceAgent oPiece, Map<String, IMoveCandidate> mpCandidateMovePositions) {
		
		// Initialise target positions at Kings position to begin with
		IPositionAgent possibleKingTarget = oPiece.getPosition();
		IPositionAgent possibleRookTarget = oPiece.getPosition();
		
		// the path is stored here
		LinkedList<IPositionAgent> pathToRook = new LinkedList<IPositionAgent>();

		// find the next field in direction of edge
		Map<String, IPositionAgent> mpNextField = oPiece.getPosition().getAllPathAgents(oPiece.getPlayer().getBoardMapping(), Direction.EDGE, Family.IGNORE, fDirection, Rank.SAME);

		while(true)
		{
			IPositionAgent posNextField = mpNextField.values().stream().findFirst().get();
			IPieceAgent pcNextField = posNextField.getPiece();
			
			System.out.println("__Checking Field For Castling Move: " + posNextField.getName());
			
			// if its empty then set king and rook target positions
			if (posNextField.getPiece() == null) {
				possibleRookTarget = possibleKingTarget; // use the previous king target as the new rook target
				possibleKingTarget = posNextField;
				pathToRook.add(posNextField);
				
				// go to next field
				mpNextField = posNextField.getAllPathAgents(oPiece.getPlayer().getBoardMapping(), Direction.EDGE, Family.IGNORE, fDirection, Rank.SAME);
				
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
					System.out.println("__LOOKING AT: " + moveCandidateEntry.getName());
					// if field is endangered then quit the function
				}
				
				// if we got this far the path is safe and we can add the move candidate to the map of possible move candidates.
				IMoveCandidate oCastlingMoveKingCandidate = new MoveCandidate((IRuleAgent)oRule, oPiece, oPiece.getPosition(), possibleKingTarget);
				IMoveCandidate oCastlingMoveRookCandidate = new MoveCandidate(null, posNextField.getPiece(), posNextField, possibleRookTarget);
				System.out.println("__candidate moves created. they are: KING " + oCastlingMoveKingCandidate.getCandidatePosition().getName() + " and ROOK " + oCastlingMoveRookCandidate.getCandidatePosition().getName());
				
				oCastlingMoveKingCandidate.addSecondaryMove(oCastlingMoveRookCandidate);
				mpCandidateMovePositions.put(possibleKingTarget.getName(), oCastlingMoveKingCandidate);
				System.out.println("__candidateMoves added.");
				
				break;				
			
			}
			else {
				// no rook was found.
				break;
			}
		}
	}
	
	/**
	 * This method executes the short castling rule.
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
		for (IMoveCandidate secondaryMoveCandidate : oMoveCandidate.getSecondaryMoves()) {
			secondaryMoveCandidate.getPieceToMove().setPosition(secondaryMoveCandidate.getCandidatePosition());
			secondaryMoveCandidate.getCandidatePosition().setPiece(secondaryMoveCandidate.getPieceToMove());
			secondaryMoveCandidate.getSourcePosition().setPiece(null);
			
			oPieceLinkedToCurrentPosition.enqueuePositionHistory(oCurrentPosition);
			oActivity.addPriorMoveEntry(secondaryMoveCandidate.getSourcePosition(), secondaryMoveCandidate.getSourcePosition().getPiece());
			oActivity.addPriorMoveEntry(secondaryMoveCandidate.getCandidatePosition(), secondaryMoveCandidate.getCandidatePosition().getPiece());

			oActivity.addPostMoveEntry(secondaryMoveCandidate.getSourcePosition(), null);
			oActivity.addPostMoveEntry(secondaryMoveCandidate.getCandidatePosition(), secondaryMoveCandidate.getSourcePosition().getPiece());
		}
		oActivity.setPlayer(oPieceLinkedToCurrentPosition.getPlayer());

		return oActivity;
	}


	static void tryPawnFirstMoveException(IRuleProcessor oRuleProcessor, IBoardAgent oBoard, IPieceAgent oPiece, Map<String, IMoveCandidate> mpCandidateMovePositions) {		
		if (oPiece.getRuns() > 0) {
			return;
		}
		
		
	}
	
}
