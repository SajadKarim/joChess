package jchess.common;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import jchess.GUI;

public class Piece implements IPieceData, IPiece {
	private PieceData m_oPiece;
	private IPlayer m_oPlayer;
	private Image m_oImage;
	//private Map<String, IRuleData> m_lstRules;
	
	public Piece() {
		m_oPiece = new PieceData();
	}
	
	public Piece(PieceData oPiece, IPlayer oPlayer){
		m_oPiece = oPiece;
		m_oPlayer = oPlayer;
		
		//m_lstRules = new HashMap<String, IRuleData>();
		
		populateRules();
		
		m_oImage = GUI.loadImage(getImagePath());
	}
	
	public Piece(Piece oPiece) {
		
	}
	
	public String getName() {
		return m_oPiece.getName();
	}
		
	public String getImagePath() {
		return m_oPiece.getImagePath();
	}
	
	public List< IRuleData> getAllRules(){
		return m_oPiece.getAllRules();
	}
	
	public IPlayer getPlayer() {
		return m_oPlayer;
	}
	
	public void setPlayer(IPlayer oPlayer) {
		m_oPlayer = oPlayer;
	}

	public Image getImage() {
		return m_oImage;
	}

	public void setImage(Image oImage) {
		m_oImage = oImage;
	}
	
	//public List<IRuleData> getAllRuleAgents(){
//		return m_lstRules;
	//}
	
	private void populateRules() {
		//for (Map.Entry<String, RuleData> entry2 : getAllRules().entrySet()) {
		//	m_lstRules.add(new Rule(entry2.getValue()));
		/*Iterator<IRuleData> it = getAllRules().iterator();
    	//while( it.hasNext()) {
    	//	m_lstRules.add(new RuleData(it.next()));
    	}*/
	}
	
	public void draw(Graphics g, Position oPosition)
    {
        try
        {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int x = oPosition.getShape().getTopLeftX();
            int y = oPosition.getShape().getTopLeftY();
            if (m_oImage != null && g != null)
            {            	
            	Polygon p = ((IPolygon)oPosition.getShape()).getPolygon();
            	
            	g.setClip(p);
            	g.drawImage(m_oImage, (int)p.getBounds2D().getCenterX()-30, (int)p.getBounds2D().getCenterY()-30, null);

            }
            else
            {
                System.out.println("image is null!");
            }

        }
        catch (java.lang.NullPointerException exc)
        {
            System.out.println("Something wrong when painting piece: " + exc.getMessage());
        }
    }
	
	public ArrayList allMoves() {
		return new ArrayList();
	}
	
	public PieceData getPieceData() {
		return m_oPiece;
	}

	public List<IRule> getRules() {
		return (List<IRule>)(Object)m_oPiece.getAllRules();
	}
}
