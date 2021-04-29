/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import dataInput.ImportFile;
import components.Button;
import tables.TableDepreciations;
import tables.TableMainFinancialData;
import tables.TableExpenses;
import etude.Etude;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import manager.EtudeSession;

/**
 *
 * @author SylvainPro
 */
public class VueDonneesFinancieres extends Vue implements ActionListener {
    
    private static final String SOLDES_INTERMEDIAIRES = "Soldes intermédiaires";
    private static final String EXPENSES = "Charges externes";
    private static final String DEPRECIATIONS = "Dotations aux amortissements";
    
    private JPanel tabs;
    
    private TableMainFinancialData tableMain;
    private TableExpenses tableExpenses;
    private TableDepreciations tableDepreciations;
    
    private final Button btnSoldesIntermediaires = new Button("Soldes intermédiaires", 250, 20, this);
    private final Button btnExpenses = new Button("Charges externes", 250, 20, this);
    private final Button btnDepreciations = new Button("Dotations aux amortissements", 250, 20, this);
    
    protected final Button btnImportExpenses = new Button("Importer", 250, 20, this);
    protected final Button btnSyncExpenses = new Button("Tout syncrhoniser", 250, 20, this);
    protected final Button btnDelExpenses = new Button("Tout supprimer", 250, 20, this);
    
    protected final Button btnImportDepreciations = new Button("Importer", 250, 20, this);
    protected final Button btnSyncDepreciations = new Button("Tout synchroniser", 250, 20, this);
    protected final Button btnDelDepreciations = new Button("Tout supprimer", 250, 20, this);
        
    /* ---------- Constructor ---------- */
    
    public VueDonneesFinancieres (EtudeSession session) {
        super(session);
        title.setText("Informations - Données financières");
        
        buildMain();
        build();
    }
    
    private void buildMain() {
        
        btnExpenses.toNegative();
        btnDepreciations.toNegative();
        
        JPanel headerTabs = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        headerTabs.add(btnSoldesIntermediaires);
        headerTabs.add(btnExpenses);
        headerTabs.add(btnDepreciations);
        headerTabs.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        headerTabs.setBackground(Color.WHITE);
        
        main.add(headerTabs, BorderLayout.PAGE_START);
        
        tabs = new JPanel(new CardLayout());
        
        JPanel mainPanel = buildTabMain();        
        JPanel expensesPanel = buildTabExpenses(etude);
        JPanel depreciationsPanel = buildPanelDepreciations(etude);
        
        tabs.add(mainPanel, SOLDES_INTERMEDIAIRES);
        tabs.add(expensesPanel, EXPENSES);
        tabs.add(depreciationsPanel, DEPRECIATIONS);
                        
        main.add(tabs, BorderLayout.CENTER);
    }
    
    /* ---------- Tabs ---------- */
    
    // Main tab
    private JPanel buildTabMain() {
        JPanel tab = new JPanel();
        tab.setLayout(new GridLayout(0,1));
        
        tableMain = new TableMainFinancialData(etude);
        tableMain.setBorder(BorderFactory.createLineBorder(new Color(100,100,100), 1));
        JScrollPane scrollPane = new JScrollPane(tableMain);
        scrollPane.getViewport().setBackground(Color.white);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());;
        tab.add(scrollPane);
        
        return tab;
    }
    
    // Expenses tab
    private JPanel buildTabExpenses(Etude etude) {
        JPanel tab = new JPanel();
        tab.setLayout(new BorderLayout());
        
        tableExpenses = new TableExpenses(etude);
        tableExpenses.setBorder(BorderFactory.createLineBorder(new Color(100,100,100), 1));
        JScrollPane scrollPane = new JScrollPane(tableExpenses);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        tab.add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttons.add(btnImportExpenses);
        buttons.add(btnSyncExpenses);
        buttons.add(btnDelExpenses);
        buttons.setBackground(Color.WHITE);
        tab.add(buttons, BorderLayout.PAGE_END);
        
        tab.setBackground(Color.white);
        return tab;
    }
    
    // Depreciations tab
    
    private JPanel buildPanelDepreciations(Etude etude) {
        JPanel tab = new JPanel();
        tab.setLayout(new BorderLayout());
        
        tableDepreciations = new TableDepreciations(etude);
        tableDepreciations.setBorder(BorderFactory.createLineBorder(new Color(100,100,100), 1));
        JScrollPane scrollPane = new JScrollPane(tableDepreciations);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());;
        tab.add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttons.add(btnImportDepreciations);
        buttons.add(btnSyncDepreciations);
        buttons.add(btnDelDepreciations);
        buttons.setBackground(Color.WHITE);
        tab.add(buttons, BorderLayout.PAGE_END);
        
        tab.setBackground(Color.white);
        return tab;
    }
    
    /* ----- ACTIONS ----- */
    
    @Override
    public Vue action(ActionEvent e,Etude etude) {
        return this;
    }
    
    @Override
    public void updateVue() {
        buildMain();
        build();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource()==btnSoldesIntermediaires) 
        {
            tableMain.updateContent();
            btnSoldesIntermediaires.toPositive();
            btnExpenses.toNegative();
            btnDepreciations.toNegative();
            CardLayout layout = (CardLayout) tabs.getLayout();
            layout.show(tabs, SOLDES_INTERMEDIAIRES);
        }
        else if (e.getSource()==btnExpenses)
        {
            tableExpenses.updateContent();
            btnSoldesIntermediaires.toNegative();
            btnExpenses.toPositive();
            btnDepreciations.toNegative();
            CardLayout layout = (CardLayout) tabs.getLayout();
            layout.show(tabs, EXPENSES);
        }
        else if (e.getSource()==btnDepreciations)
        {
            tableDepreciations.updateContent();
            btnSoldesIntermediaires.toNegative();
            btnExpenses.toNegative();
            btnDepreciations.toPositive();
            CardLayout layout = (CardLayout) tabs.getLayout();
            layout.show(tabs, DEPRECIATIONS);
        }
        else if (e.getSource()==btnImportExpenses) {
            try {
                String filePath = ImportFile.getFilePath();
                if (!filePath.equals("nullnull")) {
                    ImportFile.readExpenses(etude, filePath, ",");
                }
                tableExpenses.updateContent();
            } catch (IOException ex) {
                Logger.getLogger(VueDonneesFinancieres.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if (e.getSource()==btnSyncExpenses) {
            etude.getFinancialData().fetchDataExpenses();
            tableExpenses.updateContent();
        }
        else if (e.getSource()==btnDelExpenses) {
            etude.getFinancialData().removeExpenses();
            tableExpenses.updateContent();
        }
        else if (e.getSource()==btnImportDepreciations) {
            try {
                String filePath = ImportFile.getFilePath();
                if (!filePath.equals("nullnull")) {
                    ImportFile.readDepreciations(etude, filePath, ";");
                }
                tableDepreciations.updateContent();
            } catch (IOException ex) {
                Logger.getLogger(VueDonneesFinancieres.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if (e.getSource()==btnSyncDepreciations) {
            etude.getFinancialData().fetchDataDepreciations();
            tableDepreciations.updateContent();
        }
        else if (e.getSource()==btnDelDepreciations) {
            etude.getFinancialData().removeDepreciations();
            tableDepreciations.updateContent();
        }
    }

    
}
