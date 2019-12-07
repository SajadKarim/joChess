package jchess.common;

import java.util.List;

public interface IPiece {
	public String getName();	
	public String getImagePath();	
	public List<IRule> getRules();
	public IPlayer getPlayer();
}
