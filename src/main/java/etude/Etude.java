/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etude;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import lib.indicators.IndicateurNetValueAdded;
import static lib.indicators.IndicateurNetValueAdded.buildImpactsDirects;
import meta.Indic;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import static java.lang.Math.max;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SylvainPro
 */
public class Etude implements Serializable {
    
    static final long serialVersionUID = -1L;
    
    private Integer id;
    
    private String libelle;
    private UniteLegale uniteLegale;    
    
    private FinancialData financialData;
    
    private HashMap<Indic,IndicateurNetValueAdded> impactsDirects;
    
    private SocialFootprint productionFootprint = new SocialFootprint();
    private SocialFootprint expensesFootprint = new SocialFootprint();
    private SocialFootprint depreciationFootprint = new SocialFootprint();
    
    /* ---------- Constructor --------- */
    
    // Used in manager
    public Etude(String libelle) {
        
        this.id = getNewEtudeId();
        this.libelle = libelle;
        
        uniteLegale = new UniteLegale(libelle);
        financialData = new FinancialData();
        
        impactsDirects = buildImpactsDirects();
        
        productionFootprint = new SocialFootprint();
        expensesFootprint = new SocialFootprint();
        expensesFootprint = new SocialFootprint();
        
    }

    // Used for deserialisation
    public Etude() {}
    
    /* ---------- Setters ---------- */

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void setUniteLegale(UniteLegale uniteLegale) {
        this.uniteLegale = uniteLegale;
    }

    public void setFinancialData(FinancialData financialData) {
        this.financialData = financialData;
    }

    public void setImpactsDirects(HashMap<Indic, IndicateurNetValueAdded> impactsDirects) {
        this.impactsDirects = impactsDirects;
    }

    public void setProductionFootprint(SocialFootprint productionSocialFootprint) {
        this.productionFootprint = productionSocialFootprint;
    }

    public void setExpensesFootprint(SocialFootprint expensesSocialFootprint) {
        this.expensesFootprint = expensesSocialFootprint;
    }

    public void setDepreciationsFootprint(SocialFootprint depreciationExpensesSocialFootprint) {
        this.depreciationFootprint = depreciationExpensesSocialFootprint;
    }
        
    /* ---------- Social Footprints Updaters ---------- */
    
    public void updateFootprints() {
        updateExpensesFootprint();
        updateDepreciationsFootprint();
        updateValueAddedFootprint();
    }
    
    public void updateFootprints(Indic indic) {
        updateExpensesFootprint(indic);
        updateDepreciationsFootprint(indic);
        Etude.this.updateValueAddedFootprint(indic);
    }
    
    // Production Social Footprint
    
    public void updateProductionFootprint(Indic indic) {
        
        // if all amounts are set..
        if (financialData.getProduction()!=null 
                & financialData.getAmountExpenses()!=null
                & financialData.getAmountDepreciations()!=null
                & financialData.getNetValueAdded()!=null)
        {
            // ...and all the footprints are set (if the amount is not null)
            if ( !(financialData.getAmountExpenses()>0.0 & expensesFootprint.getIndicateur(indic).getValue()==null) 
                    & !(financialData.getAmountDepreciations()>0.0 & depreciationFootprint.getIndicateur(indic).getValue()==null) 
                    & !(financialData.getNetValueAdded()>0.0 & impactsDirects.get(indic).getValue()==null) ) 
            {
                Double absolute = 0.0;
                Double absoluteMax = 0.0;
                Double absoluteMin = 0.0;
                
                if (financialData.getAmountExpenses()>0.0) { 
                    absolute+= financialData.getAmountExpenses()*expensesFootprint.getIndicateur(indic).getValue();
                    absoluteMax+= financialData.getAmountExpenses()*expensesFootprint.getIndicateur(indic).getValueMax();
                    absoluteMin+= financialData.getAmountExpenses()*expensesFootprint.getIndicateur(indic).getValueMin();
                }
                if (financialData.getAmountDepreciations()>0.0) { 
                    absolute+= financialData.getAmountDepreciations()*depreciationFootprint.getIndicateur(indic).getValue();
                    absoluteMax+= financialData.getAmountDepreciations()*depreciationFootprint.getIndicateur(indic).getValueMax();
                    absoluteMin+= financialData.getAmountDepreciations()*depreciationFootprint.getIndicateur(indic).getValueMin();
                }
                if (financialData.getNetValueAdded()>0.0) { 
                    absolute+= financialData.getNetValueAdded()*impactsDirects.get(indic).getValue();
                    absoluteMax+= financialData.getNetValueAdded()*impactsDirects.get(indic).getValueMax();
                    absoluteMin+= financialData.getNetValueAdded()*impactsDirects.get(indic).getValueMin();
                }
                
                Double quality = absolute/financialData.getProduction();
                productionFootprint.getIndicateur(indic).setQuality(quality);
                Double uncertainty = max(absoluteMax-absolute,absolute-absoluteMin)/absolute *100;
                productionFootprint.getIndicateur(indic).setUncertainty(uncertainty);
            
            }
            else 
            {
               productionFootprint.getIndicateur(indic).setQuality(null); 
            }
        }
        else
        {
            productionFootprint.getIndicateur(indic).setQuality(null);
        }
        
    }
    
    
    // Expenses Social Footprint
    
