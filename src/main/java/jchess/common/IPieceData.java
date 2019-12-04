package jchess.common;

import java.util.List;
import java.util.Map;

public interface IPieceData {
	public String getName();	
	public String getImagePath();	
	public List<IRuleData> getAllRules();
	public PieceData getPieceData();
}
