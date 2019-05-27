/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package watki1;

import java.util.ArrayList;

/**
 *
 * @author kruczkiewicz
 */
class Wspolny_zasob {

    ArrayList<String> komunikaty = new ArrayList<>();

    synchronized void dodaj(String nowy) {
        try {
            if (komunikaty.size() > 0) {
                System.out.println("bwait1");
                wait();
                System.out.println("await1");
            }
            komunikaty.add(nowy);
            if(komunikaty.size() == 2) {
                System.out.println(komunikaty.toString());
            }
            Thread.sleep(3000);
        } catch (Exception e) {
        }
    }

    synchronized void usun() {
        try {
            if (komunikaty.size() > 0) {
                komunikaty.clear();
                System.out.println("bnotify");
                notify();
            }
        } catch (Exception e) {
        }
    }
}

class Watek1 extends Thread {

    int i = 0;
    Wspolny_zasob p1;

    public Watek1(Wspolny_zasob p) {
        p1 = p;
    }

    @Override
    public void run() {
        while (true) {
            p1.dodaj("nowy" + (++i));
        }

    }
}

class Watek2 extends Thread {

    Wspolny_zasob p1;

    public Watek2(Wspolny_zasob p) {
        p1 = p;
    }

    @Override
    public void run() {
        while (true) {
            p1.usun();
        }
    }
}

public class Watek_1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Wspolny_zasob w1 = new Wspolny_zasob();
        Thread wt1 = new Thread(new Watek1(w1));
        wt1.start();
        Thread wt2 = new Thread(new Watek2(w1));
        wt2.start();

    }
}
