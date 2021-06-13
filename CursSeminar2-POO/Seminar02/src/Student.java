import java.util.Arrays;

public class Student {
    private int idStudent;
    private String nume;
    private int grupa;
    private int anul;
    private Nota[] note;

    public Student() {
        this(0, "-", 0, 0);
    }

    public Student(int idStudent, String nume, int grupa, int anul) {
        setIdStudent(idStudent);
        setNume(nume);
        setGrupa(grupa);
        setAnul(anul);
        setNote(new Nota[0]);
    }

    public int getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getGrupa() {
        return grupa;
    }

    public void setGrupa(int grupa) {
        this.grupa = grupa;
    }

    public int getAnul() {
        return anul;
    }

    public void setAnul(int anul) {
        this.anul = anul;
    }

    public Nota[] getNote() {
        return note;
    }

    private void setNote(Nota[] note) {
        this.note = note;
    }

    public void add(Nota nota){
        for (var notaExistenta : getNote()){
            if (notaExistenta.getNumeDisciplina().equals(nota.getNumeDisciplina())){
                notaExistenta.setNota(nota.getNota());
                return;
            }
        }

        note = (Nota[]) Arrays.copyOf(note, note.length + 1);
        note[note.length - 1] = nota;
    }

    @Override
    public String toString() {
        var stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("#%d %-15s GR #%d an %d", getIdStudent(), getNume(), getGrupa(), getAnul()));
        stringBuilder.append(System.lineSeparator());
        for(var nota : getNote()) {
            stringBuilder.append(nota);
            stringBuilder.append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }
}
