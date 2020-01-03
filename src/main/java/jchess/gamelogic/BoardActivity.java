package jchess.gamelogic;

import java.util.HashMap;
import java.util.Map;

import jchess.common.IBoardActivity;
import jchess.common.IMoveCandidate;
import jchess.common.IPieceAgent;
import jchess.common.IPlayerAgent;
import jchess.common.IPositionAgent;
import jchess.common.IRuleAgent;

/**
 * This is a data class that stores board activity that is generated against a move.
 * 
 * @author	Sajad Karim
 * @since	29th Dec 2019
 */

public class BoardActivity implements IBoardActivity {
	private String m_stToString;
	private IPlayerAgent m_oPlayer;
	private Map<IPositionAgent, IPieceAgent> m_mpDetailsBeforeMove;
	private Map<IPositionAgent, IPieceAgent> m_mpDetailsAfterMove;
	
	private IMoveCandidate m_oMoveCandidate;

	public BoardActivity(IMoveCandidate oMoveCandidacy) {
		m_oMoveCandidate = oMoveCandidacy;
		
		m_stToString = "";
		m_mpDetailsBeforeMove = new HashMap<IPositionAgent, IPieceAgent>();
		m_mpDetailsAfterMove = new HashMap<IPositionAgent, IPieceAgent>();
	}
	
	public Map<IPositionAgent, IPieceAgent> getPriorMoveDetails(){
		return m_mpDetailsBeforeMove;
	}
	
	public void addPriorMoveEntry(IPositionAgent oPosition, IPieceAgent oPiece){
		m_mpDetailsBeforeMove.put(oPosition, oPiece);
	}
	
	public Map<IPositionAgent, IPieceAgent> getPostMoveDetails(){
		return m_mpDetailsAfterMove;
	}
	
	public void addPostMoveEntry(IPositionAgent oPosition, IPieceAgent oPiece){
		m_mpDetailsAfterMove.put(oPosition, oPiece);
	}

	public void setPlayer(IPlayerAgent oPlayer) {
		m_oPlayer = oPlayer;
	}
	
	public IPlayerAgent getPlayer() {
		return m_oPlayer;
	}
	
	@Override
	public String toString() {
		if( m_stToString.isEmpty()) {
		switch( m_oMoveCandidate.getRule().getRuleType()) {
			case MOVE:
				m_stToString 
					= m_oPlayer.getName() 
					+ " - from:" 
					+ m_oMoveCandidate.getSourcePosition().getName() 
					+ ", to:" 
					+ m_oMoveCandidate.getCandidatePosition().getName();
				break;
			case MOVE_AND_CAPTURE:
				m_stToString 
					= m_oPlayer.getName() 
					+ " - from:" 
					+ m_oMoveCandidate.getSourcePosition().getName() 
					+ ", to:" 
					+ m_oMoveCandidate.getCandidatePosition().getName();
				break;
			case MOVE_IFF_CAPTURE_POSSIBLE:
				m_stToString 
					= m_oPlayer.getName() 
					+ " - from:" 
					+ m_oMoveCandidate.getSourcePosition().getName() 
					+ ", to:" 
					+ m_oMoveCandidate.getCandidatePosition().getName();
				break;
			case MOVE_TRANSIENT:
				break;
			case CUSTOM:{
				switch( m_oMoveCandidate.getRule().getCustomName()) {
				case "MOVE_AND_CAPTURE[PAWN_PROMOTION]": 
					m_stToString 
						= m_oPlayer.getName() 
						+ " - from:" 
						+ m_oMoveCandidate.getSourcePosition().getName() 
						+ ", to:" 
						+ m_oMoveCandidate.getCandidatePosition().getName();
					break;
				case "MOVE[PAWN_FIRST_MOVE_EXCEPTION]":
					m_stToString 
						= m_oPlayer.getName() 
						+ " - from:" 
						+ m_oMoveCandidate.getSourcePosition().getName() 
						+ ", to:" 
						+ m_oMoveCandidate.getCandidatePosition().getName();
					break;
				case "MOVE_IFF_CAPTURE_POSSIBLE[PAWN_ENPASSANT]":
					m_stToString 
						= m_oPlayer.getName() 
						+ " - from:" 
						+ m_oMoveCandidate.getSourcePosition().getName() 
						+ ", to:" 
						+ m_oMoveCandidate.getCandidatePosition().getName();
					break;
				default:
					break;
				}
				
			}
			default:
				break;
			}
		}
		return m_stToString;
	}
	
	// TODO: Include other details in the following log information.
	public String toLog() {
		return String.format("MoveCandidate=%s, Player=%s"
				, m_oMoveCandidate.toLog()
				, m_oPlayer == null ? "NULL" : m_oPlayer.getName());
	}
}
