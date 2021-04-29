/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package renderers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author SylvainPro
 */

public class RendererIcon extends DefaultTableCellRenderer {
    
    public static final int REFRESH = 0;
    public static final int DELETE = 1;
    
    static final ImageIcon icon_refresh = new ImageIcon("src/main/lib/icon_refresh.jpg");
    static final ImageIcon icon_delete = new ImageIcon("src/main/lib/icon_delete.png");
    
    private int icon;
    private Boolean exceptLastRow = false;
    
    public RendererIcon(int icon) {
        super();
        this.icon = icon;
    }
    
    public RendererIcon(int icon, Boolean exceptLastRow) {
        super();
        this.icon = icon;
        this.exceptLastRow = exceptLastRow;
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object obj, boolean isSelected, boolean hasFocus, int row, int column) {
        
        ImageIcon imageIcon;
        switch (icon) {
            case REFRESH: imageIcon = icon_refresh; break;
            case DELETE: imageIcon = icon_delete; break;
            default: imageIcon = icon_refresh; break; 
        }
        
        Image image = imageIcon.getImage().getScaledInstance(15, 15,  Image.SCALE_SMOOTH);
        if (!exceptLastRow | row<(table.getRowCount()-1)) {
            //obj = icon_refresh;
            setIcon(new ImageIcon(image));
            //obj = new JLabel(icon_refresh);
            //cell.setPreferredSize(new Dimension(5,4));
            //cell.setMaximumSize(new Dimension(5,5));
        }
        
        Component cell = super.getTableCellRendererComponent(table, obj, isSelected, hasFocus, row, column);
        cell.setBackground(Color.white);
        
        return cell;
    }
        
}
