package jchess.gui.model.newgamewindow;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

import org.javatuples.Pair;

import jchess.common.IPlayerAgent;
import jchess.gamelogic.PlayerAgent;

/**
 * 
 * @author 	Sajad Karim
 * @since	7 Dec 2019
 */

public final class NewGameModel implements INewGameModel {
	private SortedMap<String, Pair<String, Integer>> m_mpPlayersBoardMapping; 
	
	private Map<String, IPlayerAgent> m_mpPlayer;
	
	private String m_stBoardName;
	
	public NewGameModel() {
	}
	
	public Map<String, IPlayerAgent> getPlayers() {
		return m_mpPlayer;
	}
	
	public void setPlayerBoardMapping(SortedMap<String, Pair<String, Integer>> mpPlayersBoardMapping) {
		m_mpPlayersBoardMapping = mpPlayersBoardMapping;
		m_mpPlayer = new HashMap<String, IPlayerAgent>();

		for (Map.Entry<String, Pair<String, Integer>> entry : mpPlayersBoardMapping.entrySet()) {
			for (int i = m_mpPlayer.size(); i < entry.getValue().getValue1(); i++) {
				IPlayerAgent oPlayer = new PlayerAgent();
				oPlayer.getPlayerData().setName("P" + (i + 1));
				m_mpPlayer.put(oPlayer.getName(), oPlayer);
			}
		}
	}

	public String[] getAllBoardNames() {
		String[] arBoardName = new String[m_mpPlayersBoardMapping.size()];

		int nIndex = 0;
		for (Map.Entry<String, Pair<String, Integer>> entry : m_mpPlayersBoardMapping.entrySet()) {
			arBoardName[nIndex++] = entry.getKey();
		}
		
		return arBoardName;
	}

	public int getPlayersAllowedInBoard(String stBoardName) {
		if (m_mpPlayersBoardMapping.get(stBoardName) == null) {
			return -1;
		}
		
		return m_mpPlayersBoardMapping.get(stBoardName).getValue1();
	}
	
	public Boolean updatePlayerDetails(String stPlayerId, String stFirstName, String stLastName, Image oImage) {
		IPlayerAgent oPlayer = m_mpPlayer.get(stPlayerId);
		
		if (oPlayer == null) {
			return false;
		}
		
		oPlayer.setFirstName(stFirstName);
		oPlayer.setLastName(stLastName);
		oPlayer.setImage(oImage);
		
		return true;
	}
	
	public void setSelectedBoardName(String stBoardName) {
		m_stBoardName = stBoardName;
	}

	public String getSelectedBoardName() {
		return m_stBoardName;
	}

	public String getSelectedBoardFileName() {
		return m_mpPlayersBoardMapping.get(m_stBoardName).getValue0();
	}
}
