/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bank;
/**
 *
 * @author Marta Kossowski martakossowski@gmail.com 2018-05-08
 * Program wyświetlający średni kurs kupna waluty oraz odchylenie standardowe
 * z podanego przedziału czasowego. Przykładowe poprawne dane wejściowe: 
 * EUR 2017-11-20 2017-11-24
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;
public class Bank {
    
      private static String readAll(Reader rd) throws IOException {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);
    }
    return sb.toString();
  }    

  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
    InputStream is = new URL(url).openStream();
    try {
      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
      String jsonText = readAll(rd);
      JSONObject json = new JSONObject(jsonText);
      return json;
    } finally {
      is.close();
    }
  }

  public static void main(String[] args) throws IOException, JSONException {
    String data[] = null;
    Scanner scanner = new Scanner(System.in);
    
    System.out.println("Proszę wpisać symbol waluty oraz przedział czasowy");
    System.out.println("Przykład: EUR 2017-11-20 2017-11-24");
  
    String output = scanner.nextLine();
    data = output.split(" ");
    JSONObject json = readJsonFromUrl("http://api.nbp.pl/api/exchangerates/rates/C/"+data[0]+"/"+data[1]+"/"+data[2]+"/?format=json");
    Statistics statistics = new Statistics(json);
        
    System.out.format("%.4f%n", statistics.getMean("bid"));
    System.out.format("%.4f%n", statistics.getStdDev("ask"));
  }
}