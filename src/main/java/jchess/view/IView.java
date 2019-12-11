package jchess.view;

import java.awt.Component;

import jchess.model.IModel;

public interface IView {
	public void init();
    public void drawView();
    public void refreshView();
    public Component getViewComponent();
	public void setViewData(IModel oData);
}
