/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import etude.Etude;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import manager.EtudeSession;
import tables.TableFootprint;
import tables.TableUniteLegale;

/**
 *
 * @author SylvainPro
 */

public class VueUniteLegale extends Vue {
    
    private JTable tableUniteLegale;
    private TableFootprint tableFootprint;
        
    /* ----- CONSTRUCTOR ----- */
    
    public VueUniteLegale (EtudeSession session) {
        super(session);
        title.setText("Informations - Unité légale");
        buildMain();
        build();
    }
    
    private void buildMain() {
        
        main.setLayout(new BoxLayout(main,BoxLayout.PAGE_AXIS));
        main.setAlignmentX(LEFT_ALIGNMENT);
        main.setAlignmentY(TOP_ALIGNMENT);
        
        tableUniteLegale = new TableUniteLegale(etude);
        tableUniteLegale.setBorder(BorderFactory.createLineBorder(new Color(100,100,100), 1));
        tableUniteLegale.setPreferredScrollableViewportSize(tableUniteLegale.getPreferredSize());
        JScrollPane scrollPaneUniteLegale = new JScrollPane(tableUniteLegale);
        scrollPaneUniteLegale.getViewport().setBackground(Color.WHITE);
        scrollPaneUniteLegale.setBorder(BorderFactory.createEmptyBorder());
        main.add(scrollPaneUniteLegale);
        
        tableFootprint = new TableFootprint(etude);
        tableFootprint.setBorder(BorderFactory.createLineBorder(new Color(100,100,100), 1));
        tableFootprint.setPreferredScrollableViewportSize(tableFootprint.getPreferredSize());
        JScrollPane scrollPaneFootprint = new JScrollPane(tableFootprint);
        scrollPaneFootprint.getViewport().setBackground(Color.WHITE);
        scrollPaneFootprint.setBorder(BorderFactory.createEmptyBorder());
        
        main.add(scrollPaneFootprint);
        
    }
    
    @Override
    public Vue action(ActionEvent e,Etude etude) {
        return null;
    }
    
    @Override
    public void updateVue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
