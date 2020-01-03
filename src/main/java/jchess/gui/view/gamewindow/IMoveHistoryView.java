package jchess.gui.view.gamewindow;

import javax.swing.JScrollPane;

import jchess.common.gui.IPanelView;

/**
 * 
 * @author 	Sajad Karim
 * @since	7 Dec 2019
 */

public interface IMoveHistoryView extends IPanelView {
	public JScrollPane getScrollPane();
    public void addMove(String stMoveString);
    public void removeMove(String stMoveString);
}
