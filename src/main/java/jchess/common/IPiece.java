package jchess.common;

import java.util.List;
import java.util.Map;

public interface IPiece {
	public String getName();
	
	public String getImagePath();
	
	public Map<String, Rule> getAllRules();
}
