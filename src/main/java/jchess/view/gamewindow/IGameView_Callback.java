package jchess.view.gamewindow;

import jchess.common.IPositionAgent;

/**
 * This interface facilitates call back funtionality between View and Presenter.
 * 
 * @author  Sajad Karim
 * @since	7 Dec 2019
 */

public interface IGameView_Callback {
    public void onPositionClicked(IPositionAgent oPosition);
}
