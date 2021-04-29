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
public enum Area {
    
    // Name     Code        Libelle
    
    FRA(        "FRA",      "France"),
    EUU(        "EUU",      "Union Européenne"),
    WLD(        "WLD",      "Monde");
    
    private final String code;
    private final String libelle;
    
    private Area(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }
    
}
