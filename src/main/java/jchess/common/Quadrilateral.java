package jchess.common;

import java.awt.Polygon;

/**
 * Quadrilateral.java
 * 
 * This is a data class to store a Position coordinates for designing purpose.
 *
 */

public class Quadrilateral implements IPolygon{
	private int x1, y1;
	private int x2, y2;
	private int x3, y3;
	private int x4, y4;
	private Polygon m_oPolygon;
	
	public Quadrilateral(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.x3 = x3;
		this.y3 = y3;
		this.x4 = x4;
		this.y4 = y4;

		m_oPolygon = new Polygon();
		m_oPolygon.addPoint(x1,y1);
		m_oPolygon.addPoint(x2,y2);
		m_oPolygon.addPoint(x3,y3);
		m_oPolygon.addPoint(x4,y4);		
	}
	
	public int getTopLeftX() {
		return m_oPolygon.getBounds().x;
	}
	
	public int getTopLeftY() {
		return m_oPolygon.getBounds().y;
	}

	public Polygon getPolygon() {
    	return m_oPolygon;
	}
}
