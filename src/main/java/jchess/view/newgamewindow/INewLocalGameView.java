package jchess.view.newgamewindow;

import javax.swing.JDialog;

import jchess.view.IView;

public interface INewLocalGameView extends IView {
    public void setParent(JDialog parent);
    public void setCallback(INewGame_Callback oCallback);
}
