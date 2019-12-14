package jchess.view.newgamewindow;

import jchess.model.newgamewindow.INewGameModel;

/**
 * 
 * @author  Sajad Karim
 * @since	7 Dec 2019
 */

public interface INewGameListener {
    public void onNewGameLaunchRequest(INewGameModel oData);
}
