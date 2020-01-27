package jchess.gui.presenter.newgamewindow;

import jchess.common.gui.IPresenter;
import jchess.gui.view.newgamewindow.INewGameListener;

/**
 * 
 * @author 	Sajad Karim
 * @since	7 Dec 2019
 */

public interface INewGamePresenter extends  IPresenter, INewGameListener {
	public void addListener(final INewGameListener oListener);
}
