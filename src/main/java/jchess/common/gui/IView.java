package jchess.common.gui;

import java.awt.Component;

/**
 * This interfaces exposes some generic and important methods that all the views should implement.
 * 
 * @author	Sajad Karim
 * @since	12 Dec 2019
 */

public interface IView {
	public void init();
    public void refresh();
    public void drawComponents();
	public void setViewData(IModel oData);
    public Component getViewComponent();
}
