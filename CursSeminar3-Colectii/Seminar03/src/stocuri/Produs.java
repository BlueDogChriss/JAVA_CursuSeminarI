package stocuri;

import java.util.Objects;

public class Produs implements Comparable<Produs> {

    private static final String DENUMIRE_IMPLICITA = "-";

    private final int cod;
    private final String denumire;

    public Produs(int cod) {
        this(cod, DENUMIRE_IMPLICITA);
    }

    public Produs(int cod, String denumire) {
        this.cod = cod;
        this.denumire = denumire;
    }

    public int getCod() {
        return cod;
    }

    public String getDenumire() {
        return denumire;
    }

    @Override
    public String toString() {
        return String.format("#%2d - %s", cod, denumire);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produs produs = (Produs) o;
        return cod == produs.cod;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cod);
    }

    @Override
    public int compareTo(Produs o) {
        return Integer.compare(cod, o.cod);
    }
}
