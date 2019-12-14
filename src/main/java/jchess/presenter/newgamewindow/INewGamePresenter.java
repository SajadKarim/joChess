package jchess.presenter.newgamewindow;

import javax.swing.JDialog;

import jchess.view.newgamewindow.INewGameListener;

public interface INewGamePresenter extends INewGameListener{
    public void init();
    public JDialog getViewJDialog();
	public void addListener(final INewGameListener oListener);
}
