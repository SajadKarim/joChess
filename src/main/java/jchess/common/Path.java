package jchess.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jchess.common.enumerator.Direction;

/**
 * Path.java
 * 
 * This is data class to store a Path.
 *
 */

public class Path implements IPath{
	
	private String m_stName;
	private Direction  m_enDirection;	
	private List<Position> m_lstPosition;	
	private List<Path> m_lstNeighbour;
	
	public Path(String stName, Direction enDirection, Position oPosition) {
		m_stName = stName;
		m_enDirection = enDirection;		
		m_lstNeighbour = new ArrayList<Path>();
		m_lstPosition = new ArrayList<Position>();

		if( oPosition != null)
			addPosition(oPosition);
}
	
	public String getName() {
		return m_stName;
	}

	public Direction getDirection() {
		return m_enDirection;
	}
	
	public void addNeighbour(Path oPath) {
		m_lstNeighbour.add(oPath);
	}
	
	public void addPosition(Position oPosition) {
		m_lstPosition.add(oPosition);
	}

	public List<Path> getAllNeighbors(){
		return m_lstNeighbour;
	}
		
	public List<Position> getAllPositions() {
		return m_lstPosition;
	}
	
	public Boolean doesPositionExist(String stName) {		
		Iterator<Position> it = m_lstPosition.iterator();
    	while( it.hasNext()) {
    		Position oPosition = it.next();
    		if( oPosition != null && oPosition.getName().equals(stName))
    			return true;
    	}

    	return false;
	}
}