
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class komponent_nowegoklienta2_1 implements Runnable {

    private Socket gniazdo_klienta;
    private ObjectOutputStream wyjscie;
    private ObjectInputStream wejscie;

    public komponent_nowegoklienta2_1(Socket gniazdo_klienta,
            ObjectInputStream input, ObjectOutputStream output) {
        this.gniazdo_klienta = gniazdo_klienta;
        this.wejscie = input;
        this.wyjscie = output;
    }

    @Override
    public void run() {
        komunikat wiadomosc = null;
        komunikat wiadomosci[] = {new komunikat("B"), new komunikat("CC")};
        boolean stop = true;
        try {
            for (int i = 0; i < wiadomosci.length; i++) {
                wiadomosc = (komunikat) wejscie.readObject();
                if (wiadomosc != null) {
                    System.out.println("Serwer odbiera: " + wiadomosc);
                } else {
                    break;
                }
                if(i == wiadomosci.length - 1 && stop) {
                    stop = false;
                    i = 0;
                    continue;
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
