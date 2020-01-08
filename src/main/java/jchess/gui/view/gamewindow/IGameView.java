package jchess.gui.view.gamewindow;

import jchess.common.gui.IPanelView;

/**
 * 
 * @author 	Sajad Karim
 * @since	7 Dec 2019
 */

public interface IGameView extends IPanelView {
    public void repaintClockView();
    public void repaintBoardView();
    public void repaintPlayerView();
    public void addBoardActivity(String stActivity);
    public void removeBoardActivity(String stActivity);
	public void addListener(IGameViewListener oListener);
	public void tryUndoBoardActivity();
	public void tryRedoBoardActivity();
}
