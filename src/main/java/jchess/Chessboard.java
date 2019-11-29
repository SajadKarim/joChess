/*
#    This program is free software: you can redistribute it and/or modify
#    it under the terms of the GNU General Public License as published by
#    the Free Software Foundation, either version 3 of the License, or
#    (at your option) any later version.
#
#    This program is distributed in the hope that it will be useful,
#    but WITHOUT ANY WARRANTY; without even the implied warranty of
#    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#    GNU General Public License for more details.
#
#    You should have received a copy of the GNU General Public License
#    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * Authors:
 * Mateusz Sławomir Lach ( matlak, msl )
 * Damian Marciniak
 */
package jchess;

import java.awt.*;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JPanel;
import jchess.Moves.castling;
import java.util.List;

/** Class to represent chessboard. Chessboard is made from squares.
 * It is setting the squers of chessboard and sets the pieces(pawns)
 * witch the owner is current player on it.
 */
public class Chessboard extends JPanel
{

    public static final int top = 0;
    public static final int bottom = 7;
    public Square squares[][];//squares of chessboard
    private static final Image orgImage = GUI.loadImage("chessboard.png");//image of chessboard
    private static Image image = Chessboard.orgImage;//image of chessboard
    private static final Image org_sel_square = GUI.loadImage("sel_square.png");//image of highlited square
    private static Image sel_square = org_sel_square;//image of highlited square
    private static final Image org_able_square = GUI.loadImage("able_square.png");//image of square where piece can go
    private static Image able_square = org_able_square;//image of square where piece can go
    public Square activeSquare;
    private Image upDownLabel = null;
    private Image LeftRightLabel = null;
    private Point topLeft = new Point(0, 0);
    private int active_x_square;
    private int active_y_square;
    private float square_height;//height of square
    //public Graphics graph;
    public static final int img_x = 5;//image x position (used in JChessView class!)
    public static final int img_y = img_x;//image y position (used in JChessView class!)
    public static final int img_widht = 480;//image width
    public static final int img_height = img_widht;//image height
    private ArrayList moves;
    private Settings settings;
    public King kingWhite;
    public King kingBlack;
    //-------- for undo ----------
    private Square undo1_sq_begin = null;
    private Square undo1_sq_end = null;
    private Piece undo1_piece_begin = null;
    private Piece undo1_piece_end = null;
    private Piece ifWasEnPassant = null;
    private Piece ifWasCastling = null;
    private boolean breakCastling = false; //if last move break castling
    //----------------------------
    //For En passant:
    //|-> Pawn whose in last turn moved two square
    public Pawn twoSquareMovedPawn = null;
    public Pawn twoSquareMovedPawn2 = null;
    private Moves moves_history;

    private List<IPosition> m_lstPosition;
    
    /** Chessboard class constructor
     * @param settings reference to Settings class object for this chessboard
     * @param moves_history reference to Moves class object for this chessboard 
     */
    public Chessboard(Settings settings, Moves moves_history)
    {
    	PopulatePositions();
        this.settings = settings;
        this.activeSquare = null;
        this.square_height = img_height / 8;//we need to devide to know height of field
        this.squares = new Square[8][8];//initalization of 8x8 chessboard
        this.active_x_square = 0;
        this.active_y_square = 0;
        
        int file = 97;        
        for (int i = 0; i < 8; i++)
        {//create object for each square
            for (int y = 0; y < 8; y++)
            {
            	char cfile = (char)file;
            	IPosition oPosition = getPosition("" + cfile + (8 - y));
                this.squares[i][y] = new Square(i, y, null, oPosition);
            }
            file++;
        }//--endOf--create object for each square
        this.moves_history = moves_history;
        this.setDoubleBuffered(true);
        this.drawLabels((int) this.square_height);
    }/*--endOf-Chessboard--*/


    public IPosition getPosition(String stName) {
    	Iterator<IPosition> it = m_lstPosition.iterator();
    	while( it.hasNext()) {
    		IPosition oPosition = it.next();
    		if( oPosition.getName().equals(stName))
    			return oPosition;
    	}

    	return null;
    }
    
