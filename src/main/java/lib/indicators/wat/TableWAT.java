/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib.indicators.wat;

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

public class TableWAT extends TableNetValueAddedIndicateur {
    
    private final IndicateurWAT indicateur;
    
    public TableWAT(Etude etude) {
        
        super(buildModel(etude),etude);
        
        indicateur = (IndicateurWAT) etude.getValueAddedFootprint(Indic.WAT);
        
    }
    
    private static DefaultTableModel buildModel(Etude etude) {
        
        String[] header = {"Libelle","Valeur","Incerttitude (en %)"};
        Object[][] donnees = new Object[3][3];
        
        IndicateurWAT indicateur = (IndicateurWAT) etude.getValueAddedFootprint(Indic.WAT);
        
        donnees[0][INDEX_LIBELLE] = "Consommation d'eau (en m3)";
        donnees[0][INDEX_VALUES] = indicateur.getConsumption();
        donnees[0][INDEX_UNCERTAINTY] = indicateur.getUncertainty();
        
        donnees[1][INDEX_LIBELLE] = "Valeur ajoutée nette (en €)";
        donnees[1][INDEX_VALUES] = etude.getFinancialData().getNetValueAdded();
        
        donnees[2][INDEX_LIBELLE] = "Intensité de consommation d'eau liée à la valeur ajoutée nette (en L/€)";
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
                    indicateur.setConsumption(value);
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
        etude.updateValueAddedFootprint(Indic.WAT);
        ignoreNextChanges = true;
        
        // Water
        setValueAt(indicateur.getConsumption(), 0, INDEX_VALUES);
        setValueAt(indicateur.getUncertainty(), 0, INDEX_UNCERTAINTY);
        // Net Value Added
        setValueAt(etude.getFinancialData().getNetValueAdded(), 1, INDEX_VALUES);
        // Quality
        setValueAt(indicateur.getValue(), 2, INDEX_VALUES);
        setValueAt(indicateur.getUncertainty(), 2, INDEX_UNCERTAINTY);
        
        ignoreNextChanges = false;
    }
    
}
