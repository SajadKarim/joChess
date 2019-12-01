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
package jchess.common;

import java.io.Serializable;
import java.util.Hashtable;

import jchess.*;


/**
 * Class representing the player in the game
 */
public class Player implements Serializable
{

	public String name;
	public IBoardMapping m_oBoardMapping = null;
    
    public enum colors
    {

        white, black
    }
    public colors color;

    public enum playerTypes
    {

        localUser, networkUser, computer
    }
    public playerTypes playerType;
    public boolean goDown;

    public Player()
    {
        populateCustomBoardMapping();
    }

    public Player(String name, String color)
    {
        this.name = name;
        this.color = colors.valueOf(color);
        this.goDown = false;
        
        populateCustomBoardMapping();
    }

    public void populateCustomBoardMapping() {
    	m_oBoardMapping = new BoardMapping();
    	
    	if( color == colors.white)
    		return;
    	
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

    
    /** Method setting the players name
     *  @param name name of player
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /** Method getting the players name
     *  @return name of player
     */
    public String getName()
    {
        return this.name;
    }

    /** Method setting the players type
     *  @param type type of player - enumerate
     */
    public void setType(playerTypes type)
    {
        this.playerType = type;
    }
}
