package jchess.common;

import java.util.List;

/**
 * IPieceData provides interface for the cache module.
 * It expose getters and setters and does not support
 * methods that are linked to logic of the game.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public interface IPieceData extends IPiece {
	public void setName(String stName);
	public void setImagePath(String stImagePath);
	public List<IRule> getAllRules();
	public void addRule(IRule oRule);
	public void setFamily(String stFamily);
}
