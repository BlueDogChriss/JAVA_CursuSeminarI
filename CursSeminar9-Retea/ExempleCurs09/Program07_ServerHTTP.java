import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;

public class Program07_ServerHTTP {
    public static void main(String[] args) throws IOException {

        final int SERVER_PORT = 8088;
        final int DIM_COADA_CERERI = 100;
        final String CALE = "/test";

        var server = HttpServer.create(
                new InetSocketAddress("localhost", SERVER_PORT),
                DIM_COADA_CERERI);

        server.createContext(CALE, Program07_ServerHTTP::procesareCerere);
        server.start();
    }

    public static void procesareCerere(HttpExchange exchange) throws IOException {
        System.out.printf("%s %s...", exchange.getRequestMethod(), exchange.getRequestURI());

        exchange.sendResponseHeaders(200, 0);
        try (var out = new PrintWriter(exchange.getResponseBody(), true)) {
            if (exchange.getRequestMethod().equals("GET")) {
                out.println("<html>");
                out.println("<body>");
                out.println("<form method='post' action='/test'>");
                out.println("<input name='a'>");
                out.println("+");
                out.println("<input name='b'>");
                out.println("<input type='submit' value='Suma'>");
                out.println("</form>");
                out.println("</body>");
                out.println("</html>");
            } else {
                try (var in = new BufferedReader(
                        new InputStreamReader(exchange.getRequestBody()))) {

                    String[] parametri = in.readLine().split("&");
                    int a = Integer.parseInt(parametri[0].split("=")[1]);
                    int b = Integer.parseInt(parametri[1].split("=")[1]);

                    out.println("<html>");
                    out.println("<body>");
                    out.println(a + b);
                    out.println("</body>");
                    out.println("</html>");
                }
            }
        }
        System.out.println("DONE");
    }
}
