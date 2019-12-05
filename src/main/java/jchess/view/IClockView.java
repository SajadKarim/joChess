package jchess.view;

import java.awt.Component;
import java.awt.Dimension;

import jchess.model.IClockModel;

public interface IClockView {
	public Component getComponent();
	public void setData(IClockModel oData);
    public void setDimension(Dimension oDimension);
}
