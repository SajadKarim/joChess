package jchess.gamelogic;

import java.awt.Image;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import jchess.cache.PieceData;
import jchess.common.IPieceAgent;
import jchess.common.IPieceData;
import jchess.common.IPlayerAgent;
import jchess.common.IPositionAgent;
import jchess.common.IRule;
import jchess.common.IRuleAgent;
import jchess.util.GUI;

/**
 * This class is responsible to manage underlying "Piece" (only) related data.
 * It keeps the state of the Piece and facilitates game-logic with numerous operations
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public final class PieceAgent implements IPieceAgent {
	private IPieceData m_oPiece;
	private IPlayerAgent m_oPlayer;
	private Image m_oImage;
	private IPositionAgent m_oPosition;
	private Object m_oCustomData;
	
	private Queue<IPositionAgent> m_lstPositionHistory;
	
	public PieceAgent() {
		m_oPiece = new PieceData();
		m_lstPositionHistory = new LinkedList<IPositionAgent>();
	}
	
	public PieceAgent(PieceAgent oPiece) {
		m_oImage = oPiece.m_oImage;
		m_oPlayer = oPiece.m_oPlayer;
		m_oPosition = oPiece.m_oPosition;
		m_oPiece = new PieceData((PieceData)oPiece.m_oPiece);
		m_lstPositionHistory = new LinkedList<IPositionAgent>();
		m_oCustomData = oPiece.m_oCustomData;
	}

	public void init() {
		m_oImage = GUI.loadImage(getImagePath());
	}
	
	public String getName() {
		return m_oPiece.getName();
	}
		
	public String getImagePath() {
		return m_oPiece.getImagePath();
	}
	
	public List<IRule> getAllRules() {
		return m_oPiece.getAllRules();
	}
	
	public IPlayerAgent getPlayer() {
		return m_oPlayer;
	}
	
	public void setPlayer(IPlayerAgent oPlayer) {
		m_oPlayer = oPlayer;
	}

	public Image getImage() {
		if (m_oImage == null)			
			m_oImage = GUI.loadImage(getImagePath());

		return m_oImage;
	}

	public void setImage(Image oImage) {
		m_oImage = oImage;
	}
		
	public IPieceData getPieceData() {
		return m_oPiece;
	}

	public List<IRuleAgent> getRules() {
		return (List<IRuleAgent>)(Object)m_oPiece.getAllRules();
	}
	
	public IRule getRule(String stRuleName) {
		return m_oPiece.getRule(stRuleName);
	}

	public int getRuns() {
		return m_lstPositionHistory.size();
	}

	public String getFamily() {
		return m_oPiece.getFamily();
	}
	
	@Override
	public IPieceAgent clone() {
		return new PieceAgent(this);
	}
	
	public IPositionAgent getPosition() {
		return m_oPosition;
	}
	
	public void setPosition(IPositionAgent oPosition) {
		m_oPosition = oPosition;
	}

	public String toLog() {
		return String.format("Id=%s, Position=%s"
				, getName()
				, m_oPosition == null ? "<no Position attached>" : m_oPosition.getName());
	}
	
	public void enqueuePositionHistory(IPositionAgent oPosition) {
		m_lstPositionHistory.add(oPosition);
	}

	public IPositionAgent dequeuePositionHistory() {
		return m_lstPositionHistory.poll();
	}
	
	public int getPositionHistoryCount() {
		return m_lstPositionHistory.size();
	}

	public Object getCustomData() {
		return m_oCustomData;
	}
	
	public void setCustomData(Object oCustomData) {
		m_oCustomData = oCustomData;
	}
	
	public void updateImage(String stImageFileName) {
		m_oImage = GUI.loadImage(stImageFileName);
	}
}
