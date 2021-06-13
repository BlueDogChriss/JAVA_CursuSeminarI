import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Program02_ClientTCP {
    public static void main(String[] args) throws IOException {
        final int PORT_NUMBER = 8293;
        try (Socket socket = new Socket("127.0.0.1", PORT_NUMBER);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);) {

            System.out.println("Am deschis conexiunea cu server-ul.");

            out.println("Test");
            System.out.println("Am trimis mesajul. Așteptăm răspunsul...");

            System.out.println(in.readLine());

            System.out.println("Am primit răspunsul - închidem conexiunea.");
        }
    }
}
