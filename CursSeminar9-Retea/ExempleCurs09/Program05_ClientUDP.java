import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Program05_ClientUDP {
    public static void main(String[] args) throws IOException, SocketException {
        final String SERVER_HOST_NAME = "localhost";
        final int SERVER_PORT_NUMBER = 1298;
        final int MAX_MESSAGE_SIZE = 65535;

        try (var socket = new DatagramSocket()) {
            // 1. Transmitere cerere UDP
            var buffer = "cerere".getBytes();
            var cerere = new DatagramPacket(
                    buffer,
                    buffer.length,
                    InetAddress.getByName(SERVER_HOST_NAME),
                    SERVER_PORT_NUMBER);
            socket.send(cerere);

            // 2. Receptionare raspuns
            buffer = new byte[MAX_MESSAGE_SIZE];
            var raspuns = new DatagramPacket(buffer, buffer.length);
            socket.receive(raspuns);

            String test = new String(raspuns.getData(), 0, raspuns.getLength());
            System.out.println("Am primit de la server: " + test);
        }
    }
}
