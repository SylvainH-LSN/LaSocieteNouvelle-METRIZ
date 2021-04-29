/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib.indicators.art;

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

public class TableART extends TableNetValueAddedIndicateur {
    
    private final IndicateurART indicateur;
    
    public TableART(Etude etude) {
        
        super(buildModel(etude),etude);
        
        indicateur = (IndicateurART) etude.getValueAddedFootprint(Indic.ART);
        
    }
    
    private static DefaultTableModel buildModel(Etude etude) {
        
        String[] header = {"Libelle","Montant (en €)"};
        Object[][] donnees = new Object[3][2];
        
        IndicateurART indicateur = (IndicateurART) etude.getValueAddedFootprint(Indic.ART);
        
        donnees[0][INDEX_LIBELLE] = "Valeur ajoutée produite de manière artisanale (en €)";
        donnees[0][INDEX_VALUES] = indicateur.getCraftedProduction();
        
        donnees[1][INDEX_LIBELLE] = "Valeur Ajoutée Nette (en €)";
        donnees[1][INDEX_VALUES] = etude.getFinancialData().getNetValueAdded();
        
        donnees[2][INDEX_LIBELLE] = "Contribution liée à la valeur ajoutée nette (en %)";
        donnees[2][INDEX_VALUES] = indicateur.getValue();
        
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
                    indicateur.setCraftedProduction(value);
                    etude.updateValueAddedFootprint(Indic.ART);
                    ignoreNextChanges = true;
                    setValueAt(indicateur.getValue(), 2, INDEX_VALUES);
                    ignoreNextChanges = false;
                    
                }
            }
        }
            
    }
    
    @Override
    public void updateContent() 
    {
        etude.updateValueAddedFootprint(Indic.ART);
        ignoreNextChanges = true;
        setValueAt(indicateur.getCraftedProduction(), 0, INDEX_VALUES);
        setValueAt(indicateur.getValue(), 2, INDEX_VALUES);
        ignoreNextChanges = false;
    }
    
}
