/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import components.Button;
import tables.TableMainIndicator;
import static tables.TableNetValueAddedIndicateur.getTableNetValueAddedIndicateur;
import etude.Etude;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import meta.Indic;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import tables.TableDepreciationsIndicateur;
import tables.TableExpensesIndicateur;
import tables.TableNetValueAddedIndicateur;
import manager.EtudeSession;

/**
 *
 * @author SylvainPro
 */
public class VueIndicateur extends Vue implements ActionListener {
    
    private static final String RECAP = "Soldes intermédiaires";
    private static final String VALUE_ADDED = "Valeur Ajoutée Nette";
    private static final String EXPENSES = "Charges externes";
    private static final String DEPRECIATIONS = "Dotations aux amortissements";
    
    protected Indic indicateur;
    
    protected JPanel tabs;
    
    protected TableMainIndicator tableMain;
    protected TableNetValueAddedIndicateur tableNetValueAdded;
    protected TableExpensesIndicateur expensesTable;
    protected TableDepreciationsIndicateur depreciationsTable;
    
    protected final Button btnRecap = new Button("Recapitulatif", this);
    protected final Button btnValueAdded = new Button("Valeur Ajoutée Nette", this);
    protected final Button btnExpenses = new Button("Charges externes", this);
    protected final Button btnDepreciations = new Button("Amortissements", this);
    
    protected final Button btnSyncExpenses = new Button("Tout synchroniser", 250, 20, this);
    protected final Button btnSyncDepreciations = new Button("Tout synchroniser", 250, 20, this);
        
    /* ---------- Constructor ---------- */
    
    public VueIndicateur(EtudeSession session,Indic indicateur) {
        super(session);
        this.indicateur = indicateur;
        
        title.setText(indicateur.getLibelle());
        buildMain();
        build();
    }
    
    private void buildMain() {
        
        JPanel headerTabs = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        headerTabs.add(btnRecap);
        headerTabs.add(btnValueAdded);
        headerTabs.add(btnExpenses);
        headerTabs.add(btnDepreciations);
        headerTabs.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        headerTabs.setBackground(Color.WHITE);
        
        main.add(headerTabs, BorderLayout.PAGE_START);
        
        tabs = new JPanel(new CardLayout());
        
        JPanel mainPanel = buildTabMain();        
        JPanel valueAddedPanel = buildTabValueAdded();
        JPanel expensesPanel = buildTabExpenses();
        JPanel depreciationsPanel = buildTabDepreciations();
        
        tabs.add(mainPanel, RECAP);
        tabs.add(valueAddedPanel, VALUE_ADDED);
        tabs.add(expensesPanel, EXPENSES);
        tabs.add(depreciationsPanel, DEPRECIATIONS);
                
        main.add(tabs, BorderLayout.CENTER);
        
        btnValueAdded.toNegative();
        btnExpenses.toNegative();
        btnDepreciations.toNegative();
    }
    
    /* ---------- Tabs ---------- */
    
    // Main tab
    
    protected JPanel buildTabMain() {
        
        JPanel tab = new JPanel(new GridLayout(1,0));
        tableMain = new TableMainIndicator(indicateur,etude);
        tableMain.setBorder(BorderFactory.createLineBorder(new Color(100,100,100), 1));
        JScrollPane scrollPane = new JScrollPane(tableMain);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        tab.add(scrollPane);
        
        return tab;
    }
   
    
    // Net value added tab
    
    private JPanel buildTabValueAdded(){
          
        JPanel tab = new JPanel(new GridLayout(1,0));
        tableNetValueAdded = getTableNetValueAddedIndicateur(indicateur,etude);
        tableNetValueAdded.setBorder(BorderFactory.createLineBorder(new Color(100,100,100), 1));
        JScrollPane scrollPane = new JScrollPane(tableNetValueAdded);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        tab.add(scrollPane);
        
        return tab;
    }
    
    protected JPanel buildTabExpenses() {
        
        JPanel tab = new JPanel(new BorderLayout());
        
        expensesTable = new TableExpensesIndicateur(indicateur, etude);
        expensesTable.setBorder(BorderFactory.createLineBorder(new Color(100,100,100), 1));
        JScrollPane scrollPane = new JScrollPane(expensesTable);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        tab.add(scrollPane, BorderLayout.PAGE_START);
        
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttons.add(btnSyncExpenses);
        buttons.setBackground(Color.WHITE);
        tab.add(buttons, BorderLayout.PAGE_END);
        
        tab.setBackground(Color.WHITE);
        return tab;
    }
    
    // Immobilisations tab
    
    private JPanel buildTabDepreciations() {
        
        JPanel tab = new JPanel(new BorderLayout());
        
        depreciationsTable = new TableDepreciationsIndicateur(indicateur, etude);
        depreciationsTable.setBorder(BorderFactory.createLineBorder(new Color(100,100,100), 1));
        JScrollPane scrollPane = new JScrollPane(depreciationsTable);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        tab.add(scrollPane, BorderLayout.PAGE_START);
        
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttons.add(btnSyncDepreciations);
        buttons.setBackground(Color.WHITE);
        tab.add(buttons, BorderLayout.PAGE_END);
        
        tab.setBackground(Color.WHITE);
        return tab;
    }
    
    /* ----- ACTIONS ----- */
    
    @Override
    public Vue action(ActionEvent e,Etude etude) {
        // Production
        // Valeur Ajoutée Nette
        return this;
    }
     
    @Override
    public void updateVue() {
        buildMain();
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource()==btnRecap) 
        {
            tableMain.updateContent();
            btnRecap.toPositive();
            btnValueAdded.toNegative();
            btnExpenses.toNegative();
            btnDepreciations.toNegative();
            CardLayout layout = (CardLayout) tabs.getLayout();
            layout.show(tabs, RECAP);
        }
        else if (e.getSource()==btnValueAdded)
        {
            tableNetValueAdded.updateContent();
            btnRecap.toNegative();
            btnValueAdded.toPositive();
            btnExpenses.toNegative();
            btnDepreciations.toNegative();
            CardLayout layout = (CardLayout) tabs.getLayout();
            layout.show(tabs, VALUE_ADDED);
        }
        else if (e.getSource()==btnExpenses)
        {
            expensesTable.updateContent();
            btnRecap.toNegative();
            btnValueAdded.toNegative();
            btnExpenses.toPositive();
            btnDepreciations.toNegative();
            CardLayout layout = (CardLayout) tabs.getLayout();
            layout.show(tabs, EXPENSES);
        }
        else if (e.getSource()==btnDepreciations)
        {
            depreciationsTable.updateContent();
            btnRecap.toNegative();
            btnValueAdded.toNegative();
            btnExpenses.toNegative();
            btnDepreciations.toPositive();
            CardLayout layout = (CardLayout) tabs.getLayout();
            layout.show(tabs, DEPRECIATIONS);
        }
        else if (e.getSource()==btnSyncExpenses) {
            etude.getFinancialData().fetchDataExpenses(indicateur);
        }
        else if (e.getSource()==btnSyncDepreciations) {
            etude.getFinancialData().fetchDataDepreciations(indicateur);
        }
    }
    
    
    /* ----- UTILS ----- */
    
    protected static Double readDouble (Object object) {
        String string = object.toString();
        if (!string.equals("")) {
            return Double.parseDouble(string);
        } else {
            return null;
        }
    }
    
       
}
