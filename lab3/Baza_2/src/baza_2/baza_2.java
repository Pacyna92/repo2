package baza_2;

import java.sql.*;
import java.util.ArrayList;

public class baza_2 {

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

    static public void main(String arg[]) {
        baza_2 baza = new baza_2();
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
    }
}
