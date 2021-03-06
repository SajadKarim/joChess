package jchess.common;

import java.awt.Image;
import java.util.Map;

/**
 * IPlayerAgent provides interface for the module where the all the decisions (game) are taken. It ensures that user
 * does not make any changes to underlying data object.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public interface IPlayerAgent extends IPlayer {
	public void setFirstName(String stName);
	public String  getFirstName();
	public void setLastName(String stName);
	public String  getLastName();
	public void setImage(Image oImage);
	public Image getImage();
	public void addPiece(String stPieceCustomName, IPieceAgent oPiece);
	public IPieceAgent getPiece(String stName);
	public Map<String, IPieceAgent> getAllPieces();
	public IPieceAgent getKingPiece();
	public int getRemainingTimeInSec();
	public void setRemainingTimeInSec(int nRemainingTimeInSec);
}
