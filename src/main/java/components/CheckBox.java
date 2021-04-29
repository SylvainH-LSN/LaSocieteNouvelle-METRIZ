/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;

/**
 *
 * @author SylvainPro
 */
public class CheckBox extends JCheckBox {
    
    public CheckBox(Boolean state) {
        super();
                
        setSelected(state);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(new Color(100,100,100), 1));
        setOpaque(true);
        setHorizontalAlignment(CENTER);
    }
    
}
