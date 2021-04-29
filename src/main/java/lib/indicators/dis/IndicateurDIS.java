/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib.indicators.dis;

import java.io.Serializable;
import meta.Flag;
import lib.indicators.IndicateurNetValueAdded;
import meta.Indic;

/**
 *
 * @author SylvainPro
 */

public class IndicateurDIS extends IndicateurNetValueAdded implements Serializable {
    
    private Double indexGINI;
    
    public IndicateurDIS() {
        super();
        this.indic = Indic.DIS;
    }
    
    /* ---------- Setters ---------- */
    
    public void setIndexGINI(Double value) {
        indexGINI = value;
    }
    
    /* ---------- Getters ---------- */
    
    public Double getIndexGINI() {
        return indexGINI;
    }
    
    /* ---------- Update ---------- */
    
    @Override
    public void updateValue(Double netValueAdded) {
        if (netValueAdded!=null & indexGINI!=null) {
            quality = indexGINI;
            flag = Flag.PUBLICATION;
            uncertainty = 0.0;
        } else {
            quality = null;
            flag = Flag.UNDEFINED;
            uncertainty = null;
        }
    }

}
