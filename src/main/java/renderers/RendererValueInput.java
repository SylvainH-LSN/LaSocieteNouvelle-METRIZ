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
import java.util.stream.IntStream;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import static utils.Utils.readValue;

/**
 *
 * @author SylvainPro
 */
public class RendererValueInput extends DefaultCellRenderer {
    
    static final ImageIcon icon_refresh = new ImageIcon("src/main/lib/icon_refresh.png");
    
    private int precision;
    private int[] outputRows = {};
    
    public RendererValueInput(int precision) {
        super();
        this.precision = precision;
        setHorizontalAlignment(JLabel.RIGHT);
    }
    
    public RendererValueInput(int precision, int[] outputRows) {
        super();
        this.precision = precision;
        this.outputRows = outputRows;
        setHorizontalAlignment(JLabel.RIGHT);
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object obj, boolean isSelected, boolean hasFocus, int row, int column) {
                
        Component cell;// = super.getTableCellRendererComponent(table, obj, isSelected, hasFocus, row, column);
        Color color = Color.WHITE;
        
        if (obj!=null) {
            if (readValue(obj)!= null) {
                if (precision==0) {
                    obj = (int) round(readValue(obj));
                } else {
                    obj = (double) round(readValue(obj)*pow(10,precision))/pow(10,precision);
                }
            } else if (!obj.toString().equals("") & !(obj instanceof Boolean)) {
                color = Color.RED;
            }
        }
        
        if (IntStream.of(outputRows).anyMatch(outputRow -> outputRow == row)) {
            color = new Color(225,225,225);
        } 
        
        cell = super.getTableCellRendererComponent(table, obj, isSelected, hasFocus, row, column);
        cell.setBackground(color);
        return cell;
    }
    
    public void setOutputRows(int[] outputRows) {
        this.outputRows = outputRows;
    }
    
}
