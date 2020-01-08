package jchess.common.gui;

import javax.swing.JDialog;

/**
 * IDialogView
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public interface IDialogView extends IView {
	public JDialog getJDialog();
	public void setModal(Boolean bValue);
	public void addListener(IViewClosedListener oListener);
}
