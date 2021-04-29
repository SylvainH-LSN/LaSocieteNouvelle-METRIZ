/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tables;

import renderers.DefaultCellRenderer;
import renderers.RendererValueInput;
import lib.indicators.art.TableART;
import lib.indicators.eco.TableECO;
import meta.Indic;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import lib.indicators.dis.TableDIS;
import lib.indicators.geq.TableGEQ;
import lib.indicators.ghg.TableGHG;
import lib.indicators.haz.TableHAZ;
import lib.indicators.knw.TableKNW;
import lib.indicators.mat.TableMAT;
import lib.indicators.nrg.TableNRG;
import lib.indicators.soc.TableSOC;
import lib.indicators.was.TableWAS;
import lib.indicators.wat.TableWAT;
import etude.Etude;

/**
 *
 * @author SylvainPro
 */
public abstract class TableNetValueAddedIndicateur extends Table {
    
    protected static final Integer INDEX_LIBELLE = 0;
    protected static final Integer INDEX_VALUES = 1;
    protected static final Integer INDEX_UNCERTAINTY = 2;
    
    protected Etude etude;
    
    protected Boolean ignoreNextChanges = false;
    
    public static TableNetValueAddedIndicateur getTableNetValueAddedIndicateur(Indic indic, Etude etude) {
        switch(indic)
        {
            case ART: return new TableART(etude);
            case DIS: return new TableDIS(etude);
            case ECO: return new TableECO(etude);
            case GEQ: return new TableGEQ(etude);
            case GHG: return new TableGHG(etude);
            case HAZ: return new TableHAZ(etude);
            case KNW: return new TableKNW(etude);
            case MAT: return new TableMAT(etude);
            case NRG: return new TableNRG(etude);
            case SOC: return new TableSOC(etude);
            case WAS: return new TableWAS(etude);
            case WAT: return new TableWAT(etude);
            default : return null;
        }
    }
    
    public TableNetValueAddedIndicateur(DefaultTableModel model, Etude etude) {
        
        super(model);
                
        this.etude = etude;
                
        setRenderers();
        
        if (getColumnCount()==2) {
            getColumnModel().getColumn(0).setPreferredWidth(400);
            getColumnModel().getColumn(1).setPreferredWidth(50);
        } else if (getColumnCount()==3) {
            getColumnModel().getColumn(0).setPreferredWidth(350);
            getColumnModel().getColumn(1).setPreferredWidth(50);
            getColumnModel().getColumn(2).setPreferredWidth(50);
        }
        
    }
    
    protected void setRenderers() {
        getColumnModel().getColumn(0).setCellRenderer(new DefaultCellRenderer());
        getColumnModel().getColumn(1).setCellRenderer(new RendererValueInput(0, new int[]{getRowCount()-2,getRowCount()-1}));
        if (getColumnCount()==3) {
            getColumnModel().getColumn(2).setCellRenderer(new RendererValueInput(0, new int[]{getRowCount()-2,getRowCount()-1}));
        }
    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
        return column > 0 & row < getRowCount();
    }
    
    @Override
    public void tableChanged(TableModelEvent e) {
        super.tableChanged(e);
        if (e.getType()==TableModelEvent.UPDATE & e.getFirstRow()>=0) 
        {
            handleChanged(e);
            etude.save();
        } 
        
    }
    
    protected abstract void handleChanged(TableModelEvent e);
    
    public abstract void updateContent();
}
