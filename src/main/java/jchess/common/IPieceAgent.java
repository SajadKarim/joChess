package jchess.common;

import java.awt.Image;
import java.util.List;

/**
 * IPieceAgent provides interface for the module where the
 * all the decisions (game) are taken. It ensures that user
 * does not make any changes to underlying data object.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public interface IPieceAgent extends IPiece {
	public List<IRuleAgent> getRules();
	public IPlayerAgent getPlayer();
	public void setPlayer(IPlayerAgent oPlayer);
	public int getRuns();
	public void markRun();
	public Image getImage();
}