    private void PopulatePositions() {
    	m_lstPosition = new ArrayList<IPosition>();
    	
    	m_lstPosition.add( new Position("a1", new Quadrilateral(60*0 ,60*8 ,60*1 , 60*8 , 60*1 , 60*7 , 60*0 , 60*7 )));
    	m_lstPosition.add( new Position("a2", new Quadrilateral(60*0 ,60*7 ,60*1 , 60*7 , 60*1 , 60*6 , 60*0 , 60*6 )));
    	m_lstPosition.add( new Position("a3", new Quadrilateral(60*0 ,60*6 ,60*1 , 60*6 , 60*1 , 60*5 , 60*0 , 60*5 )));
    	m_lstPosition.add( new Position("a4", new Quadrilateral(60*0 ,60*5 ,60*1 , 60*5 , 60*1 , 60*4 , 60*0 , 60*4 )));
    	m_lstPosition.add( new Position("a5", new Quadrilateral(60*0 ,60*4 ,60*1 , 60*4 , 60*1 , 60*3 , 60*0 , 60*3 )));
    	m_lstPosition.add( new Position("a6", new Quadrilateral(60*0 ,60*3 ,60*1 , 60*3 , 60*1 , 60*2 , 60*0 , 60*2 )));
    	m_lstPosition.add( new Position("a7", new Quadrilateral(60*0 ,60*2 ,60*1 , 60*2 , 60*1 , 60*1 , 60*0 , 60*1 )));
    	m_lstPosition.add( new Position("a8", new Quadrilateral(60*0 ,60*1 ,60*1 , 60*1 , 60*1 , 60*0 , 60*0 , 60*0 )));

    	m_lstPosition.add( new Position("b1", new Quadrilateral(60*1 ,60*8 ,60*2 , 60*8 , 60*2 , 60*7 , 60*1 , 60*7 )));
    	m_lstPosition.add( new Position("b2", new Quadrilateral(60*1 ,60*7 ,60*2 , 60*7 , 60*2 , 60*6 , 60*1 , 60*6 )));
    	m_lstPosition.add( new Position("b3", new Quadrilateral(60*1 ,60*6 ,60*2 , 60*6 , 60*2 , 60*5 , 60*1 , 60*5 )));
    	m_lstPosition.add( new Position("b4", new Quadrilateral(60*1 ,60*5 ,60*2 , 60*5 , 60*2 , 60*4 , 60*1 , 60*4 )));
    	m_lstPosition.add( new Position("b5", new Quadrilateral(60*1 ,60*4 ,60*2 , 60*4 , 60*2 , 60*3 , 60*1 , 60*3 )));
    	m_lstPosition.add( new Position("b6", new Quadrilateral(60*1 ,60*3 ,60*2 , 60*3 , 60*2 , 60*2 , 60*1 , 60*2 )));
    	m_lstPosition.add( new Position("b7", new Quadrilateral(60*1 ,60*2 ,60*2 , 60*2 , 60*2 , 60*1 , 60*1 , 60*1 )));
    	m_lstPosition.add( new Position("b8", new Quadrilateral(60*1 ,60*1 ,60*2 , 60*1 , 60*2 , 60*0 , 60*1 , 60*0 )));

    	m_lstPosition.add( new Position("c1", new Quadrilateral(60*2 ,60*8 ,60*3 , 60*8 , 60*3 , 60*7 , 60*2 , 60*7 )));
    	m_lstPosition.add( new Position("c2", new Quadrilateral(60*2 ,60*7 ,60*3 , 60*7 , 60*3 , 60*6 , 60*2 , 60*6 )));
    	m_lstPosition.add( new Position("c3", new Quadrilateral(60*2 ,60*6 ,60*3 , 60*6 , 60*3 , 60*5 , 60*2 , 60*5 )));
    	m_lstPosition.add( new Position("c4", new Quadrilateral(60*2 ,60*5 ,60*3 , 60*5 , 60*3 , 60*4 , 60*2 , 60*4 )));
    	m_lstPosition.add( new Position("c5", new Quadrilateral(60*2 ,60*4 ,60*3 , 60*4 , 60*3 , 60*3 , 60*2 , 60*3 )));
    	m_lstPosition.add( new Position("c6", new Quadrilateral(60*2 ,60*3 ,60*3 , 60*3 , 60*3 , 60*2 , 60*2 , 60*2 )));
    	m_lstPosition.add( new Position("c7", new Quadrilateral(60*2 ,60*2 ,60*3 , 60*2 , 60*3 , 60*1 , 60*2 , 60*1 )));
    	m_lstPosition.add( new Position("c8", new Quadrilateral(60*2 ,60*1 ,60*3 , 60*1 , 60*3 , 60*0 , 60*2 , 60*0 )));

    	m_lstPosition.add( new Position("d1", new Quadrilateral(60*3 ,60*8 ,60*4 , 60*8 , 60*4 , 60*7 , 60*3 , 60*7 )));
    	m_lstPosition.add( new Position("d2", new Quadrilateral(60*3 ,60*7 ,60*4 , 60*7 , 60*4 , 60*6 , 60*3 , 60*6 )));
    	m_lstPosition.add( new Position("d3", new Quadrilateral(60*3 ,60*6 ,60*4 , 60*6 , 60*4 , 60*5 , 60*3 , 60*5 )));
    	m_lstPosition.add( new Position("d4", new Quadrilateral(60*3 ,60*5 ,60*4 , 60*5 , 60*4 , 60*4 , 60*3 , 60*4 )));
    	m_lstPosition.add( new Position("d5", new Quadrilateral(60*3 ,60*4 ,60*4 , 60*4 , 60*4 , 60*3 , 60*3 , 60*3 )));
    	m_lstPosition.add( new Position("d6", new Quadrilateral(60*3 ,60*3 ,60*4 , 60*3 , 60*4 , 60*2 , 60*3 , 60*2 )));
    	m_lstPosition.add( new Position("d7", new Quadrilateral(60*3 ,60*2 ,60*4 , 60*2 , 60*4 , 60*1 , 60*3 , 60*1 )));
    	m_lstPosition.add( new Position("d8", new Quadrilateral(60*3 ,60*1 ,60*4 , 60*1 , 60*4 , 60*0 , 60*3 , 60*0 )));

    	m_lstPosition.add( new Position("e1", new Quadrilateral(60*4 ,60*8 ,60*5 , 60*8 , 60*5 , 60*7 , 60*4 , 60*7 )));
    	m_lstPosition.add( new Position("e2", new Quadrilateral(60*4 ,60*7 ,60*5 , 60*7 , 60*5 , 60*6 , 60*4 , 60*6 )));
    	m_lstPosition.add( new Position("e3", new Quadrilateral(60*4 ,60*6 ,60*5 , 60*6 , 60*5 , 60*5 , 60*4 , 60*5 )));
    	m_lstPosition.add( new Position("e4", new Quadrilateral(60*4 ,60*5 ,60*5 , 60*5 , 60*5 , 60*4 , 60*4 , 60*4 )));
    	m_lstPosition.add( new Position("e5", new Quadrilateral(60*4 ,60*4 ,60*5 , 60*4 , 60*5 , 60*3 , 60*4 , 60*3 )));
    	m_lstPosition.add( new Position("e6", new Quadrilateral(60*4 ,60*3 ,60*5 , 60*3 , 60*5 , 60*2 , 60*4 , 60*2 )));
    	m_lstPosition.add( new Position("e7", new Quadrilateral(60*4 ,60*2 ,60*5 , 60*2 , 60*5 , 60*1 , 60*4 , 60*1 )));
    	m_lstPosition.add( new Position("e8", new Quadrilateral(60*4 ,60*1 ,60*5 , 60*1 , 60*5 , 60*0 , 60*4 , 60*0 )));

    	m_lstPosition.add( new Position("f1", new Quadrilateral(60*5 ,60*8 ,60*6 , 60*8 , 60*6 , 60*7 , 60*5 , 60*7 )));
    	m_lstPosition.add( new Position("f2", new Quadrilateral(60*5 ,60*7 ,60*6 , 60*7 , 60*6 , 60*6 , 60*5 , 60*6 )));
    	m_lstPosition.add( new Position("f3", new Quadrilateral(60*5 ,60*6 ,60*6 , 60*6 , 60*6 , 60*5 , 60*5 , 60*5 )));
    	m_lstPosition.add( new Position("f4", new Quadrilateral(60*5 ,60*5 ,60*6 , 60*5 , 60*6 , 60*4 , 60*5 , 60*4 )));
    	m_lstPosition.add( new Position("f5", new Quadrilateral(60*5 ,60*4 ,60*6 , 60*4 , 60*6 , 60*3 , 60*5 , 60*3 )));
    	m_lstPosition.add( new Position("f6", new Quadrilateral(60*5 ,60*3 ,60*6 , 60*3 , 60*6 , 60*2 , 60*5 , 60*2 )));
    	m_lstPosition.add( new Position("f7", new Quadrilateral(60*5 ,60*2 ,60*6 , 60*2 , 60*6 , 60*1 , 60*5 , 60*1 )));
    	m_lstPosition.add( new Position("f8", new Quadrilateral(60*5 ,60*1 ,60*6 , 60*1 , 60*6 , 60*0 , 60*5 , 60*0 )));

    	m_lstPosition.add( new Position("g1", new Quadrilateral(60*6 ,60*8 ,60*7 , 60*8 , 60*7 , 60*7 , 60*6 , 60*7 )));
    	m_lstPosition.add( new Position("g2", new Quadrilateral(60*6 ,60*7 ,60*7 , 60*7 , 60*7 , 60*6 , 60*6 , 60*6 )));
    	m_lstPosition.add( new Position("g3", new Quadrilateral(60*6 ,60*6 ,60*7 , 60*6 , 60*7 , 60*5 , 60*6 , 60*5 )));
    	m_lstPosition.add( new Position("g4", new Quadrilateral(60*6 ,60*5 ,60*7 , 60*5 , 60*7 , 60*4 , 60*6 , 60*4 )));
    	m_lstPosition.add( new Position("g5", new Quadrilateral(60*6 ,60*4 ,60*7 , 60*4 , 60*7 , 60*3 , 60*6 , 60*3 )));
    	m_lstPosition.add( new Position("g6", new Quadrilateral(60*6 ,60*3 ,60*7 , 60*3 , 60*7 , 60*2 , 60*6 , 60*2 )));
    	m_lstPosition.add( new Position("g7", new Quadrilateral(60*6 ,60*2 ,60*7 , 60*2 , 60*7 , 60*1 , 60*6 , 60*1 )));
    	m_lstPosition.add( new Position("g8", new Quadrilateral(60*6 ,60*1 ,60*7 , 60*1 , 60*7 , 60*0 , 60*6 , 60*0 )));
    	
    	m_lstPosition.add( new Position("h1", new Quadrilateral(60*7 ,60*8 ,60*8 , 60*8 , 60*8 , 60*7 , 60*7 , 60*7 )));
    	m_lstPosition.add( new Position("h2", new Quadrilateral(60*7 ,60*7 ,60*8 , 60*7 , 60*8 , 60*6 , 60*7 , 60*6 )));
    	m_lstPosition.add( new Position("h3", new Quadrilateral(60*7 ,60*6 ,60*8 , 60*6 , 60*8 , 60*5 , 60*7 , 60*5 )));
    	m_lstPosition.add( new Position("h4", new Quadrilateral(60*7 ,60*5 ,60*8 , 60*5 , 60*8 , 60*4 , 60*7 , 60*4 )));
    	m_lstPosition.add( new Position("h5", new Quadrilateral(60*7 ,60*4 ,60*8 , 60*4 , 60*8 , 60*3 , 60*7 , 60*3 )));
    	m_lstPosition.add( new Position("h6", new Quadrilateral(60*7 ,60*3 ,60*8 , 60*3 , 60*8 , 60*2 , 60*7 , 60*2 )));
    	m_lstPosition.add( new Position("h7", new Quadrilateral(60*7 ,60*2 ,60*8 , 60*2 , 60*8 , 60*1 , 60*7 , 60*1 )));
    	m_lstPosition.add( new Position("h8", new Quadrilateral(60*7 ,60*1 ,60*8 , 60*1 , 60*8 , 60*0 , 60*7 , 60*0 )));

    	getPosition("a1").AddEdge(getPosition("a2"));
    	getPosition("a1").AddEdge(getPosition("b1"));
    	getPosition("a1").AddVertex(getPosition("b2"));

    	getPosition("a2").AddEdge(getPosition("a1"));
    	getPosition("a2").AddEdge(getPosition("b2"));
    	getPosition("a2").AddEdge(getPosition("a3"));
    	getPosition("a2").AddVertex(getPosition("b1"));
    	getPosition("a2").AddVertex(getPosition("b3"));

    	getPosition("a3").AddEdge(getPosition("a2"));
    	getPosition("a3").AddEdge(getPosition("b3"));
    	getPosition("a3").AddEdge(getPosition("a4"));
    	getPosition("a3").AddVertex(getPosition("b2"));
    	getPosition("a3").AddVertex(getPosition("b5"));
    	
    	getPosition("a4").AddEdge(getPosition("a3"));
    	getPosition("a4").AddEdge(getPosition("b4"));
    	getPosition("a4").AddEdge(getPosition("a5"));
    	getPosition("a4").AddVertex(getPosition("b3"));
    	getPosition("a4").AddVertex(getPosition("b5"));
    	
    	getPosition("a5").AddEdge(getPosition("a4"));
    	getPosition("a5").AddEdge(getPosition("b5"));
    	getPosition("a5").AddEdge(getPosition("a6"));
    	getPosition("a5").AddVertex(getPosition("b4"));
    	getPosition("a5").AddVertex(getPosition("b5"));

    	getPosition("a6").AddEdge(getPosition("a5"));
    	getPosition("a6").AddEdge(getPosition("b6"));
    	getPosition("a6").AddEdge(getPosition("a7"));
    	getPosition("a6").AddVertex(getPosition("b5"));
    	getPosition("a6").AddVertex(getPosition("b7"));

    	getPosition("a7").AddEdge(getPosition("a6"));
    	getPosition("a7").AddEdge(getPosition("b7"));
    	getPosition("a7").AddEdge(getPosition("a8"));
    	getPosition("a7").AddVertex(getPosition("b6"));
    	getPosition("a7").AddVertex(getPosition("b8"));

    	getPosition("a8").AddEdge(getPosition("a7"));
    	getPosition("a8").AddEdge(getPosition("b8"));
    	getPosition("a8").AddVertex(getPosition("b7"));


    	// File b
    	getPosition("b1").AddEdge(getPosition("a1"));
    	getPosition("b1").AddEdge(getPosition("b2"));
    	getPosition("b1").AddEdge(getPosition("c1"));
    	getPosition("b1").AddVertex(getPosition("a2"));
    	getPosition("b1").AddVertex(getPosition("c2"));

    	getPosition("b2").AddEdge(getPosition("a2"));
    	getPosition("b2").AddEdge(getPosition("b1"));
    	getPosition("b2").AddEdge(getPosition("c2"));
    	getPosition("b2").AddEdge(getPosition("b3"));
    	getPosition("b2").AddVertex(getPosition("a1"));
    	getPosition("b2").AddVertex(getPosition("c1"));
    	getPosition("b2").AddVertex(getPosition("a3"));
    	getPosition("b2").AddVertex(getPosition("c3"));

    	getPosition("b3").AddEdge(getPosition("a3"));
    	getPosition("b3").AddEdge(getPosition("b2"));
    	getPosition("b3").AddEdge(getPosition("c3"));
    	getPosition("b3").AddEdge(getPosition("b4"));
    	getPosition("b3").AddVertex(getPosition("a2"));
    	getPosition("b3").AddVertex(getPosition("c2"));
    	getPosition("b3").AddVertex(getPosition("a4"));
    	getPosition("b3").AddVertex(getPosition("c4"));
    	
    	getPosition("b4").AddEdge(getPosition("a4"));
    	getPosition("b4").AddEdge(getPosition("b3"));
    	getPosition("b4").AddEdge(getPosition("c4"));
    	getPosition("b4").AddEdge(getPosition("b5"));
    	getPosition("b4").AddVertex(getPosition("a3"));
    	getPosition("b4").AddVertex(getPosition("c3"));
    	getPosition("b4").AddVertex(getPosition("a5"));
    	getPosition("b4").AddVertex(getPosition("c5"));

    	getPosition("b5").AddEdge(getPosition("a5"));
    	getPosition("b5").AddEdge(getPosition("b4"));
    	getPosition("b5").AddEdge(getPosition("c5"));
    	getPosition("b5").AddEdge(getPosition("b6"));
    	getPosition("b5").AddVertex(getPosition("a4"));
    	getPosition("b5").AddVertex(getPosition("c4"));
    	getPosition("b5").AddVertex(getPosition("a6"));
    	getPosition("b5").AddVertex(getPosition("c6"));

    	getPosition("b6").AddEdge(getPosition("a6"));
    	getPosition("b6").AddEdge(getPosition("b5"));
    	getPosition("b6").AddEdge(getPosition("c6"));
    	getPosition("b6").AddEdge(getPosition("b7"));
    	getPosition("b6").AddVertex(getPosition("a5"));
    	getPosition("b6").AddVertex(getPosition("c5"));
    	getPosition("b6").AddVertex(getPosition("a7"));
    	getPosition("b6").AddVertex(getPosition("c7"));

    	getPosition("b7").AddEdge(getPosition("a7"));
    	getPosition("b7").AddEdge(getPosition("b6"));
    	getPosition("b7").AddEdge(getPosition("c7"));
    	getPosition("b7").AddEdge(getPosition("b8"));
    	getPosition("b7").AddVertex(getPosition("a6"));
    	getPosition("b7").AddVertex(getPosition("c6"));
    	getPosition("b7").AddVertex(getPosition("a8"));
    	getPosition("b7").AddVertex(getPosition("c8"));

    	getPosition("b8").AddEdge(getPosition("a8"));
    	getPosition("b8").AddEdge(getPosition("b7"));
    	getPosition("b8").AddEdge(getPosition("c8"));
    	getPosition("b8").AddVertex(getPosition("a7"));
    	getPosition("b8").AddVertex(getPosition("c7"));
    	
    	// c
    	getPosition("c1").AddEdge(getPosition("b1"));
    	getPosition("c1").AddEdge(getPosition("c2"));
    	getPosition("c1").AddEdge(getPosition("d1"));
    	getPosition("c1").AddVertex(getPosition("b2"));
    	getPosition("c1").AddVertex(getPosition("d2"));

    	getPosition("c2").AddEdge(getPosition("b2"));
    	getPosition("c2").AddEdge(getPosition("c1"));
    	getPosition("c2").AddEdge(getPosition("d2"));
    	getPosition("c2").AddEdge(getPosition("c3"));
    	getPosition("c2").AddVertex(getPosition("b1"));
    	getPosition("c2").AddVertex(getPosition("d1"));
    	getPosition("c2").AddVertex(getPosition("b3"));
    	getPosition("c2").AddVertex(getPosition("d3"));

    	getPosition("c3").AddEdge(getPosition("b3"));
    	getPosition("c3").AddEdge(getPosition("c2"));
    	getPosition("c3").AddEdge(getPosition("d3"));
    	getPosition("c3").AddEdge(getPosition("c4"));
    	getPosition("c3").AddVertex(getPosition("b2"));
    	getPosition("c3").AddVertex(getPosition("d2"));
    	getPosition("c3").AddVertex(getPosition("b4"));
    	getPosition("c3").AddVertex(getPosition("d4"));
    	
    	getPosition("c4").AddEdge(getPosition("b4"));
    	getPosition("c4").AddEdge(getPosition("c3"));
    	getPosition("c4").AddEdge(getPosition("d4"));
    	getPosition("c4").AddEdge(getPosition("c5"));
    	getPosition("c4").AddVertex(getPosition("b3"));
    	getPosition("c4").AddVertex(getPosition("d3"));
    	getPosition("c4").AddVertex(getPosition("b5"));
    	getPosition("c4").AddVertex(getPosition("d5"));

    	getPosition("c5").AddEdge(getPosition("b5"));
    	getPosition("c5").AddEdge(getPosition("c4"));
    	getPosition("c5").AddEdge(getPosition("d5"));
    	getPosition("c5").AddEdge(getPosition("c6"));
    	getPosition("c5").AddVertex(getPosition("b4"));
    	getPosition("c5").AddVertex(getPosition("d4"));
    	getPosition("c5").AddVertex(getPosition("b6"));
    	getPosition("c5").AddVertex(getPosition("d6"));

    	getPosition("c6").AddEdge(getPosition("b6"));
    	getPosition("c6").AddEdge(getPosition("c5"));
    	getPosition("c6").AddEdge(getPosition("d6"));
    	getPosition("c6").AddEdge(getPosition("c7"));
    	getPosition("c6").AddVertex(getPosition("b5"));
    	getPosition("c6").AddVertex(getPosition("d5"));
    	getPosition("c6").AddVertex(getPosition("b7"));
    	getPosition("c6").AddVertex(getPosition("d7"));

    	getPosition("c7").AddEdge(getPosition("b7"));
    	getPosition("c7").AddEdge(getPosition("c6"));
    	getPosition("c7").AddEdge(getPosition("d7"));
    	getPosition("c7").AddEdge(getPosition("c8"));
    	getPosition("c7").AddVertex(getPosition("b6"));
    	getPosition("c7").AddVertex(getPosition("d6"));
    	getPosition("c7").AddVertex(getPosition("b8"));
    	getPosition("c7").AddVertex(getPosition("d8"));

    	getPosition("c8").AddEdge(getPosition("b8"));
    	getPosition("c8").AddEdge(getPosition("c7"));
    	getPosition("c8").AddEdge(getPosition("b8"));
    	getPosition("c8").AddVertex(getPosition("b7"));
    	getPosition("c8").AddVertex(getPosition("d7"));
    	
    	// d
    	getPosition("d1").AddEdge(getPosition("c1"));
    	getPosition("d1").AddEdge(getPosition("d2"));
    	getPosition("d1").AddEdge(getPosition("e1"));
    	getPosition("d1").AddVertex(getPosition("c2"));
    	getPosition("d1").AddVertex(getPosition("e2"));

    	getPosition("d2").AddEdge(getPosition("c2"));
    	getPosition("d2").AddEdge(getPosition("d1"));
    	getPosition("d2").AddEdge(getPosition("e2"));
    	getPosition("d2").AddEdge(getPosition("d3"));
    	getPosition("d2").AddVertex(getPosition("c1"));
    	getPosition("d2").AddVertex(getPosition("e1"));
    	getPosition("d2").AddVertex(getPosition("c3"));
    	getPosition("d2").AddVertex(getPosition("e3"));

    	getPosition("d3").AddEdge(getPosition("c3"));
    	getPosition("d3").AddEdge(getPosition("d2"));
    	getPosition("d3").AddEdge(getPosition("e3"));
    	getPosition("d3").AddEdge(getPosition("d4"));
    	getPosition("d3").AddVertex(getPosition("c2"));
    	getPosition("d3").AddVertex(getPosition("e2"));
    	getPosition("d3").AddVertex(getPosition("c4"));
    	getPosition("d3").AddVertex(getPosition("e4"));
    	
    	getPosition("d4").AddEdge(getPosition("c4"));
    	getPosition("d4").AddEdge(getPosition("d3"));
    	getPosition("d4").AddEdge(getPosition("e4"));
    	getPosition("d4").AddEdge(getPosition("d5"));
    	getPosition("d4").AddVertex(getPosition("c3"));
    	getPosition("d4").AddVertex(getPosition("e3"));
    	getPosition("d4").AddVertex(getPosition("c5"));
    	getPosition("d4").AddVertex(getPosition("e5"));

    	getPosition("d5").AddEdge(getPosition("c5"));
    	getPosition("d5").AddEdge(getPosition("d4"));
    	getPosition("d5").AddEdge(getPosition("e5"));
    	getPosition("d5").AddEdge(getPosition("d6"));
    	getPosition("d5").AddVertex(getPosition("c4"));
    	getPosition("d5").AddVertex(getPosition("e4"));
    	getPosition("d5").AddVertex(getPosition("c6"));
    	getPosition("d5").AddVertex(getPosition("e6"));

    	getPosition("d6").AddEdge(getPosition("c6"));
    	getPosition("d6").AddEdge(getPosition("d5"));
    	getPosition("d6").AddEdge(getPosition("e6"));
    	getPosition("d6").AddEdge(getPosition("d7"));
    	getPosition("d6").AddVertex(getPosition("c5"));
    	getPosition("d6").AddVertex(getPosition("e5"));
    	getPosition("d6").AddVertex(getPosition("c7"));
    	getPosition("d6").AddVertex(getPosition("e7"));

    	getPosition("d7").AddEdge(getPosition("c7"));
    	getPosition("d7").AddEdge(getPosition("d6"));
    	getPosition("d7").AddEdge(getPosition("e7"));
    	getPosition("d7").AddEdge(getPosition("d8"));
    	getPosition("d7").AddVertex(getPosition("c6"));
    	getPosition("d7").AddVertex(getPosition("e6"));
    	getPosition("d7").AddVertex(getPosition("c8"));
    	getPosition("d7").AddVertex(getPosition("e8"));

    	getPosition("d8").AddEdge(getPosition("c8"));
    	getPosition("d8").AddEdge(getPosition("d7"));
    	getPosition("d8").AddEdge(getPosition("c8"));
    	getPosition("d8").AddVertex(getPosition("c7"));
    	getPosition("d8").AddVertex(getPosition("e7"));

    	// e
    	getPosition("e1").AddEdge(getPosition("d1"));
    	getPosition("e1").AddEdge(getPosition("e2"));
    	getPosition("e1").AddEdge(getPosition("f1"));
    	getPosition("e1").AddVertex(getPosition("d2"));
    	getPosition("e1").AddVertex(getPosition("f2"));

    	getPosition("e2").AddEdge(getPosition("d2"));
    	getPosition("e2").AddEdge(getPosition("e1"));
    	getPosition("e2").AddEdge(getPosition("f2"));
    	getPosition("e2").AddEdge(getPosition("e3"));
    	getPosition("e2").AddVertex(getPosition("d1"));
    	getPosition("e2").AddVertex(getPosition("f1"));
    	getPosition("e2").AddVertex(getPosition("d3"));
    	getPosition("e2").AddVertex(getPosition("f3"));

    	getPosition("e3").AddEdge(getPosition("d3"));
    	getPosition("e3").AddEdge(getPosition("e2"));
    	getPosition("e3").AddEdge(getPosition("f3"));
    	getPosition("e3").AddEdge(getPosition("e4"));
    	getPosition("e3").AddVertex(getPosition("d2"));
    	getPosition("e3").AddVertex(getPosition("f2"));
    	getPosition("e3").AddVertex(getPosition("d4"));
    	getPosition("e3").AddVertex(getPosition("f4"));
    	
    	getPosition("e4").AddEdge(getPosition("d4"));
    	getPosition("e4").AddEdge(getPosition("e3"));
    	getPosition("e4").AddEdge(getPosition("f4"));
    	getPosition("e4").AddEdge(getPosition("e5"));
    	getPosition("e4").AddVertex(getPosition("d3"));
    	getPosition("e4").AddVertex(getPosition("f3"));
    	getPosition("e4").AddVertex(getPosition("d5"));
    	getPosition("e4").AddVertex(getPosition("f5"));

    	getPosition("e5").AddEdge(getPosition("d5"));
    	getPosition("e5").AddEdge(getPosition("e4"));
    	getPosition("e5").AddEdge(getPosition("f5"));
    	getPosition("e5").AddEdge(getPosition("e6"));
    	getPosition("e5").AddVertex(getPosition("d4"));
    	getPosition("e5").AddVertex(getPosition("f4"));
    	getPosition("e5").AddVertex(getPosition("d6"));
    	getPosition("e5").AddVertex(getPosition("f6"));

    	getPosition("e6").AddEdge(getPosition("d6"));
    	getPosition("e6").AddEdge(getPosition("e5"));
    	getPosition("e6").AddEdge(getPosition("f6"));
    	getPosition("e6").AddEdge(getPosition("e7"));
    	getPosition("e6").AddVertex(getPosition("d5"));
    	getPosition("e6").AddVertex(getPosition("f5"));
    	getPosition("e6").AddVertex(getPosition("d7"));
    	getPosition("e6").AddVertex(getPosition("f7"));

    	getPosition("e7").AddEdge(getPosition("d7"));
    	getPosition("e7").AddEdge(getPosition("e6"));
    	getPosition("e7").AddEdge(getPosition("f7"));
    	getPosition("e7").AddEdge(getPosition("e8"));
    	getPosition("e7").AddVertex(getPosition("d6"));
    	getPosition("e7").AddVertex(getPosition("f6"));
    	getPosition("e7").AddVertex(getPosition("d8"));
    	getPosition("e7").AddVertex(getPosition("f8"));

    	getPosition("e8").AddEdge(getPosition("d8"));
    	getPosition("e8").AddEdge(getPosition("e7"));
    	getPosition("e8").AddEdge(getPosition("d8"));
    	getPosition("e8").AddVertex(getPosition("d7"));
    	getPosition("e8").AddVertex(getPosition("f7"));

    	// f
    	getPosition("f1").AddEdge(getPosition("e1"));
    	getPosition("f1").AddEdge(getPosition("f2"));
    	getPosition("f1").AddEdge(getPosition("g1"));
    	getPosition("f1").AddVertex(getPosition("e2"));
    	getPosition("f1").AddVertex(getPosition("g2"));

    	getPosition("f2").AddEdge(getPosition("e2"));
    	getPosition("f2").AddEdge(getPosition("f1"));
    	getPosition("f2").AddEdge(getPosition("g2"));
    	getPosition("f2").AddEdge(getPosition("f3"));
    	getPosition("f2").AddVertex(getPosition("e1"));
    	getPosition("f2").AddVertex(getPosition("g1"));
    	getPosition("f2").AddVertex(getPosition("e3"));
    	getPosition("f2").AddVertex(getPosition("g3"));

    	getPosition("f3").AddEdge(getPosition("e3"));
    	getPosition("f3").AddEdge(getPosition("f2"));
    	getPosition("f3").AddEdge(getPosition("g3"));
    	getPosition("f3").AddEdge(getPosition("f4"));
    	getPosition("f3").AddVertex(getPosition("e2"));
    	getPosition("f3").AddVertex(getPosition("g2"));
    	getPosition("f3").AddVertex(getPosition("e4"));
    	getPosition("f3").AddVertex(getPosition("g4"));
    	
    	getPosition("f4").AddEdge(getPosition("e4"));
    	getPosition("f4").AddEdge(getPosition("f3"));
    	getPosition("f4").AddEdge(getPosition("g4"));
    	getPosition("f4").AddEdge(getPosition("f5"));
    	getPosition("f4").AddVertex(getPosition("e3"));
    	getPosition("f4").AddVertex(getPosition("g3"));
    	getPosition("f4").AddVertex(getPosition("e5"));
    	getPosition("f4").AddVertex(getPosition("g5"));

    	getPosition("f5").AddEdge(getPosition("e5"));
    	getPosition("f5").AddEdge(getPosition("f4"));
    	getPosition("f5").AddEdge(getPosition("g5"));
    	getPosition("f5").AddEdge(getPosition("f6"));
    	getPosition("f5").AddVertex(getPosition("e4"));
    	getPosition("f5").AddVertex(getPosition("g4"));
    	getPosition("f5").AddVertex(getPosition("e6"));
    	getPosition("f5").AddVertex(getPosition("g6"));

    	getPosition("f6").AddEdge(getPosition("e6"));
    	getPosition("f6").AddEdge(getPosition("f5"));
    	getPosition("f6").AddEdge(getPosition("g6"));
    	getPosition("f6").AddEdge(getPosition("f7"));
    	getPosition("f6").AddVertex(getPosition("e5"));
    	getPosition("f6").AddVertex(getPosition("g5"));
    	getPosition("f6").AddVertex(getPosition("e7"));
    	getPosition("f6").AddVertex(getPosition("g7"));

    	getPosition("f7").AddEdge(getPosition("e7"));
    	getPosition("f7").AddEdge(getPosition("f6"));
    	getPosition("f7").AddEdge(getPosition("g7"));
    	getPosition("f7").AddEdge(getPosition("f8"));
    	getPosition("f7").AddVertex(getPosition("e6"));
    	getPosition("f7").AddVertex(getPosition("g6"));
    	getPosition("f7").AddVertex(getPosition("e8"));
    	getPosition("f7").AddVertex(getPosition("g8"));

    	getPosition("f8").AddEdge(getPosition("e8"));
    	getPosition("f8").AddEdge(getPosition("f7"));
    	getPosition("f8").AddEdge(getPosition("g8"));
    	getPosition("f8").AddVertex(getPosition("e7"));
    	getPosition("f8").AddVertex(getPosition("g7"));

    	// g
    	getPosition("g1").AddEdge(getPosition("f1"));
    	getPosition("g1").AddEdge(getPosition("g2"));
    	getPosition("g1").AddEdge(getPosition("h1"));
    	getPosition("g1").AddVertex(getPosition("f2"));
    	getPosition("g1").AddVertex(getPosition("h2"));

    	getPosition("g2").AddEdge(getPosition("f2"));
    	getPosition("g2").AddEdge(getPosition("g1"));
    	getPosition("g2").AddEdge(getPosition("h2"));
    	getPosition("g2").AddEdge(getPosition("g3"));
    	getPosition("g2").AddVertex(getPosition("f1"));
    	getPosition("g2").AddVertex(getPosition("h1"));
    	getPosition("g2").AddVertex(getPosition("f3"));
    	getPosition("g2").AddVertex(getPosition("h3"));

    	getPosition("g3").AddEdge(getPosition("f3"));
    	getPosition("g3").AddEdge(getPosition("g2"));
    	getPosition("g3").AddEdge(getPosition("h3"));
    	getPosition("g3").AddEdge(getPosition("g4"));
    	getPosition("g3").AddVertex(getPosition("f2"));
    	getPosition("g3").AddVertex(getPosition("h2"));
    	getPosition("g3").AddVertex(getPosition("f4"));
    	getPosition("g3").AddVertex(getPosition("h4"));
    	
    	getPosition("g4").AddEdge(getPosition("f4"));
    	getPosition("g4").AddEdge(getPosition("g3"));
    	getPosition("g4").AddEdge(getPosition("h4"));
    	getPosition("g4").AddEdge(getPosition("g5"));
    	getPosition("g4").AddVertex(getPosition("f3"));
    	getPosition("g4").AddVertex(getPosition("h3"));
    	getPosition("g4").AddVertex(getPosition("f5"));
    	getPosition("g4").AddVertex(getPosition("h5"));

    	getPosition("g5").AddEdge(getPosition("f5"));
    	getPosition("g5").AddEdge(getPosition("g4"));
    	getPosition("g5").AddEdge(getPosition("h5"));
    	getPosition("g5").AddEdge(getPosition("g6"));
    	getPosition("g5").AddVertex(getPosition("f4"));
    	getPosition("g5").AddVertex(getPosition("h4"));
    	getPosition("g5").AddVertex(getPosition("f6"));
    	getPosition("g5").AddVertex(getPosition("h6"));

    	getPosition("g6").AddEdge(getPosition("f6"));
    	getPosition("g6").AddEdge(getPosition("g5"));
    	getPosition("g6").AddEdge(getPosition("h6"));
    	getPosition("g6").AddEdge(getPosition("g7"));
    	getPosition("g6").AddVertex(getPosition("f5"));
    	getPosition("g6").AddVertex(getPosition("h5"));
    	getPosition("g6").AddVertex(getPosition("f7"));
    	getPosition("g6").AddVertex(getPosition("h7"));

    	getPosition("g7").AddEdge(getPosition("f7"));
    	getPosition("g7").AddEdge(getPosition("g6"));
    	getPosition("g7").AddEdge(getPosition("h7"));
    	getPosition("g7").AddEdge(getPosition("g8"));
    	getPosition("g7").AddVertex(getPosition("f6"));
    	getPosition("g7").AddVertex(getPosition("h6"));
    	getPosition("g7").AddVertex(getPosition("f8"));
    	getPosition("g7").AddVertex(getPosition("h8"));

    	getPosition("g8").AddEdge(getPosition("f8"));
    	getPosition("g8").AddEdge(getPosition("g7"));
    	getPosition("g8").AddEdge(getPosition("h8"));
    	getPosition("g8").AddVertex(getPosition("f7"));
    	getPosition("g8").AddVertex(getPosition("h7"));

    	// h
    	getPosition("h1").AddEdge(getPosition("g1"));
    	getPosition("h1").AddEdge(getPosition("h2"));
    	getPosition("h1").AddVertex(getPosition("g2"));

    	getPosition("h2").AddEdge(getPosition("g2"));
    	getPosition("h2").AddEdge(getPosition("h1"));
    	getPosition("h2").AddEdge(getPosition("h3"));
    	getPosition("h2").AddVertex(getPosition("g1"));
    	getPosition("h2").AddVertex(getPosition("g3"));

    	getPosition("h3").AddEdge(getPosition("g3"));
    	getPosition("h3").AddEdge(getPosition("h2"));
    	getPosition("h3").AddEdge(getPosition("h4"));
    	getPosition("h3").AddVertex(getPosition("g2"));
    	getPosition("h3").AddVertex(getPosition("g4"));
    	
    	getPosition("h4").AddEdge(getPosition("g4"));
    	getPosition("h4").AddEdge(getPosition("h3"));
    	getPosition("h4").AddEdge(getPosition("h5"));
    	getPosition("h4").AddVertex(getPosition("g3"));
    	getPosition("h4").AddVertex(getPosition("g5"));

    	getPosition("h5").AddEdge(getPosition("g5"));
    	getPosition("h5").AddEdge(getPosition("h4"));
    	getPosition("h5").AddEdge(getPosition("h6"));
    	getPosition("h5").AddVertex(getPosition("g4"));
    	getPosition("h5").AddVertex(getPosition("g6"));

    	getPosition("h6").AddEdge(getPosition("g6"));
    	getPosition("h6").AddEdge(getPosition("h5"));
    	getPosition("h6").AddEdge(getPosition("h7"));
    	getPosition("h6").AddVertex(getPosition("g5"));
    	getPosition("h6").AddVertex(getPosition("g7"));

    	getPosition("h7").AddEdge(getPosition("g7"));
    	getPosition("h7").AddEdge(getPosition("h6"));
    	getPosition("h7").AddEdge(getPosition("h8"));
    	getPosition("h7").AddVertex(getPosition("g6"));
    	getPosition("h7").AddVertex(getPosition("g8"));

    	getPosition("h8").AddEdge(getPosition("g8"));
    	getPosition("h8").AddEdge(getPosition("h7"));
    	getPosition("h8").AddVertex(getPosition("g7"));
}
    
