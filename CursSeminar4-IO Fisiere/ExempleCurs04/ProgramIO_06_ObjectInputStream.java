import java.io.*;
import java.util.ArrayList;
import java.util.List;


class Persoana implements Serializable {
    private final String nume;
    private final int varsta;

    public Persoana(String nume, int varsta) {
        this.nume = nume;
        this.varsta = varsta;
    }

    public String getNume() {
        return nume;
    }

    public int getVarsta() {
        return varsta;
    }

    @Override
    public String toString() {
        return String.format("%s (%d ani)", nume, varsta);
    }
}

public class ProgramIO_06_ObjectInputStream {
    static final String dataFilePath = ".\\data\\persoane.dat";

    public static void main(String[] args)
            throws IOException, ClassNotFoundException {

        Persoana ion = new Persoana("Ion", 23),
                maria = new Persoana("Maria", 22);

        // 1. Creare folder și fișier (dacă nu există)
        var file = new File(dataFilePath);
        file.getParentFile().mkdirs();
        file.createNewFile();

        // 2. Scriem în fișerul binar
        try (var fileOut = new ObjectOutputStream(
                new FileOutputStream(file, false))) {
            fileOut.writeObject(ion);
            fileOut.writeObject(maria);
        }

        // 3. Citim obiectele din fisier într-o listă
        List<Persoana> persoane = new ArrayList<>();
        try (var fileIn = new ObjectInputStream(
                new FileInputStream(file))) {
            while (true) {
                persoane.add((Persoana) fileIn.readObject());
            }
        } catch (EOFException e) {
            // ignore
        }

        // 4. Afișăm lista citită
        for (var persoana : persoane) {
            System.out.println(persoana);
        }
    }
}