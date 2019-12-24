package jchess.gui.view.gamewindow;

import jchess.common.gui.IPanelView;
import jchess.common.gui.IView;

/**
 * 
 * @author 	Sajad Karim
 * @since	7 Dec 2019
 */

public interface IGameView extends IPanelView {
    public void repaintClockView();
    public void repaintBoardView();
    public void repaintPlayerView();
    public void addMove(String stMoveString);
    public void removeMove(String stMoveString);
	public void addListener(IGameViewListener oListener);
	public void tryUndoMove();
	public void tryRedoMove();
}
