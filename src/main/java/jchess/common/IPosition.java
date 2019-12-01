package jchess.common;

import java.util.Map;

/**
 * IPosition.java
 * 
 * This interface exposes necessary details to data manipulation classes.
 *
 */

public interface IPosition{
	public int getFile();
	public int getRank();
	public String getName();
	public String getCategory();
	public IShape getShape();
	public Path getPath(String stName);
	public Map<String, Path> getAllPaths();
}