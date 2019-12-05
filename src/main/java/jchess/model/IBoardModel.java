package jchess.model;

import java.awt.Image;
import java.util.List;

import jchess.common.Piece;
import jchess.common.Player;
import jchess.common.Position;
import jchess.common.Rule;

public interface IBoardModel {
	public int getBoardWidth();
	public int getBoardHeight();
	public Image getBoardImage();
	public Image getActivCellImage();
	public Image getMarkedCellImage();
	public List<Position> getPositions();
}
