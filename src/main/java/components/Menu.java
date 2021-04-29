/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import views.VueUniteLegale;
import views.VueDonneesFinancieres;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import etude.Etude;
import java.awt.Dimension;
import javax.swing.Box;
import manager.EtudeSession;
import meta.Indic;
import views.VueIndicateur;

/**
 *
 * @author SylvainPro
 */
public class Menu extends JPanel implements ActionListener {
        
    public Button uniteLegale = new Button("Unité Legale", this);
    public Button donneesFinancieres = new Button("Données Financières", this);
    public Button eco = new Button("Economie nationale", this);
    public Button art = new Button("Artisanat & Savoir-Faire", this);
    public Button soc = new Button("Mission sociale", this);
    public Button knw = new Button("Compétences & Connaissances", this);
    public Button dis = new Button("Répartition des Revenus", this);
    public Button geq = new Button("Egalité Femmes/Hommes", this);
    public Button ghg = new Button("Gaz à Effet de Serre", this);
    public Button mat = new Button("Matières Premières", this);
    public Button was = new Button("Déchets", this);
    public Button nrg = new Button("Energie", this);
    public Button wat = new Button("Eau", this);
    public Button haz = new Button("Produits dangereux", this);
    public Button updateData = new Button("Analyse", this);
    public Button export = new Button("Export", this);
    
    private EtudeSession session;
    
    public Menu (EtudeSession session) {
        this.session = session;
        
        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        setAlignmentX(CENTER_ALIGNMENT);
        
        add(uniteLegale);
        add(Box.createRigidArea(new Dimension(0, 2)));
        add(donneesFinancieres);
        
        add(Box.createRigidArea(new Dimension(0, 25)));
        
        add(eco);
        add(Box.createRigidArea(new Dimension(0, 2)));
        add(art);
        add(Box.createRigidArea(new Dimension(0, 2)));
        add(soc);
        add(Box.createRigidArea(new Dimension(0, 2)));
        add(knw);
        add(Box.createRigidArea(new Dimension(0, 2)));
        add(dis);
        add(Box.createRigidArea(new Dimension(0, 2)));
        add(geq);
        add(Box.createRigidArea(new Dimension(0, 2)));
        add(ghg);
        add(Box.createRigidArea(new Dimension(0, 2)));
        add(mat);
        add(Box.createRigidArea(new Dimension(0, 2)));
        add(was);
        add(Box.createRigidArea(new Dimension(0, 2)));
        add(nrg);
        add(Box.createRigidArea(new Dimension(0, 2)));
        add(wat);
        add(Box.createRigidArea(new Dimension(0, 2)));
        add(haz);
        
        add(Box.createRigidArea(new Dimension(0, 25)));
        
        //setBackground(Color.white);
        setOpaque(false);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==uniteLegale) {
            session.updateVue(new VueUniteLegale(session));
        } else if (e.getSource()==donneesFinancieres) {
            session.updateVue(new VueDonneesFinancieres(session));
        } else if (e.getSource()==eco) {
            session.updateVue(new VueIndicateur(session, Indic.ECO));
        } else if (e.getSource()==art) {
            session.updateVue(new VueIndicateur(session, Indic.ART));
        } else if (e.getSource()==soc) {
            session.updateVue(new VueIndicateur(session, Indic.SOC));
        } else if (e.getSource()==knw) {
            session.updateVue(new VueIndicateur(session, Indic.KNW));
        } else if (e.getSource()==dis) {
            session.updateVue(new VueIndicateur(session, Indic.DIS));
        } else if (e.getSource()==geq) {
            session.updateVue(new VueIndicateur(session, Indic.GEQ));
        } else if (e.getSource()==ghg) {
            session.updateVue(new VueIndicateur(session, Indic.GHG));
        } else if (e.getSource()==mat) {
            session.updateVue(new VueIndicateur(session, Indic.MAT));
        } else if (e.getSource()==was) {
            session.updateVue(new VueIndicateur(session, Indic.WAS));
        } else if (e.getSource()==nrg) {
            session.updateVue(new VueIndicateur(session, Indic.NRG));
        } else if (e.getSource()==wat) {
            session.updateVue(new VueIndicateur(session, Indic.WAT));
        } else if (e.getSource()==haz) {
            session.updateVue(new VueIndicateur(session, Indic.HAZ));
        } else if (e.getSource()==updateData) {
            Etude etude = session.getEtude();
            etude.getFinancialData().updateExpensesSocialFootprint();
            etude.save();
            session.updateVue(new VueUniteLegale(session));
        } else {
            session.updateVue(new VueUniteLegale(session));
        }
    }
    
}
