/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tables;

import renderers.DefaultCellRenderer;
import renderers.RendererValueInput;
import etude.Etude;
import etude.FinancialData;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import static utils.Utils.readValue;

/**
 *
 * @author SylvainPro
 */

public class TableMainFinancialData extends Table {
    
    private static final String[] ENTETE = {"","Montant (en €)"};
    
    private final Etude etude;
    private Boolean ignoreChange = false;
    
    public TableMainFinancialData(Etude etude) {
        
        super(new DefaultTableModel(buildData(etude.getFinancialData()),ENTETE));
                
        this.etude = etude;

        getColumnModel().getColumn(0).setCellRenderer(new DefaultCellRenderer());
        getColumnModel().getColumn(1).setCellRenderer(new RendererValueInput(0, new int[]{3}));
        
        getColumnModel().getColumn(0).setPreferredWidth(300);
        getColumnModel().getColumn(1).setPreferredWidth(100);
        
    }
    
    private static Object[][] buildData(FinancialData donneesFinancieres) {
        
        Object[][] donnees = new Object[4][2];
        
        donnees[0][0] = "Production";
        donnees[0][1] = donneesFinancieres.getProduction();
        donnees[1][0] = "Charges externes";
        donnees[1][1] = donneesFinancieres.getAmountExpenses();
        donnees[2][0] = "Dotations aux amortissements sur immobilisations";
        donnees[2][1] = donneesFinancieres.getAmountDepreciations();
        donnees[3][0] = "Valeur Ajoutée Nette";
        donnees[3][1] = donneesFinancieres.getNetValueAdded();
                
        return donnees;
    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
        return column!=0;
    }
    
    @Override
    public void tableChanged(TableModelEvent e) {
        super.tableChanged(e);
        
        if (e.getType()==TableModelEvent.UPDATE & e.getFirstRow()>=0) {
            updateEtude(e);
        } 
        
    }
    
    private void updateEtude(TableModelEvent e) {
        
        if (!ignoreChange) {
            
            int column = e.getColumn();
            int row = e.getFirstRow();
            
            DefaultTableModel model = (DefaultTableModel)e.getSource();
            Double value = readValue(model.getValueAt(row, column));
            
            if (value!=null) {
                
                switch(row) {
                    case 0: etude.getFinancialData().setProduction(value); break;
                    case 1: etude.getFinancialData().setAmountExpenses(value); break;
                    case 2: etude.getFinancialData().setAmountDepreciations(value); break;
                    case 3: etude.getFinancialData().setNetValueAdded(value); break;
                    default: break;
                }
                etude.updateFootprints();
                etude.save();
                updateContent();
                
            }
        } 
    }
    
    public void updateContent() {
        
        ignoreChange = true;
        setValueAt(etude.getFinancialData().getProduction(),0,1);
        setValueAt(etude.getFinancialData().getAmountExpenses(),1,1);
        setValueAt(etude.getFinancialData().getAmountDepreciations(),2,1);
        setValueAt(etude.getFinancialData().getNetValueAdded(),3,1);
        ignoreChange = false;
        
    }
    
}
