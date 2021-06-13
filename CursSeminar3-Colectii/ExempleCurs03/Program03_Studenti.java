import java.util.*;

class Student implements Comparable, Cloneable {
    private Map<String, Integer> note;
    private final String nume;

    public Student(String nume) {
        this.nume = nume;
        this.note = new HashMap<>();
    }

    public String getNume() {
        return nume;
    }

    public boolean areNota(String disciplina) {
        return note.containsKey(disciplina);
    }

    public int getNota(String disciplina) {
        if (areNota(disciplina)) {
            return note.get(disciplina);
        } else {
            throw new NoSuchElementException(String.format(
                    "Studentul '%s' nu are notă la disciplina '%s'.", nume, disciplina));
        }
    }

    public Set<String> getDiscipline() {
        return note.keySet();
    }

    public void adaugaNota(String disciplina, int nota) {
        if (nota < 1 || nota > 10) {
            throw new IllegalArgumentException("Notă invalidă.");
        }
        note.put(disciplina, nota);
    }

    public void stergeNota(String disciplina) {
        note.remove(disciplina);
    }

    @Override
    public String toString() {
        var message = new StringBuilder();
        message.append(nume);
        message.append(System.lineSeparator());
        for (Map.Entry<String, Integer> nota : note.entrySet()) {
            message.append("   " + nota.getKey() + ":" + nota.getValue());
            message.append(System.lineSeparator());
        }
        return message.toString();
    }

    @Override
    public int compareTo(Object o) {
        Student altStudent = (Student)o;
        return nume.compareTo(altStudent.nume);
    }

    @Override
    public Student clone() {
        try {
            Student copie = (Student)super.clone();
            copie.note = new HashMap<>(this.note);
            return copie;
        }
        catch(CloneNotSupportedException e) {
            throw new AssertionError("Clone nu este suportat.", e);
        }
    }
}

public class Program03_Studenti {

    public static void afiseazaCatalog(List<Student> studenti, String disciplina) {

        var studentiSortati = new ArrayList<>(studenti);
        Collections.sort(studentiSortati);

        System.out.println();
        System.out.println("Catalog " + disciplina);
        System.out.println();

        for (var student : studentiSortati) {
            System.out.printf("%-8s", student.getNume());
            if (student.areNota(disciplina)) {
                System.out.printf("%3d", student.getNota(disciplina));
            } else {
                System.out.print("ABS");
            }
            System.out.println();
        }
        System.out.println("===========");
    }

    public static void main(String[] args) {

        Student ion = new Student("Ion");
        ion.adaugaNota("Java", 6);
        ion.adaugaNota("PAW", 9);
        System.out.println(ion);

        ion.adaugaNota(new String("Java"), 10);
        System.out.println(ion);

        ion.stergeNota("PAW");
        ion.adaugaNota("SGBD", 8);
        System.out.println(ion);

        for (String disciplina : ion.getDiscipline()) {
            System.out.println(disciplina + " -> " + ion.getNota(disciplina));
        }

        Student maria = new Student("Maria");
        maria.adaugaNota("Java", 9);
        maria.adaugaNota("PAW", 10);

        Student vasile = new Student("Vasile");

        List<Student> studenti = new ArrayList<>();
        studenti.add(maria);
        Collections.addAll(studenti, vasile, ion);
        afiseazaCatalog(studenti, "Java");

        var clonaIon = ion.clone();
        clonaIon.adaugaNota("Java", 8);
        clonaIon.adaugaNota("Test", 10);
        System.out.println(ion);
        System.out.println(clonaIon);
    }
}
