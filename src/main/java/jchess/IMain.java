package jchess;

import javax.swing.JDialog;
import javax.swing.JFrame;

import org.jdesktop.application.View;

/**
 * Interface for Main.java
 * 
 * @author 	Sajad Karim
 * @since	7 Dec 2019
 */
public interface IMain {
    public JFrame getAppMainFrame();
    public void showView(View oView);
	public void showDialog(JDialog oDialog);
}
