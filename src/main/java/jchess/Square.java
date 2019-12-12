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
import jchess.common.*;
import jchess.gamelogic.PositionAgent;
/**
 * Class to represent a chessboard square
 */
public class Square
{

    int pozX; // 0-7, becouse 8 squares for row/column
    int pozY; // 0-7, becouse 8 squares for row/column
    public jchess.gamelogic.PieceAgent piece = null;//object Piece on square (and extending Piecie)
    public PositionAgent m_oPosition = null;
    
    Square(int pozX, int pozY, jchess.gamelogic.PieceAgent piece, PositionAgent oPosition)
    {
        this.pozX = pozX;
        this.pozY = pozY;
        this.piece = piece;
        this.m_oPosition = oPosition;
    }/*--endOf-Square--*/


    Square(Square square)
    {
        this.pozX = square.pozX;
        this.pozY = square.pozY;
        this.piece = square.piece;
    }

    public Square clone(Square square)
    {
        return new Square(square);
    }

    void setPiece(jchess.gamelogic.PieceAgent piece)
    {
    	this.m_oPosition.setPiece(piece);
        this.piece = null;
        //this.piece.square = this;
    }
}
