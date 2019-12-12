package jchess.view.gamewindow;

import jchess.view.IView;

/**
 * 
 * @author 	Sajad Karim
 * @since	7 Dec 2019
 */

public interface IGameView extends IView {
    public void repaintClockView();
    public void repaintBoardView();
    public void repaintPlayerView();
    public void setCallback(IGameView_Callback oCallback);
}
