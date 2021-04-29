/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import etude.Etude;
import components.Menu;
import java.awt.Color;
import views.VueUniteLegale;
import views.Vue;
import java.awt.Container;
import javax.swing.GroupLayout;
import javax.swing.JFrame;

/**
 *
 * @author SylvainPro
 */
public class EtudeSession extends JFrame {
    
    private Etude etude;
    
    private final Menu menu = new Menu(this);
    private Vue vue;
    
    public EtudeSession(Etude etude) {
        
        this.etude = etude;
        
        this.vue = new VueUniteLegale(this);
        
        Container container = this.getContentPane();
        GroupLayout layout = new GroupLayout(container);
        container.setLayout(layout);
        layout.setHorizontalGroup(layout.createSequentialGroup()
            .addGap(10)
            .addComponent(menu)
            .addGap(5)
            .addComponent(vue)
            .addGap(10));
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGap(10)
            .addGroup(layout.createParallelGroup()
                .addComponent(menu)
                .addComponent(vue))
            .addGap(10));
        
        container.setBackground(Color.white);
        
        this.setTitle("Etude "+etude.getLibelle());
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    public void updateVue(Vue vue) {
        this.vue = vue;
        Container container = this.getContentPane();
        container.removeAll();
        GroupLayout layout = (GroupLayout) container.getLayout();
        layout.setHorizontalGroup(layout.createSequentialGroup()
            .addGap(10)
            .addComponent(menu)
            .addGap(5)
            .addComponent(vue)
            .addGap(10));
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGap(10)
            .addGroup(layout.createParallelGroup()
                .addComponent(menu)
                .addComponent(vue))
            .addGap(10));
        container.revalidate();
        container.repaint();
    }
        
    public Etude getEtude() {
        return etude;
    }
    
}
