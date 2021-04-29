/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import etude.Etude;
import java.awt.BorderLayout;
import java.awt.Color;
import manager.EtudeSession;

/**
 *
 * @author SylvainPro
 */
public abstract class Vue extends JPanel implements ActionListener {
    
    private static final Dimension DIMENSION_MAIN_MIN = new Dimension(800,400); // width - height
        
    protected final Etude etude;
    
    protected JLabel title;
    protected JPanel main;
    protected JPanel actions;
        
    public Vue(EtudeSession session) {
        
        this.etude = session.getEtude();
        
        this.title = new JLabel();
        
        this.main = new JPanel();
             main.setLayout(new BorderLayout());
             main.setMinimumSize(DIMENSION_MAIN_MIN);
        
        setBackground(Color.WHITE);
    }
    
    public abstract Vue action(ActionEvent e,Etude etude);
    
    protected void build() {
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createSequentialGroup()
            .addGap(10)
            .addGroup(layout.createParallelGroup()
                .addComponent(title)
                .addComponent(main))
            .addGap(10));
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addComponent(title)
            .addGap(5)    
            .addComponent(main));
    }
    
    public abstract void updateVue();
    
}
