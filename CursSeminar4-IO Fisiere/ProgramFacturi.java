import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

class Factura {

    private final String denumireClient;
    private final LocalDate dataEmitere;
    private final List<Linie> linii;

    // Observație:
    //
    // Deși are toate câmpurile marcate ca final, clasa Factură NU este imutabilă.
    // Câmpul linii conține o referință la un obiect (în cazul de față un ArrayList)
    // care poate fi modificat - vezi metoda adaugaLinie. În cazul de față marcajul
    // final pentru linii specifică doar că obiectul listă nu poate fi înlocuit cu totul
    // după ce obiectul Factura a fost creat, dar conținutul lui poate fi modificat.

    public Factura(String denumireClient, LocalDate dataEmitere) {
        this.denumireClient = denumireClient;
        this.dataEmitere = dataEmitere;
        this.linii = new ArrayList<>();
    }

    public String getDenumireClient() {
        return denumireClient;
    }

    public LocalDate getDataEmitere() {
        return dataEmitere;
    }

    public double getValoare() {
        double valoare = 0;
        for (var linie : linii) {
            valoare += linie.getValoare();
        }
        return valoare;
    }

    public int getNumarLinii() {
        return linii.size();
    }

    public Linie getLinie(int index) {
        return linii.get(index);
    }

    public void adaugaLinie(Linie linie) {
        linii.add(linie);
    }

    public void adaugaLinie(String produs, double pret, int cantitate) {
        adaugaLinie(new Linie(produs, pret, cantitate));
    }

    @Override
    public String toString() {
        var dateFormat = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
        final StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s, Client: %s%n",
                dateFormat.format(dataEmitere), denumireClient));
        for (var linie : linii) {
            sb.append(linie.toString() + System.lineSeparator());
        }
        return sb.toString();
    }

    static class Linie {
        private final String produs;
        private final double pret;
        private final int cantitate;

        public Linie(String produs, double pret, int cantitate) {
            this.produs = produs;
            this.pret = pret;
            this.cantitate = cantitate;
        }

        public String getProdus() {
            return produs;
        }

        public double getPret() {
            return pret;
        }

        public int getCantitate() {
            return cantitate;
        }

        public double getValoare() {
            return pret * cantitate;
        }

        @Override
        public String toString() {
            return String.format("%-25s %3d x %5.2f RON = %6.2f RON",
                    produs, cantitate, pret, getValoare());
        }
    }
}

public class ProgramFacturi {

    public final static int NUMAR_MAXIM_PRODUSE = 10;

    public static List<Factura> generareListaFacturi(
            int numarFacturi, LocalDate dataMinima) {

        // 1. Datele fixe folosite la generarea facturilor
        String[] denumiriClienti = new String[]{
                "ALCOR CONSTRUCT SRL",
                "SC DOMINO COSTI SRL",
                "SC TRANSCRIPT SRL",
                "SIBLANY SRL",
                "INTERFLOOR SYSTEM SRL",
                "MERCURY  IMPEX  2000  SRL",
                "ALEXANDER SRL",
                "METAL INOX IMPORT EXPOSRT SRL",
                "EURIAL BROKER DE ASIGURARE SRL"
        };

        String[] denumiriProduse = new String[]{
                "Stafide 200g",
                "Seminte de pin 300g",
                "Bulion Topoloveana 190g",
                "Paine neagra Frontera",
                "Ceai verde Lipton"

        };

        double[] preturiProduse = new double[]{
                5.20,
                12.99,
                6.29,
                4.08,
                8.99
        };

        // 2. Inițializare generare
        Random rand = new Random(); // vezi https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Random.html
        int numarMaximZile = (int) ChronoUnit.DAYS.between(dataMinima, LocalDate.now());
        List<Factura> facturi = new ArrayList<>();

        // 3. Generare facturi
        for (int indexFactura = 0; indexFactura < numarFacturi; indexFactura++) {

            var denumireClient = denumiriClienti[rand.nextInt(denumiriClienti.length)];
            var data = dataMinima.plusDays(rand.nextInt(numarMaximZile));   // maxim data curentă

            var factura = new Factura(denumireClient, data);

            // Adăugăm cel puțin un rând
            var numarProduse = 1 + rand.nextInt(NUMAR_MAXIM_PRODUSE - 1);
            for (int indexProdus = 0; indexProdus < numarProduse; indexProdus++) {

                // Atenție: produsul și prețul trebuie să fie corelate (aceeași poziție)
                var produsSelectat = rand.nextInt(denumiriProduse.length);
                var produs = denumiriProduse[produsSelectat];
                var pret = preturiProduse[produsSelectat];

                var cantitate = 1 + rand.nextInt(19);

                factura.adaugaLinie(produs, pret, cantitate);
            }

            facturi.add(factura);
        }

        return facturi;
    }

