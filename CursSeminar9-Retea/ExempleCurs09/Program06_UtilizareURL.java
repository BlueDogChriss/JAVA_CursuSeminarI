import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class Program06_UtilizareURL {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://www.ase.ro");
            try (BufferedReader in = new BufferedReader(new
                    InputStreamReader(url.openStream()))) {
                in.lines().forEach(linie -> System.out.println(linie));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
