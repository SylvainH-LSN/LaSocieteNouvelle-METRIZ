/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author SylvainPro
 */
public class LaSocieteNouvelleAPI {
    
    private static final String urlAPI = "http://api.lasocietenouvelle.org/api/v2";
    
    public static ResponseAPI fetchDataSiren(String siren) {
        
        ResponseAPI response = new ResponseAPI();
        
        if (siren!=null & !siren.equals("")) 
        {
            String urlText = urlAPI + "/siren/"+siren;
            JSONObject jsonResponse = executeRequest(urlText);
            if (jsonResponse!=null) { response.buildResponseAPI(jsonResponse); }
        }
        
        return response;
    }
    
    public static ResponseDefaultData fetchDefaultData(String geo, String activite) {
        ResponseDefaultData response = new ResponseDefaultData();
        
        String urlText = urlAPI + "/default?pays="+geo+"&activite="+activite;
        JSONObject jsonResponse = executeRequest(urlText);
        
        if (jsonResponse!=null) { response.setJSON(jsonResponse); }
        return response;
    }
    
    private static JSONObject executeRequest(String request) {
        System.out.println(request);
        try {
            URL url = new URL(request);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
           
            StringBuilder result;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                result = new StringBuilder();
                String line;
                while ( (line = br.readLine()) != null ) {
                    result.append(line);
                }
            }
            String jsonText = result.toString();
            JSONObject json = new JSONObject(jsonText);
            return json;
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(LaSocieteNouvelleAPI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LaSocieteNouvelleAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        return null;
    }
    
}