    /** Method setPieces on begin of new game or loaded game
     * @param places string with pieces to set on chessboard
     * @param plWhite reference to white player
     * @param plBlack reference to black player
     */
    public void setPieces(String places, Player plWhite, Player plBlack)
    {

        if (places.equals("")) //if newGame
        {
            if (this.settings.upsideDown)
            {
                this.setPieces4NewGame(true, plWhite, plBlack);
            }
            else
            {
                this.setPieces4NewGame(false, plWhite, plBlack);
            }

        } 
        else //if loadedGame
        {
            return;
        }
    }/*--endOf-setPieces--*/


    /**
     *
     */
    private void setPieces4NewGame(boolean upsideDown, Player plWhite, Player plBlack)
    {

        /* WHITE PIECES */
        Player player = plBlack;
        Player player1 = plWhite;
        if (upsideDown) //if white on Top
        { 
            player = plWhite;
            player1 = plBlack;
        }
        this.setFigures4NewGame(0, player, upsideDown);
        this.setPawns4NewGame(1, player);
        this.setFigures4NewGame(7, player1, upsideDown);
        this.setPawns4NewGame(6, player1);
    }/*--endOf-setPieces(boolean upsideDown)--*/


    /**  method set Figures in row (and set Queen and King to right position)
     *  @param i row where to set figures (Rook, Knight etc.)
     *  @param player which is owner of pawns
     *  @param upsideDown if true white pieces will be on top of chessboard
     * */
    private void setFigures4NewGame(int i, Player player, boolean upsideDown)
    {

        if (i != 0 && i != 7)
        {
            System.out.println("error setting figures like rook etc.");
            return;
        }
        else if (i == 0)
        {
            player.goDown = true;
        }

        this.squares[0][i].setPiece(new Rook(this, player));
        this.squares[7][i].setPiece(new Rook(this, player));
        this.squares[1][i].setPiece(new Knight(this, player));
        this.squares[6][i].setPiece(new Knight(this, player));
        this.squares[2][i].setPiece(new Bishop(this, player));
        this.squares[5][i].setPiece(new Bishop(this, player));
        if (upsideDown)
        {
            this.squares[4][i].setPiece(new Queen(this, player));
            if (player.color == Player.colors.white)
            {
                this.squares[3][i].setPiece(kingWhite = new King(this, player));
            }
            else
            {
                this.squares[3][i].setPiece(kingBlack = new King(this, player));
            }
        }
        else
        {
            this.squares[3][i].setPiece(new Queen(this, player));
            if (player.color == Player.colors.white)
            {
                this.squares[4][i].setPiece(kingWhite = new King(this, player));
            }
            else
            {
                this.squares[4][i].setPiece(kingBlack = new King(this, player));
            }
        }
    }

