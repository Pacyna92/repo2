
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class nowyklient2_1 implements Runnable {

    private Socket gniazdo_klienta;
    private ObjectOutputStream wyjscie;
    private ObjectInputStream wejscie;

    nowyklient2_1(String host_, int port_) {
        try {
            gniazdo_klienta = new Socket(host_, port_);
            wejscie = new ObjectInputStream(gniazdo_klienta.getInputStream());
            wyjscie = new ObjectOutputStream(gniazdo_klienta.getOutputStream());
            wyjscie.flush();
        } catch (Exception e) {
            System.out.println("Wyjatek klienta 1 " + e);
        }
    }

    public void run() {
        komunikat wiadomosc = null;
        komunikat wiadomosci[] = {new komunikat("A"), new komunikat("BB"), new komunikat("C")};
        try {
            for (int i = 0; i < wiadomosci.length; i++) {
                wyjscie.writeObject(wiadomosci[i]);
                System.out.println("Klient wysyla: " + wiadomosci[i]);
                if(i == wiadomosci.length - 2) {
                    continue;
                }
                wiadomosc = (komunikat) wejscie.readObject();
                System.out.println("Klient odbiera: " + wiadomosc);
            }
            gniazdo_klienta.close();
            wyjscie.close();
            wejscie.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) throws Exception {
        String s = InetAddress.getLocalHost().getHostName();
        nowyklient2_1 k2 = new nowyklient2_1(s, 15000);
        Thread t = new Thread(k2);
        t.start();
    }
}
