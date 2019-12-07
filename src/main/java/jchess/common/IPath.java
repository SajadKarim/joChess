package jchess.common;

import java.util.List;

import jchess.common.enumerator.Direction;

public interface IPath {
		public String getName();
		public Direction getDirection();
		public List<IPath> getNeighbors();
		public List<IPosition> getPositions();	// remove all the primitive data objects from interface	
		public Boolean doesPositionExist(String stName);	
}
