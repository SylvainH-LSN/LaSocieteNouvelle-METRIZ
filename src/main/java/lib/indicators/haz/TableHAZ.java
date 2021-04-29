/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib.indicators.haz;

import tables.TableNetValueAddedIndicateur;
import meta.Indic;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import etude.Etude;
import static utils.Utils.readValue;

/**
 *
 * @author SylvainPro
 */

public class TableHAZ extends TableNetValueAddedIndicateur {
    
    private final IndicateurHAZ indicateur;
    
    public TableHAZ(Etude etude) {
        
        super(buildModel(etude),etude);
        
        indicateur = (IndicateurHAZ) etude.getValueAddedFootprint(Indic.HAZ);
        
    }
    
    private static DefaultTableModel buildModel(Etude etude) {
        
        String[] header = {"Libelle","Valeur","Incertitude (en %)"};
        Object[][] donnees = new Object[3][3];
        
        IndicateurHAZ indicateur = (IndicateurHAZ) etude.getValueAddedFootprint(Indic.HAZ);
        
        // Waste production
        donnees[0][INDEX_LIBELLE] = "Utilisation de produits dangereux (en kg)";
        donnees[0][INDEX_VALUES] = indicateur.getDangerousProductsUse();
        donnees[0][INDEX_UNCERTAINTY] = indicateur.getUncertainty();
        // Net value Added
        donnees[1][INDEX_LIBELLE] = "Valeur Ajoutée Nette (en €)";
        donnees[1][INDEX_VALUES] = etude.getFinancialData().getNetValueAdded();
        // Intensity
        donnees[2][INDEX_LIBELLE] = "Intensité d'utilisation de produits dangereux (en g/€)";
        donnees[2][INDEX_VALUES] = indicateur.getValue();
        donnees[2][INDEX_UNCERTAINTY] = indicateur.getUncertainty();
        
        DefaultTableModel model = new DefaultTableModel(donnees,header);
        return model;
    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
        return row==0 & column==1;
    }
    
    @Override
    protected void handleChanged(TableModelEvent e) {
        if (!ignoreNextChanges) 
        {
            int column = e.getColumn();
            int row = e.getFirstRow();
            
            DefaultTableModel model = (DefaultTableModel)e.getSource();
            Double value = readValue(model.getValueAt(row,column));
            
            if (value!=null) {
                if (column==INDEX_VALUES) {
                    indicateur.setHazard(value);                    
                } else if (column==INDEX_UNCERTAINTY) {
                    indicateur.setUncertainty(value);
                }
                updateContent();
            }
        }
            
    }
    
    @Override
    public void updateContent() 
    {
        etude.updateValueAddedFootprint(Indic.HAZ);
        ignoreNextChanges = true;
        
        // Hazard
        setValueAt(indicateur.getDangerousProductsUse(), 0, INDEX_VALUES);
        setValueAt(indicateur.getUncertainty(), 0, INDEX_UNCERTAINTY);
        // Net Value Added
        setValueAt(etude.getFinancialData().getNetValueAdded(), 1, INDEX_VALUES);
        // Quality
        setValueAt(indicateur.getValue(), 2, INDEX_VALUES);
        setValueAt(indicateur.getUncertainty(), 2, INDEX_UNCERTAINTY);
        
        ignoreNextChanges = false;
    }
    
}
