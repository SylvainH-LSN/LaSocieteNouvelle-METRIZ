/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etude;

import com.fasterxml.jackson.annotation.JsonIgnore;
import meta.Indic;
import java.io.Serializable;
import java.util.ArrayList;
import static java.util.Collections.max;

/**
 *
 * @author SylvainPro
 */

public class FinancialData implements Serializable {
    
    static final long serialVersionUID = -1L;
    
    private Double production;
    
    private Double amountExpenses;
    private ArrayList<Expense> expenses;
    
    private Double amountDepreciations;
    private ArrayList<Depreciation> depreciations;
    
    private Double netValueAdded;
    
    /* ---------- Constructor ---------- */
    
    public FinancialData () {
        
        production = null;
        
        amountExpenses = null;
        expenses = new ArrayList<>();
        
        amountDepreciations = null;
        depreciations = new ArrayList<>();
        
        netValueAdded = null;
    }
    
    /* ---------- Setters ---------- */

    public void setProduction(Double amount) {
        production = amount;
        updateNetValueAdded();
    }
    
    public void setAmountExpenses(Double amount) {
        this.amountExpenses = amount;
        updateAmountExpenses();
        updateNetValueAdded();
    }
    
    public void setAmountDepreciations(Double amount) {
        this.amountDepreciations = amount;
        updateAmountDepreciations();
        updateNetValueAdded();
    }
    
    public void setNetValueAdded(Double amount) {
        this.netValueAdded = amount;
    }
    
    /* ---------- Update - Agregats ---------- */
    
    public void updateAmountExpenses() {
        if (expenses.size()>0) {
            Double amount = 0.0;
            for (Expense expense : expenses) {
                amount+= zeroIfNull(expense.getAmount());
            }
            if (amountExpenses==null) {
                amountExpenses = amount;
                updateNetValueAdded();
            } else if (amount>amountExpenses) {
                amountExpenses = amount;
                updateNetValueAdded();
            }
        }
    }
    
    public void updateAmountDepreciations() {
        if (depreciations.size()>0) {
            Double amount = 0.0;
            for (Expense depreciation : depreciations) {
                amount+= zeroIfNull(depreciation.getAmount());
            }
            if (amountDepreciations==null) {
                amountDepreciations = amount;
                updateNetValueAdded();
            } else if (amount>amountDepreciations) {
                amountDepreciations = amount;
                updateNetValueAdded();
            }
        } 
    }
    
    public void updateNetValueAdded() {
        if (production!=null & amountExpenses!=null & amountDepreciations!=null) {
            netValueAdded = production - amountExpenses - amountDepreciations;
        } else {
            netValueAdded = null;
        }
    }
    
    /* ---------- Getters - Agregats ---------- */
    
    public Double getProduction() {
        return production;
    }
    
    public Double getAmountExpenses() {
        return amountExpenses;
    }
    
    public Double getAmountDepreciations() {
        return amountDepreciations;
    }
    
    public Double getNetValueAdded() {
        return netValueAdded;
    }
    
    /* ---------- Expenses - Manager ---------- */
    
    public Expense addExpense(String siren) {
        Expense expense = new Expense(getNewExpenseId(),siren);
        expenses.add(expense);
        updateAmountExpenses();
        return expense;
    }
    
    public Expense getExpense(Integer id) {
        for (Expense expense : expenses) {
            if (id.equals(expense.getId())) {
                return expense;
            }
        }
        return null;
    }
    
    public ArrayList<Expense> getExpenses() {
        return expenses;
    }
    
    public void fetchDataExpenses() {
        for (Expense expense : expenses) {
            expense.fetchData();
        }
    }
    
    public void fetchDataExpenses(Indic indic) {
        for (Expense expense : expenses) {
            expense.fetchCSFdata(indic);
        }
    }
    
    public void removeExpense(Integer id) {
        Expense expense = getExpense(id);
        if (expense!=null) {
            expenses.remove(expense);
        }
        updateAmountExpenses();
    }
    
    public void removeExpenses() {
        expenses = new ArrayList<>();
        updateAmountExpenses();
    }
    
    /* ---------- Depreciations - Manager ---------- */
    
    public Depreciation addDepreciation(String siren) {
        Depreciation depreciation = new Depreciation(getNewDepreciationId(),siren);
        depreciations.add(depreciation);
        updateAmountDepreciations();
        return depreciation;
    }
    
    public Depreciation getDepreciation(Integer id) {
        for (Depreciation depreciation : depreciations) {
            if (id.equals(depreciation.getId())) {
                return depreciation;
            }
        }
        return null;
    }
    
    public ArrayList<Depreciation> getDepreciations() {
        return depreciations;
    }
    
    public void fetchDataDepreciations() {
        for (Depreciation depreciation : depreciations) {
            depreciation.fetchData();
        }
    }
    
    public void fetchDataDepreciations(Indic indic) {
        for (Depreciation depreciation : depreciations) {
            depreciation.fetchCSFdata(indic);
        }
    }
    
    public void removeDepreciation(Integer id) {
        Depreciation depreciation = getDepreciation(id);
        if (depreciation!=null) {
            depreciations.remove(depreciation);
        }
        updateAmountDepreciations();
    }
    
    public void removeDepreciations() {
        depreciations = new ArrayList<>();
        updateAmountDepreciations();
    }
    
    /* ---------- CSF - Manager ---------- */
    
    // Expenses 
    
    public void updateExpensesSocialFootprint() {
        expenses.forEach((expense) -> {
            expense.fetchCSFdata();
        });
    }
    
    public void updateExpensesSocialFootprint(Indic indicateur) {
        expenses.forEach((expense) -> {
            expense.fetchCSFdata(indicateur);
        });
    }
    
    // Depreciation expenses
    
    public void updateDepreciationsSocialFootprint() {
        depreciations.forEach((expense) -> {
            expense.fetchCSFdata();
        });
    }
    
    public void updateDepreciationsSocialFootprint(Indic indicateur) {
        depreciations.forEach((depreciationExpense) -> {
            depreciationExpense.fetchCSFdata(indicateur);
        });
    }
         
    /* ---------- Utils ---------- */
    
    @JsonIgnore
    private Integer getNewExpenseId() {
        Integer id = 0;
        if (expenses.size()>0) {
            ArrayList<Integer> ids = new ArrayList<>();
            expenses.forEach((expense) -> {
                ids.add(expense.getId());
            });
            id = max(ids);
        }
        return id+1;
    }
    
    @JsonIgnore
    private Integer getNewDepreciationId() {
        Integer id = 0;
        if (depreciations.size()>0) {
            ArrayList<Integer> ids = new ArrayList<>();
            depreciations.forEach((depreciation) -> {
                ids.add(depreciation.getId());
            });
            id = max(ids);
        }
        return id+1;
    }
    
    /* ----- OTHERS ----- */
    
    private static Double zeroIfNull (Double value) {
        if (value!=null) { return value; } 
        else { return 0.0; }
    }
        
}
