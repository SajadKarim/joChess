package jchess.common;

/**
 * IPlayer provides abstraction to IPlayerData and IPlayerAgent.
 * It fulfills functionality of Abstract Factory Pattern, and
 * it is mainly built for Cache module to make both the
 * type compatible with its implementation (data population logic). 
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 *
 */

public interface IPlayer {
	public String getName();
	public IPlayerData getPlayerData();
	public IBoardMapping getBoardMapping();
}
