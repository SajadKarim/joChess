package jchess.view.newgamewindow;

import javax.swing.JDialog;

import jchess.view.IView;

/**
 * 
 * @author  Sajad Karim
 * @since	7 Dec 2019
 */

public interface INewLocalGameView extends IView {
    public void setParent(JDialog parent);
    public void setCallback(INewGame_Callback oCallback);
}
