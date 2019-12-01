package jchess.common;

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

public class Position implements IPosition {
	private int m_nFile;
	private int m_nRank;
	private String m_stCategory;
	private IShape m_oShape;
	private Map<String, Path> m_mpPath;
	
	public Position( char chFile, int nRank, IShape oShape, String stCategory) {
		m_nFile = (int)chFile;
		m_nRank = nRank;
		m_oShape = oShape;		
		m_stCategory = stCategory;
		
		m_mpPath = new HashMap<String, Path>();
	}
	
	public Position( Position oPosition) {
		m_nFile = oPosition.m_nFile;
		m_nRank = oPosition.m_nRank;
		m_oShape = oPosition.m_oShape;		
		m_stCategory = oPosition.m_stCategory;
		
		m_mpPath = new HashMap<String, Path>(oPosition.m_mpPath);
	}

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

	public Path getPath(String stName) {
		return m_mpPath.get(stName);
	}

	public Map<String, Path> getAllPaths(){
		return m_mpPath;
	}

	public void addPath(Path oPath) {		
		m_mpPath.put(oPath.getName(), oPath);
	}
	
	public void addPathConnection(String stSource, String stDestination) {
		Path oSource = m_mpPath.get(stSource);
		if( oSource == null)
			return;
		
		Path oDestination = m_mpPath.get(stDestination);
		if( oDestination == null)
			return;
		
		oSource.addNeighbour(oDestination);
		oDestination.addNeighbour(oSource);
	}

}