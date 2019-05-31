/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

/**
 *
 * @author bartek
 */
public class GUICustomer {
    public String name, addressline1, addressline2, city, state, phone, fax, email;
    static public int credit_limit = 100;
    
    public String[] wstaw_dane() {
        name = WeWy.weString("Podaj nazwisko: ");
        addressline1 = WeWy.weString("Podaj adres1: ");
        addressline2 = WeWy.weString("Podaj adres2: ");
        city = WeWy.weString("Podaj miasto: ");
        state = WeWy.weString("Podaj stan: ");
        phone = WeWy.weString("Podaj numer telefonu: ");
        fax = WeWy.weString("Podaj numer faksu: ");
        email = WeWy.weString("Podaj email: ");
        credit_limit++;
        String[] dane = {name, addressline1, addressline2, city, state, phone, fax, email};
        return dane;
    }
}
