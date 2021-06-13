import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Program03_ServerTCPMultithreaded {

    public static void main(String[] args) throws IOException {
        final int PORT_NUMBER = 8293;
        try (ServerSocket serverSocket = new ServerSocket(PORT_NUMBER)) {
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> procesareCerere(socket)).start();
            }
        }
    }

    private static void procesareCerere(Socket socket) {
        try {
            System.out.println("Așteptăm un client...");
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            ) {
                System.out.println("Am stabilit conexiunea. Așteptăm mesajul...");

                String mesajPrimit = in.readLine();
                System.out.println("Mesaj primit - " + mesajPrimit);
                out.printf("Mesajul primit este: %s%n", mesajPrimit);

                System.out.println("Am terminat de procesat cererea - închidem conexiunea.");
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException exception) {
            }
        }
    }
}