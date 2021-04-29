/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib.indicators.geq;

import renderers.DefaultCellRenderer;
import renderers.RendererValueInput;
import tables.TableNetValueAddedIndicateur;
import meta.Indic;
import lib.indicators.geq.IndicateurGEQ;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import etude.Etude;
import static utils.Utils.readValue;

/**
 *
 * @author SylvainPro
 */

public class TableGEQ extends TableNetValueAddedIndicateur {
    
    private final IndicateurGEQ indicateur;
    
    public TableGEQ(Etude etude) {
        
        super(buildModel(etude),etude);
        
        indicateur = (IndicateurGEQ) etude.getValueAddedFootprint(Indic.GEQ);
        
    }
    
    private static DefaultTableModel buildModel(Etude etude) {
        
        String[] header = {"Libelle","Valeur"};
        Object[][] donnees = new Object[1][2];
        
        IndicateurGEQ indicateur = (IndicateurGEQ) etude.getValueAddedFootprint(Indic.GEQ);
        
        donnees[0][INDEX_LIBELLE] = "Ecart de rémunération (taux horaire brut) (en %)";
        donnees[0][INDEX_VALUES] = indicateur.getWageGap();
        
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
                    indicateur.setWageGap(value);
                    etude.updateValueAddedFootprint(Indic.GEQ);
                    
                }
            }
        }
            
    }
    
    @Override
    public void updateContent() 
    {
        etude.updateValueAddedFootprint(Indic.GEQ);
        ignoreNextChanges = true;
        setValueAt(indicateur.getWageGap(), 0, INDEX_VALUES);
        ignoreNextChanges = false;
    }
    
}