    static void afisareFacturi(List<Factura> facturi) {
        for (var factura : facturi) {
            System.out.println(factura);
        }
    }

    static void salvareFacturi(String caleFisier, List<Factura> facturi)
            throws IOException {

        // Dacă a fost specificat și un folder
        if (new File(caleFisier).getParentFile() != null) {
            // ne asigurăm că acesta există
            new File(caleFisier).getParentFile().mkdirs();
        }

        try (var fisier = new DataOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream(caleFisier)))) {
            for (var factura : facturi) {

                // Scriere inforații globale pentru factură
                fisier.writeUTF(factura.getDenumireClient());
                fisier.writeInt(factura.getDataEmitere().getYear());
                fisier.writeInt(factura.getDataEmitere().getMonthValue());
                fisier.writeInt(factura.getDataEmitere().getDayOfMonth());
                fisier.writeInt(factura.getNumarLinii());

                for (var indexLinie = 0; indexLinie < factura.getNumarLinii(); indexLinie++) {
                    var linie = factura.getLinie(indexLinie);

                    // Scriem datele pentru linia curentă
                    fisier.writeUTF(linie.getProdus());
                    fisier.writeDouble(linie.getPret());
                    fisier.writeInt(linie.getCantitate());
                }
            }
        }
    }

    static List<Factura> incarcareFacturi(String caleFisier)
            throws IOException {

        List<Factura> facturi = new ArrayList<>();
        try (var fisier = new DataInputStream(
                new BufferedInputStream(
                        new FileInputStream(caleFisier)))) {

            // Citim din fișier până la producerea excepției EOFException
            while (true) {

                // Citim datele globale pentru factură
                var denumireClient = fisier.readUTF();
                int an = fisier.readInt(), luna = fisier.readInt(), zi = fisier.readInt();
                int numarLinii = fisier.readInt();

                var factura = new Factura(denumireClient, LocalDate.of(an, luna, zi));

                for (var indexLinie = 0; indexLinie < numarLinii; indexLinie++) {

                    // Citim datele pentru linia curentă
                    var denumire = fisier.readUTF();
                    var pret = fisier.readDouble();
                    var cantitate = fisier.readInt();
                    factura.adaugaLinie(denumire, pret, cantitate);
                }

                facturi.add(factura);
            }
        } catch (EOFException e) {
            // nu facem nimic
        }

        return facturi;
    }


    static void generareRaport(String caleRaport, List<Factura> facturi)
            throws IOException {

        // 1. Definim o clasă locală pentru a stoca datele pentru raport
        class DateClient implements Comparable<DateClient> {
            public final String denumireClient;
            public int numarFacturi = 0;
            public double valoareTotala = 0;

            public DateClient(String denumireClient) {
                this.denumireClient = denumireClient;
            }

            public String toString() {
                return String.format("%-40s %3d facturi, TOTAL: %8.2f RON",
                        denumireClient, numarFacturi, valoareTotala);
            }

            public int compareTo(DateClient o) {
                return -Double.compare(valoareTotala, o.valoareTotala);
            }
        }

        // Construim un dicționar de forma DenumireClient -> (DenumireClient, NumarFacturi, ValoareTotală)
        Map<String, DateClient> clienti = new HashMap<>();
        for (var factura : facturi) {
            var client = factura.getDenumireClient();

            // 1. Ne asigurăm că avem clientul în dicționar
            if (!clienti.containsKey(client)) {
                clienti.put(client, new DateClient(client));
            }

            // 2. Extragem obiectul asociat clientului din dicționar
            // și adăugăm datele corespunzătoare facturii curente
            var dateClient = clienti.get(client);
            dateClient.numarFacturi += 1;
            dateClient.valoareTotala += factura.getValoare();
        }

        // Punem datele calculate într-o listă și sortăm lista descrescător după valoare
        var raport = new ArrayList<DateClient>(clienti.values());
        Collections.sort(raport);

        // Scriem raportul în fișierul text
        try (PrintWriter fisier = new PrintWriter(caleRaport)) {
            for (var dateClient : raport) {
                fisier.println(dateClient);
            }
        }
    }

    public static void main(String[] args)
            throws IOException {
        final String caleFisier = "date\\facturi.dat";

        var facturi = generareListaFacturi(
                30,
                LocalDate.of(2020, 1, 1));

        System.out.println("Facturi generate:");
        afisareFacturi(facturi);
        salvareFacturi(caleFisier, facturi);

        System.out.println();
        System.out.println("=============================================");
        System.out.println();

        var facturiIncarcate = incarcareFacturi(caleFisier);
        System.out.println("Facturi incarcate:");
        afisareFacturi(facturiIncarcate);

        generareRaport("date\\raport.txt", facturi);
        System.out.println("Raportul a fost generat.");
    }
}
