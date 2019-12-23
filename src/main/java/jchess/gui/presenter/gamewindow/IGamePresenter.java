package jchess.gui.presenter.gamewindow;

import java.awt.Component;

import jchess.common.gui.IPresenter;
import jchess.gamelogic.IGameListener;
import jchess.gui.view.gamewindow.IGameViewListener;

public interface IGamePresenter extends IPresenter, IGameViewListener, IGameListener {
}
