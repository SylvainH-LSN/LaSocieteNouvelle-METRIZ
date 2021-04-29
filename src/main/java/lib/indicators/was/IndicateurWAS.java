/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib.indicators.was;

import static java.lang.Math.max;
import meta.Flag;
import lib.indicators.IndicateurNetValueAdded;
import meta.Indic;

/**
 *
 * @author SylvainPro
 */
public class IndicateurWAS extends IndicateurNetValueAdded {
    
    public static final Integer TYPE_RECYCLED = 0;
    public static final Integer TYPE_NOT_DANGEROUS = 1;
    public static final Integer TYPE_DANGEROUS = 2;
    
    private Double waste;
    private Double[] items;
    private Double[] itemsUncertainty;
    
    public IndicateurWAS() {
        super();
        indic = Indic.WAS;
        items = new Double[3];
        itemsUncertainty = new Double[3];
    }
    
    /* ---------- Setters ---------- */
    
    public void setWaste(Double value) {
        waste = value;
        uncertainty = 50.0;
        items = new Double[3];
        itemsUncertainty = new Double[3];
    }
    
    public void setWaste(int item, Double value) {
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
    
    public Double getWaste() {
        return waste;
    }
    
    public Double getWaste(int item) {
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
        waste = 0.0;
        Double wasteMax = 0.0;
        Double wasteMin = 0.0;
        for (Integer item = 0; item < items.length; item++) {
            Double wasteItem = items[item];
            if (wasteItem!=null) {
                wasteMax+= wasteItem*(1 + itemsUncertainty[item]/100);
                wasteMin+= wasteItem*max(1 - itemsUncertainty[item]/100, 0.0);
                waste+= wasteItem;
                isTotalSet = true;
            }
        }
        if (isTotalSet) {
            if (waste > 0) { uncertainty = max(wasteMax-waste, waste-wasteMin)/waste *100;} 
            else { uncertainty = 0.0;}
        } else {
            waste = null;
            uncertainty = null;
            flag = Flag.UNDEFINED;
        }
    }
    
    @Override
    public void updateValue(Double netValueAdded) {
        if (netValueAdded!=null & waste!=null) {
            quality = waste*1000/netValueAdded;
            flag = Flag.PUBLICATION;
        } else {
            quality = null;
            flag = Flag.UNDEFINED;
        }
    }
    
}
