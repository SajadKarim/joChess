package jchess.gui.model.gamewindow;

import java.awt.Image;
import java.util.Map;

import jchess.common.IPositionAgent;
import jchess.gui.model.IModel;

/**
 * 
 * @author 	Sajad Karim
 * @since	7 Dec 2019
 */

public interface IBoardModel extends IModel {
	public int getBoardWidth();
	public int getBoardHeight();
	public Image getBoardImage();
	public Image getActivCellImage();
	public Image getMarkedCellImage();
	public Map<String, IPositionAgent> getPositions();
}
