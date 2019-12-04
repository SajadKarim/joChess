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

import jchess.common.BoardData;
import jchess.common.IBoardData;
import jchess.common.IPathData;
import jchess.common.IPieceData;
import jchess.common.IPlayerData;
import jchess.common.IPolygon;
import jchess.common.IPositionData;
import jchess.common.IRuleData;
import jchess.common.PathData;
import jchess.common.PieceData;
import jchess.common.Piece;
import jchess.common.PlayerData;
import jchess.common.PositionData;
import jchess.common.Position;
import jchess.common.Quadrilateral;
import jchess.common.RuleData;
import jchess.common.enumerator.Direction;
import jchess.common.enumerator.Family;
import jchess.common.enumerator.Manoeuvre;
import jchess.common.enumerator.Rank;
import jchess.common.enumerator.RuleType;

class XMLDeserializer {
	
	static void serialize(BoardData b) {		
		try
		{
		/*Map<String, PositionData> treeMap = new TreeMap<String, PositionData>(b.getAllPositions());
	        
		
		for (Map.Entry<String, PositionData> entry : treeMap.entrySet()) {
	    		PositionData oPosition = entry.getValue();
	    		
	    		System.out.print("<Position File=\"" + (char)( oPosition.getFile()) + "\" Rank=\"" + oPosition.getRank() + "\" Family=\"" + oPosition.getCategory() + "\">");
	    		System.out.print("<Directions>");
	    		
				for (Map.Entry<String, Path> entry2 : oPosition.getAllPaths().entrySet()) {
				
		    		System.out.print("<Direction Name=\""+ entry2.getValue().getName()+"\" Type=\""+ entry2.getValue().getDirection()+"\">");

		    		Iterator<PositionData> it = entry2.getValue().getAllPositions().iterator();
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
		}*/
		}
		catch(java.lang.Exception e) {
			System.out.println(e);
		}

		System.out.println("end");
	}
	
	static void populateBoard(String stFilePath, IBoardData oBoard) {
		
		try
		{
			Document m_oDocument;
			DocumentBuilder m_oBuilder;	
			DocumentBuilderFactory m_oFactory;	

			//m_oBoard = oBoard;
			
			//serialize(new Board("","","","",808,700));
			m_oFactory = DocumentBuilderFactory.newInstance();
			m_oBuilder = m_oFactory.newDocumentBuilder();
			
			m_oDocument = m_oBuilder.parse(new File(stFilePath));
	
			m_oDocument.getDocumentElement().normalize();
	
			Element oRootElement = m_oDocument.getDocumentElement();
	
			populateBoardAttributes(oBoard, oRootElement);
			
			populatePositions(oBoard, oRootElement);
			
			loadRules(oBoard, oRootElement);
			
			loadPieces(oBoard, oRootElement);
			
			loadPlayers(oBoard, oRootElement);
			
			oBoard.init();
		}
		catch(java.lang.Exception e) {
			System.out.println(e);
		}
		
		//System.out.println("hello");
		//serialize(m_oBoard);
		
		//return m_oBoard;
	}	

	private static void populateBoardAttributes(IBoardData oBoard, Element oRootElement) {
		
		String stName = oRootElement.getAttributes().getNamedItem("Name").getNodeValue();
		String stBoardImage = oRootElement.getAttributes().getNamedItem("BoardImage").getNodeValue();
		String stCellSelectionImage = oRootElement.getAttributes().getNamedItem("ActiveCellImage").getNodeValue();
		String stMoveCandidateImage = oRootElement.getAttributes().getNamedItem("MarkedCellImage").getNodeValue();
		int nWidth = Integer.parseInt( oRootElement.getAttributes().getNamedItem("Width").getNodeValue());
		int nHeight = Integer.parseInt( oRootElement.getAttributes().getNamedItem("Height").getNodeValue());
		
		oBoard.getBoardData().setName(stName);
		oBoard.getBoardData().setBoardImagePath(stBoardImage);
		oBoard.getBoardData().setActivCellImagePath(stCellSelectionImage);
		oBoard.getBoardData().setMarkedCellImagePath(stMoveCandidateImage);
		oBoard.getBoardData().setBoardWidth(nWidth);
		oBoard.getBoardData().setBoardHeight(nHeight);
	}

	private static void populatePositions(IBoardData oBoard, Element oRootElement){
		
		loadPositionsBasicDetails(oBoard, oRootElement);
		
		loadPositionsConnectionDetails(oBoard, oRootElement);
	}

