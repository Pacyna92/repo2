/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obiekt_rmi;

import interfejs_rmi.Interfejs_RMI;
import java.util.ArrayList;
import java.util.Date;
import warstwa_biznesowa.Fasada_warstwy_biznesowej;

/**
 *
 * @author bartek
 */
public class Obiekt_RMI implements Interfejs_RMI {

    private final Fasada_warstwy_biznesowej fasada = new Fasada_warstwy_biznesowej();
    @Override
    public void utworz_produkt(String[] dane, Date data) {
        fasada.utworz_produkt(dane, data);
    }

    @Override
    public String[] dane_produktu() {
        return fasada.dane_produktu();
    }

    @Override
    public ArrayList<ArrayList<String>> items() {
        return fasada.items();
    }
    
}
