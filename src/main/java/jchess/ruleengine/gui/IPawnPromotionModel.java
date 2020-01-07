package jchess.ruleengine.gui;

import java.util.List;

import jchess.common.IPieceAgent;
import jchess.common.gui.IModel;

/**
 * IPawnPromotionModel 
 * 
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */
public interface IPawnPromotionModel extends IModel {
	public List<IPieceAgent> getViewData();
	public void updateSelectedPiece(String stName);
	public IPieceAgent getSelectedPiece();
}
