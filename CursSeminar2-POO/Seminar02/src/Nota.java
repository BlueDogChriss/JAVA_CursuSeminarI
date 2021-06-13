public class Nota {
    private String numeDisciplina;
    private int nota;

    public Nota() {
        this("-", 1);
    }

    public Nota(String numeDisciplina, int nota) {
        setNumeDisciplina(numeDisciplina);
        setNota(nota);
    }

    public String getNumeDisciplina() {
        return numeDisciplina;
    }

    public void setNumeDisciplina(String numeDisciplina) {
        this.numeDisciplina = numeDisciplina;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        if (nota < 1 || nota > 10) {
            throw new IllegalArgumentException("Notă invalidă: " + nota);
        }
        this.nota = nota;
    }

    @Override
    public String toString() {
        return String.format("%-18s - %2d", getNumeDisciplina(), getNota());
    }
}
