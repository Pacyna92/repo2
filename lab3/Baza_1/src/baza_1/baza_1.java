package baza_1;

import java.sql.*;
import java.util.ArrayList;

public class baza_1 {

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
        ResultSetMetaData metaDane = krotki.getMetaData();
        int kolumny = metaDane.getColumnCount();
        ArrayList<String> listakolumn=new ArrayList();
        for (int i = 0; i < kolumny; i++) {
         listakolumn.add("Nazwa kolumny " + i + " " + metaDane.getColumnName(i + 1));
         listakolumn.add("\n");
        }
       listakolumn.add("\n");
        for (int i = 1; i < kolumny - 1; i++) {
            listakolumn.add(metaDane.getColumnName(i + 1) + "\t");
        }
        listakolumn.add("\n");
        while (krotki.next()) {
            listakolumn.add(krotki.getString("NAME") + "\t"
                    + krotki.getString("CITY") + "\t"
                    + krotki.getString("CREDIT_LIMIT"));
              listakolumn.add("\n");
        }
        polecenie.close();
        return listakolumn;
    }

    static public void main(String arg[]) {
        baza_1 baza = new baza_1();
        try {
            baza.polaczenie_z_baza();
            ArrayList<String> listakolumn=baza.dane_tablicy_osob();
            System.out.println(listakolumn);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            while (null != (e = e.getNextException())) {
                System.out.println(e.getMessage());
            }
        }
    }
}
