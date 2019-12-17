package jchess;

import javax.swing.JDialog;
import javax.swing.JFrame;

import org.jdesktop.application.View;

public interface IMain {
    public JFrame getAppMainFrame();
    public void showView(View oView);
	public void showDialog(JDialog oDialog);
}
