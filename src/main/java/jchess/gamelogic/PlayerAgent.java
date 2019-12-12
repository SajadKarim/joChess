package jchess.gamelogic;

import java.awt.Image;

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
	private String m_stFirstName;
	private String m_stLastName;
	private Image m_oImage;
	
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
	

	public void setFirstName(String stName) {
		m_stFirstName = stName;
	}


	public String getFirstName() {
		return m_stFirstName;
	}


	public void setLastName(String stName) {
		m_stLastName = stName;
	}
	

	public String getLastName() {
		return m_stLastName;
	}


	public void setImage(Image oImage) {
		m_oImage = oImage;
	}


	public Image getImage() {
		return m_oImage;
	}
}
