/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etude;

import meta.Indic;
import api.ResponseAPI;
import api.ResponseDefaultData;
import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author SylvainPro
 */
public class SocialFootprint implements Serializable {
    
    static final long serialVersionUID = -1L;
    
    private final HashMap<Indic,Indicator> indicateurs;
    
    /* ---------- Constructor ---------- */
    
    // Default
    public SocialFootprint() {
        indicateurs = new HashMap<>();
        
        for (Indic indic : Indic.values()) {
            indicateurs.put(indic,new Indicator(indic));
        }
    }
    
    /* ---------- Updaters ---------- */

    // default data
    public void setDefaultData() {
        if (!Indic.isDefautDataSet) { Indic.fetchDefaultData(); }
        for (Indic indic : Indic.values()) {
            indicateurs.put(indic,new Indicator(indic));
        }
    }
    
    // default data
    public void setDefaultData(ResponseDefaultData defaultData) {
        for (Indic indic : Indic.values()) {
            Indicator indicator = new Indicator(indic);
            indicator.update(defaultData);
            indicateurs.put(indic,indicator);
        }
    }
    
    // All indicators
    public void updateAll(ResponseAPI response) {
        for (Indic indic : Indic.values()) {
            indicateurs.get(indic).update(response);
        }
    }
    
    // Specific indicator
    public void update(Indic indic, ResponseAPI response) {
        indicateurs.get(indic).update(response);
    }
        
    /* --------- Getters ---------- */

    public Indicator getIndicateur(Indic indic) {
        return indicateurs.get(indic);
    }

    public HashMap<Indic, Indicator> getIndicateurs() {
        return indicateurs;
    }
    
    /* ---------- Merge footprints ---------- */
    
    public static SocialFootprint merge(Double amountA, SocialFootprint csfA, Double amountB, SocialFootprint csfB) {
        
        SocialFootprint newSocialFootprint = new SocialFootprint();
        Double amount = amountA + amountB;
        
        for (Indic indic : Indic.values()) 
        {
            Indicator newIndicateur = newSocialFootprint.getIndicateur(indic);
            Indicator indicateurA = csfA.getIndicateur(indic);
            Indicator indicateurB = csfB.getIndicateur(indic);
            
            Double value = (indicateurA.getValue()*amountA + indicateurB.getValue()*amountB) / amount;
            
            newIndicateur.setQuality(value);
        }
        return newSocialFootprint;
    }
    
}
