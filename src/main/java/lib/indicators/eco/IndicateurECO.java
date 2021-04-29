/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib.indicators.eco;

import meta.Flag;
import lib.indicators.IndicateurNetValueAdded;
import meta.Indic;

/**
 *
 * @author SylvainPro
 */
public class IndicateurECO extends IndicateurNetValueAdded {
    
    private Double domesticProduction;
    
    public IndicateurECO() {
        super();
        this.indic = Indic.ECO;
    }
    
    public void setDomesticProduction(Double value) {
        domesticProduction = value;
    }
    
    public Double getDomesticProduction() {
        return domesticProduction;
    }
    
    @Override
    public void updateValue(Double netValueAdded) {
        if (netValueAdded!=null & domesticProduction!=null) {
            if (netValueAdded<domesticProduction) {
                domesticProduction = netValueAdded;
            }
            quality = domesticProduction/netValueAdded*100;
            flag = Flag.PUBLICATION;
        } else {
            quality = null;
            flag = Flag.UNDEFINED;
        }
    }
    
    @Override
    public Double getUncertainty() {
        if (quality!=null) {
            return 0.0;
        } else {
            return null;
        }
    }
    
}
