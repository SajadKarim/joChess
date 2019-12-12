package jchess.view.newgamewindow;

import jchess.view.IView;

/**
 * Interface for new game window.
 * 
 * @author  Sajad Karim
 * @since	7 Dec 2019
 */

public interface INewGameView extends IView {  
	public void setCallback(INewGame_Callback oCallback);
}
