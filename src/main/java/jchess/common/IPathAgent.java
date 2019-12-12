package jchess.common;

import java.util.List;

/**
 * IPathAgent provides interface for the module where the
 * all the decisions (game) are taken. It ensures that user
 * does not make any changes to underlying data object.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public interface IPathAgent extends IPath {
	public List<IPathAgent> getAllNeighbourPathAgents();
	public List<IPositionAgent> getAllPositionAgents();
}
