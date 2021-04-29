/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib.indicators.nrg;

import tables.TableNetValueAddedIndicateur;
import meta.Indic;
import static lib.indicators.nrg.IndicateurNRG.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import etude.Etude;
import static utils.Utils.readValue;

/**
 *
 * @author SylvainPro
 */

public class TableNRG extends TableNetValueAddedIndicateur {
    
    private final IndicateurNRG indicateur;
    
    public TableNRG(Etude etude) {
        
        super(buildModel(etude),etude);
        
        indicateur = (IndicateurNRG) etude.getValueAddedFootprint(Indic.NRG);
        
    }
    
    private static DefaultTableModel buildModel(Etude etude) {
        
        String[] header = {"Libelle","Valeur","Incertitude (en %)"};
        Object[][] donnees = new Object[8][3];
        
        IndicateurNRG indicateur = (IndicateurNRG) etude.getValueAddedFootprint(Indic.NRG);
        
        // Item 1
        donnees[0][INDEX_LIBELLE] = "Electricité";
        donnees[0][INDEX_VALUES] = indicateur.getEnergy(ELECTRICITY);
        donnees[0][INDEX_VALUES] = indicateur.getUncertainty(ELECTRICITY);
        // Item 2
        donnees[1][INDEX_LIBELLE] = "Energie fossile (gaz, essence, etc.)";
        donnees[1][INDEX_VALUES] = indicateur.getEnergy(FOSSILE);
        donnees[1][INDEX_VALUES] = indicateur.getUncertainty(FOSSILE);
        // Item 3
        donnees[2][INDEX_LIBELLE] = "Biomasse";
        donnees[2][INDEX_VALUES] = indicateur.getEnergy(BIOMASSE);
        donnees[2][INDEX_VALUES] = indicateur.getUncertainty(BIOMASSE);
        // Item 4
        donnees[3][INDEX_LIBELLE] = "Chaleur";
        donnees[3][INDEX_VALUES] = indicateur.getEnergy(HEAT);
        donnees[3][INDEX_VALUES] = indicateur.getUncertainty(HEAT);
        // Item 5
        donnees[4][INDEX_LIBELLE] = "Energie renouvelable (photovoltaïque, éolien, etc.)";
        donnees[4][INDEX_VALUES] = indicateur.getEnergy(PRIMARY_RENEWABLE);
        donnees[4][INDEX_VALUES] = indicateur.getUncertainty(PRIMARY_RENEWABLE);
        // Total
        donnees[5][INDEX_LIBELLE] = "Consommation totale d'énergie (en MJ)";
        donnees[5][INDEX_VALUES] = indicateur.getEnergy();
        donnees[5][INDEX_VALUES] = indicateur.getUncertainty();
        // Net value Added
        donnees[6][INDEX_LIBELLE] = "Valeur ajoutée nette (en €)";
        donnees[6][INDEX_VALUES] = etude.getFinancialData().getNetValueAdded();
        // Intensity
        donnees[7][INDEX_LIBELLE] = "Intensité de consommation d'énergie liée à la valeur ajoutée nette (en kJ/€)";
        donnees[7][INDEX_VALUES] = indicateur.getValue();
        donnees[7][INDEX_VALUES] = indicateur.getUncertainty();
        
        DefaultTableModel model = new DefaultTableModel(donnees,header);
        return model;
    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
        return row>=0 & row<=5 & column>=1;
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
                    if (row<=4) {
                        indicateur.setEnergy(row,value);
                    } else {
                        indicateur.setEnergy(value);
                    }
                } else if (column==INDEX_UNCERTAINTY) {
                    if (row<=4) {
                        indicateur.setUncertainty(row, value);
                    } else {
                        indicateur.setUncertainty(value);
                    }
                }
                etude.updateValueAddedFootprint(Indic.NRG);
                updateContent();
            }
        }
            
    }
    
    @Override
    public void updateContent() 
    {
        etude.updateValueAddedFootprint(Indic.NRG);
        ignoreNextChanges = true;
        
        // Item1
        setValueAt(indicateur.getEnergy(ELECTRICITY), ELECTRICITY, INDEX_VALUES);
        setValueAt(indicateur.getUncertainty(ELECTRICITY), ELECTRICITY, INDEX_UNCERTAINTY);
        // Item 2
        setValueAt(indicateur.getEnergy(FOSSILE), FOSSILE, INDEX_VALUES);
        setValueAt(indicateur.getUncertainty(FOSSILE), FOSSILE, INDEX_UNCERTAINTY);
        // Item 3
        setValueAt(indicateur.getEnergy(BIOMASSE), BIOMASSE, INDEX_VALUES);
        setValueAt(indicateur.getUncertainty(BIOMASSE), BIOMASSE, INDEX_UNCERTAINTY);
        // item 4
        setValueAt(indicateur.getEnergy(HEAT), HEAT, INDEX_VALUES);
        setValueAt(indicateur.getUncertainty(HEAT), HEAT, INDEX_UNCERTAINTY);
        // Item 5
        setValueAt(indicateur.getEnergy(PRIMARY_RENEWABLE), PRIMARY_RENEWABLE, INDEX_VALUES);
        setValueAt(indicateur.getUncertainty(PRIMARY_RENEWABLE), PRIMARY_RENEWABLE, INDEX_UNCERTAINTY);
        // Total
        setValueAt(indicateur.getEnergy(), 5, INDEX_VALUES);
        setValueAt(indicateur.getUncertainty(), 5, INDEX_UNCERTAINTY);
        // Net Value Added & Quality
        setValueAt(etude.getFinancialData().getNetValueAdded(), 6, INDEX_VALUES);
        // Quality
        setValueAt(indicateur.getValue(), 7, INDEX_VALUES);
        setValueAt(indicateur.getUncertainty(), 7, INDEX_UNCERTAINTY);
        
        ignoreNextChanges = false;
    }
    
}
