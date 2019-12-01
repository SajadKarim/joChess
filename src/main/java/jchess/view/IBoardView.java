package jchess.view;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import jchess.common.IPolygon;
import jchess.common.PositionAgent;

public interface IBoardView {
	public void paintComponent(Graphics g, PositionAgent oPosition, ArrayList<PositionAgent> lstPositions);
}
