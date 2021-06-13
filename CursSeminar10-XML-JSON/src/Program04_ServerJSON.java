import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.json.*;
import java.io.*;
import java.net.InetSocketAddress;

public class Program04_ServerJSON {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create();
        server.bind(new InetSocketAddress("localhost", 8090), 100);

        server.createContext("/procesareIndicator", Program04_ServerJSON::handleProcesareIndicator);
        server.start();
    }

    static void handleProcesareIndicator(HttpExchange exchange) {

        try (var in = exchange.getRequestBody();
             var out = new PrintWriter(exchange.getResponseBody(), true);) {

            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.sendResponseHeaders(200, 0);

            var indicator = new JSONObject(new JSONTokener(in));
            var denumire = indicator.getString("denumire");
            var valori = indicator.getJSONArray("valori");

            var n = valori.length();
            var suma = 0;
            for (int index = 0; index < valori.length(); index++) {
                suma += valori.getInt(index);
            }

            var raspuns = new JSONObject();
            raspuns.put("denumire", denumire);
            raspuns.put("numarValori", n);
            raspuns.put("suma", suma);
            raspuns.put("media", n > 0 ? (double)suma / n : 0);
            raspuns.write(out);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
