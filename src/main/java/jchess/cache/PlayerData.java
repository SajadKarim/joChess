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

public class PlayerData implements IPlayerData{

	public String m_stName;
	public IBoardMapping m_oBoardMapping = null;
    
    public PlayerData()
    {
        populateCustomBoardMapping();
    }

    PlayerData(String stName)
    {
        m_stName = stName;        
        populateCustomBoardMapping();
    }

    public void populateCustomBoardMapping() {
    	m_oBoardMapping = new BoardMapping();
    	    	
    	m_oBoardMapping.addMapping('a', 'h');
    	m_oBoardMapping.addMapping('b', 'g');
    	m_oBoardMapping.addMapping('c', 'f');
    	m_oBoardMapping.addMapping('d', 'e');
    	m_oBoardMapping.addMapping('e', 'd');
    	m_oBoardMapping.addMapping('f', 'c');
    	m_oBoardMapping.addMapping('g', 'b');
    	m_oBoardMapping.addMapping('h', 'a');

    	m_oBoardMapping.addMapping('1', '8');
    	m_oBoardMapping.addMapping('2', '7');
    	m_oBoardMapping.addMapping('3', '6');
    	m_oBoardMapping.addMapping('4', '5');
    	m_oBoardMapping.addMapping('5', '4');
    	m_oBoardMapping.addMapping('6', '3');
    	m_oBoardMapping.addMapping('7', '2');
    	m_oBoardMapping.addMapping('8', '1');
    }

    public void setName(String stName)
    {
        m_stName = stName;
    }

    public String getName()
    {
        return m_stName;
    }

    public PlayerData getPlayerData() {
		return this;
	}

	public IBoardMapping getBoardMapping() {
		return m_oBoardMapping;
	}
}
