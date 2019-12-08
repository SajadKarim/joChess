package jchess.view;

import java.awt.Component;

import jchess.model.IGameModel;

public interface IGameView {
	public void init();
	public void setModelData(IGameModel oBoardModel);
    public void addListener(final GameViewListener oListener);
    public void paintView();
    public Component getViewComponent();
    public void repaintClockView();
    public void repaintBoardView();
    public void repaintPlayerView();
}
