package jchess.common;

import java.util.Map;

/**
 * IBoardData provides interface for the cache module.
 * It expose getters and setters and does not support
 * methods that are linked to logic of the game.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public interface IBoardData extends IBoard {
	public void setName(String stName);
	public void setBoardWidth(int nWidth);
	public void setBoardHeight(int nHeight);
	public void setBoardImagePath(String stPath);
	public void setActivCellImagePath(String stPath);
	public void setMarkedCellImagePath(String stPath);
	public void setRuleEngineName(String stName);

	public String getBoardImagePath();
	public String getActivCellImagePath();
	public String getMarkedCellImagePath();
	
	public void addMapping(String stPlayer, String stPiece, String stPosition);
	public void addPiece(IPiece oPiece) ;
	public void addRule(IRule oRule);
	public void addPlayer(IPlayer oPlayer);
	public void addPosition(IPosition oPosition);
	
	public Map<String, String> getPlayerMapping(String stName);	
}
