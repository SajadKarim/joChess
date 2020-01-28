package jchess.cache;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import jchess.common.IBoardData;
import jchess.common.IPiece;
import jchess.common.IPlayer;
import jchess.common.IPlayerPieceMapping;
import jchess.common.IPosition;
import jchess.common.IRule;

/**
 * This is a data class to that stores Chess-board related details in the cache.
 * It basically maps XML or DB structure for Chess-board to memory.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public final class BoardData implements IBoardData {
	/**
	 * Width of Chess-board.
	 */
	private int m_nWidth;
	/**
	 * Height of Chess-board.
	 */
	private int m_nHeight;
	/**
	 * Unique name of Chess-board.
	 */
	private String m_stName;
	/**
	 * Image name of Chess-board.
	 */
	private String m_stBoardImagePath;
	/**
	 * Image name to show Image when a cell on Chess-board is selected.
	 */
	private String m_stActiveCellImagePath;
	/**
	 * Image name to show Image when a cell on Chess-board is marked as a candidate move.
	 */
	private String m_stMarkedCellImagePath;
	/**
	 * Name of the Rule Engine that would process rules defined on Chess-board.
	 */
	private String m_stRuleEngineName;	
	/**
	 * Name of the Rule Processor that would process rules defined on Chess-board.
	 */
	private String m_stRuleProcessorName;
	/**
	 * Rules defined on Chess-board.
	 */
    private Map<String, IRule> m_mpRules;
    /**
     * Pieces defined on Chess-board.
     */
    private Map<String, IPiece> m_mpPieces;
    /**
     * Players defined on Chess-board.
     */
    private Map<String, IPlayer> m_mpPlayers;
    /**
     * Positions defined on Chess-board.
     */
    private Map<String, IPosition> m_mpPosition;
    /**
     * Mapping to link Pieces to Positions.
     */
    private Map<String, List<IPlayerPieceMapping>> m_mpMapping;
    
    /**
     * Constructor
     */
    public BoardData() {
    	m_mpRules = new HashMap<String, IRule>();
    	m_mpPieces = new HashMap<String, IPiece>();
    	m_mpPlayers = new HashMap<String, IPlayer>();    	
    	m_mpPosition = new HashMap<String, IPosition>();
    	m_mpMapping = new HashMap<String, List<IPlayerPieceMapping>>();    	
    }
    
    // region: Implements IBoard
    /**
     * Getter for Chess-board name.
     */
	public String getName() {
		return m_stName;
	}

	/**
	 * Getter for Chess-board width.
	 */
	public int getBoardWidth() {
		return m_nWidth;
	}

	/**
	 * Getter for Chess-board height.
	 */
	public int getBoardHeight() {
		return m_nHeight;
	}

	/**
	 * Getter for Chess-board' Image name.
	 */
	public String getBoardImagePath() {
		return m_stBoardImagePath;
	}

	/**
	 * Getter for active cell Image name.
	 */
	public String getActivCellImagePath() {
		return m_stActiveCellImagePath;
	}

	/**
	 * Getter for candidate cell Image name.
	 */
	public String getMarkedCellImagePath() {
		return m_stMarkedCellImagePath;
	}

	/**
	 * Returns position using unique name.
	 */
	public IPosition getPosition(String stName) {
		return m_mpPosition.get(stName);
	}
	
	/**
	 * Returns all the positions available.
	 */
	public Map<String, IPosition> getAllPositions() {
		return m_mpPosition;
	}

	/**
	 * Returns player using unique name.
	 */
	public IPlayer getPlayer(String stName) {
		return m_mpPlayers.get(stName);
	}

	/**
	 * Returns all the players available.
	 */
	public Map<String, IPlayer> getAllPlayers() {
		return m_mpPlayers;
	}

	/**
	 * Returns rules for given name.
	 */
	public IRule getRule(String stName) {
		return m_mpRules.get(stName);
	}
	
	/**
	 * Returns all the available rules.
	 */
	public  Map<String, IRule>  getAllRules() {
		return m_mpRules;
	}

	/**
	 * Returns unlinked pieces for unique name.
	 */
	public IPiece getUnlinkedPiece(String stName) {
		return m_mpPieces.get(stName);
	}

	/**
	 * Returns all the unlinked unique pieces.
	 */
	public  Map<String, IPiece> getAllUnlinkedPieces() {
		return m_mpPieces;
	}

	/**
	 * Getter for Chess-board's core data object.
	 */
	public IBoardData getBoardData() {
		return this;
	}
	
	/**
	 * This method perform any custom initializes after fetching details from XML/DB.
	 */
	public void init() {		
	}	
	//endregion
	
	//region: Implement IBoardData
	/**
	 * Setter for Chess-board name.
	 */
	public void setName(String stName) {
		m_stName = stName;
	}
	
	/**
	 * Setter for Chess-board width.
	 */
	public void setBoardWidth(int nWidth) {
		m_nWidth = nWidth;
	}
	
	/**
	 * Setter for Chess-board height.
	 */
	public void setBoardHeight(int nHeight) {
		m_nHeight = nHeight;
	}

	/**
	 * Setter for Chess-board Image name.
	 */
	public void setBoardImagePath(String stPath) {
		m_stBoardImagePath = stPath;
	}

	/**
	 * Setter for active cell Image name.
	 */
	public void setActivCellImagePath(String stPath) {
		m_stActiveCellImagePath = stPath;
	}

	/**
	 * Setter for candidate cell Image name.
	 */
	public void setMarkedCellImagePath(String stPath) {
		m_stMarkedCellImagePath = stPath;
	}
	
	/**
	 * Adds position.
	 */
	public void addPosition(IPosition oPosition) {
		m_mpPosition.put(oPosition.getName(), oPosition);
	}	

	/**
	 * Adds player.
	 */
	public void addPlayer(IPlayer oPlayer) {
		m_mpPlayers.put(oPlayer.getName(), oPlayer);
	}

	/**
	 * Adds rule.
	 */
	public void addRule(IRule oRule) {
		m_mpRules.put(oRule.getName(), oRule);
	}	

	/**
	 * Adds piece.
	 */
	public void addPiece(IPiece oPiece) {
		m_mpPieces.put(oPiece.getName(), oPiece);
	}

	/**
	 * Returns players and pieces mapping.
	 */
	public List<IPlayerPieceMapping> getPlayerMapping(String stName) {
		return m_mpMapping.get(stName);
	}	
    
	/** 
	 * Adds player-piece mapping.
	 */
	public void addMapping(String stPlayer, IPlayerPieceMapping oMapping) {
		if (m_mpMapping.get(stPlayer) == null) {
        	m_mpMapping.put(stPlayer, new LinkedList<IPlayerPieceMapping>());
    	}
    	m_mpMapping.get(stPlayer).add(oMapping);
    }
	//endregion
	
	/**
	 * Getter for Rule Engine name.
	 */
	public String getRuleEngineName() {
		return m_stRuleEngineName;
	}

	/**
	 * Setter for Rule Engine name.
	 */
	public void setRuleEngineName(String stName) {
		m_stRuleEngineName = stName;
	}

	/**
	 * Getter for Rule Processor name.
	 */
	public void setRuleProcessorName(String stName) {
		m_stRuleProcessorName = stName;
	}

	/** 
	 * Setter for Rule Processor name.
	 */
	public String getRuleProcessorName() {
		return m_stRuleProcessorName;
	}
}
