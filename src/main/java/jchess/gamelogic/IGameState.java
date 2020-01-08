package jchess.gamelogic;

import java.util.Map;

import jchess.common.IMoveCandidate;
import jchess.common.IPlayerAgent;
import jchess.common.IPositionAgent;

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
	public Map<String, IMoveCandidate> getPossibleMovesForActivePosition();
	public IMoveCandidate getMoveCandidate(IPositionAgent oPosition);
	public void setPossibleMovesForActivePosition(Map<String, IMoveCandidate> lstPositions);
	public Boolean isThisActivePlayer(String stPlayerName);
	public Boolean isThisActivePosition(String stPositionName);
	public void makeLastPlayerAsCurrentPlayer(IPlayerAgent oPlayer);
}
