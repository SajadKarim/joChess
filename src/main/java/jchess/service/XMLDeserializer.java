package jchess.service;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import jchess.common.Board;
import jchess.common.IPolygon;
import jchess.common.Path;
import jchess.common.PieceAgent;
import jchess.common.Position;
import jchess.common.PositionAgent;
import jchess.common.Quadrilateral;

class XMLDeserializer {
	Board m_oBoard;
	Document m_oDocument;
	DocumentBuilder m_oBuilder;	
	DocumentBuilderFactory m_oFactory;	
	
	void serialize(Board b) {		
		try
		{
		Map<String, Position> treeMap = new TreeMap<String, Position>(b.getAllPositions());
	        
		
		for (Map.Entry<String, Position> entry : treeMap.entrySet()) {
	    		Position oPosition = entry.getValue();
	    		
	    		System.out.print("<Position File=\"" + (char)( oPosition.getFile()) + "\" Rank=\"" + oPosition.getRank() + "\" Family=\"" + oPosition.getCategory() + "\">");
	    		System.out.print("<Directions>");
	    		
				for (Map.Entry<String, Path> entry2 : oPosition.getAllPaths().entrySet()) {
				
		    		System.out.print("<Direction Name=\""+ entry2.getValue().getName()+"\" Type=\""+ entry2.getValue().getDirection()+"\">");

		    		Iterator<Position> it = entry2.getValue().getAllPositions().iterator();
		    		while( it.hasNext()) {
		    			System.out.print("<LinkedPosition>"+ it.next().getName()+"</LinkedPosition>");
		    		}
		    		
		    		System.out.print("</Direction>");
				}
	    		
	    		System.out.print("</Directions><Connections>");

	    		int i=0;
	    		Map<String, Path> tempMap = new TreeMap<String, Path>(oPosition.getAllPaths());
	    		for (Map.Entry<String, Path> entry2 : tempMap.entrySet()) {
				//for (Map.Entry<String, Path> entry2 : oPosition.getAllPaths().entrySet()) {
					if( ++i == 5)
						break;
		    		Iterator<Path> it = entry2.getValue().getAllNeighbors().iterator();
		    		while( it.hasNext()) {
		    			System.out.print("<Connection Point1=\""+ entry2.getKey() + " Point2=\"" + it.next().getName() + "\" />");
		    		}
		    		
				}

	    		
	    		System.out.print("</Connections>");
	    		System.out.print("<Shape Type=\"Quard\"><Coordinates " + oPosition.getShape().toString() +" />");

	    		System.out.print("</Shape></Position>");
		}
		}
		catch(java.lang.Exception e) {
			System.out.println(e);
		}

		System.out.println("end");
	}
	
	Board deserliaizeBoard(String stFilePath) {
		
		try
		{
			//serialize(new Board("","","","",808,700));
			m_oFactory = DocumentBuilderFactory.newInstance();
			m_oBuilder = m_oFactory.newDocumentBuilder();
			
			m_oDocument = m_oBuilder.parse(new File(stFilePath));
	
			m_oDocument.getDocumentElement().normalize();
	
			Element oRootElement = m_oDocument.getDocumentElement();
	
			populateBoardAttributes();
			
			populatePositions(oRootElement);
		}
		catch(java.lang.Exception e) {
			System.out.println(e);
		}
		
		//System.out.println("hello");
		//serialize(m_oBoard);
		
		return m_oBoard;
	}	

	void populateBoardAttributes() {
		m_oBoard = new Board("","","","",808,700);
		//m_oBoard = new Board();
	}

	void populatePositions(Element oRootElement){
		
		loadPositionsBasicDetails(oRootElement);
		
		loadPositionsConnectionDetails(oRootElement);
	}

