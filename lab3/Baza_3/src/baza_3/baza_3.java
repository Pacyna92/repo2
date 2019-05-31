package baza_3;

import GUI.GUICustomer;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class baza_3 {

    String data, sql;
    Connection polaczenie;
    Statement polecenie;
    ResultSet krotki;

    public void polaczenie_z_baza() throws SQLException {
        data = "jdbc:derby://localhost:1527/sample";
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            System.out.println("Nie mozna zaladowac sterownika");
            throw new SQLException(e.toString());
        }
        try {
            polaczenie = DriverManager.getConnection(data, "app", "app");
        } catch (SQLException e) {
            System.out.println("Nie mozna polaczyc sie z baza danych, poniewaz:" + e);
            throw e;
        }
    }

    public ArrayList<String> dane_tablicy_osob() throws SQLException {
        polecenie = polaczenie.createStatement();
        sql = "SELECT * FROM CUSTOMER ORDER BY NAME";
        krotki = polecenie.executeQuery(sql);
        ArrayList<String> osoby = new ArrayList();
        osoby.add("\nOsoby\n");
        while (krotki.next()) {
            osoby.add(krotki.getString("NAME") + "\t"
                    + krotki.getString("CITY") + "\t"
                    + krotki.getString("CREDIT_LIMIT"));
            osoby.add("\n");
        }
        polecenie.close();
        return osoby;
    }
    
    public ArrayList<String> dane_tablicy_zakupy() throws SQLException {
        polecenie = polaczenie.createStatement();
        sql = "SELECT * FROM PURCHASE_ORDER ORDER BY SALES_DATE";
        krotki = polecenie.executeQuery(sql);
        ArrayList<String> zakupy = new ArrayList();
        zakupy.add("\nZakupy\n");
        while (krotki.next()) {
            zakupy.add(krotki.getString("SALES_DATE") + "\t"
                    + krotki.getString("SHIPPING_DATE") + "\t"
                    + krotki.getString("SHIPPING_COST") + "\t"
                    + krotki.getString("QUANTITY"));
            zakupy.add("\n");
        }
        polecenie.close();
        return zakupy;
    }
    
    public ArrayList<String> dane_zakupy_osob() throws SQLException {
        polecenie = polaczenie.createStatement();
        sql = "SELECT * FROM CUSTOMER, PURCHASE_ORDER"
                + " WHERE CUSTOMER.CUSTOMER_ID = PURCHASE_ORDER.CUSTOMER_ID"
                + " ORDER BY NAME";
        krotki = polecenie.executeQuery(sql);
        ArrayList<String> zakupyosob = new ArrayList();
        zakupyosob.add("\nZakupy poszczegolnych osob\n");
        while (krotki.next()) {
            zakupyosob.add(krotki.getString("name") + "\t"
                    + krotki.getString("CREDIT_LIMIT") + "\t"
                    + krotki.getString("SALES_DATE") + "\t"
                    + krotki.getString("SHIPPING_DATE") + "\t"
                    + krotki.getString("SHIPPING_COST") + "\t"
                    + krotki.getString("QUANTITY"));
            zakupyosob.add("\n");
        }
        polecenie.close();
        return zakupyosob;
    }
    
    public String klucz_glowny(String sql1,String sql2) {
        try {
            sql = " SELECT MAX(" + sql2 + "." + sql1 + ") + 1 AS MAXID FROM " + sql2;
            krotki = polecenie.executeQuery(sql);
            krotki.next();
            String pom = krotki.getString("MAXID");
            return pom;
        } catch (SQLException e) {
            System.out.println("Blad w wyznaczaniu klucza " + e);
        }
        return null;
    }
    
    void blad(BatchUpdateException e) {
        System.out.println("Wycofanie transakcji ");
        SQLException e1 = e.getNextException();
        while(e1 != null) {
            System.out.println(e1.getMessage());
            e1 = e1.getNextException();
        }
    }
    
    public void wstaw_osobe(String[] dane) throws SQLException {
        String id_osoby;
        polaczenie.setAutoCommit(false);
        try {
            polecenie = polaczenie.createStatement();
            if((id_osoby = klucz_glowny("CUSTOMER_ID", "CUSTOMER")) == null) {
                return;
            }
            sql = "INSERT INTO CUSTOMER (customer_id, discount_code, zip, name, addressline1, "
                    + "addressline2, city, state, phone, fax, email, credit_limit)"
                    + " VALUES (" + id_osoby + ",'" + "N" + "','" + "48128" + "','" + dane[0]
                    + "','" + dane[1] + "','" + dane[2] + "','" + dane[3] + "','" + dane[4]
                    + "','" + dane[5] + "','" + dane[6] + "','" + dane[7] + "',"
                    + GUICustomer.credit_limit + ")";
            polecenie.addBatch(sql);
            polecenie.executeBatch();
            polaczenie.commit();
        } catch(BatchUpdateException e) {
            blad(e);
            polaczenie.rollback();
        }
    }

    static public void main(String arg[]) {
        baza_3 baza = new baza_3();
        ArrayList<String> lista;
        try {
            baza.polaczenie_z_baza();
            lista = baza.dane_tablicy_osob();
            System.out.println(lista);
            lista = baza.dane_zakupy_osob();
            System.out.println(lista);
            lista = baza.dane_tablicy_zakupy();
            System.out.println(lista);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            while(null != (e = e.getNextException())) {
                System.out.println(e.getMessage());
            }
        }
        GUICustomer customer = new GUICustomer();
        try {
            baza.wstaw_osobe(customer.wstaw_dane());
        } catch (SQLException ex) {
            Logger.getLogger(baza_3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
