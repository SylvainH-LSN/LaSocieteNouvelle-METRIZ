/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib.indicators.ghg;

import tables.TableNetValueAddedIndicateur;
import meta.Indic;
import static lib.indicators.ghg.IndicateurGHG.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import etude.Etude;
import static utils.Utils.readValue;

/**
 *
 * @author SylvainPro
 */

public class TableGHG extends TableNetValueAddedIndicateur {
    
    private final IndicateurGHG indicateur;
    
    public TableGHG(Etude etude) {
        
        super(buildModel(etude),etude);
        
        indicateur = (IndicateurGHG) etude.getValueAddedFootprint(Indic.GHG);
        
    }
    
    private static DefaultTableModel buildModel(Etude etude) {
        
        String[] header = {"Libelle","Valeur","Incertitude (en %)"};
        Object[][] donnees = new Object[8][3];
        
        IndicateurGHG indicateur = (IndicateurGHG) etude.getValueAddedFootprint(Indic.GHG);
        
        // Item 1
        donnees[0][INDEX_LIBELLE] = "Emissions directes des sources fixes de combustion (en kgCO2e)";
        donnees[0][INDEX_VALUES] = indicateur.getEmissions(0);
        donnees[0][INDEX_UNCERTAINTY] = indicateur.getEmissionsUncertainty(0);
        // Item 2
        donnees[1][INDEX_LIBELLE] = "Emissions directes des sources mobiles à moteur thermique (en kgCO2e)";
        donnees[1][INDEX_VALUES] = indicateur.getEmissions(1);
        donnees[1][INDEX_UNCERTAINTY] = indicateur.getEmissionsUncertainty(1);
        // Item 3
        donnees[2][INDEX_LIBELLE] = "Emissions directes des procédés hors énergie (en kgCO2e)";
        donnees[2][INDEX_VALUES] = indicateur.getEmissions(2);
        donnees[2][INDEX_UNCERTAINTY] = indicateur.getEmissionsUncertainty(2);
        // Item 4
        donnees[3][INDEX_LIBELLE] = "Emissions directes fugitives (en kgCO2e)";
        donnees[3][INDEX_VALUES] = indicateur.getEmissions(3);
        donnees[3][INDEX_UNCERTAINTY] = indicateur.getEmissionsUncertainty(3);
        // Item 5
        donnees[4][INDEX_LIBELLE] = "Emissions issues de la biomasse (sol et forêt) (en kgCO2e)";
        donnees[4][INDEX_VALUES] = indicateur.getEmissions(4);
        donnees[4][INDEX_UNCERTAINTY] = indicateur.getEmissionsUncertainty(4);
        // Total
        donnees[5][INDEX_LIBELLE] = "Total des émissions (en kgCO2e)";
        donnees[5][INDEX_VALUES] = indicateur.getEmissions();
        donnees[5][INDEX_UNCERTAINTY] = indicateur.getUncertainty();
        // Net value Added
        donnees[6][INDEX_LIBELLE] = "Valeur Ajoutée Nette (en €)";
        donnees[6][INDEX_VALUES] = etude.getFinancialData().getNetValueAdded();
        // Intensity
        donnees[7][INDEX_LIBELLE] = "Intensité d'émission de la valeur ajoutée nette (en gCO2e/€)";
        donnees[7][INDEX_VALUES] = indicateur.getValue();
        donnees[7][INDEX_UNCERTAINTY] = indicateur.getUncertainty();
        
        DefaultTableModel model = new DefaultTableModel(donnees,header);
        return model;
    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
        return row>=0 & row<=4 & column>0;
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
                if (row>=0 & row<=4) {
                    if (column==1) {
                        indicateur.setEmissions(row,value);
                    } else if (column==2) {
                        indicateur.setEmissionsUncertainty(row, value);
                    }
                } else if (row==5) {
                    if (column==1) {
                        indicateur.setEmissions(value);
                    } else if (column==2) {
                        indicateur.setUncertainty(value);
                    }
                }
                updateContent();
            }
        }
            
    }
    
    @Override
    public void updateContent() 
    {
        etude.updateValueAddedFootprint(Indic.GHG);
        ignoreNextChanges = true;
        
        // Poste 1
        setValueAt(indicateur.getEmissions(POSTE_SOURCES_FIXES), POSTE_SOURCES_FIXES, INDEX_VALUES);
        setValueAt(indicateur.getEmissionsUncertainty(POSTE_SOURCES_FIXES), POSTE_SOURCES_FIXES, INDEX_UNCERTAINTY);
        // Poste 2
        setValueAt(indicateur.getEmissions(POSTE_SOURCES_MOBILES), POSTE_SOURCES_MOBILES, INDEX_VALUES);
        setValueAt(indicateur.getEmissionsUncertainty(POSTE_SOURCES_MOBILES), POSTE_SOURCES_MOBILES, INDEX_UNCERTAINTY);
        // Poste 3
        setValueAt(indicateur.getEmissions(POSTE_PROCEDES), POSTE_PROCEDES, INDEX_VALUES);
        setValueAt(indicateur.getEmissionsUncertainty(POSTE_PROCEDES), POSTE_PROCEDES, INDEX_UNCERTAINTY);
        // Poste 4
        setValueAt(indicateur.getEmissions(POSTE_FUGITIVES), POSTE_FUGITIVES, INDEX_VALUES);
        setValueAt(indicateur.getEmissionsUncertainty(POSTE_FUGITIVES), POSTE_FUGITIVES, INDEX_UNCERTAINTY);
        // Poste 5
        setValueAt(indicateur.getEmissions(POSTE_BIOMASSE), POSTE_BIOMASSE, INDEX_VALUES);
        setValueAt(indicateur.getEmissionsUncertainty(POSTE_BIOMASSE), POSTE_BIOMASSE, INDEX_UNCERTAINTY);
        // Total
        setValueAt(indicateur.getEmissions(), 5, INDEX_VALUES);
        setValueAt(indicateur.getUncertainty(), 5, INDEX_UNCERTAINTY);
        // Net Value Added & Quality
        setValueAt(etude.getFinancialData().getNetValueAdded(), 6, INDEX_VALUES);
        
        setValueAt(indicateur.getValue(), 7, INDEX_VALUES);
        setValueAt(indicateur.getUncertainty(), 7, INDEX_UNCERTAINTY);
        
        ignoreNextChanges = false;
    }
    
}
