package jchess.gui.view.gamewindow;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import jchess.common.gui.IModel;

/**
 * This class is responsible to draw Move History related controls on GUI.
 * 
 * @author  Sajad Karim
 * @since	7 Dec 2019
 */

public class MoveHistoryView extends AbstractTableModel implements IMoveHistoryView
{
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    private JTable table;

    public MoveHistoryView()
    {
        this.tableModel = new DefaultTableModel();
        this.table = new JTable(this.tableModel);
        this.scrollPane = new JScrollPane(this.table);
        this.scrollPane.setMaximumSize(new Dimension(100, 100));
        this.table.setMinimumSize(new Dimension(100, 100));

        this.tableModel.addColumn("Moves' History");
        this.addTableModelListener(null);
        this.tableModel.addTableModelListener(null);
        this.scrollPane.setAutoscrolls(true);
    }

    /** Method of adding new move
     * @param move String which in is capt player move
     */
    public void addMove(String stMoveString)
    {
    	String[] oRow = {stMoveString};
    	tableModel.addRow(oRow);
    }

    public void removeMove(String stMoveString)
    {
    	String stValueToCompare = (String)tableModel.getValueAt(tableModel.getRowCount()- 1, 0);
    	if( stValueToCompare.equals(stMoveString))
    		tableModel.removeRow(tableModel.getRowCount()- 1);
    }

    @Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawComponents() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setViewData(IModel oData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Component getViewComponent() {
		return null;
	}

	@Override
	public int getRowCount() {
		return tableModel.getRowCount();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return tableModel.getColumnCount();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
        return null;
	}

	@Override
	public JScrollPane getScrollPane() {
		return this.scrollPane;
	}

}