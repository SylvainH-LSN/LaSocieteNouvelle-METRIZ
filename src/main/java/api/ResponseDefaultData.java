/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import meta.Indic;
import org.json.JSONObject;

/**
 *
 * @author SylvainPro
 */
public class ResponseDefaultData {
    
    private Boolean isValid;
    
    private JSONObject jsonResponse;
    
    public ResponseDefaultData() {
        isValid = false;
    }
    
    public void setJSON(JSONObject json) {
        jsonResponse = json;
        isValid = json.getJSONObject("header").getInt("statut")==200;
    }
    
    public Boolean isValid() {
        return isValid;
    }
    
    public Double getValueIndicateur(Indic indic) {
        return jsonResponse.getJSONObject("empreinteSocietale").getJSONObject(indic.getCode()).getDouble("value");
    }
    
    public Double getUncertaintyIndicateur(Indic indic) {
        return jsonResponse.getJSONObject("empreinteSocietale").getJSONObject(indic.getCode()).getDouble("uncertainty");
    }
    
    public String getFlagIndicateur(Indic indic) {
        return jsonResponse.getJSONObject("empreinteSocietale").getJSONObject(indic.getCode()).getString("flag");
    }
    
}
