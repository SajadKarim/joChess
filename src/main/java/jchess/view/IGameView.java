package jchess.view;

import java.awt.Component;

import jchess.model.IGameModel;

/**
 * 
 * @author 	Sajad Karim
 * @since	7 Dec 2019
 */

public interface IGameView extends IView {
    public void repaintClockView();
    public void repaintBoardView();
    public void repaintPlayerView();
    public void setCallback(IGameViewCallback oCallback);
}
