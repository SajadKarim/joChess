package jchess.presenter.gamewindow;

import java.awt.Component;

import jchess.gamelogic.IGameListener;
import jchess.view.gamewindow.IGameViewListener;

public interface IGamePresenter extends IGameViewListener, IGameListener {
    public void init();
    public Component getViewComponent();
    public void showView();
}
