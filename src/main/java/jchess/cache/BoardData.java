package jchess.cache;

import java.util.HashMap;
import java.util.Map;

import jchess.common.IBoardData;
import jchess.common.IPiece;
import jchess.common.IPlayer;
import jchess.common.IPosition;
import jchess.common.IPositionData;
import jchess.common.IRule;
import jchess.common.enumerator.Direction;

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
    private Map<String, IPosition> m_mpPosition;
    private Map<String, IPlayer > m_mpPlayers;
    private Map<String, IPiece > m_mpPieces;
    private Map<String, Map<String, String>> m_mpMapping;
    private Map<String, IRule> m_mpRules;
    Map<String, IPositionData> temp = new HashMap<String, IPositionData>();

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
	
	public Map<String, IPositionData> getAllPositions_() {
		return temp;
	}

	public void populate2plyaerboard(){
        IPositionData oPosition = new PositionData('a',1, new Quadrilateral(60 * 0, 60 * 8, 60 * 1, 60 * 8, 60 * 1, 60 * 7, 60 * 0, 60 * 7),  "Black");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('a', 2, new Quadrilateral(60 * 0, 60 * 7, 60 * 1, 60 * 7, 60 * 1, 60 * 6, 60 * 0, 60 * 6), "White");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('a', 3, new Quadrilateral(60 * 0, 60 * 6, 60 * 1, 60 * 6, 60 * 1, 60 * 5, 60 * 0, 60 * 5), "Black");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('a', 4, new Quadrilateral(60 * 0, 60 * 5, 60 * 1, 60 * 5, 60 * 1, 60 * 4, 60 * 0, 60 * 4), "White");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('a', 5, new Quadrilateral(60 * 0, 60 * 4, 60 * 1, 60 * 4, 60 * 1, 60 * 3, 60 * 0, 60 * 3), "Black");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('a', 6, new Quadrilateral(60 * 0, 60 * 3, 60 * 1, 60 * 3, 60 * 1, 60 * 2, 60 * 0, 60 * 2), "White");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('a', 7, new Quadrilateral(60 * 0, 60 * 2, 60 * 1, 60 * 2, 60 * 1, 60 * 1, 60 * 0, 60 * 1), "Black");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('a', 8, new Quadrilateral(60 * 0, 60 * 1, 60 * 1, 60 * 1, 60 * 1, 60 * 0, 60 * 0, 60 * 0), "White");
        temp.put(oPosition.getName(), oPosition);

        oPosition = new PositionData('b', 1, new Quadrilateral(60 * 1, 60 * 8, 60 * 2, 60 * 8, 60 * 2, 60 * 7, 60 * 1, 60 * 7), "White");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('b', 2, new Quadrilateral(60 * 1, 60 * 7, 60 * 2, 60 * 7, 60 * 2, 60 * 6, 60 * 1, 60 * 6), "Black");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('b', 3, new Quadrilateral(60 * 1, 60 * 6, 60 * 2, 60 * 6, 60 * 2, 60 * 5, 60 * 1, 60 * 5), "White");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('b', 4, new Quadrilateral(60 * 1, 60 * 5, 60 * 2, 60 * 5, 60 * 2, 60 * 4, 60 * 1, 60 * 4), "Black");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('b', 5, new Quadrilateral(60 * 1, 60 * 4, 60 * 2, 60 * 4, 60 * 2, 60 * 3, 60 * 1, 60 * 3), "White");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('b', 6, new Quadrilateral(60 * 1, 60 * 3, 60 * 2, 60 * 3, 60 * 2, 60 * 2, 60 * 1, 60 * 2), "Black");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('b', 7, new Quadrilateral(60 * 1, 60 * 2, 60 * 2, 60 * 2, 60 * 2, 60 * 1, 60 * 1, 60 * 1), "White");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('b', 8, new Quadrilateral(60 * 1, 60 * 1, 60 * 2, 60 * 1, 60 * 2, 60 * 0, 60 * 1, 60 * 0), "Black");
        temp.put(oPosition.getName(), oPosition);

        oPosition = new PositionData('c', 1, new Quadrilateral(60 * 2, 60 * 8, 60 * 3, 60 * 8, 60 * 3, 60 * 7, 60 * 2, 60 * 7), "Black");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('c', 2, new Quadrilateral(60 * 2, 60 * 7, 60 * 3, 60 * 7, 60 * 3, 60 * 6, 60 * 2, 60 * 6), "White");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('c', 3, new Quadrilateral(60 * 2, 60 * 6, 60 * 3, 60 * 6, 60 * 3, 60 * 5, 60 * 2, 60 * 5), "Black");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('c', 4, new Quadrilateral(60 * 2, 60 * 5, 60 * 3, 60 * 5, 60 * 3, 60 * 4, 60 * 2, 60 * 4), "White");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('c', 5, new Quadrilateral(60 * 2, 60 * 4, 60 * 3, 60 * 4, 60 * 3, 60 * 3, 60 * 2, 60 * 3), "Black");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('c', 6, new Quadrilateral(60 * 2, 60 * 3, 60 * 3, 60 * 3, 60 * 3, 60 * 2, 60 * 2, 60 * 2), "White");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('c', 7, new Quadrilateral(60 * 2, 60 * 2, 60 * 3, 60 * 2, 60 * 3, 60 * 1, 60 * 2, 60 * 1), "Black");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('c', 8, new Quadrilateral(60 * 2, 60 * 1, 60 * 3, 60 * 1, 60 * 3, 60 * 0, 60 * 2, 60 * 0), "White");
        temp.put(oPosition.getName(), oPosition);

        oPosition = new PositionData('d', 1, new Quadrilateral(60 * 3, 60 * 8, 60 * 4, 60 * 8, 60 * 4, 60 * 7, 60 * 3, 60 * 7), "White");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('d', 2, new Quadrilateral(60 * 3, 60 * 7, 60 * 4, 60 * 7, 60 * 4, 60 * 6, 60 * 3, 60 * 6), "Black");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('d', 3, new Quadrilateral(60 * 3, 60 * 6, 60 * 4, 60 * 6, 60 * 4, 60 * 5, 60 * 3, 60 * 5), "White");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('d', 4, new Quadrilateral(60 * 3, 60 * 5, 60 * 4, 60 * 5, 60 * 4, 60 * 4, 60 * 3, 60 * 4), "Black");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('d', 5, new Quadrilateral(60 * 3, 60 * 4, 60 * 4, 60 * 4, 60 * 4, 60 * 3, 60 * 3, 60 * 3), "White");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('d', 6, new Quadrilateral(60 * 3, 60 * 3, 60 * 4, 60 * 3, 60 * 4, 60 * 2, 60 * 3, 60 * 2), "Black");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('d', 7, new Quadrilateral(60 * 3, 60 * 2, 60 * 4, 60 * 2, 60 * 4, 60 * 1, 60 * 3, 60 * 1), "White");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('d', 8, new Quadrilateral(60 * 3, 60 * 1, 60 * 4, 60 * 1, 60 * 4, 60 * 0, 60 * 3, 60 * 0), "Black");
        temp.put(oPosition.getName(), oPosition);

        oPosition = new PositionData('e', 1, new Quadrilateral(60 * 4, 60 * 8, 60 * 5, 60 * 8, 60 * 5, 60 * 7, 60 * 4, 60 * 7), "Black");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('e', 2, new Quadrilateral(60 * 4, 60 * 7, 60 * 5, 60 * 7, 60 * 5, 60 * 6, 60 * 4, 60 * 6), "White");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('e', 3, new Quadrilateral(60 * 4, 60 * 6, 60 * 5, 60 * 6, 60 * 5, 60 * 5, 60 * 4, 60 * 5), "Black");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('e', 4, new Quadrilateral(60 * 4, 60 * 5, 60 * 5, 60 * 5, 60 * 5, 60 * 4, 60 * 4, 60 * 4), "White");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('e', 5, new Quadrilateral(60 * 4, 60 * 4, 60 * 5, 60 * 4, 60 * 5, 60 * 3, 60 * 4, 60 * 3), "Black");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('e', 6, new Quadrilateral(60 * 4, 60 * 3, 60 * 5, 60 * 3, 60 * 5, 60 * 2, 60 * 4, 60 * 2), "White");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('e', 7, new Quadrilateral(60 * 4, 60 * 2, 60 * 5, 60 * 2, 60 * 5, 60 * 1, 60 * 4, 60 * 1), "Black");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('e', 8, new Quadrilateral(60 * 4, 60 * 1, 60 * 5, 60 * 1, 60 * 5, 60 * 0, 60 * 4, 60 * 0), "White");
        temp.put(oPosition.getName(), oPosition);

        oPosition = new PositionData('f', 1, new Quadrilateral(60 * 5, 60 * 8, 60 * 6, 60 * 8, 60 * 6, 60 * 7, 60 * 5, 60 * 7), "White");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('f', 2, new Quadrilateral(60 * 5, 60 * 7, 60 * 6, 60 * 7, 60 * 6, 60 * 6, 60 * 5, 60 * 6), "Black");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('f', 3, new Quadrilateral(60 * 5, 60 * 6, 60 * 6, 60 * 6, 60 * 6, 60 * 5, 60 * 5, 60 * 5), "White");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('f', 4, new Quadrilateral(60 * 5, 60 * 5, 60 * 6, 60 * 5, 60 * 6, 60 * 4, 60 * 5, 60 * 4), "Black");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('f', 5, new Quadrilateral(60 * 5, 60 * 4, 60 * 6, 60 * 4, 60 * 6, 60 * 3, 60 * 5, 60 * 3), "White");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('f', 6, new Quadrilateral(60 * 5, 60 * 3, 60 * 6, 60 * 3, 60 * 6, 60 * 2, 60 * 5, 60 * 2), "Black");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('f', 7, new Quadrilateral(60 * 5, 60 * 2, 60 * 6, 60 * 2, 60 * 6, 60 * 1, 60 * 5, 60 * 1), "White");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('f', 8, new Quadrilateral(60 * 5, 60 * 1, 60 * 6, 60 * 1, 60 * 6, 60 * 0, 60 * 5, 60 * 0), "Black");
        temp.put(oPosition.getName(), oPosition);

        oPosition = new PositionData('g', 1, new Quadrilateral(60 * 6, 60 * 8, 60 * 7, 60 * 8, 60 * 7, 60 * 7, 60 * 6, 60 * 7), "Black");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('g', 2, new Quadrilateral(60 * 6, 60 * 7, 60 * 7, 60 * 7, 60 * 7, 60 * 6, 60 * 6, 60 * 6), "White");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('g', 3, new Quadrilateral(60 * 6, 60 * 6, 60 * 7, 60 * 6, 60 * 7, 60 * 5, 60 * 6, 60 * 5), "Black");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('g', 4, new Quadrilateral(60 * 6, 60 * 5, 60 * 7, 60 * 5, 60 * 7, 60 * 4, 60 * 6, 60 * 4), "White");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('g', 5, new Quadrilateral(60 * 6, 60 * 4, 60 * 7, 60 * 4, 60 * 7, 60 * 3, 60 * 6, 60 * 3), "Black");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('g', 6, new Quadrilateral(60 * 6, 60 * 3, 60 * 7, 60 * 3, 60 * 7, 60 * 2, 60 * 6, 60 * 2), "White");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('g', 7, new Quadrilateral(60 * 6, 60 * 2, 60 * 7, 60 * 2, 60 * 7, 60 * 1, 60 * 6, 60 * 1), "Black");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('g', 8, new Quadrilateral(60 * 6, 60 * 1, 60 * 7, 60 * 1, 60 * 7, 60 * 0, 60 * 6, 60 * 0), "White");
        temp.put(oPosition.getName(), oPosition);

        oPosition = new PositionData('h', 1, new Quadrilateral(60 * 7, 60 * 8, 60 * 8, 60 * 8, 60 * 8, 60 * 7, 60 * 7, 60 * 7), "White");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('h', 2, new Quadrilateral(60 * 7, 60 * 7, 60 * 8, 60 * 7, 60 * 8, 60 * 6, 60 * 7, 60 * 6), "Black");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('h', 3, new Quadrilateral(60 * 7, 60 * 6, 60 * 8, 60 * 6, 60 * 8, 60 * 5, 60 * 7, 60 * 5), "White");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('h', 4, new Quadrilateral(60 * 7, 60 * 5, 60 * 8, 60 * 5, 60 * 8, 60 * 4, 60 * 7, 60 * 4), "Black");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('h', 5, new Quadrilateral(60 * 7, 60 * 4, 60 * 8, 60 * 4, 60 * 8, 60 * 3, 60 * 7, 60 * 3), "White");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('h', 6, new Quadrilateral(60 * 7, 60 * 3, 60 * 8, 60 * 3, 60 * 8, 60 * 2, 60 * 7, 60 * 2), "Black");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('h', 7, new Quadrilateral(60 * 7, 60 * 2, 60 * 8, 60 * 2, 60 * 8, 60 * 1, 60 * 7, 60 * 1), "White");
        temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('h', 8, new Quadrilateral(60 * 7, 60 * 1, 60 * 8, 60 * 1, 60 * 8, 60 * 0, 60 * 7, 60 * 0), "Black");
        temp.put(oPosition.getName(), oPosition);

        temp.get("a1").addPath(new Path("E1", Direction.EDGE, null));	//z1
        temp.get("a1").addPath(new Path("D1", Direction.VERTEX, null));	//z0
        temp.get("a1").addPath(new Path("E2", Direction.EDGE, null));	//a0
        temp.get("a1").addPath(new Path("D2", Direction.VERTEX, null));	//b0
        temp.get("a1").addPath(new Path("E3", Direction.EDGE, temp.get("b1")));
        temp.get("a1").addPath(new Path("D3", Direction.VERTEX, temp.get("b2")));
        temp.get("a1").addPath(new Path("E4", Direction.EDGE, temp.get("a2")));
        temp.get("a1").addPath(new Path("D4", Direction.VERTEX, null));	//z2        
        temp.get("a1").linkPaths("E1", "D1");
        temp.get("a1").linkPaths("D1", "E2");
        temp.get("a1").linkPaths("E2", "D2");
        temp.get("a1").linkPaths("D2", "E3");
        temp.get("a1").linkPaths("E3", "D3");
        temp.get("a1").linkPaths("D3", "E4");
        temp.get("a1").linkPaths("E4", "D4");
        temp.get("a1").linkPaths("D4", "E1");
        temp.get("a2").addPath(new Path("E1", Direction.EDGE, null));	//z2
        temp.get("a2").addPath(new Path("D1", Direction.VERTEX, null));	//z1
        temp.get("a2").addPath(new Path("E2", Direction.EDGE, temp.get("a1")));	//a1
        temp.get("a2").addPath(new Path("D2", Direction.VERTEX, temp.get("b1")));	//b1
        temp.get("a2").addPath(new Path("E3", Direction.EDGE, temp.get("b2")));
        temp.get("a2").addPath(new Path("D3", Direction.VERTEX, temp.get("b3")));
        temp.get("a2").addPath(new Path("E4", Direction.EDGE, temp.get("a3")));
        temp.get("a2").addPath(new Path("D4", Direction.VERTEX, null));	//z3        
        temp.get("a2").linkPaths("E1", "D1");
        temp.get("a2").linkPaths("D1", "E2");
        temp.get("a2").linkPaths("E2", "D2");
        temp.get("a2").linkPaths("D2", "E3");
        temp.get("a2").linkPaths("E3", "D3");
        temp.get("a2").linkPaths("D3", "E4");
        temp.get("a2").linkPaths("E4", "D4");
        temp.get("a2").linkPaths("D4", "E1");
        temp.get("a3").addPath(new Path("E1", Direction.EDGE, null));	//z3
        temp.get("a3").addPath(new Path("D1", Direction.VERTEX, null));	//z2
        temp.get("a3").addPath(new Path("E2", Direction.EDGE, temp.get("a2")));	//a2
        temp.get("a3").addPath(new Path("D2", Direction.VERTEX, temp.get("b2")));	//b2
        temp.get("a3").addPath(new Path("E3", Direction.EDGE, temp.get("b3")));
        temp.get("a3").addPath(new Path("D3", Direction.VERTEX, temp.get("b4")));
        temp.get("a3").addPath(new Path("E4", Direction.EDGE, temp.get("a4")));
        temp.get("a3").addPath(new Path("D4", Direction.VERTEX, null));	//z4        
        temp.get("a3").linkPaths("E1", "D1");
        temp.get("a3").linkPaths("D1", "E2");
        temp.get("a3").linkPaths("E2", "D2");
        temp.get("a3").linkPaths("D2", "E3");
        temp.get("a3").linkPaths("E3", "D3");
        temp.get("a3").linkPaths("D3", "E4");
        temp.get("a3").linkPaths("E4", "D4");
        temp.get("a3").linkPaths("D4", "E1");
        
        temp.get("a4").addPath(new Path("E1", Direction.EDGE, null));	//z4
        temp.get("a4").addPath(new Path("D1", Direction.VERTEX, null));	//z3
        temp.get("a4").addPath(new Path("E2", Direction.EDGE, temp.get("a3")));	//a3
        temp.get("a4").addPath(new Path("D2", Direction.VERTEX, temp.get("b3")));	//b3
        temp.get("a4").addPath(new Path("E3", Direction.EDGE, temp.get("b4")));
        temp.get("a4").addPath(new Path("D3", Direction.VERTEX, temp.get("b5")));
        temp.get("a4").addPath(new Path("E4", Direction.EDGE, temp.get("a5")));
        temp.get("a4").addPath(new Path("D4", Direction.VERTEX, null));	//z5        
        temp.get("a4").linkPaths("E1", "D1");
        temp.get("a4").linkPaths("D1", "E2");
        temp.get("a4").linkPaths("E2", "D2");
        temp.get("a4").linkPaths("D2", "E3");
        temp.get("a4").linkPaths("E3", "D3");
        temp.get("a4").linkPaths("D3", "E4");
        temp.get("a4").linkPaths("E4", "D4");
        temp.get("a4").linkPaths("D4", "E1");
        
        temp.get("a5").addPath(new Path("E1", Direction.EDGE, null));	//z5
        temp.get("a5").addPath(new Path("D1", Direction.VERTEX, null));	//z4
        temp.get("a5").addPath(new Path("E2", Direction.EDGE, temp.get("a4")));	//a4
        temp.get("a5").addPath(new Path("D2", Direction.VERTEX, temp.get("b4")));	//b4
        temp.get("a5").addPath(new Path("E3", Direction.EDGE, temp.get("b5")));
        temp.get("a5").addPath(new Path("D3", Direction.VERTEX, temp.get("b6")));
        temp.get("a5").addPath(new Path("E4", Direction.EDGE, temp.get("a6")));
        temp.get("a5").addPath(new Path("D4", Direction.VERTEX, null));	//z6        
        temp.get("a5").linkPaths("E1", "D1");
        temp.get("a5").linkPaths("D1", "E2");
        temp.get("a5").linkPaths("E2", "D2");
        temp.get("a5").linkPaths("D2", "E3");
        temp.get("a5").linkPaths("E3", "D3");
        temp.get("a5").linkPaths("D3", "E4");
        temp.get("a5").linkPaths("E4", "D4");
        temp.get("a5").linkPaths("D4", "E1");
    
        
        temp.get("a6").addPath(new Path("E1", Direction.EDGE, null));	//z5
        temp.get("a6").addPath(new Path("D1", Direction.VERTEX, null));	//z4
        temp.get("a6").addPath(new Path("E2", Direction.EDGE, temp.get("a5")));	//a4
        temp.get("a6").addPath(new Path("D2", Direction.VERTEX, temp.get("b5")));	//b4
        temp.get("a6").addPath(new Path("E3", Direction.EDGE, temp.get("b6")));
        temp.get("a6").addPath(new Path("D3", Direction.VERTEX, temp.get("b7")));
        temp.get("a6").addPath(new Path("E4", Direction.EDGE, temp.get("a7")));
        temp.get("a6").addPath(new Path("D4", Direction.VERTEX, null));	//z6        
        temp.get("a6").linkPaths("E1", "D1");
        temp.get("a6").linkPaths("D1", "E2");
        temp.get("a6").linkPaths("E2", "D2");
        temp.get("a6").linkPaths("D2", "E3");
        temp.get("a6").linkPaths("E3", "D3");
        temp.get("a6").linkPaths("D3", "E4");
        temp.get("a6").linkPaths("E4", "D4");
        temp.get("a6").linkPaths("D4", "E1");
      
        
        temp.get("a7").addPath(new Path("E1", Direction.EDGE, null));	//z5
        temp.get("a7").addPath(new Path("D1", Direction.VERTEX, null));	//z4
        temp.get("a7").addPath(new Path("E2", Direction.EDGE, temp.get("a6")));	//a4
        temp.get("a7").addPath(new Path("D2", Direction.VERTEX, temp.get("b6")));	//b4
        temp.get("a7").addPath(new Path("E3", Direction.EDGE, temp.get("b7")));
        temp.get("a7").addPath(new Path("D3", Direction.VERTEX, temp.get("b8")));
        temp.get("a7").addPath(new Path("E4", Direction.EDGE, temp.get("a8")));
        temp.get("a7").addPath(new Path("D4", Direction.VERTEX, null));	//z6        
        temp.get("a7").linkPaths("E1", "D1");
        temp.get("a7").linkPaths("D1", "E2");
        temp.get("a7").linkPaths("E2", "D2");
        temp.get("a7").linkPaths("D2", "E3");
        temp.get("a7").linkPaths("E3", "D3");
        temp.get("a7").linkPaths("D3", "E4");
        temp.get("a7").linkPaths("E4", "D4");
        temp.get("a7").linkPaths("D4", "E1");
       
        
        temp.get("a8").addPath(new Path("E1", Direction.EDGE, null));	//z5
        temp.get("a8").addPath(new Path("D1", Direction.VERTEX, null));	//z4
        temp.get("a8").addPath(new Path("E2", Direction.EDGE, temp.get("a7")));	//a4
        temp.get("a8").addPath(new Path("D2", Direction.VERTEX, temp.get("b7")));	//b4
        temp.get("a8").addPath(new Path("E3", Direction.EDGE, temp.get("b8")));
        temp.get("a8").addPath(new Path("D3", Direction.VERTEX, null));
        temp.get("a8").addPath(new Path("E4", Direction.EDGE, null));
        temp.get("a8").addPath(new Path("D4", Direction.VERTEX, null));	//z6        
        temp.get("a8").linkPaths("E1", "D1");
        temp.get("a8").linkPaths("D1", "E2");
        temp.get("a8").linkPaths("E2", "D2");
        temp.get("a8").linkPaths("D2", "E3");
        temp.get("a8").linkPaths("E3", "D3");
        temp.get("a8").linkPaths("D3", "E4");
        temp.get("a8").linkPaths("E4", "D4");
        temp.get("a8").linkPaths("D4", "E1");
     
        temp.get("b1").addPath(new Path("E1", Direction.EDGE, temp.get("a1")));	//a1
        temp.get("b1").addPath(new Path("D1", Direction.VERTEX, null));	//a0
        temp.get("b1").addPath(new Path("E2", Direction.EDGE, null));	//b0
        temp.get("b1").addPath(new Path("D2", Direction.VERTEX, null));	//c0
        temp.get("b1").addPath(new Path("E3", Direction.EDGE, temp.get("c1")));
        temp.get("b1").addPath(new Path("D3", Direction.VERTEX, temp.get("c2")));
        temp.get("b1").addPath(new Path("E4", Direction.EDGE, temp.get("b2")));
        temp.get("b1").addPath(new Path("D4", Direction.VERTEX, temp.get("a2")));	//a2        
        temp.get("b1").linkPaths("E1", "D1");
        temp.get("b1").linkPaths("D1", "E2");
        temp.get("b1").linkPaths("E2", "D2");
        temp.get("b1").linkPaths("D2", "E3");
        temp.get("b1").linkPaths("E3", "D3");
        temp.get("b1").linkPaths("D3", "E4");
        temp.get("b1").linkPaths("E4", "D4");
        temp.get("b1").linkPaths("D4", "E1");
        
        temp.get("b2").addPath(new Path("E1", Direction.EDGE, temp.get("a2")));	//a2
        temp.get("b2").addPath(new Path("D1", Direction.VERTEX, temp.get("a1")));	//a1
        temp.get("b2").addPath(new Path("E2", Direction.EDGE, temp.get("b1")));	//b1
        temp.get("b2").addPath(new Path("D2", Direction.VERTEX, temp.get("c1")));	//c1
        temp.get("b2").addPath(new Path("E3", Direction.EDGE, temp.get("c2")));
        temp.get("b2").addPath(new Path("D3", Direction.VERTEX, temp.get("c3")));
        temp.get("b2").addPath(new Path("E4", Direction.EDGE, temp.get("b3")));
        temp.get("b2").addPath(new Path("D4", Direction.VERTEX, temp.get("a3")));	        
        temp.get("b2").linkPaths("E1", "D1");
        temp.get("b2").linkPaths("D1", "E2");
        temp.get("b2").linkPaths("E2", "D2");
        temp.get("b2").linkPaths("D2", "E3");
        temp.get("b2").linkPaths("E3", "D3");
        temp.get("b2").linkPaths("D3", "E4");
        temp.get("b2").linkPaths("E4", "D4");
        temp.get("b2").linkPaths("D4", "E1");
        
        temp.get("b3").addPath(new Path("E1", Direction.EDGE, temp.get("a3")));	
        temp.get("b3").addPath(new Path("D1", Direction.VERTEX, temp.get("a2")));	
        temp.get("b3").addPath(new Path("E2", Direction.EDGE, temp.get("b2")));	
        temp.get("b3").addPath(new Path("D2", Direction.VERTEX, temp.get("c2")));	
        temp.get("b3").addPath(new Path("E3", Direction.EDGE, temp.get("c3")));
        temp.get("b3").addPath(new Path("D3", Direction.VERTEX, temp.get("c4")));
        temp.get("b3").addPath(new Path("E4", Direction.EDGE, temp.get("b4")));
        temp.get("b3").addPath(new Path("D4", Direction.VERTEX, temp.get("a4")));	        
        temp.get("b3").linkPaths("E1", "D1");
        temp.get("b3").linkPaths("D1", "E2");
        temp.get("b3").linkPaths("E2", "D2");
        temp.get("b3").linkPaths("D2", "E3");
        temp.get("b3").linkPaths("E3", "D3");
        temp.get("b3").linkPaths("D3", "E4");
        temp.get("b3").linkPaths("E4", "D4");
        temp.get("b3").linkPaths("D4", "E1");
        temp.get("b4").addPath(new Path("E1", Direction.EDGE, temp.get("a4")));	
        temp.get("b4").addPath(new Path("D1", Direction.VERTEX, temp.get("a3")));	
        temp.get("b4").addPath(new Path("E2", Direction.EDGE, temp.get("b3")));	
        temp.get("b4").addPath(new Path("D2", Direction.VERTEX, temp.get("c3")));	
        temp.get("b4").addPath(new Path("E3", Direction.EDGE, temp.get("c4")));
        temp.get("b4").addPath(new Path("D3", Direction.VERTEX, temp.get("c5")));
        temp.get("b4").addPath(new Path("E4", Direction.EDGE, temp.get("b5")));
        temp.get("b4").addPath(new Path("D4", Direction.VERTEX, temp.get("a5")));	        
        temp.get("b4").linkPaths("E1", "D1");
        temp.get("b4").linkPaths("D1", "E2");
        temp.get("b4").linkPaths("E2", "D2");
        temp.get("b4").linkPaths("D2", "E3");
        temp.get("b4").linkPaths("E3", "D3");
        temp.get("b4").linkPaths("D3", "E4");
        temp.get("b4").linkPaths("E4", "D4");
        temp.get("b4").linkPaths("D4", "E1");
        
        temp.get("b5").addPath(new Path("E1", Direction.EDGE, temp.get("a5")));	
        temp.get("b5").addPath(new Path("D1", Direction.VERTEX, temp.get("a4")));	
        temp.get("b5").addPath(new Path("E2", Direction.EDGE, temp.get("b4")));	
        temp.get("b5").addPath(new Path("D2", Direction.VERTEX, temp.get("c4")));	
        temp.get("b5").addPath(new Path("E3", Direction.EDGE, temp.get("c5")));
        temp.get("b5").addPath(new Path("D3", Direction.VERTEX, temp.get("c6")));
        temp.get("b5").addPath(new Path("E4", Direction.EDGE, temp.get("b6")));
        temp.get("b5").addPath(new Path("D4", Direction.VERTEX, temp.get("a6")));	        
        temp.get("b5").linkPaths("E1", "D1");
        temp.get("b5").linkPaths("D1", "E2");
        temp.get("b5").linkPaths("E2", "D2");
        temp.get("b5").linkPaths("D2", "E3");
        temp.get("b5").linkPaths("E3", "D3");
        temp.get("b5").linkPaths("D3", "E4");
        temp.get("b5").linkPaths("E4", "D4");
        temp.get("b5").linkPaths("D4", "E1");
        temp.get("b6").addPath(new Path("E1", Direction.EDGE, temp.get("a6")));	
        temp.get("b6").addPath(new Path("D1", Direction.VERTEX, temp.get("a5")));	
        temp.get("b6").addPath(new Path("E2", Direction.EDGE, temp.get("b5")));	
        temp.get("b6").addPath(new Path("D2", Direction.VERTEX, temp.get("c5")));	
        temp.get("b6").addPath(new Path("E3", Direction.EDGE, temp.get("c6")));
        temp.get("b6").addPath(new Path("D3", Direction.VERTEX, temp.get("c7")));
        temp.get("b6").addPath(new Path("E4", Direction.EDGE, temp.get("b7")));
        temp.get("b6").addPath(new Path("D4", Direction.VERTEX, temp.get("a7")));	        
        temp.get("b6").linkPaths("E1", "D1");
        temp.get("b6").linkPaths("D1", "E2");
        temp.get("b6").linkPaths("E2", "D2");
        temp.get("b6").linkPaths("D2", "E3");
        temp.get("b6").linkPaths("E3", "D3");
        temp.get("b6").linkPaths("D3", "E4");
        temp.get("b6").linkPaths("E4", "D4");
        temp.get("b6").linkPaths("D4", "E1");
        
     
        
        temp.get("b7").addPath(new Path("E1", Direction.EDGE, temp.get("a7")));	
        temp.get("b7").addPath(new Path("D1", Direction.VERTEX, temp.get("a6")));	
        temp.get("b7").addPath(new Path("E2", Direction.EDGE, temp.get("b6")));	
        temp.get("b7").addPath(new Path("D2", Direction.VERTEX, temp.get("c6")));	
        temp.get("b7").addPath(new Path("E3", Direction.EDGE, temp.get("c7")));
        temp.get("b7").addPath(new Path("D3", Direction.VERTEX, temp.get("c8")));
        temp.get("b7").addPath(new Path("E4", Direction.EDGE, temp.get("b8")));
        temp.get("b7").addPath(new Path("D4", Direction.VERTEX, temp.get("a8")));	        
        temp.get("b7").linkPaths("E1", "D1");
        temp.get("b7").linkPaths("D1", "E2");
        temp.get("b7").linkPaths("E2", "D2");
        temp.get("b7").linkPaths("D2", "E3");
        temp.get("b7").linkPaths("E3", "D3");
        temp.get("b7").linkPaths("D3", "E4");
        temp.get("b7").linkPaths("E4", "D4");
        temp.get("b7").linkPaths("D4", "E1");
        
        temp.get("b8").addPath(new Path("E1", Direction.EDGE, temp.get("a8")));	
        temp.get("b8").addPath(new Path("D1", Direction.VERTEX, temp.get("a7")));	
        temp.get("b8").addPath(new Path("E2", Direction.EDGE, temp.get("b7")));	
        temp.get("b8").addPath(new Path("D2", Direction.VERTEX, temp.get("c7")));	
        temp.get("b8").addPath(new Path("E3", Direction.EDGE, temp.get("c8")));
        temp.get("b8").addPath(new Path("D3", Direction.VERTEX, null));
        temp.get("b8").addPath(new Path("E4", Direction.EDGE, null));
        temp.get("b8").addPath(new Path("D4", Direction.VERTEX, null));	        
        temp.get("b8").linkPaths("E1", "D1");
        temp.get("b8").linkPaths("D1", "E2");
        temp.get("b8").linkPaths("E2", "D2");
        temp.get("b8").linkPaths("D2", "E3");
        temp.get("b8").linkPaths("E3", "D3");
        temp.get("b8").linkPaths("D3", "E4");
        temp.get("b8").linkPaths("E4", "D4");
        temp.get("b8").linkPaths("D4", "E1");
        temp.get("c1").addPath(new Path("E1", Direction.EDGE, temp.get("b1")));	//b1
        temp.get("c1").addPath(new Path("D1", Direction.VERTEX, null));	//b0
        temp.get("c1").addPath(new Path("E2", Direction.EDGE, null));	//c0
        temp.get("c1").addPath(new Path("D2", Direction.VERTEX, null));	//d0
        temp.get("c1").addPath(new Path("E3", Direction.EDGE, temp.get("d1")));
        temp.get("c1").addPath(new Path("D3", Direction.VERTEX, temp.get("d2")));
        temp.get("c1").addPath(new Path("E4", Direction.EDGE, temp.get("c2")));
        temp.get("c1").addPath(new Path("D4", Direction.VERTEX, temp.get("b2")));	//b2        
        temp.get("c1").linkPaths("E1", "D1");
        temp.get("c1").linkPaths("D1", "E2");
        temp.get("c1").linkPaths("E2", "D2");
        temp.get("c1").linkPaths("D2", "E3");
        temp.get("c1").linkPaths("E3", "D3");
        temp.get("c1").linkPaths("D3", "E4");
        temp.get("c1").linkPaths("E4", "D4");
        temp.get("c1").linkPaths("D4", "E1");
        temp.get("c2").addPath(new Path("E1", Direction.EDGE, temp.get("b2")));
        temp.get("c2").addPath(new Path("D1", Direction.VERTEX, temp.get("b1")));	//b1
        temp.get("c2").addPath(new Path("E2", Direction.EDGE, temp.get("c1")));	//c1
        temp.get("c2").addPath(new Path("D2", Direction.VERTEX, temp.get("d1")));	//d1
        temp.get("c2").addPath(new Path("E3", Direction.EDGE, temp.get("d2")));
        temp.get("c2").addPath(new Path("D3", Direction.VERTEX, temp.get("d3")));
        temp.get("c2").addPath(new Path("E4", Direction.EDGE, temp.get("c3")));
        temp.get("c2").addPath(new Path("D4", Direction.VERTEX, temp.get("b3")));
        temp.get("c2").linkPaths("E1", "D1");
        temp.get("c2").linkPaths("D1", "E2");
        temp.get("c2").linkPaths("E2", "D2");
        temp.get("c2").linkPaths("D2", "E3");
        temp.get("c2").linkPaths("E3", "D3");
        temp.get("c2").linkPaths("D3", "E4");
        temp.get("c2").linkPaths("E4", "D4");
        temp.get("c2").linkPaths("D4", "E1");
        temp.get("c3").addPath(new Path("E1", Direction.EDGE, temp.get("b3")));
        temp.get("c3").addPath(new Path("D1", Direction.VERTEX, temp.get("b2")));
        temp.get("c3").addPath(new Path("E2", Direction.EDGE, temp.get("c2")));	
        temp.get("c3").addPath(new Path("D2", Direction.VERTEX, temp.get("d2")));
        temp.get("c3").addPath(new Path("E3", Direction.EDGE, temp.get("d3")));
        temp.get("c3").addPath(new Path("D3", Direction.VERTEX, temp.get("d4")));
        temp.get("c3").addPath(new Path("E4", Direction.EDGE, temp.get("c4")));
        temp.get("c3").addPath(new Path("D4", Direction.VERTEX, temp.get("b4")));
        temp.get("c3").linkPaths("E1", "D1");
        temp.get("c3").linkPaths("D1", "E2");
        temp.get("c3").linkPaths("E2", "D2");
        temp.get("c3").linkPaths("D2", "E3");
        temp.get("c3").linkPaths("E3", "D3");
        temp.get("c3").linkPaths("D3", "E4");
        temp.get("c3").linkPaths("E4", "D4");
        temp.get("c3").linkPaths("D4", "E1");
        temp.get("c4").addPath(new Path("E1", Direction.EDGE, temp.get("b4")));
        temp.get("c4").addPath(new Path("D1", Direction.VERTEX, temp.get("b3")));
        temp.get("c4").addPath(new Path("E2", Direction.EDGE, temp.get("c3")));
        temp.get("c4").addPath(new Path("D2", Direction.VERTEX, temp.get("d3")));
        temp.get("c4").addPath(new Path("E3", Direction.EDGE, temp.get("d4")));
        temp.get("c4").addPath(new Path("D3", Direction.VERTEX, temp.get("d5")));
        temp.get("c4").addPath(new Path("E4", Direction.EDGE, temp.get("c5")));
        temp.get("c4").addPath(new Path("D4", Direction.VERTEX, temp.get("b5")));
        temp.get("c4").linkPaths("E1", "D1");
        temp.get("c4").linkPaths("D1", "E2");
        temp.get("c4").linkPaths("E2", "D2");
        temp.get("c4").linkPaths("D2", "E3");
        temp.get("c4").linkPaths("E3", "D3");
        temp.get("c4").linkPaths("D3", "E4");
        temp.get("c4").linkPaths("E4", "D4");
        temp.get("c4").linkPaths("D4", "E1");
        temp.get("c5").addPath(new Path("E1", Direction.EDGE, temp.get("b5")));
        temp.get("c5").addPath(new Path("D1", Direction.VERTEX, temp.get("b4")));
        temp.get("c5").addPath(new Path("E2", Direction.EDGE, temp.get("c4")));
        temp.get("c5").addPath(new Path("D2", Direction.VERTEX, temp.get("d4")));
        temp.get("c5").addPath(new Path("E3", Direction.EDGE, temp.get("d5")));
        temp.get("c5").addPath(new Path("D3", Direction.VERTEX, temp.get("d6")));
        temp.get("c5").addPath(new Path("E4", Direction.EDGE, temp.get("c6")));
        temp.get("c5").addPath(new Path("D4", Direction.VERTEX, temp.get("b6")));
        temp.get("c5").linkPaths("E1", "D1");
        temp.get("c5").linkPaths("D1", "E2");
        temp.get("c5").linkPaths("E2", "D2");
        temp.get("c5").linkPaths("D2", "E3");
        temp.get("c5").linkPaths("E3", "D3");
        temp.get("c5").linkPaths("D3", "E4");
        temp.get("c5").linkPaths("E4", "D4");
        temp.get("c5").linkPaths("D4", "E1");
        temp.get("c6").addPath(new Path("E1", Direction.EDGE, temp.get("b6")));
        temp.get("c6").addPath(new Path("D1", Direction.VERTEX, temp.get("b5")));
        temp.get("c6").addPath(new Path("E2", Direction.EDGE, temp.get("c5")));
        temp.get("c6").addPath(new Path("D2", Direction.VERTEX, temp.get("d5")));
        temp.get("c6").addPath(new Path("E3", Direction.EDGE, temp.get("d6")));
        temp.get("c6").addPath(new Path("D3", Direction.VERTEX, temp.get("d7")));
        temp.get("c6").addPath(new Path("E4", Direction.EDGE, temp.get("c7")));
        temp.get("c6").addPath(new Path("D4", Direction.VERTEX, temp.get("b7")));
        temp.get("c6").linkPaths("E1", "D1");
        temp.get("c6").linkPaths("D1", "E2");
        temp.get("c6").linkPaths("E2", "D2");
        temp.get("c6").linkPaths("D2", "E3");
        temp.get("c6").linkPaths("E3", "D3");
        temp.get("c6").linkPaths("D3", "E4");
        temp.get("c6").linkPaths("E4", "D4");
        temp.get("c6").linkPaths("D4", "E1");
        temp.get("c7").addPath(new Path("E1", Direction.EDGE, temp.get("b7")));
        temp.get("c7").addPath(new Path("D1", Direction.VERTEX, temp.get("b6")));
        temp.get("c7").addPath(new Path("E2", Direction.EDGE, temp.get("c6")));
        temp.get("c7").addPath(new Path("D2", Direction.VERTEX, temp.get("d6")));
        temp.get("c7").addPath(new Path("E3", Direction.EDGE, temp.get("d7")));
        temp.get("c7").addPath(new Path("D3", Direction.VERTEX, temp.get("d8")));
        temp.get("c7").addPath(new Path("E4", Direction.EDGE, temp.get("c8")));
        temp.get("c7").addPath(new Path("D4", Direction.VERTEX, temp.get("b8")));
        temp.get("c7").linkPaths("E1", "D1");
        temp.get("c7").linkPaths("D1", "E2");
        temp.get("c7").linkPaths("E2", "D2");
        temp.get("c7").linkPaths("D2", "E3");
        temp.get("c7").linkPaths("E3", "D3");
        temp.get("c7").linkPaths("D3", "E4");
        temp.get("c7").linkPaths("E4", "D4");
        temp.get("c7").linkPaths("D4", "E1");
        temp.get("c7").addPath(new Path("E1", Direction.EDGE, temp.get("b8")));
        temp.get("c7").addPath(new Path("D1", Direction.VERTEX, temp.get("b7")));
        temp.get("c7").addPath(new Path("E2", Direction.EDGE, temp.get("c7")));
        temp.get("c7").addPath(new Path("D2", Direction.VERTEX, temp.get("d7")));
        temp.get("c7").addPath(new Path("E3", Direction.EDGE, temp.get("d8")));
        temp.get("c7").addPath(new Path("D3", Direction.VERTEX, null));
        temp.get("c7").addPath(new Path("E4", Direction.EDGE, null));
        temp.get("c7").addPath(new Path("D4", Direction.VERTEX, null));
        temp.get("c7").linkPaths("E1", "D1");
        temp.get("c7").linkPaths("D1", "E2");
        temp.get("c7").linkPaths("E2", "D2");
        temp.get("c7").linkPaths("D2", "E3");
        temp.get("c7").linkPaths("E3", "D3");
        temp.get("c7").linkPaths("D3", "E4");
        temp.get("c7").linkPaths("E4", "D4");
        temp.get("c7").linkPaths("D4", "E1"); 
        // d
        temp.get("d1").addPath(new Path("E1", Direction.EDGE, temp.get("c1")));	//c1
        temp.get("d1").addPath(new Path("D1", Direction.VERTEX, null));	//c0
        temp.get("d1").addPath(new Path("E2", Direction.EDGE, null));	//d0
        temp.get("d1").addPath(new Path("D2", Direction.VERTEX, null));	//e0
        temp.get("d1").addPath(new Path("E3", Direction.EDGE, temp.get("e1")));
        temp.get("d1").addPath(new Path("D3", Direction.VERTEX, temp.get("e2")));
        temp.get("d1").addPath(new Path("E4", Direction.EDGE, temp.get("d2")));
        temp.get("d1").addPath(new Path("D4", Direction.VERTEX, temp.get("c2")));	//c2        
        temp.get("d1").linkPaths("E1", "D1");
        temp.get("d1").linkPaths("D1", "E2");
        temp.get("d1").linkPaths("E2", "D2");
        temp.get("d1").linkPaths("D2", "E3");
        temp.get("d1").linkPaths("E3", "D3");
        temp.get("d1").linkPaths("D3", "E4");
        temp.get("d1").linkPaths("E4", "D4");
        temp.get("d1").linkPaths("D4", "E1");
        temp.get("d2").addPath(new Path("E1", Direction.EDGE, temp.get("c2")));
        temp.get("d2").addPath(new Path("D1", Direction.VERTEX, temp.get("c1")));	//c1
        temp.get("d2").addPath(new Path("E2", Direction.EDGE, temp.get("d1")));	//d1
        temp.get("d2").addPath(new Path("D2", Direction.VERTEX, temp.get("e1")));	//e1
        temp.get("d2").addPath(new Path("E3", Direction.EDGE, temp.get("e2")));
        temp.get("d2").addPath(new Path("D3", Direction.VERTEX, temp.get("e3")));
        temp.get("d2").addPath(new Path("E4", Direction.EDGE, temp.get("d3")));
        temp.get("d2").addPath(new Path("D4", Direction.VERTEX, temp.get("c3")));	
        temp.get("d2").linkPaths("E1", "D1");
        temp.get("d2").linkPaths("D1", "E2");
        temp.get("d2").linkPaths("E2", "D2");
        temp.get("d2").linkPaths("D2", "E3");
        temp.get("d2").linkPaths("E3", "D3");
        temp.get("d2").linkPaths("D3", "E4");
        temp.get("d2").linkPaths("E4", "D4");
        temp.get("d2").linkPaths("D4", "E1");
        temp.get("d3").addPath(new Path("E1", Direction.EDGE, temp.get("c3")));
        temp.get("d3").addPath(new Path("D1", Direction.VERTEX, temp.get("c2")));	
        temp.get("d3").addPath(new Path("E2", Direction.EDGE, temp.get("d2")));	
        temp.get("d3").addPath(new Path("D2", Direction.VERTEX, temp.get("e2")));	
        temp.get("d3").addPath(new Path("E3", Direction.EDGE, temp.get("e3")));
        temp.get("d3").addPath(new Path("D3", Direction.VERTEX, temp.get("e4")));
        temp.get("d3").addPath(new Path("E4", Direction.EDGE, temp.get("d4")));
        temp.get("d3").addPath(new Path("D4", Direction.VERTEX, temp.get("c4")));
        temp.get("d3").linkPaths("E1", "D1");
        temp.get("d3").linkPaths("D1", "E2");
        temp.get("d3").linkPaths("E2", "D2");
        temp.get("d3").linkPaths("D2", "E3");
        temp.get("d3").linkPaths("E3", "D3");
        temp.get("d3").linkPaths("D3", "E4");
        temp.get("d3").linkPaths("E4", "D4");
        temp.get("d3").linkPaths("D4", "E1");
        temp.get("d4").addPath(new Path("E1", Direction.EDGE, temp.get("c4")));
        temp.get("d4").addPath(new Path("D1", Direction.VERTEX, temp.get("c3")));
        temp.get("d4").addPath(new Path("E2", Direction.EDGE, temp.get("d3")));
        temp.get("d4").addPath(new Path("D2", Direction.VERTEX, temp.get("e3")));
        temp.get("d4").addPath(new Path("E3", Direction.EDGE, temp.get("e4")));
        temp.get("d4").addPath(new Path("D3", Direction.VERTEX, temp.get("e5")));
        temp.get("d4").addPath(new Path("E4", Direction.EDGE, temp.get("d5")));
        temp.get("d4").addPath(new Path("D4", Direction.VERTEX, temp.get("c5")));
        temp.get("d4").linkPaths("E1", "D1");
        temp.get("d4").linkPaths("D1", "E2");
        temp.get("d4").linkPaths("E2", "D2");
        temp.get("d4").linkPaths("D2", "E3");
        temp.get("d4").linkPaths("E3", "D3");
        temp.get("d4").linkPaths("D3", "E4");
        temp.get("d4").linkPaths("E4", "D4");
        temp.get("d4").linkPaths("D4", "E1");
        temp.get("d5").addPath(new Path("E1", Direction.EDGE, temp.get("c5")));
        temp.get("d5").addPath(new Path("D1", Direction.VERTEX, temp.get("c4")));
        temp.get("d5").addPath(new Path("E2", Direction.EDGE, temp.get("d4")));
        temp.get("d5").addPath(new Path("D2", Direction.VERTEX, temp.get("e4")));
        temp.get("d5").addPath(new Path("E3", Direction.EDGE, temp.get("e5")));
        temp.get("d5").addPath(new Path("D3", Direction.VERTEX, temp.get("e6")));
        temp.get("d5").addPath(new Path("E4", Direction.EDGE, temp.get("d6")));
        temp.get("d5").addPath(new Path("D4", Direction.VERTEX, temp.get("c6")));
        temp.get("d5").linkPaths("E1", "D1");
        temp.get("d5").linkPaths("D1", "E2");
        temp.get("d5").linkPaths("E2", "D2");
        temp.get("d5").linkPaths("D2", "E3");
        temp.get("d5").linkPaths("E3", "D3");
        temp.get("d5").linkPaths("D3", "E4");
        temp.get("d5").linkPaths("E4", "D4");
        temp.get("d5").linkPaths("D4", "E1");
        temp.get("d6").addPath(new Path("E1", Direction.EDGE, temp.get("c6")));
        temp.get("d6").addPath(new Path("D1", Direction.VERTEX, temp.get("c5")));
        temp.get("d6").addPath(new Path("E2", Direction.EDGE, temp.get("d5")));
        temp.get("d6").addPath(new Path("D2", Direction.VERTEX, temp.get("e5")));
        temp.get("d6").addPath(new Path("E3", Direction.EDGE, temp.get("e6")));
        temp.get("d6").addPath(new Path("D3", Direction.VERTEX, temp.get("e7")));
        temp.get("d6").addPath(new Path("E4", Direction.EDGE, temp.get("d7")));
        temp.get("d6").addPath(new Path("D4", Direction.VERTEX, temp.get("c7")));
        temp.get("d6").linkPaths("E1", "D1");
        temp.get("d6").linkPaths("D1", "E2");
        temp.get("d6").linkPaths("E2", "D2");
        temp.get("d6").linkPaths("D2", "E3");
        temp.get("d6").linkPaths("E3", "D3");
        temp.get("d6").linkPaths("D3", "E4");
        temp.get("d6").linkPaths("E4", "D4");
        temp.get("d6").linkPaths("D4", "E1");
        temp.get("d7").addPath(new Path("E1", Direction.EDGE, temp.get("c7")));
        temp.get("d7").addPath(new Path("D1", Direction.VERTEX, temp.get("c6")));
        temp.get("d7").addPath(new Path("E2", Direction.EDGE, temp.get("d6")));
        temp.get("d7").addPath(new Path("D2", Direction.VERTEX, temp.get("e6")));
        temp.get("d7").addPath(new Path("E3", Direction.EDGE, temp.get("e7")));
        temp.get("d7").addPath(new Path("D3", Direction.VERTEX, temp.get("e8")));
        temp.get("d7").addPath(new Path("E4", Direction.EDGE, temp.get("d8")));
        temp.get("d7").addPath(new Path("D4", Direction.VERTEX, temp.get("c8")));
        temp.get("d7").linkPaths("E1", "D1");
        temp.get("d7").linkPaths("D1", "E2");
        temp.get("d7").linkPaths("E2", "D2");
        temp.get("d7").linkPaths("D2", "E3");
        temp.get("d7").linkPaths("E3", "D3");
        temp.get("d7").linkPaths("D3", "E4");
        temp.get("d7").linkPaths("E4", "D4");
        temp.get("d7").linkPaths("D4", "E1");
        temp.get("d8").addPath(new Path("E1", Direction.EDGE, temp.get("c8")));
        temp.get("d8").addPath(new Path("D1", Direction.VERTEX, temp.get("c7")));
        temp.get("d8").addPath(new Path("E2", Direction.EDGE, temp.get("d7")));
        temp.get("d8").addPath(new Path("D2", Direction.VERTEX, temp.get("e7")));
        temp.get("d8").addPath(new Path("E3", Direction.EDGE, temp.get("e8")));
        temp.get("d8").addPath(new Path("D3", Direction.VERTEX, null));
        temp.get("d8").addPath(new Path("E4", Direction.EDGE, null));
        temp.get("d8").addPath(new Path("D4", Direction.VERTEX, null));
        temp.get("d8").linkPaths("E1", "D1");
        temp.get("d8").linkPaths("D1", "E2");
        temp.get("d8").linkPaths("E2", "D2");
        temp.get("d8").linkPaths("D2", "E3");
        temp.get("d8").linkPaths("E3", "D3");
        temp.get("d8").linkPaths("D3", "E4");
        temp.get("d8").linkPaths("E4", "D4");
        temp.get("d8").linkPaths("D4", "E1");
        temp.get("e1").addPath(new Path("E1", Direction.EDGE, temp.get("d1")));
        temp.get("e1").addPath(new Path("D1", Direction.VERTEX, null));	//d0
        temp.get("e1").addPath(new Path("E2", Direction.EDGE, null));	//e0
        temp.get("e1").addPath(new Path("D2", Direction.VERTEX, null));	//f0
        temp.get("e1").addPath(new Path("E3", Direction.EDGE, temp.get("f1")));
        temp.get("e1").addPath(new Path("D3", Direction.VERTEX, temp.get("f2")));
        temp.get("e1").addPath(new Path("E4", Direction.EDGE, temp.get("e2")));
        temp.get("e1").addPath(new Path("D4", Direction.VERTEX, temp.get("d2")));
        temp.get("e1").linkPaths("E1", "D1");
        temp.get("e1").linkPaths("D1", "E2");
        temp.get("e1").linkPaths("E2", "D2");
        temp.get("e1").linkPaths("D2", "E3");
        temp.get("e1").linkPaths("E3", "D3");
        temp.get("e1").linkPaths("D3", "E4");
        temp.get("e1").linkPaths("E4", "D4");
        temp.get("e1").linkPaths("D4", "E1");
        temp.get("e2").addPath(new Path("E1", Direction.EDGE, temp.get("d2")));
        temp.get("e2").addPath(new Path("D1", Direction.VERTEX, temp.get("d1")));
        temp.get("e2").addPath(new Path("E2", Direction.EDGE, temp.get("e1")));
        temp.get("e2").addPath(new Path("D2", Direction.VERTEX, temp.get("f1")));
        temp.get("e2").addPath(new Path("E3", Direction.EDGE, temp.get("f2")));
        temp.get("e2").addPath(new Path("D3", Direction.VERTEX, temp.get("f3")));
        temp.get("e2").addPath(new Path("E4", Direction.EDGE, temp.get("e3")));
        temp.get("e2").addPath(new Path("D4", Direction.VERTEX, temp.get("d3")));
        temp.get("e2").linkPaths("E1", "D1");
        temp.get("e2").linkPaths("D1", "E2");
        temp.get("e2").linkPaths("E2", "D2");
        temp.get("e2").linkPaths("D2", "E3");
        temp.get("e2").linkPaths("E3", "D3");
        temp.get("e2").linkPaths("D3", "E4");
        temp.get("e2").linkPaths("E4", "D4");
        temp.get("e2").linkPaths("D4", "E1");
        temp.get("e3").addPath(new Path("E1", Direction.EDGE, temp.get("d3")));
        temp.get("e3").addPath(new Path("D1", Direction.VERTEX, temp.get("d2")));
        temp.get("e3").addPath(new Path("E2", Direction.EDGE, temp.get("e2")));
        temp.get("e3").addPath(new Path("D2", Direction.VERTEX, temp.get("f2")));
        temp.get("e3").addPath(new Path("E3", Direction.EDGE, temp.get("f3")));
        temp.get("e3").addPath(new Path("D3", Direction.VERTEX, temp.get("f4")));
        temp.get("e3").addPath(new Path("E4", Direction.EDGE, temp.get("e4")));
        temp.get("e3").addPath(new Path("D4", Direction.VERTEX, temp.get("d4")));
        temp.get("e3").linkPaths("E1", "D1");
        temp.get("e3").linkPaths("D1", "E2");
        temp.get("e3").linkPaths("E2", "D2");
        temp.get("e3").linkPaths("D2", "E3");
        temp.get("e3").linkPaths("E3", "D3");
        temp.get("e3").linkPaths("D3", "E4");
        temp.get("e3").linkPaths("E4", "D4");
        temp.get("e3").linkPaths("D4", "E1");
        temp.get("e4").addPath(new Path("E1", Direction.EDGE, temp.get("d4")));
        temp.get("e4").addPath(new Path("D1", Direction.VERTEX, temp.get("d3")));
        temp.get("e4").addPath(new Path("E2", Direction.EDGE, temp.get("e3")));
        temp.get("e4").addPath(new Path("D2", Direction.VERTEX, temp.get("f3")));
        temp.get("e4").addPath(new Path("E3", Direction.EDGE, temp.get("f4")));
        temp.get("e4").addPath(new Path("D3", Direction.VERTEX, temp.get("f5")));
        temp.get("e4").addPath(new Path("E4", Direction.EDGE, temp.get("e5")));
        temp.get("e4").addPath(new Path("D4", Direction.VERTEX, temp.get("d5")));
        temp.get("e4").linkPaths("E1", "D1");
        temp.get("e4").linkPaths("D1", "E2");
        temp.get("e4").linkPaths("E2", "D2");
        temp.get("e4").linkPaths("D2", "E3");
        temp.get("e4").linkPaths("E3", "D3");
        temp.get("e4").linkPaths("D3", "E4");
        temp.get("e4").linkPaths("E4", "D4");
        temp.get("e4").linkPaths("D4", "E1");
        temp.get("e5").addPath(new Path("E1", Direction.EDGE, temp.get("d5")));
        temp.get("e5").addPath(new Path("D1", Direction.VERTEX, temp.get("d4")));
        temp.get("e5").addPath(new Path("E2", Direction.EDGE, temp.get("e4")));
        temp.get("e5").addPath(new Path("D2", Direction.VERTEX, temp.get("f4")));
        temp.get("e5").addPath(new Path("E3", Direction.EDGE, temp.get("f5")));
        temp.get("e5").addPath(new Path("D3", Direction.VERTEX, temp.get("f6")));
        temp.get("e5").addPath(new Path("E4", Direction.EDGE, temp.get("e6")));
        temp.get("e5").addPath(new Path("D4", Direction.VERTEX, temp.get("d6")));
        temp.get("e5").linkPaths("E1", "D1");
        temp.get("e5").linkPaths("D1", "E2");
        temp.get("e5").linkPaths("E2", "D2");
        temp.get("e5").linkPaths("D2", "E3");
        temp.get("e5").linkPaths("E3", "D3");
        temp.get("e5").linkPaths("D3", "E4");
        temp.get("e5").linkPaths("E4", "D4");
        temp.get("e5").linkPaths("D4", "E1");
        temp.get("e6").addPath(new Path("E1", Direction.EDGE, temp.get("d6")));
        temp.get("e6").addPath(new Path("D1", Direction.VERTEX, temp.get("d5")));
        temp.get("e6").addPath(new Path("E2", Direction.EDGE, temp.get("e5")));
        temp.get("e6").addPath(new Path("D2", Direction.VERTEX, temp.get("f5")));
        temp.get("e6").addPath(new Path("E3", Direction.EDGE, temp.get("f6")));
        temp.get("e6").addPath(new Path("D3", Direction.VERTEX, temp.get("f7")));
        temp.get("e6").addPath(new Path("E4", Direction.EDGE, temp.get("e7")));
        temp.get("e6").addPath(new Path("D4", Direction.VERTEX, temp.get("d7")));
        temp.get("e6").linkPaths("E1", "D1");
        temp.get("e6").linkPaths("D1", "E2");
        temp.get("e6").linkPaths("E2", "D2");
        temp.get("e6").linkPaths("D2", "E3");
        temp.get("e6").linkPaths("E3", "D3");
        temp.get("e6").linkPaths("D3", "E4");
        temp.get("e6").linkPaths("E4", "D4");
        temp.get("e6").linkPaths("D4", "E1");
        temp.get("e7").addPath(new Path("E1", Direction.EDGE, temp.get("d7")));
        temp.get("e7").addPath(new Path("D1", Direction.VERTEX, temp.get("d6")));
        temp.get("e7").addPath(new Path("E2", Direction.EDGE, temp.get("e6")));
        temp.get("e7").addPath(new Path("D2", Direction.VERTEX, temp.get("f6")));
        temp.get("e7").addPath(new Path("E3", Direction.EDGE, temp.get("f7")));
        temp.get("e7").addPath(new Path("D3", Direction.VERTEX, temp.get("f8")));
        temp.get("e7").addPath(new Path("E4", Direction.EDGE, temp.get("e8")));
        temp.get("e7").addPath(new Path("D4", Direction.VERTEX, temp.get("d8")));
        temp.get("e7").linkPaths("E1", "D1");
        temp.get("e7").linkPaths("D1", "E2");
        temp.get("e7").linkPaths("E2", "D2");
        temp.get("e7").linkPaths("D2", "E3");
        temp.get("e7").linkPaths("E3", "D3");
        temp.get("e7").linkPaths("D3", "E4");
        temp.get("e7").linkPaths("E4", "D4");
        temp.get("e7").linkPaths("D4", "E1");
        temp.get("e8").addPath(new Path("E1", Direction.EDGE, temp.get("d8")));
        temp.get("e8").addPath(new Path("D1", Direction.VERTEX, temp.get("d7")));
        temp.get("e8").addPath(new Path("E2", Direction.EDGE, temp.get("e7")));
        temp.get("e8").addPath(new Path("D2", Direction.VERTEX, temp.get("f7")));
        temp.get("e8").addPath(new Path("E3", Direction.EDGE, temp.get("f8")));
        temp.get("e8").addPath(new Path("D3", Direction.VERTEX, null));
        temp.get("e8").addPath(new Path("E4", Direction.EDGE, null));
        temp.get("e8").addPath(new Path("D4", Direction.VERTEX, null));
        temp.get("e8").linkPaths("E1", "D1");
        temp.get("e8").linkPaths("D1", "E2");
        temp.get("e8").linkPaths("E2", "D2");
        temp.get("e8").linkPaths("D2", "E3");
        temp.get("e8").linkPaths("E3", "D3");
        temp.get("e8").linkPaths("D3", "E4");
        temp.get("e8").linkPaths("E4", "D4");
        temp.get("e8").linkPaths("D4", "E1");
        temp.get("f1").addPath(new Path("E1", Direction.EDGE, temp.get("e1")));
        temp.get("f1").addPath(new Path("D1", Direction.VERTEX, null));	//e0
        temp.get("f1").addPath(new Path("E2", Direction.EDGE, null));	//f0
        temp.get("f1").addPath(new Path("D2", Direction.VERTEX, null));	//g0
        temp.get("f1").addPath(new Path("E3", Direction.EDGE, temp.get("g1")));
        temp.get("f1").addPath(new Path("D3", Direction.VERTEX, temp.get("g2")));
        temp.get("f1").addPath(new Path("E4", Direction.EDGE, temp.get("f2")));
        temp.get("f1").addPath(new Path("D4", Direction.VERTEX, temp.get("e2")));
        temp.get("f1").linkPaths("E1", "D1");
        temp.get("f1").linkPaths("D1", "E2");
        temp.get("f1").linkPaths("E2", "D2");
        temp.get("f1").linkPaths("D2", "E3");
        temp.get("f1").linkPaths("E3", "D3");
        temp.get("f1").linkPaths("D3", "E4");
        temp.get("f1").linkPaths("E4", "D4");
        temp.get("f1").linkPaths("D4", "E1");
        temp.get("f2").addPath(new Path("E1", Direction.EDGE, temp.get("e2")));
        temp.get("f2").addPath(new Path("D1", Direction.VERTEX, temp.get("e1")));
        temp.get("f2").addPath(new Path("E2", Direction.EDGE, temp.get("f1")));
        temp.get("f2").addPath(new Path("D2", Direction.VERTEX, temp.get("g1")));
        temp.get("f2").addPath(new Path("E3", Direction.EDGE, temp.get("g2")));
        temp.get("f2").addPath(new Path("D3", Direction.VERTEX, temp.get("g3")));
        temp.get("f2").addPath(new Path("E4", Direction.EDGE, temp.get("f3")));
        temp.get("f2").addPath(new Path("D4", Direction.VERTEX, temp.get("e3")));
        temp.get("f2").linkPaths("E1", "D1");
        temp.get("f2").linkPaths("D1", "E2");
        temp.get("f2").linkPaths("E2", "D2");
        temp.get("f2").linkPaths("D2", "E3");
        temp.get("f2").linkPaths("E3", "D3");
        temp.get("f2").linkPaths("D3", "E4");
        temp.get("f2").linkPaths("E4", "D4");
        temp.get("f2").linkPaths("D4", "E1");
        temp.get("f3").addPath(new Path("E1", Direction.EDGE, temp.get("e3")));
        temp.get("f3").addPath(new Path("D1", Direction.VERTEX, temp.get("e2")));
        temp.get("f3").addPath(new Path("E2", Direction.EDGE, temp.get("f2")));
        temp.get("f3").addPath(new Path("D2", Direction.VERTEX, temp.get("g2")));
        temp.get("f3").addPath(new Path("E3", Direction.EDGE, temp.get("g3")));
        temp.get("f3").addPath(new Path("D3", Direction.VERTEX, temp.get("g4")));
        temp.get("f3").addPath(new Path("E4", Direction.EDGE, temp.get("f4")));
        temp.get("f3").addPath(new Path("D4", Direction.VERTEX, temp.get("e4")));
        temp.get("f3").linkPaths("E1", "D1");
        temp.get("f3").linkPaths("D1", "E2");
        temp.get("f3").linkPaths("E2", "D2");
        temp.get("f3").linkPaths("D2", "E3");
        temp.get("f3").linkPaths("E3", "D3");
        temp.get("f3").linkPaths("D3", "E4");
        temp.get("f3").linkPaths("E4", "D4");
        temp.get("f3").linkPaths("D4", "E1");
        temp.get("f4").addPath(new Path("E1", Direction.EDGE, temp.get("e4")));
        temp.get("f4").addPath(new Path("D1", Direction.VERTEX, temp.get("e3")));
        temp.get("f4").addPath(new Path("E2", Direction.EDGE, temp.get("f3")));
        temp.get("f4").addPath(new Path("D2", Direction.VERTEX, temp.get("g3")));
        temp.get("f4").addPath(new Path("E3", Direction.EDGE, temp.get("g4")));
        temp.get("f4").addPath(new Path("D3", Direction.VERTEX, temp.get("g5")));
        temp.get("f4").addPath(new Path("E4", Direction.EDGE, temp.get("f5")));
        temp.get("f4").addPath(new Path("D4", Direction.VERTEX, temp.get("e5")));
        temp.get("f4").linkPaths("E1", "D1");
        temp.get("f4").linkPaths("D1", "E2");
        temp.get("f4").linkPaths("E2", "D2");
        temp.get("f4").linkPaths("D2", "E3");
        temp.get("f4").linkPaths("E3", "D3");
        temp.get("f4").linkPaths("D3", "E4");
        temp.get("f4").linkPaths("E4", "D4");
        temp.get("f4").linkPaths("D4", "E1");
        temp.get("f5").addPath(new Path("E1", Direction.EDGE, temp.get("e5")));
        temp.get("f5").addPath(new Path("D1", Direction.VERTEX, temp.get("e4")));
        temp.get("f5").addPath(new Path("E2", Direction.EDGE, temp.get("f4")));
        temp.get("f5").addPath(new Path("D2", Direction.VERTEX, temp.get("g4")));
        temp.get("f5").addPath(new Path("E3", Direction.EDGE, temp.get("g5")));
        temp.get("f5").addPath(new Path("D3", Direction.VERTEX, temp.get("g6")));
        temp.get("f5").addPath(new Path("E4", Direction.EDGE, temp.get("f6")));
        temp.get("f5").addPath(new Path("D4", Direction.VERTEX, temp.get("e6")));
        temp.get("f5").linkPaths("E1", "D1");
        temp.get("f5").linkPaths("D1", "E2");
        temp.get("f5").linkPaths("E2", "D2");
        temp.get("f5").linkPaths("D2", "E3");
        temp.get("f5").linkPaths("E3", "D3");
        temp.get("f5").linkPaths("D3", "E4");
        temp.get("f5").linkPaths("E4", "D4");
        temp.get("f5").linkPaths("D4", "E1");
        temp.get("f6").addPath(new Path("E1", Direction.EDGE, temp.get("e6")));
        temp.get("f6").addPath(new Path("D1", Direction.VERTEX, temp.get("e5")));
        temp.get("f6").addPath(new Path("E2", Direction.EDGE, temp.get("f5")));
        temp.get("f6").addPath(new Path("D2", Direction.VERTEX, temp.get("g5")));
        temp.get("f6").addPath(new Path("E3", Direction.EDGE, temp.get("g6")));
        temp.get("f6").addPath(new Path("D3", Direction.VERTEX, temp.get("g7")));
        temp.get("f6").addPath(new Path("E4", Direction.EDGE, temp.get("f7")));
        temp.get("f6").addPath(new Path("D4", Direction.VERTEX, temp.get("e7")));
        temp.get("f6").linkPaths("E1", "D1");
        temp.get("f6").linkPaths("D1", "E2");
        temp.get("f6").linkPaths("E2", "D2");
        temp.get("f6").linkPaths("D2", "E3");
        temp.get("f6").linkPaths("E3", "D3");
        temp.get("f6").linkPaths("D3", "E4");
        temp.get("f6").linkPaths("E4", "D4");
        temp.get("f6").linkPaths("D4", "E1");
        temp.get("f7").addPath(new Path("E1", Direction.EDGE, temp.get("e7")));
        temp.get("f7").addPath(new Path("D1", Direction.VERTEX, temp.get("e6")));
        temp.get("f7").addPath(new Path("E2", Direction.EDGE, temp.get("f6")));
        temp.get("f7").addPath(new Path("D2", Direction.VERTEX, temp.get("g6")));
        temp.get("f7").addPath(new Path("E3", Direction.EDGE, temp.get("g7")));
        temp.get("f7").addPath(new Path("D3", Direction.VERTEX, temp.get("g8")));
        temp.get("f7").addPath(new Path("E4", Direction.EDGE, temp.get("f8")));
        temp.get("f7").addPath(new Path("D4", Direction.VERTEX, temp.get("e8")));
        temp.get("f7").linkPaths("E1", "D1");
        temp.get("f7").linkPaths("D1", "E2");
        temp.get("f7").linkPaths("E2", "D2");
        temp.get("f7").linkPaths("D2", "E3");
        temp.get("f7").linkPaths("E3", "D3");
        temp.get("f7").linkPaths("D3", "E4");
        temp.get("f7").linkPaths("E4", "D4");
        temp.get("f7").linkPaths("D4", "E1");
        temp.get("f8").addPath(new Path("E1", Direction.EDGE, temp.get("e8")));
        temp.get("f8").addPath(new Path("D1", Direction.VERTEX, temp.get("e7")));
        temp.get("f8").addPath(new Path("E2", Direction.EDGE, temp.get("f7")));
        temp.get("f8").addPath(new Path("D2", Direction.VERTEX, temp.get("g7")));
        temp.get("f8").addPath(new Path("E3", Direction.EDGE, temp.get("g8")));
        temp.get("f8").addPath(new Path("D3", Direction.VERTEX, null));
        temp.get("f8").addPath(new Path("E4", Direction.EDGE, null));
        temp.get("f8").addPath(new Path("D4", Direction.VERTEX, null));
        temp.get("f8").linkPaths("E1", "D1");
        temp.get("f8").linkPaths("D1", "E2");
        temp.get("f8").linkPaths("E2", "D2");
        temp.get("f8").linkPaths("D2", "E3");
        temp.get("f8").linkPaths("E3", "D3");
        temp.get("f8").linkPaths("D3", "E4");
        temp.get("f8").linkPaths("E4", "D4");
        temp.get("f8").linkPaths("D4", "E1");
        temp.get("g1").addPath(new Path("E1", Direction.EDGE, temp.get("f1")));
        temp.get("g1").addPath(new Path("D1", Direction.VERTEX, null));	//f0
        temp.get("g1").addPath(new Path("E2", Direction.EDGE, null));	//g0
        temp.get("g1").addPath(new Path("D2", Direction.VERTEX, null));	//h0
        temp.get("g1").addPath(new Path("E3", Direction.EDGE, temp.get("h1")));
        temp.get("g1").addPath(new Path("D3", Direction.VERTEX, temp.get("h2")));
        temp.get("g1").addPath(new Path("E4", Direction.EDGE, temp.get("g2")));
        temp.get("g1").addPath(new Path("D4", Direction.VERTEX, temp.get("f2")));
        temp.get("g1").linkPaths("E1", "D1");
        temp.get("g1").linkPaths("D1", "E2");
        temp.get("g1").linkPaths("E2", "D2");
        temp.get("g1").linkPaths("D2", "E3");
        temp.get("g1").linkPaths("E3", "D3");
        temp.get("g1").linkPaths("D3", "E4");
        temp.get("g1").linkPaths("E4", "D4");
        temp.get("g1").linkPaths("D4", "E1");
        temp.get("g2").addPath(new Path("E1", Direction.EDGE, temp.get("f2")));
        temp.get("g2").addPath(new Path("D1", Direction.VERTEX, temp.get("f1")));
        temp.get("g2").addPath(new Path("E2", Direction.EDGE, temp.get("g1")));
        temp.get("g2").addPath(new Path("D2", Direction.VERTEX, temp.get("h1")));
        temp.get("g2").addPath(new Path("E3", Direction.EDGE, temp.get("h2")));
        temp.get("g2").addPath(new Path("D3", Direction.VERTEX, temp.get("h3")));
        temp.get("g2").addPath(new Path("E4", Direction.EDGE, temp.get("g3")));
        temp.get("g2").addPath(new Path("D4", Direction.VERTEX, temp.get("f3")));
        temp.get("g2").linkPaths("E1", "D1");
        temp.get("g2").linkPaths("D1", "E2");
        temp.get("g2").linkPaths("E2", "D2");
        temp.get("g2").linkPaths("D2", "E3");
        temp.get("g2").linkPaths("E3", "D3");
        temp.get("g2").linkPaths("D3", "E4");
        temp.get("g2").linkPaths("E4", "D4");
        temp.get("g2").linkPaths("D4", "E1");
        temp.get("g3").addPath(new Path("E1", Direction.EDGE, temp.get("f3")));
        temp.get("g3").addPath(new Path("D1", Direction.VERTEX, temp.get("f2")));
        temp.get("g3").addPath(new Path("E2", Direction.EDGE, temp.get("g2")));
        temp.get("g3").addPath(new Path("D2", Direction.VERTEX, temp.get("h2")));
        temp.get("g3").addPath(new Path("E3", Direction.EDGE, temp.get("h3")));
        temp.get("g3").addPath(new Path("D3", Direction.VERTEX, temp.get("h4")));
        temp.get("g3").addPath(new Path("E4", Direction.EDGE, temp.get("g4")));
        temp.get("g3").addPath(new Path("D4", Direction.VERTEX, temp.get("f4")));
        temp.get("g3").linkPaths("E1", "D1");
        temp.get("g3").linkPaths("D1", "E2");
        temp.get("g3").linkPaths("E2", "D2");
        temp.get("g3").linkPaths("D2", "E3");
        temp.get("g3").linkPaths("E3", "D3");
        temp.get("g3").linkPaths("D3", "E4");
        temp.get("g3").linkPaths("E4", "D4");
        temp.get("g3").linkPaths("D4", "E1");
        temp.get("g4").addPath(new Path("E1", Direction.EDGE, temp.get("f4")));
        temp.get("g4").addPath(new Path("D1", Direction.VERTEX, temp.get("f3")));
        temp.get("g4").addPath(new Path("E2", Direction.EDGE, temp.get("g3")));
        temp.get("g4").addPath(new Path("D2", Direction.VERTEX, temp.get("h3")));
        temp.get("g4").addPath(new Path("E3", Direction.EDGE, temp.get("h4")));
        temp.get("g4").addPath(new Path("D3", Direction.VERTEX, temp.get("h5")));
        temp.get("g4").addPath(new Path("E4", Direction.EDGE, temp.get("g5")));
        temp.get("g4").addPath(new Path("D4", Direction.VERTEX, temp.get("f5")));
        temp.get("g4").linkPaths("E1", "D1");
        temp.get("g4").linkPaths("D1", "E2");
        temp.get("g4").linkPaths("E2", "D2");
        temp.get("g4").linkPaths("D2", "E3");
        temp.get("g4").linkPaths("E3", "D3");
        temp.get("g4").linkPaths("D3", "E4");
        temp.get("g4").linkPaths("E4", "D4");
        temp.get("g4").linkPaths("D4", "E1");
        temp.get("g5").addPath(new Path("E1", Direction.EDGE, temp.get("f5")));
        temp.get("g5").addPath(new Path("D1", Direction.VERTEX, temp.get("f4")));
        temp.get("g5").addPath(new Path("E2", Direction.EDGE, temp.get("g4")));
        temp.get("g5").addPath(new Path("D2", Direction.VERTEX, temp.get("h4")));
        temp.get("g5").addPath(new Path("E3", Direction.EDGE, temp.get("h5")));
        temp.get("g5").addPath(new Path("D3", Direction.VERTEX, temp.get("h6")));
        temp.get("g5").addPath(new Path("E4", Direction.EDGE, temp.get("g6")));
        temp.get("g5").addPath(new Path("D4", Direction.VERTEX, temp.get("f6")));
        temp.get("g5").linkPaths("E1", "D1");
        temp.get("g5").linkPaths("D1", "E2");
        temp.get("g5").linkPaths("E2", "D2");
        temp.get("g5").linkPaths("D2", "E3");
        temp.get("g5").linkPaths("E3", "D3");
        temp.get("g5").linkPaths("D3", "E4");
        temp.get("g5").linkPaths("E4", "D4");
        temp.get("g5").linkPaths("D4", "E1");
        temp.get("g6").addPath(new Path("E1", Direction.EDGE, temp.get("f6")));
        temp.get("g6").addPath(new Path("D1", Direction.VERTEX, temp.get("f5")));
        temp.get("g6").addPath(new Path("E2", Direction.EDGE, temp.get("g5")));
        temp.get("g6").addPath(new Path("D2", Direction.VERTEX, temp.get("h5")));
        temp.get("g6").addPath(new Path("E3", Direction.EDGE, temp.get("h6")));
        temp.get("g6").addPath(new Path("D3", Direction.VERTEX, temp.get("h7")));
        temp.get("g6").addPath(new Path("E4", Direction.EDGE, temp.get("g7")));
        temp.get("g6").addPath(new Path("D4", Direction.VERTEX, temp.get("f7")));
        temp.get("g6").linkPaths("E1", "D1");
        temp.get("g6").linkPaths("D1", "E2");
        temp.get("g6").linkPaths("E2", "D2");
        temp.get("g6").linkPaths("D2", "E3");
        temp.get("g6").linkPaths("E3", "D3");
        temp.get("g6").linkPaths("D3", "E4");
        temp.get("g6").linkPaths("E4", "D4");
        temp.get("g6").linkPaths("D4", "E1");
        temp.get("g7").addPath(new Path("E1", Direction.EDGE, temp.get("f7")));
        temp.get("g7").addPath(new Path("D1", Direction.VERTEX, temp.get("f6")));
        temp.get("g7").addPath(new Path("E2", Direction.EDGE, temp.get("g6")));
        temp.get("g7").addPath(new Path("D2", Direction.VERTEX, temp.get("h6")));
        temp.get("g7").addPath(new Path("E3", Direction.EDGE, temp.get("h7")));
        temp.get("g7").addPath(new Path("D3", Direction.VERTEX, temp.get("h8")));
        temp.get("g7").addPath(new Path("E4", Direction.EDGE, temp.get("g8")));
        temp.get("g7").addPath(new Path("D4", Direction.VERTEX, temp.get("f8")));
        temp.get("g7").linkPaths("E1", "D1");
        temp.get("g7").linkPaths("D1", "E2");
        temp.get("g7").linkPaths("E2", "D2");
        temp.get("g7").linkPaths("D2", "E3");
        temp.get("g7").linkPaths("E3", "D3");
        temp.get("g7").linkPaths("D3", "E4");
        temp.get("g7").linkPaths("E4", "D4");
        temp.get("g7").linkPaths("D4", "E1");
        temp.get("g8").addPath(new Path("E1", Direction.EDGE, temp.get("f8")));
        temp.get("g8").addPath(new Path("D1", Direction.VERTEX, temp.get("f7")));
        temp.get("g8").addPath(new Path("E2", Direction.EDGE, temp.get("g7")));
        temp.get("g8").addPath(new Path("D2", Direction.VERTEX, temp.get("h7")));
        temp.get("g8").addPath(new Path("E3", Direction.EDGE, temp.get("h8")));
        temp.get("g8").addPath(new Path("D3", Direction.VERTEX, temp.get("h13")));
        temp.get("g8").addPath(new Path("E4", Direction.EDGE, temp.get("g13")));
        temp.get("g8").addPath(new Path("D4", Direction.VERTEX, temp.get("f13")));
        temp.get("g8").linkPaths("E1", "D1");
        temp.get("g8").linkPaths("D1", "E2");
        temp.get("g8").linkPaths("E2", "D2");
        temp.get("g8").linkPaths("D2", "E3");
        temp.get("g8").linkPaths("E3", "D3");
        temp.get("g8").linkPaths("D3", "E4");
        temp.get("g8").linkPaths("E4", "D4");
        temp.get("g8").linkPaths("D4", "E1");
        temp.get("h1").addPath(new Path("E1", Direction.EDGE, temp.get("g1")));
        temp.get("h1").addPath(new Path("D1", Direction.VERTEX, null));	//g0
        temp.get("h1").addPath(new Path("E2", Direction.EDGE, null));	//h0
        temp.get("h1").addPath(new Path("D2", Direction.VERTEX, null));	//i0
        temp.get("h1").addPath(new Path("E3", Direction.EDGE, null));   // i1
        temp.get("h1").addPath(new Path("D3", Direction.VERTEX, null)); // i2
        temp.get("h1").addPath(new Path("E4", Direction.EDGE, temp.get("h2")));
        temp.get("h1").addPath(new Path("D4", Direction.VERTEX, temp.get("g2")));
        temp.get("h1").linkPaths("E1", "D1");
        temp.get("h1").linkPaths("D1", "E2");
        temp.get("h1").linkPaths("E2", "D2");
        temp.get("h1").linkPaths("D2", "E3");
        temp.get("h1").linkPaths("E3", "D3");
        temp.get("h1").linkPaths("D3", "E4");
        temp.get("h1").linkPaths("E4", "D4");
        temp.get("h1").linkPaths("D4", "E1");
        temp.get("h2").addPath(new Path("E1", Direction.EDGE, temp.get("g2")));
        temp.get("h2").addPath(new Path("D1", Direction.VERTEX, temp.get("g1")));
        temp.get("h2").addPath(new Path("E2", Direction.EDGE, temp.get("h1")));
        temp.get("h2").addPath(new Path("D2", Direction.VERTEX, null));	//i1
        temp.get("h2").addPath(new Path("E3", Direction.EDGE, null));   // i2
        temp.get("h2").addPath(new Path("D3", Direction.VERTEX, null)); // i3
        temp.get("h2").addPath(new Path("E4", Direction.EDGE, temp.get("h3")));
        temp.get("h2").addPath(new Path("D4", Direction.VERTEX, temp.get("g3")));
        temp.get("h2").linkPaths("E1", "D1");
        temp.get("h2").linkPaths("D1", "E2");
        temp.get("h2").linkPaths("E2", "D2");
        temp.get("h2").linkPaths("D2", "E3");
        temp.get("h2").linkPaths("E3", "D3");
        temp.get("h2").linkPaths("D3", "E4");
        temp.get("h2").linkPaths("E4", "D4");
        temp.get("h2").linkPaths("D4", "E1");
        temp.get("h3").addPath(new Path("E1", Direction.EDGE, temp.get("g3")));
        temp.get("h3").addPath(new Path("D1", Direction.VERTEX, temp.get("g2")));
        temp.get("h3").addPath(new Path("E2", Direction.EDGE, temp.get("h2")));
        temp.get("h3").addPath(new Path("D2", Direction.VERTEX, null));	//i1
        temp.get("h3").addPath(new Path("E3", Direction.EDGE, null));   // i2
        temp.get("h3").addPath(new Path("D3", Direction.VERTEX, null)); // i3
        temp.get("h3").addPath(new Path("E4", Direction.EDGE, temp.get("h4")));
        temp.get("h3").addPath(new Path("D4", Direction.VERTEX, temp.get("g4")));
        temp.get("h3").linkPaths("E1", "D1");
        temp.get("h3").linkPaths("D1", "E2");
        temp.get("h3").linkPaths("E2", "D2");
        temp.get("h3").linkPaths("D2", "E3");
        temp.get("h3").linkPaths("E3", "D3");
        temp.get("h3").linkPaths("D3", "E4");
        temp.get("h3").linkPaths("E4", "D4");
        temp.get("h3").linkPaths("D4", "E1");
        temp.get("h4").addPath(new Path("E1", Direction.EDGE, temp.get("g4")));
        temp.get("h4").addPath(new Path("D1", Direction.VERTEX, temp.get("g3")));
        temp.get("h4").addPath(new Path("E2", Direction.EDGE, temp.get("h3")));
        temp.get("h4").addPath(new Path("D2", Direction.VERTEX, null));	//i1
        temp.get("h4").addPath(new Path("E3", Direction.EDGE, null));   // i2
        temp.get("h4").addPath(new Path("D3", Direction.VERTEX, null)); // i3
        temp.get("h4").addPath(new Path("E4", Direction.EDGE, temp.get("h5")));
        temp.get("h4").addPath(new Path("D4", Direction.VERTEX, temp.get("g5")));
        temp.get("h4").linkPaths("E1", "D1");
        temp.get("h4").linkPaths("D1", "E2");
        temp.get("h4").linkPaths("E2", "D2");
        temp.get("h4").linkPaths("D2", "E3");
        temp.get("h4").linkPaths("E3", "D3");
        temp.get("h4").linkPaths("D3", "E4");
        temp.get("h4").linkPaths("E4", "D4");
        temp.get("h4").linkPaths("D4", "E1");
        temp.get("h5").addPath(new Path("E1", Direction.EDGE, temp.get("g5")));
        temp.get("h5").addPath(new Path("D1", Direction.VERTEX, temp.get("g4")));
        temp.get("h5").addPath(new Path("E2", Direction.EDGE, temp.get("h4")));
        temp.get("h5").addPath(new Path("D2", Direction.VERTEX, null));	//i1
        temp.get("h5").addPath(new Path("E3", Direction.EDGE, null));   // i2
        temp.get("h5").addPath(new Path("D3", Direction.VERTEX, null)); // i3
        temp.get("h5").addPath(new Path("E4", Direction.EDGE, temp.get("h6")));
        temp.get("h5").addPath(new Path("D4", Direction.VERTEX, temp.get("g6")));
        temp.get("h5").linkPaths("E1", "D1");
        temp.get("h5").linkPaths("D1", "E2");
        temp.get("h5").linkPaths("E2", "D2");
        temp.get("h5").linkPaths("D2", "E3");
        temp.get("h5").linkPaths("E3", "D3");
        temp.get("h5").linkPaths("D3", "E4");
        temp.get("h5").linkPaths("E4", "D4");
        temp.get("h5").linkPaths("D4", "E1");
        temp.get("h6").addPath(new Path("E1", Direction.EDGE, temp.get("g6")));
        temp.get("h6").addPath(new Path("D1", Direction.VERTEX, temp.get("g5")));
        temp.get("h6").addPath(new Path("E2", Direction.EDGE, temp.get("h5")));
        temp.get("h6").addPath(new Path("D2", Direction.VERTEX, null));	//i1
        temp.get("h6").addPath(new Path("E3", Direction.EDGE, null));   // i2
        temp.get("h6").addPath(new Path("D3", Direction.VERTEX, null)); // i3
        temp.get("h6").addPath(new Path("E4", Direction.EDGE, temp.get("h7")));
        temp.get("h6").addPath(new Path("D4", Direction.VERTEX, temp.get("g7")));
        temp.get("h6").linkPaths("E1", "D1");
        temp.get("h6").linkPaths("D1", "E2");
        temp.get("h6").linkPaths("E2", "D2");
        temp.get("h6").linkPaths("D2", "E3");
        temp.get("h6").linkPaths("E3", "D3");
        temp.get("h6").linkPaths("D3", "E4");
        temp.get("h6").linkPaths("E4", "D4");
        temp.get("h6").linkPaths("D4", "E1");
        temp.get("h7").addPath(new Path("E1", Direction.EDGE, temp.get("g7")));
        temp.get("h7").addPath(new Path("D1", Direction.VERTEX, temp.get("g6")));
        temp.get("h7").addPath(new Path("E2", Direction.EDGE, temp.get("h6")));
        temp.get("h7").addPath(new Path("D2", Direction.VERTEX, null));	//i1
        temp.get("h7").addPath(new Path("E3", Direction.EDGE, null));   // i2
        temp.get("h7").addPath(new Path("D3", Direction.VERTEX, null)); // i3
        temp.get("h7").addPath(new Path("E4", Direction.EDGE, temp.get("h8")));
        temp.get("h7").addPath(new Path("D4", Direction.VERTEX, temp.get("g8")));
        temp.get("h7").linkPaths("E1", "D1");
        temp.get("h7").linkPaths("D1", "E2");
        temp.get("h7").linkPaths("E2", "D2");
        temp.get("h7").linkPaths("D2", "E3");
        temp.get("h7").linkPaths("E3", "D3");
        temp.get("h7").linkPaths("D3", "E4");
        temp.get("h7").linkPaths("E4", "D4");
        temp.get("h7").linkPaths("D4", "E1");
        temp.get("h8").addPath(new Path("E1", Direction.EDGE, temp.get("g8")));
        temp.get("h8").addPath(new Path("D1", Direction.VERTEX, temp.get("g7")));
        temp.get("h8").addPath(new Path("E2", Direction.EDGE, temp.get("h7")));
        temp.get("h8").addPath(new Path("D2", Direction.VERTEX, null));	//i1
        temp.get("h8").addPath(new Path("E3", Direction.EDGE, null));   // i2
        temp.get("h8").addPath(new Path("D3", Direction.VERTEX, null)); // i3
        temp.get("h8").addPath(new Path("E4", Direction.EDGE, null));
        temp.get("h8").addPath(new Path("D4", Direction.VERTEX, null));
        temp.get("h8").linkPaths("E1", "D1");
        temp.get("h8").linkPaths("D1", "E2");
        temp.get("h8").linkPaths("E2", "D2");
        temp.get("h8").linkPaths("D2", "E3");
        temp.get("h8").linkPaths("E3", "D3");
        temp.get("h8").linkPaths("D3", "E4");
        temp.get("h8").linkPaths("E4", "D4");
        temp.get("h8").linkPaths("D4", "E1");

	}
	public void populatePositions() {
		

    	IPositionData oPosition = new PositionData('a',1, new Quadrilateral(217, 674, 264, 674, 245, 624, 193, 633), "Black");
    	temp.put(oPosition.getName(), oPosition);
    	oPosition = new PositionData('a',2, new Quadrilateral(193, 633, 245, 624, 228, 572, 171, 593), "White");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('a',3, new Quadrilateral(171, 593, 228, 572, 210, 522, 148, 552), "Black");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('a',4, new Quadrilateral(148, 552, 210, 522, 193, 472, 124, 512), "White");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('a',5, new Quadrilateral(124, 512, 193, 472, 159, 431, 101, 471), "Black");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('a',6, new Quadrilateral(101, 471, 159, 431, 123, 391, 77, 431), "White");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('a',7, new Quadrilateral(77, 431, 123, 391, 88, 351, 54, 389), "Black");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('a',8, new Quadrilateral(54, 389, 88, 351, 54, 310, 31, 349), "White");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('b',1, new Quadrilateral(264, 674, 310, 674, 298, 613, 246, 624), "White");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('b',2, new Quadrilateral(246, 624, 298, 613, 287, 553, 228, 572), "Black");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('b',3, new Quadrilateral(228, 572, 287, 553, 275, 492, 211, 522), "White");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('b',4, new Quadrilateral(211, 522, 275, 492, 263, 432, 194, 471), "Black");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('b',5, new Quadrilateral(194, 471, 263, 432, 219, 390, 159, 431), "White");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('b',6, new Quadrilateral(159, 431, 219, 390, 171, 350, 124, 390), "Black");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('b',7, new Quadrilateral(124, 390, 171, 350, 123, 310, 88, 350), "White");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('b',8, new Quadrilateral(88, 350, 123, 310, 77, 270, 54, 310), "Black");
    	temp.put(oPosition.getName(), oPosition);
                                         
        oPosition = new PositionData('c',1, new Quadrilateral(310, 674, 356, 674, 351, 603, 299, 613), "Black");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('c',2, new Quadrilateral(299, 613, 351, 603, 344, 533, 287, 552), "White");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('c',3, new Quadrilateral(287, 552, 344, 533, 340, 462, 276, 492), "Black");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('c',4, new Quadrilateral(276, 492, 340, 462, 334, 391, 264, 431), "White");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('c',5, new Quadrilateral(264, 431, 334, 391, 275, 351, 218, 390), "Black");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('c',6, new Quadrilateral(218, 390, 275, 351, 217, 310, 170, 349), "White");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('c',7, new Quadrilateral(170, 349, 217, 310, 159, 270, 124, 309), "Black");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('c',8, new Quadrilateral(124, 309, 159, 270, 100, 230, 78, 269), "White");
    	temp.put(oPosition.getName(), oPosition);
                                         
        oPosition = new PositionData('d',1, new Quadrilateral(357, 674, 404, 674, 404, 593, 352, 603), "White");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('d',2, new Quadrilateral(352, 603, 404, 593, 404, 512, 345, 532), "Black");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('d',3, new Quadrilateral(345, 532, 404, 512, 404, 432, 340, 461), "White");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('d',4, new Quadrilateral(340, 461, 404, 432, 404, 350, 333, 390), "Black");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('d',5, new Quadrilateral(333, 390, 404, 350, 334, 311, 276, 350), "White");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('d',6, new Quadrilateral(276, 350, 334, 311, 263, 270, 218, 309), "Black");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('d',7, new Quadrilateral(218, 309, 263, 270, 194, 224, 159, 269), "White");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('d',8, new Quadrilateral(159, 269, 194, 224, 124, 188, 100, 228), "Black");
    	temp.put(oPosition.getName(), oPosition);
                                         
        oPosition = new PositionData('e',1, new Quadrilateral(404, 674, 450, 674, 456, 603, 404, 593), "Black");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('e',2, new Quadrilateral(404, 593, 456, 603, 462, 532, 404, 512), "White");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('e',3, new Quadrilateral(404, 512, 462, 532, 468, 461, 404, 431), "Black");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('e',4, new Quadrilateral(404, 431, 468, 461, 474, 391, 404, 350), "White");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('e',9, new Quadrilateral(404, 350, 474, 391, 531, 350, 474, 310), "Black");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('e',10, new Quadrilateral(474, 310, 531, 350, 590, 309, 544, 270), "White");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('e',11, new Quadrilateral(544, 270, 590, 309, 649, 269, 615, 228), "Black");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('e',12, new Quadrilateral(651, 269, 707, 228, 684, 188, 614, 228), "White");
    	temp.put(oPosition.getName(), oPosition);
                                         
        oPosition = new PositionData('f',1, new Quadrilateral(451, 674, 497, 674, 509, 613, 457, 603), "White");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('f',2, new Quadrilateral(457, 603, 509, 613, 521, 553, 464, 533), "Black");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('f',3, new Quadrilateral(464, 533, 521, 553, 532, 492, 468, 461), "White");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('f',4, new Quadrilateral(468, 461, 532, 492, 544, 431, 475, 391), "Black");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('f',9, new Quadrilateral(475, 391, 544, 431, 591, 390, 532, 350), "White");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('f',10, new Quadrilateral(532, 350, 591, 390, 637, 350, 591, 310), "Black");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('f',11, new Quadrilateral(591, 310, 637, 350, 684, 309, 650, 269), "White");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('f',12, new Quadrilateral(685, 309, 730, 270, 707, 228, 651, 269), "Black");
    	temp.put(oPosition.getName(), oPosition);
                                         
        oPosition = new PositionData('g',1, new Quadrilateral(497, 674, 544, 674, 561, 623, 509, 614), "Black");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('g',2, new Quadrilateral(509, 614, 561, 623, 579, 573, 521, 552), "White");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('g',3, new Quadrilateral(521, 552, 579, 573, 596, 522, 533, 492), "Black");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('g',4, new Quadrilateral(533, 492, 596, 522, 614, 471, 545, 431), "White");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('g',9, new Quadrilateral(545, 431, 614, 471, 649, 431, 592, 391), "Black");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('g',10, new Quadrilateral(592, 391, 649, 431, 685, 391, 638, 350), "White");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('g',11, new Quadrilateral(638, 350, 685, 391, 720, 350, 684, 310), "Black");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('g',12, new Quadrilateral(720, 350, 755, 310, 730, 270, 685, 309), "White");
    	temp.put(oPosition.getName(), oPosition);
                                         
        oPosition = new PositionData('h',1, new Quadrilateral(544, 674, 591, 674, 613, 633, 561, 623), "White");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('h',2, new Quadrilateral(561, 623, 613, 633, 637, 592, 580, 573), "Black");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('h',3, new Quadrilateral(580, 573, 637, 592, 660, 552, 597, 522), "White");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('h',4, new Quadrilateral(597, 522, 660, 552, 683, 511, 614, 472), "Black");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('h',9, new Quadrilateral(614, 472, 683, 511, 707, 471, 650, 431), "White");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('h',10, new Quadrilateral(650, 431, 707, 471, 730, 430, 684, 391), "Black");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('h',11, new Quadrilateral(684, 391, 730, 430, 754, 390, 719, 350), "White");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('h',12, new Quadrilateral(719, 350, 754, 390, 778, 349, 755, 310), "Black");
    	temp.put(oPosition.getName(), oPosition);
                                         
        oPosition = new PositionData('i',8, new Quadrilateral(124, 188, 194, 229, 212, 177, 147, 148), "White");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('i',7, new Quadrilateral(194, 229, 263, 268, 275, 209, 212, 177), "Black");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('i',6, new Quadrilateral(263, 268, 334, 309, 339, 239, 275, 209), "White");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('i',5, new Quadrilateral(334, 309, 403, 349, 403, 269, 339, 239), "Black");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('i',9, new Quadrilateral(403, 349, 474, 310, 467, 239, 403, 269), "White");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('i',10, new Quadrilateral(474, 310, 544, 268, 533, 209, 467, 239), "Black");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('i',11, new Quadrilateral(544, 268, 614, 228, 597, 178, 533, 209), "White");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('i',12, new Quadrilateral(614, 228, 684, 188, 660, 148, 597, 178), "Black");
    	temp.put(oPosition.getName(), oPosition);
                                         
        oPosition = new PositionData('j',8, new Quadrilateral(147, 148, 212, 177, 228, 126, 171, 107), "Black");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('j',7, new Quadrilateral(212, 177, 276, 207, 287, 148, 228, 126), "White");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('j',6, new Quadrilateral(275, 208, 339, 239, 345, 168, 287, 148), "Black");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('j',5, new Quadrilateral(339, 239, 403, 269, 403, 188, 345, 168), "White");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('j',9, new Quadrilateral(403, 268, 467, 239, 462, 169, 403, 188), "Black");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('j',10, new Quadrilateral(467, 239, 533, 209, 520, 147, 462, 169), "White");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('j',11, new Quadrilateral(533, 209, 597, 178, 578, 128, 520, 147), "Black");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('j',12, new Quadrilateral(597, 178, 660, 147, 638, 108, 578, 128), "White");
    	temp.put(oPosition.getName(), oPosition);
                                         
        oPosition = new PositionData('k',8, new Quadrilateral(171, 107, 228, 126, 245, 76, 194, 66), "White");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('k',7, new Quadrilateral(228, 126, 287, 148, 299, 85, 245, 76), "Black");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('k',6, new Quadrilateral(287, 148, 345, 168, 351, 96, 299, 86), "White");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('k',5, new Quadrilateral(345, 168, 403, 188, 404, 107, 351, 96), "Black");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('k',9, new Quadrilateral(403, 188, 462, 169, 456, 98, 404, 107), "White");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('k',10, new Quadrilateral(462, 169, 520, 147, 508, 87, 456, 98), "Black");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('k',11, new Quadrilateral(520, 147, 578, 128, 561, 77, 508, 87), "White");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('k',12, new Quadrilateral(578, 128, 637, 107, 614, 66, 561, 77), "Black");
    	temp.put(oPosition.getName(), oPosition);
                                         
        oPosition = new PositionData('l',8, new Quadrilateral(194, 66, 245, 76, 264, 26, 217, 26), "Black");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('l',7, new Quadrilateral(245, 76, 299, 86, 310, 26, 264, 26), "White");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('l',6, new Quadrilateral(299, 86, 351, 96, 357, 26, 310, 26), "Black");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('l',5, new Quadrilateral(351, 96, 404, 107, 403, 26, 357, 26), "White");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('l',9, new Quadrilateral(404, 107, 456, 98, 451, 26, 403, 26), "Black");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('l',10, new Quadrilateral(456, 98, 508, 87, 498, 26, 451, 26), "White");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('l',11, new Quadrilateral(508, 87, 561, 77, 545, 27, 498, 26), "Black");
    	temp.put(oPosition.getName(), oPosition);
        oPosition = new PositionData('l',12, new Quadrilateral(561, 77, 614, 66, 591, 26, 545, 27), "White");
    	temp.put(oPosition.getName(), oPosition);
        
        temp.get("a1").addPath(new Path("E1", Direction.EDGE, null));	//z1
        temp.get("a1").addPath(new Path("D1", Direction.VERTEX, null));	//z0
        temp.get("a1").addPath(new Path("E2", Direction.EDGE, null));	//a0
        temp.get("a1").addPath(new Path("D2", Direction.VERTEX, null));	//b0
        temp.get("a1").addPath(new Path("E3", Direction.EDGE, temp.get("b1")));
        temp.get("a1").addPath(new Path("D3", Direction.VERTEX, temp.get("b2")));
        temp.get("a1").addPath(new Path("E4", Direction.EDGE, temp.get("a2")));
        temp.get("a1").addPath(new Path("D4", Direction.VERTEX, null));	//z2        
        temp.get("a1").linkPaths("E1", "D1");
        temp.get("a1").linkPaths("D1", "E2");
        temp.get("a1").linkPaths("E2", "D2");
        temp.get("a1").linkPaths("D2", "E3");
        temp.get("a1").linkPaths("E3", "D3");
        temp.get("a1").linkPaths("D3", "E4");
        temp.get("a1").linkPaths("E4", "D4");
        temp.get("a1").linkPaths("D4", "E1");
        temp.get("a2").addPath(new Path("E1", Direction.EDGE, null));	//z2
        temp.get("a2").addPath(new Path("D1", Direction.VERTEX, null));	//z1
        temp.get("a2").addPath(new Path("E2", Direction.EDGE, temp.get("a1")));	//a1
        temp.get("a2").addPath(new Path("D2", Direction.VERTEX, temp.get("b1")));	//b1
        temp.get("a2").addPath(new Path("E3", Direction.EDGE, temp.get("b2")));
        temp.get("a2").addPath(new Path("D3", Direction.VERTEX, temp.get("b3")));
        temp.get("a2").addPath(new Path("E4", Direction.EDGE, temp.get("a3")));
        temp.get("a2").addPath(new Path("D4", Direction.VERTEX, null));	//z3        
        temp.get("a2").linkPaths("E1", "D1");
        temp.get("a2").linkPaths("D1", "E2");
        temp.get("a2").linkPaths("E2", "D2");
        temp.get("a2").linkPaths("D2", "E3");
        temp.get("a2").linkPaths("E3", "D3");
        temp.get("a2").linkPaths("D3", "E4");
        temp.get("a2").linkPaths("E4", "D4");
        temp.get("a2").linkPaths("D4", "E1");
        temp.get("a3").addPath(new Path("E1", Direction.EDGE, null));	//z3
        temp.get("a3").addPath(new Path("D1", Direction.VERTEX, null));	//z2
        temp.get("a3").addPath(new Path("E2", Direction.EDGE, temp.get("a2")));	//a2
        temp.get("a3").addPath(new Path("D2", Direction.VERTEX, temp.get("b2")));	//b2
        temp.get("a3").addPath(new Path("E3", Direction.EDGE, temp.get("b3")));
        temp.get("a3").addPath(new Path("D3", Direction.VERTEX, temp.get("b4")));
        temp.get("a3").addPath(new Path("E4", Direction.EDGE, temp.get("a4")));
        temp.get("a3").addPath(new Path("D4", Direction.VERTEX, null));	//z4        
        temp.get("a3").linkPaths("E1", "D1");
        temp.get("a3").linkPaths("D1", "E2");
        temp.get("a3").linkPaths("E2", "D2");
        temp.get("a3").linkPaths("D2", "E3");
        temp.get("a3").linkPaths("E3", "D3");
        temp.get("a3").linkPaths("D3", "E4");
        temp.get("a3").linkPaths("E4", "D4");
        temp.get("a3").linkPaths("D4", "E1");
        
        temp.get("a4").addPath(new Path("E1", Direction.EDGE, null));	//z4
        temp.get("a4").addPath(new Path("D1", Direction.VERTEX, null));	//z3
        temp.get("a4").addPath(new Path("E2", Direction.EDGE, temp.get("a3")));	//a3
        temp.get("a4").addPath(new Path("D2", Direction.VERTEX, temp.get("b3")));	//b3
        temp.get("a4").addPath(new Path("E3", Direction.EDGE, temp.get("b4")));
        temp.get("a4").addPath(new Path("D3", Direction.VERTEX, temp.get("b5")));
        temp.get("a4").addPath(new Path("E4", Direction.EDGE, temp.get("a5")));
        temp.get("a4").addPath(new Path("D4", Direction.VERTEX, null));	//z5        
        temp.get("a4").linkPaths("E1", "D1");
        temp.get("a4").linkPaths("D1", "E2");
        temp.get("a4").linkPaths("E2", "D2");
        temp.get("a4").linkPaths("D2", "E3");
        temp.get("a4").linkPaths("E3", "D3");
        temp.get("a4").linkPaths("D3", "E4");
        temp.get("a4").linkPaths("E4", "D4");
        temp.get("a4").linkPaths("D4", "E1");
        
        temp.get("a5").addPath(new Path("E1", Direction.EDGE, null));	//z5
        temp.get("a5").addPath(new Path("D1", Direction.VERTEX, null));	//z4
        temp.get("a5").addPath(new Path("E2", Direction.EDGE, temp.get("a4")));	//a4
        temp.get("a5").addPath(new Path("D2", Direction.VERTEX, temp.get("b4")));	//b4
        temp.get("a5").addPath(new Path("E3", Direction.EDGE, temp.get("b5")));
        temp.get("a5").addPath(new Path("D3", Direction.VERTEX, temp.get("b6")));
        temp.get("a5").addPath(new Path("E4", Direction.EDGE, temp.get("a6")));
        temp.get("a5").addPath(new Path("D4", Direction.VERTEX, null));	//z6        
        temp.get("a5").linkPaths("E1", "D1");
        temp.get("a5").linkPaths("D1", "E2");
        temp.get("a5").linkPaths("E2", "D2");
        temp.get("a5").linkPaths("D2", "E3");
        temp.get("a5").linkPaths("E3", "D3");
        temp.get("a5").linkPaths("D3", "E4");
        temp.get("a5").linkPaths("E4", "D4");
        temp.get("a5").linkPaths("D4", "E1");
    
        
        temp.get("a6").addPath(new Path("E1", Direction.EDGE, null));	//z5
        temp.get("a6").addPath(new Path("D1", Direction.VERTEX, null));	//z4
        temp.get("a6").addPath(new Path("E2", Direction.EDGE, temp.get("a5")));	//a4
        temp.get("a6").addPath(new Path("D2", Direction.VERTEX, temp.get("b5")));	//b4
        temp.get("a6").addPath(new Path("E3", Direction.EDGE, temp.get("b6")));
        temp.get("a6").addPath(new Path("D3", Direction.VERTEX, temp.get("b7")));
        temp.get("a6").addPath(new Path("E4", Direction.EDGE, temp.get("a7")));
        temp.get("a6").addPath(new Path("D4", Direction.VERTEX, null));	//z6        
        temp.get("a6").linkPaths("E1", "D1");
        temp.get("a6").linkPaths("D1", "E2");
        temp.get("a6").linkPaths("E2", "D2");
        temp.get("a6").linkPaths("D2", "E3");
        temp.get("a6").linkPaths("E3", "D3");
        temp.get("a6").linkPaths("D3", "E4");
        temp.get("a6").linkPaths("E4", "D4");
        temp.get("a6").linkPaths("D4", "E1");
      
        
        temp.get("a7").addPath(new Path("E1", Direction.EDGE, null));	//z5
        temp.get("a7").addPath(new Path("D1", Direction.VERTEX, null));	//z4
        temp.get("a7").addPath(new Path("E2", Direction.EDGE, temp.get("a6")));	//a4
        temp.get("a7").addPath(new Path("D2", Direction.VERTEX, temp.get("b6")));	//b4
        temp.get("a7").addPath(new Path("E3", Direction.EDGE, temp.get("b7")));
        temp.get("a7").addPath(new Path("D3", Direction.VERTEX, temp.get("b8")));
        temp.get("a7").addPath(new Path("E4", Direction.EDGE, temp.get("a8")));
        temp.get("a7").addPath(new Path("D4", Direction.VERTEX, null));	//z6        
        temp.get("a7").linkPaths("E1", "D1");
        temp.get("a7").linkPaths("D1", "E2");
        temp.get("a7").linkPaths("E2", "D2");
        temp.get("a7").linkPaths("D2", "E3");
        temp.get("a7").linkPaths("E3", "D3");
        temp.get("a7").linkPaths("D3", "E4");
        temp.get("a7").linkPaths("E4", "D4");
        temp.get("a7").linkPaths("D4", "E1");
       
        
        temp.get("a8").addPath(new Path("E1", Direction.EDGE, null));	//z5
        temp.get("a8").addPath(new Path("D1", Direction.VERTEX, null));	//z4
        temp.get("a8").addPath(new Path("E2", Direction.EDGE, temp.get("a7")));	//a4
        temp.get("a8").addPath(new Path("D2", Direction.VERTEX, temp.get("b7")));	//b4
        temp.get("a8").addPath(new Path("E3", Direction.EDGE, temp.get("b8")));
        temp.get("a8").addPath(new Path("D3", Direction.VERTEX, null));
        temp.get("a8").addPath(new Path("E4", Direction.EDGE, null));
        temp.get("a8").addPath(new Path("D4", Direction.VERTEX, null));	//z6        
        temp.get("a8").linkPaths("E1", "D1");
        temp.get("a8").linkPaths("D1", "E2");
        temp.get("a8").linkPaths("E2", "D2");
        temp.get("a8").linkPaths("D2", "E3");
        temp.get("a8").linkPaths("E3", "D3");
        temp.get("a8").linkPaths("D3", "E4");
        temp.get("a8").linkPaths("E4", "D4");
        temp.get("a8").linkPaths("D4", "E1");
     
        temp.get("b1").addPath(new Path("E1", Direction.EDGE, temp.get("a1")));	//a1
        temp.get("b1").addPath(new Path("D1", Direction.VERTEX, null));	//a0
        temp.get("b1").addPath(new Path("E2", Direction.EDGE, null));	//b0
        temp.get("b1").addPath(new Path("D2", Direction.VERTEX, null));	//c0
        temp.get("b1").addPath(new Path("E3", Direction.EDGE, temp.get("c1")));
        temp.get("b1").addPath(new Path("D3", Direction.VERTEX, temp.get("c2")));
        temp.get("b1").addPath(new Path("E4", Direction.EDGE, temp.get("b2")));
        temp.get("b1").addPath(new Path("D4", Direction.VERTEX, temp.get("a2")));	//a2        
        temp.get("b1").linkPaths("E1", "D1");
        temp.get("b1").linkPaths("D1", "E2");
        temp.get("b1").linkPaths("E2", "D2");
        temp.get("b1").linkPaths("D2", "E3");
        temp.get("b1").linkPaths("E3", "D3");
        temp.get("b1").linkPaths("D3", "E4");
        temp.get("b1").linkPaths("E4", "D4");
        temp.get("b1").linkPaths("D4", "E1");
        
        temp.get("b2").addPath(new Path("E1", Direction.EDGE, temp.get("a2")));	//a2
        temp.get("b2").addPath(new Path("D1", Direction.VERTEX, temp.get("a1")));	//a1
        temp.get("b2").addPath(new Path("E2", Direction.EDGE, temp.get("b1")));	//b1
        temp.get("b2").addPath(new Path("D2", Direction.VERTEX, temp.get("c1")));	//c1
        temp.get("b2").addPath(new Path("E3", Direction.EDGE, temp.get("c2")));
        temp.get("b2").addPath(new Path("D3", Direction.VERTEX, temp.get("c3")));
        temp.get("b2").addPath(new Path("E4", Direction.EDGE, temp.get("b3")));
        temp.get("b2").addPath(new Path("D4", Direction.VERTEX, temp.get("a3")));	        
        temp.get("b2").linkPaths("E1", "D1");
        temp.get("b2").linkPaths("D1", "E2");
        temp.get("b2").linkPaths("E2", "D2");
        temp.get("b2").linkPaths("D2", "E3");
        temp.get("b2").linkPaths("E3", "D3");
        temp.get("b2").linkPaths("D3", "E4");
        temp.get("b2").linkPaths("E4", "D4");
        temp.get("b2").linkPaths("D4", "E1");
        
        temp.get("b3").addPath(new Path("E1", Direction.EDGE, temp.get("a3")));	
        temp.get("b3").addPath(new Path("D1", Direction.VERTEX, temp.get("a2")));	
        temp.get("b3").addPath(new Path("E2", Direction.EDGE, temp.get("b2")));	
        temp.get("b3").addPath(new Path("D2", Direction.VERTEX, temp.get("c2")));	
        temp.get("b3").addPath(new Path("E3", Direction.EDGE, temp.get("c3")));
        temp.get("b3").addPath(new Path("D3", Direction.VERTEX, temp.get("c4")));
        temp.get("b3").addPath(new Path("E4", Direction.EDGE, temp.get("b4")));
        temp.get("b3").addPath(new Path("D4", Direction.VERTEX, temp.get("a4")));	        
        temp.get("b3").linkPaths("E1", "D1");
        temp.get("b3").linkPaths("D1", "E2");
        temp.get("b3").linkPaths("E2", "D2");
        temp.get("b3").linkPaths("D2", "E3");
        temp.get("b3").linkPaths("E3", "D3");
        temp.get("b3").linkPaths("D3", "E4");
        temp.get("b3").linkPaths("E4", "D4");
        temp.get("b3").linkPaths("D4", "E1");
        temp.get("b4").addPath(new Path("E1", Direction.EDGE, temp.get("a4")));	
        temp.get("b4").addPath(new Path("D1", Direction.VERTEX, temp.get("a3")));	
        temp.get("b4").addPath(new Path("E2", Direction.EDGE, temp.get("b3")));	
        temp.get("b4").addPath(new Path("D2", Direction.VERTEX, temp.get("c3")));	
        temp.get("b4").addPath(new Path("E3", Direction.EDGE, temp.get("c4")));
        temp.get("b4").addPath(new Path("D3", Direction.VERTEX, temp.get("c5")));
        temp.get("b4").addPath(new Path("E4", Direction.EDGE, temp.get("b5")));
        temp.get("b4").addPath(new Path("D4", Direction.VERTEX, temp.get("a5")));	        
        temp.get("b4").linkPaths("E1", "D1");
        temp.get("b4").linkPaths("D1", "E2");
        temp.get("b4").linkPaths("E2", "D2");
        temp.get("b4").linkPaths("D2", "E3");
        temp.get("b4").linkPaths("E3", "D3");
        temp.get("b4").linkPaths("D3", "E4");
        temp.get("b4").linkPaths("E4", "D4");
        temp.get("b4").linkPaths("D4", "E1");
        
        temp.get("b5").addPath(new Path("E1", Direction.EDGE, temp.get("a5")));	
        temp.get("b5").addPath(new Path("D1", Direction.VERTEX, temp.get("a4")));	
        temp.get("b5").addPath(new Path("E2", Direction.EDGE, temp.get("b4")));	
        temp.get("b5").addPath(new Path("D2", Direction.VERTEX, temp.get("c4")));	
        temp.get("b5").addPath(new Path("E3", Direction.EDGE, temp.get("c5")));
        temp.get("b5").addPath(new Path("D3", Direction.VERTEX, temp.get("c6")));
        temp.get("b5").addPath(new Path("E4", Direction.EDGE, temp.get("b6")));
        temp.get("b5").addPath(new Path("D4", Direction.VERTEX, temp.get("a6")));	        
        temp.get("b5").linkPaths("E1", "D1");
        temp.get("b5").linkPaths("D1", "E2");
        temp.get("b5").linkPaths("E2", "D2");
        temp.get("b5").linkPaths("D2", "E3");
        temp.get("b5").linkPaths("E3", "D3");
        temp.get("b5").linkPaths("D3", "E4");
        temp.get("b5").linkPaths("E4", "D4");
        temp.get("b5").linkPaths("D4", "E1");
        temp.get("b6").addPath(new Path("E1", Direction.EDGE, temp.get("a6")));	
        temp.get("b6").addPath(new Path("D1", Direction.VERTEX, temp.get("a5")));	
        temp.get("b6").addPath(new Path("E2", Direction.EDGE, temp.get("b5")));	
        temp.get("b6").addPath(new Path("D2", Direction.VERTEX, temp.get("c5")));	
        temp.get("b6").addPath(new Path("E3", Direction.EDGE, temp.get("c6")));
        temp.get("b6").addPath(new Path("D3", Direction.VERTEX, temp.get("c7")));
        temp.get("b6").addPath(new Path("E4", Direction.EDGE, temp.get("b7")));
        temp.get("b6").addPath(new Path("D4", Direction.VERTEX, temp.get("a7")));	        
        temp.get("b6").linkPaths("E1", "D1");
        temp.get("b6").linkPaths("D1", "E2");
        temp.get("b6").linkPaths("E2", "D2");
        temp.get("b6").linkPaths("D2", "E3");
        temp.get("b6").linkPaths("E3", "D3");
        temp.get("b6").linkPaths("D3", "E4");
        temp.get("b6").linkPaths("E4", "D4");
        temp.get("b6").linkPaths("D4", "E1");
        
     
        
        temp.get("b7").addPath(new Path("E1", Direction.EDGE, temp.get("a7")));	
        temp.get("b7").addPath(new Path("D1", Direction.VERTEX, temp.get("a6")));	
        temp.get("b7").addPath(new Path("E2", Direction.EDGE, temp.get("b6")));	
        temp.get("b7").addPath(new Path("D2", Direction.VERTEX, temp.get("c6")));	
        temp.get("b7").addPath(new Path("E3", Direction.EDGE, temp.get("c7")));
        temp.get("b7").addPath(new Path("D3", Direction.VERTEX, temp.get("c8")));
        temp.get("b7").addPath(new Path("E4", Direction.EDGE, temp.get("b8")));
        temp.get("b7").addPath(new Path("D4", Direction.VERTEX, temp.get("a8")));	        
        temp.get("b7").linkPaths("E1", "D1");
        temp.get("b7").linkPaths("D1", "E2");
        temp.get("b7").linkPaths("E2", "D2");
        temp.get("b7").linkPaths("D2", "E3");
        temp.get("b7").linkPaths("E3", "D3");
        temp.get("b7").linkPaths("D3", "E4");
        temp.get("b7").linkPaths("E4", "D4");
        temp.get("b7").linkPaths("D4", "E1");
        
        temp.get("b8").addPath(new Path("E1", Direction.EDGE, temp.get("a8")));	
        temp.get("b8").addPath(new Path("D1", Direction.VERTEX, temp.get("a7")));	
        temp.get("b8").addPath(new Path("E2", Direction.EDGE, temp.get("b7")));	
        temp.get("b8").addPath(new Path("D2", Direction.VERTEX, temp.get("c7")));	
        temp.get("b8").addPath(new Path("E3", Direction.EDGE, temp.get("c8")));
        temp.get("b8").addPath(new Path("D3", Direction.VERTEX, null));
        temp.get("b8").addPath(new Path("E4", Direction.EDGE, null));
        temp.get("b8").addPath(new Path("D4", Direction.VERTEX, null));	        
        temp.get("b8").linkPaths("E1", "D1");
        temp.get("b8").linkPaths("D1", "E2");
        temp.get("b8").linkPaths("E2", "D2");
        temp.get("b8").linkPaths("D2", "E3");
        temp.get("b8").linkPaths("E3", "D3");
        temp.get("b8").linkPaths("D3", "E4");
        temp.get("b8").linkPaths("E4", "D4");
        temp.get("b8").linkPaths("D4", "E1");
        temp.get("c1").addPath(new Path("E1", Direction.EDGE, temp.get("b1")));	//b1
        temp.get("c1").addPath(new Path("D1", Direction.VERTEX, null));	//b0
        temp.get("c1").addPath(new Path("E2", Direction.EDGE, null));	//c0
        temp.get("c1").addPath(new Path("D2", Direction.VERTEX, null));	//d0
        temp.get("c1").addPath(new Path("E3", Direction.EDGE, temp.get("d1")));
        temp.get("c1").addPath(new Path("D3", Direction.VERTEX, temp.get("d2")));
        temp.get("c1").addPath(new Path("E4", Direction.EDGE, temp.get("c2")));
        temp.get("c1").addPath(new Path("D4", Direction.VERTEX, temp.get("b2")));	//b2        
        temp.get("c1").linkPaths("E1", "D1");
        temp.get("c1").linkPaths("D1", "E2");
        temp.get("c1").linkPaths("E2", "D2");
        temp.get("c1").linkPaths("D2", "E3");
        temp.get("c1").linkPaths("E3", "D3");
        temp.get("c1").linkPaths("D3", "E4");
        temp.get("c1").linkPaths("E4", "D4");
        temp.get("c1").linkPaths("D4", "E1");
        temp.get("c2").addPath(new Path("E1", Direction.EDGE, temp.get("b2")));
        temp.get("c2").addPath(new Path("D1", Direction.VERTEX, temp.get("b1")));	//b1
        temp.get("c2").addPath(new Path("E2", Direction.EDGE, temp.get("c1")));	//c1
        temp.get("c2").addPath(new Path("D2", Direction.VERTEX, temp.get("d1")));	//d1
        temp.get("c2").addPath(new Path("E3", Direction.EDGE, temp.get("d2")));
        temp.get("c2").addPath(new Path("D3", Direction.VERTEX, temp.get("d3")));
        temp.get("c2").addPath(new Path("E4", Direction.EDGE, temp.get("c3")));
        temp.get("c2").addPath(new Path("D4", Direction.VERTEX, temp.get("b3")));
        temp.get("c2").linkPaths("E1", "D1");
        temp.get("c2").linkPaths("D1", "E2");
        temp.get("c2").linkPaths("E2", "D2");
        temp.get("c2").linkPaths("D2", "E3");
        temp.get("c2").linkPaths("E3", "D3");
        temp.get("c2").linkPaths("D3", "E4");
        temp.get("c2").linkPaths("E4", "D4");
        temp.get("c2").linkPaths("D4", "E1");
        temp.get("c3").addPath(new Path("E1", Direction.EDGE, temp.get("b3")));
        temp.get("c3").addPath(new Path("D1", Direction.VERTEX, temp.get("b2")));
        temp.get("c3").addPath(new Path("E2", Direction.EDGE, temp.get("c2")));	
        temp.get("c3").addPath(new Path("D2", Direction.VERTEX, temp.get("d2")));
        temp.get("c3").addPath(new Path("E3", Direction.EDGE, temp.get("d3")));
        temp.get("c3").addPath(new Path("D3", Direction.VERTEX, temp.get("d4")));
        temp.get("c3").addPath(new Path("E4", Direction.EDGE, temp.get("c4")));
        temp.get("c3").addPath(new Path("D4", Direction.VERTEX, temp.get("b4")));
        temp.get("c3").linkPaths("E1", "D1");
        temp.get("c3").linkPaths("D1", "E2");
        temp.get("c3").linkPaths("E2", "D2");
        temp.get("c3").linkPaths("D2", "E3");
        temp.get("c3").linkPaths("E3", "D3");
        temp.get("c3").linkPaths("D3", "E4");
        temp.get("c3").linkPaths("E4", "D4");
        temp.get("c3").linkPaths("D4", "E1");
        temp.get("c4").addPath(new Path("E1", Direction.EDGE, temp.get("b4")));
        temp.get("c4").addPath(new Path("D1", Direction.VERTEX, temp.get("b3")));
        temp.get("c4").addPath(new Path("E2", Direction.EDGE, temp.get("c3")));
        temp.get("c4").addPath(new Path("D2", Direction.VERTEX, temp.get("d3")));
        temp.get("c4").addPath(new Path("E3", Direction.EDGE, temp.get("d4")));
        temp.get("c4").addPath(new Path("D3", Direction.VERTEX, temp.get("d5")));
        temp.get("c4").addPath(new Path("E4", Direction.EDGE, temp.get("c5")));
        temp.get("c4").addPath(new Path("D4", Direction.VERTEX, temp.get("b5")));
        temp.get("c4").linkPaths("E1", "D1");
        temp.get("c4").linkPaths("D1", "E2");
        temp.get("c4").linkPaths("E2", "D2");
        temp.get("c4").linkPaths("D2", "E3");
        temp.get("c4").linkPaths("E3", "D3");
        temp.get("c4").linkPaths("D3", "E4");
        temp.get("c4").linkPaths("E4", "D4");
        temp.get("c4").linkPaths("D4", "E1");
        temp.get("c5").addPath(new Path("E1", Direction.EDGE, temp.get("b5")));
        temp.get("c5").addPath(new Path("D1", Direction.VERTEX, temp.get("b4")));
        temp.get("c5").addPath(new Path("E2", Direction.EDGE, temp.get("c4")));
        temp.get("c5").addPath(new Path("D2", Direction.VERTEX, temp.get("d4")));
        temp.get("c5").addPath(new Path("E3", Direction.EDGE, temp.get("d5")));
        temp.get("c5").addPath(new Path("D3", Direction.VERTEX, temp.get("d6")));
        temp.get("c5").addPath(new Path("E4", Direction.EDGE, temp.get("c6")));
        temp.get("c5").addPath(new Path("D4", Direction.VERTEX, temp.get("b6")));
        temp.get("c5").linkPaths("E1", "D1");
        temp.get("c5").linkPaths("D1", "E2");
        temp.get("c5").linkPaths("E2", "D2");
        temp.get("c5").linkPaths("D2", "E3");
        temp.get("c5").linkPaths("E3", "D3");
        temp.get("c5").linkPaths("D3", "E4");
        temp.get("c5").linkPaths("E4", "D4");
        temp.get("c5").linkPaths("D4", "E1");
        temp.get("c6").addPath(new Path("E1", Direction.EDGE, temp.get("b6")));
        temp.get("c6").addPath(new Path("D1", Direction.VERTEX, temp.get("b5")));
        temp.get("c6").addPath(new Path("E2", Direction.EDGE, temp.get("c5")));
        temp.get("c6").addPath(new Path("D2", Direction.VERTEX, temp.get("d5")));
        temp.get("c6").addPath(new Path("E3", Direction.EDGE, temp.get("d6")));
        temp.get("c6").addPath(new Path("D3", Direction.VERTEX, temp.get("d7")));
        temp.get("c6").addPath(new Path("E4", Direction.EDGE, temp.get("c7")));
        temp.get("c6").addPath(new Path("D4", Direction.VERTEX, temp.get("b7")));
        temp.get("c6").linkPaths("E1", "D1");
        temp.get("c6").linkPaths("D1", "E2");
        temp.get("c6").linkPaths("E2", "D2");
        temp.get("c6").linkPaths("D2", "E3");
        temp.get("c6").linkPaths("E3", "D3");
        temp.get("c6").linkPaths("D3", "E4");
        temp.get("c6").linkPaths("E4", "D4");
        temp.get("c6").linkPaths("D4", "E1");
        temp.get("c7").addPath(new Path("E1", Direction.EDGE, temp.get("b7")));
        temp.get("c7").addPath(new Path("D1", Direction.VERTEX, temp.get("b6")));
        temp.get("c7").addPath(new Path("E2", Direction.EDGE, temp.get("c6")));
        temp.get("c7").addPath(new Path("D2", Direction.VERTEX, temp.get("d6")));
        temp.get("c7").addPath(new Path("E3", Direction.EDGE, temp.get("d7")));
        temp.get("c7").addPath(new Path("D3", Direction.VERTEX, temp.get("d8")));
        temp.get("c7").addPath(new Path("E4", Direction.EDGE, temp.get("c8")));
        temp.get("c7").addPath(new Path("D4", Direction.VERTEX, temp.get("b8")));
        temp.get("c7").linkPaths("E1", "D1");
        temp.get("c7").linkPaths("D1", "E2");
        temp.get("c7").linkPaths("E2", "D2");
        temp.get("c7").linkPaths("D2", "E3");
        temp.get("c7").linkPaths("E3", "D3");
        temp.get("c7").linkPaths("D3", "E4");
        temp.get("c7").linkPaths("E4", "D4");
        temp.get("c7").linkPaths("D4", "E1");
        temp.get("c7").addPath(new Path("E1", Direction.EDGE, temp.get("b8")));
        temp.get("c7").addPath(new Path("D1", Direction.VERTEX, temp.get("b7")));
        temp.get("c7").addPath(new Path("E2", Direction.EDGE, temp.get("c7")));
        temp.get("c7").addPath(new Path("D2", Direction.VERTEX, temp.get("d7")));
        temp.get("c7").addPath(new Path("E3", Direction.EDGE, temp.get("d8")));
        temp.get("c7").addPath(new Path("D3", Direction.VERTEX, null));
        temp.get("c7").addPath(new Path("E4", Direction.EDGE, null));
        temp.get("c7").addPath(new Path("D4", Direction.VERTEX, null));
        temp.get("c7").linkPaths("E1", "D1");
        temp.get("c7").linkPaths("D1", "E2");
        temp.get("c7").linkPaths("E2", "D2");
        temp.get("c7").linkPaths("D2", "E3");
        temp.get("c7").linkPaths("E3", "D3");
        temp.get("c7").linkPaths("D3", "E4");
        temp.get("c7").linkPaths("E4", "D4");
        temp.get("c7").linkPaths("D4", "E1"); 
        // d
        temp.get("d1").addPath(new Path("E1", Direction.EDGE, temp.get("c1")));	//c1
        temp.get("d1").addPath(new Path("D1", Direction.VERTEX, null));	//c0
        temp.get("d1").addPath(new Path("E2", Direction.EDGE, null));	//d0
        temp.get("d1").addPath(new Path("D2", Direction.VERTEX, null));	//e0
        temp.get("d1").addPath(new Path("E3", Direction.EDGE, temp.get("e1")));
        temp.get("d1").addPath(new Path("D3", Direction.VERTEX, temp.get("e2")));
        temp.get("d1").addPath(new Path("E4", Direction.EDGE, temp.get("d2")));
        temp.get("d1").addPath(new Path("D4", Direction.VERTEX, temp.get("c2")));	//c2        
        temp.get("d1").linkPaths("E1", "D1");
        temp.get("d1").linkPaths("D1", "E2");
        temp.get("d1").linkPaths("E2", "D2");
        temp.get("d1").linkPaths("D2", "E3");
        temp.get("d1").linkPaths("E3", "D3");
        temp.get("d1").linkPaths("D3", "E4");
        temp.get("d1").linkPaths("E4", "D4");
        temp.get("d1").linkPaths("D4", "E1");
        temp.get("d2").addPath(new Path("E1", Direction.EDGE, temp.get("c2")));
        temp.get("d2").addPath(new Path("D1", Direction.VERTEX, temp.get("c1")));	//c1
        temp.get("d2").addPath(new Path("E2", Direction.EDGE, temp.get("d1")));	//d1
        temp.get("d2").addPath(new Path("D2", Direction.VERTEX, temp.get("e1")));	//e1
        temp.get("d2").addPath(new Path("E3", Direction.EDGE, temp.get("e2")));
        temp.get("d2").addPath(new Path("D3", Direction.VERTEX, temp.get("e3")));
        temp.get("d2").addPath(new Path("E4", Direction.EDGE, temp.get("d3")));
        temp.get("d2").addPath(new Path("D4", Direction.VERTEX, temp.get("c3")));	
        temp.get("d2").linkPaths("E1", "D1");
        temp.get("d2").linkPaths("D1", "E2");
        temp.get("d2").linkPaths("E2", "D2");
        temp.get("d2").linkPaths("D2", "E3");
        temp.get("d2").linkPaths("E3", "D3");
        temp.get("d2").linkPaths("D3", "E4");
        temp.get("d2").linkPaths("E4", "D4");
        temp.get("d2").linkPaths("D4", "E1");
        temp.get("d3").addPath(new Path("E1", Direction.EDGE, temp.get("c3")));
        temp.get("d3").addPath(new Path("D1", Direction.VERTEX, temp.get("c2")));	
        temp.get("d3").addPath(new Path("E2", Direction.EDGE, temp.get("d2")));	
        temp.get("d3").addPath(new Path("D2", Direction.VERTEX, temp.get("e2")));	
        temp.get("d3").addPath(new Path("E3", Direction.EDGE, temp.get("e3")));
        temp.get("d3").addPath(new Path("D3", Direction.VERTEX, temp.get("e4")));
        temp.get("d3").addPath(new Path("E4", Direction.EDGE, temp.get("d4")));
        temp.get("d3").addPath(new Path("D4", Direction.VERTEX, temp.get("c4")));
        temp.get("d3").linkPaths("E1", "D1");
        temp.get("d3").linkPaths("D1", "E2");
        temp.get("d3").linkPaths("E2", "D2");
        temp.get("d3").linkPaths("D2", "E3");
        temp.get("d3").linkPaths("E3", "D3");
        temp.get("d3").linkPaths("D3", "E4");
        temp.get("d3").linkPaths("E4", "D4");
        temp.get("d3").linkPaths("D4", "E1");
        temp.get("d4").addPath(new Path("E1", Direction.EDGE, temp.get("c4")));
        temp.get("d4").addPath(new Path("D1", Direction.VERTEX, temp.get("c3")));
        temp.get("d4").addPath(new Path("E2", Direction.EDGE, temp.get("d3")));
        temp.get("d4").addPath(new Path("D2", Direction.VERTEX, temp.get("e3")));
        temp.get("d4").addPath(new Path("E3", Direction.EDGE, temp.get("e4")));
        temp.get("d4").addPath(new Path("D3", Direction.VERTEX, temp.get("e9")));
        temp.get("d4").addPath(new Path("E4", Direction.EDGE, temp.get("d5")));
        temp.get("d4").addPath(new Path("D4", Direction.VERTEX, temp.get("c5")));
        temp.get("d4").getPath("D3").getPathData().addPosition((temp.get("i5")));
        temp.get("d4").getPath("D3").getPathData().addPosition((temp.get("i9")));
        temp.get("d4").linkPaths("E1", "D1");
        temp.get("d4").linkPaths("D1", "E2");
        temp.get("d4").linkPaths("E2", "D2");
        temp.get("d4").linkPaths("D2", "E3");
        temp.get("d4").linkPaths("E3", "D3");
        temp.get("d4").linkPaths("D3", "E4");
        temp.get("d4").linkPaths("E4", "D4");
        temp.get("d4").linkPaths("D4", "E1");
        temp.get("d5").addPath(new Path("E1", Direction.EDGE, temp.get("c5")));
        temp.get("d5").addPath(new Path("D1", Direction.VERTEX, temp.get("c4")));
        temp.get("d5").addPath(new Path("E2", Direction.EDGE, temp.get("d4")));
        temp.get("d5").addPath(new Path("D2", Direction.VERTEX, temp.get("e4")));
        temp.get("d5").addPath(new Path("E3", Direction.EDGE, temp.get("i5")));
        temp.get("d5").addPath(new Path("D3", Direction.VERTEX, temp.get("i6")));
        temp.get("d5").addPath(new Path("E4", Direction.EDGE, temp.get("d6")));
        temp.get("d5").addPath(new Path("D4", Direction.VERTEX, temp.get("c6")));
        temp.get("d5").getPath("D2").getPathData().addPosition((temp.get("i9")));
        temp.get("d5").getPath("D2").getPathData().addPosition((temp.get("e9")));
        temp.get("d5").linkPaths("E1", "D1");
        temp.get("d5").linkPaths("D1", "E2");
        temp.get("d5").linkPaths("E2", "D2");
        temp.get("d5").linkPaths("D2", "E3");
        temp.get("d5").linkPaths("E3", "D3");
        temp.get("d5").linkPaths("D3", "E4");
        temp.get("d5").linkPaths("E4", "D4");
        temp.get("d5").linkPaths("D4", "E1");
        temp.get("d6").addPath(new Path("E1", Direction.EDGE, temp.get("c6")));
        temp.get("d6").addPath(new Path("D1", Direction.VERTEX, temp.get("c5")));
        temp.get("d6").addPath(new Path("E2", Direction.EDGE, temp.get("d5")));
        temp.get("d6").addPath(new Path("D2", Direction.VERTEX, temp.get("i5")));
        temp.get("d6").addPath(new Path("E3", Direction.EDGE, temp.get("i6")));
        temp.get("d6").addPath(new Path("D3", Direction.VERTEX, temp.get("i7")));
        temp.get("d6").addPath(new Path("E4", Direction.EDGE, temp.get("d7")));
        temp.get("d6").addPath(new Path("D4", Direction.VERTEX, temp.get("c7")));
        temp.get("d6").linkPaths("E1", "D1");
        temp.get("d6").linkPaths("D1", "E2");
        temp.get("d6").linkPaths("E2", "D2");
        temp.get("d6").linkPaths("D2", "E3");
        temp.get("d6").linkPaths("E3", "D3");
        temp.get("d6").linkPaths("D3", "E4");
        temp.get("d6").linkPaths("E4", "D4");
        temp.get("d6").linkPaths("D4", "E1");
        temp.get("d7").addPath(new Path("E1", Direction.EDGE, temp.get("c7")));
        temp.get("d7").addPath(new Path("D1", Direction.VERTEX, temp.get("c6")));
        temp.get("d7").addPath(new Path("E2", Direction.EDGE, temp.get("d6")));
        temp.get("d7").addPath(new Path("D2", Direction.VERTEX, temp.get("i6")));
        temp.get("d7").addPath(new Path("E3", Direction.EDGE, temp.get("i7")));
        temp.get("d7").addPath(new Path("D3", Direction.VERTEX, temp.get("i8")));
        temp.get("d7").addPath(new Path("E4", Direction.EDGE, temp.get("d8")));
        temp.get("d7").addPath(new Path("D4", Direction.VERTEX, temp.get("c8")));
        temp.get("d7").linkPaths("E1", "D1");
        temp.get("d7").linkPaths("D1", "E2");
        temp.get("d7").linkPaths("E2", "D2");
        temp.get("d7").linkPaths("D2", "E3");
        temp.get("d7").linkPaths("E3", "D3");
        temp.get("d7").linkPaths("D3", "E4");
        temp.get("d7").linkPaths("E4", "D4");
        temp.get("d7").linkPaths("D4", "E1");
        temp.get("d8").addPath(new Path("E1", Direction.EDGE, temp.get("c8")));
        temp.get("d8").addPath(new Path("D1", Direction.VERTEX, temp.get("c7")));
        temp.get("d8").addPath(new Path("E2", Direction.EDGE, temp.get("d7")));
        temp.get("d8").addPath(new Path("D2", Direction.VERTEX, temp.get("i7")));
        temp.get("d8").addPath(new Path("E3", Direction.EDGE, temp.get("i8")));
        temp.get("d8").addPath(new Path("D3", Direction.VERTEX, null));
        temp.get("d8").addPath(new Path("E4", Direction.EDGE, null));
        temp.get("d8").addPath(new Path("D4", Direction.VERTEX, null));
        temp.get("d8").linkPaths("E1", "D1");
        temp.get("d8").linkPaths("D1", "E2");
        temp.get("d8").linkPaths("E2", "D2");
        temp.get("d8").linkPaths("D2", "E3");
        temp.get("d8").linkPaths("E3", "D3");
        temp.get("d8").linkPaths("D3", "E4");
        temp.get("d8").linkPaths("E4", "D4");
        temp.get("d8").linkPaths("D4", "E1");
        temp.get("e1").addPath(new Path("E1", Direction.EDGE, temp.get("d1")));
        temp.get("e1").addPath(new Path("D1", Direction.VERTEX, null));	//d0
        temp.get("e1").addPath(new Path("E2", Direction.EDGE, null));	//e0
        temp.get("e1").addPath(new Path("D2", Direction.VERTEX, null));	//f0
        temp.get("e1").addPath(new Path("E3", Direction.EDGE, temp.get("f1")));
        temp.get("e1").addPath(new Path("D3", Direction.VERTEX, temp.get("f2")));
        temp.get("e1").addPath(new Path("E4", Direction.EDGE, temp.get("e2")));
        temp.get("e1").addPath(new Path("D4", Direction.VERTEX, temp.get("d2")));
        temp.get("e1").linkPaths("E1", "D1");
        temp.get("e1").linkPaths("D1", "E2");
        temp.get("e1").linkPaths("E2", "D2");
        temp.get("e1").linkPaths("D2", "E3");
        temp.get("e1").linkPaths("E3", "D3");
        temp.get("e1").linkPaths("D3", "E4");
        temp.get("e1").linkPaths("E4", "D4");
        temp.get("e1").linkPaths("D4", "E1");
        temp.get("e2").addPath(new Path("E1", Direction.EDGE, temp.get("d2")));
        temp.get("e2").addPath(new Path("D1", Direction.VERTEX, temp.get("d1")));
        temp.get("e2").addPath(new Path("E2", Direction.EDGE, temp.get("e1")));
        temp.get("e2").addPath(new Path("D2", Direction.VERTEX, temp.get("f1")));
        temp.get("e2").addPath(new Path("E3", Direction.EDGE, temp.get("f2")));
        temp.get("e2").addPath(new Path("D3", Direction.VERTEX, temp.get("f3")));
        temp.get("e2").addPath(new Path("E4", Direction.EDGE, temp.get("e3")));
        temp.get("e2").addPath(new Path("D4", Direction.VERTEX, temp.get("d3")));
        temp.get("e2").linkPaths("E1", "D1");
        temp.get("e2").linkPaths("D1", "E2");
        temp.get("e2").linkPaths("E2", "D2");
        temp.get("e2").linkPaths("D2", "E3");
        temp.get("e2").linkPaths("E3", "D3");
        temp.get("e2").linkPaths("D3", "E4");
        temp.get("e2").linkPaths("E4", "D4");
        temp.get("e2").linkPaths("D4", "E1");
        temp.get("e3").addPath(new Path("E1", Direction.EDGE, temp.get("d3")));
        temp.get("e3").addPath(new Path("D1", Direction.VERTEX, temp.get("d2")));
        temp.get("e3").addPath(new Path("E2", Direction.EDGE, temp.get("e2")));
        temp.get("e3").addPath(new Path("D2", Direction.VERTEX, temp.get("f2")));
        temp.get("e3").addPath(new Path("E3", Direction.EDGE, temp.get("f3")));
        temp.get("e3").addPath(new Path("D3", Direction.VERTEX, temp.get("f4")));
        temp.get("e3").addPath(new Path("E4", Direction.EDGE, temp.get("e4")));
        temp.get("e3").addPath(new Path("D4", Direction.VERTEX, temp.get("d4")));
        temp.get("e3").linkPaths("E1", "D1");
        temp.get("e3").linkPaths("D1", "E2");
        temp.get("e3").linkPaths("E2", "D2");
        temp.get("e3").linkPaths("D2", "E3");
        temp.get("e3").linkPaths("E3", "D3");
        temp.get("e3").linkPaths("D3", "E4");
        temp.get("e3").linkPaths("E4", "D4");
        temp.get("e3").linkPaths("D4", "E1");
        temp.get("e4").addPath(new Path("E1", Direction.EDGE, temp.get("d4")));
        temp.get("e4").addPath(new Path("D1", Direction.VERTEX, temp.get("d3")));
        temp.get("e4").addPath(new Path("E2", Direction.EDGE, temp.get("e3")));
        temp.get("e4").addPath(new Path("D2", Direction.VERTEX, temp.get("f3")));
        temp.get("e4").addPath(new Path("E3", Direction.EDGE, temp.get("f4")));
        temp.get("e4").addPath(new Path("D3", Direction.VERTEX, temp.get("f9")));
        temp.get("e4").addPath(new Path("E4", Direction.EDGE, temp.get("e9")));
        temp.get("e4").addPath(new Path("D4", Direction.VERTEX, temp.get("i9")));
        temp.get("e4").getPath("D4").getPathData().addPosition((temp.get("i5")));
        temp.get("e4").getPath("D4").getPathData().addPosition((temp.get("d5")));
        temp.get("e4").linkPaths("E1", "D1");
        temp.get("e4").linkPaths("D1", "E2");
        temp.get("e4").linkPaths("E2", "D2");
        temp.get("e4").linkPaths("D2", "E3");
        temp.get("e4").linkPaths("E3", "D3");
        temp.get("e4").linkPaths("D3", "E4");
        temp.get("e4").linkPaths("E4", "D4");
        temp.get("e4").linkPaths("D4", "E1");
        temp.get("e9").addPath(new Path("E1", Direction.EDGE, temp.get("e4")));
        temp.get("e9").addPath(new Path("D1", Direction.VERTEX, temp.get("f4")));
        temp.get("e9").addPath(new Path("E2", Direction.EDGE, temp.get("f9")));
        temp.get("e9").addPath(new Path("D2", Direction.VERTEX, temp.get("f10")));
        temp.get("e9").addPath(new Path("E3", Direction.EDGE, temp.get("e10")));
        temp.get("e9").addPath(new Path("D3", Direction.VERTEX, temp.get("i10")));
        temp.get("e9").addPath(new Path("E4", Direction.EDGE, temp.get("i9")));
        temp.get("e9").addPath(new Path("D4", Direction.VERTEX, temp.get("i5")));
        temp.get("e9").getPath("D4").getPathData().addPosition((temp.get("d5")));
        temp.get("e9").getPath("D4").getPathData().addPosition((temp.get("d4")));
        temp.get("e9").linkPaths("E1", "D1");
        temp.get("e9").linkPaths("D1", "E2");
        temp.get("e9").linkPaths("E2", "D2");
        temp.get("e9").linkPaths("D2", "E3");
        temp.get("e9").linkPaths("E3", "D3");
        temp.get("e9").linkPaths("D3", "E4");
        temp.get("e9").linkPaths("E4", "D4");
        temp.get("e9").linkPaths("D4", "E1");
        temp.get("e10").addPath(new Path("E1", Direction.EDGE, temp.get("e9")));
        temp.get("e10").addPath(new Path("D1", Direction.VERTEX, temp.get("f9")));
        temp.get("e10").addPath(new Path("E2", Direction.EDGE, temp.get("f10")));
        temp.get("e10").addPath(new Path("D2", Direction.VERTEX, temp.get("f11")));
        temp.get("e10").addPath(new Path("E3", Direction.EDGE, temp.get("e11")));
        temp.get("e10").addPath(new Path("D3", Direction.VERTEX, temp.get("i11")));
        temp.get("e10").addPath(new Path("E4", Direction.EDGE, temp.get("i10")));
        temp.get("e10").addPath(new Path("D4", Direction.VERTEX, temp.get("i9")));
        temp.get("e10").linkPaths("E1", "D1");
        temp.get("e10").linkPaths("D1", "E2");
        temp.get("e10").linkPaths("E2", "D2");
        temp.get("e10").linkPaths("D2", "E3");
        temp.get("e10").linkPaths("E3", "D3");
        temp.get("e10").linkPaths("D3", "E4");
        temp.get("e10").linkPaths("E4", "D4");
        temp.get("e10").linkPaths("D4", "E1");
        temp.get("e11").addPath(new Path("E1", Direction.EDGE, temp.get("e10")));
        temp.get("e11").addPath(new Path("D1", Direction.VERTEX, temp.get("f10")));
        temp.get("e11").addPath(new Path("E2", Direction.EDGE, temp.get("f11")));
        temp.get("e11").addPath(new Path("D2", Direction.VERTEX, temp.get("f12")));
        temp.get("e11").addPath(new Path("E3", Direction.EDGE, temp.get("e12")));
        temp.get("e11").addPath(new Path("D3", Direction.VERTEX, temp.get("i12")));
        temp.get("e11").addPath(new Path("E4", Direction.EDGE, temp.get("i11")));
        temp.get("e11").addPath(new Path("D4", Direction.VERTEX, temp.get("i10")));
        temp.get("e11").linkPaths("E1", "D1");
        temp.get("e11").linkPaths("D1", "E2");
        temp.get("e11").linkPaths("E2", "D2");
        temp.get("e11").linkPaths("D2", "E3");
        temp.get("e11").linkPaths("E3", "D3");
        temp.get("e11").linkPaths("D3", "E4");
        temp.get("e11").linkPaths("E4", "D4");
        temp.get("e11").linkPaths("D4", "E1");
        temp.get("e12").addPath(new Path("E1", Direction.EDGE, temp.get("e11")));
        temp.get("e12").addPath(new Path("D1", Direction.VERTEX, temp.get("f11")));
        temp.get("e12").addPath(new Path("E2", Direction.EDGE, temp.get("f12")));
        temp.get("e12").addPath(new Path("D2", Direction.VERTEX, null));
        temp.get("e12").addPath(new Path("E3", Direction.EDGE, null));
        temp.get("e12").addPath(new Path("D3", Direction.VERTEX, null));
        temp.get("e12").addPath(new Path("E4", Direction.EDGE, temp.get("i12")));
        temp.get("e12").addPath(new Path("D4", Direction.VERTEX, temp.get("i11")));
        temp.get("e12").linkPaths("E1", "D1");
        temp.get("e12").linkPaths("D1", "E2");
        temp.get("e12").linkPaths("E2", "D2");
        temp.get("e12").linkPaths("D2", "E3");
        temp.get("e12").linkPaths("E3", "D3");
        temp.get("e12").linkPaths("D3", "E4");
        temp.get("e12").linkPaths("E4", "D4");
        temp.get("e12").linkPaths("D4", "E1");
        temp.get("f1").addPath(new Path("E1", Direction.EDGE, temp.get("e1")));
        temp.get("f1").addPath(new Path("D1", Direction.VERTEX, null));	//e0
        temp.get("f1").addPath(new Path("E2", Direction.EDGE, null));	//f0
        temp.get("f1").addPath(new Path("D2", Direction.VERTEX, null));	//g0
        temp.get("f1").addPath(new Path("E3", Direction.EDGE, temp.get("g1")));
        temp.get("f1").addPath(new Path("D3", Direction.VERTEX, temp.get("g2")));
        temp.get("f1").addPath(new Path("E4", Direction.EDGE, temp.get("f2")));
        temp.get("f1").addPath(new Path("D4", Direction.VERTEX, temp.get("e2")));
        temp.get("f1").linkPaths("E1", "D1");
        temp.get("f1").linkPaths("D1", "E2");
        temp.get("f1").linkPaths("E2", "D2");
        temp.get("f1").linkPaths("D2", "E3");
        temp.get("f1").linkPaths("E3", "D3");
        temp.get("f1").linkPaths("D3", "E4");
        temp.get("f1").linkPaths("E4", "D4");
        temp.get("f1").linkPaths("D4", "E1");
        temp.get("f2").addPath(new Path("E1", Direction.EDGE, temp.get("e2")));
        temp.get("f2").addPath(new Path("D1", Direction.VERTEX, temp.get("e1")));
        temp.get("f2").addPath(new Path("E2", Direction.EDGE, temp.get("f1")));
        temp.get("f2").addPath(new Path("D2", Direction.VERTEX, temp.get("g1")));
        temp.get("f2").addPath(new Path("E3", Direction.EDGE, temp.get("g2")));
        temp.get("f2").addPath(new Path("D3", Direction.VERTEX, temp.get("g3")));
        temp.get("f2").addPath(new Path("E4", Direction.EDGE, temp.get("f3")));
        temp.get("f2").addPath(new Path("D4", Direction.VERTEX, temp.get("e3")));
        temp.get("f2").linkPaths("E1", "D1");
        temp.get("f2").linkPaths("D1", "E2");
        temp.get("f2").linkPaths("E2", "D2");
        temp.get("f2").linkPaths("D2", "E3");
        temp.get("f2").linkPaths("E3", "D3");
        temp.get("f2").linkPaths("D3", "E4");
        temp.get("f2").linkPaths("E4", "D4");
        temp.get("f2").linkPaths("D4", "E1");
        temp.get("f3").addPath(new Path("E1", Direction.EDGE, temp.get("e3")));
        temp.get("f3").addPath(new Path("D1", Direction.VERTEX, temp.get("e2")));
        temp.get("f3").addPath(new Path("E2", Direction.EDGE, temp.get("f2")));
        temp.get("f3").addPath(new Path("D2", Direction.VERTEX, temp.get("g2")));
        temp.get("f3").addPath(new Path("E3", Direction.EDGE, temp.get("g3")));
        temp.get("f3").addPath(new Path("D3", Direction.VERTEX, temp.get("g4")));
        temp.get("f3").addPath(new Path("E4", Direction.EDGE, temp.get("f4")));
        temp.get("f3").addPath(new Path("D4", Direction.VERTEX, temp.get("e4")));
        temp.get("f3").linkPaths("E1", "D1");
        temp.get("f3").linkPaths("D1", "E2");
        temp.get("f3").linkPaths("E2", "D2");
        temp.get("f3").linkPaths("D2", "E3");
        temp.get("f3").linkPaths("E3", "D3");
        temp.get("f3").linkPaths("D3", "E4");
        temp.get("f3").linkPaths("E4", "D4");
        temp.get("f3").linkPaths("D4", "E1");
        temp.get("f4").addPath(new Path("E1", Direction.EDGE, temp.get("e4")));
        temp.get("f4").addPath(new Path("D1", Direction.VERTEX, temp.get("e3")));
        temp.get("f4").addPath(new Path("E2", Direction.EDGE, temp.get("f3")));
        temp.get("f4").addPath(new Path("D2", Direction.VERTEX, temp.get("g3")));
        temp.get("f4").addPath(new Path("E3", Direction.EDGE, temp.get("g4")));
        temp.get("f4").addPath(new Path("D3", Direction.VERTEX, temp.get("g9")));
        temp.get("f4").addPath(new Path("E4", Direction.EDGE, temp.get("f9")));
        temp.get("f4").addPath(new Path("D4", Direction.VERTEX, temp.get("e9")));
        temp.get("f4").linkPaths("E1", "D1");
        temp.get("f4").linkPaths("D1", "E2");
        temp.get("f4").linkPaths("E2", "D2");
        temp.get("f4").linkPaths("D2", "E3");
        temp.get("f4").linkPaths("E3", "D3");
        temp.get("f4").linkPaths("D3", "E4");
        temp.get("f4").linkPaths("E4", "D4");
        temp.get("f4").linkPaths("D4", "E1");
        temp.get("f9").addPath(new Path("E1", Direction.EDGE, temp.get("e9")));
        temp.get("f9").addPath(new Path("D1", Direction.VERTEX, temp.get("e4")));
        temp.get("f9").addPath(new Path("E2", Direction.EDGE, temp.get("f4")));
        temp.get("f9").addPath(new Path("D2", Direction.VERTEX, temp.get("g4")));
        temp.get("f9").addPath(new Path("E3", Direction.EDGE, temp.get("g9")));
        temp.get("f9").addPath(new Path("D3", Direction.VERTEX, temp.get("g10")));
        temp.get("f9").addPath(new Path("E4", Direction.EDGE, temp.get("f10")));
        temp.get("f9").addPath(new Path("D4", Direction.VERTEX, temp.get("e10")));
        temp.get("f9").linkPaths("E1", "D1");
        temp.get("f9").linkPaths("D1", "E2");
        temp.get("f9").linkPaths("E2", "D2");
        temp.get("f9").linkPaths("D2", "E3");
        temp.get("f9").linkPaths("E3", "D3");
        temp.get("f9").linkPaths("D3", "E4");
        temp.get("f9").linkPaths("E4", "D4");
        temp.get("f9").linkPaths("D4", "E1");
        temp.get("f10").addPath(new Path("E1", Direction.EDGE, temp.get("e10")));
        temp.get("f10").addPath(new Path("D1", Direction.VERTEX, temp.get("e9")));
        temp.get("f10").addPath(new Path("E2", Direction.EDGE, temp.get("f9")));
        temp.get("f10").addPath(new Path("D2", Direction.VERTEX, temp.get("g9")));
        temp.get("f10").addPath(new Path("E3", Direction.EDGE, temp.get("g10")));
        temp.get("f10").addPath(new Path("D3", Direction.VERTEX, temp.get("g11")));
        temp.get("f10").addPath(new Path("E4", Direction.EDGE, temp.get("f11")));
        temp.get("f10").addPath(new Path("D4", Direction.VERTEX, temp.get("e11")));
        temp.get("f10").linkPaths("E1", "D1");
        temp.get("f10").linkPaths("D1", "E2");
        temp.get("f10").linkPaths("E2", "D2");
        temp.get("f10").linkPaths("D2", "E3");
        temp.get("f10").linkPaths("E3", "D3");
        temp.get("f10").linkPaths("D3", "E4");
        temp.get("f10").linkPaths("E4", "D4");
        temp.get("f10").linkPaths("D4", "E1");
        temp.get("f11").addPath(new Path("E1", Direction.EDGE, temp.get("e11")));
        temp.get("f11").addPath(new Path("D1", Direction.VERTEX, temp.get("e10")));
        temp.get("f11").addPath(new Path("E2", Direction.EDGE, temp.get("f10")));
        temp.get("f11").addPath(new Path("D2", Direction.VERTEX, temp.get("g10")));
        temp.get("f11").addPath(new Path("E3", Direction.EDGE, temp.get("g11")));
        temp.get("f11").addPath(new Path("D3", Direction.VERTEX, temp.get("g12")));
        temp.get("f11").addPath(new Path("E4", Direction.EDGE, temp.get("f12")));
        temp.get("f11").addPath(new Path("D4", Direction.VERTEX, temp.get("e12")));
        temp.get("f11").linkPaths("E1", "D1");
        temp.get("f11").linkPaths("D1", "E2");
        temp.get("f11").linkPaths("E2", "D2");
        temp.get("f11").linkPaths("D2", "E3");
        temp.get("f11").linkPaths("E3", "D3");
        temp.get("f11").linkPaths("D3", "E4");
        temp.get("f11").linkPaths("E4", "D4");
        temp.get("f11").linkPaths("D4", "E1");
        temp.get("f12").addPath(new Path("E1", Direction.EDGE, temp.get("e12")));
        temp.get("f12").addPath(new Path("D1", Direction.VERTEX, temp.get("e11")));
        temp.get("f12").addPath(new Path("E2", Direction.EDGE, temp.get("f11")));
        temp.get("f12").addPath(new Path("D2", Direction.VERTEX, temp.get("g11")));
        temp.get("f12").addPath(new Path("E3", Direction.EDGE, temp.get("g12")));
        temp.get("f12").addPath(new Path("D3", Direction.VERTEX, null));
        temp.get("f12").addPath(new Path("E4", Direction.EDGE, null));
        temp.get("f12").addPath(new Path("D4", Direction.VERTEX, null));
        temp.get("f12").linkPaths("E1", "D1");
        temp.get("f12").linkPaths("D1", "E2");
        temp.get("f12").linkPaths("E2", "D2");
        temp.get("f12").linkPaths("D2", "E3");
        temp.get("f12").linkPaths("E3", "D3");
        temp.get("f12").linkPaths("D3", "E4");
        temp.get("f12").linkPaths("E4", "D4");
        temp.get("f12").linkPaths("D4", "E1");
        temp.get("g1").addPath(new Path("E1", Direction.EDGE, temp.get("f1")));
        temp.get("g1").addPath(new Path("D1", Direction.VERTEX, null));	//f0
        temp.get("g1").addPath(new Path("E2", Direction.EDGE, null));	//g0
        temp.get("g1").addPath(new Path("D2", Direction.VERTEX, null));	//h0
        temp.get("g1").addPath(new Path("E3", Direction.EDGE, temp.get("h1")));
        temp.get("g1").addPath(new Path("D3", Direction.VERTEX, temp.get("h2")));
        temp.get("g1").addPath(new Path("E4", Direction.EDGE, temp.get("g2")));
        temp.get("g1").addPath(new Path("D4", Direction.VERTEX, temp.get("f2")));
        temp.get("g1").linkPaths("E1", "D1");
        temp.get("g1").linkPaths("D1", "E2");
        temp.get("g1").linkPaths("E2", "D2");
        temp.get("g1").linkPaths("D2", "E3");
        temp.get("g1").linkPaths("E3", "D3");
        temp.get("g1").linkPaths("D3", "E4");
        temp.get("g1").linkPaths("E4", "D4");
        temp.get("g1").linkPaths("D4", "E1");
        temp.get("g2").addPath(new Path("E1", Direction.EDGE, temp.get("f2")));
        temp.get("g2").addPath(new Path("D1", Direction.VERTEX, temp.get("f1")));	
        temp.get("g2").addPath(new Path("E2", Direction.EDGE, temp.get("g1")));	
        temp.get("g2").addPath(new Path("D2", Direction.VERTEX, temp.get("h1")));	
        temp.get("g2").addPath(new Path("E3", Direction.EDGE, temp.get("h2")));
        temp.get("g2").addPath(new Path("D3", Direction.VERTEX, temp.get("h3")));
        temp.get("g2").addPath(new Path("E4", Direction.EDGE, temp.get("g3")));
        temp.get("g2").addPath(new Path("D4", Direction.VERTEX, temp.get("f3")));
        temp.get("g2").linkPaths("E1", "D1");
        temp.get("g2").linkPaths("D1", "E2");
        temp.get("g2").linkPaths("E2", "D2");
        temp.get("g2").linkPaths("D2", "E3");
        temp.get("g2").linkPaths("E3", "D3");
        temp.get("g2").linkPaths("D3", "E4");
        temp.get("g2").linkPaths("E4", "D4");
        temp.get("g2").linkPaths("D4", "E1");
        temp.get("g3").addPath(new Path("E1", Direction.EDGE, temp.get("f3")));
        temp.get("g3").addPath(new Path("D1", Direction.VERTEX, temp.get("f2")));
        temp.get("g3").addPath(new Path("E2", Direction.EDGE, temp.get("g2")));
        temp.get("g3").addPath(new Path("D2", Direction.VERTEX, temp.get("h2")));
        temp.get("g3").addPath(new Path("E3", Direction.EDGE, temp.get("h3")));
        temp.get("g3").addPath(new Path("D3", Direction.VERTEX, temp.get("h4")));
        temp.get("g3").addPath(new Path("E4", Direction.EDGE, temp.get("g4")));
        temp.get("g3").addPath(new Path("D4", Direction.VERTEX, temp.get("f4")));
        temp.get("g3").linkPaths("E1", "D1");
        temp.get("g3").linkPaths("D1", "E2");
        temp.get("g3").linkPaths("E2", "D2");
        temp.get("g3").linkPaths("D2", "E3");
        temp.get("g3").linkPaths("E3", "D3");
        temp.get("g3").linkPaths("D3", "E4");
        temp.get("g3").linkPaths("E4", "D4");
        temp.get("g3").linkPaths("D4", "E1");
        temp.get("g4").addPath(new Path("E1", Direction.EDGE, temp.get("f4")));
        temp.get("g4").addPath(new Path("D1", Direction.VERTEX, temp.get("f3")));
        temp.get("g4").addPath(new Path("E2", Direction.EDGE, temp.get("g3")));
        temp.get("g4").addPath(new Path("D2", Direction.VERTEX, temp.get("h3")));
        temp.get("g4").addPath(new Path("E3", Direction.EDGE, temp.get("h4")));
        temp.get("g4").addPath(new Path("D3", Direction.VERTEX, temp.get("h9")));
        temp.get("g4").addPath(new Path("E4", Direction.EDGE, temp.get("g9")));
        temp.get("g4").addPath(new Path("D4", Direction.VERTEX, temp.get("f9")));
        temp.get("g4").linkPaths("E1", "D1");
        temp.get("g4").linkPaths("D1", "E2");
        temp.get("g4").linkPaths("E2", "D2");
        temp.get("g4").linkPaths("D2", "E3");
        temp.get("g4").linkPaths("E3", "D3");
        temp.get("g4").linkPaths("D3", "E4");
        temp.get("g4").linkPaths("E4", "D4");
        temp.get("g4").linkPaths("D4", "E1");
        temp.get("g9").addPath(new Path("E1", Direction.EDGE, temp.get("f9")));
        temp.get("g9").addPath(new Path("D1", Direction.VERTEX, temp.get("f4")));
        temp.get("g9").addPath(new Path("E2", Direction.EDGE, temp.get("g4")));
        temp.get("g9").addPath(new Path("D2", Direction.VERTEX, temp.get("h4")));
        temp.get("g9").addPath(new Path("E3", Direction.EDGE, temp.get("h9")));
        temp.get("g9").addPath(new Path("D3", Direction.VERTEX, temp.get("h10")));
        temp.get("g9").addPath(new Path("E4", Direction.EDGE, temp.get("g10")));
        temp.get("g9").addPath(new Path("D4", Direction.VERTEX, temp.get("f10")));
        temp.get("g9").linkPaths("E1", "D1");
        temp.get("g9").linkPaths("D1", "E2");
        temp.get("g9").linkPaths("E2", "D2");
        temp.get("g9").linkPaths("D2", "E3");
        temp.get("g9").linkPaths("E3", "D3");
        temp.get("g9").linkPaths("D3", "E4");
        temp.get("g9").linkPaths("E4", "D4");
        temp.get("g9").linkPaths("D4", "E1");
        temp.get("g10").addPath(new Path("E1", Direction.EDGE, temp.get("f10")));
        temp.get("g10").addPath(new Path("D1", Direction.VERTEX, temp.get("f9")));
        temp.get("g10").addPath(new Path("E2", Direction.EDGE, temp.get("g9")));
        temp.get("g10").addPath(new Path("D2", Direction.VERTEX, temp.get("h9")));
        temp.get("g10").addPath(new Path("E3", Direction.EDGE, temp.get("h10")));
        temp.get("g10").addPath(new Path("D3", Direction.VERTEX, temp.get("h11")));
        temp.get("g10").addPath(new Path("E4", Direction.EDGE, temp.get("g11")));
        temp.get("g10").addPath(new Path("D4", Direction.VERTEX, temp.get("f11")));
        temp.get("g10").linkPaths("E1", "D1");
        temp.get("g10").linkPaths("D1", "E2");
        temp.get("g10").linkPaths("E2", "D2");
        temp.get("g10").linkPaths("D2", "E3");
        temp.get("g10").linkPaths("E3", "D3");
        temp.get("g10").linkPaths("D3", "E4");
        temp.get("g10").linkPaths("E4", "D4");
        temp.get("g10").linkPaths("D4", "E1");
        temp.get("g11").addPath(new Path("E1", Direction.EDGE, temp.get("f11")));
        temp.get("g11").addPath(new Path("D1", Direction.VERTEX, temp.get("f10")));
        temp.get("g11").addPath(new Path("E2", Direction.EDGE, temp.get("g10")));
        temp.get("g11").addPath(new Path("D2", Direction.VERTEX, temp.get("h10")));
        temp.get("g11").addPath(new Path("E3", Direction.EDGE, temp.get("h11")));
        temp.get("g11").addPath(new Path("D3", Direction.VERTEX, temp.get("h12")));
        temp.get("g11").addPath(new Path("E4", Direction.EDGE, temp.get("g12")));
        temp.get("g11").addPath(new Path("D4", Direction.VERTEX, temp.get("f12")));
        temp.get("g11").linkPaths("E1", "D1");
        temp.get("g11").linkPaths("D1", "E2");
        temp.get("g11").linkPaths("E2", "D2");
        temp.get("g11").linkPaths("D2", "E3");
        temp.get("g11").linkPaths("E3", "D3");
        temp.get("g11").linkPaths("D3", "E4");
        temp.get("g11").linkPaths("E4", "D4");
        temp.get("g11").linkPaths("D4", "E1");
        temp.get("g12").addPath(new Path("E1", Direction.EDGE, temp.get("f12")));
        temp.get("g12").addPath(new Path("D1", Direction.VERTEX, temp.get("f11")));
        temp.get("g12").addPath(new Path("E2", Direction.EDGE, temp.get("g11")));
        temp.get("g12").addPath(new Path("D2", Direction.VERTEX, temp.get("h11")));
        temp.get("g12").addPath(new Path("E3", Direction.EDGE, temp.get("h12")));
        temp.get("g12").addPath(new Path("D3", Direction.VERTEX, temp.get("h13")));
        temp.get("g12").addPath(new Path("E4", Direction.EDGE, temp.get("g13")));
        temp.get("g12").addPath(new Path("D4", Direction.VERTEX, temp.get("f13")));
        temp.get("g12").linkPaths("E1", "D1");
        temp.get("g12").linkPaths("D1", "E2");
        temp.get("g12").linkPaths("E2", "D2");
        temp.get("g12").linkPaths("D2", "E3");
        temp.get("g12").linkPaths("E3", "D3");
        temp.get("g12").linkPaths("D3", "E4");
        temp.get("g12").linkPaths("E4", "D4");
        temp.get("g12").linkPaths("D4", "E1");
        temp.get("h1").addPath(new Path("E1", Direction.EDGE, temp.get("g1")));
        temp.get("h1").addPath(new Path("D1", Direction.VERTEX, null));	//g0
        temp.get("h1").addPath(new Path("E2", Direction.EDGE, null));	//h0
        temp.get("h1").addPath(new Path("D2", Direction.VERTEX, null));	//i0
        temp.get("h1").addPath(new Path("E3", Direction.EDGE, null));   // i1
        temp.get("h1").addPath(new Path("D3", Direction.VERTEX, null)); // i2
        temp.get("h1").addPath(new Path("E4", Direction.EDGE, temp.get("h2")));
        temp.get("h1").addPath(new Path("D4", Direction.VERTEX, temp.get("g2")));
        temp.get("h1").linkPaths("E1", "D1");
        temp.get("h1").linkPaths("D1", "E2");
        temp.get("h1").linkPaths("E2", "D2");
        temp.get("h1").linkPaths("D2", "E3");
        temp.get("h1").linkPaths("E3", "D3");
        temp.get("h1").linkPaths("D3", "E4");
        temp.get("h1").linkPaths("E4", "D4");
        temp.get("h1").linkPaths("D4", "E1");
        temp.get("h2").addPath(new Path("E1", Direction.EDGE, temp.get("g2")));
        temp.get("h2").addPath(new Path("D1", Direction.VERTEX, temp.get("g1")));
        temp.get("h2").addPath(new Path("E2", Direction.EDGE, temp.get("h1")));
        temp.get("h2").addPath(new Path("D2", Direction.VERTEX, null));	//i1
        temp.get("h2").addPath(new Path("E3", Direction.EDGE, null));   // i2
        temp.get("h2").addPath(new Path("D3", Direction.VERTEX, null)); // i3
        temp.get("h2").addPath(new Path("E4", Direction.EDGE, temp.get("h3")));
        temp.get("h2").addPath(new Path("D4", Direction.VERTEX, temp.get("g3")));
        temp.get("h2").linkPaths("E1", "D1");
        temp.get("h2").linkPaths("D1", "E2");
        temp.get("h2").linkPaths("E2", "D2");
        temp.get("h2").linkPaths("D2", "E3");
        temp.get("h2").linkPaths("E3", "D3");
        temp.get("h2").linkPaths("D3", "E4");
        temp.get("h2").linkPaths("E4", "D4");
        temp.get("h2").linkPaths("D4", "E1");
        temp.get("h3").addPath(new Path("E1", Direction.EDGE, temp.get("g3")));
        temp.get("h3").addPath(new Path("D1", Direction.VERTEX, temp.get("g2")));
        temp.get("h3").addPath(new Path("E2", Direction.EDGE, temp.get("h2")));
        temp.get("h3").addPath(new Path("D2", Direction.VERTEX, null));	//i1
        temp.get("h3").addPath(new Path("E3", Direction.EDGE, null));   // i2
        temp.get("h3").addPath(new Path("D3", Direction.VERTEX, null)); // i3
        temp.get("h3").addPath(new Path("E4", Direction.EDGE, temp.get("h4")));
        temp.get("h3").addPath(new Path("D4", Direction.VERTEX, temp.get("g4")));
        temp.get("h3").linkPaths("E1", "D1");
        temp.get("h3").linkPaths("D1", "E2");
        temp.get("h3").linkPaths("E2", "D2");
        temp.get("h3").linkPaths("D2", "E3");
        temp.get("h3").linkPaths("E3", "D3");
        temp.get("h3").linkPaths("D3", "E4");
        temp.get("h3").linkPaths("E4", "D4");
        temp.get("h3").linkPaths("D4", "E1");
        temp.get("h4").addPath(new Path("E1", Direction.EDGE, temp.get("g4")));
        temp.get("h4").addPath(new Path("D1", Direction.VERTEX, temp.get("g3")));
        temp.get("h4").addPath(new Path("E2", Direction.EDGE, temp.get("h3")));
        temp.get("h4").addPath(new Path("D2", Direction.VERTEX, null));	//i1
        temp.get("h4").addPath(new Path("E3", Direction.EDGE, null));   // i2
        temp.get("h4").addPath(new Path("D3", Direction.VERTEX, null)); // i3
        temp.get("h4").addPath(new Path("E4", Direction.EDGE, temp.get("h9")));
        temp.get("h4").addPath(new Path("D4", Direction.VERTEX, temp.get("g9")));
        temp.get("h4").linkPaths("E1", "D1");
        temp.get("h4").linkPaths("D1", "E2");
        temp.get("h4").linkPaths("E2", "D2");
        temp.get("h4").linkPaths("D2", "E3");
        temp.get("h4").linkPaths("E3", "D3");
        temp.get("h4").linkPaths("D3", "E4");
        temp.get("h4").linkPaths("E4", "D4");
        temp.get("h4").linkPaths("D4", "E1");
        temp.get("h9").addPath(new Path("E1", Direction.EDGE, temp.get("g9")));
        temp.get("h9").addPath(new Path("D1", Direction.VERTEX, temp.get("g4")));
        temp.get("h9").addPath(new Path("E2", Direction.EDGE, temp.get("h4")));
        temp.get("h9").addPath(new Path("D2", Direction.VERTEX, null));	//i1
        temp.get("h9").addPath(new Path("E3", Direction.EDGE, null));   // i2
        temp.get("h9").addPath(new Path("D3", Direction.VERTEX, null)); // i3
        temp.get("h9").addPath(new Path("E4", Direction.EDGE, temp.get("h10")));
        temp.get("h9").addPath(new Path("D4", Direction.VERTEX, temp.get("g10")));
        temp.get("h9").linkPaths("E1", "D1");
        temp.get("h9").linkPaths("D1", "E2");
        temp.get("h9").linkPaths("E2", "D2");
        temp.get("h9").linkPaths("D2", "E3");
        temp.get("h9").linkPaths("E3", "D3");
        temp.get("h9").linkPaths("D3", "E4");
        temp.get("h9").linkPaths("E4", "D4");
        temp.get("h9").linkPaths("D4", "E1");
        temp.get("h10").addPath(new Path("E1", Direction.EDGE, temp.get("g10")));
        temp.get("h10").addPath(new Path("D1", Direction.VERTEX, temp.get("g9")));
        temp.get("h10").addPath(new Path("E2", Direction.EDGE, temp.get("h9")));
        temp.get("h10").addPath(new Path("D2", Direction.VERTEX, null));	//i1
        temp.get("h10").addPath(new Path("E3", Direction.EDGE, null));   // i2
        temp.get("h10").addPath(new Path("D3", Direction.VERTEX, null)); // i3
        temp.get("h10").addPath(new Path("E4", Direction.EDGE, temp.get("h11")));
        temp.get("h10").addPath(new Path("D4", Direction.VERTEX, temp.get("g11")));
        temp.get("h10").linkPaths("E1", "D1");
        temp.get("h10").linkPaths("D1", "E2");
        temp.get("h10").linkPaths("E2", "D2");
        temp.get("h10").linkPaths("D2", "E3");
        temp.get("h10").linkPaths("E3", "D3");
        temp.get("h10").linkPaths("D3", "E4");
        temp.get("h10").linkPaths("E4", "D4");
        temp.get("h10").linkPaths("D4", "E1");
        temp.get("h11").addPath(new Path("E1", Direction.EDGE, temp.get("g11")));
        temp.get("h11").addPath(new Path("D1", Direction.VERTEX, temp.get("g10")));
        temp.get("h11").addPath(new Path("E2", Direction.EDGE, temp.get("h10")));
        temp.get("h11").addPath(new Path("D2", Direction.VERTEX, null));	//i1
        temp.get("h11").addPath(new Path("E3", Direction.EDGE, null));   // i2
        temp.get("h11").addPath(new Path("D3", Direction.VERTEX, null)); // i3
        temp.get("h11").addPath(new Path("E4", Direction.EDGE, temp.get("h12")));
        temp.get("h11").addPath(new Path("D4", Direction.VERTEX, temp.get("g12")));
        temp.get("h11").linkPaths("E1", "D1");
        temp.get("h11").linkPaths("D1", "E2");
        temp.get("h11").linkPaths("E2", "D2");
        temp.get("h11").linkPaths("D2", "E3");
        temp.get("h11").linkPaths("E3", "D3");
        temp.get("h11").linkPaths("D3", "E4");
        temp.get("h11").linkPaths("E4", "D4");
        temp.get("h11").linkPaths("D4", "E1");
        temp.get("h12").addPath(new Path("E1", Direction.EDGE, temp.get("g12")));
        temp.get("h12").addPath(new Path("D1", Direction.VERTEX, temp.get("g11")));
        temp.get("h12").addPath(new Path("E2", Direction.EDGE, temp.get("h11")));
        temp.get("h12").addPath(new Path("D2", Direction.VERTEX, null));	//i1
        temp.get("h12").addPath(new Path("E3", Direction.EDGE, null));   // i2
        temp.get("h12").addPath(new Path("D3", Direction.VERTEX, null)); // i3
        temp.get("h12").addPath(new Path("E4", Direction.EDGE, null));
        temp.get("h12").addPath(new Path("D4", Direction.VERTEX, null));
        temp.get("h12").linkPaths("E1", "D1");
        temp.get("h12").linkPaths("D1", "E2");
        temp.get("h12").linkPaths("E2", "D2");
        temp.get("h12").linkPaths("D2", "E3");
        temp.get("h12").linkPaths("E3", "D3");
        temp.get("h12").linkPaths("D3", "E4");
        temp.get("h12").linkPaths("E4", "D4");
        temp.get("h12").linkPaths("D4", "E1");
        
        temp.get("i8").addPath(new Path("E1", Direction.EDGE, temp.get("j8")));
        temp.get("i8").addPath(new Path("D1", Direction.VERTEX, null));	//j9 ++
        temp.get("i8").addPath(new Path("E2", Direction.EDGE, null));	//i9 ++
        temp.get("i8").addPath(new Path("D2", Direction.VERTEX, null));	//d9 ++
        temp.get("i8").addPath(new Path("E3", Direction.EDGE, temp.get("d8")));   // d8
        temp.get("i8").addPath(new Path("D3", Direction.VERTEX, temp.get("d7"))); // d7
        temp.get("i8").addPath(new Path("E4", Direction.EDGE, temp.get("i7")));
        temp.get("i8").addPath(new Path("D4", Direction.VERTEX, temp.get("j7")));
        temp.get("i8").linkPaths("E1", "D1");
        temp.get("i8").linkPaths("D1", "E2");
        temp.get("i8").linkPaths("E2", "D2");
        temp.get("i8").linkPaths("D2", "E3");
        temp.get("i8").linkPaths("E3", "D3");
        temp.get("i8").linkPaths("D3", "E4");
        temp.get("i8").linkPaths("E4", "D4");
        temp.get("i8").linkPaths("D4", "E1");
        temp.get("i7").addPath(new Path("E1", Direction.EDGE, temp.get("j7")));
        temp.get("i7").addPath(new Path("D1", Direction.VERTEX, temp.get("j8")));	//j8 ++
        temp.get("i7").addPath(new Path("E2", Direction.EDGE, temp.get("i8")));	//i8 ++
        temp.get("i7").addPath(new Path("D2", Direction.VERTEX, temp.get("d8")));	//d8 ++
        temp.get("i7").addPath(new Path("E3", Direction.EDGE, temp.get("d7")));   // d8
        temp.get("i7").addPath(new Path("D3", Direction.VERTEX, temp.get("d6"))); // d7
        temp.get("i7").addPath(new Path("E4", Direction.EDGE, temp.get("i6")));
        temp.get("i7").addPath(new Path("D4", Direction.VERTEX, temp.get("j6")));
        temp.get("i7").linkPaths("E1", "D1");
        temp.get("i7").linkPaths("D1", "E2");
        temp.get("i7").linkPaths("E2", "D2");
        temp.get("i7").linkPaths("D2", "E3");
        temp.get("i7").linkPaths("E3", "D3");
        temp.get("i7").linkPaths("D3", "E4");
        temp.get("i7").linkPaths("E4", "D4");
        temp.get("i7").linkPaths("D4", "E1");
        temp.get("i6").addPath(new Path("E1", Direction.EDGE, temp.get("j6")));
        temp.get("i6").addPath(new Path("D1", Direction.VERTEX, temp.get("j7")));	//j8 ++
        temp.get("i6").addPath(new Path("E2", Direction.EDGE, temp.get("i7")));	//i8 ++
        temp.get("i6").addPath(new Path("D2", Direction.VERTEX, temp.get("d7")));	//d8 ++
        temp.get("i6").addPath(new Path("E3", Direction.EDGE, temp.get("d6")));   // d8
        temp.get("i6").addPath(new Path("D3", Direction.VERTEX, temp.get("d5"))); // d7
        temp.get("i6").addPath(new Path("E4", Direction.EDGE, temp.get("i5")));
        temp.get("i6").addPath(new Path("D4", Direction.VERTEX, temp.get("j5")));
        temp.get("i6").linkPaths("E1", "D1");
        temp.get("i6").linkPaths("D1", "E2");
        temp.get("i6").linkPaths("E2", "D2");
        temp.get("i6").linkPaths("D2", "E3");
        temp.get("i6").linkPaths("E3", "D3");
        temp.get("i6").linkPaths("D3", "E4");
        temp.get("i6").linkPaths("E4", "D4");
        temp.get("i6").linkPaths("D4", "E1");
        temp.get("i5").addPath(new Path("E1", Direction.EDGE, temp.get("j5")));
        temp.get("i5").addPath(new Path("D1", Direction.VERTEX, temp.get("j6")));	//j8 ++
        temp.get("i5").addPath(new Path("E2", Direction.EDGE, temp.get("i6")));	//i8 ++
        temp.get("i5").addPath(new Path("D2", Direction.VERTEX, temp.get("d6")));	//d8 ++
        temp.get("i5").addPath(new Path("E3", Direction.EDGE, temp.get("d5")));   // d8
        temp.get("i5").addPath(new Path("D3", Direction.VERTEX, temp.get("d4"))); // d7
        temp.get("i5").addPath(new Path("E4", Direction.EDGE, temp.get("e4")));
        temp.get("i5").addPath(new Path("D4", Direction.VERTEX, temp.get("j9")));
        temp.get("i5").getPath("D3").getPathData().addPosition((temp.get("e9")));
        temp.get("i5").getPath("D3").getPathData().addPosition((temp.get("i9")));
        temp.get("i5").linkPaths("E1", "D1");
        temp.get("i5").linkPaths("D1", "E2");
        temp.get("i5").linkPaths("E2", "D2");
        temp.get("i5").linkPaths("D2", "E3");
        temp.get("i5").linkPaths("E3", "D3");
        temp.get("i5").linkPaths("D3", "E4");
        temp.get("i5").linkPaths("E4", "D4");
        temp.get("i5").linkPaths("D4", "E1");
        temp.get("i9").addPath(new Path("E1", Direction.EDGE, temp.get("j9")));
        temp.get("i9").addPath(new Path("D1", Direction.VERTEX, temp.get("j5")));	//j8 ++
        temp.get("i9").addPath(new Path("E2", Direction.EDGE, temp.get("i5")));	//i8 ++
        temp.get("i9").addPath(new Path("D2", Direction.VERTEX, temp.get("d5")));	//d8 ++
        temp.get("i9").addPath(new Path("E3", Direction.EDGE, temp.get("e9")));   // d8
        temp.get("i9").addPath(new Path("D3", Direction.VERTEX, temp.get("e10"))); // d7
        temp.get("i9").addPath(new Path("E4", Direction.EDGE, temp.get("i10")));
        temp.get("i9").addPath(new Path("D4", Direction.VERTEX, temp.get("j10")));
        temp.get("i9").getPath("D2").getPathData().addPosition((temp.get("d4")));
        temp.get("i9").getPath("D2").getPathData().addPosition((temp.get("e4")));
        temp.get("i9").linkPaths("E1", "D1");
        temp.get("i9").linkPaths("D1", "E2");
        temp.get("i9").linkPaths("E2", "D2");
        temp.get("i9").linkPaths("D2", "E3");
        temp.get("i9").linkPaths("E3", "D3");
        temp.get("i9").linkPaths("D3", "E4");
        temp.get("i9").linkPaths("E4", "D4");
        temp.get("i9").linkPaths("D4", "E1");
        temp.get("i10").addPath(new Path("E1", Direction.EDGE, temp.get("j10")));
        temp.get("i10").addPath(new Path("D1", Direction.VERTEX, temp.get("j9")));	//j8 ++
        temp.get("i10").addPath(new Path("E2", Direction.EDGE, temp.get("i9")));	//i8 ++
        temp.get("i10").addPath(new Path("D2", Direction.VERTEX, temp.get("e9")));	//d8 ++
        temp.get("i10").addPath(new Path("E3", Direction.EDGE, temp.get("e10")));   // d8
        temp.get("i10").addPath(new Path("D3", Direction.VERTEX, temp.get("e11"))); // d7
        temp.get("i10").addPath(new Path("E4", Direction.EDGE, temp.get("i11")));
        temp.get("i10").addPath(new Path("D4", Direction.VERTEX, temp.get("j11")));
        temp.get("i10").linkPaths("E1", "D1");
        temp.get("i10").linkPaths("D1", "E2");
        temp.get("i10").linkPaths("E2", "D2");
        temp.get("i10").linkPaths("D2", "E3");
        temp.get("i10").linkPaths("E3", "D3");
        temp.get("i10").linkPaths("D3", "E4");
        temp.get("i10").linkPaths("E4", "D4");
        temp.get("i10").linkPaths("D4", "E1");
        temp.get("i11").addPath(new Path("E1", Direction.EDGE, temp.get("j11")));
        temp.get("i11").addPath(new Path("D1", Direction.VERTEX, temp.get("j10")));	//j8 ++
        temp.get("i11").addPath(new Path("E2", Direction.EDGE, temp.get("i10")));	//i8 ++
        temp.get("i11").addPath(new Path("D2", Direction.VERTEX, temp.get("e10")));	//d8 ++
        temp.get("i11").addPath(new Path("E3", Direction.EDGE, temp.get("e11")));   // d8
        temp.get("i11").addPath(new Path("D3", Direction.VERTEX, temp.get("e12"))); // d7
        temp.get("i11").addPath(new Path("E4", Direction.EDGE, temp.get("i12")));
        temp.get("i11").addPath(new Path("D4", Direction.VERTEX, temp.get("j12")));
        temp.get("i11").linkPaths("E1", "D1");
        temp.get("i11").linkPaths("D1", "E2");
        temp.get("i11").linkPaths("E2", "D2");
        temp.get("i11").linkPaths("D2", "E3");
        temp.get("i11").linkPaths("E3", "D3");
        temp.get("i11").linkPaths("D3", "E4");
        temp.get("i11").linkPaths("E4", "D4");
        temp.get("i11").linkPaths("D4", "E1");
        temp.get("i12").addPath(new Path("E1", Direction.EDGE, temp.get("j12")));
        temp.get("i12").addPath(new Path("D1", Direction.VERTEX, temp.get("j11")));	//j8 ++
        temp.get("i12").addPath(new Path("E2", Direction.EDGE, temp.get("i11")));	//i8 ++
        temp.get("i12").addPath(new Path("D2", Direction.VERTEX, temp.get("e11")));	//d8 ++
        temp.get("i12").addPath(new Path("E3", Direction.EDGE, temp.get("e12")));   // d8
        temp.get("i12").addPath(new Path("D3", Direction.VERTEX, null)); // d7
        temp.get("i12").addPath(new Path("E4", Direction.EDGE, null));
        temp.get("i12").addPath(new Path("D4", Direction.VERTEX, null));
        temp.get("i12").linkPaths("E1", "D1");
        temp.get("i12").linkPaths("D1", "E2");
        temp.get("i12").linkPaths("E2", "D2");
        temp.get("i12").linkPaths("D2", "E3");
        temp.get("i12").linkPaths("E3", "D3");
        temp.get("i12").linkPaths("D3", "E4");
        temp.get("i12").linkPaths("E4", "D4");
        temp.get("i12").linkPaths("D4", "E1");
        // j
        temp.get("j8").addPath(new Path("E1", Direction.EDGE, temp.get("k8")));
        temp.get("j8").addPath(new Path("D1", Direction.VERTEX, null));	//k9 ++
        temp.get("j8").addPath(new Path("E2", Direction.EDGE, null));	//j9 ++
        temp.get("j8").addPath(new Path("D2", Direction.VERTEX, null));	//i9 ++
        temp.get("j8").addPath(new Path("E3", Direction.EDGE, temp.get("i8")));   // d8
        temp.get("j8").addPath(new Path("D3", Direction.VERTEX, temp.get("i7"))); // d7
        temp.get("j8").addPath(new Path("E4", Direction.EDGE, temp.get("j7")));
        temp.get("j8").addPath(new Path("D4", Direction.VERTEX, temp.get("k7")));
        temp.get("j8").linkPaths("E1", "D1");
        temp.get("j8").linkPaths("D1", "E2");
        temp.get("j8").linkPaths("E2", "D2");
        temp.get("j8").linkPaths("D2", "E3");
        temp.get("j8").linkPaths("E3", "D3");
        temp.get("j8").linkPaths("D3", "E4");
        temp.get("j8").linkPaths("E4", "D4");
        temp.get("j8").linkPaths("D4", "E1");
        temp.get("j7").addPath(new Path("E1", Direction.EDGE, temp.get("k7")));
        temp.get("j7").addPath(new Path("D1", Direction.VERTEX, temp.get("k8")));
        temp.get("j7").addPath(new Path("E2", Direction.EDGE, temp.get("j8")));
        temp.get("j7").addPath(new Path("D2", Direction.VERTEX, temp.get("i8")));
        temp.get("j7").addPath(new Path("E3", Direction.EDGE, temp.get("i7")));
        temp.get("j7").addPath(new Path("D3", Direction.VERTEX, temp.get("i6")));
        temp.get("j7").addPath(new Path("E4", Direction.EDGE, temp.get("j6")));
        temp.get("j7").addPath(new Path("D4", Direction.VERTEX, temp.get("k6")));
        temp.get("j7").linkPaths("E1", "D1");
        temp.get("j7").linkPaths("D1", "E2");
        temp.get("j7").linkPaths("E2", "D2");
        temp.get("j7").linkPaths("D2", "E3");
        temp.get("j7").linkPaths("E3", "D3");
        temp.get("j7").linkPaths("D3", "E4");
        temp.get("j7").linkPaths("E4", "D4");
        temp.get("j7").linkPaths("D4", "E1");
        temp.get("j6").addPath(new Path("E1", Direction.EDGE, temp.get("k6")));
        temp.get("j6").addPath(new Path("D1", Direction.VERTEX, temp.get("k7")));
        temp.get("j6").addPath(new Path("E2", Direction.EDGE, temp.get("j7")));
        temp.get("j6").addPath(new Path("D2", Direction.VERTEX, temp.get("i7")));
        temp.get("j6").addPath(new Path("E3", Direction.EDGE, temp.get("i6")));
        temp.get("j6").addPath(new Path("D3", Direction.VERTEX, temp.get("i5")));
        temp.get("j6").addPath(new Path("E4", Direction.EDGE, temp.get("j5")));
        temp.get("j6").addPath(new Path("D4", Direction.VERTEX, temp.get("k5")));
        temp.get("j6").linkPaths("E1", "D1");
        temp.get("j6").linkPaths("D1", "E2");
        temp.get("j6").linkPaths("E2", "D2");
        temp.get("j6").linkPaths("D2", "E3");
        temp.get("j6").linkPaths("E3", "D3");
        temp.get("j6").linkPaths("D3", "E4");
        temp.get("j6").linkPaths("E4", "D4");
        temp.get("j6").linkPaths("D4", "E1");
        temp.get("j5").addPath(new Path("E1", Direction.EDGE, temp.get("k5")));
        temp.get("j5").addPath(new Path("D1", Direction.VERTEX, temp.get("k6")));
        temp.get("j5").addPath(new Path("E2", Direction.EDGE, temp.get("j6")));
        temp.get("j5").addPath(new Path("D2", Direction.VERTEX, temp.get("i6")));
        temp.get("j5").addPath(new Path("E3", Direction.EDGE, temp.get("i5")));
        temp.get("j5").addPath(new Path("D3", Direction.VERTEX, temp.get("i9")));
        temp.get("j5").addPath(new Path("E4", Direction.EDGE, temp.get("j9")));
        temp.get("j5").addPath(new Path("D4", Direction.VERTEX, temp.get("k9")));
        temp.get("j5").linkPaths("E1", "D1");
        temp.get("j5").linkPaths("D1", "E2");
        temp.get("j5").linkPaths("E2", "D2");
        temp.get("j5").linkPaths("D2", "E3");
        temp.get("j5").linkPaths("E3", "D3");
        temp.get("j5").linkPaths("D3", "E4");
        temp.get("j5").linkPaths("E4", "D4");
        temp.get("j5").linkPaths("D4", "E1");
        temp.get("j9").addPath(new Path("E1", Direction.EDGE, temp.get("k9")));
        temp.get("j9").addPath(new Path("D1", Direction.VERTEX, temp.get("k5")));
        temp.get("j9").addPath(new Path("E2", Direction.EDGE, temp.get("j5")));
        temp.get("j9").addPath(new Path("D2", Direction.VERTEX, temp.get("i5")));
        temp.get("j9").addPath(new Path("E3", Direction.EDGE, temp.get("i9")));
        temp.get("j9").addPath(new Path("D3", Direction.VERTEX, temp.get("i10")));
        temp.get("j9").addPath(new Path("E4", Direction.EDGE, temp.get("j10")));
        temp.get("j9").addPath(new Path("D4", Direction.VERTEX, temp.get("k10")));
        temp.get("j9").linkPaths("E1", "D1");
        temp.get("j9").linkPaths("D1", "E2");
        temp.get("j9").linkPaths("E2", "D2");
        temp.get("j9").linkPaths("D2", "E3");
        temp.get("j9").linkPaths("E3", "D3");
        temp.get("j9").linkPaths("D3", "E4");
        temp.get("j9").linkPaths("E4", "D4");
        temp.get("j9").linkPaths("D4", "E1");
        temp.get("j10").addPath(new Path("E1", Direction.EDGE, temp.get("k10")));
        temp.get("j10").addPath(new Path("D1", Direction.VERTEX, temp.get("k9")));
        temp.get("j10").addPath(new Path("E2", Direction.EDGE, temp.get("j9")));
        temp.get("j10").addPath(new Path("D2", Direction.VERTEX, temp.get("i9")));
        temp.get("j10").addPath(new Path("E3", Direction.EDGE, temp.get("i10")));
        temp.get("j10").addPath(new Path("D3", Direction.VERTEX, temp.get("i11")));
        temp.get("j10").addPath(new Path("E4", Direction.EDGE, temp.get("j11")));
        temp.get("j10").addPath(new Path("D4", Direction.VERTEX, temp.get("k11")));
        temp.get("j10").linkPaths("E1", "D1");
        temp.get("j10").linkPaths("D1", "E2");
        temp.get("j10").linkPaths("E2", "D2");
        temp.get("j10").linkPaths("D2", "E3");
        temp.get("j10").linkPaths("E3", "D3");
        temp.get("j10").linkPaths("D3", "E4");
        temp.get("j10").linkPaths("E4", "D4");
        temp.get("j10").linkPaths("D4", "E1");
        temp.get("j11").addPath(new Path("E1", Direction.EDGE, temp.get("k11")));
        temp.get("j11").addPath(new Path("D1", Direction.VERTEX, temp.get("k10")));
        temp.get("j11").addPath(new Path("E2", Direction.EDGE, temp.get("j10")));
        temp.get("j11").addPath(new Path("D2", Direction.VERTEX, temp.get("i10")));
        temp.get("j11").addPath(new Path("E3", Direction.EDGE, temp.get("i11")));
        temp.get("j11").addPath(new Path("D3", Direction.VERTEX, temp.get("i12")));
        temp.get("j11").addPath(new Path("E4", Direction.EDGE, temp.get("j12")));
        temp.get("j11").addPath(new Path("D4", Direction.VERTEX, temp.get("k12")));
        temp.get("j11").linkPaths("E1", "D1");
        temp.get("j11").linkPaths("D1", "E2");
        temp.get("j11").linkPaths("E2", "D2");
        temp.get("j11").linkPaths("D2", "E3");
        temp.get("j11").linkPaths("E3", "D3");
        temp.get("j11").linkPaths("D3", "E4");
        temp.get("j11").linkPaths("E4", "D4");
        temp.get("j11").linkPaths("D4", "E1");
        temp.get("j12").addPath(new Path("E1", Direction.EDGE, temp.get("k12")));
        temp.get("j12").addPath(new Path("D1", Direction.VERTEX, temp.get("k11")));
        temp.get("j12").addPath(new Path("E2", Direction.EDGE, temp.get("j11")));
        temp.get("j12").addPath(new Path("D2", Direction.VERTEX, temp.get("i11")));
        temp.get("j12").addPath(new Path("E3", Direction.EDGE, temp.get("i12")));
        temp.get("j12").addPath(new Path("D3", Direction.VERTEX, null));
        temp.get("j12").addPath(new Path("E4", Direction.EDGE, null));
        temp.get("j12").addPath(new Path("D4", Direction.VERTEX, null));
        temp.get("j12").linkPaths("E1", "D1");
        temp.get("j12").linkPaths("D1", "E2");
        temp.get("j12").linkPaths("E2", "D2");
        temp.get("j12").linkPaths("D2", "E3");
        temp.get("j12").linkPaths("E3", "D3");
        temp.get("j12").linkPaths("D3", "E4");
        temp.get("j12").linkPaths("E4", "D4");
        temp.get("j12").linkPaths("D4", "E1");
        // k
        temp.get("k8").addPath(new Path("E1", Direction.EDGE, temp.get("l8")));
        temp.get("k8").addPath(new Path("D1", Direction.VERTEX, null));	//l9 ++
        temp.get("k8").addPath(new Path("E2", Direction.EDGE, null));	//k9 ++
        temp.get("k8").addPath(new Path("D2", Direction.VERTEX, null));	//j9 ++
        temp.get("k8").addPath(new Path("E3", Direction.EDGE, temp.get("j8")));   
        temp.get("k8").addPath(new Path("D3", Direction.VERTEX, temp.get("j7"))); 
        temp.get("k8").addPath(new Path("E4", Direction.EDGE, temp.get("k7")));
        temp.get("k8").addPath(new Path("D4", Direction.VERTEX, temp.get("l7")));
        temp.get("k8").linkPaths("E1", "D1");
        temp.get("k8").linkPaths("D1", "E2");
        temp.get("k8").linkPaths("E2", "D2");
        temp.get("k8").linkPaths("D2", "E3");
        temp.get("k8").linkPaths("E3", "D3");
        temp.get("k8").linkPaths("D3", "E4");
        temp.get("k8").linkPaths("E4", "D4");
        temp.get("k8").linkPaths("D4", "E1");
        temp.get("k7").addPath(new Path("E1", Direction.EDGE, temp.get("l7")));
        temp.get("k7").addPath(new Path("D1", Direction.VERTEX, temp.get("l8")));
        temp.get("k7").addPath(new Path("E2", Direction.EDGE, temp.get("k8")));
        temp.get("k7").addPath(new Path("D2", Direction.VERTEX, temp.get("j8")));
        temp.get("k7").addPath(new Path("E3", Direction.EDGE, temp.get("j7")));
        temp.get("k7").addPath(new Path("D3", Direction.VERTEX, temp.get("j6")));
        temp.get("k7").addPath(new Path("E4", Direction.EDGE, temp.get("k6")));
        temp.get("k7").addPath(new Path("D4", Direction.VERTEX, temp.get("l6")));
        temp.get("k7").linkPaths("E1", "D1");
        temp.get("k7").linkPaths("D1", "E2");
        temp.get("k7").linkPaths("E2", "D2");
        temp.get("k7").linkPaths("D2", "E3");
        temp.get("k7").linkPaths("E3", "D3");
        temp.get("k7").linkPaths("D3", "E4");
        temp.get("k7").linkPaths("E4", "D4");
        temp.get("k7").linkPaths("D4", "E1");
        temp.get("k6").addPath(new Path("E1", Direction.EDGE, temp.get("l6")));
        temp.get("k6").addPath(new Path("D1", Direction.VERTEX, temp.get("l7")));
        temp.get("k6").addPath(new Path("E2", Direction.EDGE, temp.get("k7")));
        temp.get("k6").addPath(new Path("D2", Direction.VERTEX, temp.get("j7")));
        temp.get("k6").addPath(new Path("E3", Direction.EDGE, temp.get("j6")));
        temp.get("k6").addPath(new Path("D3", Direction.VERTEX, temp.get("j5")));
        temp.get("k6").addPath(new Path("E4", Direction.EDGE, temp.get("k5")));
        temp.get("k6").addPath(new Path("D4", Direction.VERTEX, temp.get("l5")));
        temp.get("k6").linkPaths("E1", "D1");
        temp.get("k6").linkPaths("D1", "E2");
        temp.get("k6").linkPaths("E2", "D2");
        temp.get("k6").linkPaths("D2", "E3");
        temp.get("k6").linkPaths("E3", "D3");
        temp.get("k6").linkPaths("D3", "E4");
        temp.get("k6").linkPaths("E4", "D4");
        temp.get("k6").linkPaths("D4", "E1");
        temp.get("k5").addPath(new Path("E1", Direction.EDGE, temp.get("l5")));
        temp.get("k5").addPath(new Path("D1", Direction.VERTEX, temp.get("l6")));
        temp.get("k5").addPath(new Path("E2", Direction.EDGE, temp.get("k6")));
        temp.get("k5").addPath(new Path("D2", Direction.VERTEX, temp.get("j6")));
        temp.get("k5").addPath(new Path("E3", Direction.EDGE, temp.get("j5")));
        temp.get("k5").addPath(new Path("D3", Direction.VERTEX, temp.get("j9")));
        temp.get("k5").addPath(new Path("E4", Direction.EDGE, temp.get("k9")));
        temp.get("k5").addPath(new Path("D4", Direction.VERTEX, temp.get("l9")));
        temp.get("k5").linkPaths("E1", "D1");
        temp.get("k5").linkPaths("D1", "E2");
        temp.get("k5").linkPaths("E2", "D2");
        temp.get("k5").linkPaths("D2", "E3");
        temp.get("k5").linkPaths("E3", "D3");
        temp.get("k5").linkPaths("D3", "E4");
        temp.get("k5").linkPaths("E4", "D4");
        temp.get("k5").linkPaths("D4", "E1");
        temp.get("k9").addPath(new Path("E1", Direction.EDGE, temp.get("l9")));
        temp.get("k9").addPath(new Path("D1", Direction.VERTEX, temp.get("l5")));
        temp.get("k9").addPath(new Path("E2", Direction.EDGE, temp.get("k5")));
        temp.get("k9").addPath(new Path("D2", Direction.VERTEX, temp.get("j5")));
        temp.get("k9").addPath(new Path("E3", Direction.EDGE, temp.get("j9")));
        temp.get("k9").addPath(new Path("D3", Direction.VERTEX, temp.get("j10")));
        temp.get("k9").addPath(new Path("E4", Direction.EDGE, temp.get("k10")));
        temp.get("k9").addPath(new Path("D4", Direction.VERTEX, temp.get("l10")));
        temp.get("k9").linkPaths("E1", "D1");
        temp.get("k9").linkPaths("D1", "E2");
        temp.get("k9").linkPaths("E2", "D2");
        temp.get("k9").linkPaths("D2", "E3");
        temp.get("k9").linkPaths("E3", "D3");
        temp.get("k9").linkPaths("D3", "E4");
        temp.get("k9").linkPaths("E4", "D4");
        temp.get("k9").linkPaths("D4", "E1");
        temp.get("k10").addPath(new Path("E1", Direction.EDGE, temp.get("l10")));
        temp.get("k10").addPath(new Path("D1", Direction.VERTEX, temp.get("l9")));
        temp.get("k10").addPath(new Path("E2", Direction.EDGE, temp.get("k9")));
        temp.get("k10").addPath(new Path("D2", Direction.VERTEX, temp.get("j9")));
        temp.get("k10").addPath(new Path("E3", Direction.EDGE, temp.get("j10")));
        temp.get("k10").addPath(new Path("D3", Direction.VERTEX, temp.get("j11")));
        temp.get("k10").addPath(new Path("E4", Direction.EDGE, temp.get("k11")));
        temp.get("k10").addPath(new Path("D4", Direction.VERTEX, temp.get("l11")));
        temp.get("k10").linkPaths("E1", "D1");
        temp.get("k10").linkPaths("D1", "E2");
        temp.get("k10").linkPaths("E2", "D2");
        temp.get("k10").linkPaths("D2", "E3");
        temp.get("k10").linkPaths("E3", "D3");
        temp.get("k10").linkPaths("D3", "E4");
        temp.get("k10").linkPaths("E4", "D4");
        temp.get("k10").linkPaths("D4", "E1");
        temp.get("k11").addPath(new Path("E1", Direction.EDGE, temp.get("l11")));
        temp.get("k11").addPath(new Path("D1", Direction.VERTEX, temp.get("l10")));
        temp.get("k11").addPath(new Path("E2", Direction.EDGE, temp.get("k10")));
        temp.get("k11").addPath(new Path("D2", Direction.VERTEX, temp.get("j10")));
        temp.get("k11").addPath(new Path("E3", Direction.EDGE, temp.get("j11")));
        temp.get("k11").addPath(new Path("D3", Direction.VERTEX, temp.get("j12")));
        temp.get("k11").addPath(new Path("E4", Direction.EDGE, temp.get("k12")));
        temp.get("k11").addPath(new Path("D4", Direction.VERTEX, temp.get("l12")));
        temp.get("k11").linkPaths("E1", "D1");
        temp.get("k11").linkPaths("D1", "E2");
        temp.get("k11").linkPaths("E2", "D2");
        temp.get("k11").linkPaths("D2", "E3");
        temp.get("k11").linkPaths("E3", "D3");
        temp.get("k11").linkPaths("D3", "E4");
        temp.get("k11").linkPaths("E4", "D4");
        temp.get("k11").linkPaths("D4", "E1");
        temp.get("k12").addPath(new Path("E1", Direction.EDGE, temp.get("l12")));
        temp.get("k12").addPath(new Path("D1", Direction.VERTEX, temp.get("l11")));
        temp.get("k12").addPath(new Path("E2", Direction.EDGE, temp.get("k11")));
        temp.get("k12").addPath(new Path("D2", Direction.VERTEX, temp.get("j11")));
        temp.get("k12").addPath(new Path("E3", Direction.EDGE, temp.get("j12")));
        temp.get("k12").addPath(new Path("D3", Direction.VERTEX, null));
        temp.get("k12").addPath(new Path("E4", Direction.EDGE, null));
        temp.get("k12").addPath(new Path("D4", Direction.VERTEX, null));
        temp.get("k12").linkPaths("E1", "D1");
        temp.get("k12").linkPaths("D1", "E2");
        temp.get("k12").linkPaths("E2", "D2");
        temp.get("k12").linkPaths("D2", "E3");
        temp.get("k12").linkPaths("E3", "D3");
        temp.get("k12").linkPaths("D3", "E4");
        temp.get("k12").linkPaths("E4", "D4");
        temp.get("k12").linkPaths("D4", "E1");
        
        // l
        temp.get("l8").addPath(new Path("E1", Direction.EDGE, null)); //m
        temp.get("l8").addPath(new Path("D1", Direction.VERTEX, null));	//m
        temp.get("l8").addPath(new Path("E2", Direction.EDGE, null));	//l9 ++
        temp.get("l8").addPath(new Path("D2", Direction.VERTEX, null));	//k9 ++
        temp.get("l8").addPath(new Path("E3", Direction.EDGE, temp.get("k8")));
        temp.get("l8").addPath(new Path("D3", Direction.VERTEX, temp.get("k7")));
        temp.get("l8").addPath(new Path("E4", Direction.EDGE, temp.get("l7")));
        temp.get("l8").addPath(new Path("D4", Direction.VERTEX, null)); //m
        temp.get("l8").linkPaths("E1", "D1");
        temp.get("l8").linkPaths("D1", "E2");
        temp.get("l8").linkPaths("E2", "D2");
        temp.get("l8").linkPaths("D2", "E3");
        temp.get("l8").linkPaths("E3", "D3");
        temp.get("l8").linkPaths("D3", "E4");
        temp.get("l8").linkPaths("E4", "D4");
        temp.get("l8").linkPaths("D4", "E1");
        temp.get("l7").addPath(new Path("E1", Direction.EDGE, null)); //m
        temp.get("l7").addPath(new Path("D1", Direction.VERTEX, null));	//m
        temp.get("l7").addPath(new Path("E2", Direction.EDGE, temp.get("l8")));	//l8 ++
        temp.get("l7").addPath(new Path("D2", Direction.VERTEX, temp.get("k8")));	//k8 ++
        temp.get("l7").addPath(new Path("E3", Direction.EDGE, temp.get("k7")));
        temp.get("l7").addPath(new Path("D3", Direction.VERTEX, temp.get("k6")));
        temp.get("l7").addPath(new Path("E4", Direction.EDGE, temp.get("l6")));
        temp.get("l7").addPath(new Path("D4", Direction.VERTEX, null)); //m
        temp.get("l7").linkPaths("E1", "D1");
        temp.get("l7").linkPaths("D1", "E2");
        temp.get("l7").linkPaths("E2", "D2");
        temp.get("l7").linkPaths("D2", "E3");
        temp.get("l7").linkPaths("E3", "D3");
        temp.get("l7").linkPaths("D3", "E4");
        temp.get("l7").linkPaths("E4", "D4");
        temp.get("l7").linkPaths("D4", "E1");
        temp.get("l6").addPath(new Path("E1", Direction.EDGE, null)); //m
        temp.get("l6").addPath(new Path("D1", Direction.VERTEX, null));	//m
        temp.get("l6").addPath(new Path("E2", Direction.EDGE, temp.get("l7")));	//l8 ++
        temp.get("l6").addPath(new Path("D2", Direction.VERTEX, temp.get("k7")));	//k8 ++
        temp.get("l6").addPath(new Path("E3", Direction.EDGE, temp.get("k6")));
        temp.get("l6").addPath(new Path("D3", Direction.VERTEX, temp.get("k5")));
        temp.get("l6").addPath(new Path("E4", Direction.EDGE, temp.get("l5")));
        temp.get("l6").addPath(new Path("D4", Direction.VERTEX, null)); //m
        temp.get("l6").linkPaths("E1", "D1");
        temp.get("l6").linkPaths("D1", "E2");
        temp.get("l6").linkPaths("E2", "D2");
        temp.get("l6").linkPaths("D2", "E3");
        temp.get("l6").linkPaths("E3", "D3");
        temp.get("l6").linkPaths("D3", "E4");
        temp.get("l6").linkPaths("E4", "D4");
        temp.get("l6").linkPaths("D4", "E1");
        temp.get("l5").addPath(new Path("E1", Direction.EDGE, null)); //m
        temp.get("l5").addPath(new Path("D1", Direction.VERTEX, null));	//m
        temp.get("l5").addPath(new Path("E2", Direction.EDGE, temp.get("l6")));	//l8 ++
        temp.get("l5").addPath(new Path("D2", Direction.VERTEX, temp.get("k6")));	//k8 ++
        temp.get("l5").addPath(new Path("E3", Direction.EDGE, temp.get("k5")));
        temp.get("l5").addPath(new Path("D3", Direction.VERTEX, temp.get("k9")));
        temp.get("l5").addPath(new Path("E4", Direction.EDGE, temp.get("l9")));
        temp.get("l5").addPath(new Path("D4", Direction.VERTEX, null)); //m
        temp.get("l5").linkPaths("E1", "D1");
        temp.get("l5").linkPaths("D1", "E2");
        temp.get("l5").linkPaths("E2", "D2");
        temp.get("l5").linkPaths("D2", "E3");
        temp.get("l5").linkPaths("E3", "D3");
        temp.get("l5").linkPaths("D3", "E4");
        temp.get("l5").linkPaths("E4", "D4");
        temp.get("l5").linkPaths("D4", "E1");
        temp.get("l9").addPath(new Path("E1", Direction.EDGE, null)); //m
        temp.get("l9").addPath(new Path("D1", Direction.VERTEX, null));	//m
        temp.get("l9").addPath(new Path("E2", Direction.EDGE, temp.get("l5")));	//l8 ++
        temp.get("l9").addPath(new Path("D2", Direction.VERTEX, temp.get("k5")));	//k8 ++
        temp.get("l9").addPath(new Path("E3", Direction.EDGE, temp.get("k9")));
        temp.get("l9").addPath(new Path("D3", Direction.VERTEX, temp.get("k10")));
        temp.get("l9").addPath(new Path("E4", Direction.EDGE, temp.get("l10")));
        temp.get("l9").addPath(new Path("D4", Direction.VERTEX, null)); //m
        temp.get("l9").linkPaths("E1", "D1");
        temp.get("l9").linkPaths("D1", "E2");
        temp.get("l9").linkPaths("E2", "D2");
        temp.get("l9").linkPaths("D2", "E3");
        temp.get("l9").linkPaths("E3", "D3");
        temp.get("l9").linkPaths("D3", "E4");
        temp.get("l9").linkPaths("E4", "D4");
        temp.get("l9").linkPaths("D4", "E1");
        temp.get("l10").addPath(new Path("E1", Direction.EDGE, null)); //m
        temp.get("l10").addPath(new Path("D1", Direction.VERTEX, null));	//m
        temp.get("l10").addPath(new Path("E2", Direction.EDGE, temp.get("l9")));	//l8 ++
        temp.get("l10").addPath(new Path("D2", Direction.VERTEX, temp.get("k9")));	//k8 ++
        temp.get("l10").addPath(new Path("E3", Direction.EDGE, temp.get("k10")));
        temp.get("l10").addPath(new Path("D3", Direction.VERTEX, temp.get("k11")));
        temp.get("l10").addPath(new Path("E4", Direction.EDGE, temp.get("l11")));
        temp.get("l10").addPath(new Path("D4", Direction.VERTEX, null)); //m
        temp.get("l10").linkPaths("E1", "D1");
        temp.get("l10").linkPaths("D1", "E2");
        temp.get("l10").linkPaths("E2", "D2");
        temp.get("l10").linkPaths("D2", "E3");
        temp.get("l10").linkPaths("E3", "D3");
        temp.get("l10").linkPaths("D3", "E4");
        temp.get("l10").linkPaths("E4", "D4");
        temp.get("l10").linkPaths("D4", "E1");
        temp.get("l11").addPath(new Path("E1", Direction.EDGE, null)); //m
        temp.get("l11").addPath(new Path("D1", Direction.VERTEX, null));	//m
        temp.get("l11").addPath(new Path("E2", Direction.EDGE, temp.get("l10")));	//l8 ++
        temp.get("l11").addPath(new Path("D2", Direction.VERTEX, temp.get("k10")));	//k8 ++
        temp.get("l11").addPath(new Path("E3", Direction.EDGE, temp.get("k11")));
        temp.get("l11").addPath(new Path("D3", Direction.VERTEX, temp.get("k12")));
        temp.get("l11").addPath(new Path("E4", Direction.EDGE, temp.get("l12")));
        temp.get("l11").addPath(new Path("D4", Direction.VERTEX, null)); //m
        temp.get("l11").linkPaths("E1", "D1");
        temp.get("l11").linkPaths("D1", "E2");
        temp.get("l11").linkPaths("E2", "D2");
        temp.get("l11").linkPaths("D2", "E3");
        temp.get("l11").linkPaths("E3", "D3");
        temp.get("l11").linkPaths("D3", "E4");
        temp.get("l11").linkPaths("E4", "D4");
        temp.get("l11").linkPaths("D4", "E1");
        temp.get("l12").addPath(new Path("E1", Direction.EDGE, null)); //m
        temp.get("l12").addPath(new Path("D1", Direction.VERTEX, null));	//m
        temp.get("l12").addPath(new Path("E2", Direction.EDGE, temp.get("l11")));	//l8 ++
        temp.get("l12").addPath(new Path("D2", Direction.VERTEX, temp.get("k11")));	//k8 ++
        temp.get("l12").addPath(new Path("E3", Direction.EDGE, temp.get("k12")));
        temp.get("l12").addPath(new Path("D3", Direction.VERTEX, null));
        temp.get("l12").addPath(new Path("E4", Direction.EDGE, null));
        temp.get("l12").addPath(new Path("D4", Direction.VERTEX, null)); //m
        temp.get("l12").linkPaths("E1", "D1");
        temp.get("l12").linkPaths("D1", "E2");
        temp.get("l12").linkPaths("E2", "D2");
        temp.get("l12").linkPaths("D2", "E3");
        temp.get("l12").linkPaths("E3", "D3");
        temp.get("l12").linkPaths("D3", "E4");
        temp.get("l12").linkPaths("E4", "D4");
        temp.get("l12").linkPaths("D4", "E1");
	}
}
