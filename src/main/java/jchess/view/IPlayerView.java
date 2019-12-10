package jchess.view;

import java.awt.Component;
import java.awt.Dimension;

import jchess.model.IPlayerModel;

public interface IPlayerView {
	public Component getComponent();
	public void setData(IPlayerModel oData);
    public void setDimension(Dimension oDimension);
}
