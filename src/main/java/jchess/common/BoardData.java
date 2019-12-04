package jchess.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jchess.GUI;
import jchess.common.enumerator.Direction;

public class BoardData implements IBoardData {
	private int m_nWidth;
	private int m_nHeight;
	private String m_stName;
	private String m_stBoardImagePath;
	private String m_stActiveCellImagePath;
	private String m_stMarkedCellImagePath;	
    private Map<String, IPositionData> m_mpPosition;
    private Map<String, IPlayerData> m_mpPlayers;
    private Map<String, IPieceData> m_mpPieces;
    private Map<String, Map<String, String>> m_mpMapping;
    private Map<String, IRuleData> m_mpRules;
    
    public BoardData() {
    	m_mpPosition = new HashMap<String, IPositionData>();
    	m_mpPlayers = new HashMap<String, IPlayerData>();    	
    	m_mpPieces = new HashMap<String, IPieceData>();
    	m_mpMapping = new HashMap<String, Map<String, String>>();    	
    	m_mpRules = new HashMap<String, IRuleData>();
    }
    
	public BoardData(String stName, String stBoardImagePath, String stActiveCellImagePath, String stMarkedCellImagePath, int nWidth, int nHeight) {
		m_nWidth = nWidth;
		m_nHeight = nHeight;
		m_stName = stName;
		m_stBoardImagePath= stBoardImagePath;
		m_stActiveCellImagePath = stActiveCellImagePath;
		m_stMarkedCellImagePath = stMarkedCellImagePath;
	
    	m_mpPosition = new HashMap<String, IPositionData>();
    	m_mpPlayers = new HashMap<String, IPlayerData>();    	
    	m_mpPieces = new HashMap<String, IPieceData>();
    	m_mpMapping = new HashMap<String, Map<String, String>>();    	
    	m_mpRules = new HashMap<String, IRuleData>();

    	m_nWidth = 808;
		m_nHeight = 700;
		m_stBoardImagePath = "chessboard.png";
		m_stActiveCellImagePath = "sel_square.png";
		m_stMarkedCellImagePath = "able_square.png";
		
		//populatePositions();
		//populatePlayers();
		//populatePieces();
		//populateMappings();
	}
	
	public String getName() {
		return m_stName;
	}
	public void setName(String stName) {
		m_stName = stName;
	}
	
	public int getBoardWidth() {
		return m_nWidth;
	}
	public void setBoardWidth(int nWidth) {
		m_nWidth = nWidth;
	}

	public int getBoardHeight() {
		return m_nHeight;
	}
	public void setBoardHeight(int nHeight) {
		m_nHeight = nHeight;
	}

	public String getBoardImagePath() {
		return m_stBoardImagePath;
	}
	public void setBoardImagePath(String stPath) {
		m_stBoardImagePath = stPath;
	}

	public String getActivCellImagePath() {
		return m_stActiveCellImagePath;
	}
	public void setActivCellImagePath(String stPath) {
		m_stActiveCellImagePath = stPath;
	}

	public String getMarkedCellImagePath() {
		return m_stMarkedCellImagePath;
	}
	public void setMarkedCellImagePath(String stPath) {
		m_stMarkedCellImagePath = stPath;
	}
	
	public void addPosition(IPositionData oPosition) {
		m_mpPosition.put(oPosition.getName(), oPosition);
	}	
	public IPositionData getPosition(String stName) {
		return m_mpPosition.get(stName);
	}
	public List<IPositionData> getAllPositions() {
		return new ArrayList<IPositionData>( m_mpPosition.values());
	}
	public Map<String, IPositionData> getAllPositionsMap() {
		return m_mpPosition;
	}
	
	public void addPlayer(IPlayerData oPlayer) {
		m_mpPlayers.put(oPlayer.getName(), oPlayer);
	}
	public IPlayerData getPlayer(String stName) {
		return m_mpPlayers.get(stName);
	}	
	public List<IPlayerData> getAllPlayers() {
		return new ArrayList<IPlayerData>( m_mpPlayers.values());
	}

	public void addRule(IRuleData oRule) {
		m_mpRules.put(oRule.getName(), oRule);
	}
	public IRuleData getRule(String stName) {
		return m_mpRules.get(stName);
	}
	public List<IRuleData> getAllRules() {
		return new ArrayList<IRuleData>(m_mpRules.values());
	}

	public void addPiece(IPieceData oPiece) {
		m_mpPieces.put(oPiece.getName(), oPiece);
	}
	public IPieceData getPiece(String stName) {
		return m_mpPieces.get(stName);
	}
	public List<IPieceData> getAllPieces() {
		return new ArrayList<IPieceData>(m_mpPieces.values());
	}

	public Map<String, String> getPlayerMapping(String stName) {
		return m_mpMapping.get(stName);
	}	
    public void addMapping(String stPlayer, String stPiece, String stPosition) {
    	if( m_mpMapping.get(stPlayer) == null)
        	m_mpMapping.put(stPlayer, new HashMap<String, String>());	
    	m_mpMapping.get(stPlayer).put(stPosition, stPiece);
    }
    
	public IPositionData createPosition() {
		return new PositionData();
	}	

	public IPieceData createPiece() {
		return new PieceData();
	}	

	public IRuleData createRule() {
		return new RuleData();
	}

	public IPlayerData createPlayer() {
		return new PlayerData();
	}

	public BoardData getBoardData() {
		return this;
	}
	
	public void init() {
		
	}
}