    /**  method set Pawns in row
     *  @param i row where to set pawns
     *  @param player player which is owner of pawns
     * */
    private void setPawns4NewGame(int i, Player player)
    {
        if (i != 1 && i != 6)
        {
            System.out.println("error setting pawns etc.");
            return;
        }
        for (int x = 0; x < 8; x++)
        {
            this.squares[x][i].setPiece(new Pawn(this, player));
        }
    }

    /** method to get reference to square from given x and y integeres
     * @param x x position on chessboard
     * @param y y position on chessboard
     * @return reference to searched square
     */
    public Square getSquare(int x, int y)
    { 
        if ((x > this.get_height()) || (y > this.get_widht())) //test if click is out of chessboard
        {
            System.out.println("click out of chessboard.");
            return null;
        }
        if (this.settings.renderLabels)
        {
            x -= this.upDownLabel.getHeight(null);
            y -= this.upDownLabel.getHeight(null);
        }
        double square_x = x / square_height;//count which field in X was clicked
        double square_y = y / square_height;//count which field in Y was clicked

        if (square_x > (int) square_x) //if X is more than X parsed to Integer
        {
            square_x = (int) square_x + 1;//parse to integer and increment
        }
        if (square_y > (int) square_y) //if X is more than X parsed to Integer
        {
            square_y = (int) square_y + 1;//parse to integer and increment
        }
        //Square newActiveSquare = this.squares[(int)square_x-1][(int)square_y-1];//4test
        System.out.println("square_x: " + square_x + " square_y: " + square_y + " \n"); //4tests
        Square result;
        try
        {
            result = this.squares[(int) square_x - 1][(int) square_y - 1];
        }
        catch (java.lang.ArrayIndexOutOfBoundsException exc)
        {
            System.out.println("!!Array out of bounds when getting Square with Chessboard.getSquare(int,int) : " + exc);
            return null;
        }
        return this.squares[(int) square_x - 1][(int) square_y - 1];
    }

