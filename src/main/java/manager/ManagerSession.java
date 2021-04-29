/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import etude.Etude;
import components.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import renderers.DefaultCellRenderer;
import tables.Table;

/**
 *
 * @author SylvainPro
 */
public class ManagerSession extends JFrame implements ActionListener {
    
    private static final String[] enteteTableEtudes = {"Siren","Unité Légale"};
    private static final Integer[] widthColums = {200,400};
    private static final Integer TABLE_WIDTH = 600;
    private static final Integer TABLE_HEIGHT = 500;
        
    /* ----- FRAME ELEMENTS ----- */
    
    private JPanel panelEtudes;
    private JTable tableEtudes;
    private JPanel panelActions;
    private final Button buttonOpen = new Button("Ouvrir");
    private final Button buttonDelete = new Button("Supprimer");
    private final Button buttonNew  = new Button("Nouvelle Etude");
    
    private ArrayList<Etude> etudes;
    
    /* ----- CONSTRUCTOR ----- */
    
    public ManagerSession() {
        
        loadEtudes();
        buildPanelEtudes();
        buildPanelActions();
        
        Container container = this.getContentPane();
        GroupLayout layout = new GroupLayout(container);
        container.setLayout(layout);
        layout.setHorizontalGroup(layout.createSequentialGroup()
            .addGap(10)
            .addComponent(panelEtudes)
            .addGap(5)
            .addComponent(panelActions)
            .addGap(10));
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGap(10)
            .addGroup(layout.createParallelGroup()
                .addComponent(panelEtudes)
                .addComponent(panelActions))
            .addGap(10));
        
        this.getContentPane().setBackground(Color.WHITE);
        
        this.setTitle("METRIZ by La Société Nouvelle - Gestionnaire d'Etudes");
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    private void loadEtudes () {
        etudes = new ArrayList<>();
        File folder = new File("src/main/data");
        File[] listFiles = folder.listFiles();
        for (File file : listFiles) {
            if (file.getName().matches("etude_([0-9]+).json")) {
                try {
                    Etude etude = Etude.open(file.getPath());
                    etudes.add(etude);
                } catch (IOException | ClassNotFoundException ex) {
                    Logger.getLogger(ManagerSession.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    private void buildPanelEtudes () {
        this.panelEtudes = new JPanel();
        
        Object[][] table = new Object[etudes.size()][2];
        int indiceLigne = 0;
        for (Etude etude : etudes) {
            table[indiceLigne][0] = etude;
            table[indiceLigne][1] = etude.getUniteLegale().getDenomination();
            indiceLigne++;
        }
        
        tableEtudes = new Table(new DefaultTableModel(table,enteteTableEtudes));
        
        tableEtudes.getColumnModel().getColumn(0).setCellRenderer(new DefaultCellRenderer());
        tableEtudes.getColumnModel().getColumn(1).setCellRenderer(new DefaultCellRenderer());
        
        tableEtudes.getColumnModel().getColumn(0).setPreferredWidth(widthColums[0]);
        tableEtudes.getColumnModel().getColumn(1).setPreferredWidth(widthColums[1]);
        
        tableEtudes.setBorder(BorderFactory.createLineBorder(new Color(100,100,100), 1));
        tableEtudes.setPreferredScrollableViewportSize(new Dimension(TABLE_WIDTH,TABLE_HEIGHT));
        
        JScrollPane scrollPane = new JScrollPane(tableEtudes);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(100,100,100), 1));
        
        GroupLayout layout = new GroupLayout(panelEtudes);
        panelEtudes.setLayout(layout);
        layout.setHorizontalGroup(layout.createSequentialGroup()
            .addComponent(scrollPane));
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addComponent(scrollPane));
    }
    
    private void buildPanelActions () {
        this.panelActions = new JPanel();
        
        buttonOpen.addActionListener(this);
        buttonDelete.addActionListener(this);
        buttonNew.addActionListener(this);
        
        GroupLayout layout = new GroupLayout(panelActions);
        panelActions.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup()
            .addComponent(buttonOpen)
            .addComponent(buttonDelete)
            .addComponent(buttonNew));
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addComponent(buttonOpen)
            .addGap(5)
            .addComponent(buttonDelete)
            .addGap(5)
            .addComponent(buttonNew));
    }
    
    private void updateView() {
        buildPanelEtudes();
        Container container = this.getContentPane();
        container.removeAll();
        GroupLayout layout = new GroupLayout(container);
        container.setLayout(layout);
        layout.setHorizontalGroup(layout.createSequentialGroup()
            .addGap(10)
            .addComponent(panelEtudes)
            .addGap(5)
            .addComponent(panelActions)
            .addGap(10));
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGap(10)
            .addGroup(layout.createParallelGroup()
                .addComponent(panelEtudes)
                .addComponent(panelActions))
            .addGap(10));
        container.revalidate();
        container.repaint();
    }
    
    /* ----- LISTENERS ----- */

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==this.buttonOpen) {
            Etude selectedEtude = getSelectEtude();
            if (selectedEtude!=null) {
                EtudeSession session = new EtudeSession(selectedEtude);
            } else {
                JOptionPane.showMessageDialog(null,"Aucune étude sélectionnée");
            }
        } else if (e.getSource()==this.buttonDelete) {
            Etude selectedEtude = getSelectEtude();
            if (selectedEtude!=null) {
                int confirmation = JOptionPane.showConfirmDialog(null,"Supprimer - "+selectedEtude.getLibelle()+" ?","Confirmation",JOptionPane.YES_NO_OPTION);
                if (confirmation==0) {
                    selectedEtude.delete();
                    loadEtudes();
                    updateView();
                }
            } else {
                JOptionPane.showMessageDialog(this,"Aucune étude sélectionnée");
            }
        } else if (e.getSource()==this.buttonNew) {
            String libelleEtude = getLibelleNouvelleEtude();
            Etude nouvelleEtude = new Etude(libelleEtude);
            nouvelleEtude.save();
            loadEtudes();
            updateView();
            EtudeSession session = new EtudeSession(nouvelleEtude);
        }
    }
    private String getLibelleNouvelleEtude() {
        String libelle = "";
        Boolean isLibelleAvailable = false;
        while(!isLibelleAvailable | libelle.equals("")) {
            libelle = JOptionPane.showInputDialog("Nom de l'Etude");
            isLibelleAvailable = true;
            for (Etude etude : etudes) {
                if (libelle.equals(etude.getLibelle())) {
                    isLibelleAvailable = false;
                    JOptionPane.showMessageDialog(null,"Nom déjà utilisé","Errerr",ERROR);
                }
            }
        }
        return libelle;
    }
    
    private Etude getSelectEtude() {
        int selectedRow = tableEtudes.getSelectedRow();
        if (selectedRow > -1) {
            return (Etude) tableEtudes.getValueAt(selectedRow,0);
        } else {
            return null;
        }
    }
    
}
