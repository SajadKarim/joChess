package jchess.cache;

import java.util.HashMap;
import java.util.Map;

import jchess.common.IBoardData;
import jchess.common.IPiece;
import jchess.common.IPlayer;
import jchess.common.IPosition;
import jchess.common.IRule;

/**
 * This is a container class to store "Board" related details in cache.
 * It actually maps XML or DB structure for "Board" to memory.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public class BoardData implements IBoardData {
	private int m_nWidth;
	private int m_nHeight;
	private String m_stName;
	private String m_stBoardImagePath;
	private String m_stActiveCellImagePath;
	private String m_stMarkedCellImagePath;
	private String m_stRuleEngineName;	
	private String m_stRuleProcessorName;	
    private Map<String, IPosition> m_mpPosition;
    private Map<String, IPlayer > m_mpPlayers;
    private Map<String, IPiece > m_mpPieces;
    private Map<String, Map<String, String>> m_mpMapping;
    private Map<String, IRule> m_mpRules;
    
    public BoardData() {
    	m_mpPosition = new HashMap<String, IPosition>();
    	m_mpPlayers = new HashMap<String, IPlayer >();    	
    	m_mpPieces = new HashMap<String, IPiece >();
    	m_mpMapping = new HashMap<String, Map<String, String>>();    	
    	m_mpRules = new HashMap<String, IRule>();
    }
    
    // region: Implements IBoard
	public String getName() {
		return m_stName;
	}

	public int getBoardWidth() {
		return m_nWidth;
	}

	public int getBoardHeight() {
		return m_nHeight;
	}

	public String getBoardImagePath() {
		return m_stBoardImagePath;
	}

	public String getActivCellImagePath() {
		return m_stActiveCellImagePath;
	}

	public String getMarkedCellImagePath() {
		return m_stMarkedCellImagePath;
	}

	public IPosition getPosition(String stName) {
		return m_mpPosition.get(stName);
	}
	
	public Map<String, IPosition> getAllPositions() {
		return m_mpPosition;
	}

	public IPlayer getPlayer(String stName) {
		return m_mpPlayers.get(stName);
	}

	public Map<String, IPlayer > getAllPlayers() {
		return m_mpPlayers;
		//return new ArrayList<IPlayer >( m_mpPlayers.values());
	}

	public IRule getRule(String stName) {
		return m_mpRules.get(stName);
	}
	
	public  Map<String, IRule>  getAllRules() {
		return m_mpRules;
	}

	public IPiece getPiece(String stName) {
		return m_mpPieces.get(stName);
	}

	public  Map<String, IPiece>  getAllPieces() {
		return m_mpPieces;
	}

	public IPosition createPosition() {
		return new PositionData();
	}	

	public IPiece createPiece() {
		return new PieceData();
	}	

	public IRule createRule() {
		return new RuleData();
	}

	public IPlayer createPlayer() {
		return new PlayerData();
	}

	public IBoardData getBoardData() {
		return this;
	}
	
	public void init() {
		
	}
	//endregion
	
	//region: Implement IBoardData
	public void setName(String stName) {
		m_stName = stName;
	}
	
	public void setBoardWidth(int nWidth) {
		m_nWidth = nWidth;
	}
	
	public void setBoardHeight(int nHeight) {
		m_nHeight = nHeight;
	}

	public void setBoardImagePath(String stPath) {
		m_stBoardImagePath = stPath;
	}

	public void setActivCellImagePath(String stPath) {
		m_stActiveCellImagePath = stPath;
	}

	public void setMarkedCellImagePath(String stPath) {
		m_stMarkedCellImagePath = stPath;
	}
	
	public void addPosition(IPosition oPosition) {
		m_mpPosition.put(oPosition.getName(), oPosition);
	}	

	public void addPlayer(IPlayer oPlayer) {
		m_mpPlayers.put(oPlayer.getName(), oPlayer);
	}

	public void addRule(IRule oRule) {
		m_mpRules.put(oRule.getName(), oRule);
	}	

	public void addPiece(IPiece oPiece) {
		m_mpPieces.put(oPiece.getName(), oPiece);
	}

	public Map<String, String> getPlayerMapping(String stName) {
		return m_mpMapping.get(stName);
	}	
    
	public void addMapping(String stPlayer, String stPiece, String stPosition) {
    	if( m_mpMapping.get(stPlayer) == null)
        	m_mpMapping.put(stPlayer, new HashMap<String, String>());	
    	m_mpMapping.get(stPlayer).put(stPosition, stPiece);
    }
	//endregion
	
	public String getRuleEngineName() {
		return m_stRuleEngineName;
	}

	public void setRuleEngineName(String stName) {
		m_stRuleEngineName = stName;
	}

	public void setRuleProcessorName(String stName) {
		m_stRuleProcessorName = stName;
	}

	public String getRuleProcessorName() {
		return m_stRuleProcessorName;
	}
}