    public void updateExpensesFootprint() {
        for (Indic indic : Indic.values()) 
        {
            updateExpensesFootprint(indic);
        }
    }
    
    public void updateExpensesFootprint(Indic indic) {
        
        if(!Indic.isDefautDataSet) {
            Indic.fetchDefaultData();
        }
        
        Indicator indicatorExpenses = expensesFootprint.getIndicateur(indic);
        
        if (financialData.getAmountExpenses()!=null) {
        
            Double totalAmount = 0.0;
            Double absolute = 0.0;
            Double absoluteMax = 0.0;
            Double absoluteMin = 0.0;

            // Process for listed expenses
            ArrayList<Expense> expenses = financialData.getExpenses();
            for (Expense expense : expenses) 
            {
                Double amountExpense = expense.getAmount();
                if (amountExpense!=null) 
                {
                    Indicator indicatorExpense = expense.getFootprint().getIndicateur(indic);
                    if (indicatorExpense.getValue()==null) { indicatorExpense.setDefaultData(); }

                    totalAmount+= expense.getAmount();
                    absolute+= indicatorExpense.getValue()*amountExpense;
                    absoluteMax+= indicatorExpense.getValueMax()*amountExpense;
                    absoluteMin+= indicatorExpense.getValueMin()*amountExpense;
                }
            }
            // Process for remaining expenses (difference between total declared & total listed expenses)
            if (totalAmount<financialData.getAmountExpenses()) {
                Double amountRemaining = financialData.getAmountExpenses()-totalAmount;
                totalAmount = financialData.getAmountExpenses();
                absolute+= indic.getDefaultValue()*amountRemaining;
                absoluteMax+= indic.getDefaultValueMax()*amountRemaining;
                absoluteMin+= indic.getDefaultValueMin()*amountRemaining;
            }

            if (totalAmount>0.0) { 
                indicatorExpenses.setQuality(absolute/totalAmount);
                Double uncertainty = max(absoluteMax-absolute,absolute-absoluteMin)/absolute *100;
                indicatorExpenses.setUncertainty(uncertainty);
            } else {
                indicatorExpenses.setQuality(null); 
                indicatorExpenses.setUncertainty(null);
            }
        
        } else { 
            indicatorExpenses.setQuality(null); 
            indicatorExpenses.setUncertainty(null);
        }
        
        updateProductionFootprint(indic);
    }
    
    // Depreciation expenses Social Footprint
    
    public void updateDepreciationsFootprint() {
        for (Indic indic : Indic.values()) 
        {
            updateDepreciationsFootprint(indic);
        }
    }
    
