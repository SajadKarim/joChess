package jchess.ruleengine;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.javatuples.Pair;

import jchess.common.IBoardAgent;
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
import jchess.gui.IGUIHandle;
import jchess.ruleengine.gui.IPawnPromotionDialogView;
import jchess.ruleengine.gui.IPawnPromotionModel;
import jchess.ruleengine.gui.PawnPromotionDialogView;
import jchess.ruleengine.gui.PawnPromotionModel;
import jchess.ruleengine.gui.PawnPromotionPresenter;

public class PawnRulesProcessor {
	public static void tryPawnPromotionRule(IBoardAgent oBoard, IPositionAgent oSelectedPosition, Map<String,Pair<IPositionAgent, IRuleAgent>> mpCandidateMovePositions) {
		tryPawnPromotionRuleForEdge(oBoard, oSelectedPosition, mpCandidateMovePositions);
		tryPawnPromotionRuleForVertex(oBoard, oSelectedPosition, mpCandidateMovePositions);
	}

	static void tryPawnPromotionRuleForEdge(IBoardAgent oBoard, IPositionAgent oSelectedPosition, Map<String,Pair<IPositionAgent, IRuleAgent>> mpCandidateMovePositions) {
		IPieceAgent oPiece = oSelectedPosition.getPiece();
		IPlayerAgent oPlayer = oPiece.getPlayer();

		Map<String, IPositionAgent> mpPosition = oSelectedPosition.getAllPathAgents(oPlayer.getBoardMapping(), Direction.EDGE, Family.IGNORE, File.SAME, Rank.FORWARD);
		for( Map.Entry<String, IPositionAgent> it : mpPosition.entrySet()) {
			
			if( it.getValue().getPiece() != null)
				continue;
			
			Map<String, IPositionAgent> mpNextPosition = it.getValue().getAllPathAgents(oPlayer.getBoardMapping(), Direction.EDGE, Family.DIFFERENT, File.SAME, Rank.FORWARD);

			if( mpNextPosition.size() == 0) {
				IRuleAgent oRule = (IRuleAgent)oBoard.createRule();
	        	oRule.getRuleData().setRuleType(RuleType.CUSTOM);
	        	oRule.getRuleData().setCustomName("PAWN_MOVE_PROMOTION");
	        	    		
	        	mpCandidateMovePositions.put(it.getValue().getName(), new Pair<IPositionAgent, IRuleAgent>(it.getValue(), oRule));
			}
		}
	}

	static void tryPawnPromotionRuleForVertex(IBoardAgent oBoard, IPositionAgent oSelectedPosition, Map<String,Pair<IPositionAgent, IRuleAgent>> mpCandidateMovePositions) {
		IPieceAgent oPiece = oSelectedPosition.getPiece();
		IPlayerAgent oPlayer = oPiece.getPlayer();

		Map<String, IPositionAgent> mpPosition = oSelectedPosition.getAllPathAgents(oPlayer.getBoardMapping(), Direction.VERTEX, Family.SAME, File.IGNORE, Rank.FORWARD);
		for( Map.Entry<String, IPositionAgent> it : mpPosition.entrySet()) {
			Map<String, IPositionAgent> mpNextPosition = it.getValue().getAllPathAgents(oPlayer.getBoardMapping(), Direction.EDGE, Family.DIFFERENT, File.SAME, Rank.FORWARD);

			if( mpNextPosition.size() == 0) {
				IRuleAgent oRule = (IRuleAgent)oBoard.createRule();
	        	oRule.getRuleData().setRuleType(RuleType.CUSTOM);
	        	oRule.getRuleData().setCustomName("PAWN_CAPTURE_PROMOTION");
	        	    		
	        	mpCandidateMovePositions.put(it.getValue().getName(), new Pair<IPositionAgent, IRuleAgent>(it.getValue(), oRule));
			}
		}
	}

