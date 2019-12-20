package jchess.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jchess.common.IPieceData;
import jchess.common.IRule;

/**
 * This is a container class to store "Piece" related details in cache.
 * It actually maps XML or DB structure for "Piece" to memory.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public class PieceData implements IPieceData {
	String m_stName;
	String m_stImagePath;
	String m_stFamily;
	Map<String, IRule > m_mpRules;
	
	public PieceData() {
		m_mpRules = new HashMap<String, IRule >();
	}
	
	public void init() {		
	}
	
	public PieceData(String stName, String stImagePath, String stFamily) {
		m_stName = stName;
		m_stImagePath = stImagePath;
		m_stFamily = stFamily;
		m_mpRules = new HashMap<String, IRule >();
	}
	
	public PieceData(PieceData oPiece) {
		m_stName = oPiece.m_stName;
		m_stImagePath = oPiece.m_stImagePath;
		m_stFamily = oPiece.m_stFamily;
		m_mpRules = new HashMap<String, IRule>(oPiece.m_mpRules);
	}
	
	// region: Implements IPiece
	public String getName() {
		return m_stName;
	}
		
	public String getImagePath() {
		return m_stImagePath;
	}
	// endregion
	
	// region: Implements IPieceData
	public void setName(String stName) {
		m_stName = stName;
	}

	public void setImagePath(String stImagePath) {
		m_stImagePath = stImagePath;
	}
	public List<IRule> getAllRules(){
		return new ArrayList<IRule >(m_mpRules.values());
	}
	
	public PieceData getPieceData() {
		return this;
	}

	public void addRule(IRule oRule) {
		m_mpRules.put(oRule.getName(), oRule);
	}
	// endregion
	
	public void setFamily(String stFamily) {
		m_stFamily = stFamily;
	}

	public String getFamily() {
		return m_stFamily;
	}
	
	public IPieceData clone() {
		return new PieceData(this);
	}
}
