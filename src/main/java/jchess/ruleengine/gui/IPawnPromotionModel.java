package jchess.ruleengine.gui;

import java.util.List;
import java.util.Map;

import jchess.common.IPieceAgent;
import jchess.common.gui.IModel;

public interface IPawnPromotionModel extends IModel {
	public List<IPieceAgent> getViewData();
	public void updateSelectedPiece(String stName);
	public IPieceAgent getSelectedPiece();
}