    /** Method selecting piece in chessboard
     * @param  sq square to select (when clicked))
     */
    public void select(Square sq)
    {
        this.activeSquare = sq;
        this.active_x_square = sq.pozX + 1;
        this.active_y_square = sq.pozY + 1;

        //this.draw();//redraw
        System.out.println("active_x: " + this.active_x_square + " active_y: " + this.active_y_square);//4tests
        repaint();

    }/*--endOf-select--*/


    /** Method set variables active_x_square & active_y_square
     * to 0 values.
     */
    public void unselect()
    {
        this.active_x_square = 0;
        this.active_y_square = 0;
        this.activeSquare = null;
        //this.draw();//redraw
        repaint();
    }/*--endOf-unselect--*/
    
    public int get_widht()
    {
        return this.get_widht(false);
    }
    
    public int get_height()
    {
        return this.get_height(false);
    }


    public int get_widht(boolean includeLables)
    {
        return this.getHeight();
    }/*--endOf-get_widht--*/


    int get_height(boolean includeLabels)
    {
        if (this.settings.renderLabels)
        {
            return Chessboard.image.getHeight(null) + upDownLabel.getHeight(null);
        }
        return Chessboard.image.getHeight(null);
    }/*--endOf-get_height--*/


    int get_square_height()
    {
        int result = (int) this.square_height;
        return result;
    }

