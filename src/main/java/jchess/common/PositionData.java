package jchess.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import jchess.common.enumerator.*;
/**
 * Position.java
 * 
 * This is data class to store a Position.
 *
 */

public class PositionData implements IPositionData {
	private int m_nFile;
	private int m_nRank;
	private String m_stCategory;
	private IShape m_oShape;
	private Map<String, IPathData> m_mpPath;
	
	PositionData() {		
		m_mpPath = new HashMap<String, IPathData>();
	}
	
	PositionData( char chFile, int nRank, IShape oShape, String stCategory) {
		m_nFile = (int)chFile;
		m_nRank = nRank;
		m_oShape = oShape;		
		m_stCategory = stCategory;
		
		m_mpPath = new HashMap<String, IPathData>();
	}
	
	PositionData( PositionData oPosition) {
		m_nFile = oPosition.m_nFile;
		m_nRank = oPosition.m_nRank;
		m_oShape = oPosition.m_oShape;		
		m_stCategory = oPosition.m_stCategory;
		
		m_mpPath = new HashMap<String, IPathData>(oPosition.m_mpPath);
	}

	public int getFile() {
		return m_nFile;
	}

	public void setFile(int nFile) {
		m_nFile = nFile;
	}

	public int getRank() {
		return m_nRank;
	}
	
	public void setRank(int nRank) {
		m_nRank = nRank;
	}

	public String getName() {
		return "" + (char)m_nFile + "" + m_nRank;
	}

	public String getCategory() {
		return m_stCategory;
	}

	public void setCategory(String  stCategory) {
		m_stCategory = stCategory;
	}

	public IShape getShape() {
		return m_oShape;
	}

	public void setShape(IShape oShape) {
		m_oShape = oShape;
	}

	public IPathData getPath(String stName) {
		return m_mpPath.get(stName);
	}

	public List<IPathData> getAllPaths(){
		return new ArrayList<IPathData>(m_mpPath.values());
	}

	public PositionData getPosition() {
		return this;
	}

	public void addPath(IPathData oPath) {		
		m_mpPath.put(oPath.getName(), oPath);
	}
	
	public void linkPaths(String stPathA, String stPathB) {
		IPathData oPathA = m_mpPath.get(stPathA);
		if( oPathA == null)
			return;
		
		IPathData oPathB = m_mpPath.get(stPathB);
		if( oPathB == null)
			return;
		
		oPathA.addNeighbour(oPathB);
		oPathB.addNeighbour(oPathA);
	}

}