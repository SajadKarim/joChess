package jchess.cache;

import java.awt.Polygon;

import jchess.common.IPolygon;

/**
 * This class is specific for figures with four vertexes.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 * 
 */

public final class Quadrilateral implements IPolygon {
	private int m_x1;
	private int m_y1;
	private int m_x2;
	private int m_y2;
	private int m_x3;
	private int m_y3;
	private int m_x4;
	private int m_y4;
	private Polygon m_oPolygon;
	
	public Quadrilateral(int 	x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
		this.m_x1 = x1;
		this.m_y1 = y1;
		this.m_x2 = x2;
		this.m_y2 = y2;
		this.m_x3 = x3;
		this.m_y3 = y3;
		this.m_x4 = x4;
		this.m_y4 = y4;

		m_oPolygon = new Polygon();
		m_oPolygon.addPoint(m_x1, m_y1);
		m_oPolygon.addPoint(m_x2, m_y2);
		m_oPolygon.addPoint(m_x3, m_y3);
		m_oPolygon.addPoint(m_x4, m_y4);		
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
	
	public String toString() {
		return 	"x1=\"" + m_x1 + "\" " 
				+
				"y1=\"" + m_y1 + "\" "
				+
				"x2=\"" + m_x2 + "\" "
				+
				"y2=\"" + m_y2 + "\" "
				+
				"x3=\"" + m_x3 + "\" "
				+
				"y3=\"" + m_y3 + "\" "
				+
				"x4=\"" + m_x4 + "\" "
				+
				"y4=\"" + m_y4 + "\" ";
	}
}
