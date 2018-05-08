/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bank;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Marta
 */
public class Statistics {
   
    private JSONObject json;
    private int size;   

    public Statistics(JSONObject json) {
        this.json = json;
        size = json.length();
    }   
    
    double getMean(String meanOf) throws JSONException {
        double sumPurchasePrice = 0;
        for (int i = 0; i < size; i++)
            sumPurchasePrice += json.getJSONArray("rates").getJSONObject(i).getDouble(meanOf);

        return sumPurchasePrice/size;
    }

    double getVariance(String varianceOf) throws JSONException { 
        double mean = getMean(varianceOf);
        double temp = 0;
        double value = 0;
        
        for (int i = 0; i < size; i++){
            value = json.getJSONArray("rates").getJSONObject(i).getDouble(varianceOf);
            temp += (value - mean)*(value - mean);
        }
        return temp/(size-1);
    } 
     double getStdDev(String stdDevOf) throws JSONException { 
         return Math.sqrt(getVariance(stdDevOf));
    }
}