/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etude;

import api.LaSocieteNouvelleAPI;
import api.ResponseAPI;
import api.ResponseDefaultData;
import meta.Indic;
import java.io.Serializable;

/**
 *
 * @author SylvainPro
 */
public class Expense implements Serializable {
    
    static final long serialVersionUID = -1L;
    
    // Id
    private Integer id;
    
    // Information
    private String corporateId;
    private String corporateName;
    private String areaCode;
    private String corporateActivity;
    
    // Amount
    private Double amount;
    
    // Sync
    private Boolean dataFetched;
    
    // Social footprint
    private String footprintId;
    private SocialFootprint footprint; 
    
    /* ---------- Constructors ---------- */
    
    // Used on interface
    public Expense(Integer id, String siren) {
        this.id = id;
        
        corporateId = siren;
        corporateName = null;
        corporateActivity = null;
        areaCode = null;
        
        amount = null;
        
        footprintId = getFootprintId();
        footprint = new SocialFootprint();
        
        dataFetched = false;
        fetchData();
    }
    
    // Used on import
    public Expense(Integer id, String siren, Double montant) {
        this.id = id;
        
        this.corporateId = siren;
        this.corporateName = "";
        this.corporateActivity = "";
        this.areaCode = "";
        
        this.amount = montant;
        
        footprintId = getFootprintId();
        footprint = new SocialFootprint();
        
        dataFetched = false;
        fetchData();
    }
    
    public Expense() {}
    
    // Set footrpint id
    private String getFootprintId() {
        
        // SIREN
        if (corporateId.matches("[0-9]{9}")) {
            return corporateId;
        
        // SIRET
        } else if (corporateId.matches("[0-9]{14}")) {
            return corporateId.substring(0,9);
            
        // VAT NUMBER
        } else if (corporateId.matches("FR%")) {
            return corporateId;
            
        } else {
            return corporateId;
        }
        
    }
    
    /* ---------- Setters ---------- */

    public void setCorporateId(String identifiant) {
        this.corporateId = identifiant;
        this.footprintId = getFootprintId();
    }
        
    public void setCorporateName(String denominationUniteLegale) {
        this.corporateName = denominationUniteLegale;
    }
    
    public void setCorporateActivity(String corporateActivity) {
        this.corporateActivity = corporateActivity;
    }
    
    public void setAreaCode(String geo) {
        this.areaCode = geo;
    }
    
    public void setAmount(Double montant) {
        this.amount = montant;
    }
    
    /* ---------- Fetch Data ---------- */  
    
    // Fetch all data
    public void fetchData() {
        
        ResponseAPI response = LaSocieteNouvelleAPI.fetchDataSiren(footprintId);
        
        dataFetched = response.isValid();
        if (response.isValid()) {
            corporateName = response.getDenomination();
            corporateActivity = response.getEconomicBranch();
            areaCode = "FRA";
            footprint.updateAll(response);
            
        } else {
            
            String area = areaCode !=null ? areaCode : "WLD";
            String activity = corporateActivity!=null ? corporateActivity : "00";
            ResponseDefaultData defaultData = LaSocieteNouvelleAPI.fetchDefaultData(area, activity);
            
            if (!defaultData.isValid()) {
                defaultData = LaSocieteNouvelleAPI.fetchDefaultData("WLD", "00");
            }
            
            footprint.setDefaultData(defaultData);
        }
        
    }
    
    // Fetch CSF data for all indicators & complete general data
    public void fetchCSFdata() {
        ResponseAPI response = LaSocieteNouvelleAPI.fetchDataSiren(footprintId);
        dataFetched = response.isValid();
        if (response.isValid()) {
            if (corporateName.equals("")) { corporateName = response.getDenomination(); }
            if (corporateActivity.equals("")) { corporateActivity = response.getEconomicBranch(); }
            if (areaCode.equals("")) { areaCode = "FRA"; }
            footprint.updateAll(response);
            
        } else {
            // to be continued
        }
    }
        
    // Fetch only CSF data for a specific indicator
    public void fetchCSFdata(Indic indic) {
        ResponseAPI response = LaSocieteNouvelleAPI.fetchDataSiren(footprintId);
        if (response.isValid()) {
            footprint.update(indic, response);
        }
    }
    
    /* ---------- Getters ---------- */
    
    public Integer getId() {
        return id;
    }
    
    public String getCorporateId() {
        return corporateId;
    }

    public String getCorporateName() {
        return corporateName;
    }
    
    public String getCorporateActivity() {
        return corporateActivity;
    }

    public String getAreaCode() {
        return areaCode;
    }
    
    public Double getAmount() {
        return amount;
    }
    
    public SocialFootprint getFootprint() {
        return footprint;
    }
    
    public Boolean isDataFetched() {
        return dataFetched;
    }
    
}