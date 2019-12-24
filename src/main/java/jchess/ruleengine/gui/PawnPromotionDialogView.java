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
package jchess.ruleengine.gui;

import javax.swing.*;

import jchess.common.IPieceAgent;
import jchess.common.gui.DialogResult;
import jchess.common.gui.IViewClosedListener;
import jchess.common.gui.IModel;
import jchess.common.gui.IView;
import jchess.gui.view.gamewindow.IGameViewListener;
import jchess.util.GUI;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;

/** Class responsible for promotion of a pawn.
 * When pawn reach the end of the chessboard it can be change to rook,
 * bishop, queen or knight. For what pawn is promoted decideds player.
 * @param parent Information about the current piece
 * @param color The player color
 */
public class PawnPromotionDialogView extends JDialog implements IPawnPromotionDialogView , ActionListener
{
    GridBagLayout gbl;
    public String result;
    GridBagConstraints gbc;

    List<IViewClosedListener> m_lstListener;
    
    public PawnPromotionDialogView(Frame parent, List<IPieceAgent> lstPieces)
    {
        super(parent);
        
        m_lstListener = new ArrayList<IViewClosedListener>();
        
        this.setTitle("Choose piece");
        this.setMinimumSize(new Dimension(520, 130));
        this.setSize(new Dimension(520, 130));
        this.setMaximumSize(new Dimension(520, 130));
        this.setResizable(false);
        this.setLayout(new GridLayout(1, 4));
        //this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.gbl = new GridBagLayout();
        this.gbc = new GridBagConstraints();
        Iterator<IPieceAgent> it = lstPieces.iterator(); 
        while( it.hasNext()) {
        	IPieceAgent oPiece = it.next();
        	JButton btn = new JButton(oPiece.getName(), new ImageIcon(oPiece.getImage()));
        	btn.addActionListener(this);

        	this.add(btn);
        }
        this.setModal(true);
    }

    /** Method wich is changing a pawn into queen, rook, bishop or knight
     * @param arg0 Capt information about performed action
     */
    public void actionPerformed(ActionEvent arg0)
    {
        for (final IViewClosedListener oListener : m_lstListener) {
            oListener.onViewClosed(DialogResult.OK, ((JButton)arg0.getSource()).getText());
        }

        this.setVisible(false);
    }

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawComponents() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setViewData(IModel oData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addListener(IViewClosedListener oListener) {
		m_lstListener.add(oListener);
	}
	
	public JDialog getJDialog() {
		return this;
	}

	public void setModal(Boolean bValue) {
		this.setModal(bValue);
	}

	@Override
	public Component getViewComponent() {
		return this;
	}
}