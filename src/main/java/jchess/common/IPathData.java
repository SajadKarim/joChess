package jchess.common;

import java.util.List;

import jchess.common.enumerator.Direction;

/**
 * IPath.java
 * 
 * This interface exposes necessary details to data manipulation classes.
 *
 */

public interface IPathData{	
	public String getName();
	public Direction getDirection();
	public void addNeighbour(IPathData oPath);
	public void addPosition(IPositionData oPosition);
	public List<IPathData> getAllNeighbors();
	public List<IPositionData> getAllPositions();	// remove all the primitive data objects from interface	
	public Boolean doesPositionExist(String stName);	
}