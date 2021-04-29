/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tables;

import renderers.DefaultCellRenderer;
import meta.Indic;
import javax.swing.table.DefaultTableModel;
import etude.Etude;
import renderers.RendererValueOutput;

/**
 *
 * @author SylvainPro
 */

public class TableFootprint extends Table {
        
    private final Etude etude;
    
    public TableFootprint(Etude etude) {
        
        super(buildModel(etude));
                
        this.etude = etude;
                
        getColumnModel().getColumn(0).setCellRenderer(new DefaultCellRenderer());
        getColumnModel().getColumn(1).setCellRenderer(new DefaultCellRenderer());
        getColumnModel().getColumn(2).setCellRenderer(new RendererValueOutput(0));
        getColumnModel().getColumn(3).setCellRenderer(new RendererValueOutput(0));
        
        getColumnModel().getColumn(0).setPreferredWidth(50);
        getColumnModel().getColumn(1).setPreferredWidth(450);
        getColumnModel().getColumn(2).setPreferredWidth(100);
        getColumnModel().getColumn(3).setPreferredWidth(100);
        
        updateContent();
        
    }
    
    private static DefaultTableModel buildModel(Etude etude) {
        
        etude.updateFootprints();
        
        String[] header = {"Code","Indicateur","Valeur","Incertitude (en %)"};
        Object[][] donnees = new Object[12][3];
        
        // Agrégat
        donnees[0][0] = "ART";
        donnees[0][1] = "Contribution aux Métiers d'Art et aux Savoir-Faire (en "+Indic.ART.getUnit()+")";
        
        donnees[1][0] = "DIS";
        donnees[1][1] = "Indice de répartition des rémunérations (en "+Indic.DIS.getUnit()+")";
        
        donnees[2][0] = "ECO";
        donnees[2][1] = "Contribution à l'économie française (en "+Indic.ECO.getUnit()+")";
        
        donnees[3][0] = "GEQ";
        donnees[3][1] = "Indice d'écart de rémunération Femmes/Hommes (en "+Indic.GEQ.getUnit()+")";
        
        donnees[4][0] = "GHG";
        donnees[4][1] = "Intensité d'émission de gaz à effet de serre  (en "+Indic.GHG.getUnit()+")";
        
        donnees[5][0] = "HAZ";
        donnees[5][1] = "Intensité d'utilisation de produits dangereux  (en "+Indic.HAZ.getUnit()+")";
        
        donnees[6][0] = "KNW";
        donnees[6][1] = "Contribution à l'évolution des compétences et des connaissances  (en "+Indic.KNW.getUnit()+")";
        
        donnees[7][0] = "MAT";
        donnees[7][1] = "Intenisté d'extraction de matières premières  (en "+Indic.MAT.getUnit()+")";
        
        donnees[8][0] = "NRG";
        donnees[8][1] = "Intensité de consommation d'énergie  (en "+Indic.NRG.getUnit()+")";
        
        donnees[9][0] = "SOC";
        donnees[9][1] = "Contribution aux acteurs d'intérêt social  (en "+Indic.SOC.getUnit()+")";
        
        donnees[10][0] = "WAS";
        donnees[10][1] = "Intensité de production de déchets  (en "+Indic.WAS.getUnit()+")";
        
        donnees[11][0] = "WAT";
        donnees[11][1] = "Intensité de consommation d'eau  (en "+Indic.WAT.getUnit()+")";
                
        DefaultTableModel model = new DefaultTableModel(donnees,header);
        return model;
    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
    
    public void updateContent() {
                
        setValueAt(etude.getProductionFootprint().getIndicateur(Indic.ART).getValue(), 0, 2);
        setValueAt(etude.getProductionFootprint().getIndicateur(Indic.ART).getUncertainty(), 0, 3);
        
        setValueAt(etude.getProductionFootprint().getIndicateur(Indic.DIS).getValue(), 1, 2);
        setValueAt(etude.getProductionFootprint().getIndicateur(Indic.DIS).getUncertainty(), 1, 3);
        
        setValueAt(etude.getProductionFootprint().getIndicateur(Indic.ECO).getValue(), 2, 2);
        setValueAt(etude.getProductionFootprint().getIndicateur(Indic.ECO).getUncertainty(), 2, 3);
        
        setValueAt(etude.getProductionFootprint().getIndicateur(Indic.GEQ).getValue(), 3, 2);
        setValueAt(etude.getProductionFootprint().getIndicateur(Indic.GEQ).getUncertainty(), 3, 3);
        
        setValueAt(etude.getProductionFootprint().getIndicateur(Indic.GHG).getValue(), 4, 2);
        setValueAt(etude.getProductionFootprint().getIndicateur(Indic.GHG).getUncertainty(), 4, 3);
        
        setValueAt(etude.getProductionFootprint().getIndicateur(Indic.HAZ).getValue(), 5, 2);
        setValueAt(etude.getProductionFootprint().getIndicateur(Indic.HAZ).getUncertainty(), 5, 3);
        
        setValueAt(etude.getProductionFootprint().getIndicateur(Indic.KNW).getValue(), 6, 2);
        setValueAt(etude.getProductionFootprint().getIndicateur(Indic.KNW).getUncertainty(), 6, 3);
        
        setValueAt(etude.getProductionFootprint().getIndicateur(Indic.MAT).getValue(), 7, 2);
        setValueAt(etude.getProductionFootprint().getIndicateur(Indic.MAT).getUncertainty(), 7, 3);
        
        setValueAt(etude.getProductionFootprint().getIndicateur(Indic.NRG).getValue(), 8, 2);
        setValueAt(etude.getProductionFootprint().getIndicateur(Indic.NRG).getUncertainty(), 8, 3);
        
        setValueAt(etude.getProductionFootprint().getIndicateur(Indic.SOC).getValue(), 9, 2);
        setValueAt(etude.getProductionFootprint().getIndicateur(Indic.SOC).getUncertainty(), 9, 3);
        
        setValueAt(etude.getProductionFootprint().getIndicateur(Indic.WAS).getValue(), 10, 2);
        setValueAt(etude.getProductionFootprint().getIndicateur(Indic.WAS).getUncertainty(), 10, 3);
        
        setValueAt(etude.getProductionFootprint().getIndicateur(Indic.WAT).getValue(), 11, 2);
        setValueAt(etude.getProductionFootprint().getIndicateur(Indic.WAT).getUncertainty(), 11, 3);
        
    }
    
}
