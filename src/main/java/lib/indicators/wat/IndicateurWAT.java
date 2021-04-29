/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib.indicators.wat;

import static java.lang.Math.max;
import meta.Flag;
import lib.indicators.IndicateurNetValueAdded;
import meta.Indic;

/**
 *
 * @author SylvainPro
 */

public class IndicateurWAT extends IndicateurNetValueAdded {
    
    public static final Integer DELIVERED = 0;
    public static final Integer WITHDRAWAL = 1;
    
    private Double consumption;
    private Double[] items;
    private Double[] itemsUncertainty;
    
    public IndicateurWAT() {
        super();
        this.indic = Indic.WAT;
        this.items = new Double[2];
        this.itemsUncertainty = new Double[2];
    }
    
    /* ---------- Setters ---------- */
    
    public void setConsumption(Double value) {
        consumption = value;
        uncertainty = 50.0;
        items = new Double[2];
        itemsUncertainty = new Double[2];
    }
    
    public void setConsumption(int item, Double value) {
        items[item] = value;
        itemsUncertainty[item] = 50.0;
        updateTotal();
    }
    
    public void setUncertainty(int item, Double value) {
        itemsUncertainty[item] = value;
        updateTotal();
    }

    // For serialization
    public void setItems(Double[] items) {
        this.items = items;
    }
    public void setItemsUncertainty(Double[] itemsUncertainty) {
        this.itemsUncertainty = itemsUncertainty;
    }
    
    /* ---------- Getters ---------- */
    
    public Double getConsumption() {
        return consumption;
    }
    
    public Double getConsumption(int item) {
        return items[item];
    }

    public Double getUncertainty(int item) {
        return itemsUncertainty[item];
    }
    
    // For serialization
    public Double[] getItems() {
        return items;
    }
    public Double[] getItemsUncertainty() {
        return itemsUncertainty;
    }
    
    /* ---------- Update ---------- */
    
    private void updateTotal() {
        Boolean isTotalSet = false;
        consumption = 0.0;
        Double consumptionMax = 0.0;
        Double consumptionMin = 0.0;
        for (Integer item = 0; item < items.length; item++) {
            Double consumptionItem = items[item];
            if (consumptionItem!=null) {
                consumptionMax+= consumptionItem*(1 + itemsUncertainty[item]/100);
                consumptionMin+= consumptionItem*(1 - itemsUncertainty[item]/100);
                consumption+= consumptionItem;
                isTotalSet = true;
            }
        }
        if (isTotalSet) {
            if (consumption > 0) { uncertainty = max(consumptionMax-consumption, consumption-consumptionMin)/consumption *100;} 
            else                 { uncertainty = 0.0;}
        } else {
            consumption = null;
            uncertainty = null;
            flag = Flag.UNDEFINED;
        }
    }
    
    @Override
    public void updateValue(Double netValueAdded) {
        if (netValueAdded!=null & consumption!=null) {
            quality = consumption*1000/netValueAdded;
            flag = Flag.PUBLICATION;
        } else {
            quality = null;
            flag = Flag.UNDEFINED;
            uncertainty = null;
        }
    }
    
}
