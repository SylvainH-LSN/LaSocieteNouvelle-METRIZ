/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tables;

import renderers.DefaultCellRenderer;
import renderers.RendererValueInput;
import renderers.RendererIcon;
import renderers.RendererValueOutput;
import etude.Depreciation;
import etude.Etude;
import etude.Expense;
import etude.FinancialData;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import meta.Indic;

/**
 *
 * @author SylvainPro
 */
public abstract class TableFinancialDetailsIndicator extends Table implements MouseListener  {

    // Index columns
    protected int INDEX_CORPORATE_ID;
    protected int INDEX_CORPORATE_NAME;
    protected int INDEX_AMOUNT;
    protected int INDEX_INDICATEUR_VALUE;
    protected int INDEX_INDICATEUR_FLAG;
    protected int INDEX_UNCERTAINTY;
    protected int INDEX_YEAR;
    protected int INDEX_SYNC;
    protected int INDEX_ID;
    protected int NB_COLUMNS;
    
    // Fonctionnal element
    protected DefaultTableModel model;
    protected Boolean ignoreNextChanges = false;
    
    // Data
    protected Indic indic;
    protected Etude etude;
    protected FinancialData financialData;
    
    /* ---------- Constrcutor ---------- */
    
    public TableFinancialDetailsIndicator(DefaultTableModel model, Indic indic, Etude etude) {
        super(model);
        
        this.model = model;
        this.indic = indic;
        this.etude = etude;
        this.financialData = etude.getFinancialData();
        
        setColumnsIndex();
        setRenderers();
        setCellEditors();
        setSizes();
        
        getColumnModel().removeColumn(this.getColumnModel().getColumn(INDEX_ID));
        addMouseListener(this);
    }
    
    abstract protected void setColumnsIndex();
    
    private void setRenderers() {
        getColumnModel().getColumn(INDEX_CORPORATE_ID).setCellRenderer(new DefaultCellRenderer());
        getColumnModel().getColumn(INDEX_CORPORATE_NAME).setCellRenderer(new DefaultCellRenderer());
        getColumnModel().getColumn(INDEX_AMOUNT).setCellRenderer(new RendererValueOutput(0));
        getColumnModel().getColumn(INDEX_INDICATEUR_VALUE).setCellRenderer(new RendererValueInput(0));
        getColumnModel().getColumn(INDEX_INDICATEUR_FLAG).setCellRenderer(new RendererValueOutput(0));
        getColumnModel().getColumn(INDEX_UNCERTAINTY).setCellRenderer(new RendererValueInput(0));
        getColumnModel().getColumn(INDEX_SYNC).setCellRenderer(new RendererIcon(RendererIcon.REFRESH));
    }
    
    private void setCellEditors() {
        getColumnModel().getColumn(INDEX_INDICATEUR_VALUE).setCellEditor(this.getDefaultEditor(Double.class));
    }
    
    private void setSizes() {
        getColumnModel().getColumn(INDEX_CORPORATE_ID).setPreferredWidth(50);
        getColumnModel().getColumn(INDEX_CORPORATE_NAME).setPreferredWidth(200);
        getColumnModel().getColumn(INDEX_AMOUNT).setPreferredWidth(50);
        getColumnModel().getColumn(INDEX_INDICATEUR_VALUE).setPreferredWidth(50);
        getColumnModel().getColumn(INDEX_INDICATEUR_FLAG).setPreferredWidth(25);
        getColumnModel().getColumn(INDEX_UNCERTAINTY).setPreferredWidth(50);
        getColumnModel().getColumn(INDEX_SYNC).setMaxWidth(20);
    }
    
    /* ---------- Common Methods ---------- */
    
    abstract void updateContent();
    
    abstract void handleChanged(TableModelEvent e);
    
    /* ---------- JTable Methods ---------- */
    
    @Override
    public boolean isCellEditable(int row, int column) {
        return column==INDEX_INDICATEUR_VALUE
                | column==INDEX_UNCERTAINTY;
    }
    
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
    
    @Override
    public Class<?> getColumnClass(int column) {
        if (column==INDEX_SYNC) {
            return ImageIcon.class;
        } else {
            return super.getColumnClass(column);
        }
    }
    
    /* ---------- Mouse Listener ---------- */
    
    @Override
    public void mouseClicked(MouseEvent e) {
        
        int row = rowAtPoint(e.getPoint());
        int col = columnAtPoint(e.getPoint());
            
        if (col==INDEX_SYNC) {
            
            Integer id = (Integer) model.getValueAt(row, INDEX_ID);
            Expense expense;
            if (this instanceof TableExpensesIndicateur) { expense = financialData.getExpense(id); }
            else { expense = (Depreciation) financialData.getDepreciation(id); }
                
            expense.fetchCSFdata(indic);
                
            ignoreNextChanges = true;
                setValueAt(expense.getFootprint().getIndicateur(indic).getValue(), row, INDEX_INDICATEUR_VALUE);
                setValueAt(expense.getFootprint().getIndicateur(indic).getFlag().getCode(), row, INDEX_INDICATEUR_FLAG);
            ignoreNextChanges = false;
        }
        
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
