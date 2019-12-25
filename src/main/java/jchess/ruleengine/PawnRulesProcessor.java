package jchess.ruleengine;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import jchess.common.IBoardAgent;
import jchess.common.IMove;
import jchess.common.IMoveCandidacy;
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
import jchess.gamelogic.Move;
import jchess.gamelogic.MoveCandidacy;
import jchess.gui.IGUIHandle;
import jchess.ruleengine.gui.IPawnPromotionDialogView;
import jchess.ruleengine.gui.IPawnPromotionModel;
import jchess.ruleengine.gui.PawnPromotionDialogView;
import jchess.ruleengine.gui.PawnPromotionModel;
import jchess.ruleengine.gui.PawnPromotionPresenter;

public class PawnRulesProcessor {
	public static void tryPawnPromotionRule(IBoardAgent oBoard, IPositionAgent oSelectedPosition, Map<String, IMoveCandidacy> mpCandidateMovePositions) {
		tryPawnPromotionRuleForEdge(oBoard, oSelectedPosition, mpCandidateMovePositions);
		tryPawnPromotionRuleForVertex(oBoard, oSelectedPosition, mpCandidateMovePositions);
	}

	static void tryPawnPromotionRuleForEdge(IBoardAgent oBoard, IPositionAgent oSelectedPosition, Map<String, IMoveCandidacy> mpCandidateMovePositions) {
		IPieceAgent oPiece = oSelectedPosition.getPiece();
		IPlayerAgent oPlayer = oPiece.getPlayer();

		Map<String, IPositionAgent> mpPosition = oSelectedPosition.getAllPathAgents(oPlayer.getBoardMapping(), Direction.EDGE, Family.IGNORE, File.SAME, Rank.FORWARD);
		for( Map.Entry<String, IPositionAgent> it : mpPosition.entrySet()) {
			
			//if( it.getValue().getPiece() != null)
			//	continue;
			
			Map<String, IPositionAgent> mpNextPosition = it.getValue().getAllPathAgents(oPlayer.getBoardMapping(), Direction.EDGE, Family.DIFFERENT, File.SAME, Rank.FORWARD);

			if( mpNextPosition.size() == 0) {
				IRuleAgent oRule = (IRuleAgent)oBoard.createRule();
	        	oRule.getRuleData().setRuleType(RuleType.CUSTOM);
	        	oRule.getRuleData().setCustomName("MOVE_AND_CAPTURE[PAWN_PROMOTION]");
	        	    		
	        	mpCandidateMovePositions.put(it.getValue().getName(), new MoveCandidacy(oSelectedPosition, it.getValue(), oRule));
			}
		}
	}

	static void tryPawnPromotionRuleForVertex(IBoardAgent oBoard, IPositionAgent oSelectedPosition, Map<String, IMoveCandidacy> mpCandidateMovePositions) {
		IPieceAgent oPiece = oSelectedPosition.getPiece();
		IPlayerAgent oPlayer = oPiece.getPlayer();

		Map<String, IPositionAgent> mpPosition = oSelectedPosition.getAllPathAgents(oPlayer.getBoardMapping(), Direction.VERTEX, Family.SAME, File.IGNORE, Rank.FORWARD);
		for( Map.Entry<String, IPositionAgent> it : mpPosition.entrySet()) {
			Map<String, IPositionAgent> mpNextPosition = it.getValue().getAllPathAgents(oPlayer.getBoardMapping(), Direction.EDGE, Family.DIFFERENT, File.SAME, Rank.FORWARD);

			if( mpNextPosition.size() == 0) {
				IRuleAgent oRule = (IRuleAgent)oBoard.createRule();
	        	oRule.getRuleData().setRuleType(RuleType.CUSTOM);
	        	oRule.getRuleData().setCustomName("MOVE_AND_CAPTURE[PAWN_PROMOTION]");
	        	    		
	        	mpCandidateMovePositions.put(it.getValue().getName(), new MoveCandidacy(oSelectedPosition, it.getValue(), oRule));
			}
		}
	}

	public static IMove tryExecutePawnPromotionRule(IBoardAgent oBoard, IGUIHandle oGUIHandle, IMoveCandidacy oMoveCandidate) {
		IMove oMove = new Move(oMoveCandidate);
		
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
			return oMove;
		
		IPieceAgent oSourcePieceAfterMove = (IPieceAgent)oData.getSelectedPiece().clone();
		oSourcePieceAfterMove.setPlayer(oPlayer); 
		
		oMoveCandidate.getSourcePosition().setPiece(null);
		oMoveCandidate.getCandidatePosition().setPiece(oSourcePieceAfterMove);
		
		oSourcePieceAfterMove.markRun();
		
		oMove.setMoveSuccessState(true);
		oMove.addPriorMoveEntry(oMoveCandidate.getSourcePosition().getName(), oSourcePiecePriorMove);
		oMove.addPriorMoveEntry(oMoveCandidate.getCandidatePosition().getName(), oDestinationPiecePriorMove);
		oMove.addPostMoveEntry(oMoveCandidate.getSourcePosition().getName(), null);
		oMove.addPostMoveEntry(oMoveCandidate.getCandidatePosition().getName(), oSourcePieceAfterMove);
		oMove.setPlayer(oPlayer);

		return oMove;
	}	

	static void tryPawnFirstMoveException(IRuleProcessor oRuleProcessor, IBoardAgent oBoard, IPositionAgent oSelectedPosition, Map<String, IMoveCandidacy> mpCandidateMovePositions) {		
		if( oSelectedPosition.getPiece().getRuns() > 0)
			return;
		
		IRule oRule = oBoard.createRule();		
		oRule.getRuleData().setRuleType(RuleType.CUSTOM);
		oRule.getRuleData().setDirection(Direction.EDGE);
		oRule.getRuleData().setMaxRecurrenceCount(1);
		oRule.getRuleData().setFile(File.SAME);
		oRule.getRuleData().setRank(Rank.FORWARD);
		oRule.getRuleData().setFamily(Family.IGNORE);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.BLINKER);
		oRule.getRuleData().setName("PawnFirstMoveException");
		oRule.getRuleData().setCustomName("MOVE_TRANSIENT[PAWN_FIRST_MOVE_EXCEPTION]");
		
		IRule oInnerRule= oBoard.createRule();		
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
		qData.add(new RuleProcessorData((IRuleAgent)oRule, oSelectedPosition, null));
		oRuleProcessor.tryFindPossibleCandidateMovePositions(oSelectedPosition.getPiece().getPlayer(), qData , mpCandidateMovePositions);
}
	
	public static IMove tryExecutePawnFirstMoveException(IMoveCandidacy oMoveCandidate) {
		IMove oMove = new Move(oMoveCandidate);
		
		if( oMoveCandidate.getCandidatePosition().getPiece() == null) {
			IPieceAgent oPiece = oMoveCandidate.getSourcePosition().getPiece();
			oMoveCandidate.getCandidatePosition().setPiece((jchess.gamelogic.PieceAgent)oPiece);
			oMoveCandidate.getSourcePosition().setPiece(null);

			oPiece.markRun();
			
			oMove.setMoveSuccessState(true);
			oMove.addPriorMoveEntry(oMoveCandidate.getSourcePosition().getName(), oPiece);
			oMove.addPriorMoveEntry(oMoveCandidate.getCandidatePosition().getName(), null);
			oMove.addPostMoveEntry(oMoveCandidate.getSourcePosition().getName(), null);
			oMove.addPostMoveEntry(oMoveCandidate.getCandidatePosition().getName(), oPiece);
			oMove.setPlayer(oPiece.getPlayer());
		}
		
		return oMove;
	}
}
