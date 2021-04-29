/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib.indicators.soc;

import renderers.DefaultCellRenderer;
import renderers.RendererValueInput;
import tables.TableNetValueAddedIndicateur;
import meta.Indic;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import etude.Etude;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author SylvainPro
 */

public class TableSOC extends TableNetValueAddedIndicateur {
    
    private final IndicateurSOC indicateur;
    
    public TableSOC(Etude etude) {
        
        super(buildModel(etude),etude);
        
        indicateur = (IndicateurSOC) etude.getValueAddedFootprint(Indic.SOC);
                
    }
    
    private static DefaultTableModel buildModel(Etude etude) {
        
        String[] header = {"Libelle","Valeur"};
        Object[][] donnees = new Object[2][2];
        
        IndicateurSOC indicateur = (IndicateurSOC) etude.getValueAddedFootprint(Indic.SOC);
        
        donnees[0][INDEX_LIBELLE] = "Intérêt social ou raison d'être définie";
        donnees[0][INDEX_VALUES] = indicateur.getHasSocialPurpose();
        
        donnees[1][INDEX_LIBELLE] = "Contribution liée à la valeur ajoutée (en %)";
        donnees[1][INDEX_VALUES] = indicateur.getValue();
        
        DefaultTableModel model = new DefaultTableModel(donnees,header);
        return model;
    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
        return row==0 & column==1;
    }
    
    @Override
    protected void setRenderers() {
        getColumnModel().getColumn(0).setCellRenderer(new DefaultCellRenderer());
        getColumnModel().getColumn(1).setCellRenderer(new RendererValueInput(0, new int[]{getRowCount()-1}));
    }
    
    @Override
    public TableCellEditor getCellEditor(int row, int column) {
        if(row==0 & column==1) {
            return super.getDefaultEditor(Boolean.class);
        } else {
            return super.getCellEditor(row, column);
        }
    }
    
    @Override
    protected void handleChanged(TableModelEvent e) {
        if (!ignoreNextChanges) 
        {
            int column = e.getColumn();
            int row = e.getFirstRow();
            
            DefaultTableModel model = (DefaultTableModel)e.getSource();
            Boolean value = (Boolean) model.getValueAt(row,column);
            
            if (value!=null) {
                if (column==INDEX_VALUES) {
                    indicateur.setHasSocialPurpose(value);
                    etude.updateValueAddedFootprint(Indic.SOC);
                    ignoreNextChanges = true;
                    setValueAt(indicateur.getValue(), 1, INDEX_VALUES);
                    ignoreNextChanges = false;
                    
                }
            }
        }
            
    }
    
    @Override
    public void updateContent() 
    {
        etude.updateValueAddedFootprint(Indic.SOC);
        ignoreNextChanges = true;
        setValueAt(indicateur.getHasSocialPurpose(), 0, INDEX_VALUES);
        setValueAt(indicateur.getValue(), 1, INDEX_VALUES);
        ignoreNextChanges = false;
    }
    
}
