package jchess.view;

import java.awt.Component;
import java.awt.Dimension;

/**
 * 
 * @author 	Sajad Karim
 * @since	7 Dec 2019
 */

import jchess.model.IBoardModel;

public interface IBoardView extends IView {
	public void SetDimension(Dimension oDimension);
}
