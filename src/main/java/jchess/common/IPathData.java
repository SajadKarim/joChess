package jchess.common;

import java.util.List;

import jchess.common.enumerator.Direction;

/**
 * IPathData provides interface for the cache module.
 * It expose getters and setters and does not support
 * methods that are linked to logic of the game.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public interface IPathData extends IPath {	
	public void setName(String stName);
	public void setDirection(Direction enDirection);
	public void addNeighbour(IPath oPath);
	public void addPosition(IPosition oPosition);
	public List<IPathData> getAllNeighbourPathData();
	public List<IPositionData> getAllPositionData();
}