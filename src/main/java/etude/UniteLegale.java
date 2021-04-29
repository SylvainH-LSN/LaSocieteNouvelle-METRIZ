/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etude;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.json.JSONObject;

/**
 *
 * @author SylvainPro
 */
public class UniteLegale implements Serializable {
    
    static final long serialVersionUID = -1L;
    
    private String siren;
    private String denomination;
    private String debutPeriode;
    private String finPeriode;
    
    /* ---------- constructor ---------- */
    
    public UniteLegale(String siren) {
        this.siren = siren;
    }
    
    public UniteLegale() {}
    
    /* ----- SETTERS ----- */

    public void setSiren(String siren) {
        this.siren = siren;
    }
    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }
    public void setDebutPeriode(String debutPeriode) {
        this.debutPeriode = debutPeriode;
    }
    public void setFinPeriode(String finPeriode) {
        this.finPeriode = finPeriode;
    }
    
    /* ----- GETTERS ----- */

    public String getSiren() {
        return siren;
    }
    public String getDenomination() {
        return denomination;
    }
    public String getDebutPeriode() {
        return debutPeriode;
    }
    public String getFinPeriode() {
        return finPeriode;
    }
    
    /* ---------- JSON ---------- */
    
    @JsonIgnore
    public JSONObject getJSON() throws IOException {
        JSONObject jsonUniteLegale = new JSONObject();
        
        jsonUniteLegale.put("siren", siren);
        jsonUniteLegale.put("denomination", denomination);    
        jsonUniteLegale.put("debutPeriode", debutPeriode);
        jsonUniteLegale.put("finPeriode", finPeriode);
        
        return jsonUniteLegale;
    }

    public UniteLegale(JSONObject jsonObject) throws IOException, ClassNotFoundException {

        siren = jsonObject.getString("siren");
        denomination = jsonObject.getString("denomination");
        debutPeriode = jsonObject.getString("debutPeriode");
        finPeriode = jsonObject.getString("finPeriode");
        
    }
    
}
