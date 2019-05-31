/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 *
 * @author bartek
 */
public class GUIPurchase_order {
    private String quantity, shipping_cost, freight_company, name, kod_produktu, sales_date,
                   shipping_date;
    
    public String wstaw_date(String komunikat) {
        Date pom1 = new Date();
        String pom2 = WeWy.weString(komunikat);
        pom1.setTime(pom1.getTime() + Long.parseLong(pom2) * 24 * 60 * 60 * 1000);
        SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd");
        String data = format.format(pom1.getTime());
        return data;
    }
    
    public String[] wstaw_dane_zlecenia() {
        sales_date = wstaw_date("Podaj date sprzedazy (za ile dni): ");
        shipping_date = wstaw_date("Podaj date wysylki (za ile dni): ");
        quantity = WeWy.weString("Podaj koszt zakupu: ");
        shipping_cost = WeWy.weString("Podaj koszt wysylki: ");
        freight_company = WeWy.weString("Podaj firme przewozowa: ");
        name = WeWy.weString("Podaj nazwisko osoby: ");
        kod_produktu = WeWy.weString("Podaj kod produktu: ");
        String[] dane = {name, kod_produktu, quantity, shipping_cost, sales_date, shipping_date,
                         freight_company};
        return dane;
    }
}
