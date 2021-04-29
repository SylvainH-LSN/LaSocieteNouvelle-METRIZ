/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib.indicators;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import meta.Indic;
import etude.Indicator;
import lib.indicators.wat.IndicateurWAT;
import lib.indicators.eco.IndicateurECO;
import lib.indicators.was.IndicateurWAS;
import lib.indicators.soc.IndicateurSOC;
import lib.indicators.nrg.IndicateurNRG;
import lib.indicators.mat.IndicateurMAT;
import lib.indicators.knw.IndicateurKNW;
import lib.indicators.haz.IndicateurHAZ;
import lib.indicators.ghg.IndicateurGHG;
import lib.indicators.art.IndicateurART;
import lib.indicators.geq.IndicateurGEQ;
import lib.indicators.dis.IndicateurDIS;
import java.util.HashMap;

/**
 *
 * @author SylvainPro
 */

@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME, 
  include = JsonTypeInfo.As.PROPERTY, 
  property = "type")
@JsonSubTypes({ 
  @Type(value = IndicateurART.class, name = "IndicateurART"), 
  @Type(value = IndicateurDIS.class, name = "IndicateurDIS"),
  @Type(value = IndicateurECO.class, name = "IndicateurECO"),
  @Type(value = IndicateurGEQ.class, name = "IndicateurGEQ"),
  @Type(value = IndicateurGHG.class, name = "IndicateurGHG"),
  @Type(value = IndicateurHAZ.class, name = "IndicateurHAZ"),
  @Type(value = IndicateurKNW.class, name = "IndicateurKNW"),
  @Type(value = IndicateurMAT.class, name = "IndicateurMAT"),
  @Type(value = IndicateurNRG.class, name = "IndicateurNRG"),
  @Type(value = IndicateurSOC.class, name = "IndicateurSOC"),
  @Type(value = IndicateurWAS.class, name = "IndicateurWAS"),
  @Type(value = IndicateurWAT.class, name = "IndicateurWAT")
})
public abstract class IndicateurNetValueAdded extends Indicator {

    public IndicateurNetValueAdded() {
        super();
    }
    
    public abstract void updateValue(Double netValueAdded);
    
    public static HashMap<Indic,IndicateurNetValueAdded> buildImpactsDirects() {
        HashMap<Indic,IndicateurNetValueAdded> impacts = new HashMap<>();
        
        impacts.put(Indic.ART, new IndicateurART());
        impacts.put(Indic.DIS, new IndicateurDIS());
        impacts.put(Indic.ECO, new IndicateurECO());
        impacts.put(Indic.GEQ, new IndicateurGEQ());
        impacts.put(Indic.GHG, new IndicateurGHG());
        impacts.put(Indic.HAZ, new IndicateurHAZ());
        impacts.put(Indic.KNW, new IndicateurKNW());
        impacts.put(Indic.MAT, new IndicateurMAT());
        impacts.put(Indic.NRG, new IndicateurNRG());
        impacts.put(Indic.SOC, new IndicateurSOC());
        impacts.put(Indic.WAS, new IndicateurWAS());
        impacts.put(Indic.WAT, new IndicateurWAT());
        
        return impacts;
    }
    
}
