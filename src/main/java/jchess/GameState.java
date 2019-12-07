package jchess;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.javatuples.Pair;

import jchess.common.IPlayer;
import jchess.common.IPosition;
import jchess.common.IRule;
import jchess.common.Player;

public class GameState implements IGameState {

	private IPlayer m_oActivePlayer;
	private IPosition m_oSelectedPiece;
	private Map<String, Pair<IPosition, IRule>> m_lstPossibleMovePositionsForSelectedPiece;
	private Queue<IPlayer> m_qPlayersInQueue;
	
	public GameState(List<IPlayer> lstPlayers) {
		m_qPlayersInQueue = new LinkedList<IPlayer>();
		
		Iterator<IPlayer> it = lstPlayers.iterator();
		while(it.hasNext()) {
			m_qPlayersInQueue.add(it.next());
		}
		
		switchPlayTurn();
	}
	
	public IPosition getActivePosition() {
		return m_oSelectedPiece;
	}

	public void setActivePosition(IPosition oSelectedPiece) {
		m_oSelectedPiece = oSelectedPiece;
	}

	public IPlayer getActivePlayer() {
		return m_oActivePlayer;
	}

	public void switchPlayTurn() {
		m_oActivePlayer = m_qPlayersInQueue.poll();
		m_qPlayersInQueue.add(m_oActivePlayer);
	}

	public Map<String, Pair<IPosition, IRule>> getPossibleMovesForActivePosition() {
		return m_lstPossibleMovePositionsForSelectedPiece;
	}

	public void setPossibleMovesForActivePosition(Map<String, Pair<IPosition, IRule>> lstPositions) {
		m_lstPossibleMovePositionsForSelectedPiece = lstPositions;
	}

	public Pair<IPosition, IRule> doesPositionExistsInMoveCandidates(IPosition oPosition) {
		if( m_lstPossibleMovePositionsForSelectedPiece.get(oPosition.getName()) != null)
			return m_lstPossibleMovePositionsForSelectedPiece.get(oPosition.getName());
		return null;
	}
}
