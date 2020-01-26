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
import jchess.ruleengine.gui.IPawnPromotionDialogView;
import jchess.ruleengine.gui.IPawnPromotionModel;
import jchess.ruleengine.gui.PawnPromotionDialogView;
import jchess.ruleengine.gui.PawnPromotionModel;
import jchess.ruleengine.gui.PawnPromotionPresenter;

/**
 * This is a custom class specific to process the custom rules that are peculiar to Pawn piece only.
 * This class defines all the custom rules that at the moments are not supportable in XML.
 *  
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public final class PawnRulesProcessor {
	private static final IBoardFactory m_oBoardFactory = new BoardAgentFactory();
	
	/**
	 * This method looks for the possibility whether the pawn is eligible to make a promotion move.
	 * 
	 * @param IBoardAgent
	 * @param IPieceAgent
	 * @param Map<String, IMoveCandidate>
	 */
	public static void tryPawnPromotionRule(IBoardAgent oBoard, IPieceAgent oPiece, Map<String, IMoveCandidate> mpCandidateMovePositions) {
		//tryPawnPromotionRuleForEdge(oBoard, oPiece, mpCandidateMovePositions);
		tryPawnPromotionRuleForVertex(oBoard, oPiece, mpCandidateMovePositions);
	}

	/* Bug Fix: 
	 * Jira Id: NOT-110
	 * Commenting out folloiwng code as pawn promotion rules should not work on edge.
	 * 
	static void tryPawnPromotionRuleForEdge(IBoardAgent oBoard, IPieceAgent oPiece, Map<String, IMoveCandidate> mpCandidateMovePositions) {
		IPositionAgent oPosition = oPiece.getPosition();
		IPlayerAgent oPlayer = oPiece.getPlayer();

		Map<String, IPositionAgent> mpPosition = oPosition.getAllPathAgents(oPlayer.getBoardMapping(), Direction.EDGE, Family.IGNORE, File.SAME, Rank.FORWARD);
		for( Map.Entry<String, IPositionAgent> it : mpPosition.entrySet()) {
			
			Map<String, IPositionAgent> mpNextPosition = it.getValue().getAllPathAgents(oPlayer.getBoardMapping(), Direction.EDGE, Family.DIFFERENT, File.SAME, Rank.FORWARD);

			if( mpNextPosition.size() == 0) {
				IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
	        	oRule.getRuleData().setRuleType(RuleType.CUSTOM);
	        	oRule.getRuleData().setCustomName("MOVE_AND_CAPTURE[PAWN_PROMOTION]");
	        	    		
	        	mpCandidateMovePositions.put(it.getValue().getName(), new MoveCandidate(oRule, oPosition.getPiece(), oPosition, it.getValue()));
			}
		}
	}
	*/
	
	/**
	 * This method looks for the possibility whether the pawn is eligible to make a promotion move. It looks of the opposite
	 * player's piece on the positions linked to its vertexes.
	 * 
	 * @param IBoardAgent
	 * @param IPieceAgent
	 * @param Map<String, IMoveCandidate>
	 */
	static void tryPawnPromotionRuleForVertex(IBoardAgent oBoard, IPieceAgent oPiece, Map<String, IMoveCandidate> mpCandidateMovePositions) {
		IPositionAgent oPosition = oPiece.getPosition();
		IPlayerAgent oPlayer = oPiece.getPlayer();

		Map<String, IPositionAgent> mpPosition = oPosition.getAllPathAgents(oPlayer.getBoardMapping(), Direction.VERTEX, Family.SAME, File.IGNORE, Rank.FORWARD);
		for (Map.Entry<String, IPositionAgent> it : mpPosition.entrySet()) {
			Map<String, IPositionAgent> mpNextPosition = it.getValue().getAllPathAgents(oPlayer.getBoardMapping(), Direction.EDGE, Family.DIFFERENT, File.SAME, Rank.FORWARD);

			if (mpNextPosition.size() == 0) {
				IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
	        	oRule.getRuleData().setRuleType(RuleType.CUSTOM);
	        	oRule.getRuleData().setCustomName("MOVE_AND_CAPTURE[PAWN_PROMOTION]");
	        	    		
	        	mpCandidateMovePositions.put(it.getValue().getName(), new MoveCandidate(oRule, oPosition.getPiece(), oPosition, it.getValue()));
			}
		}
	}

	/**
	 * This method executes pawn promotion rule.
	 * 
	 * @param IBoardAgent
	 * @param IGUIHandle
	 * @param IMoveCandidate
	 * @return IBoardActivity
	 */
	public static IBoardActivity tryExecutePawnPromotionRule(IBoardAgent oBoard, IGUIHandle oGUIHandle, IMoveCandidate oMoveCandidate) {
		IBoardActivity oActivity = new BoardActivity(oMoveCandidate);
		
		IPieceAgent oSourcePiecePriorMove = oMoveCandidate.getSourcePosition().getPiece();
		IPieceAgent oDestinationPiecePriorMove = oMoveCandidate.getCandidatePosition().getPiece();

		IPlayerAgent oPlayer = oSourcePiecePriorMove.getPlayer();
	
		// TODO: For the time being I am skipping unit test for the following code.
		// How To: Need to move the following window launching code to a separate file, so that we can extend it for unittest and
		// pass a default piece to be selected to continue with the unit test.
		IPawnPromotionModel oData = new PawnPromotionModel(oBoard.getAllUnlinkedPieceAgents(), oSourcePiecePriorMove);
		IPawnPromotionDialogView oView = new PawnPromotionDialogView(oGUIHandle.getGUIMainFrame(), oData.getViewData());
		IPresenter oPresenter = new PawnPromotionPresenter(oView, oData, null);		
		oGUIHandle.showDialog(oPresenter);
		
		if (oData.getSelectedPiece() == null) {
			return null;
		}

		oMoveCandidate.getSourcePosition().getPiece().setPosition(null);
		
		IPieceAgent oPiece = (IPieceAgent)oData.getSelectedPiece().clone();
		oPiece.setPlayer(oPlayer);
		
		oMoveCandidate.getSourcePosition().setPiece(oPiece);
		setPositionsAndUpdateActivity(oMoveCandidate.getSourcePosition(), oMoveCandidate.getCandidatePosition(), oActivity);
		
		return oActivity;
	}	

	/**
	 * This method looks for the possibility whether the pawn is eligible to jump one position on its first move or not.
	 * 
	 * @param IRuleProcessor
	 * @param IBoardAgent
	 * @param IPieceAgent
	 * @param Map<String, IMoveCandidate> 
	 */
	static void tryPawnFirstMoveException(IRuleProcessor oRuleProcessor, IBoardAgent oBoard, IPieceAgent oPiece, Map<String, IMoveCandidate> mpCandidateMovePositions) {		
		if (oPiece.getRuns() > 0) {
			return;
		}
		
		IRule oRule = m_oBoardFactory.createRule();		
		oRule.getRuleData().setRuleType(RuleType.CUSTOM);
		oRule.getRuleData().setDirection(Direction.EDGE);
		oRule.getRuleData().setMaxRecurrenceCount(1);
		oRule.getRuleData().setFile(File.SAME);
		oRule.getRuleData().setRank(Rank.FORWARD);
		oRule.getRuleData().setFamily(Family.IGNORE);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.BLINKER);
		oRule.getRuleData().setName("PawnFirstMoveException");
		oRule.getRuleData().setCustomName("MOVE_TRANSIENT[PAWN_FIRST_MOVE_EXCEPTION]");
		
		IRule oInnerRule = m_oBoardFactory.createRule();		
		oInnerRule.getRuleData().setRuleType(RuleType.CUSTOM);
		oInnerRule.getRuleData().setDirection(Direction.EDGE);
		oInnerRule.getRuleData().setMaxRecurrenceCount(1);
		oInnerRule.getRuleData().setFile(File.SAME);
		oInnerRule.getRuleData().setRank(Rank.FORWARD);
		oInnerRule.getRuleData().setFamily(Family.IGNORE);
		oInnerRule.getRuleData().setManoeuvreStrategy(Manoeuvre.BLINKER);
		oInnerRule.getRuleData().setName("PawnFirstMoveException_InnerRule");
		oInnerRule.getRuleData().setCustomName("MOVE[PAWN_FIRST_MOVE_EXCEPTION]");
		
		oRule.getRuleData().addRule(oInnerRule);
		((IRuleAgent)oRule).reset();
		
		Queue<RuleProcessorData> qData = new LinkedList<RuleProcessorData>();
		qData.add(new RuleProcessorData((IRuleAgent)oRule, oPiece.getPosition(), null));
		oRuleProcessor.tryFindPossibleCandidateMovePositions(oPiece, oPiece.getPosition(), oPiece.getPlayer(), qData , mpCandidateMovePositions);
}
	
	/**
	 * This method let pawn to jump one piece on its first move.
	 * 
	 * @param IBoardAgent
	 * @param IGUIHandle
	 * @param IMoveCandidate
	 * @return IBoardActivity
	 */
	public static IBoardActivity tryExecutePawnFirstMoveException(IBoardAgent oBoard, IMoveCandidate oMoveCandidate) {
		IBoardActivity oActivity = null;
		
		if (oMoveCandidate.getCandidatePosition().getPiece() == null) {
			oActivity = new BoardActivity(oMoveCandidate);
			
			setPositionsAndUpdateActivity(oMoveCandidate.getSourcePosition(), oMoveCandidate.getCandidatePosition(), oActivity);
		}
		
		return oActivity;
	}
	
	/**
	 * This method checks the eligibility of EnPassant rule.
	 * 
	 * @param IBoardAgent
	 * @param IPieceAgent
	 * @param Map<String, IMoveCandidate>
	 */
	public static void tryPawnEnPassantRule(IBoardAgent oBoard, IPieceAgent oPiece, Map<String, IMoveCandidate> mpCandidateMovePositions) {
		IPositionAgent oPosition = oPiece.getPosition();
		IPlayerAgent oPlayer = oPiece.getPlayer();

		Map<String, IPositionAgent> mpPosition = oPosition.getAllPathAgents(oPlayer.getBoardMapping(), Direction.EDGE, Family.DIFFERENT, File.IGNORE, Rank.SAME);
		for (Map.Entry<String, IPositionAgent> it : mpPosition.entrySet()) {
			IPositionAgent oTargetPosition = it.getValue();
			IPieceAgent oTargetPiece = oTargetPosition.getPiece();
			
			// Checks the target piece should not belong to the same player.
			if (oTargetPiece != null && oTargetPiece.getPlayer() != oPiece.getPlayer()) { 
				
				// Fetching the player's last activity to see which piece the player moved in its last turn.
				IBoardActivity oActivity = oBoard.getLastActivityByPlayer(oTargetPiece.getPlayer());
				if (oActivity == null) {
					continue;
				}
				
				IMoveCandidate oMoveCandidate =  oActivity.getMoveCandidate();
				if (oMoveCandidate == null) {
					// Abnormal state: Log error.
					continue;
				}
				
				// Checking if this was the same piece that was moved two positions ahead in the last move by the player.
				if (!oMoveCandidate.getPieceToMove().equals(oTargetPiece)) {
					continue;
				}
				
				 /* 
				  * Background: Pawn piece can jump to two places ahead on its first move only but by doing this it cannot escape the capture. 
				  * Therefore, if Pawn make two jumps on its first move to escape capture by other player's pawn then the rule En-Passant let 
				  * other players to capture, if possible, the Pawn on their very next move (only).
				  * The following check verifies whether the piece moved two positions ahead in its first move or not.
				  */
				if (Math.abs(oMoveCandidate.getSourcePosition().getRank() - oMoveCandidate.getCandidatePosition().getRank()) == 2) {
					IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
		        	oRule.getRuleData().setRuleType(RuleType.CUSTOM);
		        	oRule.getRuleData().setCustomName("MOVE_IFF_CAPTURE_POSSIBLE[PAWN_ENPASSANT]");
		        	    		
		        	mpCandidateMovePositions.put(it.getValue().getName(), new MoveCandidate(oRule, oPosition.getPiece(), oPosition, it.getValue()));
				}
			}
		}
	}

	/**
	 * This method executes EnPassant rule.
	 * 
	 * @param IBoardAgent
	 * @param IMoveCandidate
	 * @return IBoardActivity
	 */
	public static IBoardActivity tryExecutePawnEnPassantRule(IBoardAgent oBoard, IMoveCandidate oMoveCandidate) {
		IBoardActivity oActivity = new BoardActivity(oMoveCandidate);

		setPositionsAndUpdateActivity(oMoveCandidate.getSourcePosition(), oMoveCandidate.getCandidatePosition(), oActivity);
		
		return oActivity;
	}

	private static void setPositionsAndUpdateActivity(IPositionAgent oCurrentPosition, IPositionAgent oCandidatePosition, IBoardActivity oActivity) {
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
}
