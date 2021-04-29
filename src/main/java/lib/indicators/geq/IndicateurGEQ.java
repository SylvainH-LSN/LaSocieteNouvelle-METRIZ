/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib.indicators.geq;

import meta.Flag;
import lib.indicators.IndicateurNetValueAdded;
import meta.Indic;

/**
 *
 * @author SylvainPro
 */

public class IndicateurGEQ extends IndicateurNetValueAdded {
    
    private Double wageGap;
    private Boolean hasEmployees;
    
    public IndicateurGEQ() {
        super();
        this.indic = Indic.GEQ;
        this.hasEmployees = false;
    }
    
    /* ---------- Setters ---------- */
    
    public void setHasEmployees(Boolean hasEmployees) {
        this.hasEmployees = hasEmployees;
    }
    
    public void setWageGap(Double value) {
        wageGap = value;
    }
    
    /* ---------- Getters ---------- */
    
    public Double getWageGap() {
        return wageGap;
    }

    public Boolean getHasEmployees() {
        return hasEmployees;
    }
    
    /* ---------- Update ---------- */
    
    @Override
    public void updateValue(Double netValueAdded) {
        if (netValueAdded!=null & !hasEmployees) {
            quality = 0.0;
            flag = Flag.PUBLICATION;
            uncertainty = 0.0;
        }
        if (netValueAdded!=null & wageGap!=null) {
            quality = wageGap;
            flag = Flag.PUBLICATION;
            uncertainty = 0.0;
        } else {
            quality = null;
            flag = Flag.UNDEFINED;
            uncertainty = null;
        }
    }
    
}