    public void move(Square begin, Square end)
    {
        move(begin, end, true);
    }

    /** Method to move piece over chessboard
     * @param xFrom from which x move piece
     * @param yFrom from which y move piece
     * @param xTo to which x move piece
     * @param yTo to which y move piece
     */
    public void move(int xFrom, int yFrom, int xTo, int yTo)
    {
        Square fromSQ = null;
        Square toSQ = null;
        try
        {
            fromSQ = this.squares[xFrom][yFrom];
            toSQ = this.squares[xTo][yTo];
        }
        catch (java.lang.IndexOutOfBoundsException exc)
        {
            System.out.println("error moving piece: " + exc);
            return;
        }
        this.move(this.squares[xFrom][yFrom], this.squares[xTo][yTo], true);
    }

    public void move(Square begin, Square end, boolean refresh)
    {
        this.move(begin, end, refresh, true);
    }

    /** Method move piece from square to square
     * @param begin square from which move piece
     * @param end square where we want to move piece         *
     * @param refresh chessboard, default: true
     * */
    public void move(Square begin, Square end, boolean refresh, boolean clearForwardHistory)
    {

        castling wasCastling = Moves.castling.none;
        Piece promotedPiece = null;
        boolean wasEnPassant = false;
        if (end.piece != null)
        {
            end.piece.square = null;
        }

        Square tempBegin = new Square(begin);//4 moves history
        Square tempEnd = new Square(end);  //4 moves history
        //for undo
        undo1_piece_begin = begin.piece;
        undo1_sq_begin = begin;
        undo1_piece_end = end.piece;
        undo1_sq_end = end;
        ifWasEnPassant = null;
        ifWasCastling = null;
        breakCastling = false;
        // ---

        twoSquareMovedPawn2 = twoSquareMovedPawn;

        begin.piece.square = end;//set square of piece to ending
        end.piece = begin.piece;//for ending square set piece from beginin square
        begin.piece = null;//make null piece for begining square

        begin.m_oPosition.setPiece(null);
        end.m_oPosition.setPiece(begin.piece);
        
        if (end.piece.name.equals("King"))
        {
            if (!((King) end.piece).wasMotion)
            {
                breakCastling = true;
                ((King) end.piece).wasMotion = true;
            }

            //Castling
            if (begin.pozX + 2 == end.pozX)
            {
                move(squares[7][begin.pozY], squares[end.pozX - 1][begin.pozY], false, false);
                ifWasCastling = end.piece;  //for undo
                wasCastling = Moves.castling.shortCastling;
                //this.moves_history.addMove(tempBegin, tempEnd, clearForwardHistory, wasCastling, wasEnPassant);
                //return;
            }
            else if (begin.pozX - 2 == end.pozX)
            {
                move(squares[0][begin.pozY], squares[end.pozX + 1][begin.pozY], false, false);
                ifWasCastling = end.piece;  // for undo
                wasCastling = Moves.castling.longCastling;
                //this.moves_history.addMove(tempBegin, tempEnd, clearForwardHistory, wasCastling, wasEnPassant);
                //return;
            }
            //endOf Castling
        }
        else if (end.piece.name.equals("Rook"))
        {
            if (!((Rook) end.piece).wasMotion)
            {
                breakCastling = true;
                ((Rook) end.piece).wasMotion = true;
            }
        }
        else if (end.piece.name.equals("Pawn"))
        {
            if (twoSquareMovedPawn != null && squares[end.pozX][begin.pozY] == twoSquareMovedPawn.square) //en passant
            {
                ifWasEnPassant = squares[end.pozX][begin.pozY].piece; //for undo

                tempEnd.piece = squares[end.pozX][begin.pozY].piece; //ugly hack - put taken pawn in en passant plasty do end square

                squares[end.pozX][begin.pozY].piece = null;
                wasEnPassant = true;
            }

            if (begin.pozY - end.pozY == 2 || end.pozY - begin.pozY == 2) //moved two square
            {
                breakCastling = true;
                twoSquareMovedPawn = (Pawn) end.piece;
            }
            else
            {
                twoSquareMovedPawn = null; //erase last saved move (for En passant)
            }

            if (end.piece.square.pozY == 0 || end.piece.square.pozY == 7) //promote Pawn
            {
                if (clearForwardHistory)
                {
                    String color;
                    if (end.piece.player.color == Player.colors.white)
                    {
                        color = "W"; // promotionWindow was show with pieces in this color
                    }
                    else
                    {
                        color = "B";
                    }

                    String newPiece = JChessApp.jcv.showPawnPromotionBox(color); //return name of new piece

                    if (newPiece.equals("Queen")) // transform pawn to queen
                    {
                        Queen queen = new Queen(this, end.piece.player);
                        queen.chessboard = end.piece.chessboard;
                        queen.player = end.piece.player;
                        queen.square = end.piece.square;
                        end.piece = queen;
                    }
                    else if (newPiece.equals("Rook")) // transform pawn to rook
                    {
                        Rook rook = new Rook(this, end.piece.player);
                        rook.chessboard = end.piece.chessboard;
                        rook.player = end.piece.player;
                        rook.square = end.piece.square;
                        end.piece = rook;
                    }
                    else if (newPiece.equals("Bishop")) // transform pawn to bishop
                    {
                        Bishop bishop = new Bishop(this, end.piece.player);
                        bishop.chessboard = end.piece.chessboard;
                        bishop.player = end.piece.player;
                        bishop.square = end.piece.square;
                        end.piece = bishop;
                    }
                    else // transform pawn to knight
                    {
                        Knight knight = new Knight(this, end.piece.player);
                        knight.chessboard = end.piece.chessboard;
                        knight.player = end.piece.player;
                        knight.square = end.piece.square;
                        end.piece = knight;
                    }
                    promotedPiece = end.piece;
                }
            }
        }
        else if (!end.piece.name.equals("Pawn"))
        {
            twoSquareMovedPawn = null; //erase last saved move (for En passant)
        }
        //}

        if (refresh)
        {
            this.unselect();//unselect square
            repaint();
        }

        if (clearForwardHistory)
        {
            this.moves_history.clearMoveForwardStack();
            this.moves_history.addMove(tempBegin, tempEnd, true, wasCastling, wasEnPassant, promotedPiece);
        }
        else
        {
            this.moves_history.addMove(tempBegin, tempEnd, false, wasCastling, wasEnPassant, promotedPiece);
        }
        
        
        reattachPositions();
    }/*endOf-move()-*/


