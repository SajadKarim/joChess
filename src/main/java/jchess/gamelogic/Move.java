package jchess.gamelogic;

import java.util.HashMap;
import java.util.Map;

import jchess.common.IMove;
import jchess.common.IMoveCandidacy;
import jchess.common.IPieceAgent;
import jchess.common.IPlayerAgent;

/**
 * This class holds the information regarding a move.
 * It helps Game in performing Redo and Undo operations.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public class Move implements IMove {
	private IMoveCandidacy m_oMoveCandidacy;

	private IPlayerAgent m_oPlayer;
	private String m_stMoveDisplayString;
	private Boolean m_bIsMoveSuccessful;
	private Map<String, IPieceAgent> m_mpDetailsBeforeMove;
	private Map<String, IPieceAgent> m_mpDetailsAfterMove;
	
	public Move(IMoveCandidacy oMoveCandidacy) {
		m_oMoveCandidacy = oMoveCandidacy;
		
		m_stMoveDisplayString = "";
		m_bIsMoveSuccessful = false;
		m_mpDetailsBeforeMove = new HashMap<String, IPieceAgent>();
		m_mpDetailsAfterMove = new HashMap<String, IPieceAgent>();
	}
		
	public Boolean IsMoveSuccessful() {
		return m_bIsMoveSuccessful;
	}
	
	public void setMoveSuccessState(Boolean bIsMoveSuccessful) {
		m_bIsMoveSuccessful = bIsMoveSuccessful;
	}

	public String getMoveDisplayString() {
		return m_stMoveDisplayString;
	}
	
	public void setMoveDisplayString(String stMoveDisplayString) {
		m_stMoveDisplayString = stMoveDisplayString;
	}

	public Map<String, IPieceAgent> getPriorMoveDetails(){
		return m_mpDetailsBeforeMove;
	}
	
	public void addPriorMoveEntry(String stPositionId, IPieceAgent oPiece){
		m_mpDetailsBeforeMove.put(stPositionId, oPiece);
	}
	
	public Map<String, IPieceAgent> getPostMoveDetails(){
		return m_mpDetailsAfterMove;
	}
	
	public void addPostMoveEntry(String stPositionId, IPieceAgent oPiece){
		m_mpDetailsAfterMove.put(stPositionId, oPiece);
	}

	public void setPlayer(IPlayerAgent oPlayer) {
		m_oPlayer = oPlayer;
	}
	
	public IPlayerAgent getPlayer() {
		return m_oPlayer;
	}
	
	@Override
	public String toString() {
		String stReturnValue = "";
		switch( m_oMoveCandidacy.getRule().getRuleType()) {
		case MOVE:
			stReturnValue 
				= m_oPlayer.getName() 
				+ " - from:" 
				+ m_oMoveCandidacy.getSourcePosition().getName() 
				+ ", to:" 
				+ m_oMoveCandidacy.getCandidatePosition().getName();
			break;
		case MOVE_AND_CAPTURE:
			stReturnValue 
				= m_oPlayer.getName() 
				+ " - from:" 
				+ m_oMoveCandidacy.getSourcePosition().getName() 
				+ ", to:" 
				+ m_oMoveCandidacy.getCandidatePosition().getName();
			break;
		case MOVE_IFF_CAPTURE_POSSIBLE:
			stReturnValue 
				= m_oPlayer.getName() 
				+ " - from:" 
				+ m_oMoveCandidacy.getSourcePosition().getName() 
				+ ", to:" 
				+ m_oMoveCandidacy.getCandidatePosition().getName();
			break;
		case MOVE_TRANSIENT:
			break;
		case CUSTOM:{
			switch( m_oMoveCandidacy.getRule().getCustomName()) {
			case "MOVE_AND_CAPTURE[PAWN_PROMOTION]": 
				stReturnValue 
					= m_oPlayer.getName() 
					+ " - from:" 
					+ m_oMoveCandidacy.getSourcePosition().getName() 
					+ ", to:" 
					+ m_oMoveCandidacy.getCandidatePosition().getName();
				break;
			case "MOVE[PAWN_FIRST_MOVE_EXCEPTION]":
				stReturnValue 
					= m_oPlayer.getName() 
					+ " - from:" 
					+ m_oMoveCandidacy.getSourcePosition().getName() 
					+ ", to:" 
					+ m_oMoveCandidacy.getCandidatePosition().getName();
				break;
			default:
				break;
			}
			
		}
		default:
			break;
		}
		return stReturnValue;
	}
	
	// TODO: Include other details in the following log infomation.
	public String toLog() {
		return String.format("IsSusseccul=%s, MoveCandidate=%s, Player=%s"
				, m_bIsMoveSuccessful
				, m_oMoveCandidacy.toLog()
				, m_oPlayer == null ? "NULL" : m_oPlayer.getName());
	}
}
