package jchess.gui.model.newgamewindow;

import java.awt.Image;
import java.util.Map;
import java.util.SortedMap;

import org.javatuples.Pair;

import jchess.common.IPlayerAgent;
import jchess.common.gui.IModel;

public interface INewGameModel extends IModel {
	public Map<String, IPlayerAgent> getPlayers();
	public void setPlayerBoardMapping(SortedMap<String, Pair<String, Integer>> mpPlayersBoardMapping);
	public Boolean updatePlayerDetails(String stPlayerId, String stFirstName, String stLastName, Image oImage);
	public String[] getAllBoardNames();
	public int getPlayersAllowedInBoard(String stBoardName);
	public void setSelectedBoardName(String stBoardName);
	public String getSelectedBoardName();
	public String getSelectedBoardFileName();
}
