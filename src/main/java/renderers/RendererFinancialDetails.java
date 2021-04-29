/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package renderers;

import tables.TableFinancialDetails;
import tables.TableExpenses;
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
public class RendererFinancialDetails extends DefaultTableCellRenderer {
    
    private static final Color LIGHT_GREEN = new Color(230,240,200);
    private static final Color LIGHT_RED = new Color(240,200,200);
    private static final Color BASIC_GREY = new Color(100,100,100);
    
    protected DefaultTableModel model;
    protected TableFinancialDetails table;
    protected FinancialData financialData; 
    
    public RendererFinancialDetails(TableFinancialDetails table, FinancialData financialData) {
        super();
        this.table = table;
        this.model = (DefaultTableModel) table.getModel();
        this.financialData = financialData;
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object obj, boolean isSelected, boolean hasFocus, int row, int column) {

        Component cell = super.getTableCellRendererComponent(table, obj, isSelected, hasFocus, row, column);
        cell.setBackground(Color.white);
        
        Object cellValue = model.getValueAt(row, this.table.INDEX_AMOUNT);
                
        if (row<(model.getRowCount()-1)) {
            
            Integer id = (Integer) model.getValueAt(row, this.table.INDEX_ID);
            Expense expense;
            if (this.table instanceof TableExpenses) { expense = financialData.getExpense(id); }
            else { expense = (Expense) financialData.getDepreciation(id); }
            
            // Data fetched
            if (column==this.table.INDEX_CORPORATE_ID & expense.isDataFetched()) { 
                cell.setBackground(LIGHT_GREEN);
            }
            
            // Wrong amount
            if (column==this.table.INDEX_AMOUNT & readValue(cellValue)==null) {
                cell.setBackground(LIGHT_RED);
            }
            
        }
        
        return cell;
    }
    
}
