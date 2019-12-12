package jchess.gamelogic;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.javatuples.Pair;

import com.google.inject.Inject;

import jchess.common.IPlayerAgent;
import jchess.common.IPositionAgent;
import jchess.common.IRuleAgent;

/**
 * This class is container to hold current state of the Board.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public class GameState implements IGameState {

	private IPlayerAgent m_oActivePlayer;
	private IPositionAgent m_oSelectedPiece;
	private Map<String, Pair<IPositionAgent, IRuleAgent>> m_lstPossibleMovePositionsForSelectedPiece;
	private Queue<IPlayerAgent> m_qPlayersInQueue;
	
	public GameState(Map<String, IPlayerAgent> lstPlayers) {
		m_qPlayersInQueue = new LinkedList<IPlayerAgent>();
		
		for (Map.Entry<String,IPlayerAgent> entry : lstPlayers.entrySet()) {
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

	public IPlayerAgent getActivePlayer() {
		return m_oActivePlayer;
	}

	public void switchPlayTurn() {
		m_oActivePlayer = m_qPlayersInQueue.poll();
		m_qPlayersInQueue.add(m_oActivePlayer);
		
		m_oSelectedPiece = null;
		m_lstPossibleMovePositionsForSelectedPiece = null;
	}

	public Map<String, Pair<IPositionAgent, IRuleAgent>> getPossibleMovesForActivePosition() {
		return m_lstPossibleMovePositionsForSelectedPiece;
	}

	public void setPossibleMovesForActivePosition(Map<String, Pair<IPositionAgent, IRuleAgent>> lstPositions) {
		m_lstPossibleMovePositionsForSelectedPiece = lstPositions;
	}

	public Pair<IPositionAgent, IRuleAgent> doesPositionExistsInMoveCandidates(IPositionAgent oPosition) {
		if( m_lstPossibleMovePositionsForSelectedPiece.get(oPosition.getName()) != null)
			return m_lstPossibleMovePositionsForSelectedPiece.get(oPosition.getName());
		return null;
	}
}