    public void reattachPositions() {
    	int file = 97;        
        for (int i = 0; i < 8; i++)
        {//create object for each square
            for (int y = 0; y < 8; y++)
            {
            	char cfile = (char)file;
            	IPosition oPosition = getPosition("" + cfile + (8 - y));
            	((Position)oPosition).m_oPiece  = null;
            	this.squares[i][y].m_oPosition = oPosition;
                ((Position)oPosition).m_oPiece = this.squares[i][y].piece;
            }
            file++;
        }//--endOf--create object for each square
        
    }
    
    public boolean redo()
    {
        return redo(true);
    }

    public boolean redo(boolean refresh)
    {
        if ( this.settings.gameType == Settings.gameTypes.local ) //redo only for local game
        {
            Move first = this.moves_history.redo();

            Square from = null;
            Square to = null;

            if (first != null)
            {
                from = first.getFrom();
                to = first.getTo();

                this.move(this.squares[from.pozX][from.pozY], this.squares[to.pozX][to.pozY], true, false);
                if (first.getPromotedPiece() != null)
                {
                    Pawn pawn = (Pawn) this.squares[to.pozX][to.pozY].piece;
                    pawn.square = null;

                    this.squares[to.pozX][to.pozY].piece = first.getPromotedPiece();
                    Piece promoted = this.squares[to.pozX][to.pozY].piece;
                    promoted.square = this.squares[to.pozX][to.pozY];
                }
                return true;
            }
            
        }
        return false;
    }

    public boolean undo()
    {
        return undo(true);
    }

