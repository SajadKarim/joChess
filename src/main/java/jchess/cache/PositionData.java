package jchess.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jchess.common.IPath;
import jchess.common.IPathData;
import jchess.common.IPositionData;
import jchess.common.IShape;

/**
 * This is a container class to store "Position" related details in cache.
 * It actually maps XML or DB structure for "Position" to memory.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public class PositionData implements IPositionData {
	private int m_nFile;
	private int m_nRank;
	private String m_stCategory;
	private IShape m_oShape;
	private Map<String, IPath> m_mpPath;
	
	public PositionData() {		
		m_mpPath = new HashMap<String, IPath>();
	}
	
	PositionData( char chFile, int nRank, IShape oShape, String stCategory) {
		m_nFile = (int)chFile;
		m_nRank = nRank;
		m_oShape = oShape;		
		m_stCategory = stCategory;
		
		m_mpPath = new HashMap<String, IPath>();
	}
	
	PositionData( PositionData oPosition) {
		m_nFile = oPosition.m_nFile;
		m_nRank = oPosition.m_nRank;
		m_oShape = oPosition.m_oShape;		
		m_stCategory = oPosition.m_stCategory;
		
		m_mpPath = new HashMap<String, IPath>(oPosition.m_mpPath);
	}

	// region: Immplements IPosition
	public int getFile() {
		return m_nFile;
	}

	public int getRank() {
		return m_nRank;
	}

	public String getName() {
		return "" + (char)m_nFile + "" + m_nRank;
	}

	public String getCategory() {
		return m_stCategory;
	}

	public IShape getShape() {
		return m_oShape;
	}
	
	public IPath getPath(String stName) {
		return m_mpPath.get(stName);
	}

	public Map<String, IPath> getAllPaths(){
		return m_mpPath;
	}

	// endregion

	// region: Implements IPositionData
	public void setFile(int nFile) {
		m_nFile = nFile;
	}
	
	public void setRank(int nRank) {
		m_nRank = nRank;
	}

	public void setCategory(String  stCategory) {
		m_stCategory = stCategory;
	}

	public void setShape(IShape oShape) {
		m_oShape = oShape;
	}

	
	//public List<IPath > getAllPaths(){
	//	return new ArrayList<IPath>(m_mpPath.values());
	//}

	public PositionData getPosition() {
		return this;
	}

	public void addPath(IPathData oPath) {		
		m_mpPath.put(oPath.getName(), oPath);
	}
	
	public void linkPaths(String stPathA, String stPathB) {
		IPath oPathA = m_mpPath.get(stPathA);
		if( oPathA == null)
			return;
		
		IPath oPathB = m_mpPath.get(stPathB);
		if( oPathB == null)
			return;
		
		oPathA.getPathData().addNeighbour(oPathB);
		oPathB.getPathData().addNeighbour(oPathA);
	}
	// endregion
}