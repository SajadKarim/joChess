package jchess.view.newgamewindow;

import javax.swing.JDialog;

import jchess.view.IView;

/**
 * Interface for new game window.
 * 
 * @author  Sajad Karim
 * @since	7 Dec 2019
 */

public interface INewGameView extends IView {  
	public JDialog getJDialog();
	public void addListener(INewGameListener oListener);
}
