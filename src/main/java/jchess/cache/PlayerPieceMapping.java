package jchess.cache;

import jchess.common.IPlayerPieceMapping;

/**
 * This is a data class that keeps information about which pieces are set to players and on what positions they have to be placed.
 * 
 * @author 	Sajad Karim
 * @since	22 Jan 2020
 */
public final class PlayerPieceMapping implements IPlayerPieceMapping {
	private String m_stPieceCustomName;
	private String m_stPieceRef;
	private String m_stPositionRef;

	public PlayerPieceMapping(String stPieceCustomName, String stPieceRef, String stPositionRef) {
		m_stPieceCustomName = stPieceCustomName;
		m_stPieceRef = stPieceRef;
		m_stPositionRef = stPositionRef;
	}
	
	public String getPieceCustomName() {
		return m_stPieceCustomName;
	}

	public String getPieceRef() {
		return m_stPieceRef;
	}

	public String getPositionRef() {
		return m_stPositionRef;
	}
}
