/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tables;

import etude.Etude;
import etude.Expense;
import meta.Indic;
import java.util.ArrayList;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import static utils.Utils.readValue;

/**
 *
 * @author SylvainPro
 */

public class TableExpensesIndicateur extends TableFinancialDetailsIndicator {
    
    private static final String[] HEADER = {"Identifiant","Fournisseur","Montant (en €)","Qualité (en %)","Flag.","Incertitude (en %)","","Id"};
    
    public TableExpensesIndicateur(Indic indic, Etude etude) {
        super(new DefaultTableModel(new Object[0][HEADER.length],HEADER), indic, etude);
        updateContent();
    }
    
    @Override
    protected void setColumnsIndex() {
        INDEX_CORPORATE_ID = 0;
        INDEX_CORPORATE_NAME = 1;
        INDEX_AMOUNT = 2;
        INDEX_INDICATEUR_VALUE = 3;
        INDEX_INDICATEUR_FLAG = 4;
        INDEX_UNCERTAINTY = 5;
        INDEX_SYNC = 6;
        INDEX_ID = 7;
        NB_COLUMNS = HEADER.length;
    }
    
    @Override
     void handleChanged(TableModelEvent e) {
            
        int column = e.getColumn();
        int row = e.getFirstRow();
        
        Integer idExpense = (Integer) model.getValueAt(row, INDEX_ID);
        Expense expense = financialData.getExpense(idExpense);
        
        Double value = readValue(model.getValueAt(row,column));
        if (value!=null) {
            expense.getFootprint().getIndicateur(indic).setQuality(value);
            etude.updateExpensesFootprint(indic);
        }
        
    }
    
    @Override
    public void updateContent() {
        
        model.setRowCount(0);
        ArrayList<Expense> expenses = financialData.getExpenses();
        
        ignoreNextChanges = true;
        
        expenses.stream().map((expense) -> {
            Object[] data = new Object[NB_COLUMNS];
            data[INDEX_ID] = expense.getId();
            data[INDEX_CORPORATE_ID] = expense.getCorporateId();
            data[INDEX_CORPORATE_NAME] = expense.getCorporateName();
            data[INDEX_AMOUNT] = expense.getAmount();
            data[INDEX_INDICATEUR_VALUE] = expense.getFootprint().getIndicateur(indic).getValue();
            data[INDEX_INDICATEUR_FLAG] = expense.getFootprint().getIndicateur(indic).getFlag().getCode();
            data[INDEX_UNCERTAINTY] = expense.getFootprint().getIndicateur(indic).getUncertainty();
            return data;
        }).forEachOrdered((data) -> {
            model.addRow(data);
        });
        
        ignoreNextChanges = false;
         
    }

}
