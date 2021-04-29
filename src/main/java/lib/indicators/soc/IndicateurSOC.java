/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib.indicators.soc;

import meta.Flag;
import lib.indicators.IndicateurNetValueAdded;
import meta.Indic;

/**
 *
 * @author SylvainPro
 */

public class IndicateurSOC extends IndicateurNetValueAdded {
    
    private Boolean hasSocialPurpose = false;
    
    public IndicateurSOC() {
        super();
        this.indic = Indic.SOC;
    }
    
    /* --------- Setters --------- */
    
    public void setHasSocialPurpose(Boolean hasSocialPurpose) {
        this.hasSocialPurpose = hasSocialPurpose;
        quality = hasSocialPurpose ? 100.0 : 0.0;
        uncertainty = 0.0;
    }
    
    /* --------- Getters --------- */
    
    public Boolean getHasSocialPurpose() {
        return hasSocialPurpose;
    }
    
    /* --------- Update --------- */
    
    @Override
    public void updateValue(Double netValueAdded) {
        if (netValueAdded!=null & hasSocialPurpose!=null) {
            quality = hasSocialPurpose ? 100.0 : 0.0;
            flag = Flag.PUBLICATION;
            uncertainty = 0.0;
        } else {
            quality = null;
            flag = Flag.UNDEFINED;
            uncertainty = null;
        }
    }
    
    /* --------- Override --------- */
    
    @Override
    public Double getUncertainty() {
        if (quality!=null) {
            return 0.0;
        } else {
            return null;
        }
    }

}
