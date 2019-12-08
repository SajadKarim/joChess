package jchess.common;

/**
 * IPlayerData provides interface for the cache module.
 * It expose getters and setters and does not support
 * methods that are linked to logic of the game.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public interface IPlayerData extends IPlayer {
    public void setName(String stName);
	public void addBoardMapping(int nSource, int nDestination);
	public int getBoardMapping(int nSource);
}
