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
 * This is an specialized class to handle custom rules (not handled in XML yet) related to Pawn piece.
 * 
 * @author Sajad Karim
 * @since  7th Dec 2019
 */

public class PawnRulesProcessor {
	private static final IBoardFactory m_oBoardFactory = new BoardAgentFactory();
	
	public static void tryPawnPromotionRule(IBoardAgent oBoard, IPieceAgent oPiece, Map<String, IMoveCandidate> mpCandidateMovePositions) {
		tryPawnPromotionRuleForEdge(oBoard, oPiece, mpCandidateMovePositions);
		tryPawnPromotionRuleForVertex(oBoard, oPiece, mpCandidateMovePositions);
	}

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

	static void tryPawnPromotionRuleForVertex(IBoardAgent oBoard, IPieceAgent oPiece, Map<String, IMoveCandidate> mpCandidateMovePositions) {
		IPositionAgent oPosition = oPiece.getPosition();
		IPlayerAgent oPlayer = oPiece.getPlayer();

		Map<String, IPositionAgent> mpPosition = oPosition.getAllPathAgents(oPlayer.getBoardMapping(), Direction.VERTEX, Family.SAME, File.IGNORE, Rank.FORWARD);
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

	public static IBoardActivity tryExecutePawnPromotionRule(IBoardAgent oBoard, IGUIHandle oGUIHandle, IMoveCandidate oMoveCandidate) {
		IBoardActivity oActivity = new BoardActivity(oMoveCandidate);
		
		IPieceAgent oSourcePiecePriorMove = oMoveCandidate.getSourcePosition().getPiece();
		IPieceAgent oDestinationPiecePriorMove = oMoveCandidate.getCandidatePosition().getPiece();

		IPlayerAgent oPlayer = oSourcePiecePriorMove.getPlayer();
	
		// TODO: For the time being I am skipping unit test for the following code.
		// How To: Need to move the following window launching code to a separate file, so that we can extend it for unittest and
		// pass a default piece to be selected to continue with the unit test.
		IPawnPromotionModel oData = new PawnPromotionModel(oBoard.getAllPieceAgents(), oSourcePiecePriorMove);
		IPawnPromotionDialogView oView = new PawnPromotionDialogView(oGUIHandle.getGUIMainFrame(), oData.getViewData());
		IPresenter oPresenter = new PawnPromotionPresenter(oView, oData, null);		
		oGUIHandle.showDialog(oPresenter);
		
		if( oData.getSelectedPiece() == null)
			return null;
		
		IPieceAgent oSourcePieceAfterMove = (IPieceAgent)oData.getSelectedPiece().clone();
		oSourcePieceAfterMove.setPosition(oMoveCandidate.getCandidatePosition());
		oSourcePieceAfterMove.setPlayer(oPlayer); 
		
		oMoveCandidate.getSourcePosition().setPiece(null);
		oMoveCandidate.getCandidatePosition().setPiece(oSourcePieceAfterMove);
		
		oSourcePieceAfterMove.enqueuePositionHistory(oMoveCandidate.getSourcePosition());
		
		oActivity.addPriorMoveEntry(oMoveCandidate.getSourcePosition(), oSourcePiecePriorMove);
		oActivity.addPriorMoveEntry(oMoveCandidate.getCandidatePosition(), oDestinationPiecePriorMove);
		oActivity.addPostMoveEntry(oMoveCandidate.getSourcePosition(), null);
		oActivity.addPostMoveEntry(oMoveCandidate.getCandidatePosition(), oSourcePieceAfterMove);
		oActivity.setPlayer(oPlayer);

		return oActivity;
	}	

	static void tryPawnFirstMoveException(IRuleProcessor oRuleProcessor, IBoardAgent oBoard, IPieceAgent oPiece, Map<String, IMoveCandidate> mpCandidateMovePositions) {		
		if( oPiece.getRuns() > 0)
			return;
		
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
		
		IRule oInnerRule= m_oBoardFactory.createRule();		
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
	
	public static IBoardActivity tryExecutePawnFirstMoveException(IBoardAgent oBoard, IMoveCandidate oMoveCandidate) {
		IBoardActivity oActivity = null;
		
		if( oMoveCandidate.getCandidatePosition().getPiece() == null) {
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
			oActivity.addPriorMoveEntry(oNewPosition, oPieceLinkedToNewPosition );
			oActivity.addPostMoveEntry(oCurrentPosition, null );
			oActivity.addPostMoveEntry(oNewPosition, oPieceLinkedToCurrentPosition);
			oActivity.setPlayer(oPieceLinkedToCurrentPosition.getPlayer());
		}
		
		return oActivity;
	}
	
	public static void tryPawnEnPassantRule(IBoardAgent oBoard, IPieceAgent oPiece, Map<String, IMoveCandidate> mpCandidateMovePositions) {
		IPositionAgent oPosition = oPiece.getPosition();
		IPlayerAgent oPlayer = oPiece.getPlayer();

		Map<String, IPositionAgent> mpPosition = oPosition.getAllPathAgents(oPlayer.getBoardMapping(), Direction.EDGE, Family.DIFFERENT, File.IGNORE, Rank.SAME);
		for( Map.Entry<String, IPositionAgent> it : mpPosition.entrySet()) {
			IPositionAgent oNextPosition = it.getValue();
			IPieceAgent oNextPiece = oNextPosition.getPiece();
			
			if( oNextPiece != null && oNextPiece.getPlayer() != oPiece.getPlayer() && oNextPiece.getPositionHistoryCount() == 1) {
				IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
	        	oRule.getRuleData().setRuleType(RuleType.CUSTOM);
	        	oRule.getRuleData().setCustomName("MOVE_IFF_CAPTURE_POSSIBLE[PAWN_ENPASSANT]");
	        	    		
	        	mpCandidateMovePositions.put(it.getValue().getName(), new MoveCandidate(oRule, oPosition.getPiece(), oPosition, it.getValue()));
			}
		}
	}

	public static IBoardActivity tryExecutePawnEnPassantRule(IBoardAgent oBoard, IMoveCandidate oMoveCandidate) {
		IBoardActivity oActivity = new BoardActivity(oMoveCandidate);

		IPieceAgent oPieceLinkedToCurrentPosition = oMoveCandidate.getSourcePosition().getPiece();
		IPieceAgent oPieceLinkedToNewPosition = oMoveCandidate.getCandidatePosition().getPiece();
		IPositionAgent oCurrentPosition = oMoveCandidate.getSourcePosition();
		IPositionAgent oNewPosition = oMoveCandidate.getCandidatePosition();
		
		oPieceLinkedToCurrentPosition.setPosition(oNewPosition);
		oNewPosition.setPiece(oPieceLinkedToCurrentPosition);
		oCurrentPosition.setPiece(null);
		
		oPieceLinkedToCurrentPosition.enqueuePositionHistory(oCurrentPosition);
		
		oActivity.addPriorMoveEntry(oCurrentPosition, oPieceLinkedToCurrentPosition);
		oActivity.addPriorMoveEntry(oNewPosition, oPieceLinkedToNewPosition );
		oActivity.addPostMoveEntry(oCurrentPosition, null );
		oActivity.addPostMoveEntry(oNewPosition, oPieceLinkedToCurrentPosition);
		oActivity.setPlayer(oPieceLinkedToCurrentPosition.getPlayer());
		
		return oActivity;
	}

}