    public void updateDepreciationsFootprint(Indic indic) {
        
        if(!Indic.isDefautDataSet) {
            Indic.fetchDefaultData();
        }
        Indicator indicatorDepreciations = depreciationFootprint.getIndicateur(indic);
        
        if (financialData.getAmountDepreciations()!=null) {

            Double totalAmount = 0.0;
            Double absolute = 0.0;
            Double absoluteMax = 0.0;
            Double absoluteMin = 0.0;
            
            // Process for listed depreciations
            ArrayList<Depreciation> depreciations = financialData.getDepreciations();
            for (Depreciation depreciation : depreciations) 
            {
                Double amountDepreciation = depreciation.getAmount();
                if (amountDepreciation!=null) 
                {
                    Indicator indicatorDepreciation = depreciation.getFootprint().getIndicateur(indic);
                    if (indicatorDepreciation.getValue()==null) { indicatorDepreciation.setDefaultData(); }

                    totalAmount+= depreciation.getAmount();
                    absolute+= indicatorDepreciation.getValue()*amountDepreciation;
                    absoluteMax+= indicatorDepreciation.getValueMax()*amountDepreciation;
                    absoluteMin+= indicatorDepreciation.getValueMin()*amountDepreciation;
                }
            }
            // Process for remaining amount of depreciations
            if (totalAmount<financialData.getAmountDepreciations()) {
                Double amountRemaining = financialData.getAmountDepreciations()-totalAmount;
                totalAmount = financialData.getAmountDepreciations();
                absolute+= indic.getDefaultValue()*amountRemaining;
                absoluteMax+= indic.getDefaultValueMax()*amountRemaining;
                absoluteMin+= indic.getDefaultValueMin()*amountRemaining;
            }
            
            if (totalAmount>0.0) { 
                indicatorDepreciations.setQuality(absolute/totalAmount);
                Double uncertainty = max(absoluteMax-absolute,absolute-absoluteMin)/absolute *100;
                indicatorDepreciations.setUncertainty(uncertainty);
            } else {
                indicatorDepreciations.setQuality(null);
                indicatorDepreciations.setUncertainty(null);
            }
        
        } else { 
            indicatorDepreciations.setQuality(null);
            indicatorDepreciations.setUncertainty(null);
        }
        
        updateProductionFootprint(indic);
    }
    
    // Net Value Added
    
    public void updateValueAddedFootprint() {
        for (Indic indic : Indic.values()) {
            Etude.this.updateValueAddedFootprint(indic);
        }
    }
    
    public void updateValueAddedFootprint(Indic indic) {
        impactsDirects.get(indic).updateValue(getFinancialData().getNetValueAdded());
        updateProductionFootprint(indic);
    }
    
    /* ---------- Getters ---------- */

    // General data

    public Integer getId() {
        return id;
    }

    public String getLibelle() {
        return libelle;
    }

    public UniteLegale getUniteLegale() {
        return uniteLegale;
    }

    public FinancialData getFinancialData() {
        return financialData;
    }

    // Footprints
    
    public SocialFootprint getProductionFootprint() {
        return productionFootprint;
    }
    
    public SocialFootprint getExpensesFootprint( ) {
        return expensesFootprint;
    }
    
    public SocialFootprint getDepreciationsFootprint() {
        return depreciationFootprint;
    }
     
    public HashMap<Indic, IndicateurNetValueAdded> getImpactsDirects() {
        return impactsDirects;
    }
    
    @JsonIgnore
    public Indicator getValueAddedFootprint(Indic indic) {
        return impactsDirects.get(indic);
    }
    
    /* ---------- Others ---------- */
    
    @Override
    public String toString () {
        return this.libelle;
    }
    
    /* ---------- Serialization ---------- */
    
    public void save() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();      
            objectMapper.writeValue(new File(getNameFile(id)), this);
        } catch (IOException ex) {
            Logger.getLogger(Etude.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    public static Etude open(String path) throws FileNotFoundException, IOException, ClassNotFoundException {
        FileReader file = new FileReader(path);
        Etude etude;
        try (BufferedReader reader = new BufferedReader(file)) {
            String jsonString = reader.readLine();
            ObjectMapper objectMapper = new ObjectMapper();
            etude = objectMapper.readValue(jsonString, Etude.class);
        }
        return etude;
    }
    
    public void delete () {
        File file = new File(getNameFile(id));
        file.delete();
    }
    
    private static String getNameFile(Integer id) {
        return "src/main/data/"+"etude_"+id+".json";
    }
 
    private static Integer getNewEtudeId() {
        File directory = new File("src/main/data/");
        Integer id = 1;
        
        Boolean idValid = false;
        while (id<=directory.list().length & !idValid) {
            File file = new File(getNameFile(id));
            idValid = !file.exists();
            id++;
        }
        
        return id;
    }

    
}