    public synchronized boolean undo(boolean refresh) //undo last move
    {
        Move last = this.moves_history.undo();


        if (last != null && last.getFrom() != null)
        {
            Square begin = last.getFrom();
            Square end = last.getTo();
            try
            {
                Piece moved = last.getMovedPiece();
                this.squares[begin.pozX][begin.pozY].piece = moved;

                moved.square = this.squares[begin.pozX][begin.pozY];

                Piece taken = last.getTakenPiece();
                if (last.getCastlingMove() != castling.none)
                {
                    Piece rook = null;
                    if (last.getCastlingMove() == castling.shortCastling)
                    {
                        rook = this.squares[end.pozX - 1][end.pozY].piece;
                        this.squares[7][begin.pozY].piece = rook;
                        rook.square = this.squares[7][begin.pozY];
                        this.squares[end.pozX - 1][end.pozY].piece = null;
                    }
                    else
                    {
                        rook = this.squares[end.pozX + 1][end.pozY].piece;
                        this.squares[0][begin.pozY].piece = rook;
                        rook.square = this.squares[0][begin.pozY];
                        this.squares[end.pozX + 1][end.pozY].piece = null;
                    }
                    ((King) moved).wasMotion = false;
                    ((Rook) rook).wasMotion = false;
                    this.breakCastling = false;
                }
                else if (moved.name.equals("Rook"))
                {
                    ((Rook) moved).wasMotion = false;
                }
                else if (moved.name.equals("Pawn") && last.wasEnPassant())
                {
                    Pawn pawn = (Pawn) last.getTakenPiece();
                    this.squares[end.pozX][begin.pozY].piece = pawn;
                    pawn.square = this.squares[end.pozX][begin.pozY];

                }
                else if (moved.name.equals("Pawn") && last.getPromotedPiece() != null)
                {
                    Piece promoted = this.squares[end.pozX][end.pozY].piece;
                    promoted.square = null;
                    this.squares[end.pozX][end.pozY].piece = null;
                }

                //check one more move back for en passant
                Move oneMoveEarlier = this.moves_history.getLastMoveFromHistory();
                if (oneMoveEarlier != null && oneMoveEarlier.wasPawnTwoFieldsMove())
                {
                    Piece canBeTakenEnPassant = this.squares[oneMoveEarlier.getTo().pozX][oneMoveEarlier.getTo().pozY].piece;
                    if (canBeTakenEnPassant.name.equals("Pawn"))
                    {
                        this.twoSquareMovedPawn = (Pawn) canBeTakenEnPassant;
                    }
                }

                if (taken != null && !last.wasEnPassant())
                {
                    this.squares[end.pozX][end.pozY].piece = taken;
                    taken.square = this.squares[end.pozX][end.pozY];
                }
                else
                {
                    this.squares[end.pozX][end.pozY].piece = null;
                }

                if (refresh)
                {
                    this.unselect();//unselect square
                    repaint();
                }

            }
            catch (java.lang.ArrayIndexOutOfBoundsException exc)
            {
                return false;
            }
            catch (java.lang.NullPointerException exc)
            {
                return false;
            }

            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Method to draw Chessboard and their elements (pieces etc.)
     * @deprecated 
     */
    void draw()
    {
        this.getGraphics().drawImage(image, this.getTopLeftPoint().x, this.getTopLeftPoint().y, null);//draw an Image of chessboard
        this.drawLabels();
        this.repaint();
    }/*--endOf-draw--*/


    /**
     * Annotations to superclass Game updateing and painting the crossboard
     */
    @Override
    public void update(Graphics g)
    {
        repaint();
    }

    public Point getTopLeftPoint()
    {
        if (this.settings.renderLabels)
        {
            return new Point(this.topLeft.x + this.upDownLabel.getHeight(null), this.topLeft.y + this.upDownLabel.getHeight(null));
        }
        return this.topLeft;
    }

    @Override
    /*public void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Point topLeftPoint = this.getTopLeftPoint();
        if (this.settings.renderLabels)
        {
            if(topLeftPoint.x <= 0 && topLeftPoint.y <= 0) //if renderLabels and (0,0), than draw it! (for first run)
            {
                this.drawLabels();
            }
            g2d.drawImage(this.upDownLabel, 0, 0, null);
            g2d.drawImage(this.upDownLabel, 0, Chessboard.image.getHeight(null) + topLeftPoint.y, null);
            g2d.drawImage(this.LeftRightLabel, 0, 0, null);
            g2d.drawImage(this.LeftRightLabel, Chessboard.image.getHeight(null) + topLeftPoint.x, 0, null);
        }
        g2d.drawImage(image, topLeftPoint.x, topLeftPoint.y, null);//draw an Image of chessboard
        for (int i = 0; i < 8; i++) //drawPiecesOnSquares
        {
            for (int y = 0; y < 8; y++)
            {
                if (this.squares[i][y].piece != null)
                {
                    this.squares[i][y].piece.draw(g, this.squares[i][y].m_oPosition);//draw image of Piece
                }
            }
        }//--endOf--drawPiecesOnSquares
        if ((this.active_x_square != 0) && (this.active_y_square != 0)) //if some square is active
        {
            g2d.drawImage(sel_square, 
                            ((this.active_x_square - 1) * (int) square_height) + topLeftPoint.x,
                            ((this.active_y_square - 1) * (int) square_height) + topLeftPoint.y, null);//draw image of selected square
            Square tmpSquare = this.squares[(int) (this.active_x_square - 1)][(int) (this.active_y_square - 1)];
            if (tmpSquare.piece != null)
            {
                this.moves = this.squares[(int) (this.active_x_square - 1)][(int) (this.active_y_square - 1)].piece.allMoves();
            }

            for (Iterator it = moves.iterator(); moves != null && it.hasNext();)
            {
                Square sq = (Square) it.next();
                g2d.drawImage(able_square, 
                        (sq.pozX * (int) square_height) + topLeftPoint.x,
                        (sq.pozY * (int) square_height) + topLeftPoint.y, null);
            }
        }
    }*//*--endOf-paint--*/
    public void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Point topLeftPoint = this.getTopLeftPoint();
        if (this.settings.renderLabels)
        {
            if(topLeftPoint.x <= 0 && topLeftPoint.y <= 0) //if renderLabels and (0,0), than draw it! (for first run)
            {
                this.drawLabels();
            }
            g2d.drawImage(this.upDownLabel, 0, 0, null);
            g2d.drawImage(this.upDownLabel, 0, Chessboard.image.getHeight(null) + topLeftPoint.y, null);
            g2d.drawImage(this.LeftRightLabel, 0, 0, null);
            g2d.drawImage(this.LeftRightLabel, Chessboard.image.getHeight(null) + topLeftPoint.x, 0, null);
        }
        g2d.drawImage(image, topLeftPoint.x, topLeftPoint.y, null);//draw an Image of chessboard
        for (int i = 0; i < 8; i++) //drawPiecesOnSquares
        {
            for (int y = 0; y < 8; y++)
            {
                if (this.squares[i][y].piece != null)
                {
                    this.squares[i][y].piece.draw(g, this.squares[i][y].m_oPosition);//draw image of Piece
                }
            }
        }//--endOf--drawPiecesOnSquares
        if ((this.active_x_square != 0) && (this.active_y_square != 0)) //if some square is active
        {
            g2d.drawImage(sel_square, 
                            this.activeSquare.m_oPosition.getShape().getTopLeftX(),
                            this.activeSquare.m_oPosition.getShape().getTopLeftY(), null);//draw image of selected square
            Square tmpSquare = this.squares[(int) (this.active_x_square - 1)][(int) (this.active_y_square - 1)];
            if (tmpSquare.piece != null)
            {
            	ArrayList temp = RuleEngine.getTryFindPossibleMove(  this.squares[(int) (this.active_x_square - 1)][(int) (this.active_y_square - 1)].piece, this.squares[(int) (this.active_x_square - 1)][(int) (this.active_y_square - 1)].m_oPosition );
            	//ArrayList temp = this.squares[(int) (this.active_x_square - 1)][(int) (this.active_y_square - 1)].piece.getPossibleMoves();
            	
                for (Iterator it = temp.iterator(); temp != null && it.hasNext();)
                {
                	Position sq = (Position) it.next();
                    g2d.drawImage(able_square, 
                    		sq.getShape().getTopLeftX(),
                    		sq.getShape().getTopLeftY(), null);
                }

                
                //this.moves = this.squares[(int) (this.active_x_square - 1)][(int) (this.active_y_square - 1)].piece.allMoves();
            }

            /*for (Iterator it = moves.iterator(); moves != null && it.hasNext();)
            {
                Square sq = (Square) it.next();
                g2d.drawImage(able_square, 
                		sq.m_oPosition.getShape().getTopLeftX(),
                		sq.m_oPosition.getShape().getTopLeftY(), null);
            }*/
        }
    }/*--endOf-paint--*/


    public void resizeChessboard(int height)
    {
        BufferedImage resized = new BufferedImage(height, height, BufferedImage.TYPE_INT_ARGB_PRE);
        Graphics g = resized.createGraphics();
        g.drawImage(Chessboard.orgImage, 0, 0, height, height, null);
        g.dispose();
        Chessboard.image = resized.getScaledInstance(height, height, 0);
        this.square_height = (float) (height / 8);
        if (this.settings.renderLabels)
        {
            height += 2 * (this.upDownLabel.getHeight(null));
        }
        this.setSize(height, height);

        resized = new BufferedImage((int) square_height, (int) square_height, BufferedImage.TYPE_INT_ARGB_PRE);
        g = resized.createGraphics();
        g.drawImage(Chessboard.org_able_square, 0, 0, (int) square_height, (int) square_height, null);
        g.dispose();
        Chessboard.able_square = resized.getScaledInstance((int) square_height, (int) square_height, 0);

        resized = new BufferedImage((int) square_height, (int) square_height, BufferedImage.TYPE_INT_ARGB_PRE);
        g = resized.createGraphics();
        g.drawImage(Chessboard.org_sel_square, 0, 0, (int) square_height, (int) square_height, null);
        g.dispose();
        Chessboard.sel_square = resized.getScaledInstance((int) square_height, (int) square_height, 0);
        this.drawLabels();
    }

    protected void drawLabels()
    {
        this.drawLabels((int) this.square_height);
    }

    protected final void drawLabels(int square_height)
    {
        //BufferedImage uDL = new BufferedImage(800, 800, BufferedImage.TYPE_3BYTE_BGR);
        int min_label_height = 20;
        int labelHeight = (int) Math.ceil(square_height / 4);
        labelHeight = (labelHeight < min_label_height) ? min_label_height : labelHeight;
        int labelWidth =  (int) Math.ceil(square_height * 8 + (2 * labelHeight)); 
        BufferedImage uDL = new BufferedImage(labelWidth + min_label_height, labelHeight, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D uDL2D = (Graphics2D) uDL.createGraphics();
        uDL2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        uDL2D.setColor(Color.white);

        uDL2D.fillRect(0, 0, labelWidth + min_label_height, labelHeight);
        uDL2D.setColor(Color.black);
        uDL2D.setFont(new Font("Arial", Font.BOLD, 12));
        int addX = (square_height / 2);
        if (this.settings.renderLabels)
        {
            addX += labelHeight;
        }

        String[] letters =
        {
            "a", "b", "c", "d", "e", "f", "g", "h"
        };
        if (!this.settings.upsideDown)
        {
            for (int i = 1; i <= letters.length; i++)
            {
                uDL2D.drawString(letters[i - 1], (square_height * (i - 1)) + addX, 10 + (labelHeight / 3));
            }
        }
        else
        {
            int j = 1;
            for (int i = letters.length; i > 0; i--, j++)
            {
                uDL2D.drawString(letters[i - 1], (square_height * (j - 1)) + addX, 10 + (labelHeight / 3));
            }
        }
        uDL2D.dispose();
        this.upDownLabel = uDL;

        uDL = new BufferedImage(labelHeight, labelWidth + min_label_height, BufferedImage.TYPE_3BYTE_BGR);
        uDL2D = (Graphics2D) uDL.createGraphics();
        uDL2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        uDL2D.setColor(Color.white);
        //uDL2D.fillRect(0, 0, 800, 800);
        uDL2D.fillRect(0, 0, labelHeight, labelWidth + min_label_height);
        uDL2D.setColor(Color.black);
        uDL2D.setFont(new Font("Arial", Font.BOLD, 12));

        if (this.settings.upsideDown)
        {
            for (int i = 1; i <= 8; i++)
            {
                uDL2D.drawString(new Integer(i).toString(), 3 + (labelHeight / 3), (square_height * (i - 1)) + addX);
            }
        }
        else
        {
            int j = 1;
            for (int i = 8; i > 0; i--, j++)
            {
                uDL2D.drawString(new Integer(i).toString(), 3 + (labelHeight / 3), (square_height * (j - 1)) + addX);
            }
        }
        uDL2D.dispose();
        this.LeftRightLabel = uDL;
    }

    public void componentMoved(ComponentEvent e)
    {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void componentShown(ComponentEvent e)
    {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void componentHidden(ComponentEvent e)
    {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
}
