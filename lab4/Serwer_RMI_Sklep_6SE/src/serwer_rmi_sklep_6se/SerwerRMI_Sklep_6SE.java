/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serwer_rmi_sklep_6se;

import interfejs_rmi.Interfejs_RMI;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import obiekt_rmi.Obiekt_RMI;

/**
 *
 * @author bartek
 */
public class SerwerRMI_Sklep_6SE {
    public static void main(String[] args) {
         if(System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
            }
         try {
             Interfejs_RMI obiekt = new Obiekt_RMI();
             Interfejs_RMI stub = (Interfejs_RMI) UnicastRemoteObject.exportObject(obiekt, 0);
             Registry registry = LocateRegistry.createRegistry(5002);
             registry.rebind("RMI_Sklep", stub);
             System.out.println("Serwer przygotowany do RMI");
         } catch (RemoteException e) {
             System.out.println("Wyjatek serwera 2: " + e);
         }
    } 
}
