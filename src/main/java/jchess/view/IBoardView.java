package jchess.view;

import java.awt.Component;

import jchess.model.IBoardModel;

public interface IBoardView {
	public void setModelData(IBoardModel oBoardModel);
    public void addListener(final BoardViewListener oListener);
    public void paintView();
    public Component getViewComponent();
}
