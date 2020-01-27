package jchess.gui.presenter.mainwindow;

import java.awt.Component;

import jchess.common.gui.IPresenter;

public interface IMainPresenter extends IPresenter {
	public void addTab(Component oComponent, String stName);
	public void closetab();
}