/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tables;

import etude.Etude;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import renderers.DefaultCellRenderer;

/**
 *
 * @author SylvainPro
 */
public class TableUniteLegale extends Table {
    
    private static final String[] ENTETE = {"Libelle","Donnée"};
    
    private Etude etude;
    
    public TableUniteLegale(Etude etude) {
        super(new DefaultTableModel(buildData(etude),ENTETE));
        
        this.etude = etude;
        
        getColumnModel().getColumn(0).setCellRenderer(new DefaultCellRenderer());
        getColumnModel().getColumn(1).setCellRenderer(new DefaultCellRenderer());
        
    }
    
    private static Object[][] buildData(Etude etude) {
        
        Object[][] donnees = new Object[3][2];
        
        donnees[0][0] = "Denomination";
        donnees[0][1] = etude.getUniteLegale().getDenomination();
        
        donnees[1][0] = "siren";
        donnees[1][1] = etude.getUniteLegale().getSiren();
        
        donnees[2][0] = "Année de fin de l'exercice";
        donnees[2][1] = etude.getUniteLegale().getFinPeriode();
                
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
        
        int column = e.getColumn();
        int row = e.getFirstRow();

        DefaultTableModel model = (DefaultTableModel)e.getSource();
        String value = (String) model.getValueAt(row, column);

        if (value!=null) {

            switch(row) {
                case 0: etude.getUniteLegale().setDenomination(value); break;
                case 1: etude.getUniteLegale().setSiren(value); break;
                case 2: etude.getUniteLegale().setFinPeriode(value); break;
                default: break;
            }
            etude.save();
        }
    }
    
}
