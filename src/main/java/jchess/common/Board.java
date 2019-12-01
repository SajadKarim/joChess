package jchess.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jchess.GUI;
import jchess.common.enumerator.Direction;

public class Board implements IBoard {
	private int m_nWidth;
	private int m_nHeight;
	private String m_stName;
	private String m_stBoardImagePath;
	private String m_stActiveCellImagePath;
	private String m_stMarkedCellImagePath;	
    private Map<String, Position> m_mpPosition;
    private Map<String, Player> m_mpPlayers;
    private Map<String, Piece> m_mpPieces;
    private Map<String, Map<String, String>> m_mpMapping;
    
    
	public Board(String stName, String stBoardImagePath, String stActiveCellImagePath, String stMarkedCellImagePath, int nWidth, int nHeight) {
		m_nWidth = nWidth;
		m_nHeight = nHeight;
		m_stName = stName;
		m_stBoardImagePath= stBoardImagePath;
		m_stActiveCellImagePath = stActiveCellImagePath;
		m_stMarkedCellImagePath = stMarkedCellImagePath;
	
    	m_mpPosition = new HashMap<String, Position>();

    	m_mpPlayers = new HashMap<String, Player>();
    	
    	m_mpPieces = new HashMap<String, Piece>();

    	m_mpMapping = new HashMap<String, Map<String, String>>();
    	
		m_nWidth = 808;
		m_nHeight = 700;
		m_stBoardImagePath = "chessboard.png";
		m_stActiveCellImagePath = "sel_square.png";
		m_stMarkedCellImagePath = "able_square.png";
		
		populatePositions();
		populatePlayers();
		populatePieces();
		populateMappings();
	}
	
	public String getName() {
		return m_stName;
	}
	
	public String getBoardImagePath() {
		return m_stBoardImagePath;
	}

	public String getActivCellImagePath() {
		return m_stActiveCellImagePath;
	}

	public String getMarkedCellImagePath() {
		return m_stMarkedCellImagePath;
	}
	
	public Position getPosition(String stName) {
		return m_mpPosition.get(stName);
	}
	
	public Map<String, Position> getAllPositions() {
		return m_mpPosition;
	}
	
	public Map<String, Player> getAllPlayers() {
		return m_mpPlayers;
	}

	public Map<String, String> getPlayerMapping(String stName) {
		return m_mpMapping.get(stName);
	}

	public Piece getPiece(String stName) {
		return m_mpPieces.get(stName);
	}
	
	public int getBoardWidth() {
		return m_nWidth;
	}

	public int getBoardHeight() {
		return m_nHeight;
	}
	
