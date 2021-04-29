/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib.indicators.mat;

import tables.TableNetValueAddedIndicateur;
import meta.Indic;
import static lib.indicators.mat.IndicateurMAT.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import etude.Etude;
import static utils.Utils.readValue;

/**
 *
 * @author SylvainPro
 */

public class TableMAT extends TableNetValueAddedIndicateur {
    
    private final IndicateurMAT indicateur;
    
    public TableMAT(Etude etude) {
        
        super(buildModel(etude),etude);
        
        indicateur = (IndicateurMAT) etude.getValueAddedFootprint(Indic.MAT);
        
    }
    
    private static DefaultTableModel buildModel(Etude etude) {
        
        String[] header = {"Libelle","Valeur","Incertitude (en %)"};
        Object[][] donnees = new Object[7][3];
        
        IndicateurMAT indicateur = (IndicateurMAT) etude.getValueAddedFootprint(Indic.MAT);
        
        // Item 1
        donnees[0][INDEX_LIBELLE] = "Biomasse (Cultures et résidus, Bois, Matières biologiques, etc.)";
        donnees[0][INDEX_VALUES] = indicateur.getMaterials(BIOMASSE);
        donnees[0][INDEX_UNCERTAINTY] = indicateur.getUncertainty(BIOMASSE);
        // Item 2
        donnees[1][INDEX_LIBELLE] = "Minerais métalliques (Fer, Cuivre, Zinc, etc.)";
        donnees[1][INDEX_VALUES] = indicateur.getMaterials(METAL);
        donnees[1][INDEX_UNCERTAINTY] = indicateur.getUncertainty(METAL);
        // Item 3
        donnees[2][INDEX_LIBELLE] = "Minerais non-métalliques (Mabre, Granit, Ardoise, etc.)";
        donnees[2][INDEX_VALUES] = indicateur.getMaterials(NON_METAL);
        donnees[2][INDEX_UNCERTAINTY] = indicateur.getUncertainty(NON_METAL);
        // Item 4
        donnees[3][INDEX_LIBELLE] = "Matières fossiles (Charbon, Gaz, Pétrole, etc.)";
        donnees[3][INDEX_VALUES] = indicateur.getMaterials(FOSSILE);
        donnees[3][INDEX_UNCERTAINTY] = indicateur.getUncertainty(FOSSILE);
        // Total
        donnees[4][INDEX_LIBELLE] = "Extraction totale (en kg)";
        donnees[4][INDEX_VALUES] = indicateur.getMaterials();
        donnees[4][INDEX_UNCERTAINTY] = indicateur.getUncertainty();
        // Net value Added
        donnees[5][INDEX_LIBELLE] = "Valeur Ajoutée Nette (en €)";
        donnees[5][INDEX_VALUES] = etude.getFinancialData().getNetValueAdded();
        // Intensity
        donnees[6][INDEX_LIBELLE] = "Intensité d'extraction liée à la valeur ajoutée nette (en g/€)";
        donnees[6][INDEX_VALUES] = indicateur.getValue();
        donnees[6][INDEX_UNCERTAINTY] = indicateur.getUncertainty();
        
        DefaultTableModel model = new DefaultTableModel(donnees,header);
        return model;
    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
        return row>=0 & row<=4 & column==1;
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
                    if (row<=3) {
                        indicateur.setMaterials(row,value);
                    } else {
                        indicateur.setMaterials(value);
                    }
                } else if (column==INDEX_UNCERTAINTY) {
                    if (row<=3) {
                        indicateur.setUncertainty(row, value);
                    } else {
                        indicateur.setUncertainty(value);
                    }
                }
                etude.updateValueAddedFootprint(Indic.MAT);
                updateContent();
            }
        }
            
    }
    
    @Override
    public void updateContent() 
    {
        etude.updateValueAddedFootprint(Indic.MAT);
        ignoreNextChanges = true;
        
        // Materials extractions
        setValueAt(indicateur.getMaterials(BIOMASSE), BIOMASSE, INDEX_VALUES);
        setValueAt(indicateur.getUncertainty(BIOMASSE), BIOMASSE, INDEX_UNCERTAINTY);
        
        setValueAt(indicateur.getMaterials(METAL), METAL, INDEX_VALUES);
        setValueAt(indicateur.getUncertainty(METAL), METAL, INDEX_UNCERTAINTY);
        
        setValueAt(indicateur.getMaterials(NON_METAL), NON_METAL, INDEX_VALUES);
        setValueAt(indicateur.getUncertainty(NON_METAL), NON_METAL, INDEX_UNCERTAINTY);
        
        setValueAt(indicateur.getMaterials(FOSSILE), FOSSILE, INDEX_VALUES);
        setValueAt(indicateur.getUncertainty(FOSSILE), FOSSILE, INDEX_UNCERTAINTY);
        // Total
        setValueAt(indicateur.getMaterials(), 4, INDEX_VALUES);
        setValueAt(indicateur.getUncertainty(), 4, INDEX_UNCERTAINTY);
        
        setValueAt(etude.getFinancialData().getNetValueAdded(), 5, INDEX_VALUES);
        
        setValueAt(indicateur.getValue(), 6, INDEX_VALUES);
        setValueAt(indicateur.getUncertainty(), 6, INDEX_UNCERTAINTY);
        
        ignoreNextChanges = false;
    }
    
}
