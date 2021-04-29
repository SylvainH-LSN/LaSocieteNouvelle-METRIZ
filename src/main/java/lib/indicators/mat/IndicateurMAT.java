/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib.indicators.mat;

import static java.lang.Math.max;
import meta.Flag;
import lib.indicators.IndicateurNetValueAdded;
import meta.Indic;

/**
 *
 * @author SylvainPro
 */

public class IndicateurMAT extends IndicateurNetValueAdded {
    
    public static final Integer BIOMASSE = 0;
    public static final Integer METAL = 1;
    public static final Integer NON_METAL = 2;
    public static final Integer FOSSILE = 3;
    
    private Double materials;
    private Double[] items;
    private Double[] itemsUncertainty;
    
    public IndicateurMAT() {
        super();
        indic = Indic.MAT;
        items = new Double[6];
        itemsUncertainty = new Double[6];
    }
    
    /* ---------- Setters ---------- */
    
    public void setMaterials(Double value) {
        materials = value;
        uncertainty = 50.0;
        items = new Double[6];
        itemsUncertainty = new Double[6];
    }
    
    public void setMaterials(int item, Double value) {
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
    
    public Double getMaterials() {
        return materials;
    }
    
    public Double getMaterials(Integer item) {
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
        materials = 0.0;
        Double materialsMax = 0.0;
        Double materialsMin = 0.0;
        for (Integer item = 0; item < items.length; item++) {
            Double materialsItem = items[item];
            if (materialsItem!=null) {
                materialsMax+= materialsItem*(1 + itemsUncertainty[item]/100);
                materialsMin+= materialsItem*max(1 - itemsUncertainty[item]/100, 0.0);
                materials+= materialsItem;
                isTotalSet = true;
            }
        }
        if (isTotalSet) {
            if (materials > 0.0) { uncertainty = max(materialsMax-materials, materials-materialsMin)/materials *100;} 
            else                 { uncertainty = 0.0;}
        } else {
            materials = null;
            uncertainty = null;
            flag = Flag.UNDEFINED;
        }
    }
    
    @Override
    public void updateValue(Double netValueAdded) {
        if (netValueAdded!=null & materials!=null) {
            quality = materials*1000/netValueAdded;
            flag = Flag.PUBLICATION;
        } else {
            quality = null;
            flag = Flag.UNDEFINED;
            uncertainty = null;
        }
    }

}
