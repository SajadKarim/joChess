package jchess.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jchess.common.IPieceData;
import jchess.common.IRule;

/**
 * This is a data class to store "Piece" related details in cache.
 * It actually maps XML or DB structure for "Piece" to memory.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public final class PieceData implements IPieceData {
	/** 
	 * Name of piece.
	 */
	private String m_stName;
	/**
	 * Image name of piece.
	 */
	private String m_stImagePath;
	/**
	 * Family of piece.
	 */
	private String m_stFamily;
	/**
	 * Rules linked to piece.
	 */
	private Map<String, IRule> m_mpRule;
	
	/**
	 * Constructor.
	 */
	public PieceData() {
		m_mpRule = new HashMap<String, IRule>();
	}
		
	public void init() {		
	}
	
	/** 
	 * Construction
	 * 
	 * @param oPiece Instance for core piece data.
	 */
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
	/**
	 * Getter for piece name.
	 */
	public String getName() {
		return m_stName;
	}
	
	/**
	 * Getter for piece Image name.
	 */
	public String getImagePath() {
		return m_stImagePath;
	}
	// endregion
	
	// region: Implements IPieceData
	/**
	 * Setter for piece name.
	 */
	public void setName(String stName) {
		m_stName = stName;
	}

	/**
	 * Setter for piece image name.
	 */
	public void setImagePath(String stImagePath) {
		m_stImagePath = stImagePath;
	}
	
	/**
	 * Returns all rules linked to piece.
	 */
	public List<IRule> getAllRules() {
		return new ArrayList<IRule>(m_mpRule.values());
	}
	
	/**
	 * Returns core piece data object.
	 */
	public PieceData getPieceData() {
		return this;
	}

	/**
	 * Add a new rule.
	 */
	public void addRule(IRule oRule) {
		m_mpRule.put(oRule.getName(), oRule);
	}

	/**
	 * Returns rule against the name.
	 */
	public IRule getRule(String stRuleName) {
		return m_mpRule.get(stRuleName);
	}
	// endregion
	
	/**
	 * Sets family of piece.
	 */
	public void setFamily(String stFamily) {
		m_stFamily = stFamily;
	}

	/**
	 * Returns family of piece.
	 */
	public String getFamily() {
		return m_stFamily;
	}
	
	@Override
	/**
	 * Create a duplicate instance of piece.
	 */
	public IPieceData clone() {
		return new PieceData(this);
	}
}
