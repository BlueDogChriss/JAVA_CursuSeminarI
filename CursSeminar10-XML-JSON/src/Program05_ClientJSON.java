import java.io.PrintWriter;
import java.net.URL;
import org.json.*;

public class Program05_ClientJSON {
    public static void main(String[] args) throws Exception {
        //var dateIntrare = getIndicator("test", new int[]{1, 2, 3, 4});
        var dateIntrare = new JSONObject(
                new Indicator("test", new int[]{1, 2, 3, 4}),
                new String[] { "denumire", "valori"}
        );
        System.out.println(dateIntrare);

        var url = new URL("http://localhost:8090/procesareIndicator");
        var connection = url.openConnection();
        connection.setDoOutput(true);

        try (var out = new PrintWriter(connection.getOutputStream(), true)) {
            dateIntrare.write(out);
        }

        try (var in = connection.getInputStream()) {
            var raspuns = new JSONObject(new JSONTokener(in));
            System.out.println("Media este: " + raspuns.getDouble("media"));
        }
    }

    static JSONObject getIndicator(String denumire, int[] valori) {
        var rezultat = new JSONObject();
        rezultat.put("denumire", denumire);
        rezultat.put("valori", new JSONArray(valori));
        return rezultat;
    }

    public static class Indicator {
        public String denumire;
        public int[] valori;

        public Indicator(String denumire, int[] valori) {
            this.denumire = denumire;
            this.valori = valori;
        }
    }
}
