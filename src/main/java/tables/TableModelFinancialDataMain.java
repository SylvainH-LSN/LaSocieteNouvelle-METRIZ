/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tables;

import etude.FinancialData;
import javax.swing.table.DefaultTableModel;
import static utils.Utils.readValue;
import static utils.Utils.showValue;

/**
 *
 * @author SylvainPro
 */
public class TableModelFinancialDataMain extends DefaultTableModel {
    
    private static final String[] ENTETE = {"","Montant (en €)"};
        
    private final FinancialData donneesFinancieres;
    
    public TableModelFinancialDataMain(FinancialData donneesFinancieres) {
        
        super(buildContent(donneesFinancieres),ENTETE);
        this.donneesFinancieres = donneesFinancieres;
        
    }
    
    private static Object[][] buildContent(FinancialData donneesFinancieres) {
        
        Object[][] donnees = new Object[7][2];
        
        donnees[0][0] = "Production";
        donnees[0][1] = showValue(donneesFinancieres.getProduction());
        donnees[1][0] = "Charges externes";
        donnees[1][1] = showValue(donneesFinancieres.getAmountExpenses());
        donnees[2][0] = "Dotations aux amortissements sur immobilisations";
        donnees[2][1] = showValue(donneesFinancieres.getAmountDepreciations());
        donnees[3][0] = "Valeur Ajoutée Nette";
        donnees[3][1] = showValue(donneesFinancieres.getNetValueAdded());
                
        return donnees;
    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
        return column!=0;
    }
    
    @Override
    public void setValueAt(Object value, int row, int column) {
        
        super.setValueAt(value, row, column);
        
        if (column==1) {
            
            Double amount = readValue(value);
            if (amount!=null) {
                switch (row) {
                    
                    case 0: donneesFinancieres.setProduction(amount); break;
                    case 4: donneesFinancieres.setAmountExpenses(amount); break;
                    case 5: donneesFinancieres.setAmountDepreciations(amount); break;
                    case 6: donneesFinancieres.setNetValueAdded(amount); break;
                        
                    default:
                        break;
                }
            }
        }
    }
    
    
    
}
