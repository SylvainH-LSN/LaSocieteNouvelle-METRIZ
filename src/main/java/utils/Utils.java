/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;


/**
 *
 * @author SylvainPro
 */
public class Utils {
    
   
    // read Double from object
    public static Double readValue(Object object) {
        
        // Case already Double
        if (object instanceof Double) { return (Double) object; }
        
        // Case object null
        else if (object==null) { return null; }
            
        // Case empty string
        else if (object.toString().equals("")) { return null; }
        
        // Try parsing
        else {
            Double value;
            try {
                value = Double.valueOf(object.toString());
            } catch (java.lang.NumberFormatException e) {
                value = null;
            }
            return value;
        }
    }
    
    // Show value
    public static Object showValue (Double value) {
        if (value!=null) {
            if (value>=0) {
                return round(value);
            } else {
                return "("+round(value)+")";
            }
        } else {
            return "";
        }
    }
    
    private static Double round(Double value) {
        return (double) Math.round(value*100)/100;
    }
    
    private static Object[][] sortTable (Object[][] table,Integer indexTri) {
        int sizeTable = table.length;
        for(int i = 0; i<sizeTable-1; i++) {
            if (table[i][indexTri] instanceof String) {
                for (int j = i+1; j<sizeTable; j++) {
                    Object[] line = table[i];
                    table[i] = table[j];
                    table[j] = line;
                }
            } else {
                for (int j = i+1; j<sizeTable; j++) {
                    if (!(table[j][indexTri] instanceof String)) {
                        if( ((Double)table[i][indexTri])<((Double)table[j][indexTri]) ) {
                        //if( ( (100-(Double)table[i][indexTri])*((Double)table[i][indexTri-1]) ) < ( (100-(Double)table[j][indexTri])*((Double)table[j][indexTri-1])) ) {    
                            Object[] line = table[i];
                            table[i] = table[j];
                            table[j] = line;
                        }
                    }
                }
            }
        }
        return table;
    }
    
}
