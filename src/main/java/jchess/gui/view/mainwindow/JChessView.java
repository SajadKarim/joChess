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

package jchess.gui.view.mainwindow;

import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import jchess.IMain;
import jchess.gui.view.aboutwindow.*;
import jchess.gui.view.themewindow.*;
import jchess.gui.IGUIManager;
import jchess.util.IAppLogger;
import jchess.util.LogLevel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.*;

import java.awt.Component;
import java.awt.event.*;

/**
 * The application's main frame.
 */

@Singleton
public final class JChessView extends FrameView implements IJChessView, ActionListener, ComponentListener
{
	private IMain m_oMainApplication;
    private IAppLogger m_oLogger; 
    private IGUIManager m_oGUIManager;
	
	@Inject
    public JChessView(IMain oMainApplication, IAppLogger oLogger, IGUIManager oGUIManager) {
        super((SingleFrameApplication)oMainApplication);
                
        m_oLogger = oLogger;
        m_oGUIManager = oGUIManager;
        m_oMainApplication = oMainApplication;
        
        initComponents();
        
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
       	messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });

       	messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
        
    }

    public void actionPerformed(ActionEvent event)
    {
        Object target = event.getSource();
        if (target == newGameItem)
        {
        	m_oGUIManager.showNewGameWindow();
        }
        else if (target == saveGameItem)
        { 
        }
        else if (target == loadGameItem)
        { 
        }
        else if (target == this.themeSettingsMenu)
        {
            try
            {
                ThemeChooseWindow choose = new ThemeChooseWindow(this.getFrame());
                m_oMainApplication.showDialog(choose);
            } 
            catch(Exception exc)
            {
                JOptionPane.showMessageDialog(
                		m_oMainApplication.getAppMainFrame(), 
                    exc.getMessage()
                );
                System.out.println("Something wrong creating window - perhaps themeList is null");                
            }
        }
    }

 
    ///--endOf- don't delete, becouse they're interfaces for MouseEvent
        
    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = m_oMainApplication.getAppMainFrame();
            aboutBox = new JChessAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        m_oMainApplication.showDialog(aboutBox);
    }

    public String showPawnPromotionBox(String color) {
       
        return "";
    }



    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    	m_oLogger.writeLog(LogLevel.ERROR, "Initializing components.", "initComponents", "JChessView");
        
    	this.getFrame().setTitle("N-Players Chess Game!");
    	
    	mainPanel = new javax.swing.JPanel();
        gamesPane = new JChessTabbedPane(m_oGUIManager);
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        newGameItem = new javax.swing.JMenuItem();
        loadGameItem = new javax.swing.JMenuItem();
        saveGameItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        gameMenu = new javax.swing.JMenu();
        moveBackItem = new javax.swing.JMenuItem();
        moveForwardItem = new javax.swing.JMenuItem();
        rewindToBegin = new javax.swing.JMenuItem();
        rewindToEnd = new javax.swing.JMenuItem();
        optionsMenu = new javax.swing.JMenu();
        themeSettingsMenu = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setMaximumSize(new java.awt.Dimension(800, 600));
        mainPanel.setMinimumSize(new java.awt.Dimension(800, 600));
        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setPreferredSize(new java.awt.Dimension(800, 600));

        gamesPane.setName("gamesPane"); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(gamesPane, javax.swing.GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(gamesPane, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE))
        );

        menuBar.setName("menuBar"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(jchess.Main.class).getContext().getResourceMap(JChessView.class);
        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        newGameItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        newGameItem.setText(resourceMap.getString("newGameItem.text")); // NOI18N
        newGameItem.setName("newGameItem"); // NOI18N
        fileMenu.add(newGameItem);
        newGameItem.addActionListener(this);

        loadGameItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        loadGameItem.setText(resourceMap.getString("loadGameItem.text")); // NOI18N
        loadGameItem.setName("loadGameItem"); // NOI18N
        fileMenu.add(loadGameItem);
        loadGameItem.addActionListener(this);

        saveGameItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveGameItem.setText(resourceMap.getString("saveGameItem.text")); // NOI18N
        saveGameItem.setName("saveGameItem"); // NOI18N
        fileMenu.add(saveGameItem);
        saveGameItem.addActionListener(this);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(jchess.Main.class).getContext().getActionMap(JChessView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        gameMenu.setText(resourceMap.getString("gameMenu.text")); // NOI18N
        gameMenu.setName("gameMenu"); // NOI18N

        moveBackItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        moveBackItem.setText(resourceMap.getString("moveBackItem.text")); // NOI18N
        moveBackItem.setName("moveBackItem"); // NOI18N
        moveBackItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                moveBackItemMouseClicked(evt);
            }
        });
        moveBackItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveBackItemActionPerformed(evt);
            }
        });
        gameMenu.add(moveBackItem);

        moveForwardItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        moveForwardItem.setText(resourceMap.getString("moveForwardItem.text")); // NOI18N
        moveForwardItem.setName("moveForwardItem"); // NOI18N
        moveForwardItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                moveForwardItemMouseClicked(evt);
            }
        });
        moveForwardItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveForwardItemActionPerformed(evt);
            }
        });
        gameMenu.add(moveForwardItem);

        rewindToBegin.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        rewindToBegin.setText(resourceMap.getString("rewindToBegin.text")); // NOI18N
        rewindToBegin.setName("rewindToBegin"); // NOI18N
        rewindToBegin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rewindToBeginActionPerformed(evt);
            }
        });
        gameMenu.add(rewindToBegin);

        rewindToEnd.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        rewindToEnd.setText(resourceMap.getString("rewindToEnd.text")); // NOI18N
        rewindToEnd.setName("rewindToEnd"); // NOI18N
        rewindToEnd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rewindToEndActionPerformed(evt);
            }
        });
        gameMenu.add(rewindToEnd);

        menuBar.add(gameMenu);

        optionsMenu.setText(resourceMap.getString("optionsMenu.text")); // NOI18N
        optionsMenu.setName("optionsMenu"); // NOI18N

        themeSettingsMenu.setText(resourceMap.getString("themeSettingsMenu.text")); // NOI18N
        themeSettingsMenu.setName("themeSettingsMenu"); // NOI18N
        optionsMenu.add(themeSettingsMenu);
        themeSettingsMenu.addActionListener(this);

        menuBar.add(optionsMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 616, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void moveBackItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_moveBackItemActionPerformed
    {    
    	m_oGUIManager.onPlayerRequestForUndoBoardActivity(gamesPane.getSelectedComponent());
    }//GEN-LAST:event_moveBackItemActionPerformed

    private void moveBackItemMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_moveBackItemMouseClicked
    {//GEN-HEADEREND:event_moveBackItemMouseClicked
        // TODO add your handling code here:
       
    }//GEN-LAST:event_moveBackItemMouseClicked

    private void moveForwardItemMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_moveForwardItemMouseClicked
    {//GEN-HEADEREND:event_moveForwardItemMouseClicked
        // TODO add your handling code here:
             
    }//GEN-LAST:event_moveForwardItemMouseClicked

    private void moveForwardItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_moveForwardItemActionPerformed
    {    
    	m_oGUIManager.onPlayerRequestForRedoBoardActivity(gamesPane.getSelectedComponent());
    }//GEN-LAST:event_moveForwardItemActionPerformed

    private void rewindToBeginActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_rewindToBeginActionPerformed
    {//GEN-HEADEREND:event_rewindToBeginActionPerformed
     
    }//GEN-LAST:event_rewindToBeginActionPerformed

    private void rewindToEndActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_rewindToEndActionPerformed
    {//GEN-HEADEREND:event_rewindToEndActionPerformed
     
    }//GEN-LAST:event_rewindToEndActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu gameMenu;
    private javax.swing.JTabbedPane gamesPane;
    private javax.swing.JMenuItem loadGameItem;
    public javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem moveBackItem;
    private javax.swing.JMenuItem moveForwardItem;
    private javax.swing.JMenuItem newGameItem;
    private javax.swing.JMenu optionsMenu;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JMenuItem rewindToBegin;
    private javax.swing.JMenuItem rewindToEnd;
    private javax.swing.JMenuItem saveGameItem;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JMenuItem themeSettingsMenu;
    // End of variables declaration//GEN-END:variables
    //private JTabbedPaneWithIcon gamesPane;
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;
    public  JDialog  newGameFrame;

    public void componentResized(ComponentEvent e) {
        System.out.println("jchessView resized!!;");
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public int getNumberOfOpenedTabs()
    {
        return this.gamesPane.getTabCount();
    }

    public void componentMoved(ComponentEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void componentShown(ComponentEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void componentHidden(ComponentEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

	public void addTab(Component oComponent, String stName) {
		this.gamesPane.addTab(stName, oComponent);
		this.gamesPane.setSelectedComponent(oComponent);
	}
	
	/**
	 * Remove the current working Tab
	 */
	public void removeCurrentTab()
	{
		this.gamesPane.removeTabAt(this.gamesPane.getSelectedIndex());
	}
}
