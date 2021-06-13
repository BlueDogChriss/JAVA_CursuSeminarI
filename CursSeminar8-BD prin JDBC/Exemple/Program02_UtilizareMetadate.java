import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Program02_UtilizareMetadate {

    public static <T> List<T> citireLista(String url, String numeTabela, Class<T> clasa)
            throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        var rezultat = new ArrayList<T>();

        var sql = String.format("SELECT * FROM %s", numeTabela);

        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement();) {

            try (ResultSet resultSet = statement.executeQuery(sql)) {

                var metadate = resultSet.getMetaData();

                while (resultSet.next()) {
                    var obiect = clasa.getConstructor().newInstance();

                    for (int colIndex = 1; colIndex <= metadate.getColumnCount(); colIndex++){
                        var numeColoana = metadate.getColumnName(colIndex);
                        var numeMetodaSet = "set" + numeColoana;

                        for (var metoda : clasa.getMethods()) {
                            if (metoda.getName().equals(numeMetodaSet)){
                                var valoare = resultSet.getObject(colIndex);
                                metoda.invoke(obiect, valoare);
                            }
                        }
                    }

                    rezultat.add(obiect);
                }
            }
        }
        return rezultat;
    }

    public static void main(String[] args) throws Exception {
        String url = "jdbc:sqlite:date\\persoane.db";

        // 1. Afișare tabele existente
        try (Connection connection = DriverManager.getConnection(url);) {
            DatabaseMetaData metadate = connection.getMetaData();
            try (var tables = metadate.getTables(null, null, null, null);) {
                while (tables.next()) {
                    var denumireTabela = tables.getString("TABLE_NAME");
                    System.out.println(denumireTabela);

                    // Afișare coloane
                    try (var coloane = metadate.getColumns(null, null, denumireTabela, null)) {
                        while (coloane.next()) {
                            System.out.printf("%-12s %s%n",
                                    coloane.getString("COLUMN_NAME"),
                                    coloane.getString("TYPE_NAME"));
                        }
                    }
                    System.out.println();
                }
            }
        }

        // 2. Metodă generică pentru citire date
        citireLista(url, "Persoane", Persoana.class).stream()
                .forEach(elem -> System.out.println(elem));

        citireLista(url, "Produse", Produs.class).stream()
                .forEach(elem -> System.out.println(elem));
    }
}


class Produs {
    private int cod;
    private String denumire;
    private double pret;
    private int cantitate;

    public Produs() {
        denumire = "";
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
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
        final StringBuilder sb = new StringBuilder("Produs{");
        sb.append("cod=").append(cod);
        sb.append(", denumire='").append(denumire).append('\'');
        sb.append(", pret=").append(pret);
        sb.append(", cantitate=").append(cantitate);
        sb.append('}');
        return sb.toString();
    }
}