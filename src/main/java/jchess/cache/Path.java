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
 * This is a data class to store "Path or Direction" related details in cache.
 * It actually maps XML or DB structure for "Path or Direction" to memory.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public final class Path implements IPathData, IPathAgent {
	/**
	 * Name of the path.
	 */
	private String m_stName;
	/**
	 * Direction of path.
	 */
	private Direction  m_enDirection;
	/**
	 * List of paths linked to the path.
	 */
	private List<IPath> m_lstNeighbour;
	/**
	 * List of positions linked to the path.
	 */
	private List<IPosition> m_lstPosition;	
	
	/**
	 * Constructor.
	 * 
	 * @param stName Name for the path.
	 * @param enDirection Direction for the path.
	 * @param oPosition Linked position to the path.
	 */
	public Path(String stName, Direction enDirection, IPositionData oPosition) {
		m_stName = stName;
		m_enDirection = enDirection;		
		m_lstNeighbour = new ArrayList<IPath>();
		m_lstPosition = new ArrayList<IPosition>();

		if (oPosition != null) {
			addPosition(oPosition);
		}
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param oPath Instance of path.
	 */
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
	/**
	 * Getter for path name.
	 */
	public String getName() {
		return m_stName;
	}

	/**
	 * Getter for path direction.
	 */
	public Direction getDirection() {
		return m_enDirection;
	}
	
	/**
	 * Returns all the neighouring paths.
	 */
	public List<IPath> getAllNeighbors() {
		return m_lstNeighbour;
	}
	
	/**
	 * Returns all the linked positions.
	 */
	public List<IPosition> getAllPositions() {
		return m_lstPosition;
	}

	/**
	 * Checks if the position is linked to the path or not.
	 */
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
	
	/**
	 * Returns path's core data object.
	 */
	public IPathData getPathData() {
		return this;
	}

	// endregion
	
	// region: Implements IPathData
	/**
	 * Sets name of the path.
	 */
	public void setName(String stName) {
		m_stName = stName;
	}
	
	/**
	 * Sets direction of the path.
	 */
	public void setDirection(Direction enDirection) {
		m_enDirection = enDirection;
	}
	
	/**
	 * Adds neighbour for the path.
	 */
	public void addNeighbour(IPath oPath) {
		m_lstNeighbour.add(oPath);
	}
	
	/**
	 *  Adds positions for the path.
	 */
	public void addPosition(IPosition oPosition) {
		m_lstPosition.add(oPosition);
	}
	
	/**
	 * Returns all the neighbouring paths.
	 */
	public List<IPathData> getAllNeighbourPathData() {
		return (List<IPathData>)(Object)m_lstNeighbour;
	}

	/**
	 * Returns all the linked positions.
	 */
	public List<IPositionData> getAllPositionData() {
		return (List<IPositionData>)(Object)m_lstPosition;
	}
	// endregion
	
	// region: Implements IPathAgent
	/**
	 * Returns all the neighbouring paths.
	 */
	public List<IPathAgent> getAllNeighbourPathAgents() {
		return (List<IPathAgent>)(Object)m_lstNeighbour;
	}
		
	/** 
	 * Returns all the linked positions.
	 */
	public List<IPositionAgent> getAllPositionAgents() {
		return (List<IPositionAgent>)(Object)m_lstPosition;
	}
	// endregion

	/**
	 * Creates a duplicate instance of the path.
	 */
	public IPath clone() {
		return new Path(this);
	}
}