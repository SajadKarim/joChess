package jchess.common;

/**
 * This interface ensures that the class must keep information about which pieces are to players and on what positions they have to be placed.

 * @author 	Sajad Karim
 * @since	22 Jan 2020
 */
public interface IPlayerPieceMapping {
	public String getPieceCustomName();
	public String getPieceRef();
	public String getPositionRef();
}
