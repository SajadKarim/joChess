package jchess.common;

import java.util.List;

import jchess.common.enumerator.Direction;

/**
 * IPath provides abstraction to IPathData and IPathAgent.
 * It fulfills functionality of Abstract Factory Pattern, and
 * it is mainly built for Cache module to make both the
 * type compatible with its implementation (data population logic). 
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 *
 */

public interface IPath {
	public String getName();
	public Direction getDirection();
	public Boolean doesPositionExist(String stName);	
	public List<IPath> getAllNeighbors();
	public List<IPosition> getAllPositions();
	public IPathData getPathData();
}
