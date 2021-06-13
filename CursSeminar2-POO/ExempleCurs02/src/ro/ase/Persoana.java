package ro.ase;

public class Persoana implements Comparable {

    public static final int COD_NEINITIALIZAT = 0;
    public static final String NUME_NECUNOSCUT = "-";

    private static int ultimulCod = 0;

    private int cod;
    private String nume;

    public Persoana() {
        this(NUME_NECUNOSCUT);
    }

    public Persoana(String nume) {
        ultimulCod = ultimulCod + 1;
        this.cod = ultimulCod;
        this.nume = nume;
    }

    public int getCod() {
        return cod;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public String toString() {
        return "Persoana{" +
                "cod=" + cod +
                ", nume='" + nume + '\'' +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        Persoana celalalt = (Persoana)o;
        return this.nume.compareTo(celalalt.getNume());
    }
}

