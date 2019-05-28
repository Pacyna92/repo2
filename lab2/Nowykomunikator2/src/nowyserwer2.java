import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class nowyserwer2 implements Runnable {
    private ServerSocket serwer;
    
    public nowyserwer2(int port_) {
        try {
            serwer = new ServerSocket(port_);
        } catch (IOException e) {
            System.out.println(e); }
    }

    public void run() {
        Socket gniazdo_klienta = null;
        ObjectOutputStream wyjscie;
        ObjectInputStream wejscie;
        while (true) {
            try {
                gniazdo_klienta = serwer.accept();  //oczekiwanie na klienta
                if (gniazdo_klienta == null) {
                    System.out.println("Minal czas akceptacji");
                    continue;
                }
                wyjscie = new ObjectOutputStream(gniazdo_klienta.getOutputStream());
                wejscie = new ObjectInputStream(gniazdo_klienta.getInputStream());
                komponent_nowegoklienta2 komp_klienta =
                        new komponent_nowegoklienta2(gniazdo_klienta, wejscie, wyjscie);
                Thread watek_komponentu_klienta = new Thread(komp_klienta);
                watek_komponentu_klienta.start();
            } catch (IOException e) {
                System.out.println("Nie mozna polaczyc sie z klientem " + e);
                System.exit(1);
            }// przerwanie pracy serwera nie jest zalecane w praktyce
        }
    }
    public static void main(String args[]) throws Exception {
        int Port = 15000;
        nowyserwer2 s2 = new nowyserwer2(Port);
        Thread t = new Thread(s2);
        t.start();
    }
}
