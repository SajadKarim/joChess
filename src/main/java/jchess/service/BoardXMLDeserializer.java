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

import jchess.cache.BoardData;
import jchess.cache.Path;
import jchess.cache.Quadrilateral;
import jchess.common.IBoard;
import jchess.common.IBoardMapping;
import jchess.common.IPath;
import jchess.common.IPathData;
import jchess.common.IPiece;
import jchess.common.IPlayer;
import jchess.common.IPosition;
import jchess.common.IRule;
import jchess.common.enumerator.Direction;
import jchess.common.enumerator.Family;
import jchess.common.enumerator.Manoeuvre;
import jchess.common.enumerator.Rank;
import jchess.common.enumerator.RuleType;

/**
 * This class loads data from XML file.
 * 
 * @author 	Sajad Karim
 * @since	7 Dec 2019
 */

class BoardXMLDeserializer {
	
	/**
	 * Following code serializes Board object.
	 * Important: Need to re-factor it.
	 * 
	 * @param oBoard
	 */
	static void serializeBoard(BoardData oBoard) {		
		try
		{
			Map<String, IPosition> treeMap = new TreeMap<String, IPosition>(oBoard.getAllPositions());

			for (Map.Entry<String, IPosition> entry : treeMap.entrySet()) {
				IPosition oPosition = entry.getValue();
	    		
	    		System.out.print("<Position File=\"" + (char)( oPosition.getFile()) + "\" Rank=\"" + oPosition.getRank() + "\" Family=\"" + oPosition.getCategory() + "\">");
	    		System.out.print("<Directions>");
	    		
				for (Map.Entry<String, IPath> entry2 : oPosition.getAllPaths().entrySet()) {
				
		    		System.out.print("<Direction Name=\""+ entry2.getValue().getName()+"\" Type=\""+ entry2.getValue().getDirection()+"\">");

		    		Iterator<IPosition> it = entry2.getValue().getAllPositions().iterator();
		    		while( it.hasNext()) {
		    			System.out.print("<LinkedPosition>"+ it.next().getName()+"</LinkedPosition>");
		    		}
		    		
		    		System.out.print("</Direction>");
				}
	    		
	    		System.out.print("</Directions><Connections>");

	    		int i=0;
	    		Map<String, IPath> tempMap = new TreeMap<String, IPath>(oPosition.getAllPaths());
	    		for (Map.Entry<String, IPath> entry2 : tempMap.entrySet()) {
				//for (Map.Entry<String, Path> entry2 : oPosition.getAllPaths().entrySet()) {
					if( ++i == 5)
						break;
		    		Iterator<IPath> it = entry2.getValue().getAllNeighbors().iterator();
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
	}
	
	static void populateBoard(String stFilePath, IBoard oBoard) {
		
		try
		{
			Document m_oDocument;
			DocumentBuilder m_oBuilder;	
			DocumentBuilderFactory m_oFactory;	

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
	}	
	
	static void populateBoardPlayerDetailsOnly(String stFilePath, IBoard oBoard) {
		
		try
		{
			Document m_oDocument;
			DocumentBuilder m_oBuilder;	
			DocumentBuilderFactory m_oFactory;	

			m_oFactory = DocumentBuilderFactory.newInstance();

			m_oBuilder = m_oFactory.newDocumentBuilder();
			
			m_oDocument = m_oBuilder.parse(new File(stFilePath));
	
			m_oDocument.getDocumentElement().normalize();
	
			Element oRootElement = m_oDocument.getDocumentElement();
	
			populateBoardAttributes(oBoard, oRootElement);
			
			//populatePositions(oBoard, oRootElement);
			
			//loadRules(oBoard, oRootElement);
			
			//loadPieces(oBoard, oRootElement);
			
			loadPlayers(oBoard, oRootElement);
			
			//oBoard.init();
		}
		catch(java.lang.Exception e) {
			System.out.println(e);
		}
	}	
	
	private static void populateBoardAttributes(IBoard oBoard, Element oRootElement) {
		
		String stName = oRootElement.getAttributes().getNamedItem("Name").getNodeValue();
		String stBoardImage = oRootElement.getAttributes().getNamedItem("BoardImage").getNodeValue();
		String stCellSelectionImage = oRootElement.getAttributes().getNamedItem("ActiveCellImage").getNodeValue();
		String stMoveCandidateImage = oRootElement.getAttributes().getNamedItem("MarkedCellImage").getNodeValue();
		int nWidth = Integer.parseInt( oRootElement.getAttributes().getNamedItem("Width").getNodeValue());
		int nHeight = Integer.parseInt( oRootElement.getAttributes().getNamedItem("Height").getNodeValue());
		String stRuleEngine = oRootElement.getAttributes().getNamedItem("RuleEngine").getNodeValue();
		
		oBoard.getBoardData().setName(stName);
		oBoard.getBoardData().setBoardImagePath(stBoardImage);
		oBoard.getBoardData().setActivCellImagePath(stCellSelectionImage);
		oBoard.getBoardData().setMarkedCellImagePath(stMoveCandidateImage);
		oBoard.getBoardData().setBoardWidth(nWidth);
		oBoard.getBoardData().setBoardHeight(nHeight);
		oBoard.getBoardData().setRuleEngineName(stRuleEngine);
	}

	private static void populatePositions(IBoard oBoard, Element oRootElement){
		
		loadPositionsBasicDetails(oBoard, oRootElement);
		
		loadPositionsConnectionDetails(oBoard, oRootElement);
	}

	private static void loadPositionsBasicDetails(IBoard oBoard, Element oRootElement){

		try
		{
		NodeList lstPositionNodes = ((Element)oRootElement.getElementsByTagName("Positions").item(0)).getElementsByTagName("Position");
		for( int nPositionIndex = 0, nPositionIndexMax = lstPositionNodes.getLength(); nPositionIndex < nPositionIndexMax; nPositionIndex++) {
		
			IPosition oPosition = oBoard.createPosition();

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
			
			oBoard.getBoardData().addPosition(oPosition);
		}
		}
		catch(java.lang.Exception e) {
			System.out.println(e);
		}

	}

	private static void loadPositionsConnectionDetails(IBoard oBoard, Element oRootElement){

		try
		{
			NodeList lstPositionNodes = ((Element)oRootElement.getElementsByTagName("Positions").item(0)).getElementsByTagName("Position");
			for( int nPositionIndex = 0, nPositionIndexMax = lstPositionNodes.getLength(); nPositionIndex < nPositionIndexMax; nPositionIndex++) {
	
				Element oPositionNode = (Element)(lstPositionNodes.item(nPositionIndex));
				
				String stFile = oPositionNode.getAttributes().getNamedItem("File").getNodeValue();
				String stRank = oPositionNode.getAttributes().getNamedItem("Rank").getNodeValue();
				
				IPosition oPosition = oBoard.getPosition(stFile + stRank);
				NodeList lstDirectionNodes = ((Element)oPositionNode.getElementsByTagName("Directions").item(0)).getElementsByTagName("Direction");
				for( int nDirectionIndex = 0, nDirectionIndexMax = lstDirectionNodes.getLength(); nDirectionIndex < nDirectionIndexMax; nDirectionIndex++) {
					Element oDirectionNode = (Element)(lstDirectionNodes.item(nDirectionIndex));
	
					String stType = oDirectionNode.getAttributes().getNamedItem("Type").getNodeValue();
					String stName = oDirectionNode.getAttributes().getNamedItem("Name").getNodeValue();
	
					IPathData oPath = new Path(stName, StringToEnum.convertStringToDirection(stType), null);
	
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

	
	private static void loadRules(IBoard oBoard, Element oRootElement){
		populateRule(oBoard, oRootElement, null);
	}
	
	private static void populateRule(IBoard oBoard, Element oRootElement, IRule oParentRule) {
		try{			
			NodeList lstRuleNodes = ((Element)oRootElement.getElementsByTagName("Rules").item(0)).getElementsByTagName("Rule");
			for( int nRuleIndex = 0, nRuleIndexMax = lstRuleNodes.getLength(); nRuleIndex < nRuleIndexMax; nRuleIndex++) {
				Element oRuleNode = (Element)(lstRuleNodes.item(nRuleIndex));
				
				String stName = oRuleNode.getAttributes().getNamedItem("Name").getNodeValue();
				RuleType enRuleType = StringToEnum.convertStringToRuleType( oRuleNode.getAttributes().getNamedItem("Type").getNodeValue());
				Direction enDirection = StringToEnum.convertStringToDirection( oRuleNode.getAttributes().getNamedItem("Direction").getNodeValue());
				Manoeuvre enManoeuvre = StringToEnum.convertStringToManoeuvre(  oRuleNode.getAttributes().getNamedItem("Manoeuvre").getNodeValue());
				int nMaxRecurrenceAllowed = Integer.parseInt( oRuleNode.getAttributes().getNamedItem("MaxRecurrenceAllowed").getNodeValue());
				jchess.common.enumerator.File enFile = StringToEnum.convertStringToFile( oRuleNode.getAttributes().getNamedItem("File").getNodeValue());
				Rank enRank = StringToEnum.convertStringToRank( oRuleNode.getAttributes().getNamedItem("Rank").getNodeValue());
				Family enFamily = StringToEnum.convertStringToFamily( oRuleNode.getAttributes().getNamedItem("Family").getNodeValue());
				
				IRule oRule = oBoard.createRule();
				
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
	
	private static void loadPieces(IBoard oBoard, Element oRootElement){

		try
		{
		NodeList lstPieceNodes = ((Element)oRootElement.getElementsByTagName("Pieces").item(0)).getElementsByTagName("Piece");
		for( int nPieceIndex = 0, nPieceIndexMax = lstPieceNodes.getLength(); nPieceIndex < nPieceIndexMax; nPieceIndex++) {
		
			Element oPieceNode = (Element)(lstPieceNodes.item(nPieceIndex));
			
			String stName = oPieceNode.getAttributes().getNamedItem("Name").getNodeValue();
			String stImage = oPieceNode.getAttributes().getNamedItem("Image").getNodeValue();
			
			IPiece oPiece = oBoard.createPiece();
			
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

	private static void loadPlayers(IBoard oBoard, Element oRootElement){

		try{
			NodeList lstPlayerNodes = ((Element)oRootElement.getElementsByTagName("Players").item(0)).getElementsByTagName("Player");
			for( int nPlayerIndex = 0, nPlayerIndexMax = lstPlayerNodes.getLength(); nPlayerIndex < nPlayerIndexMax; nPlayerIndex++) {
			
				Element oPlayerNode = (Element)(lstPlayerNodes.item(nPlayerIndex));
				
				String stName = oPlayerNode.getAttributes().getNamedItem("Name").getNodeValue();
							
				IPlayer oPlayer = oBoard.createPlayer();
				oPlayer.getPlayerData().setName(stName);
				//oPlayer.getPlayerData().setColor("white");
	
				try {
					NodeList lstPieceNodes = ((Element)oPlayerNode.getElementsByTagName("Pieces").item(0)).getElementsByTagName("Piece");
					for( int nPieceIndex = 0, nPieceIndexMax = lstPieceNodes.getLength(); nPieceIndex < nPieceIndexMax; nPieceIndex++) {
						Element oPieceNode = (Element)(lstPieceNodes.item(nPieceIndex));
		
						String stPieceName = oPieceNode.getAttributes().getNamedItem("Name").getNodeValue();
						String stPosition = oPieceNode.getAttributes().getNamedItem("Position").getNodeValue();
		
						//oPlayer.getPlayerData().addRule(oBoard.getBoardData().getRule(stRule));
						
						oBoard.getBoardData().addMapping(stName, stPieceName, stPosition);
					}   
					
					NodeList lstMappingNodes = ((Element)oPlayerNode.getElementsByTagName("BoardMapping").item(0)).getElementsByTagName("Map");
					for( int nMappingIndex = 0, nMappingIndexMax = lstMappingNodes.getLength(); nMappingIndex < nMappingIndexMax; nMappingIndex++) {
						Element oMappingNode = (Element)(lstMappingNodes.item(nMappingIndex));
		
						String stTo = oMappingNode.getAttributes().getNamedItem("To").getNodeValue();
						String stFrom = oMappingNode.getAttributes().getNamedItem("From").getNodeValue();
		
						int nTo = tryParseInt(stTo);
						int nFrom = tryParseInt(stFrom);
						
						//oPlayer.getPlayerData().addRule(oBoard.getBoardData().getRule(stRule));
						
						oPlayer.getPlayerData().addBoardMapping(nFrom, nTo);
					}   
				}
				catch(java.lang.Exception e) {
					System.out.println(e);
				}
				/*if( oPlayer.getName().equals("P2"))
					populateCustomBoardMapping2( oPlayer.getPlayerData().getBoardMapping());
				if( oPlayer.getName().equals("P3"))
					populateCustomBoardMapping3( oPlayer.getPlayerData().getBoardMapping());
	*/
				oBoard.getBoardData().addPlayer( oPlayer);
			}
		}
		catch(java.lang.Exception e) {
			System.out.println(e);
		}
	}
	
	private static int tryParseInt(String stValue) {
		
		try {
			return Integer.parseInt(stValue);
			
		} catch(java.lang.Exception e) {
			
		}
		
		try {
			return (int)stValue.charAt(0);
			
		} catch(java.lang.Exception e) {
			
		}
		return -1;
	}
}
