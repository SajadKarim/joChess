package jchess.cache;

import java.util.HashMap;
import java.util.Map;

import jchess.common.IPath;
import jchess.common.IPathData;
import jchess.common.IPosition;
import jchess.common.IPositionData;
import jchess.common.IShape;

/**
 * This is a data class to store "Position" related details in cache.
 * It actually maps XML or DB structure for "Position" to memory.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public final class PositionData implements IPositionData {
	/**
	 * File of position.
	 */
	private int m_nFile;
	/**
	 * Rank of position.
	 */
	private int m_nRank;
	/**
	 * Category of position.
	 */
	private String m_stCategory;
	/**
	 * Shape of position.
	 */
	private IShape m_oShape;
	/**
	 * Paths linked to position.
	 */
	private Map<String, IPath> m_mpPath;
	
	/**
	 * Default constructor.
	 */
	public PositionData() {		
		m_mpPath = new HashMap<String, IPath>();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param chFile File
	 * @param nRank Rank
	 * @param oShape Shape
	 * @param stCategory Category
	 */
	PositionData(char chFile, int nRank, IShape oShape, String stCategory) {
		m_nFile = (int)chFile;
		m_nRank = nRank;
		m_oShape = oShape;		
		m_stCategory = stCategory;
		
		m_mpPath = new HashMap<String, IPath>();
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param oPosition
	 */
	public PositionData(PositionData oPosition) {
		m_nFile = oPosition.m_nFile;
		m_nRank = oPosition.m_nRank;
		m_oShape = oPosition.m_oShape;		
		m_stCategory = oPosition.m_stCategory;
		
		// TODO: I tired HashMap's clone, but it does not call Object's copy constructor.
		// For the time being I am manually copying all the objects. Need to do it proper way to do deep copy.
		m_mpPath  = new HashMap<String, IPath>();
		for (Map.Entry<String, IPath> it : oPosition.m_mpPath .entrySet()) {
			m_mpPath.put(it.getKey(), it.getValue().clone());
		}
		//m_mpPath = new HashMap<String, IPath>(oPosition.m_mpPath);
	}

	// region: Immplements IPosition
	/**
	 * Getter for position file.
	 */
	public int getFile() {
		return m_nFile;
	}

	/**
	 * Getter for position rank.
	 */
	public int getRank() {
		return m_nRank;
	}

	/**
	 *  Getter for position name.
	 */
	public String getName() {
		return "" + (char)m_nFile + "" + m_nRank;
	}

	/**
	 * Getter for position category.
	 */
	public String getCategory() {
		return m_stCategory;
	}

	/**
	 * Getter for position shape.
	 */
	public IShape getShape() {
		return m_oShape;
	}
	
	/**
	 * Return path against the name.
	 */
	public IPath getPath(String stName) {
		return m_mpPath.get(stName);
	}

	/**
	 * Return all the linked paths.
	 */
	public Map<String, IPath> getAllPaths() {
		return m_mpPath;
	}

	// endregion

	// region: Implements IPositionData
	/**
	 * Setter for position file.
	 */
	public void setFile(int nFile) {
		m_nFile = nFile;
	}
	
	/**
	 * Setter for position rank.
	 */
	public void setRank(int nRank) {
		m_nRank = nRank;
	}

	/**
	 * Setter for position category.
	 */
	public void setCategory(String  stCategory) {
		m_stCategory = stCategory;
	}

	/**
	 * Setter for position shape.
	 */
	public void setShape(IShape oShape) {
		m_oShape = oShape;
	}

	
	//public List<IPath > getAllPaths(){
	//	return new ArrayList<IPath>(m_mpPath.values());
	//}

	/**
	 * Returns core position object.
	 */
	public PositionData getPosition() {
		return this;
	}

	/**
	 * Adds new neigbour path.
	 */
	public void addPath(IPathData oPath) {		
		m_mpPath.put(oPath.getName(), oPath);
	}
	
	/**
	 * Establishes connection between paths.
	 */
	public void linkPaths(String stPathA, String stPathB) {
		IPath oPathA = m_mpPath.get(stPathA);
		if (oPathA == null) {
			return;
		}
		
		IPath oPathB = m_mpPath.get(stPathB);
		if (oPathB == null) {
			return;
		}
		
		oPathA.getPathData().addNeighbour(oPathB);
		oPathB.getPathData().addNeighbour(oPathA);
	}
	// endregion
	
	/**
	 * Create a duplicate instance of position.
	 */
	public IPosition clone() {
		return new PositionData(this);
	}
}