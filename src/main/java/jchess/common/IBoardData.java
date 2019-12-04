package jchess.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IBoardData {
	public String getName();
	public int getBoardWidth();
	public int getBoardHeight();
	public String getBoardImagePath();
	public String getActivCellImagePath();
	public String getMarkedCellImagePath();
	
	public void addPosition(IPositionData oPosition);
	public IPositionData getPosition(String stName);
	
	public IPlayerData getPlayer(String stName);
	public List<IPlayerData> getAllPlayers();
	
	public IRuleData getRule(String stName);
	public List<IRuleData> getAllRules();
	
	public List<IPieceData> getAllPieces();
	
	public IPositionData createPosition();
	public IPieceData createPiece();
	public IRuleData createRule();
	public IPlayerData createPlayer();
	
	public BoardData getBoardData();
	
	public void init();
}