	private static void loadPositionsBasicDetails(IBoardData oBoard, Element oRootElement){

		try
		{
		NodeList lstPositionNodes = ((Element)oRootElement.getElementsByTagName("Positions").item(0)).getElementsByTagName("Position");
		for( int nPositionIndex = 0, nPositionIndexMax = lstPositionNodes.getLength(); nPositionIndex < nPositionIndexMax; nPositionIndex++) {
		
			IPositionData oPosition = oBoard.createPosition();

			Element oPositionNode = (Element)(lstPositionNodes.item(nPositionIndex));
			
			String stFile = oPositionNode.getAttributes().getNamedItem("File").getNodeValue();
			String stRank = oPositionNode.getAttributes().getNamedItem("Rank").getNodeValue();
			String stFamily = oPositionNode.getAttributes().getNamedItem("Family").getNodeValue();
			
			oPosition.getPosition().setFile(stFile.charAt(0));
			oPosition.getPosition().setRank(Integer.parseInt(stRank));
			oPosition.getPosition().setCategory(stFamily);
						
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
			
			oPosition.getPosition().setShape(oShape);
			
			oBoard.addPosition(oPosition);
		}
		}
		catch(java.lang.Exception e) {
			System.out.println(e);
		}

	}

	private static void loadPositionsConnectionDetails(IBoardData oBoard, Element oRootElement){

		try
		{
			NodeList lstPositionNodes = ((Element)oRootElement.getElementsByTagName("Positions").item(0)).getElementsByTagName("Position");
			for( int nPositionIndex = 0, nPositionIndexMax = lstPositionNodes.getLength(); nPositionIndex < nPositionIndexMax; nPositionIndex++) {
	
				Element oPositionNode = (Element)(lstPositionNodes.item(nPositionIndex));
				
				String stFile = oPositionNode.getAttributes().getNamedItem("File").getNodeValue();
				String stRank = oPositionNode.getAttributes().getNamedItem("Rank").getNodeValue();
				
				IPositionData oPosition = oBoard.getPosition(stFile + stRank);
				NodeList lstDirectionNodes = ((Element)oPositionNode.getElementsByTagName("Directions").item(0)).getElementsByTagName("Direction");
				for( int nDirectionIndex = 0, nDirectionIndexMax = lstDirectionNodes.getLength(); nDirectionIndex < nDirectionIndexMax; nDirectionIndex++) {
					Element oDirectionNode = (Element)(lstDirectionNodes.item(nDirectionIndex));
	
					String stType = oDirectionNode.getAttributes().getNamedItem("Type").getNodeValue();
					String stName = oDirectionNode.getAttributes().getNamedItem("Name").getNodeValue();
	
					IPathData oPath = new PathData(stName, CustomTypeMapping.convertStringToDirection(stType), null);
	
					NodeList lstLinkedPositionNodes = oDirectionNode.getElementsByTagName("LinkedPosition");
					for( int nLinkedPositionIndex = 0, nLinkedPositionIndexMax = lstLinkedPositionNodes.getLength(); nLinkedPositionIndex < nLinkedPositionIndexMax; nLinkedPositionIndex++) {
						String stLinkedPosition = lstLinkedPositionNodes.item(nLinkedPositionIndex).getChildNodes().item(0).getNodeValue();
						
						oPath.addPosition(oBoard.getPosition(stLinkedPosition));
					}
					
					oPosition.getPosition().addPath(oPath);
				}   
	
				NodeList lstConnectionNodes = ((Element)oPositionNode.getElementsByTagName("Connections").item(0)).getElementsByTagName("Connection");
				for( int nConnectionIndex = 0, nConnectionIndexMax = lstConnectionNodes.getLength(); nConnectionIndex < nConnectionIndexMax; nConnectionIndex++) {
	
					Element oConnectionNode = (Element)(lstConnectionNodes.item(nConnectionIndex));
	
					String stPoint1 = oConnectionNode.getAttributes().getNamedItem("Point1").getNodeValue();
					String stPoint2 = oConnectionNode.getAttributes().getNamedItem("Point2").getNodeValue();
					
					oPosition.getPosition().linkPaths(stPoint1, stPoint2);
				}
			}
		}
		catch(java.lang.Exception e) {
			System.out.println(e);
		}

	}

	
	private static void loadRules(IBoardData oBoard, Element oRootElement){
		populateRule(oBoard, oRootElement, null);
	}
	
