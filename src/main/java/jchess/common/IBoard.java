package jchess.common;

import java.awt.Image;
import java.util.List;

public interface IBoard {
	public String getName();
	public int getBoardWidth();
	public int getBoardHeight();
	public Image getBoardImage();
	public Image getActivCellImage();
	public Image getMarkedCellImage();
	
	public Position getPositionByName(String stName);
	public List<Position> getPositions();
	
	public Player getPlayerByName(String stName);
	public List<Player> getPlayers();
	
	public Rule getRuleByName(String stName);
	public List<Rule> getRules();
	
	public Piece getPieceByName(String stName);
	public List<Piece> getPieces();
}
