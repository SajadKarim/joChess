package jchess.common;

import java.awt.Polygon;

/**
 * IPolygon provides abstraction to polygons.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public interface IPolygon extends IShape {
	public Polygon getPolygon();	
}
