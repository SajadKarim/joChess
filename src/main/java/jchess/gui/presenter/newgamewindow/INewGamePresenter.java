package jchess.gui.presenter.newgamewindow;

import jchess.common.gui.IPresenter;
import jchess.gui.view.newgamewindow.INewGameListener;

public interface INewGamePresenter extends  IPresenter, INewGameListener {
	public void addListener(final INewGameListener oListener);
}
