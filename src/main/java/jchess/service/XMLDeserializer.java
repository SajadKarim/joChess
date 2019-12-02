package jchess.service;

import java.io.File;
import java.util.Iterator;
import java.util.List;
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
import jchess.common.Piece;
import jchess.common.PieceAgent;
import jchess.common.Player;
import jchess.common.Position;
import jchess.common.PositionAgent;
import jchess.common.Quadrilateral;
import jchess.common.Rule;
import jchess.common.enumerator.Direction;
import jchess.common.enumerator.Family;
import jchess.common.enumerator.Manoeuvre;
import jchess.common.enumerator.Rank;
import jchess.common.enumerator.RuleType;

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
			
			loadRules(oRootElement);
			
			loadPieces(oRootElement);
			
			loadPlayers(oRootElement);
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

	
	void loadRules(Element oRootElement){
		populateRule(oRootElement, null);
	}
	
	void populateRule(Element oRootElement, Rule oParentRule) {
		try{			
			NodeList lstRuleNodes = ((Element)oRootElement.getElementsByTagName("Rules").item(0)).getElementsByTagName("Rule");
			for( int nRuleIndex = 0, nRuleIndexMax = lstRuleNodes.getLength(); nRuleIndex < nRuleIndexMax; nRuleIndex++) {
				Element oRuleNode = (Element)(lstRuleNodes.item(nRuleIndex));
				
				String stName = oRuleNode.getAttributes().getNamedItem("Name").getNodeValue();
				RuleType enRuleType = CustomTypeMapping.convertStringToRuleType( oRuleNode.getAttributes().getNamedItem("Type").getNodeValue());
				Direction enDirection = CustomTypeMapping.convertStringToDirection( oRuleNode.getAttributes().getNamedItem("Direction").getNodeValue());
				Manoeuvre enManoeuvre = CustomTypeMapping.convertStringToManoeuvre(  oRuleNode.getAttributes().getNamedItem("Manoeuvre").getNodeValue());
				int nMaxRecurrenceAllowed = Integer.parseInt( oRuleNode.getAttributes().getNamedItem("MaxRecurrenceAllowed").getNodeValue());
				jchess.common.enumerator.File enFile = CustomTypeMapping.convertStringToFile( oRuleNode.getAttributes().getNamedItem("File").getNodeValue());
				Rank enRank = CustomTypeMapping.convertStringToRank( oRuleNode.getAttributes().getNamedItem("Rank").getNodeValue());
				Family enFamily = CustomTypeMapping.convertStringToFamily( oRuleNode.getAttributes().getNamedItem("Family").getNodeValue());
				
				Rule oRule= new Rule(stName, enRuleType, enDirection, enManoeuvre, nMaxRecurrenceAllowed, enFamily, enFile, enRank, false, null);
				
				if( oParentRule != null)
					oParentRule.addRule( oRule);
				else
					m_oBoard.addRule( oRule);
				
				populateRule(oRuleNode, oRule);
			}
		}
		catch(java.lang.Exception e) {
			System.out.println(e);
		}
	}
	
	void loadPieces(Element oRootElement){

		try
		{
		NodeList lstPieceNodes = ((Element)oRootElement.getElementsByTagName("Pieces").item(0)).getElementsByTagName("Piece");
		for( int nPieceIndex = 0, nPieceIndexMax = lstPieceNodes.getLength(); nPieceIndex < nPieceIndexMax; nPieceIndex++) {
		
			Element oPieceNode = (Element)(lstPieceNodes.item(nPieceIndex));
			
			String stName = oPieceNode.getAttributes().getNamedItem("Name").getNodeValue();
			String stImage = oPieceNode.getAttributes().getNamedItem("Image").getNodeValue();
						
			Piece oPiece = new Piece(stName, stImage);

			NodeList lstRuleNodes = ((Element)oPieceNode.getElementsByTagName("AllowedMoves").item(0)).getElementsByTagName("Rule");
			for( int nRuleIndex = 0, nRuleIndexMax = lstRuleNodes.getLength(); nRuleIndex < nRuleIndexMax; nRuleIndex++) {
				Element oRuleNode = (Element)(lstRuleNodes.item(nRuleIndex));

				String stRule = oRuleNode.getChildNodes().item(0).getNodeValue();
				
				oPiece.addRule(m_oBoard.getRule(stRule));
			}   

			m_oBoard.addPiece( oPiece);
		}
		}
		catch(java.lang.Exception e) {
			System.out.println(e);
		}

	}

	void loadPlayers(Element oRootElement){

		try
		{
		NodeList lstPlayerNodes = ((Element)oRootElement.getElementsByTagName("Players").item(0)).getElementsByTagName("Player");
		for( int nPlayerIndex = 0, nPlayerIndexMax = lstPlayerNodes.getLength(); nPlayerIndex < nPlayerIndexMax; nPlayerIndex++) {
		
			Element oPlayerNode = (Element)(lstPlayerNodes.item(nPlayerIndex));
			
			String stName = oPlayerNode.getAttributes().getNamedItem("Name").getNodeValue();
						
			Player oPlayer = new Player(stName, "white");

			NodeList lstPieceNodes = ((Element)oPlayerNode.getElementsByTagName("Pieces").item(0)).getElementsByTagName("Piece");
			for( int nPieceIndex = 0, nPieceIndexMax = lstPieceNodes.getLength(); nPieceIndex < nPieceIndexMax; nPieceIndex++) {
				Element oPieceNode = (Element)(lstPieceNodes.item(nPieceIndex));

				String stPieceName = oPieceNode.getAttributes().getNamedItem("Name").getNodeValue();
				String stPosition = oPieceNode.getAttributes().getNamedItem("Position").getNodeValue();

				//oPlayer.se.addRule(m_oBoard.getRule(stRule));
				
				m_oBoard.addMapping(stName, stPieceName, stPosition);
			}   

			m_oBoard.addPlayer( oPlayer);
		}
		}
		catch(java.lang.Exception e) {
			System.out.println(e);
		}

	}
}
