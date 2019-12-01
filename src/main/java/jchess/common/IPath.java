package jchess.common;

import java.util.List;

import jchess.common.enumerator.Direction;

/**
 * IPath.java
 * 
 * This interface exposes necessary details to data manipulation classes.
 *
 */

public interface IPath{	
	public String getName();
	
	public Direction getDirection();
	
	public void addNeighbour(Path oPath);
	
	public void addPosition(Position oPosition);

	public List<Path> getAllNeighbors();
		
	public List<Position> getAllPositions();	// remove all the primitive data objects from interface
	
	public Boolean doesPositionExist(String stName);	
}