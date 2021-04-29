/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataInput;

import etude.Depreciation;
import etude.Etude;
import etude.Expense;
import java.awt.FileDialog;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JDialog;
import static utils.Utils.readValue;

/**
 *
 * @author SylvainPro
 */
public class ImportFile {
    
    public static String getFilePath() {
        FileDialog fileDialog = new FileDialog(new JDialog(),"Importation",FileDialog.LOAD);
        fileDialog.setDirectory("C:\\");
        fileDialog.setFile("*.csv");
        fileDialog.setVisible(true);
        return fileDialog.getDirectory()+fileDialog.getFile();
    }
    
    public static void readExpenses(Etude etude, String filePath, String delimiter) throws FileNotFoundException, IOException {
                
        FileReader file = new FileReader(filePath);
        BufferedReader reader = new BufferedReader(file);
        
        int idxCompanyId = -1;
        int idxCompanyName = -1;
        int idxAmount = -1;
        
        String row = reader.readLine();
        int idx = 0;
        for (String header : row.split(delimiter)) {
            switch (header.replace("\"", "")) {
                case "company_id": idxCompanyId = idx; break;
                case "company_name": idxCompanyName = idx; break;
                case "amount": idxAmount = idx; break;
                default: break;
            }
            idx++;
        }
        if (idxCompanyId > -1)
        {
            while( (row = reader.readLine()) !=null ) 
            {
                String[] objects = row.split(delimiter);
                
                String companyId = objects[idxCompanyId].replace("\"","");
                Expense expense = etude.getFinancialData().addExpense(companyId);
                
                if (idxAmount > -1) {
                    Double amount = readValue(objects[idxAmount].replace("\"",""));
                    expense.setAmount(amount);
                }
                
                if (idxCompanyName > -1) {
                    String companyName = objects[idxCompanyName].replace("\"","");
                    expense.setCorporateName(companyName);
                }
                
            }
        }
        etude.save();
    }
    
    public static void readDepreciations(Etude etude, String filePath, String delimiter) throws FileNotFoundException, IOException {
                
        FileReader file = new FileReader(filePath);
        BufferedReader reader = new BufferedReader(file);
        
        int idxCompanyId = -1;
        int idxCompanyName = -1;
        int idxAmount = -1;
        int idxYear = -1;
        
        String row = reader.readLine();
        int idx = 0;
        for (String header : row.split(delimiter)) {
            switch (header.replace("\"","")) {
                case "company_id": idxCompanyId = idx; break;
                case "company_name": idxCompanyName = idx; break;
                case "amount": idxAmount = idx; break;
                case "year": idxYear = idx; break;
                default: break;
            }
            idx++;
        }
        
        if (idxCompanyId > -1)
        {
            while( (row = reader.readLine()) !=null ) 
            {
                String[] objects = row.split(delimiter);
                
                String companyId = objects[idxCompanyId].replace("\"","");
                Depreciation depreciation = etude.getFinancialData().addDepreciation(companyId);
                
                if (idxAmount > -1) {
                    Double amount = readValue(objects[idxAmount].replace("\"",""));
                    depreciation.setAmount(amount);
                }
                
                if (idxCompanyName > -1) {
                    String companyName = objects[idxCompanyName].replace("\"","");
                    depreciation.setCorporateName(companyName);
                }
                
                if (idxYear > -1) {
                    String year = objects[idxYear].replace("\"","");
                    depreciation.setYear(year);
                }
                
            }
        }
    }
    
}
