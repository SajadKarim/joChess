package jchess.gamelogic;

import java.awt.Image;
import java.util.LinkedList;
import java.util.Map;

import jchess.cache.BoardData;
import jchess.common.IBoardActivity;
import jchess.common.IBoardAgent;
import jchess.common.IBoardData;
import jchess.common.IMoveCandidate;
import jchess.common.IPiece;
import jchess.common.IPieceAgent;
import jchess.common.IPlayer;
import jchess.common.IPlayerAgent;
import jchess.common.IPosition;
import jchess.common.IPositionAgent;
import jchess.common.IRule;
import jchess.common.IRuleAgent;
import jchess.util.GUI;

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

	private int m_nActivityIndex;
	private LinkedList<IBoardActivity> m_lstActivity;
	
    public BoardAgent() {
    	m_oBoard = new BoardData();
    	
    	m_nActivityIndex = -1;
    	m_lstActivity = new LinkedList<IBoardActivity>();
    }
    
	public BoardAgent(BoardData oBoard) {
		m_oBoard = oBoard;

    	m_nActivityIndex = -1;
    	m_lstActivity = new LinkedList<IBoardActivity>();

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

	public IPiece getUnlinkedPiece(String stName) {
		return m_oBoard.getUnlinkedPiece(stName);
	}

	public  Map<String, IPiece> getAllUnlinkedPieces() {
		return m_oBoard.getAllUnlinkedPieces();
	}

	public IBoardData getBoardData() {
		return this.m_oBoard;
	}
	
	public void init() {
		m_oBoardImage = GUI.loadImage(getBoardImagePath());
		m_oActivCellImage = GUI.loadImage(getActivCellImagePath());
		m_oMarkedCellImage = GUI.loadImage(getMarkedCellImagePath());
		
		updatePieceAndPositionReferences();
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
	
    private void updatePieceAndPositionReferences() {
    	for(Map.Entry<String, IPlayerAgent> itPlayers: getPlayers().entrySet()) {
    		IPlayerAgent oPlayer = itPlayers.getValue();

    		Map<String, String> mpPlayerPieceMapping = m_oBoard.getPlayerMapping(oPlayer.getName());
    		if( mpPlayerPieceMapping != null) {
	    		for (Map.Entry<String, String> itPlayerPieceMapping : mpPlayerPieceMapping.entrySet()) {
	    			IPositionAgent oPosition = getPositionAgent(itPlayerPieceMapping.getKey());
	    			IPieceAgent oPiece = (IPieceAgent)m_oBoard.getUnlinkedPiece(itPlayerPieceMapping.getValue()).clone();
	    			
	    			oPiece.setPlayer(oPlayer);
	    			
	    			oPlayer.addPiece(oPiece);
	    			
	    			linkPieceAndPosition(oPosition, oPiece);    			
	    		}
    		}
    	}    
    }
    
    private void linkPieceAndPosition(IPositionAgent oPosition, IPieceAgent oPiece) {
    	oPosition.setPiece(oPiece);
    	oPiece.setPosition(oPosition);
    }
    
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
	
	public IPieceAgent getUnlinkedPieceAgent(String stName) {
		return (IPieceAgent)getUnlinkedPiece(stName);
	}
	public  Map<String, IPieceAgent> getAllUnlinkedPieceAgents(){
		return (Map<String, IPieceAgent>)(Object)getAllUnlinkedPieces();
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
		return (PieceAgent)getUnlinkedPiece(stName);
	}
	public Map<String, IPieceAgent> getPieces(){
		return (Map<String, IPieceAgent>)(Object)getPieces();
	}
	//endregion
	
	public String getRuleEngineName() {
		return m_oBoard.getRuleEngineName();
	}

	public void setRuleEngineName(String stName) {
		m_oBoard.setRuleEngineName(stName);
	}

	public String getRuleProcessorName() {
		return m_oBoard.getRuleProcessorName();
	}
	
	public void addActivity(IBoardActivity oActivity) {
		if( m_nActivityIndex != m_lstActivity.size() - 1) {
			for(int nIndex = m_lstActivity.size() - 1; nIndex > m_nActivityIndex; nIndex--) {
				m_lstActivity.remove(nIndex);
			}
			//m_lstActivity.subList(m_nActivityIndex, m_lstActivity.size() - 1).clear();
		}
		
		m_lstActivity.add(oActivity);
		m_nActivityIndex = m_lstActivity.size() - 1;		
	}

	public IBoardActivity undoLastActivity() {
		IBoardActivity oActivity = null;
		
		if( m_nActivityIndex >= 0) {
			oActivity = m_lstActivity.get(m_nActivityIndex);
			m_nActivityIndex--;
		}
		
		if( oActivity != null) {
			for(Map.Entry<IPositionAgent, IPieceAgent> it : oActivity.getPriorMoveDetails().entrySet()) {
				IPositionAgent oPosition = it.getKey();
				IPieceAgent oPiece = it.getValue();
								
				oPosition.setPiece(oPiece);
				if( oPiece != null) {
					IPositionAgent oPieceCurrentPosition = oPiece.getPosition();				

					oPiece.setPosition(oPosition);
					
					if( oPieceCurrentPosition != oPosition) {
						oPiece.dequeuePositionHistory();
					}
				}
			}
		}

		return oActivity;
	}
	
	public IBoardActivity redoLastActivity() {
		IBoardActivity oActivity = null;
		
		if( m_nActivityIndex < m_lstActivity.size() -1) {
			m_nActivityIndex++;
			oActivity = m_lstActivity.get(m_nActivityIndex);			
		}
		
		if( oActivity != null) {
			for(Map.Entry<IPositionAgent, IPieceAgent> it : oActivity.getPostMoveDetails().entrySet()) {
				IPositionAgent oPosition = it.getKey();
				IPieceAgent oPiece = it.getValue();
				
				oPosition.setPiece(oPiece);
				if( oPiece != null) {
					IPositionAgent oPieceCurrentPosition = oPiece.getPosition();				

					oPiece.setPosition(oPosition);

					if( oPieceCurrentPosition != oPosition) {
						oPiece.enqueuePositionHistory(oPosition);
					}
				}
			}
		}
		
		return oActivity;
	}
	
	public IBoardActivity getLastActivityByPlayer(IPlayerAgent oPlayer) {
		for(int nIndex = m_lstActivity.size() - 1; nIndex >= 0; nIndex--) {
			IBoardActivity oActivity = m_lstActivity.get(nIndex);
			
			if( oActivity.getPlayer().equals(oPlayer))
				return oActivity;
		}
		
		return null;
	}
}
