/*
#    This program is free software: you can redistribute it and/or modify
#    it under the terms of the GNU General Public License as published by
#    the Free Software Foundation, either version 3 of the License, or
#    (at your option) any later version.
#
#    This program is distributed in the hope that it will be useful,
#    but WITHOUT ANY WARRANTY; without even the implied warranty of
#    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#    GNU General Public License for more details.
#
#    You should have received a copy of the GNU General Public License
#    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * Authors:
 * Mateusz Sławomir Lach ( matlak, msl )
 * Damian Marciniak
 */
package jchess.cache;

import jchess.common.IBoardMapping;
import jchess.common.IPlayerData;

/**
 * This is a data class to store "Player" related details in cache.
 * It actually maps XML or DB structure for "Player" to memory.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public final class PlayerData implements IPlayerData {
	/**
	 * Name of player.
	 */
	private String m_stName;
	/**
	 * Piece and position mapping of player.
	 */
	private IBoardMapping m_oBoardMapping = null;
    
	/**
	 * Default constructor.
	 */
    public PlayerData() {    	
    	m_oBoardMapping = new BoardMapping();
    }

    /**
     * Setter for name.
     */
    public void setName(String stName) {
        m_stName = stName;
    }

    /**
     * Getter for name.
     */
    public String getName() {
        return m_stName;
    }

    /**
     * Return player core player data object.
     */
    public PlayerData getPlayerData() {
		return this;
	}

    /**
     * Getter for board mappings.
     */
	public IBoardMapping getBoardMapping() {
		return m_oBoardMapping;
	}
	
	/**
	 * Add new board mapping entry.
	 */
	public void addBoardMapping(int nSource, int nDestination) {
		m_oBoardMapping.addMapping(nSource, nDestination);
	}
	
	/**
	 * Returns specific board mapping entry against provided file and rank.
	 */
	public int getBoardMapping(int nSource) {
		return m_oBoardMapping.getMapping(nSource);
	}
}
