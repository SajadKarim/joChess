package jchess.common;

public interface IBoard {
	public String getName();

	public String getBoardImagePath();
	
	public String getActivCellImagePath();
	
	public String getMarkedCellImagePath();
	
	public int getBoardWidth();

	public int getBoardHeight();
}
