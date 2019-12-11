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
 * Author: Mateusz Sławomir Lach ( matlak, msl )
 */
package jchess;

import jchess.Moves.castling;

class Move
{

    protected Square from = null;
    protected Square to = null;
    protected Piece movedPiece = null;
    protected Piece takenPiece = null;
    protected Piece promotedTo = null;
    protected boolean wasEnPassant = false;
    protected castling castlingMove = castling.none;
    protected boolean wasPawnTwoFieldsMove = false;
    /**
     * Move of the piece
     * @param from is where the piece stand
     * @param to is where the piece will arrive
     * @param movedPiece is what piece that moved
     * @param takenPiece is what piece will be taken
     * @param castlingMove to check if this is a castling move or not
     * @param wasEnPassant to check if this is a en passant or not
     * @param promotedPiece the piece that pawn get promoted into
     */
    Move(Square from, Square to, Piece movedPiece, Piece takenPiece, castling castlingMove, boolean wasEnPassant, Piece promotedPiece)
    {
        this.from = from;
        this.to = to;

        this.movedPiece = movedPiece;
        this.takenPiece = takenPiece;

        this.castlingMove = castlingMove;
        this.wasEnPassant = wasEnPassant;

        if (movedPiece.name.equals("Pawn") && Math.abs(to.pozY - from.pozY) == 2)
        {
            this.wasPawnTwoFieldsMove = true;
        }
        else if (movedPiece.name.equals("Pawn") && to.pozY == Chessboard.bottom || to.pozY == Chessboard.top && promotedPiece != null)
        {
            this.promotedTo = promotedPiece;
        }
    }
    /**
     * To get the Square where the piece start to move
     * @return return the square that the piece stand
     */
    public Square getFrom()
    {
        return this.from;
    }
    /**
     * To get the Square where the piece arrive
     * @return return the square the piece get to
     */
    public Square getTo()
    {
        return this.to;
    }
    /**
     * To get the recent moved piece
     * @return return the recent moved piece
     */
    public Piece getMovedPiece()
    {
        return this.movedPiece;
    }
    /**
     * To get the piece that was taken
     * @return return the taken piece
     */
    public Piece getTakenPiece()
    {
        return this.takenPiece;
    }
    /**
     * To check if the move was En Passant
     * the move that capture the special pawn that occur immediately after the pawn makes a move of two squares from it starting place.
     * It could be captured by an enemy pawn had advanced in only one square
     * @return return true if this was En Passant and False if it isn't
     */
    public boolean wasEnPassant()
    {
        return this.wasEnPassant;
    }
    /**
     * To check if the Pawn was move 2 square
     * @return return True if pawn moved 2 square and False if it isn't
     */
    public boolean wasPawnTwoFieldsMove()
    {
        return this.wasPawnTwoFieldsMove;
    }
    /**
     * To get the castling move
     * @return return the castling move
     */
    public castling getCastlingMove()
    {
        return this.castlingMove;
    }
    /**
     * To get the the piece that was promoted
     * @return return the piece that get promoted into
     */
    public Piece getPromotedPiece()
    {
        return this.promotedTo;
    }
}
