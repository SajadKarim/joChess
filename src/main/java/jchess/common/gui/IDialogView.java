package jchess.common.gui;

import javax.swing.JDialog;

public interface IDialogView extends IView {
	public JDialog getJDialog();
	public void setModal(Boolean bValue);
	public void addListener(IViewClosedListener oListener);
}
