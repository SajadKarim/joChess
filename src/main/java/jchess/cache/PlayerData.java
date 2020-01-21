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
 * This is a container class to store "Player" related details in cache.
 * It actually maps XML or DB structure for "Player" to memory.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public final class PlayerData implements IPlayerData {
	private String m_stName;
	private IBoardMapping m_oBoardMapping = null;
    
    public PlayerData() {    	
    	m_oBoardMapping = new BoardMapping();
    }

    public void setName(String stName) {
        m_stName = stName;
    }

    public String getName() {
        return m_stName;
    }

    public PlayerData getPlayerData() {
		return this;
	}

	public IBoardMapping getBoardMapping() {
		return m_oBoardMapping;
	}
	
	public void addBoardMapping(int nSource, int nDestination) {
		m_oBoardMapping.addMapping(nSource, nDestination);
	}
	
	public int getBoardMapping(int nSource) {
		return m_oBoardMapping.getMapping(nSource);
	}
}
