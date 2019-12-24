package jchess.gamelogic;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.javatuples.Pair;

import com.google.inject.Inject;

import jchess.common.IMoveCandidacy;
import jchess.common.IPlayerAgent;
import jchess.common.IPositionAgent;
import jchess.common.IRuleAgent;
import jchess.gui.model.gamewindow.IGameModel;

/**
 * This class is container to hold current state of the Board.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public class GameState implements IGameState {
	private IPlayerAgent m_oActivePlayer;
	private IPositionAgent m_oSelectedPiece;
	private Map<String, IMoveCandidacy> m_lstPossibleMovePositionsForSelectedPiece;
	private Queue<IPlayerAgent> m_qPlayersInQueue;
	
	@Inject
	public GameState(IGameModel oGameModel) {
		m_qPlayersInQueue = new LinkedList<IPlayerAgent>();
		
		for (Map.Entry<String,IPlayerAgent> entry : oGameModel.getAllPlayerAgents().entrySet()) {
			m_qPlayersInQueue.add(entry.getValue());
		}
		
		switchPlayTurn();
	}
	
	public IPositionAgent getActivePosition() {
		return m_oSelectedPiece;
	}

	public void setActivePosition(IPositionAgent oSelectedPiece) {
		m_oSelectedPiece = oSelectedPiece;
	}
	/**
	 * Get the player that is currently active
	 * @return the active player
	 */
	public IPlayerAgent getActivePlayer() {
		return m_oActivePlayer;
	}
	/**
	 * Switch the turn of the player
	 */
	public void switchPlayTurn() {
		m_oActivePlayer = m_qPlayersInQueue.poll();
		m_qPlayersInQueue.add(m_oActivePlayer);
		
		m_oSelectedPiece = null;
		m_lstPossibleMovePositionsForSelectedPiece = null;
	}

	public Map<String, IMoveCandidacy> getPossibleMovesForActivePosition() {
		return m_lstPossibleMovePositionsForSelectedPiece;
	}

	public void setPossibleMovesForActivePosition(Map<String, IMoveCandidacy> lstPositions) {
		m_lstPossibleMovePositionsForSelectedPiece = lstPositions;
	}

	public IMoveCandidacy doesPositionExistsInMoveCandidates(IPositionAgent oPosition) {
		if( m_lstPossibleMovePositionsForSelectedPiece.get(oPosition.getName()) != null)
			return m_lstPossibleMovePositionsForSelectedPiece.get(oPosition.getName());
		return null;
	}
	
	public Boolean isThisActivePlayer(String stPlayerName) {
		return m_oActivePlayer.getName().equals(stPlayerName);
	}
	
	public Boolean isThisActivePosition(String stPositionName) {
		return m_oSelectedPiece.getName().equals(stPositionName);
	}
	
	public void makeLastPlayerAsCurrentPlayer(IPlayerAgent oPlayer) {
		while( !m_qPlayersInQueue.peek().equals(oPlayer)) {
			switchPlayTurn();
		}
	}
}