    public void populatePositions() {
    	Position oPosition = new Position('a',1, new Quadrilateral(217, 674, 264, 674, 245, 624, 193, 633), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
    	oPosition = new Position('a',2, new Quadrilateral(193, 633, 245, 624, 228, 572, 171, 593), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('a',3, new Quadrilateral(171, 593, 228, 572, 210, 522, 148, 552), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('a',4, new Quadrilateral(148, 552, 210, 522, 193, 472, 124, 512), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('a',5, new Quadrilateral(124, 512, 193, 472, 159, 431, 101, 471), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('a',6, new Quadrilateral(101, 471, 159, 431, 123, 391, 77, 431), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('a',7, new Quadrilateral(77, 431, 123, 391, 88, 351, 54, 389), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('a',8, new Quadrilateral(54, 389, 88, 351, 54, 310, 31, 349), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);

        oPosition = new Position('b',1, new Quadrilateral(264, 674, 310, 674, 298, 613, 246, 624), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('b',2, new Quadrilateral(246, 624, 298, 613, 287, 553, 228, 572), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('b',3, new Quadrilateral(228, 572, 287, 553, 275, 492, 211, 522), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('b',4, new Quadrilateral(211, 522, 275, 492, 263, 432, 194, 471), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('b',5, new Quadrilateral(194, 471, 263, 432, 219, 390, 159, 431), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('b',6, new Quadrilateral(159, 431, 219, 390, 171, 350, 124, 390), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('b',7, new Quadrilateral(124, 390, 171, 350, 123, 310, 88, 350), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('b',8, new Quadrilateral(88, 350, 123, 310, 77, 270, 54, 310), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
                                         
        oPosition = new Position('c',1, new Quadrilateral(310, 674, 356, 674, 351, 603, 299, 613), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('c',2, new Quadrilateral(299, 613, 351, 603, 344, 533, 287, 552), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('c',3, new Quadrilateral(287, 552, 344, 533, 340, 462, 276, 492), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('c',4, new Quadrilateral(276, 492, 340, 462, 334, 391, 264, 431), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('c',5, new Quadrilateral(264, 431, 334, 391, 275, 351, 218, 390), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('c',6, new Quadrilateral(218, 390, 275, 351, 217, 310, 170, 349), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('c',7, new Quadrilateral(170, 349, 217, 310, 159, 270, 124, 309), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('c',8, new Quadrilateral(124, 309, 159, 270, 100, 230, 78, 269), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
                                         
        oPosition = new Position('d',1, new Quadrilateral(357, 674, 404, 674, 404, 593, 352, 603), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('d',2, new Quadrilateral(352, 603, 404, 593, 404, 512, 345, 532), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('d',3, new Quadrilateral(345, 532, 404, 512, 404, 432, 340, 461), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('d',4, new Quadrilateral(340, 461, 404, 432, 404, 350, 333, 390), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('d',5, new Quadrilateral(333, 390, 404, 350, 334, 311, 276, 350), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('d',6, new Quadrilateral(276, 350, 334, 311, 263, 270, 218, 309), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('d',7, new Quadrilateral(218, 309, 263, 270, 194, 224, 159, 269), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('d',8, new Quadrilateral(159, 269, 194, 224, 124, 188, 100, 228), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
                                         
        oPosition = new Position('e',1, new Quadrilateral(404, 674, 450, 674, 456, 603, 404, 593), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('e',2, new Quadrilateral(404, 593, 456, 603, 462, 532, 404, 512), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('e',3, new Quadrilateral(404, 512, 462, 532, 468, 461, 404, 431), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('e',4, new Quadrilateral(404, 431, 468, 461, 474, 391, 404, 350), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('e',9, new Quadrilateral(404, 350, 474, 391, 531, 350, 474, 310), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('e',10, new Quadrilateral(474, 310, 531, 350, 590, 309, 544, 270), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('e',11, new Quadrilateral(544, 270, 590, 309, 649, 269, 615, 228), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('e',12, new Quadrilateral(651, 269, 707, 228, 684, 188, 614, 228), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
                                         
        oPosition = new Position('f',1, new Quadrilateral(451, 674, 497, 674, 509, 613, 457, 603), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('f',2, new Quadrilateral(457, 603, 509, 613, 521, 553, 464, 533), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('f',3, new Quadrilateral(464, 533, 521, 553, 532, 492, 468, 461), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('f',4, new Quadrilateral(468, 461, 532, 492, 544, 431, 475, 391), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('f',9, new Quadrilateral(475, 391, 544, 431, 591, 390, 532, 350), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('f',10, new Quadrilateral(532, 350, 591, 390, 637, 350, 591, 310), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('f',11, new Quadrilateral(591, 310, 637, 350, 684, 309, 650, 269), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('f',12, new Quadrilateral(685, 309, 730, 270, 707, 228, 651, 269), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
                                         
        oPosition = new Position('g',1, new Quadrilateral(497, 674, 544, 674, 561, 623, 509, 614), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('g',2, new Quadrilateral(509, 614, 561, 623, 579, 573, 521, 552), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('g',3, new Quadrilateral(521, 552, 579, 573, 596, 522, 533, 492), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('g',4, new Quadrilateral(533, 492, 596, 522, 614, 471, 545, 431), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('g',9, new Quadrilateral(545, 431, 614, 471, 649, 431, 592, 391), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('g',10, new Quadrilateral(592, 391, 649, 431, 685, 391, 638, 350), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('g',11, new Quadrilateral(638, 350, 685, 391, 720, 350, 684, 310), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('g',12, new Quadrilateral(720, 350, 755, 310, 730, 270, 685, 309), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
                                         
        oPosition = new Position('h',1, new Quadrilateral(544, 674, 591, 674, 613, 633, 561, 623), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('h',2, new Quadrilateral(561, 623, 613, 633, 637, 592, 580, 573), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('h',3, new Quadrilateral(580, 573, 637, 592, 660, 552, 597, 522), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('h',4, new Quadrilateral(597, 522, 660, 552, 683, 511, 614, 472), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('h',9, new Quadrilateral(614, 472, 683, 511, 707, 471, 650, 431), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('h',10, new Quadrilateral(650, 431, 707, 471, 730, 430, 684, 391), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('h',11, new Quadrilateral(684, 391, 730, 430, 754, 390, 719, 350), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('h',12, new Quadrilateral(719, 350, 754, 390, 778, 349, 755, 310), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
                                         
        oPosition = new Position('i',8, new Quadrilateral(124, 188, 194, 229, 212, 177, 147, 148), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('i',7, new Quadrilateral(194, 229, 263, 268, 275, 209, 212, 177), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('i',6, new Quadrilateral(263, 268, 334, 309, 339, 239, 275, 209), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('i',5, new Quadrilateral(334, 309, 403, 349, 403, 269, 339, 239), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('i',9, new Quadrilateral(403, 349, 474, 310, 467, 239, 403, 269), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('i',10, new Quadrilateral(474, 310, 544, 268, 533, 209, 467, 239), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('i',11, new Quadrilateral(544, 268, 614, 228, 597, 178, 533, 209), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('i',12, new Quadrilateral(614, 228, 684, 188, 660, 148, 597, 178), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
                                         
        oPosition = new Position('j',8, new Quadrilateral(147, 148, 212, 177, 228, 126, 171, 107), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('j',7, new Quadrilateral(212, 177, 276, 207, 287, 148, 228, 126), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('j',6, new Quadrilateral(275, 208, 339, 239, 345, 168, 287, 148), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('j',5, new Quadrilateral(339, 239, 403, 269, 403, 188, 345, 168), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('j',9, new Quadrilateral(403, 268, 467, 239, 462, 169, 403, 188), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('j',10, new Quadrilateral(467, 239, 533, 209, 520, 147, 462, 169), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('j',11, new Quadrilateral(533, 209, 597, 178, 578, 128, 520, 147), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('j',12, new Quadrilateral(597, 178, 660, 147, 638, 108, 578, 128), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
                                         
        oPosition = new Position('k',8, new Quadrilateral(171, 107, 228, 126, 245, 76, 194, 66), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('k',7, new Quadrilateral(228, 126, 287, 148, 299, 85, 245, 76), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('k',6, new Quadrilateral(287, 148, 345, 168, 351, 96, 299, 86), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('k',5, new Quadrilateral(345, 168, 403, 188, 404, 107, 351, 96), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('k',9, new Quadrilateral(403, 188, 462, 169, 456, 98, 404, 107), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('k',10, new Quadrilateral(462, 169, 520, 147, 508, 87, 456, 98), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('k',11, new Quadrilateral(520, 147, 578, 128, 561, 77, 508, 87), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('k',12, new Quadrilateral(578, 128, 637, 107, 614, 66, 561, 77), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
                                         
        oPosition = new Position('l',8, new Quadrilateral(194, 66, 245, 76, 264, 26, 217, 26), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('l',7, new Quadrilateral(245, 76, 299, 86, 310, 26, 264, 26), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('l',6, new Quadrilateral(299, 86, 351, 96, 357, 26, 310, 26), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('l',5, new Quadrilateral(351, 96, 404, 107, 403, 26, 357, 26), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('l',9, new Quadrilateral(404, 107, 456, 98, 451, 26, 403, 26), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('l',10, new Quadrilateral(456, 98, 508, 87, 498, 26, 451, 26), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('l',11, new Quadrilateral(508, 87, 561, 77, 545, 27, 498, 26), "Black");
    	m_mpPosition.put(oPosition.getName(), oPosition);
        oPosition = new Position('l',12, new Quadrilateral(561, 77, 614, 66, 591, 26, 545, 27), "White");
    	m_mpPosition.put(oPosition.getName(), oPosition);

        
        m_mpPosition.get("a1").addPath(new Path("E1", Direction.EDGE, null));	//z1
        m_mpPosition.get("a1").addPath(new Path("D1", Direction.VERTEX, null));	//z0
        m_mpPosition.get("a1").addPath(new Path("E2", Direction.EDGE, null));	//a0
        m_mpPosition.get("a1").addPath(new Path("D2", Direction.VERTEX, null));	//b0
        m_mpPosition.get("a1").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("b1")));
        m_mpPosition.get("a1").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("b2")));
        m_mpPosition.get("a1").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("a2")));
        m_mpPosition.get("a1").addPath(new Path("D4", Direction.VERTEX, null));	//z2        
        m_mpPosition.get("a1").addPathConnection("E1", "D1");
        m_mpPosition.get("a1").addPathConnection("D1", "E2");
        m_mpPosition.get("a1").addPathConnection("E2", "D2");
        m_mpPosition.get("a1").addPathConnection("D2", "E3");
        m_mpPosition.get("a1").addPathConnection("E3", "D3");
        m_mpPosition.get("a1").addPathConnection("D3", "E4");
        m_mpPosition.get("a1").addPathConnection("E4", "D4");
        m_mpPosition.get("a1").addPathConnection("D4", "E1");

        m_mpPosition.get("a2").addPath(new Path("E1", Direction.EDGE, null));	//z2
        m_mpPosition.get("a2").addPath(new Path("D1", Direction.VERTEX, null));	//z1
        m_mpPosition.get("a2").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("a1")));	//a1
        m_mpPosition.get("a2").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("b1")));	//b1
        m_mpPosition.get("a2").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("b2")));
        m_mpPosition.get("a2").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("b3")));
        m_mpPosition.get("a2").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("a3")));
        m_mpPosition.get("a2").addPath(new Path("D4", Direction.VERTEX, null));	//z3        
        m_mpPosition.get("a2").addPathConnection("E1", "D1");
        m_mpPosition.get("a2").addPathConnection("D1", "E2");
        m_mpPosition.get("a2").addPathConnection("E2", "D2");
        m_mpPosition.get("a2").addPathConnection("D2", "E3");
        m_mpPosition.get("a2").addPathConnection("E3", "D3");
        m_mpPosition.get("a2").addPathConnection("D3", "E4");
        m_mpPosition.get("a2").addPathConnection("E4", "D4");
        m_mpPosition.get("a2").addPathConnection("D4", "E1");

        /*m_mpPosition.get("a2").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("a1")));
        m_mpPosition.get("a2").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("b2")));
        m_mpPosition.get("a2").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("a3")));
        m_mpPosition.get("a2").addPath(new Path("E4", Direction.EDGE, null));        
        m_mpPosition.get("a2").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("b3")));
        m_mpPosition.get("a2").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("b1")));
        m_mpPosition.get("a2").addPath(new Path("D3", Direction.VERTEX, null));
        m_mpPosition.get("a2").addPath(new Path("D4", Direction.VERTEX, null));
        m_mpPosition.get("a2").addPathConnection("E1", "D1");
        m_mpPosition.get("a2").addPathConnection("D1", "E2");
        m_mpPosition.get("a2").addPathConnection("E2", "D2");
        m_mpPosition.get("a2").addPathConnection("D2", "E3");
        m_mpPosition.get("a2").addPathConnection("E1", "D1");
        m_mpPosition.get("a2").addPathConnection("D1", "E2");
        m_mpPosition.get("a2").addPathConnection("E2", "D2");
        m_mpPosition.get("a2").addPathConnection("D2", "E3");
*/

        m_mpPosition.get("a3").addPath(new Path("E1", Direction.EDGE, null));	//z3
        m_mpPosition.get("a3").addPath(new Path("D1", Direction.VERTEX, null));	//z2
        m_mpPosition.get("a3").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("a2")));	//a2
        m_mpPosition.get("a3").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("b2")));	//b2
        m_mpPosition.get("a3").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("b3")));
        m_mpPosition.get("a3").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("b4")));
        m_mpPosition.get("a3").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("a4")));
        m_mpPosition.get("a3").addPath(new Path("D4", Direction.VERTEX, null));	//z4        
        m_mpPosition.get("a3").addPathConnection("E1", "D1");
        m_mpPosition.get("a3").addPathConnection("D1", "E2");
        m_mpPosition.get("a3").addPathConnection("E2", "D2");
        m_mpPosition.get("a3").addPathConnection("D2", "E3");
        m_mpPosition.get("a3").addPathConnection("E3", "D3");
        m_mpPosition.get("a3").addPathConnection("D3", "E4");
        m_mpPosition.get("a3").addPathConnection("E4", "D4");
        m_mpPosition.get("a3").addPathConnection("D4", "E1");

        /*m_mpPosition.get("a3").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("a2")));
        m_mpPosition.get("a3").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("b3")));
        m_mpPosition.get("a3").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("a4")));
        m_mpPosition.get("a3").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("b4")));
        m_mpPosition.get("a3").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("b2")));
        m_mpPosition.get("a3").addPathConnection("E1", "D1");
        m_mpPosition.get("a3").addPathConnection("D1", "E2");
        m_mpPosition.get("a3").addPathConnection("E2", "D2");
        m_mpPosition.get("a3").addPathConnection("D2", "E3");
*/

        
        m_mpPosition.get("a4").addPath(new Path("E1", Direction.EDGE, null));	//z4
        m_mpPosition.get("a4").addPath(new Path("D1", Direction.VERTEX, null));	//z3
        m_mpPosition.get("a4").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("a3")));	//a3
        m_mpPosition.get("a4").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("b3")));	//b3
        m_mpPosition.get("a4").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("b4")));
        m_mpPosition.get("a4").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("b5")));
        m_mpPosition.get("a4").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("a5")));
        m_mpPosition.get("a4").addPath(new Path("D4", Direction.VERTEX, null));	//z5        
        m_mpPosition.get("a4").addPathConnection("E1", "D1");
        m_mpPosition.get("a4").addPathConnection("D1", "E2");
        m_mpPosition.get("a4").addPathConnection("E2", "D2");
        m_mpPosition.get("a4").addPathConnection("D2", "E3");
        m_mpPosition.get("a4").addPathConnection("E3", "D3");
        m_mpPosition.get("a4").addPathConnection("D3", "E4");
        m_mpPosition.get("a4").addPathConnection("E4", "D4");
        m_mpPosition.get("a4").addPathConnection("D4", "E1");

        /*m_mpPosition.get("a4").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("a3")));
        m_mpPosition.get("a4").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("b4")));
        m_mpPosition.get("a4").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("a5")));
        m_mpPosition.get("a4").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("b5")));
        m_mpPosition.get("a4").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("b3")));
        m_mpPosition.get("a4").addPathConnection("E1", "D1");
        m_mpPosition.get("a4").addPathConnection("D1", "E2");
        m_mpPosition.get("a4").addPathConnection("E2", "D2");
        m_mpPosition.get("a4").addPathConnection("D2", "E3");
*/
        
        m_mpPosition.get("a5").addPath(new Path("E1", Direction.EDGE, null));	//z5
        m_mpPosition.get("a5").addPath(new Path("D1", Direction.VERTEX, null));	//z4
        m_mpPosition.get("a5").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("a4")));	//a4
        m_mpPosition.get("a5").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("b4")));	//b4
        m_mpPosition.get("a5").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("b5")));
        m_mpPosition.get("a5").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("b6")));
        m_mpPosition.get("a5").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("a6")));
        m_mpPosition.get("a5").addPath(new Path("D4", Direction.VERTEX, null));	//z6        
        m_mpPosition.get("a5").addPathConnection("E1", "D1");
        m_mpPosition.get("a5").addPathConnection("D1", "E2");
        m_mpPosition.get("a5").addPathConnection("E2", "D2");
        m_mpPosition.get("a5").addPathConnection("D2", "E3");
        m_mpPosition.get("a5").addPathConnection("E3", "D3");
        m_mpPosition.get("a5").addPathConnection("D3", "E4");
        m_mpPosition.get("a5").addPathConnection("E4", "D4");
        m_mpPosition.get("a5").addPathConnection("D4", "E1");

        
        /*m_mpPosition.get("a5").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("a4")));
        m_mpPosition.get("a5").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("b5")));
        m_mpPosition.get("a5").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("a6")));
        m_mpPosition.get("a5").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("b6")));
        m_mpPosition.get("a5").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("b4")));
        m_mpPosition.get("a5").addPathConnection("E1", "D1");
        m_mpPosition.get("a5").addPathConnection("D1", "E2");
        m_mpPosition.get("a5").addPathConnection("E2", "D2");
        m_mpPosition.get("a5").addPathConnection("D2", "E3");
*/
        
        m_mpPosition.get("a6").addPath(new Path("E1", Direction.EDGE, null));	//z5
        m_mpPosition.get("a6").addPath(new Path("D1", Direction.VERTEX, null));	//z4
        m_mpPosition.get("a6").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("a5")));	//a4
        m_mpPosition.get("a6").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("b5")));	//b4
        m_mpPosition.get("a6").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("b6")));
        m_mpPosition.get("a6").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("b7")));
        m_mpPosition.get("a6").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("a7")));
        m_mpPosition.get("a6").addPath(new Path("D4", Direction.VERTEX, null));	//z6        
        m_mpPosition.get("a6").addPathConnection("E1", "D1");
        m_mpPosition.get("a6").addPathConnection("D1", "E2");
        m_mpPosition.get("a6").addPathConnection("E2", "D2");
        m_mpPosition.get("a6").addPathConnection("D2", "E3");
        m_mpPosition.get("a6").addPathConnection("E3", "D3");
        m_mpPosition.get("a6").addPathConnection("D3", "E4");
        m_mpPosition.get("a6").addPathConnection("E4", "D4");
        m_mpPosition.get("a6").addPathConnection("D4", "E1");

        
        /*m_mpPosition.get("a6").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("a5")));
        m_mpPosition.get("a6").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("b6")));
        m_mpPosition.get("a6").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("a7")));
        m_mpPosition.get("a6").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("b7")));
        m_mpPosition.get("a6").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("b5")));
        m_mpPosition.get("a6").addPathConnection("E1", "D1");
        m_mpPosition.get("a6").addPathConnection("D1", "E2");
        m_mpPosition.get("a6").addPathConnection("E2", "D2");
        m_mpPosition.get("a6").addPathConnection("D2", "E3");
*/
        
        m_mpPosition.get("a7").addPath(new Path("E1", Direction.EDGE, null));	//z5
        m_mpPosition.get("a7").addPath(new Path("D1", Direction.VERTEX, null));	//z4
        m_mpPosition.get("a7").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("a6")));	//a4
        m_mpPosition.get("a7").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("b6")));	//b4
        m_mpPosition.get("a7").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("b7")));
        m_mpPosition.get("a7").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("b8")));
        m_mpPosition.get("a7").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("a8")));
        m_mpPosition.get("a7").addPath(new Path("D4", Direction.VERTEX, null));	//z6        
        m_mpPosition.get("a7").addPathConnection("E1", "D1");
        m_mpPosition.get("a7").addPathConnection("D1", "E2");
        m_mpPosition.get("a7").addPathConnection("E2", "D2");
        m_mpPosition.get("a7").addPathConnection("D2", "E3");
        m_mpPosition.get("a7").addPathConnection("E3", "D3");
        m_mpPosition.get("a7").addPathConnection("D3", "E4");
        m_mpPosition.get("a7").addPathConnection("E4", "D4");
        m_mpPosition.get("a7").addPathConnection("D4", "E1");

        /*m_mpPosition.get("a7").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("a6")));
        m_mpPosition.get("a7").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("b7")));
        m_mpPosition.get("a7").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("a8")));
        m_mpPosition.get("a7").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("b8")));
        m_mpPosition.get("a7").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("b6")));
        m_mpPosition.get("a7").addPathConnection("E1", "D1");
        m_mpPosition.get("a7").addPathConnection("D1", "E2");
        m_mpPosition.get("a7").addPathConnection("E2", "D2");
        m_mpPosition.get("a7").addPathConnection("D2", "E3");
*/
        
        m_mpPosition.get("a8").addPath(new Path("E1", Direction.EDGE, null));	//z5
        m_mpPosition.get("a8").addPath(new Path("D1", Direction.VERTEX, null));	//z4
        m_mpPosition.get("a8").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("a7")));	//a4
        m_mpPosition.get("a8").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("b7")));	//b4
        m_mpPosition.get("a8").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("b8")));
        m_mpPosition.get("a8").addPath(new Path("D3", Direction.VERTEX, null));
        m_mpPosition.get("a8").addPath(new Path("E4", Direction.EDGE, null));
        m_mpPosition.get("a8").addPath(new Path("D4", Direction.VERTEX, null));	//z6        
        m_mpPosition.get("a8").addPathConnection("E1", "D1");
        m_mpPosition.get("a8").addPathConnection("D1", "E2");
        m_mpPosition.get("a8").addPathConnection("E2", "D2");
        m_mpPosition.get("a8").addPathConnection("D2", "E3");
        m_mpPosition.get("a8").addPathConnection("E3", "D3");
        m_mpPosition.get("a8").addPathConnection("D3", "E4");
        m_mpPosition.get("a8").addPathConnection("E4", "D4");
        m_mpPosition.get("a8").addPathConnection("D4", "E1");

        
        /*m_mpPosition.get("a8").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("a7")));
        m_mpPosition.get("a8").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("b8")));
        m_mpPosition.get("a8").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("b7")));
        m_mpPosition.get("a8").addPathConnection("E1", "D1");
        m_mpPosition.get("a8").addPathConnection("D1", "E2");
*/
        // File b
        m_mpPosition.get("b1").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("a1")));
        m_mpPosition.get("b1").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("b2")));
        m_mpPosition.get("b1").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("c1")));
        m_mpPosition.get("b1").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("a2")));
        m_mpPosition.get("b1").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("c2")));
        m_mpPosition.get("b1").addPathConnection("E1", "D1");
        m_mpPosition.get("b1").addPathConnection("D1", "E2");
        m_mpPosition.get("b1").addPathConnection("E2", "D2");
        m_mpPosition.get("b1").addPathConnection("D2", "E3");

        m_mpPosition.get("b2").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("a2")));
        m_mpPosition.get("b2").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("b1")));
        m_mpPosition.get("b2").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("c2")));
        m_mpPosition.get("b2").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("b3")));
        m_mpPosition.get("b2").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("a1")));
        m_mpPosition.get("b2").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("c1")));
        m_mpPosition.get("b2").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("a3")));
        m_mpPosition.get("b2").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("c3")));
        m_mpPosition.get("b2").addPathConnection("E1", "D3");
        m_mpPosition.get("b2").addPathConnection("D3", "E4");
        m_mpPosition.get("b2").addPathConnection("E4", "D4");
        m_mpPosition.get("b2").addPathConnection("D4", "E3");
        m_mpPosition.get("b2").addPathConnection("E3", "D2");
        m_mpPosition.get("b2").addPathConnection("D2", "E2");
        m_mpPosition.get("b2").addPathConnection("E2", "D1");
        m_mpPosition.get("b2").addPathConnection("D1", "E1");


        m_mpPosition.get("b3").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("a3")));
        m_mpPosition.get("b3").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("b2")));
        m_mpPosition.get("b3").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("c3")));
        m_mpPosition.get("b3").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("b4")));
        m_mpPosition.get("b3").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("a2")));
        m_mpPosition.get("b3").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("c2")));
        m_mpPosition.get("b3").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("a4")));
        m_mpPosition.get("b3").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("c4")));
        m_mpPosition.get("b3").addPathConnection("E1", "D3");
        m_mpPosition.get("b3").addPathConnection("D3", "E4");
        m_mpPosition.get("b3").addPathConnection("E4", "D4");
        m_mpPosition.get("b3").addPathConnection("D4", "E3");
        m_mpPosition.get("b3").addPathConnection("E3", "D2");
        m_mpPosition.get("b3").addPathConnection("D2", "E2");
        m_mpPosition.get("b3").addPathConnection("E2", "D1");
        m_mpPosition.get("b3").addPathConnection("D1", "E1");

        m_mpPosition.get("b4").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("a4")));
        m_mpPosition.get("b4").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("b3")));
        m_mpPosition.get("b4").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("c4")));
        m_mpPosition.get("b4").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("b5")));
        m_mpPosition.get("b4").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("a3")));
        m_mpPosition.get("b4").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("c3")));
        m_mpPosition.get("b4").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("a5")));
        m_mpPosition.get("b4").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("c5")));
        m_mpPosition.get("b4").addPathConnection("E1", "D3");
        m_mpPosition.get("b4").addPathConnection("D3", "E4");
        m_mpPosition.get("b4").addPathConnection("E4", "D4");
        m_mpPosition.get("b4").addPathConnection("D4", "E3");
        m_mpPosition.get("b4").addPathConnection("E3", "D2");
        m_mpPosition.get("b4").addPathConnection("D2", "E2");
        m_mpPosition.get("b4").addPathConnection("E2", "D1");
        m_mpPosition.get("b4").addPathConnection("D1", "E1");

        m_mpPosition.get("b5").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("a5")));
        m_mpPosition.get("b5").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("b4")));
        m_mpPosition.get("b5").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("c5")));
        m_mpPosition.get("b5").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("b6")));
        m_mpPosition.get("b5").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("a4")));
        m_mpPosition.get("b5").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("c4")));
        m_mpPosition.get("b5").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("a6")));
        m_mpPosition.get("b5").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("c6")));
        m_mpPosition.get("b5").addPathConnection("E1", "D3");
        m_mpPosition.get("b5").addPathConnection("D3", "E4");
        m_mpPosition.get("b5").addPathConnection("E4", "D4");
        m_mpPosition.get("b5").addPathConnection("D4", "E3");
        m_mpPosition.get("b5").addPathConnection("E3", "D2");
        m_mpPosition.get("b5").addPathConnection("D2", "E2");
        m_mpPosition.get("b5").addPathConnection("E2", "D1");
        m_mpPosition.get("b5").addPathConnection("D1", "E1");

        m_mpPosition.get("b6").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("a6")));
        m_mpPosition.get("b6").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("b5")));
        m_mpPosition.get("b6").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("c6")));
        m_mpPosition.get("b6").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("b7")));
        m_mpPosition.get("b6").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("a5")));
        m_mpPosition.get("b6").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("c5")));
        m_mpPosition.get("b6").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("a7")));
        m_mpPosition.get("b6").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("c7")));
        m_mpPosition.get("b6").addPathConnection("E1", "D3");
        m_mpPosition.get("b6").addPathConnection("D3", "E4");
        m_mpPosition.get("b6").addPathConnection("E4", "D4");
        m_mpPosition.get("b6").addPathConnection("D4", "E3");
        m_mpPosition.get("b6").addPathConnection("E3", "D2");
        m_mpPosition.get("b6").addPathConnection("D2", "E2");
        m_mpPosition.get("b6").addPathConnection("E2", "D1");
        m_mpPosition.get("b6").addPathConnection("D1", "E1");

        m_mpPosition.get("b7").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("a7")));
        m_mpPosition.get("b7").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("b6")));
        m_mpPosition.get("b7").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("c7")));
        m_mpPosition.get("b7").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("b8")));
        m_mpPosition.get("b7").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("a6")));
        m_mpPosition.get("b7").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("c6")));
        m_mpPosition.get("b7").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("a8")));
        m_mpPosition.get("b7").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("c8")));
        m_mpPosition.get("b7").addPathConnection("E1", "D3");
        m_mpPosition.get("b7").addPathConnection("D3", "E4");
        m_mpPosition.get("b7").addPathConnection("E4", "D4");
        m_mpPosition.get("b7").addPathConnection("D4", "E3");
        m_mpPosition.get("b7").addPathConnection("E3", "D2");
        m_mpPosition.get("b7").addPathConnection("D2", "E2");
        m_mpPosition.get("b7").addPathConnection("E2", "D1");
        m_mpPosition.get("b7").addPathConnection("D1", "E1");

        m_mpPosition.get("b8").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("a8")));
        m_mpPosition.get("b8").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("b7")));
        m_mpPosition.get("b8").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("c8")));
        m_mpPosition.get("b8").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("a7")));
        m_mpPosition.get("b8").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("c7")));
        m_mpPosition.get("b8").addPathConnection("E1", "D1");
        m_mpPosition.get("b8").addPathConnection("D1", "E2");
        m_mpPosition.get("b8").addPathConnection("E2", "D2");
        m_mpPosition.get("b8").addPathConnection("D2", "E3");

        // c
        m_mpPosition.get("c1").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("b1")));
        m_mpPosition.get("c1").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("c2")));
        m_mpPosition.get("c1").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("d1")));
        m_mpPosition.get("c1").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("b2")));
        m_mpPosition.get("c1").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("d2")));
        m_mpPosition.get("c1").addPathConnection("E1", "D1");
        m_mpPosition.get("c1").addPathConnection("D1", "E2");
        m_mpPosition.get("c1").addPathConnection("E2", "D2");
        m_mpPosition.get("c1").addPathConnection("D2", "E3");

        m_mpPosition.get("c2").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("b2")));
        m_mpPosition.get("c2").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("c1")));
        m_mpPosition.get("c2").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("d2")));
        m_mpPosition.get("c2").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("c3")));
        m_mpPosition.get("c2").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("b1")));
        m_mpPosition.get("c2").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("d1")));
        m_mpPosition.get("c2").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("b3")));
        m_mpPosition.get("c2").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("d3")));
        m_mpPosition.get("c2").addPathConnection("E1", "D3");
        m_mpPosition.get("c2").addPathConnection("D3", "E4");
        m_mpPosition.get("c2").addPathConnection("E4", "D4");
        m_mpPosition.get("c2").addPathConnection("D4", "E3");
        m_mpPosition.get("c2").addPathConnection("E3", "D2");
        m_mpPosition.get("c2").addPathConnection("D2", "E2");
        m_mpPosition.get("c2").addPathConnection("E2", "D1");
        m_mpPosition.get("c2").addPathConnection("D1", "E1");

        m_mpPosition.get("c3").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("b3")));
        m_mpPosition.get("c3").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("c2")));
        m_mpPosition.get("c3").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("d3")));
        m_mpPosition.get("c3").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("c4")));
        m_mpPosition.get("c3").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("b2")));
        m_mpPosition.get("c3").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("d2")));
        m_mpPosition.get("c3").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("b4")));
        m_mpPosition.get("c3").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("d4")));
        m_mpPosition.get("c3").addPathConnection("E1", "D3");
        m_mpPosition.get("c3").addPathConnection("D3", "E4");
        m_mpPosition.get("c3").addPathConnection("E4", "D4");
        m_mpPosition.get("c3").addPathConnection("D4", "E3");
        m_mpPosition.get("c3").addPathConnection("E3", "D2");
        m_mpPosition.get("c3").addPathConnection("D2", "E2");
        m_mpPosition.get("c3").addPathConnection("E2", "D1");
        m_mpPosition.get("c3").addPathConnection("D1", "E1");

        m_mpPosition.get("c4").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("b4")));
        m_mpPosition.get("c4").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("c3")));
        m_mpPosition.get("c4").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("d4")));
        m_mpPosition.get("c4").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("c5")));
        m_mpPosition.get("c4").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("b3")));
        m_mpPosition.get("c4").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("d3")));
        m_mpPosition.get("c4").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("b5")));
        m_mpPosition.get("c4").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("d5")));
        m_mpPosition.get("c4").addPathConnection("E1", "D3");
        m_mpPosition.get("c4").addPathConnection("D3", "E4");
        m_mpPosition.get("c4").addPathConnection("E4", "D4");
        m_mpPosition.get("c4").addPathConnection("D4", "E3");
        m_mpPosition.get("c4").addPathConnection("E3", "D2");
        m_mpPosition.get("c4").addPathConnection("D2", "E2");
        m_mpPosition.get("c4").addPathConnection("E2", "D1");
        m_mpPosition.get("c4").addPathConnection("D1", "E1");

        m_mpPosition.get("c5").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("b5")));
        m_mpPosition.get("c5").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("c4")));
        m_mpPosition.get("c5").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("d5")));
        m_mpPosition.get("c5").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("c6")));
        m_mpPosition.get("c5").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("b4")));
        m_mpPosition.get("c5").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("d4")));
        m_mpPosition.get("c5").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("b6")));
        m_mpPosition.get("c5").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("d6")));
        m_mpPosition.get("c5").addPathConnection("E1", "D3");
        m_mpPosition.get("c5").addPathConnection("D3", "E4");
        m_mpPosition.get("c5").addPathConnection("E4", "D4");
        m_mpPosition.get("c5").addPathConnection("D4", "E3");
        m_mpPosition.get("c5").addPathConnection("E3", "D2");
        m_mpPosition.get("c5").addPathConnection("D2", "E2");
        m_mpPosition.get("c5").addPathConnection("E2", "D1");
        m_mpPosition.get("c5").addPathConnection("D1", "E1");

        m_mpPosition.get("c6").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("b6")));
        m_mpPosition.get("c6").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("c5")));
        m_mpPosition.get("c6").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("d6")));
        m_mpPosition.get("c6").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("c7")));
        m_mpPosition.get("c6").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("b5")));
        m_mpPosition.get("c6").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("d5")));
        m_mpPosition.get("c6").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("b7")));
        m_mpPosition.get("c6").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("d7")));
        m_mpPosition.get("c6").addPathConnection("E1", "D3");
        m_mpPosition.get("c6").addPathConnection("D3", "E4");
        m_mpPosition.get("c6").addPathConnection("E4", "D4");
        m_mpPosition.get("c6").addPathConnection("D4", "E3");
        m_mpPosition.get("c6").addPathConnection("E3", "D2");
        m_mpPosition.get("c6").addPathConnection("D2", "E2");
        m_mpPosition.get("c6").addPathConnection("E2", "D1");
        m_mpPosition.get("c6").addPathConnection("D1", "E1");

        m_mpPosition.get("c7").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("b7")));
        m_mpPosition.get("c7").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("c6")));
        m_mpPosition.get("c7").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("d7")));
        m_mpPosition.get("c7").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("c8")));
        m_mpPosition.get("c7").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("b6")));
        m_mpPosition.get("c7").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("d6")));
        m_mpPosition.get("c7").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("b8")));
        m_mpPosition.get("c7").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("d8")));
        m_mpPosition.get("c7").addPathConnection("E1", "D3");
        m_mpPosition.get("c7").addPathConnection("D3", "E4");
        m_mpPosition.get("c7").addPathConnection("E4", "D4");
        m_mpPosition.get("c7").addPathConnection("D4", "E3");
        m_mpPosition.get("c7").addPathConnection("E3", "D2");
        m_mpPosition.get("c7").addPathConnection("D2", "E2");
        m_mpPosition.get("c7").addPathConnection("E2", "D1");
        m_mpPosition.get("c7").addPathConnection("D1", "E1");

        m_mpPosition.get("c8").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("b8")));
        m_mpPosition.get("c8").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("c7")));
        m_mpPosition.get("c8").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("d8")));
        m_mpPosition.get("c8").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("b7")));
        m_mpPosition.get("c8").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("d7")));
        m_mpPosition.get("c8").addPathConnection("E1", "D1");
        m_mpPosition.get("c8").addPathConnection("D1", "E2");
        m_mpPosition.get("c8").addPathConnection("E2", "D2");
        m_mpPosition.get("c8").addPathConnection("D2", "E3");

        // d
        m_mpPosition.get("d1").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("c1")));
        m_mpPosition.get("d1").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("d2")));
        m_mpPosition.get("d1").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("e1")));
        m_mpPosition.get("d1").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("c2")));
        m_mpPosition.get("d1").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("e2")));
        m_mpPosition.get("d1").addPathConnection("E1", "D1");
        m_mpPosition.get("d1").addPathConnection("D1", "E2");
        m_mpPosition.get("d1").addPathConnection("E2", "D2");
        m_mpPosition.get("d1").addPathConnection("D2", "E3");

        m_mpPosition.get("d2").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("c2")));
        m_mpPosition.get("d2").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("d1")));
        m_mpPosition.get("d2").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("e2")));
        m_mpPosition.get("d2").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("d3")));
        m_mpPosition.get("d2").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("c1")));
        m_mpPosition.get("d2").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("e1")));
        m_mpPosition.get("d2").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("c3")));
        m_mpPosition.get("d2").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("e3")));
        m_mpPosition.get("d2").addPathConnection("E1", "D1");
        m_mpPosition.get("d2").addPathConnection("D1", "E2");
        m_mpPosition.get("d2").addPathConnection("E2", "D2");
        m_mpPosition.get("d2").addPathConnection("D2", "E3");
        m_mpPosition.get("d2").addPathConnection("E3", "D4");
        m_mpPosition.get("d2").addPathConnection("D4", "E4");
        m_mpPosition.get("d2").addPathConnection("E4", "D3");
        m_mpPosition.get("d2").addPathConnection("D3", "E1");

        m_mpPosition.get("d3").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("c3")));
        m_mpPosition.get("d3").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("d2")));
        m_mpPosition.get("d3").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("e3")));
        m_mpPosition.get("d3").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("d4")));
        m_mpPosition.get("d3").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("c2")));
        m_mpPosition.get("d3").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("e2")));
        m_mpPosition.get("d3").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("c4")));
        m_mpPosition.get("d3").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("e4")));
        m_mpPosition.get("d3").addPathConnection("E1", "D1");
        m_mpPosition.get("d3").addPathConnection("D1", "E2");
        m_mpPosition.get("d3").addPathConnection("E2", "D2");
        m_mpPosition.get("d3").addPathConnection("D2", "E3");
        m_mpPosition.get("d3").addPathConnection("E3", "D4");
        m_mpPosition.get("d3").addPathConnection("D4", "E4");
        m_mpPosition.get("d3").addPathConnection("E4", "D3");
        m_mpPosition.get("d3").addPathConnection("D3", "E1");

        m_mpPosition.get("d4").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("c4")));
        m_mpPosition.get("d4").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("d3")));
        m_mpPosition.get("d4").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("e4")));
        m_mpPosition.get("d4").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("d5")));
        m_mpPosition.get("d4").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("c3")));
        m_mpPosition.get("d4").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("e3")));
        m_mpPosition.get("d4").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("c5")));
        m_mpPosition.get("d4").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("e9")));
        //m_mpPosition.get("d4").addPath(new Path("D5", Direction.VERTEX, m_mpPosition.get("i5")));
        //m_mpPosition.get("d4").addPath(new Path("D6", Direction.VERTEX, m_mpPosition.get("i9")));
        m_mpPosition.get("d4").getPath("D4").addPosition((m_mpPosition.get("i5")));
        m_mpPosition.get("d4").getPath("D4").addPosition((m_mpPosition.get("i9")));
        m_mpPosition.get("d4").addPathConnection("E1", "D1");
        m_mpPosition.get("d4").addPathConnection("D1", "E2");
        m_mpPosition.get("d4").addPathConnection("E2", "D2");
        m_mpPosition.get("d4").addPathConnection("D2", "E3");
        m_mpPosition.get("d4").addPathConnection("E3", "D4");
        m_mpPosition.get("d4").addPathConnection("D4", "E4");
        //m_mpPosition.get("d4").addPathConnection("D6", "D5");
        //m_mpPosition.get("d4").addPathConnection("D5", "E4");
        m_mpPosition.get("d4").addPathConnection("E4", "D3");
        m_mpPosition.get("d4").addPathConnection("D3", "E1");

        m_mpPosition.get("d5").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("c5")));
        m_mpPosition.get("d5").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("d4")));
        m_mpPosition.get("d5").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("i5")));
        m_mpPosition.get("d5").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("d6")));
        m_mpPosition.get("d5").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("c4")));
        m_mpPosition.get("d5").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("e4")));
        m_mpPosition.get("d5").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("c6")));
        m_mpPosition.get("d5").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("i6")));
        //m_mpPosition.get("d5").addPath(new Path("D5", Direction.VERTEX, m_mpPosition.get("i9")));
        //m_mpPosition.get("d5").addPath(new Path("D6", Direction.VERTEX, m_mpPosition.get("e9")));
        m_mpPosition.get("d5").getPath("D2").addPosition((m_mpPosition.get("i9")));
        m_mpPosition.get("d5").getPath("D2").addPosition((m_mpPosition.get("e9")));

        m_mpPosition.get("d5").addPathConnection("E1", "D1");
        m_mpPosition.get("d5").addPathConnection("D1", "E2");
        m_mpPosition.get("d5").addPathConnection("E2", "D2");
        m_mpPosition.get("d5").addPathConnection("D2", "E3");
        //m_mpPosition.get("d5").addPathConnection("D6", "D5");
        //m_mpPosition.get("d5").addPathConnection("D5", "E3");
        m_mpPosition.get("d5").addPathConnection("E3", "D4");
        m_mpPosition.get("d5").addPathConnection("D4", "E4");
        m_mpPosition.get("d5").addPathConnection("E4", "D3");
        m_mpPosition.get("d5").addPathConnection("D3", "E1");


        m_mpPosition.get("d6").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("c6")));
        m_mpPosition.get("d6").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("d5")));
        m_mpPosition.get("d6").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("i6")));
        m_mpPosition.get("d6").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("d7")));
        m_mpPosition.get("d6").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("c5")));
        m_mpPosition.get("d6").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("i5")));
        m_mpPosition.get("d6").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("c7")));
        m_mpPosition.get("d6").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("i7")));
        m_mpPosition.get("d6").addPathConnection("E1", "D1");
        m_mpPosition.get("d6").addPathConnection("D1", "E2");
        m_mpPosition.get("d6").addPathConnection("E2", "D2");
        m_mpPosition.get("d6").addPathConnection("D2", "E3");
        m_mpPosition.get("d6").addPathConnection("E3", "D4");
        m_mpPosition.get("d6").addPathConnection("D4", "E4");
        m_mpPosition.get("d6").addPathConnection("E4", "D3");
        m_mpPosition.get("d6").addPathConnection("D3", "E1");

        m_mpPosition.get("d7").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("c7")));
        m_mpPosition.get("d7").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("d6")));
        m_mpPosition.get("d7").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("i7")));
        m_mpPosition.get("d7").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("d8")));
        m_mpPosition.get("d7").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("c6")));
        m_mpPosition.get("d7").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("i6")));
        m_mpPosition.get("d7").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("c8")));
        m_mpPosition.get("d7").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("i8")));
        m_mpPosition.get("d7").addPathConnection("E1", "D1");
        m_mpPosition.get("d7").addPathConnection("D1", "E2");
        m_mpPosition.get("d7").addPathConnection("E2", "D2");
        m_mpPosition.get("d7").addPathConnection("D2", "E3");
        m_mpPosition.get("d7").addPathConnection("E3", "D4");
        m_mpPosition.get("d7").addPathConnection("D4", "E4");
        m_mpPosition.get("d7").addPathConnection("E4", "D3");
        m_mpPosition.get("d7").addPathConnection("D3", "E1");

        m_mpPosition.get("d8").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("c8")));
        m_mpPosition.get("d8").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("d7")));
        m_mpPosition.get("d8").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("i8")));
        m_mpPosition.get("d8").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("c7")));
        m_mpPosition.get("d8").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("i7")));
        m_mpPosition.get("d8").addPathConnection("E1", "D1");
        m_mpPosition.get("d8").addPathConnection("D1", "E2");
        m_mpPosition.get("d8").addPathConnection("E2", "D2");
        m_mpPosition.get("d8").addPathConnection("D2", "E3");

        // e
        m_mpPosition.get("e1").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("d1")));
        m_mpPosition.get("e1").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("e2")));
        m_mpPosition.get("e1").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("f1")));
        m_mpPosition.get("e1").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("d2")));
        m_mpPosition.get("e1").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("f2")));
        m_mpPosition.get("e1").addPathConnection("E1", "D1");
        m_mpPosition.get("e1").addPathConnection("D1", "E2");
        m_mpPosition.get("e1").addPathConnection("E2", "D2");
        m_mpPosition.get("e1").addPathConnection("D2", "E3");

        m_mpPosition.get("e2").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("d2")));
        m_mpPosition.get("e2").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("e1")));
        m_mpPosition.get("e2").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("f2")));
        m_mpPosition.get("e2").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("e3")));
        m_mpPosition.get("e2").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("d1")));
        m_mpPosition.get("e2").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("f1")));
        m_mpPosition.get("e2").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("d3")));
        m_mpPosition.get("e2").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("f3")));
        m_mpPosition.get("e2").addPathConnection("E1", "D1");
        m_mpPosition.get("e2").addPathConnection("D1", "E2");
        m_mpPosition.get("e2").addPathConnection("E2", "D2");
        m_mpPosition.get("e2").addPathConnection("D2", "E3");
        m_mpPosition.get("e2").addPathConnection("E3", "D4");
        m_mpPosition.get("e2").addPathConnection("D4", "E4");
        m_mpPosition.get("e2").addPathConnection("E4", "D3");
        m_mpPosition.get("e2").addPathConnection("D3", "E1");

        m_mpPosition.get("e3").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("d3")));
        m_mpPosition.get("e3").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("e2")));
        m_mpPosition.get("e3").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("f3")));
        m_mpPosition.get("e3").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("e4")));
        m_mpPosition.get("e3").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("d2")));
        m_mpPosition.get("e3").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("f2")));
        m_mpPosition.get("e3").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("d4")));
        m_mpPosition.get("e3").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("f4")));
        m_mpPosition.get("e3").addPathConnection("E1", "D1");
        m_mpPosition.get("e3").addPathConnection("D1", "E2");
        m_mpPosition.get("e3").addPathConnection("E2", "D2");
        m_mpPosition.get("e3").addPathConnection("D2", "E3");
        m_mpPosition.get("e3").addPathConnection("E3", "D4");
        m_mpPosition.get("e3").addPathConnection("D4", "E4");
        m_mpPosition.get("e3").addPathConnection("E4", "D3");
        m_mpPosition.get("e3").addPathConnection("D3", "E1");

        m_mpPosition.get("e4").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("d4")));
        m_mpPosition.get("e4").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("e3")));
        m_mpPosition.get("e4").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("f4")));
        m_mpPosition.get("e4").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("e9")));
        m_mpPosition.get("e4").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("d3")));
        m_mpPosition.get("e4").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("f3")));
        m_mpPosition.get("e4").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("d5")));
        m_mpPosition.get("e4").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("f9")));
        //m_mpPosition.get("e4").addPath(new Path("D5", Direction.VERTEX, m_mpPosition.get("i5")));
        //m_mpPosition.get("e4").addPath(new Path("D6", Direction.VERTEX, m_mpPosition.get("i9")));
        m_mpPosition.get("e4").getPath("D3").addPosition((m_mpPosition.get("i5")));
        m_mpPosition.get("e4").getPath("D3").addPosition((m_mpPosition.get("i9")));

        m_mpPosition.get("e4").addPathConnection("E1", "D1");
        m_mpPosition.get("e4").addPathConnection("D1", "E2");
        m_mpPosition.get("e4").addPathConnection("E2", "D2");
        m_mpPosition.get("e4").addPathConnection("D2", "E3");
        m_mpPosition.get("e4").addPathConnection("E3", "D4");
        m_mpPosition.get("e4").addPathConnection("D4", "E4");
        m_mpPosition.get("e4").addPathConnection("E4", "D3");
        //m_mpPosition.get("e4").addPathConnection("D6", "D5");
        //m_mpPosition.get("e4").addPathConnection("D5", "D3");
        m_mpPosition.get("e4").addPathConnection("D3", "E1");

        m_mpPosition.get("e9").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("i9")));
        m_mpPosition.get("e9").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("e4")));
        m_mpPosition.get("e9").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("f9")));
        m_mpPosition.get("e9").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("e10")));
        m_mpPosition.get("e9").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("i5")));
        //m_mpPosition.get("e9").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("d5")));
        //m_mpPosition.get("e9").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("d4")));
        m_mpPosition.get("e9").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("f4")));
        m_mpPosition.get("e9").addPath(new Path("D5", Direction.VERTEX, m_mpPosition.get("f10")));
        m_mpPosition.get("e9").addPath(new Path("D6", Direction.VERTEX, m_mpPosition.get("i10")));
        m_mpPosition.get("e9").getPath("D1").addPosition((m_mpPosition.get("d5")));
        m_mpPosition.get("e9").getPath("D1").addPosition((m_mpPosition.get("d4")));

        m_mpPosition.get("e9").addPathConnection("E2", "D4");
        m_mpPosition.get("e9").addPathConnection("D4", "E3");
        m_mpPosition.get("e9").addPathConnection("E3", "D5");
        m_mpPosition.get("e9").addPathConnection("D5", "E4");
        m_mpPosition.get("e9").addPathConnection("E4", "D6");
        m_mpPosition.get("e9").addPathConnection("D6", "E1");
        m_mpPosition.get("e9").addPathConnection("E1", "D1");
        m_mpPosition.get("e9").addPathConnection("D1", "E2");
        //m_mpPosition.get("e9").addPathConnection("D2", "D3");
        //m_mpPosition.get("e9").addPathConnection("D3", "E2");

        m_mpPosition.get("e10").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("i10")));
        m_mpPosition.get("e10").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("e9")));
        m_mpPosition.get("e10").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("f10")));
        m_mpPosition.get("e10").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("e11")));
        m_mpPosition.get("e10").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("f9")));
        m_mpPosition.get("e10").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("i9")));
        m_mpPosition.get("e10").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("f11")));
        m_mpPosition.get("e10").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("i11")));
        m_mpPosition.get("e10").addPathConnection("E2", "D1");
        m_mpPosition.get("e10").addPathConnection("D1", "E3");
        m_mpPosition.get("e10").addPathConnection("E3", "D3");
        m_mpPosition.get("e10").addPathConnection("D3", "E4");
        m_mpPosition.get("e10").addPathConnection("E4", "D4");
        m_mpPosition.get("e10").addPathConnection("D4", "E1");
        m_mpPosition.get("e10").addPathConnection("E1", "D2");
        m_mpPosition.get("e10").addPathConnection("D2", "E2");

        m_mpPosition.get("e11").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("i11")));
        m_mpPosition.get("e11").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("e10")));
        m_mpPosition.get("e11").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("f11")));
        m_mpPosition.get("e11").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("e12")));
        m_mpPosition.get("e11").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("f10")));
        m_mpPosition.get("e11").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("i10")));
        m_mpPosition.get("e11").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("f12")));
        m_mpPosition.get("e11").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("i12")));
        m_mpPosition.get("e11").addPathConnection("E2", "D1");
        m_mpPosition.get("e11").addPathConnection("D1", "E3");
        m_mpPosition.get("e11").addPathConnection("E3", "D3");
        m_mpPosition.get("e11").addPathConnection("D3", "E4");
        m_mpPosition.get("e11").addPathConnection("E4", "D4");
        m_mpPosition.get("e11").addPathConnection("D4", "E1");
        m_mpPosition.get("e11").addPathConnection("E1", "D2");
        m_mpPosition.get("e11").addPathConnection("D2", "E2");

        m_mpPosition.get("e12").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("e11")));
        m_mpPosition.get("e12").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("i12")));
        m_mpPosition.get("e12").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("f12")));
        m_mpPosition.get("e12").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("i11")));
        m_mpPosition.get("e12").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("f11")));
        m_mpPosition.get("e12").addPathConnection("E2", "D1");
        m_mpPosition.get("e12").addPathConnection("D1", "E2");
        m_mpPosition.get("e12").addPathConnection("E1", "D2");
        m_mpPosition.get("e12").addPathConnection("D2", "E3");

        // f
        m_mpPosition.get("f1").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("e1")));
        m_mpPosition.get("f1").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("f2")));
        m_mpPosition.get("f1").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("g1")));
        m_mpPosition.get("f1").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("e2")));
        m_mpPosition.get("f1").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("g2")));
        m_mpPosition.get("f1").addPathConnection("E1", "D1");
        m_mpPosition.get("f1").addPathConnection("D1", "E2");
        m_mpPosition.get("f1").addPathConnection("E2", "D2");
        m_mpPosition.get("f1").addPathConnection("D2", "E3");

        m_mpPosition.get("f2").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("e2")));
        m_mpPosition.get("f2").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("f1")));
        m_mpPosition.get("f2").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("g2")));
        m_mpPosition.get("f2").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("f3")));
        m_mpPosition.get("f2").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("e1")));
        m_mpPosition.get("f2").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("g1")));
        m_mpPosition.get("f2").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("e3")));
        m_mpPosition.get("f2").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("g3")));
        m_mpPosition.get("f2").addPathConnection("E1", "D1");
        m_mpPosition.get("f2").addPathConnection("D1", "E2");
        m_mpPosition.get("f2").addPathConnection("E2", "D2");
        m_mpPosition.get("f2").addPathConnection("D2", "E3");
        m_mpPosition.get("f2").addPathConnection("E3", "D4");
        m_mpPosition.get("f2").addPathConnection("D4", "E4");
        m_mpPosition.get("f2").addPathConnection("E4", "D3");
        m_mpPosition.get("f2").addPathConnection("D3", "E1");


        m_mpPosition.get("f3").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("e3")));
        m_mpPosition.get("f3").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("f2")));
        m_mpPosition.get("f3").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("g3")));
        m_mpPosition.get("f3").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("f4")));
        m_mpPosition.get("f3").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("e2")));
        m_mpPosition.get("f3").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("g2")));
        m_mpPosition.get("f3").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("e4")));
        m_mpPosition.get("f3").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("g4")));
        m_mpPosition.get("f3").addPathConnection("E1", "D1");
        m_mpPosition.get("f3").addPathConnection("D1", "E2");
        m_mpPosition.get("f3").addPathConnection("E2", "D2");
        m_mpPosition.get("f3").addPathConnection("D2", "E3");
        m_mpPosition.get("f3").addPathConnection("E3", "D4");
        m_mpPosition.get("f3").addPathConnection("D4", "E4");
        m_mpPosition.get("f3").addPathConnection("E4", "D3");
        m_mpPosition.get("f3").addPathConnection("D3", "E1");

        m_mpPosition.get("f4").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("e4")));
        m_mpPosition.get("f4").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("f3")));
        m_mpPosition.get("f4").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("g4")));
        m_mpPosition.get("f4").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("f9")));
        m_mpPosition.get("f4").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("e3")));
        m_mpPosition.get("f4").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("g3")));
        m_mpPosition.get("f4").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("e9")));
        m_mpPosition.get("f4").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("g9")));
        m_mpPosition.get("f4").addPathConnection("E1", "D1");
        m_mpPosition.get("f4").addPathConnection("D1", "E2");
        m_mpPosition.get("f4").addPathConnection("E2", "D2");
        m_mpPosition.get("f4").addPathConnection("D2", "E3");
        m_mpPosition.get("f4").addPathConnection("E3", "D4");
        m_mpPosition.get("f4").addPathConnection("D4", "E4");
        m_mpPosition.get("f4").addPathConnection("E4", "D3");
        m_mpPosition.get("f4").addPathConnection("D3", "E1");

        m_mpPosition.get("f9").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("e9")));
        m_mpPosition.get("f9").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("f4")));
        m_mpPosition.get("f9").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("g9")));
        m_mpPosition.get("f9").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("f10")));
        m_mpPosition.get("f9").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("e4")));
        m_mpPosition.get("f9").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("g4")));
        m_mpPosition.get("f9").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("e10")));
        m_mpPosition.get("f9").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("g10")));
        m_mpPosition.get("f9").addPathConnection("E1", "D1");
        m_mpPosition.get("f9").addPathConnection("D1", "E2");
        m_mpPosition.get("f9").addPathConnection("E2", "D2");
        m_mpPosition.get("f9").addPathConnection("D2", "E3");
        m_mpPosition.get("f9").addPathConnection("E3", "D4");
        m_mpPosition.get("f9").addPathConnection("D4", "E4");
        m_mpPosition.get("f9").addPathConnection("E4", "D3");
        m_mpPosition.get("f9").addPathConnection("D3", "E1");

        m_mpPosition.get("f10").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("e10")));
        m_mpPosition.get("f10").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("f9")));
        m_mpPosition.get("f10").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("g10")));
        m_mpPosition.get("f10").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("f11")));
        m_mpPosition.get("f10").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("e9")));
        m_mpPosition.get("f10").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("g9")));
        m_mpPosition.get("f10").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("e11")));
        m_mpPosition.get("f10").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("g11")));
        m_mpPosition.get("f10").addPathConnection("E1", "D1");
        m_mpPosition.get("f10").addPathConnection("D1", "E2");
        m_mpPosition.get("f10").addPathConnection("E2", "D2");
        m_mpPosition.get("f10").addPathConnection("D2", "E3");
        m_mpPosition.get("f10").addPathConnection("E3", "D4");
        m_mpPosition.get("f10").addPathConnection("D4", "E4");
        m_mpPosition.get("f10").addPathConnection("E4", "D3");
        m_mpPosition.get("f10").addPathConnection("D3", "E1");

        m_mpPosition.get("f11").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("e11")));
        m_mpPosition.get("f11").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("f10")));
        m_mpPosition.get("f11").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("g11")));
        m_mpPosition.get("f11").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("f12")));
        m_mpPosition.get("f11").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("e10")));
        m_mpPosition.get("f11").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("g10")));
        m_mpPosition.get("f11").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("e12")));
        m_mpPosition.get("f11").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("g12")));
        m_mpPosition.get("f11").addPathConnection("E1", "D1");
        m_mpPosition.get("f11").addPathConnection("D1", "E2");
        m_mpPosition.get("f11").addPathConnection("E2", "D2");
        m_mpPosition.get("f11").addPathConnection("D2", "E3");
        m_mpPosition.get("f11").addPathConnection("E3", "D4");
        m_mpPosition.get("f11").addPathConnection("D4", "E4");
        m_mpPosition.get("f11").addPathConnection("E4", "D3");
        m_mpPosition.get("f11").addPathConnection("D3", "E1");

        m_mpPosition.get("f12").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("e12")));
        m_mpPosition.get("f12").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("f11")));
        m_mpPosition.get("f12").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("g12")));
        m_mpPosition.get("f12").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("e11")));
        m_mpPosition.get("f12").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("g11")));
        m_mpPosition.get("f12").addPathConnection("E1", "D1");
        m_mpPosition.get("f12").addPathConnection("D1", "E2");
        m_mpPosition.get("f12").addPathConnection("E2", "D2");
        m_mpPosition.get("f12").addPathConnection("D2", "E3");

        // g
        m_mpPosition.get("g1").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("f1")));
        m_mpPosition.get("g1").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("g2")));
        m_mpPosition.get("g1").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("h1")));
        m_mpPosition.get("g1").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("f2")));
        m_mpPosition.get("g1").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("h2")));
        m_mpPosition.get("g1").addPathConnection("E1", "D1");
        m_mpPosition.get("g1").addPathConnection("D1", "E2");
        m_mpPosition.get("g1").addPathConnection("E2", "D2");
        m_mpPosition.get("g1").addPathConnection("D2", "E3");

        m_mpPosition.get("g2").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("f2")));
        m_mpPosition.get("g2").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("g1")));
        m_mpPosition.get("g2").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("h2")));
        m_mpPosition.get("g2").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("g3")));
        m_mpPosition.get("g2").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("f1")));
        m_mpPosition.get("g2").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("h1")));
        m_mpPosition.get("g2").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("f3")));
        m_mpPosition.get("g2").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("h3")));
        m_mpPosition.get("g2").addPathConnection("E1", "D1");
        m_mpPosition.get("g2").addPathConnection("D1", "E2");
        m_mpPosition.get("g2").addPathConnection("E2", "D2");
        m_mpPosition.get("g2").addPathConnection("D2", "E3");
        m_mpPosition.get("g2").addPathConnection("E3", "D4");
        m_mpPosition.get("g2").addPathConnection("D4", "E4");
        m_mpPosition.get("g2").addPathConnection("E4", "D3");
        m_mpPosition.get("g2").addPathConnection("D3", "E1");

        m_mpPosition.get("g3").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("f3")));
        m_mpPosition.get("g3").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("g2")));
        m_mpPosition.get("g3").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("h3")));
        m_mpPosition.get("g3").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("g4")));
        m_mpPosition.get("g3").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("f2")));
        m_mpPosition.get("g3").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("h2")));
        m_mpPosition.get("g3").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("f4")));
        m_mpPosition.get("g3").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("h4")));
        m_mpPosition.get("g3").addPathConnection("E1", "D1");
        m_mpPosition.get("g3").addPathConnection("D1", "E2");
        m_mpPosition.get("g3").addPathConnection("E2", "D2");
        m_mpPosition.get("g3").addPathConnection("D2", "E3");
        m_mpPosition.get("g3").addPathConnection("E3", "D4");
        m_mpPosition.get("g3").addPathConnection("D4", "E4");
        m_mpPosition.get("g3").addPathConnection("E4", "D3");
        m_mpPosition.get("g3").addPathConnection("D3", "E1");

        m_mpPosition.get("g4").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("f4")));
        m_mpPosition.get("g4").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("g3")));
        m_mpPosition.get("g4").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("h4")));
        m_mpPosition.get("g4").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("g9")));
        m_mpPosition.get("g4").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("f3")));
        m_mpPosition.get("g4").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("h3")));
        m_mpPosition.get("g4").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("f9")));
        m_mpPosition.get("g4").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("h9")));
        m_mpPosition.get("g4").addPathConnection("E1", "D1");
        m_mpPosition.get("g4").addPathConnection("D1", "E2");
        m_mpPosition.get("g4").addPathConnection("E2", "D2");
        m_mpPosition.get("g4").addPathConnection("D2", "E3");
        m_mpPosition.get("g4").addPathConnection("E3", "D4");
        m_mpPosition.get("g4").addPathConnection("D4", "E4");
        m_mpPosition.get("g4").addPathConnection("E4", "D3");
        m_mpPosition.get("g4").addPathConnection("D3", "E1");

        m_mpPosition.get("g9").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("f9")));
        m_mpPosition.get("g9").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("g4")));
        m_mpPosition.get("g9").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("h9")));
        m_mpPosition.get("g9").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("g10")));
        m_mpPosition.get("g9").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("f4")));
        m_mpPosition.get("g9").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("h4")));
        m_mpPosition.get("g9").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("f10")));
        m_mpPosition.get("g9").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("h10")));
        m_mpPosition.get("g9").addPathConnection("E1", "D1");
        m_mpPosition.get("g9").addPathConnection("D1", "E2");
        m_mpPosition.get("g9").addPathConnection("E2", "D2");
        m_mpPosition.get("g9").addPathConnection("D2", "E3");
        m_mpPosition.get("g9").addPathConnection("E3", "D4");
        m_mpPosition.get("g9").addPathConnection("D4", "E4");
        m_mpPosition.get("g9").addPathConnection("E4", "D3");
        m_mpPosition.get("g9").addPathConnection("D3", "E1");

        m_mpPosition.get("g10").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("f10")));
        m_mpPosition.get("g10").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("g9")));
        m_mpPosition.get("g10").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("h10")));
        m_mpPosition.get("g10").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("g11")));
        m_mpPosition.get("g10").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("f9")));
        m_mpPosition.get("g10").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("h9")));
        m_mpPosition.get("g10").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("f11")));
        m_mpPosition.get("g10").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("h11")));
        m_mpPosition.get("g10").addPathConnection("E1", "D1");
        m_mpPosition.get("g10").addPathConnection("D1", "E2");
        m_mpPosition.get("g10").addPathConnection("E2", "D2");
        m_mpPosition.get("g10").addPathConnection("D2", "E3");
        m_mpPosition.get("g10").addPathConnection("E3", "D4");
        m_mpPosition.get("g10").addPathConnection("D4", "E4");
        m_mpPosition.get("g10").addPathConnection("E4", "D3");
        m_mpPosition.get("g10").addPathConnection("D3", "E1");

        m_mpPosition.get("g11").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("f11")));
        m_mpPosition.get("g11").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("g10")));
        m_mpPosition.get("g11").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("h11")));
        m_mpPosition.get("g11").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("g12")));
        m_mpPosition.get("g11").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("f10")));
        m_mpPosition.get("g11").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("h10")));
        m_mpPosition.get("g11").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("f12")));
        m_mpPosition.get("g11").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("h12")));
        m_mpPosition.get("g11").addPathConnection("E1", "D1");
        m_mpPosition.get("g11").addPathConnection("D1", "E2");
        m_mpPosition.get("g11").addPathConnection("E2", "D2");
        m_mpPosition.get("g11").addPathConnection("D2", "E3");
        m_mpPosition.get("g11").addPathConnection("E3", "D4");
        m_mpPosition.get("g11").addPathConnection("D4", "E4");
        m_mpPosition.get("g11").addPathConnection("E4", "D3");
        m_mpPosition.get("g11").addPathConnection("D3", "E1");

        m_mpPosition.get("g12").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("f12")));
        m_mpPosition.get("g12").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("g11")));
        m_mpPosition.get("g12").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("h12")));
        m_mpPosition.get("g12").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("f11")));
        m_mpPosition.get("g12").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("h11")));
        m_mpPosition.get("g12").addPathConnection("E1", "D1");
        m_mpPosition.get("g12").addPathConnection("D1", "E2");
        m_mpPosition.get("g12").addPathConnection("E2", "D2");
        m_mpPosition.get("g12").addPathConnection("D2", "E3");

        // h
        m_mpPosition.get("h1").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("g1")));
        m_mpPosition.get("h1").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("h2")));
        m_mpPosition.get("h1").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("g2")));
        m_mpPosition.get("h1").addPathConnection("E1", "D1");
        m_mpPosition.get("h1").addPathConnection("D1", "E2");

        m_mpPosition.get("h2").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("g2")));
        m_mpPosition.get("h2").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("h1")));
        m_mpPosition.get("h2").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("h3")));
        m_mpPosition.get("h2").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("g1")));
        m_mpPosition.get("h2").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("g3")));
        m_mpPosition.get("h2").addPathConnection("E2", "D1");
        m_mpPosition.get("h2").addPathConnection("D1", "E1");
        m_mpPosition.get("h2").addPathConnection("E1", "D2");
        m_mpPosition.get("h2").addPathConnection("D2", "E3");

        m_mpPosition.get("h3").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("g3")));
        m_mpPosition.get("h3").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("h2")));
        m_mpPosition.get("h3").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("h4")));
        m_mpPosition.get("h3").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("g2")));
        m_mpPosition.get("h3").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("g4")));
        m_mpPosition.get("h3").addPathConnection("E2", "D1");
        m_mpPosition.get("h3").addPathConnection("D1", "E1");
        m_mpPosition.get("h3").addPathConnection("E1", "D2");
        m_mpPosition.get("h3").addPathConnection("D2", "E3");

        m_mpPosition.get("h4").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("g4")));
        m_mpPosition.get("h4").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("h3")));
        m_mpPosition.get("h4").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("h9")));
        m_mpPosition.get("h4").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("g3")));
        m_mpPosition.get("h4").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("g9")));
        m_mpPosition.get("h4").addPathConnection("E2", "D1");
        m_mpPosition.get("h4").addPathConnection("D1", "E1");
        m_mpPosition.get("h4").addPathConnection("E1", "D2");
        m_mpPosition.get("h4").addPathConnection("D2", "E3");

        m_mpPosition.get("h9").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("g9")));
        m_mpPosition.get("h9").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("h4")));
        m_mpPosition.get("h9").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("h10")));
        m_mpPosition.get("h9").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("g4")));
        m_mpPosition.get("h9").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("g10")));
        m_mpPosition.get("h9").addPathConnection("E2", "D1");
        m_mpPosition.get("h9").addPathConnection("D1", "E1");
        m_mpPosition.get("h9").addPathConnection("E1", "D2");
        m_mpPosition.get("h9").addPathConnection("D2", "E3");

        m_mpPosition.get("h10").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("g10")));
        m_mpPosition.get("h10").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("h9")));
        m_mpPosition.get("h10").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("h11")));
        m_mpPosition.get("h10").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("g9")));
        m_mpPosition.get("h10").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("g11")));
        m_mpPosition.get("h10").addPathConnection("E2", "D1");
        m_mpPosition.get("h10").addPathConnection("D1", "E1");
        m_mpPosition.get("h10").addPathConnection("E1", "D2");
        m_mpPosition.get("h10").addPathConnection("D2", "E3");

        m_mpPosition.get("h11").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("g11")));
        m_mpPosition.get("h11").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("h10")));
        m_mpPosition.get("h11").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("h12")));
        m_mpPosition.get("h11").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("g10")));
        m_mpPosition.get("h11").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("g12")));
        m_mpPosition.get("h11").addPathConnection("E2", "D1");
        m_mpPosition.get("h11").addPathConnection("D1", "E1");
        m_mpPosition.get("h11").addPathConnection("E1", "D2");
        m_mpPosition.get("h11").addPathConnection("D2", "E3");

        m_mpPosition.get("h12").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("g12")));
        m_mpPosition.get("h12").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("h11")));
        m_mpPosition.get("h12").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("g11")));
        m_mpPosition.get("h12").addPathConnection("E1", "D1");
        m_mpPosition.get("h12").addPathConnection("D1", "E2");
        /////////////////

        // i
        m_mpPosition.get("i8").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("j8")));
        m_mpPosition.get("i8").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("d8")));
        m_mpPosition.get("i8").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("i7")));
        m_mpPosition.get("i8").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("d7")));
        m_mpPosition.get("i8").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("j7")));
        m_mpPosition.get("i8").addPathConnection("E1", "D2");
        m_mpPosition.get("i8").addPathConnection("D2", "E3");
        m_mpPosition.get("i8").addPathConnection("E3", "D1");
        m_mpPosition.get("i8").addPathConnection("D1", "E2");

        m_mpPosition.get("i7").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("d7")));
        m_mpPosition.get("i7").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("j7")));
        m_mpPosition.get("i7").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("i6")));
        m_mpPosition.get("i7").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("i8")));
        m_mpPosition.get("i7").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("j6")));
        m_mpPosition.get("i7").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("j8")));
        m_mpPosition.get("i7").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("d6")));
        m_mpPosition.get("i7").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("d8")));
        m_mpPosition.get("i7").addPathConnection("E1", "D3");
        m_mpPosition.get("i7").addPathConnection("D3", "E3");
        m_mpPosition.get("i7").addPathConnection("E3", "D1");
        m_mpPosition.get("i7").addPathConnection("D1", "E2");
        m_mpPosition.get("i7").addPathConnection("E2", "D2");
        m_mpPosition.get("i7").addPathConnection("D2", "E4");
        m_mpPosition.get("i7").addPathConnection("E4", "D4");
        m_mpPosition.get("i7").addPathConnection("D4", "E1");

        m_mpPosition.get("i6").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("d6")));
        m_mpPosition.get("i6").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("j6")));
        m_mpPosition.get("i6").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("i5")));
        m_mpPosition.get("i6").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("i7")));
        m_mpPosition.get("i6").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("j5")));
        m_mpPosition.get("i6").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("j7")));
        m_mpPosition.get("i6").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("d5")));
        m_mpPosition.get("i6").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("d7")));
        m_mpPosition.get("i6").addPathConnection("E1", "D3");
        m_mpPosition.get("i6").addPathConnection("D3", "E3");
        m_mpPosition.get("i6").addPathConnection("E3", "D1");
        m_mpPosition.get("i6").addPathConnection("D1", "E2");
        m_mpPosition.get("i6").addPathConnection("E2", "D2");
        m_mpPosition.get("i6").addPathConnection("D2", "E4");
        m_mpPosition.get("i6").addPathConnection("E4", "D4");
        m_mpPosition.get("i6").addPathConnection("D4", "E1");

        m_mpPosition.get("i5").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("i6")));
        m_mpPosition.get("i5").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("i9")));
        m_mpPosition.get("i5").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("j5")));
        m_mpPosition.get("i5").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("d5")));
        m_mpPosition.get("i5").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("j6")));
        m_mpPosition.get("i5").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("j9")));
        m_mpPosition.get("i5").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("d6")));
        m_mpPosition.get("i5").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("d4")));
        //m_mpPosition.get("i5").addPath(new Path("D5", Direction.VERTEX, m_mpPosition.get("e4")));
        //m_mpPosition.get("i5").addPath(new Path("D6", Direction.VERTEX, m_mpPosition.get("e9")));
        m_mpPosition.get("i5").getPath("D4").addPosition((m_mpPosition.get("e4")));
        m_mpPosition.get("i5").getPath("D4").addPosition((m_mpPosition.get("e9")));

        m_mpPosition.get("i5").addPathConnection("E1", "D3");
        m_mpPosition.get("i5").addPathConnection("D3", "E4");
        m_mpPosition.get("i5").addPathConnection("E4", "D4");
        m_mpPosition.get("i5").addPathConnection("D4", "E2");
        //m_mpPosition.get("i5").addPathConnection("D5", "D6");
        //m_mpPosition.get("i5").addPathConnection("D6", "E2");
        m_mpPosition.get("i5").addPathConnection("E2", "D2");
        m_mpPosition.get("i5").addPathConnection("D2", "E3");
        m_mpPosition.get("i5").addPathConnection("E3", "D1");
        m_mpPosition.get("i5").addPathConnection("D1", "E1");

        m_mpPosition.get("i9").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("i10")));
        m_mpPosition.get("i9").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("i5")));
        m_mpPosition.get("i9").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("j9")));
        m_mpPosition.get("i9").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("e9")));
        m_mpPosition.get("i9").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("j10")));
        m_mpPosition.get("i9").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("j5")));
        m_mpPosition.get("i9").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("e10")));
        m_mpPosition.get("i9").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("e4")));
        //m_mpPosition.get("i9").addPath(new Path("D5", Direction.VERTEX, m_mpPosition.get("d4")));
        //m_mpPosition.get("i9").addPath(new Path("D6", Direction.VERTEX, m_mpPosition.get("d5")));
        m_mpPosition.get("i9").getPath("D4").addPosition((m_mpPosition.get("d4")));
        m_mpPosition.get("i9").getPath("D4").addPosition((m_mpPosition.get("d5")));

        m_mpPosition.get("i9").addPathConnection("E1", "D3");
        m_mpPosition.get("i9").addPathConnection("D3", "E4");
        m_mpPosition.get("i9").addPathConnection("E4", "D4");
        m_mpPosition.get("i9").addPathConnection("D4", "E2");
        //m_mpPosition.get("i9").addPathConnection("D5", "D6");
        //m_mpPosition.get("i9").addPathConnection("D6", "E2");
        m_mpPosition.get("i9").addPathConnection("E2", "D2");
        m_mpPosition.get("i9").addPathConnection("D2", "E3");
        m_mpPosition.get("i9").addPathConnection("E3", "D1");
        m_mpPosition.get("i9").addPathConnection("D1", "E1");

        m_mpPosition.get("i10").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("i9")));
        m_mpPosition.get("i10").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("i11")));
        m_mpPosition.get("i10").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("j10")));
        m_mpPosition.get("i10").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("e10")));
        m_mpPosition.get("i10").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("j9")));
        m_mpPosition.get("i10").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("e9")));
        m_mpPosition.get("i10").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("j11")));
        m_mpPosition.get("i10").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("e11")));
        m_mpPosition.get("i10").addPathConnection("E1", "D1");
        m_mpPosition.get("i10").addPathConnection("D1", "E3");
        m_mpPosition.get("i10").addPathConnection("E3", "D3");
        m_mpPosition.get("i10").addPathConnection("D3", "E2");
        m_mpPosition.get("i10").addPathConnection("E2", "D4");
        m_mpPosition.get("i10").addPathConnection("D4", "E4");
        m_mpPosition.get("i10").addPathConnection("E4", "D2");
        m_mpPosition.get("i10").addPathConnection("D2", "E1");

        m_mpPosition.get("i11").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("i10")));
        m_mpPosition.get("i11").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("i12")));
        m_mpPosition.get("i11").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("e11")));
        m_mpPosition.get("i11").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("j11")));
        m_mpPosition.get("i11").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("j12")));
        m_mpPosition.get("i11").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("j10")));
        m_mpPosition.get("i11").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("e12")));
        m_mpPosition.get("i11").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("e10")));
        m_mpPosition.get("i11").addPathConnection("E1", "D2");
        m_mpPosition.get("i11").addPathConnection("D2", "E4");
        m_mpPosition.get("i11").addPathConnection("E4", "D1");
        m_mpPosition.get("i11").addPathConnection("D1", "E2");
        m_mpPosition.get("i11").addPathConnection("E2", "D3");
        m_mpPosition.get("i11").addPathConnection("D3", "E3");
        m_mpPosition.get("i11").addPathConnection("E3", "D4");
        m_mpPosition.get("i11").addPathConnection("D4", "E1");

        m_mpPosition.get("i12").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("i11")));
        m_mpPosition.get("i12").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("e12")));
        m_mpPosition.get("i12").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("j12")));
        m_mpPosition.get("i12").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("e11")));
        m_mpPosition.get("i12").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("j11")));
        m_mpPosition.get("i12").addPathConnection("E3", "D2");
        m_mpPosition.get("i12").addPathConnection("D2", "E1");
        m_mpPosition.get("i12").addPathConnection("E1", "D1");
        m_mpPosition.get("i12").addPathConnection("D1", "E2");

        // j
        m_mpPosition.get("j8").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("j7")));
        m_mpPosition.get("j8").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("i8")));
        m_mpPosition.get("j8").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("k8")));
        m_mpPosition.get("j8").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("i7")));
        m_mpPosition.get("j8").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("k7")));
        m_mpPosition.get("j8").addPathConnection("E3", "D2");
        m_mpPosition.get("j8").addPathConnection("D2", "E1");
        m_mpPosition.get("j8").addPathConnection("E1", "D1");
        m_mpPosition.get("j8").addPathConnection("D1", "E2");

        m_mpPosition.get("j7").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("j8")));
        m_mpPosition.get("j7").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("j6")));
        m_mpPosition.get("j7").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("i7")));
        m_mpPosition.get("j7").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("k7")));
        m_mpPosition.get("j7").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("i8")));
        m_mpPosition.get("j7").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("i6")));
        m_mpPosition.get("j7").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("k8")));
        m_mpPosition.get("j7").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("k6")));
        m_mpPosition.get("j7").addPathConnection("E1", "D1");
        m_mpPosition.get("j7").addPathConnection("D1", "E3");
        m_mpPosition.get("j7").addPathConnection("E3", "D2");
        m_mpPosition.get("j7").addPathConnection("D2", "E2");
        m_mpPosition.get("j7").addPathConnection("E2", "D4");
        m_mpPosition.get("j7").addPathConnection("D4", "E4");
        m_mpPosition.get("j7").addPathConnection("E4", "D3");
        m_mpPosition.get("j7").addPathConnection("D3", "E1");


        m_mpPosition.get("j6").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("j5")));
        m_mpPosition.get("j6").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("j7")));
        m_mpPosition.get("j6").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("i6")));
        m_mpPosition.get("j6").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("k6")));
        m_mpPosition.get("j6").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("i5")));
        m_mpPosition.get("j6").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("i7")));
        m_mpPosition.get("j6").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("k5")));
        m_mpPosition.get("j6").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("k7")));
        m_mpPosition.get("j6").addPathConnection("E1", "D1");
        m_mpPosition.get("j6").addPathConnection("D1", "E3");
        m_mpPosition.get("j6").addPathConnection("E3", "D2");
        m_mpPosition.get("j6").addPathConnection("D2", "E2");
        m_mpPosition.get("j6").addPathConnection("E2", "D4");
        m_mpPosition.get("j6").addPathConnection("D4", "E4");
        m_mpPosition.get("j6").addPathConnection("E4", "D3");
        m_mpPosition.get("j6").addPathConnection("D3", "E1");

        m_mpPosition.get("j5").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("j9")));
        m_mpPosition.get("j5").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("j6")));
        m_mpPosition.get("j5").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("k5")));
        m_mpPosition.get("j5").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("i5")));
        m_mpPosition.get("j5").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("i6")));
        m_mpPosition.get("j5").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("k6")));
        m_mpPosition.get("j5").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("i9")));
        m_mpPosition.get("j5").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("k9")));
        m_mpPosition.get("j5").addPathConnection("E2", "D1");
        m_mpPosition.get("j5").addPathConnection("D1", "E4");
        m_mpPosition.get("j5").addPathConnection("E4", "D3");
        m_mpPosition.get("j5").addPathConnection("D3", "E1");
        m_mpPosition.get("j5").addPathConnection("E1", "D4");
        m_mpPosition.get("j5").addPathConnection("D4", "E3");
        m_mpPosition.get("j5").addPathConnection("E3", "D2");
        m_mpPosition.get("j5").addPathConnection("D2", "E2");

        m_mpPosition.get("j9").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("j10")));
        m_mpPosition.get("j9").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("j5")));
        m_mpPosition.get("j9").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("i9")));
        m_mpPosition.get("j9").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("k9")));
        m_mpPosition.get("j9").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("i5")));
        m_mpPosition.get("j9").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("i10")));
        m_mpPosition.get("j9").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("k5")));
        m_mpPosition.get("j9").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("k10")));
        m_mpPosition.get("j9").addPathConnection("E2", "D1");
        m_mpPosition.get("j9").addPathConnection("D1", "E3");
        m_mpPosition.get("j9").addPathConnection("E3", "D2");
        m_mpPosition.get("j9").addPathConnection("D2", "E1");
        m_mpPosition.get("j9").addPathConnection("E1", "D4");
        m_mpPosition.get("j9").addPathConnection("D4", "E4");
        m_mpPosition.get("j9").addPathConnection("E4", "D3");
        m_mpPosition.get("j9").addPathConnection("D3", "E2");

        m_mpPosition.get("j10").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("j9")));
        m_mpPosition.get("j10").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("j11")));
        m_mpPosition.get("j10").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("i10")));
        m_mpPosition.get("j10").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("k10")));
        m_mpPosition.get("j10").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("i9")));
        m_mpPosition.get("j10").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("i11")));
        m_mpPosition.get("j10").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("k9")));
        m_mpPosition.get("j10").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("k11")));
        m_mpPosition.get("j10").addPathConnection("E1", "D1");
        m_mpPosition.get("j10").addPathConnection("D1", "E3");
        m_mpPosition.get("j10").addPathConnection("E3", "D2");
        m_mpPosition.get("j10").addPathConnection("D2", "E2");
        m_mpPosition.get("j10").addPathConnection("E2", "D4");
        m_mpPosition.get("j10").addPathConnection("D4", "E4");
        m_mpPosition.get("j10").addPathConnection("E4", "D3");
        m_mpPosition.get("j10").addPathConnection("D3", "E1");

        m_mpPosition.get("j11").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("j12")));
        m_mpPosition.get("j11").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("j10")));
        m_mpPosition.get("j11").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("i11")));
        m_mpPosition.get("j11").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("k11")));
        m_mpPosition.get("j11").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("i10")));
        m_mpPosition.get("j11").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("i12")));
        m_mpPosition.get("j11").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("k10")));
        m_mpPosition.get("j11").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("k12")));
        m_mpPosition.get("j11").addPathConnection("E2", "D1");
        m_mpPosition.get("j11").addPathConnection("D1", "E3");
        m_mpPosition.get("j11").addPathConnection("E3", "D2");
        m_mpPosition.get("j11").addPathConnection("D2", "E1");
        m_mpPosition.get("j11").addPathConnection("E1", "D4");
        m_mpPosition.get("j11").addPathConnection("D4", "E4");
        m_mpPosition.get("j11").addPathConnection("E4", "D3");
        m_mpPosition.get("j11").addPathConnection("D3", "E2");

        m_mpPosition.get("j12").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("j11")));
        m_mpPosition.get("j12").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("i12")));
        m_mpPosition.get("j12").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("k12")));
        m_mpPosition.get("j12").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("i11")));
        m_mpPosition.get("j12").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("k11")));
        m_mpPosition.get("j12").addPathConnection("E3", "D2");
        m_mpPosition.get("j12").addPathConnection("D2", "E1");
        m_mpPosition.get("j12").addPathConnection("E1", "D1");
        m_mpPosition.get("j12").addPathConnection("D1", "E2");

        // k
        m_mpPosition.get("k8").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("k7")));
        m_mpPosition.get("k8").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("j8")));
        m_mpPosition.get("k8").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("l8")));
        m_mpPosition.get("k8").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("j7")));
        m_mpPosition.get("k8").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("l7")));
        m_mpPosition.get("k8").addPathConnection("E3", "D2");
        m_mpPosition.get("k8").addPathConnection("D2", "E1");
        m_mpPosition.get("k8").addPathConnection("E1", "D1");
        m_mpPosition.get("k8").addPathConnection("D1", "E2");

        m_mpPosition.get("k7").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("k8")));
        m_mpPosition.get("k7").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("k6")));
        m_mpPosition.get("k7").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("j7")));
        m_mpPosition.get("k7").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("l7")));
        m_mpPosition.get("k7").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("j8")));
        m_mpPosition.get("k7").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("j6")));
        m_mpPosition.get("k7").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("l8")));
        m_mpPosition.get("k7").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("l6")));
        m_mpPosition.get("k7").addPathConnection("E1", "D1");
        m_mpPosition.get("k7").addPathConnection("D1", "E3");
        m_mpPosition.get("k7").addPathConnection("E3", "D2");
        m_mpPosition.get("k7").addPathConnection("D2", "E2");
        m_mpPosition.get("k7").addPathConnection("E2", "D4");
        m_mpPosition.get("k7").addPathConnection("D4", "E4");
        m_mpPosition.get("k7").addPathConnection("E4", "D3");
        m_mpPosition.get("k7").addPathConnection("D3", "E1");

        m_mpPosition.get("k6").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("k5")));
        m_mpPosition.get("k6").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("k7")));
        m_mpPosition.get("k6").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("j6")));
        m_mpPosition.get("k6").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("l6")));
        m_mpPosition.get("k6").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("j5")));
        m_mpPosition.get("k6").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("j7")));
        m_mpPosition.get("k6").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("l5")));
        m_mpPosition.get("k6").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("l7")));
        m_mpPosition.get("k6").addPathConnection("E1", "D1");
        m_mpPosition.get("k6").addPathConnection("D1", "E3");
        m_mpPosition.get("k6").addPathConnection("E3", "D2");
        m_mpPosition.get("k6").addPathConnection("D2", "E2");
        m_mpPosition.get("k6").addPathConnection("E2", "D4");
        m_mpPosition.get("k6").addPathConnection("D4", "E4");
        m_mpPosition.get("k6").addPathConnection("E4", "D3");
        m_mpPosition.get("k6").addPathConnection("D3", "E1");

        m_mpPosition.get("k5").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("k9")));
        m_mpPosition.get("k5").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("k6")));
        m_mpPosition.get("k5").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("j5")));
        m_mpPosition.get("k5").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("l5")));
        m_mpPosition.get("k5").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("j6")));
        m_mpPosition.get("k5").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("l6")));
        m_mpPosition.get("k5").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("j9")));
        m_mpPosition.get("k5").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("l9")));
        m_mpPosition.get("k5").addPathConnection("E1", "D3");
        m_mpPosition.get("k5").addPathConnection("D3", "E3");
        m_mpPosition.get("k5").addPathConnection("E3", "D1");
        m_mpPosition.get("k5").addPathConnection("D1", "E2");
        m_mpPosition.get("k5").addPathConnection("E2", "D2");
        m_mpPosition.get("k5").addPathConnection("D2", "E4");
        m_mpPosition.get("k5").addPathConnection("E4", "D4");
        m_mpPosition.get("k5").addPathConnection("D4", "E1");

        m_mpPosition.get("k9").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("k10")));
        m_mpPosition.get("k9").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("k5")));
        m_mpPosition.get("k9").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("j9")));
        m_mpPosition.get("k9").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("l9")));
        m_mpPosition.get("k9").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("j5")));
        m_mpPosition.get("k9").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("j10")));
        m_mpPosition.get("k9").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("l5")));
        m_mpPosition.get("k9").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("l10")));
        m_mpPosition.get("k9").addPathConnection("E2", "D1");
        m_mpPosition.get("k9").addPathConnection("D1", "E3");
        m_mpPosition.get("k9").addPathConnection("E3", "D2");
        m_mpPosition.get("k9").addPathConnection("D2", "E1");
        m_mpPosition.get("k9").addPathConnection("E1", "D4");
        m_mpPosition.get("k9").addPathConnection("D4", "E4");
        m_mpPosition.get("k9").addPathConnection("E4", "D3");
        m_mpPosition.get("k9").addPathConnection("D3", "E2");

        m_mpPosition.get("k10").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("k9")));
        m_mpPosition.get("k10").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("k11")));
        m_mpPosition.get("k10").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("j10")));
        m_mpPosition.get("k10").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("l10")));
        m_mpPosition.get("k10").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("j9")));
        m_mpPosition.get("k10").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("j11")));
        m_mpPosition.get("k10").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("l9")));
        m_mpPosition.get("k10").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("l11")));
        m_mpPosition.get("k10").addPathConnection("E1", "D1");
        m_mpPosition.get("k10").addPathConnection("D1", "E3");
        m_mpPosition.get("k10").addPathConnection("E3", "D2");
        m_mpPosition.get("k10").addPathConnection("D2", "E2");
        m_mpPosition.get("k10").addPathConnection("E2", "D4");
        m_mpPosition.get("k10").addPathConnection("D4", "E4");
        m_mpPosition.get("k10").addPathConnection("E4", "D3");
        m_mpPosition.get("k10").addPathConnection("D3", "E1");

        m_mpPosition.get("k11").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("k12")));
        m_mpPosition.get("k11").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("k10")));
        m_mpPosition.get("k11").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("j11")));
        m_mpPosition.get("k11").addPath(new Path("E4", Direction.EDGE, m_mpPosition.get("l11")));
        m_mpPosition.get("k11").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("j10")));
        m_mpPosition.get("k11").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("j12")));
        m_mpPosition.get("k11").addPath(new Path("D3", Direction.VERTEX, m_mpPosition.get("l10")));
        m_mpPosition.get("k11").addPath(new Path("D4", Direction.VERTEX, m_mpPosition.get("l12")));
        m_mpPosition.get("k11").addPathConnection("E2", "D1");
        m_mpPosition.get("k11").addPathConnection("D1", "E3");
        m_mpPosition.get("k11").addPathConnection("E3", "D2");
        m_mpPosition.get("k11").addPathConnection("D2", "E1");
        m_mpPosition.get("k11").addPathConnection("E1", "D4");
        m_mpPosition.get("k11").addPathConnection("D4", "E4");
        m_mpPosition.get("k11").addPathConnection("E4", "D3");
        m_mpPosition.get("k11").addPathConnection("D3", "E2");

        m_mpPosition.get("k12").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("k11")));
        m_mpPosition.get("k12").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("j12")));
        m_mpPosition.get("k12").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("l12")));
        m_mpPosition.get("k12").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("j11")));
        m_mpPosition.get("k12").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("l11")));
        m_mpPosition.get("k12").addPathConnection("E3", "D2");
        m_mpPosition.get("k12").addPathConnection("D2", "E1");
        m_mpPosition.get("k12").addPathConnection("E1", "D1");
        m_mpPosition.get("k12").addPathConnection("D1", "E2");

        // l
        m_mpPosition.get("l8").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("l7")));
        m_mpPosition.get("l8").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("k8")));
        m_mpPosition.get("l8").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("k7")));
        m_mpPosition.get("l8").addPathConnection("E1", "D1");
        m_mpPosition.get("l8").addPathConnection("D1", "E2");

        m_mpPosition.get("l7").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("l8")));
        m_mpPosition.get("l7").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("l6")));
        m_mpPosition.get("l7").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("k7")));
        m_mpPosition.get("l7").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("k8")));
        m_mpPosition.get("l7").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("k6")));
        m_mpPosition.get("l7").addPathConnection("E1", "D1");
        m_mpPosition.get("l7").addPathConnection("D1", "E3");
        m_mpPosition.get("l7").addPathConnection("E3", "D2");
        m_mpPosition.get("l7").addPathConnection("D2", "E2");

        m_mpPosition.get("l6").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("l5")));
        m_mpPosition.get("l6").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("l7")));
        m_mpPosition.get("l6").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("k6")));
        m_mpPosition.get("l6").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("k5")));
        m_mpPosition.get("l6").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("k7")));
        m_mpPosition.get("l6").addPathConnection("E1", "D1");
        m_mpPosition.get("l6").addPathConnection("D1", "E3");
        m_mpPosition.get("l6").addPathConnection("E3", "D2");
        m_mpPosition.get("l6").addPathConnection("D2", "E2");

        m_mpPosition.get("l5").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("l9")));
        m_mpPosition.get("l5").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("l6")));
        m_mpPosition.get("l5").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("k5")));
        m_mpPosition.get("l5").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("k6")));
        m_mpPosition.get("l5").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("k9")));
        m_mpPosition.get("l5").addPathConnection("E1", "D2");
        m_mpPosition.get("l5").addPathConnection("D2", "E3");
        m_mpPosition.get("l5").addPathConnection("E3", "D1");
        m_mpPosition.get("l5").addPathConnection("D1", "E2");

        m_mpPosition.get("l9").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("l10")));
        m_mpPosition.get("l9").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("l5")));
        m_mpPosition.get("l9").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("k9")));
        m_mpPosition.get("l9").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("k5")));
        m_mpPosition.get("l9").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("k10")));
        m_mpPosition.get("l9").addPathConnection("E1", "D2");
        m_mpPosition.get("l9").addPathConnection("D2", "E3");
        m_mpPosition.get("l9").addPathConnection("E3", "D1");
        m_mpPosition.get("l9").addPathConnection("D1", "E2");

        m_mpPosition.get("l10").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("l9")));
        m_mpPosition.get("l10").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("l11")));
        m_mpPosition.get("l10").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("k10")));
        m_mpPosition.get("l10").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("k9")));
        m_mpPosition.get("l10").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("k11")));
        m_mpPosition.get("l10").addPathConnection("E1", "D1");
        m_mpPosition.get("l10").addPathConnection("D1", "E3");
        m_mpPosition.get("l10").addPathConnection("E3", "D2");
        m_mpPosition.get("l10").addPathConnection("D2", "E2");

        m_mpPosition.get("l11").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("l12")));
        m_mpPosition.get("l11").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("l10")));
        m_mpPosition.get("l11").addPath(new Path("E3", Direction.EDGE, m_mpPosition.get("k11")));
        m_mpPosition.get("l11").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("k10")));
        m_mpPosition.get("l11").addPath(new Path("D2", Direction.VERTEX, m_mpPosition.get("k12")));
        m_mpPosition.get("l11").addPathConnection("E1", "D2");
        m_mpPosition.get("l11").addPathConnection("D2", "E3");
        m_mpPosition.get("l11").addPathConnection("E3", "D1");
        m_mpPosition.get("l11").addPathConnection("D1", "E2");

        m_mpPosition.get("l12").addPath(new Path("E1", Direction.EDGE, m_mpPosition.get("l11")));
        m_mpPosition.get("l12").addPath(new Path("E2", Direction.EDGE, m_mpPosition.get("k12")));
        m_mpPosition.get("l12").addPath(new Path("D1", Direction.VERTEX, m_mpPosition.get("k11")));
        m_mpPosition.get("l12").addPathConnection("E1", "D1");
        m_mpPosition.get("l12").addPathConnection("D1", "E2");    
    }

    public void populatePieces() {
    	Piece oPiece = new Piece("RookWhite", "Rook-W.png");
    	m_mpPieces.put(oPiece.getName(), oPiece);
    	oPiece = new Piece("KnightWhite", "Knight-W.png");
    	m_mpPieces.put(oPiece.getName(), oPiece);
    	oPiece = new Piece("BishopWhite", "Bishop-w.png");
    	m_mpPieces.put(oPiece.getName(), oPiece);
    	oPiece = new Piece("QueenWhite", "Queen-W.png");
    	m_mpPieces.put(oPiece.getName(), oPiece);
    	oPiece = new Piece("KingWhite", "King-W.png");
    	m_mpPieces.put(oPiece.getName(), oPiece);
        oPiece = new Piece("PawnWhite", "Pawn-W.png");
        m_mpPieces.put(oPiece.getName(), oPiece);

    	oPiece = new Piece("RookBlack", "Rook-B.png");
    	m_mpPieces.put(oPiece.getName(), oPiece);
    	oPiece = new Piece("KnightBlack", "Knight-B.png");
    	m_mpPieces.put(oPiece.getName(), oPiece);
    	oPiece = new Piece("BishopBlack", "Bishop-B.png");
    	m_mpPieces.put(oPiece.getName(), oPiece);
    	oPiece = new Piece("QueenBlack", "Queen-B.png");
    	m_mpPieces.put(oPiece.getName(), oPiece);
    	oPiece = new Piece("KingBlack", "King-B.png");
    	m_mpPieces.put(oPiece.getName(), oPiece);
        oPiece = new Piece("PawnBlack", "Pawn-B.png");
        m_mpPieces.put(oPiece.getName(), oPiece);
}

    public void populatePlayers() {
		m_mpPlayers.put("P1", new Player("P1", "white"));
		m_mpPlayers.put("P2", new Player("P2", "black"));
    }
    
    public void populateMappings() {
    	m_mpMapping.put("P1", new HashMap<String, String>());
    	m_mpMapping.get("P1").put("a1", "RookWhite");
    	m_mpMapping.get("P1").put("h1", "RookWhite");
    	m_mpMapping.get("P1").put("b1", "KnightWhite");
    	m_mpMapping.get("P1").put("g1", "KnightWhite");
    	m_mpMapping.get("P1").put("c1", "BishopWhite");
    	m_mpMapping.get("P1").put("f1", "BishopWhite");
    	m_mpMapping.get("P1").put("d1", "QueenWhite");
    	m_mpMapping.get("P1").put("e1", "KingWhite");

    	m_mpMapping.get("P1").put("a2", "PawnWhite");
    	m_mpMapping.get("P1").put("b2", "PawnWhite");
    	m_mpMapping.get("P1").put("c2", "PawnWhite");
    	m_mpMapping.get("P1").put("d2", "PawnWhite");
    	m_mpMapping.get("P1").put("e2", "PawnWhite");
    	m_mpMapping.get("P1").put("f2", "PawnWhite");
    	m_mpMapping.get("P1").put("g2", "PawnWhite");
    	m_mpMapping.get("P1").put("h2", "PawnWhite");

    	m_mpMapping.put("P2", new HashMap<String, String>());
    	m_mpMapping.get("P2").put("a8", "RookBlack");
    	m_mpMapping.get("P2").put("l8", "RookBlack");
    	m_mpMapping.get("P2").put("b8", "KnightBlack");
    	m_mpMapping.get("P2").put("k8", "KnightBlack");
    	m_mpMapping.get("P2").put("c8", "BishopBlack");
    	m_mpMapping.get("P2").put("j8", "BishopBlack");
    	m_mpMapping.get("P2").put("d8", "QueenBlack");
    	m_mpMapping.get("P2").put("i8", "KingBlack");

    	m_mpMapping.get("P2").put("a7", "PawnBlack");
    	m_mpMapping.get("P2").put("b7", "PawnBlack");
    	m_mpMapping.get("P2").put("c7", "PawnBlack");
    	m_mpMapping.get("P2").put("d7", "PawnBlack");
    	m_mpMapping.get("P2").put("i7", "PawnBlack");
    	m_mpMapping.get("P2").put("j7", "PawnBlack");
    	m_mpMapping.get("P2").put("k7", "PawnBlack");
    	m_mpMapping.get("P2").put("l7", "PawnBlack");

    	
    	//getPosition("e9").setPiece(new PieceAgent(new Piece("KnightWhite", "Knight-W.png"), playerWhite));    
    }
}
