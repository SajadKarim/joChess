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

public final class PieceData implements IPieceData {
	private String m_stName;
	private String m_stImagePath;
	private String m_stFamily;
	private Map<String, IRule> m_mpRule;
	
	public PieceData() {
		m_mpRule = new HashMap<String, IRule>();
	}
	
	public void init() {		
	}
	
	public PieceData(PieceData oPiece) {
		m_stName = oPiece.m_stName;
		m_stImagePath = oPiece.m_stImagePath;
		m_stFamily = oPiece.m_stFamily;
		
		// TODO: I tired HashMap's clone, but it does not call Object's copy constructor.
		// For the time being I am manually copying all the objects. Need to do it proper way to do deep copy.
		m_mpRule = new HashMap<String, IRule>();
		for (Map.Entry<String, IRule> it : oPiece.m_mpRule.entrySet()) {
			m_mpRule.put(it.getKey(), it.getValue().clone());
		}
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
	public List<IRule> getAllRules() {
		return new ArrayList<IRule>(m_mpRule.values());
	}
	
	public PieceData getPieceData() {
		return this;
	}

	public void addRule(IRule oRule) {
		m_mpRule.put(oRule.getName(), oRule);
	}

	public IRule getRule(String stRuleName) {
		return m_mpRule.get(stRuleName);
	}
	// endregion
	
	public void setFamily(String stFamily) {
		m_stFamily = stFamily;
	}

	public String getFamily() {
		return m_stFamily;
	}
	
	@Override
	public IPieceData clone() {
		return new PieceData(this);
	}
}