	private static void populateRule(IBoardData oBoard, Element oRootElement, IRuleData oParentRule) {
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
				
				IRuleData oRule = oBoard.createRule();
				
				oRule.getRuleData().setName(stName);
				oRule.getRuleData().setRuleType(enRuleType);
				oRule.getRuleData().setDirection(enDirection);
				oRule.getRuleData().setManoeuvreStrategy(enManoeuvre);
				oRule.getRuleData().setMaxRecurrenceCount(nMaxRecurrenceAllowed);
				oRule.getRuleData().setFamily(enFamily);
				oRule.getRuleData().setFile(enFile);
				oRule.getRuleData().setRank(enRank);
				
				if( oParentRule != null)
					oParentRule.getRuleData().addRule( oRule);
				else
					oBoard.getBoardData().addRule( oRule);
				
				populateRule(oBoard, oRuleNode, oRule);
			}
		}
		catch(java.lang.Exception e) {
			System.out.println(e);
		}
	}
	
	private static void loadPieces(IBoardData oBoard, Element oRootElement){

		try
		{
		NodeList lstPieceNodes = ((Element)oRootElement.getElementsByTagName("Pieces").item(0)).getElementsByTagName("Piece");
		for( int nPieceIndex = 0, nPieceIndexMax = lstPieceNodes.getLength(); nPieceIndex < nPieceIndexMax; nPieceIndex++) {
		
			Element oPieceNode = (Element)(lstPieceNodes.item(nPieceIndex));
			
			String stName = oPieceNode.getAttributes().getNamedItem("Name").getNodeValue();
			String stImage = oPieceNode.getAttributes().getNamedItem("Image").getNodeValue();
			
			IPieceData oPiece = oBoard.createPiece();
			
			oPiece.getPieceData().setName(stName);
			oPiece.getPieceData().setImagePath(stImage);

			NodeList lstRuleNodes = ((Element)oPieceNode.getElementsByTagName("AllowedMoves").item(0)).getElementsByTagName("Rule");
			for( int nRuleIndex = 0, nRuleIndexMax = lstRuleNodes.getLength(); nRuleIndex < nRuleIndexMax; nRuleIndex++) {
				Element oRuleNode = (Element)(lstRuleNodes.item(nRuleIndex));

				String stRule = oRuleNode.getChildNodes().item(0).getNodeValue();
				
				oPiece.getPieceData().addRule(oBoard.getRule(stRule));
			}   

			oBoard.getBoardData().addPiece( oPiece);
		}
		}
		catch(java.lang.Exception e) {
			System.out.println(e);
		}

	}

	private static void loadPlayers(IBoardData oBoard, Element oRootElement){

		try
		{
		NodeList lstPlayerNodes = ((Element)oRootElement.getElementsByTagName("Players").item(0)).getElementsByTagName("Player");
		for( int nPlayerIndex = 0, nPlayerIndexMax = lstPlayerNodes.getLength(); nPlayerIndex < nPlayerIndexMax; nPlayerIndex++) {
		
			Element oPlayerNode = (Element)(lstPlayerNodes.item(nPlayerIndex));
			
			String stName = oPlayerNode.getAttributes().getNamedItem("Name").getNodeValue();
						
			IPlayerData oPlayer = oBoard.createPlayer();
			oPlayer.getPlayerData().setName(stName);
			oPlayer.getPlayerData().setColor("white");

			NodeList lstPieceNodes = ((Element)oPlayerNode.getElementsByTagName("Pieces").item(0)).getElementsByTagName("Piece");
			for( int nPieceIndex = 0, nPieceIndexMax = lstPieceNodes.getLength(); nPieceIndex < nPieceIndexMax; nPieceIndex++) {
				Element oPieceNode = (Element)(lstPieceNodes.item(nPieceIndex));

				String stPieceName = oPieceNode.getAttributes().getNamedItem("Name").getNodeValue();
				String stPosition = oPieceNode.getAttributes().getNamedItem("Position").getNodeValue();

				//oPlayer.getPlayerData().addRule(oBoard.getBoardData().getRule(stRule));
				
				oBoard.getBoardData().addMapping(stName, stPieceName, stPosition);
			}   

			oBoard.getBoardData().addPlayer( oPlayer);
		}
		}
		catch(java.lang.Exception e) {
			System.out.println(e);
		}

	}
}
