package jchess.gui.view.newgamewindow;

import javax.swing.JDialog;

import jchess.common.gui.IPanelView;

/**
 * 
 * @author  Sajad Karim
 * @since	7 Dec 2019
 */

public interface INewLocalGameView extends IPanelView {
    public void setParent(JDialog parent);
	public void addListener(final INewGameListener oListener);
}
