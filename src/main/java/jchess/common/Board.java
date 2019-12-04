package jchess.common;

import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jchess.GUI;
import jchess.common.enumerator.Direction;

public class Board implements IBoardData, IBoard {
	private BoardData m_oBoard;
	private Image m_oBoardImage;
	private Image m_oActivCellImage;
	private Image m_oMarkedCellImage;

    public Board() {
    	m_oBoard = new BoardData();
    }
    
	public Board(BoardData oBoard) {
		m_oBoard = oBoard;
		m_oBoardImage = GUI.loadImage(getBoardImagePath());
		m_oActivCellImage = GUI.loadImage(getActivCellImagePath());
		m_oMarkedCellImage = GUI.loadImage(getMarkedCellImagePath());
		
    	PopulatePositions();
    	mapPieces();
	}
	
	//region IBoardData
	public String getName() {
		return m_oBoard.getName();
	}
	
	public int getBoardWidth() {
		return m_oBoard.getBoardWidth();
	}

	public int getBoardHeight() {
		return m_oBoard.getBoardHeight();
	}

	public String getBoardImagePath() {
		return m_oBoard.getBoardImagePath();
	}

	public String getActivCellImagePath() {
		return m_oBoard.getActivCellImagePath();
	}

	public String getMarkedCellImagePath() {
		return m_oBoard.getMarkedCellImagePath();
	}	
	
	public void addPosition(IPositionData oPosition) {
		m_oBoard.addPosition(oPosition);
	}	
	public IPositionData getPosition(String stName) {
		return m_oBoard.getPosition(stName);
	}
	public List<IPositionData> getAllPositions() {
		return m_oBoard.getAllPositions();
	}
	public Map<String, IPositionData> getAllPositionsMap() {
		return m_oBoard.getAllPositionsMap();
	}

	public IPlayerData getPlayer(String stName) {
		return m_oBoard.getPlayer(stName);
	}	
	public List<IPlayerData> getAllPlayers() {
		return m_oBoard.getAllPlayers();
	}

	public IRuleData getRule(String stName) {
		return m_oBoard.getRule(stName);
	}
	public List<IRuleData> getAllRules() {
		return m_oBoard.getAllRules();
	}

	public IPieceData getPiece(String stName) {
		return m_oBoard.getPiece(stName);
	}
	public List<IPieceData> getAllPieces() {
		return m_oBoard.getAllPieces();
	}

	public IPositionData createPosition() {
		return new Position();
	}	

	public IPieceData createPiece() {
		return new Piece();
	}	

	public IRuleData createRule() {
		return new Rule();
	}	
	
	public IPlayerData createPlayer() {
		return new Player();
	}

	public BoardData getBoardData() {
		return this.m_oBoard;
	}
	
	public void init() {
		m_oBoardImage = GUI.loadImage(getBoardImagePath());
		m_oActivCellImage = GUI.loadImage(getActivCellImagePath());
		m_oMarkedCellImage = GUI.loadImage(getMarkedCellImagePath());
		
		mapPieces();
	}
	// endregion
	
	public Image getBoardImage() {
		return m_oBoardImage;
	}

	public Image getActivCellImage() {
		return m_oActivCellImage;
	}
	
	public Image getMarkedCellImage() {
		return m_oMarkedCellImage;
	}
	
    public void PopulatePositions() {
    	//for (Map.Entry<String, PositionData> entry : m_oBoard.getAllPositions().entrySet()) {
        //   	m_mpPosition.put(entry.getKey(), new Position(entry.getValue()));
    	//}
    }
    
    public void mapPieces() {
    	try {
    	Iterator<IPlayerData> it = m_oBoard.getAllPlayers().iterator();
    	while( it.hasNext()){
    		IPlayerData o = it.next();
    		for (Map.Entry<String, String> entry2 : m_oBoard.getPlayerMapping(o.getName()).entrySet()) {
    			Piece oo = new Piece(m_oBoard.getPiece(entry2.getValue()).getPieceData(), o.getPlayerData());
    			((Position)getPosition(entry2.getKey())).setPiece(oo);
    		}
    	}    
    	}catch(java.lang.Exception e) {
			System.out.println(e);
    }}
    
    //region IBoard
	public Position getPositionByName(String stName) {
		return (Position)getPosition(stName);
	}
	public List<Position> getPositions(){
		return (List<Position>)(Object)getAllPositions();
	}
	
	public Player getPlayerByName(String stName) {
		return (Player)getPlayer(stName);
	}
	public List<Player> getPlayers(){
		return (List<Player>)(Object)getAllPlayers();		
	}
	
	public Rule getRuleByName(String stName) {
		return (Rule)getRule(stName);
	}
	public List<Rule> getRules(){
		return (List<Rule>)(Object)getRules();
	}
	
	public Piece getPieceByName(String stName) {
		return (Piece)getPiece(stName);
	}
	public List<Piece> getPieces(){
		return (List<Piece>)(Object)getPieces();
	}

    //endregion
}
