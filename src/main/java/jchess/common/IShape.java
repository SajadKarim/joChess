package jchess.common;

import java.awt.Polygon;

/**
 * IShape.java
 * 
 * This interface exposes necessary details to the classes responsible to generate view.
 *
 */

public interface IShape{
	public int getTopLeftX();
	public int getTopLeftY();
	public String toString();
}
