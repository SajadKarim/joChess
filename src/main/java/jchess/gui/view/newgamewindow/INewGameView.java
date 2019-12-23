package jchess.gui.view.newgamewindow;

import javax.swing.JDialog;

import jchess.common.gui.IPanelView;
import jchess.common.gui.IView;

/**
 * Interface for new game window.
 * 
 * @author  Sajad Karim
 * @since	7 Dec 2019
 */

public interface INewGameView extends IPanelView {  
	public JDialog getJDialog();
	public void addListener(INewGameListener oListener);
}