	void loadPositionsBasicDetails(Element oRootElement){

		try
		{
		NodeList lstPositionNodes = ((Element)oRootElement.getElementsByTagName("Positions").item(0)).getElementsByTagName("Position");
		for( int nPositionIndex = 0, nPositionIndexMax = lstPositionNodes.getLength(); nPositionIndex < nPositionIndexMax; nPositionIndex++) {
		
			Position oPosition = new Position();

			Element oPositionNode = (Element)(lstPositionNodes.item(nPositionIndex));
			
			String stFile = oPositionNode.getAttributes().getNamedItem("File").getNodeValue();
			String stRank = oPositionNode.getAttributes().getNamedItem("Rank").getNodeValue();
			String stFamily = oPositionNode.getAttributes().getNamedItem("Family").getNodeValue();
			
			oPosition.setFile(stFile.charAt(0));
			oPosition.setRank(Integer.parseInt(stRank));
			oPosition.setCategory(stFamily);
						
			/*NodeList lstDirectionNodes = ((Element)oPositionNode.getElementsByTagName("Directions").item(0)).getElementsByTagName("Direction");
			for( int nDirectionIndex = 0, nDirectionIndexMax = lstPositionNodes.getLength(); nDirectionIndex < nDirectionIndexMax; nDirectionIndex++) {
				Element oDirectionNode = (Element)(lstDirectionNodes.item(nDirectionIndex));

				String stType = oDirectionNode.getAttributes().getNamedItem("Type").getNodeValue();
				String stName = oDirectionNode.getAttributes().getNamedItem("Name").getNodeValue();

				Path oPath = new Path(stName, CustomTypeMapping.convertStringToDirection(stType), null);

				NodeList lstLinkedPositionNodes = oDirectionNode.getElementsByTagName("LinkedPosition");
				for( int nLinkedPositionIndex = 0, nLinkedPositionIndexMax = lstLinkedPositionNodes.getLength(); nLinkedPositionIndex < nLinkedPositionIndexMax; nLinkedPositionIndex++) {
					String stLinkedPosition = lstLinkedPositionNodes.item(nLinkedPositionIndex).getNodeValue();
				}
				
				oPosition.addPath(oPath);
			}   

			NodeList lstConnectionNodes = ((Element)oPositionNode.getElementsByTagName("Connections").item(0)).getElementsByTagName("Connection");
			for( int nConnectionIndex = 0, nConnectionIndexMax = lstConnectionNodes.getLength(); nConnectionIndex < nConnectionIndexMax; nConnectionIndex++) {

				Element oConnectionNode = (Element)(lstConnectionNodes.item(nConnectionIndex));

				String stPoint1 = oConnectionNode.getAttributes().getNamedItem("Point1").getNodeValue();
				String stPoint2 = oConnectionNode.getAttributes().getNamedItem("Point2").getNodeValue();
				
				oPosition.addPathConnection(stPoint1, stPoint2);
			}
			*/
			Node oShapeNode = ((Element)oPositionNode.getElementsByTagName("Shape").item(0)).getElementsByTagName("Coordinates").item(0);

			String stX1 = oShapeNode.getAttributes().getNamedItem("x1").getNodeValue();
			String stY1 = oShapeNode.getAttributes().getNamedItem("y1").getNodeValue();
			String stX2 = oShapeNode.getAttributes().getNamedItem("x2").getNodeValue();
			String stY2 = oShapeNode.getAttributes().getNamedItem("y2").getNodeValue();
			String stX3 = oShapeNode.getAttributes().getNamedItem("x3").getNodeValue();
			String stY3 = oShapeNode.getAttributes().getNamedItem("y3").getNodeValue();
			String stX4 = oShapeNode.getAttributes().getNamedItem("x4").getNodeValue();
			String stY4 = oShapeNode.getAttributes().getNamedItem("y4").getNodeValue();

			Quadrilateral oShape = new Quadrilateral(Integer.parseInt(stX1), 
					Integer.parseInt(stY1), 
					Integer.parseInt(stX2), 
					Integer.parseInt(stY2), 
					Integer.parseInt(stX3), 
					Integer.parseInt(stY3), 
					Integer.parseInt(stX4), 
					Integer.parseInt(stY4));
			
			oPosition.setShape(oShape);
			
			m_oBoard.addPosition( oPosition);
		}
		}
		catch(java.lang.Exception e) {
			System.out.println(e);
		}

	}

