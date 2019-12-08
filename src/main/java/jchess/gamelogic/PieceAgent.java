package jchess.gamelogic;

import java.awt.Image;
import java.util.List;

import jchess.GUI;
import jchess.cache.PieceData;
import jchess.common.IPieceAgent;
import jchess.common.IPieceData;
import jchess.common.IPlayerAgent;
import jchess.common.IRule;
import jchess.common.IRuleAgent;

/**
 * This class is responsible to manage underlying "Piece" (only) related data.
 * It keeps the state of the Piece and facilitates game-logic with numerous operations
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public class PieceAgent implements IPieceAgent {
	private IPieceData m_oPiece;
	private IPlayerAgent m_oPlayer;
	private Image m_oImage;
	
	public PieceAgent() {
		m_oPiece = new PieceData();
	}
	
	public PieceAgent(IPieceData oPiece, IPlayerAgent oPlayer){
		m_oPiece = oPiece;
		m_oPlayer = oPlayer;
		m_oImage = GUI.loadImage(getImagePath());
	}
	
	public PieceAgent(PieceAgent oPiece) {
	}
	
	public String getName() {
		return m_oPiece.getName();
	}
		
	public String getImagePath() {
		return m_oPiece.getImagePath();
	}
	
	public List< IRule> getAllRules(){
		return m_oPiece.getAllRules();
	}
	
	public IPlayerAgent getPlayer() {
		return m_oPlayer;
	}
	
	public void setPlayer(IPlayerAgent oPlayer) {
		m_oPlayer = oPlayer;
	}

	public Image getImage() {
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
}
