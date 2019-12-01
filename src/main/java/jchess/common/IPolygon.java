package jchess.common;

import java.awt.Polygon;

/**
 * IPolygon.java
 * 
 * This interface exposes necessary details to the classes responsible to generate view.
 *
 */

public interface IPolygon extends IShape{
	public Polygon getPolygon();	

}
