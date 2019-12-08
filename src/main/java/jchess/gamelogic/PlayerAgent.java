package jchess.gamelogic;

import jchess.cache.PlayerData;
import jchess.common.IBoardMapping;
import jchess.common.IPlayerAgent;
import jchess.common.IPlayerData;

/**
 * This class is responsible to manage underlying "Player" (only) related data.
 * It keeps the state of the Player and facilitates game-logic with numerous operations
 * E.g. It lets game-logic about the board-positions-mapping assigned to the Player.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public class PlayerAgent implements IPlayerAgent {
	private IPlayerData m_oPlayerData;
	
	public PlayerAgent() {
		m_oPlayerData = new PlayerData();
	}
	
	public String getName()
    {
        return m_oPlayerData.getName();
    }
	
	public IPlayerData getPlayerData() {
		return m_oPlayerData;
	}
	
	public IBoardMapping getBoardMapping() {
		return m_oPlayerData.getBoardMapping();
	}
}
