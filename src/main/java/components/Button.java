/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 *
 * @author SylvainPro
 */
public class Button extends JButton {
        
    private static final Color GREY_LSN = new Color(100,100,100);
    private static final Color LIGHTGREY_LSN = new Color(150,150,150);
    
    private Boolean isNegative;
    
    public Button(String libelle) {
        this.setText(libelle);
        
        this.setMinimumSize(new Dimension(200,25));
        this.setPreferredSize(new Dimension(200,25));
        this.setMaximumSize(new Dimension(200,25));
        
        this.setBackground(GREY_LSN);
        this.setBorderPainted(false);
        this.setForeground(Color.white);
        
        this.setFocusPainted(false);
        
        super.setContentAreaFilled(false);
        
        isNegative = false;
    }
    
    public Button(String libelle, ActionListener listener) {
        this.setText(libelle);
        
        this.setMinimumSize(new Dimension(250,25));
        this.setPreferredSize(new Dimension(250,25));
        this.setMaximumSize(new Dimension(250,25));
        
        this.setBackground(GREY_LSN);
        this.setBorderPainted(false);
        this.setForeground(Color.white);
        
        this.setFocusPainted(false);
        
        super.setContentAreaFilled(false);
        
        addActionListener(listener);
        
        isNegative = false;
    }
    
    public Button(String libelle, int width, int height) {
        this.setText(libelle);
        
        this.setMinimumSize(new Dimension(width,height));
        this.setPreferredSize(new Dimension(width,height));
        this.setMaximumSize(new Dimension(width,height));
        
        this.setBackground(GREY_LSN);
        this.setBorderPainted(false);
        this.setForeground(Color.white);
        
        this.setFocusPainted(false);
        
        super.setContentAreaFilled(false);
    }
    
    public Button(String libelle, int width, int height, ActionListener listener) {
        this.setText(libelle);
        
        this.setMinimumSize(new Dimension(width,height));
        this.setPreferredSize(new Dimension(width,height));
        this.setMaximumSize(new Dimension(width,height));
        
        this.setBackground(GREY_LSN);
        this.setBorderPainted(false);
        this.setForeground(Color.white);
        
        this.setFocusPainted(false);
        
        super.setContentAreaFilled(false);
        
        addActionListener(listener);
    }
    
    public void toNegative() {
        setBackground(Color.WHITE);
        setForeground(GREY_LSN);
        setBorder(BorderFactory.createLineBorder(GREY_LSN, 5));
        isNegative = true;
    }
    public void toPositive() {
        setBackground(GREY_LSN);
        setForeground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        isNegative = false;
    }
    public void switchRender() {
        if (isNegative) {
            toPositive();
        } else {
            toNegative();
        }
    }
    
    public void setListener(ActionListener listener) {
        this.addActionListener(listener);
    }
    
    @Override
        protected void paintComponent(Graphics g) {
            
            if (getModel().isPressed()) {
                g.setColor(LIGHTGREY_LSN);
            } else if (getModel().isRollover()) {
                g.setColor(LIGHTGREY_LSN);
            } else {
                g.setColor(getBackground());
            }
            g.fillRect(0, 0, getWidth(), getHeight());
            super.paintComponent(g);
        }
        
      
    
}
