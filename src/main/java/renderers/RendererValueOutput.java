/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package renderers;

import java.awt.Color;
import java.awt.Component;
import static java.lang.Math.pow;
import static java.lang.Math.round;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import static utils.Utils.readValue;

/**
 *
 * @author SylvainPro
 */
public class RendererValueOutput extends DefaultCellRenderer {
    
    static final ImageIcon icon_refresh = new ImageIcon("src/main/lib/icon_refresh.png");
    
    private int precision;
    
    public RendererValueOutput(int precision) {
        super();
        this.precision = precision;
        setHorizontalAlignment(JLabel.RIGHT);
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object obj, boolean isSelected, boolean hasFocus, int row, int column) {
                
        Component cell;
        Color color = new Color(225,225,225);
        
        if (obj!=null) {
            if (readValue(obj)!= null) {
                if (precision==0) {
                    obj = (int) round(readValue(obj));
                } else {
                    obj = (double) round(readValue(obj)*pow(10,precision))/pow(10,precision);
                }
            }
        }
        
        cell = super.getTableCellRendererComponent(table, obj, isSelected, hasFocus, row, column);
        cell.setBackground(color);
        return cell;
    }
    
}
