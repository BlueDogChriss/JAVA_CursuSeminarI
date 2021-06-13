import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

public class Program08_ClientHTTP {
    public static void main(String[] args) throws IOException {
        int a = 11, b = 7;

        URL url = new URL("http://localhost:8088/test");
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);

        try (var out = new PrintWriter(connection.getOutputStream(), true)) {
            out.printf("a=%d&b=%d", a, b);
        }

        try (var in = new BufferedReader(
                new InputStreamReader(
                        connection.getInputStream()))) {
            in.lines().forEach(linie -> System.out.println(linie));
        }
    }
}
