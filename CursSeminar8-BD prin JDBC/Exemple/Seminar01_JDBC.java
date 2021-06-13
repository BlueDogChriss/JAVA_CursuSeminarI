import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

class Tranzactie {
    private int codProdus;
    private String denumireProdus;
    private double pret;
    private int cantitate;

    public Tranzactie(int codProdus, String denumireProdus, double pret, int cantitate) {
        this.codProdus = codProdus;
        this.denumireProdus = denumireProdus;
        this.pret = pret;
        this.cantitate = cantitate;
    }

    public int getCodProdus() {
        return codProdus;
    }

    public void setCodProdus(int codProdus) {
        this.codProdus = codProdus;
    }

    public String getDenumireProdus() {
        return denumireProdus;
    }

    public void setDenumireProdus(String denumireProdus) {
        this.denumireProdus = denumireProdus;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Tranzactie{");
        sb.append("codProdus=").append(codProdus);
        sb.append(", denumireProdus='").append(denumireProdus).append('\'');
        sb.append(", pret=").append(pret);
        sb.append(", cantitate=").append(cantitate);
        sb.append('}');
        return sb.toString();
    }
}

public class Seminar01_JDBC {

    static final String url = "jdbc:sqlite:date\\tranzactii.db";

    static void adaugaTranzactii(List<Tranzactie> tranzactii) throws SQLException {
        try (
                var connection = DriverManager.getConnection(url);
                var cmdInsert = connection.prepareStatement(
                        "INSERT INTO Tranzactii VALUES (?, ?, ?, ?)");
        ) {
            for (var tranzactie : tranzactii) {
                cmdInsert.setInt(1, tranzactie.getCodProdus());
                cmdInsert.setString(2, tranzactie.getDenumireProdus());
                cmdInsert.setDouble(3, tranzactie.getPret());
                cmdInsert.setInt(4, tranzactie.getCantitate());

                cmdInsert.execute();
            }
        }
    }

    static void genereazaBazaDeDate() throws SQLException {
        try (
                var connection = DriverManager.getConnection(url);
                var command = connection.createStatement();
        ) {
            command.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS Tranzactii (" +
                            "CodProdus INTEGER, " +
                            "DenumireProdus STRING(100), " +
                            "Pret REAL, " +
                            "Cantitate INTEGER)");


            command.executeUpdate("DELETE FROM Tranzactii");
        }

        final String[] produse = {
                "Stafide 200g",
                "Seminte de pin 300g",
                "Bulion Topoloveana 190g",
                "Paine neagra Frontera",
                "Ceai verde Lipton"
        };

        var random = new Random();

        List<Tranzactie> tranzactii = new ArrayList<>();
        for (int index = 0; index < 30; index++) {
            int cod = random.nextInt(produse.length);
            String denumire = produse[cod];
            double pret = 1 + random.nextDouble() * 10;
            int cantitate = random.nextInt(30) - 10;
            tranzactii.add(new Tranzactie(cod, denumire, pret, cantitate));
        }

        adaugaTranzactii(tranzactii);
    }

    static void afisareStocuri() throws SQLException {
        List<Tranzactie> tranzactii = new ArrayList<>();
        try (
                var connection = DriverManager.getConnection(url);
                var select = connection.createStatement();
                var rs = select.executeQuery("SELECT * FROM Tranzactii")) {
            while (rs.next()) {
                tranzactii.add(new Tranzactie(
                        rs.getInt("CodProdus"),
                        rs.getString("DenumireProdus"),
                        rs.getDouble("Pret"),
                        rs.getInt("Cantitate")
                ));
            }
        }

        var produse = tranzactii.stream()
                .collect(Collectors.toMap(
                        Tranzactie::getCodProdus,
                        Tranzactie::getDenumireProdus,
                        (p1, p2) -> p1));

        var tranzactiiGrupate = tranzactii.stream()
                .collect(Collectors.groupingBy(Tranzactie::getCodProdus));

        for (var codProdus : produse.keySet()) {

            var pretMediuIntrare = tranzactiiGrupate.get(codProdus).stream()
                    .filter(t -> t.getCantitate() > 0)
                    .mapToDouble(Tranzactie::getPret)
                    .average().orElse(0);

            var stoc = tranzactiiGrupate.get(codProdus).stream()
                    .mapToInt(Tranzactie::getCantitate)
                    .sum();

            System.out.printf("%-30s %3d %6.2f RON%n",
                    produse.get(codProdus), stoc, pretMediuIntrare);
        }
    }

    public static void main(String[] args) throws SQLException {
        genereazaBazaDeDate();
        afisareStocuri();
    }
}
