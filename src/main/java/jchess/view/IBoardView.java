package jchess.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import jchess.model.IBoardModel;

public interface IBoardView {
	public void setData(IBoardModel oBoardModel);
	public void SetDimension(Dimension oDimension);
    public Component getComponent();
}
