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
		
		if (oPiece.getPositionHistoryCount() > 0) {
			return;
		}
		tryShortCastlingRule(oBoard, oPiece, mpCandidateMovePositions);
		tryLongCastlingRule(oBoard, oPiece, mpCandidateMovePositions);
		
	
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
		// if rook is at the right square and has never moved
		// and if path is clear
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
		// if rook is at the left square and has never moved
		// and if path is clear
		// and path is safe
		// then display the possible move
	}
	
	/**
	 * This method executes the short castling rule.
	 * 
	 * @param IBoardAgent
	 * @param IMoveCandidate
	 * @return IBoardActivity
	 */
	public static IBoardActivity tryExecuteShortCastlingRule(IBoardAgent oBoard, IMoveCandidate oMoveCandidate) {
		return null;
	}

	/**
	 * This method executes the long castling rule.
	 * 
	 * @param IBoardAgent
	 * @param IMoveCandidate
	 * @return IBoardActivity
	 */
	public static IBoardActivity tryExecuteLongCastlingRule(IBoardAgent oBoard, IMoveCandidate oMoveCandidate) {
		return null;
	}
}
