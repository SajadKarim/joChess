package jchess.gamelogic;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import jchess.cache.PlayerData;
import jchess.common.IBoardMapping;
import jchess.common.IPieceAgent;
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

public final class PlayerAgent implements IPlayerAgent {
	private IPlayerData m_oPlayerData;
	private String m_stFirstName;
	private String m_stLastName;
	private Image m_oImage;
	private Map<String, IPieceAgent> m_mpPieces;
	private int m_nRemainingGameTimeInSec;
	
	public PlayerAgent() {
		m_oPlayerData = new PlayerData();
		m_mpPieces = new HashMap<String, IPieceAgent>();
		m_nRemainingGameTimeInSec = 30 * 60;	//TODO: #REFACTOR move this value in config file.
	}
	
	public String getName() {
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
	
	public void addPiece(String stPieceCustomName, IPieceAgent oPiece) {
		m_mpPieces.put(stPieceCustomName, oPiece);
	}

	public IPieceAgent getPiece(String stName) {
		return m_mpPieces.get(stName);
	}

	public Map<String, IPieceAgent> getAllPieces() {
		return m_mpPieces;
	}
	
	public IPieceAgent getKingPiece() {
		String[] KingArray = new String[] {"Black", "White", "Red"};
		String stKingName = "";
		IPieceAgent oKingPiece = null;
		for (String stKingColour : KingArray) {
			stKingName = "King" + stKingColour;
			IPieceAgent tempPiece = getPiece(stKingName);
			if (tempPiece != null) {
				oKingPiece = tempPiece;
				return oKingPiece;
			}
		}
		return oKingPiece;
	}

	public int getRemainingTimeInSec() {
		return m_nRemainingGameTimeInSec;
	}

	public void setRemainingTimeInSec(int nRemainingTimeInSec) {
		m_nRemainingGameTimeInSec = nRemainingTimeInSec;	
	}
}
