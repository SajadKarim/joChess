package jchess.gui.presenter.mainwindow;

import java.awt.Component;

import jchess.common.gui.IPresenter;

/**
 * 
 * @author 	Sajad Karim
 * @since	7 Dec 2019
 */

public interface IMainPresenter extends IPresenter {
	public void addTab(Component oComponent, String stName);
	public void closetab();
}