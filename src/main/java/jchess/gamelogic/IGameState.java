package jchess.gamelogic;

import java.util.Map;

import org.javatuples.Pair;

import jchess.common.IMoveCandidacy;
import jchess.common.IPlayerAgent;
import jchess.common.IPositionAgent;
import jchess.common.IRuleAgent;

/**
 * This provides abstraction to the current state of the data.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public interface IGameState {
	public IPositionAgent getActivePosition();
	public IPlayerAgent getActivePlayer();
	public void setActivePosition(IPositionAgent oSelectedPiece);
	public void switchPlayTurn();
	public Map<String, IMoveCandidacy> getPossibleMovesForActivePosition();
	public IMoveCandidacy getMoveCandidate(IPositionAgent oPosition);
	public void setPossibleMovesForActivePosition(Map<String, IMoveCandidacy> lstPositions);
	public Boolean isThisActivePlayer(String stPlayerName);
	public Boolean isThisActivePosition(String stPositionName);
	public void makeLastPlayerAsCurrentPlayer(IPlayerAgent oPlayer);
}
