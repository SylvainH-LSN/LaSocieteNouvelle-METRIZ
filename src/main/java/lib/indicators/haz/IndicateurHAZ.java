/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib.indicators.haz;

import meta.Flag;
import lib.indicators.IndicateurNetValueAdded;
import meta.Indic;

/**
 *
 * @author SylvainPro
 */

public class IndicateurHAZ extends IndicateurNetValueAdded {
    
    private Double dangerousProductsUse;
    
    public IndicateurHAZ() {
        super();
        this.indic = Indic.HAZ;
    }
    
    /* ---------- Setters ---------- */
    
    public void setHazard(Double value) {
        dangerousProductsUse = value;
        
        if (dangerousProductsUse!=null) {
            if (dangerousProductsUse == 0.0) { uncertainty = 0.0; } 
            else { uncertainty = 50.0; }
        } else {
            uncertainty = null;
        }
    }

    /* ---------- Getters ---------- */
    
    public Double getDangerousProductsUse() {
        return dangerousProductsUse;
    }
    
    /* ---------- Update ---------- */
    
    @Override
    public void updateValue(Double netValueAdded) {
        if (netValueAdded!=null & dangerousProductsUse!=null) {
            quality = dangerousProductsUse*1000/netValueAdded;
            flag = Flag.PUBLICATION;
        } else {
            quality = null;
            flag = Flag.UNDEFINED;
            uncertainty = null;
        }
    }
    
}
