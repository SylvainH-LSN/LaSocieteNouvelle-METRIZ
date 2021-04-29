/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meta;

/**
 *
 * @author SylvainPro
 */
public enum Flag {
    
    // Name                 Code    Libelle                                 Declared    Adjusted
    // -----------------------------------------------------------------------------------------
    // Valeurs déclarées/publiées
    PUBLICATION(            "p",    "Valeur déclarée",                      true,       true),
    PUBLIC_REPORTING(       "r",    "Valeur publique",                      false,      true),
    
    // Valeurs ajustées
    SIMPLIFIED_PUBLICATION( "h",    "Valeur déclarée sur valeur ajoutée",   true,       true),
    ESTIMATION(             "e",    "Valeur estimée",                       false,      true),
    ADJUSTED_DATA(          "a",    "Valeur ajustée",                       false,      true),
    
    // Valeurs par defaut
    SECTOR_SPECIFIC_DATA(   "s",    "Valeur sectorielle",                   false,      false),
    DEFAULT_DATA(           "d",    "Valeur par défaut",                    false,      false),
    
    // Valeur non applicable
    NOT_APPLICABLE(         "n",    "Non applicable",                       false,      false),
    
    // Valeur saisie
    INPUT_DATA(             "i",    "Valeur saisie",                        false,      false),
    UNDEFINED(              "u",    "Valeur non définie",                   false,      false);
        
    private final String code;
    private final String libelle;
    private final Boolean isDeclared;
    private final Boolean isAdjusted;
    
    private Flag (String code, String libelle, Boolean isDeclared, Boolean isAdjusted) {
        this.code = code;
        this.libelle = libelle;
        this.isDeclared = isDeclared;
        this.isAdjusted = isAdjusted;
    }
    
    public String getCode () {
        return this.code;
    }

    public String getLibelle() {
        return libelle;
    }
    
    public Boolean isDeclared() {
        return isDeclared;
    }

    public Boolean isAdjusted() {
        return isAdjusted;
    }
    
    /* ----- AUTRES ----- */
    
    public static Flag getFlag (String flag) {
        
        switch (flag) {
            case "p" : return PUBLICATION;
            case "h" : return SIMPLIFIED_PUBLICATION;
            case "r" : return PUBLIC_REPORTING;
            case "e" : return ESTIMATION;
            case "a" : return ADJUSTED_DATA;
            case "s" : return SECTOR_SPECIFIC_DATA;
            case "d" : return DEFAULT_DATA;
            case "n" : return NOT_APPLICABLE;
            case "i" : return INPUT_DATA;
            default:
                return DEFAULT_DATA;
        }
        
    }
    
}
