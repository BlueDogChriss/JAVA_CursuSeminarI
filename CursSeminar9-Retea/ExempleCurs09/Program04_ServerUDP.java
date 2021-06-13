import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Program04_ServerUDP {
    public static void main(String[] args) throws IOException, SocketException {
        final int PORT_NUMBER = 1298;
        final int MAX_MESSAGE_SIZE = 65535;

        try (var socket = new DatagramSocket(PORT_NUMBER)) {
            while (true) {
                var buffer = new byte[MAX_MESSAGE_SIZE];
                var cerere = new DatagramPacket(buffer, buffer.length);
                socket.receive(cerere);

                String test = new String(cerere.getData(), 0, cerere.getLength());
                System.out.println("Am primit: " + test);

                InetAddress adresaClient = cerere.getAddress();
                int portClient = cerere.getPort();
                buffer = "raspuns".getBytes();
                var raspuns = new DatagramPacket(buffer, buffer.length, adresaClient, portClient);
                socket.send(raspuns);
            }
        }
    }
}
