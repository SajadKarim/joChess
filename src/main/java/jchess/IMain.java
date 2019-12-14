package jchess;

import javax.swing.JDialog;
import javax.swing.JFrame;

public interface IMain {
    public JFrame getAppMainFrame();
	public void showDialog(JDialog oDialog);
}