	void loadPositionsConnectionDetails(Element oRootElement){

		try
		{
		NodeList lstPositionNodes = ((Element)oRootElement.getElementsByTagName("Positions").item(0)).getElementsByTagName("Position");
		for( int nPositionIndex = 0, nPositionIndexMax = lstPositionNodes.getLength(); nPositionIndex < nPositionIndexMax; nPositionIndex++) {
			//Position oPosition = new Position();

			Element oPositionNode = (Element)(lstPositionNodes.item(nPositionIndex));
			
			String stFile = oPositionNode.getAttributes().getNamedItem("File").getNodeValue();
			String stRank = oPositionNode.getAttributes().getNamedItem("Rank").getNodeValue();
			//String stFamily = oPositionNode.getAttributes().getNamedItem("Family").getNodeValue();
			
			/*oPosition.setFile(stFile.charAt(0));
			oPosition.setRank(Character.getNumericValue(stRank.charAt(0)));
			oPosition.setCategory(stFamily);
			*/		
			
			Position oPosition = m_oBoard.getPosition(stFile + stRank);
			NodeList lstDirectionNodes = ((Element)oPositionNode.getElementsByTagName("Directions").item(0)).getElementsByTagName("Direction");
			for( int nDirectionIndex = 0, nDirectionIndexMax = lstDirectionNodes.getLength(); nDirectionIndex < nDirectionIndexMax; nDirectionIndex++) {
				Element oDirectionNode = (Element)(lstDirectionNodes.item(nDirectionIndex));

				String stType = oDirectionNode.getAttributes().getNamedItem("Type").getNodeValue();
				String stName = oDirectionNode.getAttributes().getNamedItem("Name").getNodeValue();

				Path oPath = new Path(stName, CustomTypeMapping.convertStringToDirection(stType), null);

				NodeList lstLinkedPositionNodes = oDirectionNode.getElementsByTagName("LinkedPosition");
				for( int nLinkedPositionIndex = 0, nLinkedPositionIndexMax = lstLinkedPositionNodes.getLength(); nLinkedPositionIndex < nLinkedPositionIndexMax; nLinkedPositionIndex++) {
					String stLinkedPosition = lstLinkedPositionNodes.item(nLinkedPositionIndex).getChildNodes().item(0).getNodeValue();
					
					oPath.addPosition(m_oBoard.getPosition(stLinkedPosition));
				}
				
				oPosition.addPath(oPath);
			}   

			NodeList lstConnectionNodes = ((Element)oPositionNode.getElementsByTagName("Connections").item(0)).getElementsByTagName("Connection");
			for( int nConnectionIndex = 0, nConnectionIndexMax = lstConnectionNodes.getLength(); nConnectionIndex < nConnectionIndexMax; nConnectionIndex++) {

				Element oConnectionNode = (Element)(lstConnectionNodes.item(nConnectionIndex));

				String stPoint1 = oConnectionNode.getAttributes().getNamedItem("Point1").getNodeValue();
				String stPoint2 = oConnectionNode.getAttributes().getNamedItem("Point2").getNodeValue();
				
				oPosition.addPathConnection(stPoint1, stPoint2);
			}
			/*
			Node oShapeNode = ((Element)oPositionNode.getElementsByTagName("Shape").item(0)).getElementsByTagName("Coordinates").item(0);

			String stX1 = oShapeNode.getAttributes().getNamedItem("x1").getNodeValue();
			String stY1 = oShapeNode.getAttributes().getNamedItem("y1").getNodeValue();
			String stX2 = oShapeNode.getAttributes().getNamedItem("x2").getNodeValue();
			String stY2 = oShapeNode.getAttributes().getNamedItem("y2").getNodeValue();
			String stX3 = oShapeNode.getAttributes().getNamedItem("x3").getNodeValue();
			String stY3 = oShapeNode.getAttributes().getNamedItem("y3").getNodeValue();
			String stX4 = oShapeNode.getAttributes().getNamedItem("x4").getNodeValue();
			String stY4 = oShapeNode.getAttributes().getNamedItem("y4").getNodeValue();

			Quadrilateral oShape = new Quadrilateral(Character.getNumericValue(stX1.charAt(0)), 
					Character.getNumericValue(stY1.charAt(0)), 
					Character.getNumericValue(stX2.charAt(0)), 
					Character.getNumericValue(stY2.charAt(0)), 
					Character.getNumericValue(stX3.charAt(0)), 
					Character.getNumericValue(stY3.charAt(0)), 
					Character.getNumericValue(stX4.charAt(0)), 
					Character.getNumericValue(stY4.charAt(0)));
			
			oPosition.setShape(oShape);
			
			m_oBoard.addPosition( oPosition);
			*/
		}
		}
		catch(java.lang.Exception e) {
			System.out.println(e);
		}

	}
}
