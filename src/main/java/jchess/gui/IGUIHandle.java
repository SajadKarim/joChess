package jchess.gui;

import javax.swing.JFrame;

import jchess.common.gui.IPresenter;

public interface IGUIHandle {
	public JFrame getGUIMainFrame();
	public void showDialog(IPresenter oPresenter);
}
