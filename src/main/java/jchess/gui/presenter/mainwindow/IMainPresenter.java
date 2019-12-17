package jchess.gui.presenter.mainwindow;

import java.awt.Component;

import org.jdesktop.application.View;

public interface IMainPresenter{
	public void init();
	public View getView();
	public void addTab(Component oComponent, String stName);
}