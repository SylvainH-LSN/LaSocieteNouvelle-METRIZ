/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib.indicators.nrg;

import static java.lang.Double.max;
import meta.Flag;
import lib.indicators.IndicateurNetValueAdded;
import meta.Indic;

/**
 *
 * @author SylvainPro
 */
public class IndicateurNRG extends IndicateurNetValueAdded {
    
    public static final Integer ELECTRICITY = 0;
    public static final Integer FOSSILE = 1;
    public static final Integer BIOMASSE = 2;
    public static final Integer HEAT = 3;
    public static final Integer PRIMARY_RENEWABLE = 4;
    
    private Double energy;
    private Double[] items;
    private Double[] itemsUncertainty;
    
    public IndicateurNRG() {
        super();
        indic = Indic.GHG;
        items = new Double[5];
        itemsUncertainty = new Double[5];
    }
    
    /* ---------- Setters ---------- */
    
    public void setEnergy(Double value) {
        energy = value;
        uncertainty = 50.0;
        items = new Double[5];
        itemsUncertainty = new Double[5];
    }
    
    public void setEnergy(int item, Double value) {
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
    
    public Double getEnergy() {
        return energy;
    }
    
    public Double getEnergy(int item) {
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
        energy = 0.0;
        Double energyMax = 0.0;
        Double energyMin = 0.0;
        for (Integer item = 0; item < items.length; item++) {
            Double energyItem = items[item];
            if (energyItem!=null) {
                energyMax+= energyItem*(1 + itemsUncertainty[item]/100);
                energyMin+= energyItem*max(1 - itemsUncertainty[item]/100, 0.0);
                energy+= energyItem;
                isTotalSet = true;
            }
        }
        if (isTotalSet) {
            if (energy > 0) { uncertainty = max(energyMax-energy, energy-energyMin)/energy *100;} 
            else            { uncertainty = 0.0;}
        } else {
            energy = null;
            uncertainty = null;
            flag = Flag.UNDEFINED;
        }
    }
    
    @Override
    public void updateValue(Double netValueAdded) {
        if (netValueAdded!=null & energy!=null) {
            quality = energy*1000/netValueAdded;
            flag = Flag.PUBLICATION;
        } else {
            quality = null;
            flag = Flag.UNDEFINED;
            uncertainty = null;
        }
    }

}
