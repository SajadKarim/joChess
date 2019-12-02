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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.Graphics;
import java.awt.Image;
import jchess.common.enumerator.*;
import jchess.common.*;
/**
 * Class to represent a chess pawn knight
 */
public class Knight extends Piece
{

    public static short value = 3;
    protected static final Image imageWhite = GUI.loadImage("Knight-W.png");
    protected static final Image imageBlack = GUI.loadImage("Knight-B.png");

    public Knight(Chessboard chessboard, Player player)
    {
        super(chessboard, player);//call initializer of super type: Piece
        //this.setImages("Knight-W.png", "Knight-B.png");
        this.symbol = "N";
        this.setImage();
    }

    @Override
    void setImage()
    {
        if (this.player.color == this.player.color.black)
        {
            image = imageBlack;
        }
        else
        {
            image = imageWhite;
        }
        orgImage = image;
    }

    void populateRules() {
        Map<String, Rule> _rules1 = new HashMap<String, Rule>();
        _rules1.put("1", new Rule("1", RuleType.MOVE_AND_CAPTURE, Direction.EDGE, Manoeuvre.FILE_AND_RANK, 1, Family.IGNORE, File.SAME, Rank.FORWARD, false, null));
        _rules1.put("2", new Rule("2", RuleType.MOVE_AND_CAPTURE, Direction.EDGE, Manoeuvre.FILE_AND_RANK, 1, Family.IGNORE, File.FORWARD, Rank.SAME, false, null));
        
        Rule rule1 = new Rule("", RuleType.MOVE_TRANSIENT, Direction.VERTEX, Manoeuvre.BLINKER, 1, Family.SAME, File.IGNORE, Rank.IGNORE, true, _rules1);
        		 	
        List<Rule> _rules2 = new ArrayList<Rule>();
        //_rules2.add(new Rule(RULE_TYPE.MOVE_AND_CAPTURE, DIRECTION.EDGE, 1, FAMILY.ANY, FILE.SAME, RANK.FORWARD, false, null));
        //_rules2.add(new Rule(RULE_TYPE.MOVE_AND_CAPTURE, DIRECTION.EDGE, 1, FAMILY.ANY, FILE.BACKWARD, RANK.SAME, false, null));
        
       //IRule rule2 = new Rule(RULE_TYPE.MOVE_TRANSIENT, DIRECTION.VERTEX, 1, FAMILY.ANY, FILE.BACKWARD, RANK.FORWARD, true, _rules2);
        
        List<Rule> _rules3 = new ArrayList<Rule>();
        //_rules3.add(new Rule(RULE_TYPE.MOVE_AND_CAPTURE, DIRECTION.EDGE, 1, FAMILY.ANY, FILE.SAME, RANK.BACKWARD, false, null));
        //_rules3.add(new Rule(RULE_TYPE.MOVE_AND_CAPTURE, DIRECTION.EDGE, 1, FAMILY.ANY, FILE.BACKWARD, RANK.SAME, false, null));
        
        //IRule rule3 = new Rule(RULE_TYPE.MOVE_TRANSIENT, DIRECTION.VERTEX, 1, FAMILY.ANY, FILE.BACKWARD, RANK.BACKWARD, true, _rules3);
        
        m_lstRules.add(new RuleAgent(rule1));
       // m_lstRules.add(rule2);
       // m_lstRules.add(rule3);

        //List<IRule> _rules = new ArrayList<IRule>();
        //_rules.add(new Rule(RULE_TYPE.MOVE_AND_CAPTURE, DIRECTION.EDGE, MANOEUVRE.FILE_AND_RANK, 1, FAMILY.ANY, FILE.SAME, RANK.FORWARD, true, null));
        //_rules.add(new Rule(RULE_TYPE.MOVE_AND_CAPTURE, DIRECTION.EDGE, MANOEUVRE.FILE_AND_RANK, 1, FAMILY.ANY, FILE.FORWARD, RANK.SAME, true, null));

        //m_lstRules.add( new Rule(RULE_TYPE.MOVE_TRANSIENT, DIRECTION.VERTEX, MANOEUVRE.BLINKER, 1, FAMILY.SAME, FILE.IGNORE, RANK.IGNORE, true, _rules));
        //m_lstRules.add( );

    }

    /**
     *  Annotation to superclass Piece changing pawns location
     * @return  ArrayList with new possition of pawn
     */
    @Override
    public ArrayList allMoves()
    {
        ArrayList list = new ArrayList();

        // knight all moves
        //  _______________ Y:
        // |_|_|_|_|_|_|_|_|7
        // |_|_|_|_|_|_|_|_|6
        // |_|_|2|_|3|_|_|_|5
        // |_|1|_|_|_|4|_|_|4
        // |_|_|_|K|_|_|_|_|3
        // |_|8|_|_|_|5|_|_|2
        // |_|_|7|_|6|_|_|_|1
        // |_|_|_|_|_|_|_|_|0
        //X:0 1 2 3 4 5 6 7
        //

        int newX, newY;

        //1
        newX = this.square.pozX - 2;
        newY = this.square.pozY + 1;

        if (!isout(newX, newY) && checkPiece(newX, newY))
        {
            if (this.player.color == Player.colors.white) //white
            {
                if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
            else //or black
            {
                if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
        }

        //2
        newX = this.square.pozX - 1;
        newY = this.square.pozY + 2;

        if (!isout(newX, newY) && checkPiece(newX, newY))
        {
            if (this.player.color == Player.colors.white) //white
            {
                if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
            else //or black
            {
                if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
        }

        //3
        newX = this.square.pozX + 1;
        newY = this.square.pozY + 2;

        if (!isout(newX, newY) && checkPiece(newX, newY))
        {
            if (this.player.color == Player.colors.white) //white
            {
                if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
            else //or black
            {
                if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
        }

        //4
        newX = this.square.pozX + 2;
        newY = this.square.pozY + 1;

        if (!isout(newX, newY) && checkPiece(newX, newY))
        {
            if (this.player.color == Player.colors.white) //white
            {
                if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
            else //or black
            {
                if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
        }

        //5
        newX = this.square.pozX + 2;
        newY = this.square.pozY - 1;

        if (!isout(newX, newY) && checkPiece(newX, newY))
        {
            if (this.player.color == Player.colors.white) //white
            {
                if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
            else //or black
            {
                if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
        }

        //6
        newX = this.square.pozX + 1;
        newY = this.square.pozY - 2;

        if (!isout(newX, newY) && checkPiece(newX, newY))
        {
            if (this.player.color == Player.colors.white) //white
            {
                if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
            else //or black
            {
                if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
        }

        //7
        newX = this.square.pozX - 1;
        newY = this.square.pozY - 2;

        if (!isout(newX, newY) && checkPiece(newX, newY))
        {
            if (this.player.color == Player.colors.white) //white
            {
                if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
            else //or black
            {
                if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
        }

        //8
        newX = this.square.pozX - 2;
        newY = this.square.pozY - 1;

        if (!isout(newX, newY) && checkPiece(newX, newY))
        {
            if (this.player.color == Player.colors.white) //white
            {
                if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
            else //or black
            {
                if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
        }

        return list;
    }
}
