package jchess.gui.view;

import java.awt.Component;

import jchess.gui.model.IModel;

/**
 * This interfaces exposes some generic and important methods that all the views
 * should implement.
 * 
 * @author	Sajad Karim
 * @since	12 Dec 2019
 */

public interface IView {
	public void init();
    public void drawView();
    public void refreshView();
    public Component getViewComponent();
	public void setViewData(IModel oData);
}
