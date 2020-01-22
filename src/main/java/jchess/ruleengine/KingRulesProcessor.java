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
		
		IRule oRule = m_oBoardFactory.createRule();		
		oRule.getRuleData().setRuleType(RuleType.MOVE_AND_CAPTURE);
		oRule.getRuleData().setDirection(Direction.EDGE);
		oRule.getRuleData().setMaxRecurrenceCount(Integer.MAX_VALUE);
		oRule.getRuleData().setFile(fDirection);
		oRule.getRuleData().setRank(Rank.SAME);
		oRule.getRuleData().setFamily(Family.IGNORE);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRule.getRuleData().setName("KingCastlingCheck");
		oRule.getRuleData().setCustomName("MOVE_TRANSIENT[KING_CASTLING_CHECK]");
		
		((IRuleAgent)oRule).reset();
		
		Queue<RuleProcessorData> qData = new LinkedList<RuleProcessorData>();
		qData.add(new RuleProcessorData((IRuleAgent)oRule, oPiece.getPosition(), null));
		
		Map<String, IMoveCandidate> mpHorizontalPathToNextPiece = new HashMap<String, IMoveCandidate>();
		oRuleProcessor.tryFindPossibleCandidateMovePositions(oPiece, oPiece.getPosition(), oPiece.getPlayer(), qData , mpHorizontalPathToNextPiece);
		
		if (mpHorizontalPathToNextPiece.isEmpty()) {
			System.out.println("Horizontal path seems to be empty");
			return;
		}
		// Get One more field in the same direction to see what was blocking.
		//Iterator<Entry<String,IMoveCandidate>> lastEntryIterator = mpHorizontalPathToNextPiece.entrySet().iterator();
		Entry<String,IMoveCandidate> lastEntry = null;
		for (Entry<String,IMoveCandidate> element : mpHorizontalPathToNextPiece.entrySet()) {
			lastEntry = element;
		}
		// the final field with the piece or edge
		Map<String, IPositionAgent> mpHorizontalPathToNextPiece_LastField = lastEntry.getValue().getCandidatePosition().getAllPathAgents(oPiece.getPlayer().getBoardMapping(), Direction.EDGE, Family.IGNORE, fDirection, Rank.IGNORE);
		IPositionAgent oPositionOfLastField = mpHorizontalPathToNextPiece_LastField.values().stream().findFirst().get();

		// If he's a rook and he has never moved get first and second entry in map (last and second-last fields in path)
		if(oPositionOfLastField.getPiece() != null && oPositionOfLastField.getPiece().getName() == "Rook" + oPiece.getFamily() && oPositionOfLastField.getPiece().getPositionHistoryCount() == 0) {
			
			IMoveCandidate possibleKingMoveCandidate = mpHorizontalPathToNextPiece.values().stream().findFirst().get();
			IMoveCandidate possibleRookMoveCandidate = mpHorizontalPathToNextPiece.values().stream().skip(1).findFirst().get();
			
			for (Entry<String, IMoveCandidate> moveCandidateEntry : mpHorizontalPathToNextPiece.entrySet()) {
				IPositionAgent fieldOnPath = moveCandidateEntry.getValue().getCandidatePosition();
				System.out.println("__LOOKING AT: " + fieldOnPath.getName());
				// if field is endangered then quit the function
			}
			// if we got this far the path is safe and we can add the move candidate to the .
			possibleKingMoveCandidate.getRule().getRuleData().setCustomName("MOVE[KING_CASTLING]");
			possibleKingMoveCandidate.addSecondaryMove(new MoveCandidate(null, oPositionOfLastField.getPiece(), oPositionOfLastField, possibleRookMoveCandidate.getCandidatePosition()));
			mpCandidateMovePositions.put(oPositionOfLastField.getName(), possibleKingMoveCandidate);
			
		}
		
		
		System.out.println("Got possible positions for king.");
		/*IPositionAgent possibleKingTarget = oPiece.getPosition();
		IPositionAgent possibleRookTarget = oPiece.getPosition();
		
		for (Entry<String, IMoveCandidate> moveCandidateEntry : mpHorizontalPathToNextPiece.entrySet()) {
			IPositionAgent fieldOnPath = moveCandidateEntry.getValue().getCandidatePosition();
			System.out.println("__LOOKING AT: " + fieldOnPath.getName());
			
			if (fieldOnPath.getPiece() == null) {
				possibleRookTarget = possibleKingTarget;
				possibleKingTarget = fieldOnPath;
			}
			else if (fieldOnPath.getPiece().getName() == "Rook" + oPiece.getFamily() && fieldOnPath.getPiece().getPositionHistoryCount() == 0) {
				// we have found a rook so the path seems to be clear.
				// TODO: check if fields are endangered
				
				moveCandidateEntry.getValue().getRule().getRuleData().setCustomName("MOVE[KING_CASTLING]");
				moveCandidateEntry.getValue().addSecondaryMove(new MoveCandidate(null, fieldOnPath.getPiece(), fieldOnPath, possibleRookTarget));
				mpCandidateMovePositions.put(fieldOnPath.getName(), moveCandidateEntry.getValue());
			}
		}*/
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
		
		// move the rook
		for (IMoveCandidate secondaryMoveCandidate : oMoveCandidate.getSecondaryMoves()) {
			oActivity.addPriorMoveEntry(secondaryMoveCandidate.getSourcePosition(), secondaryMoveCandidate.getSourcePosition().getPiece());
			oActivity.addPriorMoveEntry(secondaryMoveCandidate.getCandidatePosition(), secondaryMoveCandidate.getCandidatePosition().getPiece());

			oActivity.addPostMoveEntry(secondaryMoveCandidate.getSourcePosition(), null);
			oActivity.addPostMoveEntry(secondaryMoveCandidate.getCandidatePosition(), secondaryMoveCandidate.getSourcePosition().getPiece());
		}

		return oActivity;
	}


	static void tryPawnFirstMoveException(IRuleProcessor oRuleProcessor, IBoardAgent oBoard, IPieceAgent oPiece, Map<String, IMoveCandidate> mpCandidateMovePositions) {		
		if (oPiece.getRuns() > 0) {
			return;
		}
		
		
	}
	
}
