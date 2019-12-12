package jchess.gamelogic;

import jchess.presenter.gamewindow.IGamePresenter_Callback;

public interface IGame {
	public void init();
	public void setCallback(IGamePresenter_Callback oGamePresenter);
}
