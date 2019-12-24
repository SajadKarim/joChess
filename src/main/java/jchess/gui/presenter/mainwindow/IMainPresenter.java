package jchess.gui.presenter.mainwindow;

import java.awt.Component;

import org.jdesktop.application.View;

import jchess.common.gui.IPresenter;

public interface IMainPresenter extends IPresenter {
	public void addTab(Component oComponent, String stName);
}