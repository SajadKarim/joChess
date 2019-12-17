package jchess.gui.view.newgamewindow;

import javax.swing.JDialog;

import jchess.gui.view.IView;

/**
 * 
 * @author  Sajad Karim
 * @since	7 Dec 2019
 */

public interface INewLocalGameView extends IView {
    public void setParent(JDialog parent);
	public void addListener(final INewGameListener oListener);
}
