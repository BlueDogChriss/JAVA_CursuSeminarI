import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class ProgramStudenti {

    private static Student[] centralizator = new Student[0];
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        afisare("Inițial");
        citireStudenti();
        afisare("După citire studenți");

        citireCatalog();
        afisare("După citire catalog SD");

        citireCatalog();
        afisare("După citire catalog Java");

        afisareCatalog("Structuri de date");
        afisareCatalog("Programare Java");
    }

    static void afisare(String mesaj) {
        System.out.println(mesaj + ":\n");
        for (var student : centralizator) {
            System.out.println(student);
        }
        System.out.println("--------------------------------------------------------------");
    }

    static void afisareCatalog(String numeDisciplina) {

        class ElementCatalog {
            public String numeStudent;
            public int nota;

            public ElementCatalog(String numeStudent, int nota) {
                this.numeStudent = numeStudent;
                this.nota = nota;
            }

            @Override
            public String toString() {
                return String.format("  %-20s %2d", numeStudent, nota);
            }
        }

        // 1. Determinare număr de elemente catalog
        int numarNoteCatalog = 0;
        for (int indexStudent = 0; indexStudent < centralizator.length; indexStudent++){
            var student = centralizator[indexStudent];
            for (int indexNota = 0; indexNota < student.getNote().length; indexNota++) {
                var nota = student.getNote()[indexNota];
                if (nota.getNumeDisciplina().equals(numeDisciplina)){
                    numarNoteCatalog++;
                }
            }
        }

        // 2. Construire vector catalog
        var catalog = new ElementCatalog[ numarNoteCatalog];
        int indexCatalog = 0;
        for (int indexStudent = 0; indexStudent < centralizator.length; indexStudent++){
            var student = centralizator[indexStudent];
            for (int indexNota = 0; indexNota < student.getNote().length; indexNota++) {
                var nota = student.getNote()[indexNota];
                if (nota.getNumeDisciplina().equals(numeDisciplina)){
                    catalog[indexCatalog] = new ElementCatalog(student.getNume(), nota.getNota());
                    indexCatalog = indexCatalog + 1;
                }
            }
        }

        // 3. Sortare și afișare vector
        Arrays.sort(catalog, new Comparator() {
            public int compare(Object o1, Object o2) {
                var  e1 = (ElementCatalog)o1;
                var  e2 = (ElementCatalog)o2;
                return -Integer.compare(e1.nota, e2.nota);
            }
        });

        System.out.println();
        System.out.println("Catalog " + numeDisciplina);
        for (var element : catalog) {
            System.out.println(element);
        }
        System.out.println();
    }

    static void citireStudenti() {

        int numarStudenti = Integer.parseInt(scanner.nextLine());

        centralizator = new Student[numarStudenti];

        for (int index = 0; index < numarStudenti; index++) {

            String linieStudent = scanner.nextLine();
            String linieNote = scanner.nextLine();

            var student = new Student(
                    Integer.parseInt(linieStudent.split(",")[0]),
                    linieStudent.split(",")[1],
                    Integer.parseInt(linieStudent.split(",")[2]),
                    Integer.parseInt(linieStudent.split(",")[3])
            );

            var elementeNote = linieNote.split(",");
            for (int indexNota = 1; indexNota < elementeNote.length; indexNota += 2) {
                var disciplina = elementeNote[indexNota - 1];
                var nota = Integer.parseInt(elementeNote[indexNota]);

                student.add(new Nota(disciplina, nota));
            }

            centralizator[index] = student;
        }

    }

    static void citireCatalog() {

        var disciplina = scanner.nextLine();
        var numarNote = Integer.parseInt(scanner.nextLine());

        buclaStudent:
        for (int index = 0; index < numarNote; index++) {
            var linieNota = scanner.nextLine();
            int idStudent = Integer.parseInt(linieNota.split(",")[0]);
            int nota = Integer.parseInt(linieNota.split(",")[1]);

            for (var student : centralizator) {
                if (student.getIdStudent() == idStudent) {
                    student.add(new Nota(disciplina, nota));
                    continue buclaStudent;
                }
            }

            System.err.println("Cod student invalid: #" + idStudent);
        }
    }
}
