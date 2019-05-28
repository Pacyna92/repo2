import java.io.ObjectInputStream; 
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class nowyklient1_1 implements Runnable {
    private Socket gniazdo_klienta;
    private ObjectOutputStream wyjscie;
    private ObjectInputStream wejscie;

    nowyklient1_1(String host_, int port_) {
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
        komunikat wiadomosci[] = new komunikat []{new komunikat("A")};
        try {
            wyjscie.writeObject(wiadomosci[0]);
            System.out.println("Klient wysyla: " + wiadomosci[0]);
            gniazdo_klienta.close();
            wyjscie.close();
            wejscie.close();
        } catch (Exception e) {
            System.out.println("Wyjatek klienta 2 " + e);
        }
    }

    public static void main(String args[]) throws Exception {
        String s = InetAddress.getLocalHost().getHostName();
        nowyklient1_1 k2 = new nowyklient1_1(s, 15000);
        Thread t = new Thread(k2);
        t.start();
    }
}
