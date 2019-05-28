import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class komponent_nowegoklienta2 implements Runnable {
    private Socket gniazdo_klienta;
    private ObjectOutputStream wyjscie;
    private ObjectInputStream wejscie;
   
    public komponent_nowegoklienta2(Socket gniazdo_klienta,
            ObjectInputStream input, ObjectOutputStream output) {
        this.gniazdo_klienta = gniazdo_klienta;
        this.wejscie = input;
        this.wyjscie = output;
    }
    public void run() {
        String wiadomosc;
        String wiadomosci[] = {"AA", "BB", "CC"};
        try {
            for (int i = 0; i< wiadomosci.length; i++) {
                 wiadomosc = (String) wejscie.readObject();
                if (wiadomosc != null) {
                    System.out.println("Serwer odbiera: " + wiadomosc);
                } else {
                    break;
                }
                wyjscie.writeObject(wiadomosci[i]);
                System.out.println("Serwer wysyla: " + wiadomosci[i]);
            }
           
            wejscie.close();
            wyjscie.close();
            gniazdo_klienta.close();
        } catch (Exception e) {
            System.out.println("Wyjatek komponentu klienta po stronie serwera " + e);
        }
    }
}

