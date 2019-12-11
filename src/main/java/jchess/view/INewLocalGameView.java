package jchess.view;

import javax.swing.JDialog;

public interface INewLocalGameView extends IView {
    public void setParent(JDialog parent);
    public void setCallback(INewGameCallback oCallback);
}
