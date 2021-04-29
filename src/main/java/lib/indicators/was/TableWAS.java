/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib.indicators.was;

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

public class TableWAS extends TableNetValueAddedIndicateur {
    
    private final IndicateurWAS indicateur;
    
    public TableWAS(Etude etude) {
        
        super(buildModel(etude),etude);
        
        indicateur = (IndicateurWAS) etude.getValueAddedFootprint(Indic.WAS);
        
    }
    
    private static DefaultTableModel buildModel(Etude etude) {
        
        String[] header = {"Libelle","Valeur","Incertitude (en %)"};
        Object[][] donnees = new Object[3][3];
        
        IndicateurWAS indicateur = (IndicateurWAS) etude.getValueAddedFootprint(Indic.WAS);
        
        donnees[0][INDEX_LIBELLE] = "Production de déchets (en kg)";
        donnees[0][INDEX_VALUES] = indicateur.getWaste();
        donnees[0][INDEX_UNCERTAINTY] = indicateur.getUncertainty();
        
        donnees[1][INDEX_LIBELLE] = "Valeur ajoutée nette (en €)";
        donnees[1][INDEX_VALUES] = etude.getFinancialData().getNetValueAdded();
        
        donnees[2][INDEX_LIBELLE] = "Intensité de production de déchets liée à la valeur ajoutée nette (en g/€)";
        donnees[2][INDEX_VALUES] = indicateur.getValue();
        donnees[2][INDEX_VALUES] = indicateur.getUncertainty();
        
        DefaultTableModel model = new DefaultTableModel(donnees,header);
        return model;
    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
        return row==0 & column>=1;
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
                    indicateur.setWaste(value);
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
        etude.updateValueAddedFootprint(Indic.WAS);
        ignoreNextChanges = true;
        
        setValueAt(indicateur.getWaste(), 0, INDEX_VALUES);
        setValueAt(indicateur.getWaste(), 0, INDEX_UNCERTAINTY);
        
        setValueAt(etude.getFinancialData().getNetValueAdded(), 1, INDEX_VALUES);
        
        setValueAt(indicateur.getValue(), 2, INDEX_VALUES);
        setValueAt(indicateur.getValue(), 2, INDEX_UNCERTAINTY);
        
        ignoreNextChanges = false;
    }
    
}
