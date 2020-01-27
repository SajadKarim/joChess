package jchess.gui.presenter.gamewindow;

import java.util.Map;

import jchess.common.IPlayerAgent;
import jchess.common.gui.IPresenter;
import jchess.gamelogic.IGameListener;
import jchess.gui.view.gamewindow.IGameViewListener;

/**
 * 
 * @author 	Sajad Karim
 * @since	7 Dec 2019
 */

public interface IGamePresenter extends IPresenter, IGameViewListener, IGameListener {
	public void updatePlayerNames(Map<String, IPlayerAgent> mpPlayer);
}
