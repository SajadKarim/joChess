package jchess.common.gui;

import java.awt.Component;

import javax.swing.JDialog;

public interface IPresenter extends IViewClosedListener{
    public void init();
    public void showView();
    public void getView();
    public Component getViewComponent();
    public JDialog tryGetViewJDialog();
}