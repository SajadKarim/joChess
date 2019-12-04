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

public class PathData implements IPathData{
	
	private String m_stName;
	private Direction  m_enDirection;	
	private List<IPositionData> m_lstPosition;	
	private List<IPathData> m_lstNeighbour;
	
	public PathData(String stName, Direction enDirection, IPositionData oPosition) {
		m_stName = stName;
		m_enDirection = enDirection;		
		m_lstNeighbour = new ArrayList<IPathData>();
		m_lstPosition = new ArrayList<IPositionData>();

		if( oPosition != null)
			addPosition(oPosition);
}
	
	public String getName() {
		return m_stName;
	}

	public Direction getDirection() {
		return m_enDirection;
	}
	
	public void addNeighbour(IPathData oPath) {
		m_lstNeighbour.add(oPath);
	}
	
	public void addPosition(IPositionData oPosition) {
		m_lstPosition.add(oPosition);
	}

	public List<IPathData> getAllNeighbors(){
		return m_lstNeighbour;
	}
		
	public List<IPositionData> getAllPositions() {
		return m_lstPosition;
	}
	
	public Boolean doesPositionExist(String stName) {		
		Iterator<IPositionData> it = m_lstPosition.iterator();
    	while( it.hasNext()) {
    		IPositionData oPosition = it.next();
    		if( oPosition != null && oPosition.getName().equals(stName))
    			return true;
    	}

    	return false;
	}
}