import java.io.ObjectInputStream; 
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class nowyklient2 implements Runnable {
    private Socket gniazdo_klienta;
    private ObjectOutputStream wyjscie;
    private ObjectInputStream wejscie;

    nowyklient2(String host_, int port_) {
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
        String wiadomosc;
        String wiadomosci[] = {"A", "B", "C"};
        try {
            for (int i = 0; i < wiadomosci.length; i++) {
                wyjscie.writeObject(wiadomosci[i]);
                System.out.println("Klient wysyla: " + wiadomosci[i]);
                wiadomosc = (String) wejscie.readObject();
                if(wiadomosc != null) {
                    System.out.println("Klient odbiera: " + wiadomosc);
                } else {
                    break;
                }
            }
            gniazdo_klienta.close();
            wyjscie.close();
            wejscie.close();
        } catch (Exception e) {
            System.out.println("Wyjatek klienta 2 " + e);
        }
    }

    public static void main(String args[]) throws Exception {
        String s = InetAddress.getLocalHost().getHostName();
        nowyklient2 k2 = new nowyklient2(s, 15000);
        Thread t = new Thread(k2);
        t.start();
    }
}
