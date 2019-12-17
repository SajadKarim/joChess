package jchess.gui.view.newgamewindow;

import jchess.gui.model.newgamewindow.INewGameModel;

/**
 * 
 * @author  Sajad Karim
 * @since	7 Dec 2019
 */

public interface INewGameListener {
    public void onNewGameLaunchRequest(INewGameModel oData);
}
