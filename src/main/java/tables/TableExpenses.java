/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tables;

import etude.Etude;
import etude.Expense;
import java.util.ArrayList;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import meta.EconomicBranch;
import static utils.Utils.readValue;

/**
 *
 * @author SylvainPro
 */
public class TableExpenses extends TableFinancialDetails {
    
    private static final String[] HEADER = {"Identifiant","Fournisseur","Zone","Code Activite","Montant","","","Id"};
        
    /* ---------- Constructor ---------- */
    
    public TableExpenses(Etude etude) {
        super(new DefaultTableModel(new Object[0][HEADER.length],HEADER), etude);
        updateContent();
    }
    
    @Override
    void setIndexColumns() {
        INDEX_CORPORATE_ID = 0;
        INDEX_CORPORATE_NAME = 1;
        INDEX_AREA_CODE = 2;
        INDEX_CORPORATE_APE = 3;
        INDEX_AMOUNT = 4;
        INDEX_SYNC = 5;
        INDEX_DEL = 6;
        INDEX_ID = 7;
        NB_COLUMNS = HEADER.length;
    }

    @Override
     void handleChanged(TableModelEvent e) {
        
        int column = e.getColumn();
        int row = e.getFirstRow();
            
        // Add new expense
        if ((row+1)==model.getRowCount()) {
            
            String siren = (String) model.getValueAt(row,INDEX_CORPORATE_ID);
            Expense expense = financialData.addExpense(siren);
            
            ignoreNextChanges = true;
                model.setValueAt(expense.getId(), row, INDEX_ID);
                model.setValueAt(expense.getCorporateName(), row, INDEX_CORPORATE_NAME);
                model.setValueAt(expense.getAreaCode(), row, INDEX_AREA_CODE);
            ignoreNextChanges = false;
                
            // Add new row
            addRow();
                
        // Update expense    
        } else {
                
            Integer idExpense = (Integer) model.getValueAt(row, INDEX_ID);
            Expense expense = financialData.getExpense(idExpense);
            
            // Update corporate id
            if (column==INDEX_CORPORATE_ID)
            {
                String siren = (String) model.getValueAt(row,column);
                expense.setCorporateId(siren);
                
                expense.fetchData();
                ignoreNextChanges = true; 
                    model.setValueAt(expense.getCorporateName(), row, INDEX_CORPORATE_NAME);
                    model.setValueAt(expense.getAreaCode(), row, INDEX_AREA_CODE);
                    model.setValueAt(expense.getCorporateActivity(), row, INDEX_CORPORATE_APE); 
                ignoreNextChanges = false;
            }
            
            // Override corporate name
            else if (column==INDEX_CORPORATE_NAME) 
            {
                String denomination = (String) model.getValueAt(row,column);
                expense.setCorporateName(denomination);
            }
            
            // Override corporate name
            else if (column==INDEX_CORPORATE_APE) 
            {
                String activity = (String) model.getValueAt(row,column);
                if (EconomicBranch.isCodeCorrect(activity)) {
                    expense.setCorporateName(activity);
                }
            }
            
            // set expense amount
            else if (column==INDEX_AMOUNT)
            {
                Double value = readValue(model.getValueAt(row,column));
                if (value!=null) { 
                    expense.setAmount(value); 
                    financialData.updateAmountExpenses();
                    etude.updateFootprints();
                    etude.save();
                }
            }
            
            // Remove expense
            else if (column==INDEX_DEL)
            {
                financialData.removeExpense(idExpense);
                etude.updateExpensesFootprint();
                etude.save();
                updateContent();
            }
        }
    }
     
    @Override
    public void updateContent() {
        
        model.setRowCount(0);
        ArrayList<Expense> expenses = financialData.getExpenses();
        
        ignoreNextChanges = true;
        
        // Build data row
        expenses.stream().map((expense) -> {
            Object[] data = new Object[NB_COLUMNS];
            data[INDEX_ID] = expense.getId();
            data[INDEX_CORPORATE_ID] = expense.getCorporateId();
            data[INDEX_CORPORATE_NAME] = expense.getCorporateName();
            data[INDEX_AREA_CODE] = expense.getAreaCode();
            data[INDEX_CORPORATE_APE] = expense.getCorporateActivity();
            data[INDEX_AMOUNT] = expense.getAmount();
            return data;
        // Add row
        }).forEachOrdered((data) -> {
            model.addRow(data);
        });
        
        // Adding empty row
        addRow();
        
        ignoreNextChanges = false;
        
    }

}
