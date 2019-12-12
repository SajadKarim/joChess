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
	Map<String, IRule > m_mpRules;
	
	public PieceData() {
		m_mpRules = new HashMap<String, IRule >();
	}
	
	public PieceData(String stName, String stImagePath) {
		m_stName = stName;
		m_stImagePath = stImagePath;		
		m_mpRules = new HashMap<String, IRule >();
	}
	
/*	public PieceData(PieceData oPiece) {
		this.m_stName = oPiece.m_stName;
		this.m_stImagePath = oPiece.m_stImagePath;
		
		this.m_mpRules = new HashMap<String, IRuleData>(oPiece.m_mpRules);
	}
*/
	
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
}
