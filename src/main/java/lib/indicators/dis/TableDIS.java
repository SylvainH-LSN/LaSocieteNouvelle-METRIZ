/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib.indicators.dis;

import renderers.DefaultCellRenderer;
import renderers.RendererValueInput;
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

public class TableDIS extends TableNetValueAddedIndicateur {
    
    private final IndicateurDIS indicateur;
    
    public TableDIS(Etude etude) {
        
        super(buildModel(etude),etude);
        
        indicateur = (IndicateurDIS) etude.getValueAddedFootprint(Indic.DIS);
        
    }
    
    private static DefaultTableModel buildModel(Etude etude) {
        
        String[] header = {"Libelle","Valeur"};
        Object[][] donnees = new Object[1][2];
        
        IndicateurDIS indicateur = (IndicateurDIS) etude.getValueAddedFootprint(Indic.DIS);
        
        donnees[0][INDEX_LIBELLE] = "Indice de GINI - Taux horaires bruts (/100)";
        donnees[0][INDEX_VALUES] = indicateur.getIndexGINI();
        
        DefaultTableModel model = new DefaultTableModel(donnees,header);
        return model;
    }
    
    @Override
    protected void setRenderers() {
        getColumnModel().getColumn(0).setCellRenderer(new DefaultCellRenderer());
        getColumnModel().getColumn(1).setCellRenderer(new RendererValueInput(1, new int[]{}));
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
                    indicateur.setIndexGINI(value);
                    etude.updateValueAddedFootprint(Indic.DIS);
                    
                }
            }
        }
            
    }
    
    @Override
    public void updateContent() 
    {
        etude.updateValueAddedFootprint(Indic.DIS);
        ignoreNextChanges = true;
        setValueAt(indicateur.getIndexGINI(), 0, INDEX_VALUES);
        ignoreNextChanges = false;
    }
    
}
