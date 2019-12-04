package jchess.common;

import java.util.List;
import java.util.Map;

/**
 * IPosition.java
 * 
 * This interface exposes necessary details to data manipulation classes.
 *
 */

public interface IPositionData{
	public int getFile();
	public int getRank();
	public String getName();
	public String getCategory();
	public IShape getShape();
	public PositionData getPosition();
	public IPathData getPath(String stName);
	public List<IPathData> getAllPaths();
}