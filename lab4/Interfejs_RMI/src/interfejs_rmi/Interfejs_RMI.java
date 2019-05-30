/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfejs_rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author bartek
 */
public interface Interfejs_RMI extends Remote {
    public void utworz_produkt(String dane[], Date data) throws RemoteException;
    public String[] dane_produktu() throws RemoteException;
    public ArrayList<ArrayList<String>> items() throws RemoteException;
}
