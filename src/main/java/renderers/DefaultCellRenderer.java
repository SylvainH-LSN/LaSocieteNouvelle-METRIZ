/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package renderers;

import components.CheckBox;
import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author SylvainPro
 */
public class DefaultCellRenderer extends DefaultTableCellRenderer {
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object obj, boolean isSelected, boolean hasFocus, int row, int column) {
        
        Component cell = super.getTableCellRendererComponent(table, obj, isSelected, hasFocus, row, column);
        
        cell.setBackground(Color.WHITE);
        if (hasFocus) {
            setBorder(BorderFactory.createLineBorder(new Color(100,100,100), 1));
        }
        
        if (obj instanceof Boolean) {
            cell = new CheckBox((Boolean) obj);
        }
        
        return cell;
    }
    
}
