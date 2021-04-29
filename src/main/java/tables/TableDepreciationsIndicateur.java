/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tables;

import etude.Depreciation;
import etude.Etude;
import meta.Indic;
import java.util.ArrayList;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import static utils.Utils.readValue;

/**
 *
 * @author SylvainPro
 */

public class TableDepreciationsIndicateur extends TableFinancialDetailsIndicator {
        
    private static final String[] HEADER = {"Identifiant","Fournisseur","Année","Dotation (en €)","Qualité (en %)","Flag.","Incertitude (en %)","","Id"};
    
    public TableDepreciationsIndicateur(Indic indic, Etude etude) {
        super(new DefaultTableModel(new Object[0][HEADER.length],HEADER), indic, etude);
        updateContent();
    }
    
    @Override
    protected void setColumnsIndex() {
        INDEX_CORPORATE_ID = 0;
        INDEX_CORPORATE_NAME = 1;
        INDEX_YEAR = 2;
        INDEX_AMOUNT = 3;
        INDEX_INDICATEUR_VALUE = 4;
        INDEX_INDICATEUR_FLAG = 5;
        INDEX_UNCERTAINTY = 6;
        INDEX_SYNC = 7;
        INDEX_ID = 8;
        NB_COLUMNS = HEADER.length;
    }
    
    @Override
     void handleChanged(TableModelEvent e) {
        
        int column = e.getColumn();
        int row = e.getFirstRow();
        
        Integer id = (Integer) model.getValueAt(row, INDEX_ID);
        Depreciation depreciation = financialData.getDepreciation(id);
        
        Double value = readValue(model.getValueAt(row,column));
        if (value!=null) {
            depreciation.getFootprint().getIndicateur(indic).setQuality(value); 
        }
    }
    
    @Override
    public void updateContent() {
        
        model.setRowCount(0);
        ArrayList<Depreciation> depreciations = financialData.getDepreciations();
        
        ignoreNextChanges = true;
        
        depreciations.stream().map((depreciation) -> {
            Object[] data = new Object[NB_COLUMNS];
            data[INDEX_ID] = depreciation.getId();
            data[INDEX_CORPORATE_ID] = depreciation.getCorporateId();
            data[INDEX_CORPORATE_NAME] = depreciation.getCorporateName();
            data[INDEX_YEAR] = depreciation.getYear();
            data[INDEX_AMOUNT] = depreciation.getAmount();
            data[INDEX_INDICATEUR_VALUE] = depreciation.getFootprint().getIndicateur(indic).getValue();
            data[INDEX_INDICATEUR_FLAG] = depreciation.getFootprint().getIndicateur(indic).getFlag().getCode();
            data[INDEX_UNCERTAINTY] = depreciation.getFootprint().getIndicateur(indic).getUncertainty();
            return data;
        }).forEachOrdered((data) -> {
            model.addRow(data);
        });
        
        ignoreNextChanges = false;
        
    }

    
}
