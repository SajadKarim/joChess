package jchess.ruleengine.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jchess.common.IPieceAgent;

/**
 * PawnPromotionModel- Model for PawnPromotion related functionality. 
 * 
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public class PawnPromotionModel implements IPawnPromotionModel {
	IPieceAgent m_oSelectedPiece;
	IPieceAgent m_oPieceToPromote;
	private Map<String, IPieceAgent> m_mpPieces;
	
	public PawnPromotionModel(Map<String, IPieceAgent > mpPieces, IPieceAgent oPieceToPromote) {
		m_mpPieces = mpPieces;
		m_oSelectedPiece = null;
		m_oPieceToPromote = oPieceToPromote;
	}
	
	public List<IPieceAgent> getViewData(){
		List<IPieceAgent> lstPieces = new ArrayList<IPieceAgent>();
		
		for (Map.Entry<String, IPieceAgent> entry : m_mpPieces.entrySet()) { 
			if( entry.getValue().getFamily().equals(m_oPieceToPromote.getFamily())
				&& !(entry.getValue().getName().equals(m_oPieceToPromote.getName()))) {
				lstPieces.add(entry.getValue());
			}
		}
		
		return lstPieces;
	}
	
	public void updateSelectedPiece(String stName) {
		for (Map.Entry<String, IPieceAgent> entry : m_mpPieces.entrySet()) { 
			if( entry.getValue().getName().equals(stName)) {
				m_oSelectedPiece = entry.getValue();
				break;
			}
		}
	}

	public IPieceAgent getSelectedPiece() {
		return m_oSelectedPiece;
	}
}
