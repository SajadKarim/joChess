package jchess.common;

import jchess.common.PlayerData.colors;
import jchess.common.PlayerData.playerTypes;

public class Player implements IPlayerData {
	private PlayerData m_oPlayerData;
	
	public Player() {
		m_oPlayerData = new PlayerData();
	}
	
	public Player(PlayerData oPlayerData) {
		m_oPlayerData = oPlayerData;
	}
	
	public Player(Player oPlayer) {
		m_oPlayerData = new PlayerData(oPlayer.m_oPlayerData);
	}

	public String getName()
    {
        return m_oPlayerData.getName();
    }
	
	public colors getColor()
    {
        return m_oPlayerData.color;
    }

	public playerTypes getplayertype()
    {
        return m_oPlayerData.playerType;
    }

	public PlayerData getPlayerData() {
		return m_oPlayerData;
	}
	
    public void setName(String name)
    {
        m_oPlayerData.setName(name);
    }

    public void setColor(String stName) {
    	m_oPlayerData.setColor(stName);
    }
    /** Method getting the players name
     *  @return name of player
     */
    public void setType(playerTypes type)
    {
        m_oPlayerData.setType(type);
    }
    
}
