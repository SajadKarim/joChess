package jchess.common.gui;

import java.awt.Component;

import javax.swing.JDialog;

import org.jdesktop.application.View;

public interface IPresenter extends IViewClosedListener{
    public void init();
    public void showView();
    public View tryGetJDesktopView();
    public JDialog tryGetViewJDialog();
    public Component tryGetViewComponent();
}