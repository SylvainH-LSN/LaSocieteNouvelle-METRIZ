/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib.indicators.ghg;

import static java.lang.Math.max;
import meta.Flag;
import lib.indicators.IndicateurNetValueAdded;
import meta.Indic;

/**
 *
 * @author SylvainPro
 */
public class IndicateurGHG extends IndicateurNetValueAdded {
    
    public static final Integer POSTE_SOURCES_FIXES = 0 ;
    public static final Integer POSTE_SOURCES_MOBILES = 1 ;
    public static final Integer POSTE_PROCEDES = 2 ;
    public static final Integer POSTE_FUGITIVES = 3 ;
    public static final Integer POSTE_BIOMASSE = 4 ;
    
    private Double emissions;
    private Double[] emissionsItems;
    private Double[] emissionsItemsUncertainty;
    
    public IndicateurGHG() {
        super();
        indic = Indic.GHG;
        emissionsItems = new Double[5];
        emissionsItemsUncertainty = new Double[5];
    }
    
    /* ---------- Setters ---------- */
    
    public void setEmissions(Double value) {
        emissions = value;
        uncertainty = 50.0;
        emissionsItems = new Double[5];
        emissionsItemsUncertainty = new Double[5];
    }
    
    public void setEmissions(Integer item, Double value) {
        emissionsItems[item] = value; // Gap (-1) with carbon assessment items numbering
        emissionsItemsUncertainty[item] = 50.0;
        updateTotal();
    }
    
    public void setEmissionsUncertainty(Integer item, Double value) {
        emissionsItemsUncertainty[item] = value;
        updateTotal();
    }
    
    /* ---------- Getters ---------- */
    
    public Double getEmissions() {
        return emissions;
    }
    
    public Double getEmissions(int item) {
        return emissionsItems[item];
    }
    
    public Double getEmissionsUncertainty(int item) {
        return emissionsItemsUncertainty[item];
    }

    public Double[] getEmissionsItems() {
        return emissionsItems;
    }

    public Double[] getEmissionsItemsUncertainty() {
        return emissionsItemsUncertainty;
    }
    
    /* ---------- Update ---------- */
    
    private void updateTotal() {
        Boolean isTotalSet = false;
        emissions = 0.0;
        Double emissionsMax = 0.0;
        Double emissionsMin = 0.0;
        for (Integer item = 0; item < emissionsItems.length; item++) {
            if (emissionsItems[item]!=null) {
                Double emissionsItem = emissionsItems[item];
                emissionsMax+= emissionsItem*(1 + emissionsItemsUncertainty[item]/100);
                emissionsMin+= emissionsItem*max(1 - emissionsItemsUncertainty[item]/100, 0.0);
                emissions+= emissionsItem;
                isTotalSet = true;
            }
        }
        if (isTotalSet) {
            if (emissions > 0.0) { uncertainty = max(emissionsMax-emissions, emissions-emissionsMin)/emissions *100;} 
            else                 { uncertainty = 0.0;}
        } else {
            emissions = null;
            uncertainty = null;
            flag = Flag.UNDEFINED;
        }
    }
    
    @Override
    public void updateValue(Double netValueAdded) {
        updateTotal();
        if (netValueAdded!=null & emissions!=null) {
            quality = emissions*1000/netValueAdded;
            flag = Flag.PUBLICATION;
        } else {
            quality = null;
            flag = Flag.UNDEFINED;
            uncertainty = null;
        }
    }
    
}
