/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tables;

import renderers.RendererFinancialDetails;
import renderers.RendererIcon;
import etude.Etude;
import etude.Expense;
import etude.FinancialData;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SylvainPro
 */

public abstract class TableFinancialDetails extends Table implements MouseListener {
        
    // Index columns 
    public int INDEX_CORPORATE_ID;
    public int INDEX_CORPORATE_NAME;
    public int INDEX_AREA_CODE;
    public int INDEX_CORPORATE_APE;
    public int INDEX_AMOUNT;
    public int INDEX_YEAR;
    public int INDEX_SYNC;
    public int INDEX_DEL;
    
    public Integer INDEX_ID;
    
    public Integer NB_COLUMNS;
    
    protected DefaultTableModel model;
    protected Boolean ignoreNextChanges = false;
        
    protected Etude etude;
    protected FinancialData financialData;
    
    
    /* ---------- Constructor ---------- */
    
    public TableFinancialDetails(DefaultTableModel model, Etude etude) {
        
        super(model);
        this.model = model;
        
        this.etude = etude;
        this.financialData = etude.getFinancialData();
        
        setIndexColumns();
        
        setRenderer();
        
        setCellEditors();
        setSizes();
                
        // Hide id column
        getColumnModel().removeColumn(this.getColumnModel().getColumn(INDEX_ID));
        
        addMouseListener(this);
    }
    
    abstract void setIndexColumns();
    
    private void setRenderer() {
        RendererFinancialDetails renderer = new RendererFinancialDetails(this,financialData);
        setDefaultRenderer(Object.class, renderer);
        getColumnModel().getColumn(INDEX_SYNC).setCellRenderer(new RendererIcon(RendererIcon.REFRESH));
        getColumnModel().getColumn(INDEX_DEL).setCellRenderer(new RendererIcon(RendererIcon.DELETE));
    }
    
    private void setCellEditors() {
        getColumnModel().getColumn(INDEX_ID).setCellEditor(this.getDefaultEditor(Integer.class));
        getColumnModel().getColumn(INDEX_SYNC).setCellEditor(this.getDefaultEditor(ImageIcon.class));
    }
    
    private void setSizes() {
        getColumnModel().getColumn(INDEX_ID).setPreferredWidth(50);
        getColumnModel().getColumn(INDEX_CORPORATE_NAME).setPreferredWidth(200);
        getColumnModel().getColumn(INDEX_CORPORATE_APE).setPreferredWidth(100);
        getColumnModel().getColumn(INDEX_AREA_CODE).setPreferredWidth(25);
        getColumnModel().getColumn(INDEX_AMOUNT).setPreferredWidth(50);
        getColumnModel().getColumn(INDEX_SYNC).setMaxWidth(20);
        getColumnModel().getColumn(INDEX_DEL).setMaxWidth(20);
    }
    
    /* ---------- Common Methods ---------- */
    
    abstract void updateContent();

    protected void addRow() {
        model.addRow(new Object[NB_COLUMNS]);
    }
    
    /* ---------- JTable Methods ---------- */
    
    @Override
    public Class getColumnClass(int column) {
        return (column == INDEX_SYNC) ? Icon.class : Object.class;
      }
    
    @Override
    public boolean isCellEditable(int row, int column) {
        return column==INDEX_CORPORATE_ID |
               ((column==INDEX_CORPORATE_NAME | column==INDEX_AMOUNT | column==INDEX_AREA_CODE | column==INDEX_CORPORATE_APE) 
                    & row<(model.getRowCount()-1));
    }
    
    
    /* ---------- Table Methods ---------- */
    
    @Override
    public void tableChanged(TableModelEvent e) {
        super.tableChanged(e);
        if (e.getType()==TableModelEvent.UPDATE & e.getFirstRow()>=0) 
        {
            if (!ignoreNextChanges) {
                handleChanged(e);
                etude.save();
            }
        } 
    }
    
    abstract void handleChanged(TableModelEvent e);
   
    /* ---------- Mouse Listener ---------- */
    
    @Override
    public void mouseClicked(MouseEvent e) {
        
        int row = rowAtPoint(e.getPoint());
        int col = columnAtPoint(e.getPoint());
                
        if (col==INDEX_SYNC & row<(getRowCount()-1)) 
        {
            Integer id = (Integer) model.getValueAt(row, INDEX_ID);
            Expense expense;
            if (this instanceof TableExpenses) { expense = financialData.getExpense(id); }
            else {expense = (Expense) financialData.getDepreciation(id); }
            expense.fetchData();
        }
        else if (col==INDEX_DEL & row<(getRowCount()-1))
        {
            Integer id = (Integer) model.getValueAt(row, INDEX_ID);
            if (this instanceof TableExpenses) { financialData.removeExpense(id); }
            else { financialData.removeDepreciation(id); }
        }
        etude.save();
        updateContent();
    }

    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    
    
}
