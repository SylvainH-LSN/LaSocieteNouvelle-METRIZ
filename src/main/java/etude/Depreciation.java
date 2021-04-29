/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etude;

import etude.Expense;

/**
 *
 * @author SylvainPro
 */
public class Depreciation extends Expense {
    
    private String year;
    
    public Depreciation(Integer id, String siren) {
        super(id,siren);
        this.year = "";
    }
    
    public Depreciation() {}
    
    public void setYear(String year) {
        this.year = year;
    }
    
    public String getYear() {
        return year;
    }
    
    /* ---------- JSON ---------- */
    /*
    public JSONObject getJSON() throws IOException {
        JSONObject jsonDepreciation = super.getJSON();
        
        jsonDepreciation.put("year", year);
        
        return jsonDepreciation;
    }

    public Depreciation(JSONObject jsonObject) throws IOException, ClassNotFoundException {
        
        super(jsonObject);
        year = jsonObject.getString("year");
        
    }
    */ 
}
