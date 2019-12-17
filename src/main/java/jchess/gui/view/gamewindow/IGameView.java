package jchess.gui.view.gamewindow;

import jchess.gui.view.IView;

/**
 * 
 * @author 	Sajad Karim
 * @since	7 Dec 2019
 */

public interface IGameView extends IView {
    public void repaintClockView();
    public void repaintBoardView();
    public void repaintPlayerView();
	public void addListener(IGameViewListener oListener);
}
