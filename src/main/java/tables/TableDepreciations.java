/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tables;

import etude.Depreciation;
import etude.Etude;
import java.util.ArrayList;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import static utils.Utils.readValue;

/**
 *
 * @author SylvainPro
 */
public class TableDepreciations extends TableFinancialDetails {
    
    private static final String[] HEADER = {"Identifiant","Fournisseur","Zone","Activite","Année","Montant","","","Id"};
    
    /* ---------- Constructor ---------- */
    
    public TableDepreciations(Etude etude) {
        super(new DefaultTableModel(new Object[0][HEADER.length],HEADER), etude);
        updateContent();
    }
    
    
    @Override
    void setIndexColumns() {
        INDEX_CORPORATE_ID = 0;
        INDEX_CORPORATE_NAME = 1;
        INDEX_AREA_CODE = 2;
        INDEX_CORPORATE_APE = 3;
        INDEX_YEAR = 4;
        INDEX_AMOUNT = 5;
        INDEX_SYNC = 6;
        INDEX_DEL = 7;
        INDEX_ID = 8;
        NB_COLUMNS = HEADER.length;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return super.isCellEditable(row, column) 
                | ( row<(getRowCount()-1) & column==INDEX_YEAR) ;
    }
    
    @Override
     void handleChanged(TableModelEvent e) {
        
        int column = e.getColumn();
        int row = e.getFirstRow();
            
        // Add new expense
        if ((row+1)==model.getRowCount()) {
            
            String siren = (String) model.getValueAt(row, INDEX_CORPORATE_ID);
            Depreciation depreciation = financialData.addDepreciation(siren);
            
            ignoreNextChanges = true;
                model.setValueAt(depreciation.getId(), row, INDEX_ID);
                model.setValueAt(depreciation.getCorporateName(), row, INDEX_CORPORATE_NAME);
                model.setValueAt(depreciation.getAreaCode(), row, INDEX_AREA_CODE);
            ignoreNextChanges = false;
                
            // Add new row
            addRow();
            
        // Update expense    
        } else {
            
            Integer idDepreciation = (Integer) model.getValueAt(row, INDEX_ID);
            Depreciation depreciation = financialData.getDepreciation(idDepreciation);
            
            // Update corporate id
            if (column==INDEX_CORPORATE_ID)
            {
                String siren = (String) model.getValueAt(row,column);
                depreciation.setCorporateId(siren);
                
                depreciation.fetchData();
                
                ignoreNextChanges = true; 
                    model.setValueAt(depreciation.getCorporateName(), row, INDEX_CORPORATE_NAME); 
                ignoreNextChanges = false;
            }
            
            // Override corporate name
            else if (column==INDEX_CORPORATE_NAME) 
            {
                String denomination = (String) model.getValueAt(row,column);
                depreciation.setCorporateName(denomination);
            }
            
            // Set depreciation amount
            else if (column==INDEX_AMOUNT)
            {
                Double value = readValue(model.getValueAt(row,column));
                if (value!=null) { 
                    depreciation.setAmount(value); 
                    financialData.updateAmountDepreciations();
                    etude.updateFootprints();
                    etude.save();
                }
            }
            
            // Remove depreciation
            else if (column==INDEX_DEL)
            {
                financialData.removeDepreciation(idDepreciation);
                etude.updateExpensesFootprint();
                etude.save();
                updateContent();
            }
        }
    }
    
    @Override
    public void updateContent() {
        
        model.setRowCount(0);
        ArrayList<Depreciation> depreciations = financialData.getDepreciations();
        
        ignoreNextChanges = true;
        
        depreciations.stream().map((depreciation) -> 
        {
            Object[] data = new Object[NB_COLUMNS];
                data[INDEX_ID] = depreciation.getId();
                data[INDEX_CORPORATE_ID] = depreciation.getCorporateId();
                data[INDEX_CORPORATE_NAME] = depreciation.getCorporateName();
                data[INDEX_YEAR] = depreciation.getYear();
                data[INDEX_AREA_CODE] = depreciation.getAreaCode();
                data[INDEX_CORPORATE_APE] = depreciation.getCorporateActivity();
                data[INDEX_AMOUNT] = depreciation.getAmount();
            return data;
        }).forEachOrdered((data) -> 
        {
            model.addRow(data);
        });
        
        addRow();
        
        ignoreNextChanges = false;
        
    }
    
    

}
