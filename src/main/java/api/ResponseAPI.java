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
public class ResponseAPI {
    
    private Boolean isValid;
    
    private JSONObject jsonResponse;
    
    public ResponseAPI() {
        isValid = false;
    }
    
    public void buildResponseAPI(JSONObject json) {
        jsonResponse = json;
        isValid = json.getJSONObject("header").getInt("statut")==200;
    }
    
    public Boolean isValid() {
        return isValid;
    }
    
    public String getDenomination() {
        return jsonResponse.getJSONObject("profil").getJSONObject("descriptionUniteLegale").getString("denomination");
    }
    
    public String getEconomicBranch() {
        return jsonResponse.getJSONObject("profil").getJSONObject("descriptionUniteLegale").getString("activitePrincipale").substring(0,2);
    }
    
    /* ---------- Getters for CSF data ---------- */
    
    public Double getValue(Indic indic) {
        return jsonResponse.getJSONObject("profil").getJSONObject("empreinteSocietale").getJSONObject(indic.getCode()).getDouble("value");
    }
    
    public String getFlagCode(Indic indic) {
        return jsonResponse.getJSONObject("profil").getJSONObject("empreinteSocietale").getJSONObject(indic.getCode()).getString("flag");
    }
    
    public Double getUncertainty(Indic indic) {
        return jsonResponse.getJSONObject("profil").getJSONObject("empreinteSocietale").getJSONObject(indic.getCode()).getDouble("uncertainty");
    }
    
}
