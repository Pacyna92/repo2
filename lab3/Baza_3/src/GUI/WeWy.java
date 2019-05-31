/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author bartek
 */
public class WeWy {
    static public String weString(String menu) {
        InputStreamReader wejscie = new InputStreamReader(System.in);
        BufferedReader bufor = new BufferedReader(wejscie);
        try {
            System.out.print(menu);
            return bufor.readLine();
        } catch(IOException e) {
            System.err.println("Blad IO String");
            return "";
        }
    }
    
    static public void wyString(ArrayList<String> lista) {
        lista.forEach((wiersz) -> {
            System.out.println(wiersz);
        });
    }
}
