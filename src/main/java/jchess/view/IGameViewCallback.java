package jchess.view;

import jchess.common.IPositionAgent;

/**
 * This interface facilitates call back funtionality between View and Presenter.
 * 
 * @author  Sajad Karim
 * @since	7 Dec 2019
 */

public interface IGameViewCallback {
    public void onPositionClicked(IPositionAgent oPosition);
}
