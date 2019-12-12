package jchess.view.gamewindow;

import java.awt.Component;
import java.awt.Dimension;

/**
 * 
 * @author 	Sajad Karim
 * @since	7 Dec 2019
 */

import jchess.view.IView;

public interface IBoardView extends IView {
	public void SetDimension(Dimension oDimension);
}