	public static void tryExecutePawnPromotionRule(IBoardAgent oBoard, IGUIHandle oGUIHandle, IPositionAgent oSourcePosition, Pair<IPositionAgent, IRuleAgent> prPositionAndRule) {
		switch( prPositionAndRule.getValue1().getCustomName()) {
		case "PAWN_MOVE_PROMOTION":{
			PawnRulesProcessor.tryExecutePawnPromotionRuleForEdge(oBoard, oGUIHandle, oSourcePosition, prPositionAndRule);
		}
			break;
		case "PAWN_CAPTURE_PROMOTION":{
			PawnRulesProcessor.tryExecutePawnPromotionRuleForVertex(oBoard, oGUIHandle, oSourcePosition, prPositionAndRule);
		}
			break;
		default:
			break;
		}
	}

	static void tryExecutePawnPromotionRuleForEdge(IBoardAgent oBoard, IGUIHandle oGUIHandle, IPositionAgent oSourcePosition, Pair<IPositionAgent, IRuleAgent> prPositionAndRule) {
		IPawnPromotionModel oData = new PawnPromotionModel(oBoard.getAllPieceAgents(), oSourcePosition.getPiece());
		IPawnPromotionDialogView oView = new PawnPromotionDialogView(oGUIHandle.getGUIMainFrame(), oData.getViewData());
		IPresenter oPresenter = new PawnPromotionPresenter(oView, oData, null);		
		oGUIHandle.showDialog(oPresenter);
		
		if( oData.getSelectedPiece() == null)
			return;
		
		IPieceAgent oPiece = (IPieceAgent)oData.getSelectedPiece().clone();
		oPiece.setPlayer(oSourcePosition.getPiece().getPlayer()); 
		
		oSourcePosition.setPiece(null);
		prPositionAndRule.getValue0().setPiece(oPiece);
	}	

	static void tryExecutePawnPromotionRuleForVertex(IBoardAgent oBoard, IGUIHandle oGUIHandle, IPositionAgent oSourcePosition, Pair<IPositionAgent, IRuleAgent> prPositionAndRule) {
		IPawnPromotionModel oData = new PawnPromotionModel(oBoard.getAllPieceAgents(), oSourcePosition.getPiece());
		IPawnPromotionDialogView oView = new PawnPromotionDialogView(oGUIHandle.getGUIMainFrame(), oData.getViewData());
		IPresenter oPresenter = new PawnPromotionPresenter(oView, oData, null);		
		oGUIHandle.showDialog(oPresenter);
		
		if( oData.getSelectedPiece() == null)
			return;
		
		IPieceAgent oPiece = (IPieceAgent)oData.getSelectedPiece().clone();
		oPiece.setPlayer(oSourcePosition.getPiece().getPlayer()); 
		
		oSourcePosition.setPiece(null);
		prPositionAndRule.getValue0().setPiece(oPiece);
	}	

	static void tryPawnFirstMoveException(IRuleProcessor oRuleProcessor, IBoardAgent oBoard, IPositionAgent oPosition, Map<String,Pair<IPositionAgent, IRuleAgent>> mpCandidateMovePositions) {
		if( oPosition.getPiece().hasPieceAlreadyMadeMove())
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
		qData.add(new RuleProcessorData((IRuleAgent)oRule, oPosition, null));
		oRuleProcessor.tryFindPossibleCandidateMovePositions(oPosition.getPiece().getPlayer(), oPosition.getPiece(), qData , mpCandidateMovePositions);
	}
	
	public static void tryExecutePawnFirstMoveException(IPositionAgent oSourcePosition, Pair<IPositionAgent, IRuleAgent> oDestinationPositionAndRule) {
		if( oDestinationPositionAndRule.getValue0().getPiece() == null) {
			jchess.common.IPieceAgent oPiece = oSourcePosition.getPiece();
			oDestinationPositionAndRule.getValue0().setPiece((jchess.gamelogic.PieceAgent)oPiece);
			oSourcePosition.setPiece(null);
		}
		
		if(!oDestinationPositionAndRule.getValue0().getPiece().hasPieceAlreadyMadeMove() ) { 
			oDestinationPositionAndRule.getValue0().getPiece().recordPeiceFirstMove();
		}
	}
}
