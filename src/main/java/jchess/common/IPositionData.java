package jchess.common;

/**
 * IPositionData provides interface for the cache module.
 * It expose getters and setters and does not support methods that are linked to logic of the game.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public interface IPositionData extends IPosition {
	public void setFile(int nFile);
	public void setRank(int nRank);
	public void setCategory(String  stCategory);
	public void setShape(IShape oShape);
	public void addPath(IPathData oPath);	
	public void linkPaths(String stPathA, String stPathB);
}