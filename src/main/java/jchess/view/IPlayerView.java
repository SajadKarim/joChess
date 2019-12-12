package jchess.view;

import java.awt.Component;
import java.awt.Dimension;

import jchess.model.IPlayerModel;

/**
 * 
 * @author 	Sajad Karim
 * @since	7 Dec 2019
 */

public interface IPlayerView extends IView {
    public void setDimension(Dimension oDimension);
}
