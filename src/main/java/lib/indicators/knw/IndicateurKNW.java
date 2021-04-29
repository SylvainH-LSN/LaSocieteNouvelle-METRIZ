/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib.indicators.knw;

import meta.Flag;
import lib.indicators.IndicateurNetValueAdded;
import meta.Indic;

/**
 *
 * @author SylvainPro
 */
public class IndicateurKNW extends IndicateurNetValueAdded {
    
    public static final Integer SPENDINGS_INTERNALS_TRAININGS = 0;
    public static final Integer SPENDINGS_RESEARCH = 1;
    public static final Integer SPENDINGS_TRAININGS_CONTRACTS = 2;
    
    private Double spendings;
    private Double[] spendingsItems;
    private Double[] spendingsItemsUncertainty;
    
    public IndicateurKNW() {
        super();
        this.indic = Indic.KNW;
        this.spendingsItems = new Double[3];
        this.spendingsItemsUncertainty = new Double[3];
    }
    
    /* ---------- Setters ---------- */
    
    public void setSpendings(Double value) {
        spendings = value;
    }
    
    public void setSpendings(Integer item, Double value) {
        spendingsItems[item] = value;
        updateTotal();
    }

    public void setSpendingsItems(Double[] spendingsItems) {
        this.spendingsItems = spendingsItems;
    }

    public void setSpendingsItemsUncertainty(Double[] spendingsItemsUncertainty) {
        this.spendingsItemsUncertainty = spendingsItemsUncertainty;
    }
    
    /* ---------- Getters ---------- */
    
    public Double getSpendings() {
        return spendings;
    }
    
    public Double getSpendings(Integer item) {
        return spendingsItems[item];
    }

    public Double[] getSpendingsItems() {
        return spendingsItems;
    }

    public Double[] getSpendingsItemsUncertainty() {
        return spendingsItemsUncertainty;
    }
    
    /* ---------- Update ---------- */
    
    private void updateTotal() {
        spendings = 0.0;
        for (Integer item = 0; item < spendingsItems.length; item++) {
            Double spendingsItem = spendingsItems[item];
            spendings+= spendingsItem!=null ? spendingsItem : 0.0;
        }
    }
    
    @Override
    public void updateValue(Double netValueAdded) {
        if (netValueAdded!=null & spendings!=null) {
            quality = spendings/netValueAdded*100;
            flag = Flag.PUBLICATION;
        } else {
            quality = null;
            flag = Flag.UNDEFINED;
        }
    }
    
    /* ---------- Override ---------- */

    @Override
    public Double getUncertainty() {
        if (quality!=null) {
            return 0.0;
        } else {
            return null;
        }
    }
    
}
