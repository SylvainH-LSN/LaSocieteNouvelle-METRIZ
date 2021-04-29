/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tables;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author SylvainPro
 */

public class Table extends JTable {
    
    public Table(DefaultTableModel model) {
        
        super(model);
        
        UIManager.getDefaults().put("TableHeader.cellBorder" , BorderFactory.createEmptyBorder());
        
        JTableHeader header = getTableHeader();
        header.setBackground(new Color(100,100,100));
        header.setForeground(Color.white);
        //header.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        //setIntercellSpacing(new Dimension(0,0));
        
        setGridColor(new Color(100,100,100));
        
        //setBorder(BorderFactory.createLineBorder(new Color(100,100,100), 2));
        
    }
    
}
