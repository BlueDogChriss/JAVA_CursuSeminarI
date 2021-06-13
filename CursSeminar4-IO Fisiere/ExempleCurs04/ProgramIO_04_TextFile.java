import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ProgramIO_04_TextFile {
    static final String testFilePath = ".\\Data\\persoane.txt";

    public static void main(String[] args)
            throws IOException {

        // 1. Scriere în fișier text
        try (var fisier = new PrintWriter(testFilePath)){
            for (int i = 0; i < 10; i++) {
                fisier.printf("%d %s%n", i, "Persoana " + i);

                /* SAU:
                fisier.print(i);
                fisier.print(" ");
                fisier.println("Persoana" + i); */
            }
        }

        // 2. Citire din fisier text
        try (var fisier = new Scanner(new File(testFilePath))){
            while(fisier.hasNextLine()){
                int cod = fisier.nextInt();
                String nume = fisier.nextLine().trim();

                System.out.println(cod);
                System.out.println(nume);
            }
        }
    }
}
