package jchess.model;

import java.awt.Image;
import java.util.Map;

import jchess.common.IPositionAgent;

public interface IBoardModel {
	public int getBoardWidth();
	public int getBoardHeight();
	public Image getBoardImage();
	public Image getActivCellImage();
	public Image getMarkedCellImage();
	public Map<String, IPositionAgent> getPositions();
}
