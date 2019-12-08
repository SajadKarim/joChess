package jchess.gamelogic;

import java.awt.Image;
import java.util.Map;

import jchess.GUI;
import jchess.cache.BoardData;
import jchess.common.IBoardAgent;
import jchess.common.IBoardData;
import jchess.common.IPiece;
import jchess.common.IPieceAgent;
import jchess.common.IPlayer;
import jchess.common.IPlayerAgent;
import jchess.common.IPosition;
import jchess.common.IPositionAgent;
import jchess.common.IRule;
import jchess.common.IRuleAgent;

/**
 * This class is responsible to manage underlying "Board" (only) related data.
 * It keeps the state of the Board and facilitates game-logic with numerous operations
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public class BoardAgent implements IBoardAgent {
	private IBoardData m_oBoard;
	private Image m_oBoardImage;
	private Image m_oActivCellImage;
	private Image m_oMarkedCellImage;

    public BoardAgent() {
    	m_oBoard = new BoardData();
    }
    
	public BoardAgent(BoardData oBoard) {
		m_oBoard = oBoard;
		
    	init();
	}
	
	//region: Implements IBoard
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
	
	public IPosition getPosition(String stName) {
		return m_oBoard.getPosition(stName);
	}
	
	public Map<String, IPosition> getAllPositions() {
		return m_oBoard.getAllPositions();
	}

	public IPlayer getPlayer(String stName) {
		return m_oBoard.getPlayer(stName);
	}

	public Map<String, IPlayer > getAllPlayers() {
		return m_oBoard.getAllPlayers();
	}

	public IRule getRule(String stName) {
		return m_oBoard.getRule(stName);
	}
	
	public  Map<String, IRule> getAllRules() {
		return m_oBoard.getAllRules();
	}

	public IPiece getPiece(String stName) {
		return m_oBoard.getPiece(stName);
	}

	public  Map<String, IPiece> getAllPieces() {
		return m_oBoard.getAllPieces();
	}

	public IPosition createPosition() {
		return new PositionAgent();
	}	

	public IPiece createPiece() {
		return new PieceAgent();
	}	

	public IRule createRule() {
		return new RuleAgent();
	}	
	
	public IPlayer createPlayer() {
		return new PlayerAgent();
	}

	public IBoardData getBoardData() {
		return this.m_oBoard;
	}
	
	public void init() {
		m_oBoardImage = GUI.loadImage(getBoardImagePath());
		m_oActivCellImage = GUI.loadImage(getActivCellImagePath());
		m_oMarkedCellImage = GUI.loadImage(getMarkedCellImagePath());
		
		mapPieces();
	}
	//endregion
	
	//region: Implements IBoardAgent
	public Image getBoardImage() {
		return m_oBoardImage;
	}

	public Image getActivCellImage() {
		return m_oActivCellImage;
	}
	
	public Image getMarkedCellImage() {
		return m_oMarkedCellImage;
	}
	
    public void mapPieces() {
    	try {
    	for(Map.Entry<String, IPlayerAgent> entry: getPlayers().entrySet()) {
    	//Iterator<IPlayerAgent> it = this.getPlayers().iterator();
    	//while( it.hasNext()){
    		IPlayerAgent o = entry.getValue();// it.next();
    		for (Map.Entry<String, String> entry2 : m_oBoard.getPlayerMapping(o.getName()).entrySet()) {
    			IPieceAgent oo = new PieceAgent(m_oBoard.getPiece(entry2.getValue()).getPieceData(), o);
    			((PositionAgent)getPosition(entry2.getKey())).setPiece(oo);
    		}
    	}    
    	}catch(java.lang.Exception e) {
			System.out.println(e);
    }}
    
    
	public IPositionAgent getPositionAgent(String stName) {
		return (IPositionAgent)getPosition(stName);
	}
	public Map<String, IPositionAgent> getAllPositionAgents(){
		return (Map<String, IPositionAgent>)(Object)getAllPositions();
	}
	
	public IPlayerAgent getPlayerAgent(String stName) {
		return (IPlayerAgent)getPlayer(stName);
	}
	public Map<String, IPlayerAgent > getAllPlayerAgents(){
		return (Map<String, IPlayerAgent>)(Object)getAllPlayers();
	}
	
	public IRuleAgent getRuleAgent(String stName) {
		return (IRuleAgent)getRule(stName);
	}
	public  Map<String, IRuleAgent> getAllRuleAgents(){
		return (Map<String, IRuleAgent>)(Object)getAllRules();
	}
	
	public IPieceAgent getPieceAgent(String stName) {
		return (IPieceAgent)getPiece(stName);
	}
	public  Map<String, IPieceAgent>  getAllPieceAgents(){
		return (Map<String, IPieceAgent>)(Object)getAllPieces();
	}
	
	public PositionAgent getPositionByName(String stName) {
		return (PositionAgent)getPosition(stName);
	}
	public Map<String, IPositionAgent> getPositions(){
		return (Map<String, IPositionAgent>)(Object)getAllPositions();
	}
	
	public IPlayerAgent getPlayerByName(String stName) {
		return (PlayerAgent)getPlayer(stName);
	}
	public Map<String, IPlayerAgent> getPlayers(){
		return (Map<String, IPlayerAgent>)(Object)getAllPlayers();		
	}
	
	public RuleAgent getRuleByName(String stName) {
		return (RuleAgent)getRule(stName);
	}
	public Map<String, IRuleAgent> getRules(){
		return (Map<String, IRuleAgent>)(Object)getRules();
	}
	
	public PieceAgent getPieceByName(String stName) {
		return (PieceAgent)getPiece(stName);
	}
	public Map<String, IPieceAgent> getPieces(){
		return (Map<String, IPieceAgent>)(Object)getPieces();
	}
	//endregion
}
