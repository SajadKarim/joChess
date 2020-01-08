package jchess.gui;

import javax.swing.JFrame;

import jchess.common.gui.IPresenter;

/**
 * IGUIHandle gives limited access to other modules.
 * Modules can launch new windows/dialog using this interface.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public interface IGUIHandle {
	public JFrame getGUIMainFrame();
	public void showDialog(IPresenter oPresenter);
}
