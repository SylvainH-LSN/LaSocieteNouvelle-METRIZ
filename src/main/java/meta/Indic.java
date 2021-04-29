/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meta;

import api.LaSocieteNouvelleAPI;
import api.ResponseDefaultData;
import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 *
 * @author SylvainPro
 */
public enum Indic {
    
    ECO ("ECO","Contribution à l'Economie nationale",                                               "%",        true),
    ART ("ART","Contribution aux Métiers d'Art et aux Savoir-Faire",                                "%",        true),
    SOC ("SOC","Contribution aux Acteurs d'Intérêt social",                                         "%",        true),
    KNW ("KNW","Contribution à l'Evolution des Connaissances et des Compétences",                   "%",        true),
    DIS ("DIS","Indice de répartition des revenus",                                                 "/100",     true),
    GEQ ("GEQ","Indice d'Egalité des revenus entre les Femmes et les Hommes",                       "%",        true),
    GHG ("GHG","Intensité d'Emission de Gaz à Effet de Serre",                                      "gCO2e/€",  true),
    MAT ("MAT","Intensité d'Extraction de Matières premières",                                      "g/€",      true),
    WAS ("WAS","Intensité de Production de Déchets",                                                "g/€",      true),
    NRG ("NRG","Intensité de Consommation d'Energie",                                               "kJ/€",     true),
    WAT ("WAT","Intensité de Consommation d'Eau",                                                   "L/€",      true),
    HAZ ("HAZ","Intensité d'Utilisation de produits dangereux pour la Santé et l'Environnement",    "g/€",      true);
         
    public static Boolean isDefautDataSet = false;
    
    private final String code;
    private final String libelle;
    private final String unit;
    private final Boolean iqve;
    
    private Double defaultValue;
    private Flag defaultFlag;
    private Double defaultUncertainty;
    
    private Indic (String code, String libelle, String unit, Boolean iqve) {
        this.code = code;
        this.libelle = libelle;
        this.unit = unit;
        this.iqve = iqve;
        this.defaultValue = null;
        this.defaultFlag = Flag.UNDEFINED;
        this.defaultUncertainty = null;
    }
            
    public void setDefaultValue(Double value) {
        this.defaultValue = value;
    }
    
    public void setDefaultFlag(Flag flag) {
        this.defaultFlag = flag;
    }
    
    public void setDefaultUncertainty(Double value) {
        this.defaultUncertainty = value;
    }
    
    public String getCode () {
        return this.code;
    }

    public String getLibelle() {
        return libelle;
    }
    
    public String getUnit() {
        return unit;
    }

    public Boolean isIqve() {
        return iqve;
    }
    
    public Double getDefaultValue() {
        return defaultValue;
    }
    
    public Flag getDefaultFlag() {
        return defaultFlag;
    }
    
    public Double getDefaultUncertainty() {
        return defaultUncertainty;
    }
    
    public Boolean isMarkOut() {
        return this==ART | this==DIS | this==ECO | this==GEQ | this==KNW | this==SOC;
    }
    
    public Double getDefaultValueMax() {
        if (getDefaultValue()!=null & getDefaultUncertainty()!=null) {
            Double coef = 1.0 + getDefaultUncertainty()/100;
            if (this.isMarkOut()) {
                return min(getDefaultValue()*coef,100.0);
            } else {
                return getDefaultValue()*coef;
            }
        } else  {
            return null;
        }
    }
    
    public Double getDefaultValueMin() {
        if (getDefaultValue()!=null & getDefaultUncertainty()!=null) {
            Double coef = max(1.0 - getDefaultUncertainty()/100, 0.0);
            return getDefaultValue()*coef;
        } else  {
            return null;
        }
    }
    
    /* ---------- Default data ---------- */
    
    public static void fetchDefaultData() {
        
        ResponseDefaultData defaultData = LaSocieteNouvelleAPI.fetchDefaultData("FRA", "00");
        if (defaultData.isValid()) {
            for (Indic indic : Indic.values()) {
                Double value = defaultData.getValueIndicateur(indic);
                indic.setDefaultValue(value);
                Double uncertainty = defaultData.getUncertaintyIndicateur(indic);
                indic.setDefaultUncertainty(uncertainty);
                String flag = defaultData.getFlagIndicateur(indic);
                indic.setDefaultFlag(Flag.getFlag(flag));
            }
            isDefautDataSet = true;
        }
        
    }
    
    /* ----- AUTRES ----- */
    
    public static Indic getIndicateur (String indic) {
        
        switch (indic) {
            case "ECO": return ECO;
            case "ART": return ART;
            case "SOC": return SOC;
            case "KNW": return KNW;
            case "DIS": return DIS;
            case "GEQ": return GEQ;
            case "GHG": return GHG;
            case "MAT": return MAT;
            case "WAS": return WAS;
            case "NRG": return NRG;
            case "WAT": return WAT;
            case "HAZ": return HAZ;
            default:
                return ECO;
        }
        
    }
    
        
}
