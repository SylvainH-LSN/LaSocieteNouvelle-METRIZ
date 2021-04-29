/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib.indicators.art;

import meta.Flag;
import lib.indicators.IndicateurNetValueAdded;
import meta.Indic;

/**
 *
 * @author SylvainPro
 */
public class IndicateurART extends IndicateurNetValueAdded {
    
    private Double craftedProduction;
    private Boolean hasLabel;
    
    public IndicateurART() {
        super();
        this.indic = Indic.ART;
        this.hasLabel = false;
    }
    
    /* ---------- Setters ---------- */
    
    public void setHasLabel(Boolean hasLabel) {
        this.hasLabel = hasLabel;
    }
    
    public void setCraftedProduction(Double value) {
        craftedProduction = value;
    }    
    
    /* ---------- Getters ---------- */
    
    public Double getCraftedProduction() {
        return craftedProduction;
    }

    public Boolean getHasLabel() {
        return hasLabel;
    }
    
    /* ---------- Update ---------- */
    
    @Override
    public void updateValue(Double netValueAdded) {
        if (netValueAdded!=null & hasLabel) {
            quality = 100.0;
            flag = Flag.PUBLICATION;
        } else if (netValueAdded!=null & craftedProduction!=null) {
            quality = craftedProduction/netValueAdded*100;
            flag = Flag.PUBLICATION;
        } else {
            quality = null;
            flag = Flag.UNDEFINED;
        }
    }
    
    /* ---------- Ovveride ---------- */
    
    @Override
    public Double getUncertainty() {
        if (quality!=null) {
            return 0.0;
        } else {
            return null;
        }
    }
    
}
