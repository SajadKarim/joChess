package jchess.cache;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jchess.common.IPath;
import jchess.common.IPathAgent;
import jchess.common.IPathData;
import jchess.common.IPosition;
import jchess.common.IPositionAgent;
import jchess.common.IPositionData;
import jchess.common.enumerator.Direction;

/**
 * This is a container class to store "Path or Direction" related details in cache.
 * It actually maps XML or DB structure for "Path or Direction" to memory.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public final class Path implements IPathData, IPathAgent {	
	private String m_stName;
	private Direction  m_enDirection;	
	private List<IPath> m_lstNeighbour;
	private List<IPosition> m_lstPosition;	
	
	public Path(String stName, Direction enDirection, IPositionData oPosition) {
		m_stName = stName;
		m_enDirection = enDirection;		
		m_lstNeighbour = new ArrayList<IPath>();
		m_lstPosition = new ArrayList<IPosition>();

		if (oPosition != null) {
			addPosition(oPosition);
		}
	}
	
	public Path(Path oPath) {
		m_stName = oPath.m_stName;
		m_enDirection = oPath.m_enDirection;		

		m_lstNeighbour = new ArrayList<IPath>();
		
		for (IPath oInnerPath : oPath.m_lstNeighbour) {
			m_lstNeighbour.add(oInnerPath);
		}
		
		m_lstPosition = new ArrayList<IPosition>();
		
		for (IPosition oPosition : oPath.m_lstPosition) {
			m_lstPosition.add(oPosition);
		}
	}

	// region: Implements IPath
	public String getName() {
		return m_stName;
	}

	public Direction getDirection() {
		return m_enDirection;
	}
	
	public List<IPath> getAllNeighbors() {
		return m_lstNeighbour;
	}
	
	public List<IPosition> getAllPositions() {
		return m_lstPosition;
	}

	public Boolean doesPositionExist(String stName) {		
		Iterator<IPosition> it = m_lstPosition.iterator();
    	while (it.hasNext()) {
    		IPosition oPosition = it.next();
    		
    		if (oPosition != null && oPosition.getName().equals(stName)) {
    			return true;
    		}
    	}

    	return false;
	}
	
	public IPathData getPathData() {
		return this;
	}

	// endregion
	
	// region: Implements IPathData
	public void setName(String stName) {
		m_stName = stName;
	}
	public void setDirection(Direction enDirection ) {
		m_enDirection = enDirection;
	}
	
	public void addNeighbour(IPath oPath) {
		m_lstNeighbour.add(oPath);
	}
	
	public void addPosition(IPosition oPosition) {
		m_lstPosition.add(oPosition);
	}
	
	public List<IPathData> getAllNeighbourPathData() {
		return (List<IPathData>)(Object)m_lstNeighbour;
	}
		
	public List<IPositionData> getAllPositionData() {
		return (List<IPositionData>)(Object)m_lstPosition;
	}
	// endregion
	
	// region: Implements IPathAgent	
	public List<IPathAgent> getAllNeighbourPathAgents() {
		return (List<IPathAgent>)(Object)m_lstNeighbour;
	}
		
	public List<IPositionAgent> getAllPositionAgents() {
		return (List<IPositionAgent>)(Object)m_lstPosition;
	}
	// endregion

	public IPath clone() {
		return new Path(this);
	}
}