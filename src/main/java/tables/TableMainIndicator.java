/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tables;

import renderers.DefaultCellRenderer;
import renderers.RendererValueInput;
import etude.FinancialData;
import meta.Indic;
import javax.swing.table.DefaultTableModel;
import etude.Etude;

/**
 *
 * @author SylvainPro
 */

public class TableMainIndicator extends Table {
        
    private final Indic indic;
    private final Etude etude;
    
    public TableMainIndicator(Indic indic, Etude etude) {
        
        super(buildModel(indic, etude));
                
        this.indic = indic;
        this.etude = etude;
                
        getColumnModel().getColumn(0).setCellRenderer(new DefaultCellRenderer());
        getColumnModel().getColumn(1).setCellRenderer(new RendererValueInput(0, new int[]{0,1,2,3} ));
        getColumnModel().getColumn(2).setCellRenderer(new RendererValueInput(0, new int[]{0,1,2,3} ));
        getColumnModel().getColumn(3).setCellRenderer(new RendererValueInput(0, new int[]{0,1,2,3} ));
        
        getColumnModel().getColumn(0).setPreferredWidth(300);
        getColumnModel().getColumn(1).setPreferredWidth(100);
        
    }
    
    private static DefaultTableModel buildModel(Indic indicateur, Etude etude) {
        
        etude.updateFootprints(indicateur);
        FinancialData donneesFinancieres = etude.getFinancialData();
        
        String[] header = {"Agrégat","Montant (en €)","Qualité (en "+indicateur.getUnit()+")","Incertitude (en %)"};
        Object[][] donnees = new Object[4][4];
        
        // Agrégat
        donnees[0][0] = "Production";
        donnees[1][0] = "Charges externes";
        donnees[2][0] = "Dotations aux amortissements sur immobilisations";
        donnees[3][0] = "Valeur Ajoutée Nette";
        
        // Montant
        donnees[0][1] = donneesFinancieres.getProduction();
        donnees[1][1] = donneesFinancieres.getAmountExpenses();
        donnees[2][1] = donneesFinancieres.getAmountDepreciations();
        donnees[3][1] = donneesFinancieres.getNetValueAdded();
        
        // Qualité
        donnees[0][2] = etude.getProductionFootprint().getIndicateur(indicateur).getValue();
        donnees[1][2] = etude.getExpensesFootprint().getIndicateur(indicateur).getValue();
        donnees[2][2] = etude.getDepreciationsFootprint().getIndicateur(indicateur).getValue();
        donnees[3][2] = etude.getValueAddedFootprint(indicateur).getValue();
        
        // Incertitude
        donnees[0][3] = etude.getProductionFootprint().getIndicateur(indicateur).getUncertainty();
        donnees[1][3] = etude.getExpensesFootprint().getIndicateur(indicateur).getUncertainty();
        donnees[2][3] = etude.getDepreciationsFootprint().getIndicateur(indicateur).getUncertainty();
        donnees[3][3] = etude.getValueAddedFootprint(indicateur).getUncertainty();
                
        DefaultTableModel model = new DefaultTableModel(donnees,header);
        return model;
    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
    
    public void updateContent() {
        
        FinancialData financialData = etude.getFinancialData();
        setValueAt(financialData.getProduction(),0,1);
        
        setValueAt(etude.getProductionFootprint().getIndicateur(indic).getValue(),0,2);
        setValueAt(etude.getProductionFootprint().getIndicateur(indic).getUncertainty(),0,3);
        
        setValueAt(etude.getExpensesFootprint().getIndicateur(indic).getValue(),1,2);
        setValueAt(etude.getExpensesFootprint().getIndicateur(indic).getUncertainty(),1,3);
        
        setValueAt(etude.getDepreciationsFootprint().getIndicateur(indic).getValue(),2,2);
        setValueAt(etude.getDepreciationsFootprint().getIndicateur(indic).getUncertainty(),2,3);
        
        setValueAt(etude.getValueAddedFootprint(indic).getValue(),3,2);
        setValueAt(etude.getValueAddedFootprint(indic).getUncertainty(),3,3);
        
    }
    
}
