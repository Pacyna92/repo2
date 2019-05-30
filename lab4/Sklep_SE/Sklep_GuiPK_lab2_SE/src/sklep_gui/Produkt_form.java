/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sklep_gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.PatternSyntaxException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Kruczkiewicz
 */
public class Produkt_form extends JPanel implements ActionListener  {
    JLabel lnazwa = new JLabel("Nazwa"); 		//utworzenie etykiety pola do wprowadzania nazwy produktu
    JTextField nazwa = new JTextField(15);		// utworzenie pola wejsciowego do wprowadzania nazwy produktu
    JLabel lcena = new JLabel("Cena");		 //utworzenie etykiety pola do wprowadzania ceny produktu
    JTextField cena = new JTextField(15);		 // utworzenie pola wejsciowego do wprowadzania ceny produktu
    JLabel lpromocja = new JLabel("Promocja");	 //utworzenie etykiety pola do wprowadzania promocji produktu
    JTextField promocja = new JTextField(15);		 // utworzenie pola wejsciowego do wprowadzania promocji produktu
    JLabel ldata = new JLabel("Data");		 //utworzenie etykiety pola do wprowadzania daty produkcji produktu
    JTextField data = new JTextField(15);		 // utworzenie pola wejsciowego do wprowadzania daty produkcji produktu
    JButton dodaj_produkt = new JButton("Dodaj produkt");	//utworzenie przycisku do wywołania akcji dodania produktu w aplikacji 
 
public void init() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));  	//dodanie sposobu rozmieszczania elementów formularza
        add(lnazwa);			//dodanie etykiety nazwy do obiektu typu JPanel
        add(nazwa);			//dodanie pola do wprowadzania nazwy do obiektu typu JPanel
        add(lcena);			 //dodanie etykiety ceny do obiektu typu JPanel
        add(cena);			 //dodanie pola do wprowadzania cenu do obiektu typu JPanel
        add(lpromocja);			 //dodanie etykiety promocji do obiektu typu JPanel
        add(promocja);			 //dodanie pola do wprowadzania promocji do obiektu typu JPanel
        add(ldata);			 //dodanie etykiety daty do obiektu typu JPanel
        add(data);			 //dodanie pola do wprowadzania daty do obiektu typu JPanel
        dodaj_produkt.addActionListener(this);	//przycisk uruchamiający zdarzenie po kliknieciu
		                      	 	//obslugiwany przez obiekt typu Produkt_form za pomoca metody actionPerformed
        add(dodaj_produkt);			//dodanie przycisku do obiektu typu JPanel
    }

  public String content_validate(JTextField val, int typ) {//walidacja danych 
        String s = val.getText();
        if (s.equals("") || s.length() > 15) { 		//sprawdzenie, czy lancuch jest pusty lub dluzszy niż 15 znakow
            JOptionPane.showMessageDialog(this, "Lancuch danych jest dluzszy niz 15 lub jest pusty");
            return null;
        } else {
            s = s.replaceAll(" ", "_"); 		//wyeliminowanie spacji z lancucha
            val.setText(s);
        }
        if (typ == 1) {		                          // jesli sa dane liczbowe, sprawdzenie, czy można dokonać parsowania na liczbe
            try {                  
                Float.parseFloat(s);
            } catch (NumberFormatException e) {
		 JOptionPane.showMessageDialog(this, "Blad formatu danych liczbowych");
              	 return null;
            }
        }
        return s;
    }
 public String[] form_produkt() {
        if (content_validate(nazwa, 0) == null) 			//walidacja danych nazwy jako lancucha
            return null;  
        if (content_validate(cena, 1) == null) 			//walidacja danych ceny jako liczby
            return null;
        if (content_validate(promocja, 1) == null) 		//walidacja danych ceny jako liczby
            return null;
        String dane[] = {(String) nazwa.getText(), (String) cena.getText(), (String) promocja.getText()};	//tablica z danymi produktu
        return dane;
    }

    public Date data() {
        if (content_validate(data, 0) == null)			 //walidacja danych daty jako lancucha
            return null;
        int rok, miesiac, dzien;
        String data1 = data.getText();
        try {
            String[] data2 = data1.split("-");	//podzial lancucha daty np 12-12-2016 na tablicę lancuchow, reprezentujacych elementy daty
            rok = Integer.parseInt(data2[2]);
            miesiac = Integer.parseInt(data2[1]);
            dzien = Integer.parseInt(data2[0]);
        } catch (PatternSyntaxException | NumberFormatException | ArrayIndexOutOfBoundsException e) {        //kontrola poprawności 	 	JOptionPane.showMessageDialog(this, "Blad formatu daty");		         //formatu daty
                          return null;
        }
        GregorianCalendar gc = new GregorianCalendar();		//pomocnicza klasa do utworzenia daty 
        gc.set(rok, miesiac - 1, dzien);			//tworzenie daty
        return gc.getTime();				//pobranie daty jako obiektu typu Date
    }
 @Override
    public void actionPerformed(ActionEvent evt) {	//obsluga zdarzenia kliknięcia na przycisk "Dodaj_produkt"
        String[] dane = form_produkt(); 		//utworzenie tablicy z danymi produktu: nazwa, cena, promocja
        if (dane == null) {
            return;
        }
        Date data_ = data();			//utworzenie daty
        if (data_ == null) {
            return;
        }
        GUI_main.getFacade().utworz_produkt(dane, data_);	// wywołanie metody logiki biznesowej tworzacej obiekt typu Produkt1
    }
}
