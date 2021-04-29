/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etude;

import meta.Indic;
import meta.Flag;
import api.ResponseAPI;
import api.ResponseDefaultData;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 *
 * @author SylvainPro
 */

public class Indicator implements Serializable {
    
    protected Indic indic;
    
    protected Double quality;
    protected Flag flag;
    protected Double uncertainty;
    
    /* ---------- Constructor ---------- */
    
    // Classic indicateur
    public Indicator(Indic indic) {
        this.indic = indic;
        this.quality = null;
        this.flag = Flag.UNDEFINED;
        this.uncertainty = null;
    }
    
    // Impact direct
    protected Indicator() {
        this.quality = null;
        this.flag = Flag.UNDEFINED;
        this.uncertainty = null;
    }
    
    /* ---------- Update from API & Setters ---------- */
    
    public void update(ResponseAPI response) {
        quality = response.getValue(indic);
        flag = Flag.getFlag(response.getFlagCode(indic));
        uncertainty = response.getUncertainty(indic);
    }
    
    public void update(ResponseDefaultData response) {
        quality = response.getValueIndicateur(indic);
        flag = Flag.getFlag(response.getFlagIndicateur(indic));
        uncertainty = response.getUncertaintyIndicateur(indic);
    }
            
    public void setQuality(Double value) {
        quality = value;
        if (value!=null) {
            flag = Flag.INPUT_DATA;
        } else {
            flag = Flag.UNDEFINED;
            uncertainty = null;
        }
    }
    
    public void setDefaultData() {
        if (!Indic.isDefautDataSet) {
            Indic.fetchDefaultData();
        }
        quality = indic.getDefaultValue();
        flag = indic.getDefaultFlag();
        uncertainty = indic.getDefaultUncertainty();
    }
    
    public void setUncertainty(Double value) {
        uncertainty = value;
    }
    
    /* ---------- Getters ---------- */
    
    public Indic getIndic() {
        return indic;
    }
    
    @JsonProperty("quality")
    public Double getValue() {
        return quality;
    }
    
    public Flag getFlag() {
        return flag;
    }
    
    public Double getUncertainty() {
        return uncertainty;
    }
    
    @JsonIgnore
    public Double getValueMax() {
        if (quality!=null & getUncertainty()!=null) {
            Double coef = 1.0 + getUncertainty()/100;
            if (indic.isMarkOut()) {
                return min(quality*coef,100.0);
            } else {
                return quality*coef;
            }
        } else  {
            return null;
        }
    }
    
    @JsonIgnore
    public Double getValueMin() {
        if (quality!=null & getUncertainty()!=null) {
            Double coef = max(1.0 - getUncertainty()/100, 0.0);
            return quality*coef;
        } else  {
            return null;
        }
    }
    
}
