package jchess.view.gamewindow;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import jchess.Settings;

/**
 * This class is responsible to draw Move History related controls on GUI.
 * 
 * @author  Sajad Karim
 * @since	7 Dec 2019
 */

public class MoveHistoryView extends AbstractTableModel implements IMoveHistoryView
{
    private ArrayList<String> move = new ArrayList<String>();
    private int columnsNum = 3;
    private int rowsNum = 0;
    private String[] names = new String[]
    {
        Settings.lang("white"), Settings.lang("black")
    };
    private MyDefaultTableModel tableModel;
    private JScrollPane scrollPane;
    private JTable table;
    private boolean enterBlack = false;
    //private Game game;

    enum castling
    {
        none, shortCastling, longCastling
    }

    public MoveHistoryView()
    {
        this.tableModel = new MyDefaultTableModel();
        this.table = new JTable(this.tableModel);
        this.scrollPane = new JScrollPane(this.table);
        this.scrollPane.setMaximumSize(new Dimension(100, 100));
        this.table.setMinimumSize(new Dimension(100, 100));
       //this.game = game;

        this.tableModel.addColumn("Moves History");
        //this.tableModel.addColumn(this.names[1]);
        this.addTableModelListener(null);
        this.tableModel.addTableModelListener(null);
        this.scrollPane.setAutoscrolls(true);
    }

    public void draw()
    {
    }

    @Override
    public String getValueAt(int x, int y)
    {
        return this.move.get((y * 2) - 1 + (x - 1));
    }

    @Override
    public int getRowCount()
    {
        return this.rowsNum;
    }

    @Override
    public int getColumnCount()
    {
        return this.columnsNum;
    }

    protected void addRow()
    {
        this.tableModel.addRow(new String[2]);
    }

    protected void addCastling(String move)
    {
        this.move.remove(this.move.size() - 1);//remove last element (move of Rook)
        if (!this.enterBlack)
        {
            this.tableModel.setValueAt(move, this.tableModel.getRowCount() - 1, 1);//replace last value
        }
        else
        {
            this.tableModel.setValueAt(move, this.tableModel.getRowCount() - 1, 0);//replace last value
        }
        this.move.add(move);//add new move (O-O or O-O-O)
    }

    @Override
    public boolean isCellEditable(int a, int b)
    {
        return false;
    }

    /** Method of adding new moves to the table
     * @param str String which in is saved player move
     */
    protected void addMove2Table(String str)
    {
        try
        {
            if (!this.enterBlack)
            {
                this.addRow();
                this.rowsNum = this.tableModel.getRowCount() - 1;
                this.tableModel.setValueAt(str, rowsNum, 0);
            }
            else
            {
                this.tableModel.setValueAt(str, rowsNum, 1);
                this.rowsNum = this.tableModel.getRowCount() - 1;
            }
            this.enterBlack = !this.enterBlack;
            this.table.scrollRectToVisible(table.getCellRect(table.getRowCount() - 1, 0, true));//scroll to down

        }
        catch (java.lang.ArrayIndexOutOfBoundsException exc)
        {
            if (this.rowsNum > 0)
            {
                this.rowsNum--;
                addMove2Table(str);
            }
        }
    }

    /** Method of adding new move
     * @param move String which in is capt player move
     */
    public void addMove(String move)
    {
       

    }

    public void clearMoveForwardStack()
    {
        //this.moveForwardStack.clear();
    }

    public JScrollPane getScrollPane()
    {
        return this.scrollPane;
    }

    public ArrayList<String> getMoves()
    {
        return this.move;
    }

}
/*
 * Overriding DefaultTableModel and  isCellEditable method
 * (history cannot be edited by player)
 */

class MyDefaultTableModel extends DefaultTableModel
{

    MyDefaultTableModel()
    {
        super();
    }

    @Override
    public boolean isCellEditable(int a, int b)
    {
        return false;
    }
}