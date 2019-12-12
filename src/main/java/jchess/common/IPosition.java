package jchess.common;

import java.util.Map;

/**
 * IPosition provides abstraction to IPositionData and IPositionAgent.
 * It fulfills functionality of Abstract Factory Pattern, and
 * it is mainly built for Cache module to make both the
 * type compatible with its implementation (data population logic). 
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 *
 */

public interface IPosition {
	public int getFile();
	public int getRank();
	public String getName();
	public String getCategory();
	public IShape getShape();
	public IPositionData getPosition();
	public IPath getPath(String stName);
	public Map<String, IPath> getAllPaths();
}
