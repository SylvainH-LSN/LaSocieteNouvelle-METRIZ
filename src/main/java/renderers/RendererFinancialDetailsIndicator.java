/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package renderers;

import tables.TableFinancialDetailsIndicator;
import tables.TableExpensesIndicateur;
import etude.Expense;
import etude.FinancialData;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import static utils.Utils.readValue;

/**
 *
 * @author SylvainPro
 */
public class RendererFinancialDetailsIndicator extends DefaultTableCellRenderer {
    
    protected static final Integer INDEX_CORPORATE_ID = 0;
    protected static final Integer INDEX_CORPORATE_NAME = 1;
    protected static final Integer INDEX_AMOUNT = 2;
    protected static final Integer INDEX_INDICATEUR_VALUE = 3;
    protected static final Integer INDEX_INDICATEUR_FLAG = 4;
    
    protected static final Integer INDEX_SYNC = 5;
    protected static final Integer INDEX_ID = 6;
    
    protected static final Integer NB_COLUMNS = 7;
    
    protected DefaultTableModel model;
    protected TableFinancialDetailsIndicator table;
    protected FinancialData financialData; 
    
    public RendererFinancialDetailsIndicator(TableFinancialDetailsIndicator table, FinancialData financialData) {
        super();
        this.table = table;
        this.model = (DefaultTableModel) table.getModel();
        this.financialData = financialData;
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object obj, boolean isSelected, boolean hasFocus, int row, int column) {

        Component cell = super.getTableCellRendererComponent(table, obj, isSelected, hasFocus, row, column);
        
        Object cellValue = model.getValueAt(row, INDEX_INDICATEUR_VALUE);
        
        // if no value all the line is white
        if (cellValue==null | cellValue.toString().equals("") | row==model.getRowCount()) {
            cell.setBackground(Color.white);
        
        // if the value is wrong (just the cell is red)
        } else if (readValue(cellValue)==null) {
            if (column==INDEX_INDICATEUR_VALUE) {
                cell.setBackground(Color.red);
            } else {
                cell.setBackground(Color.white);
            }
            
        // Other cases
        } else {
            
            Integer id = (Integer) model.getValueAt(row, INDEX_ID);
            Expense expense;
            if (table instanceof TableExpensesIndicateur) { expense = financialData.getExpense(id); }
            else { expense = (Expense) financialData.getDepreciation(id); }
            
            if (expense.isDataFetched()) {
                cell.setBackground(Color.green);
            } else {
                cell.setBackground(Color.white);
            }
        }
        
        return cell;
    }
    
}
