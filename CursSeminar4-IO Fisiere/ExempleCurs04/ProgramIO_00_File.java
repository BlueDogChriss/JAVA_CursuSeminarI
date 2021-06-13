import java.io.File;
import java.io.IOException;

public class ProgramIO_00_File {

    public static void showFolders(File cale, int level) {
        for (int i = 0; i < level; i++) {
            System.out.print("  ");
        }

        if (cale.isDirectory()) {
            System.out.println("[" + cale.getName() + "]");

            for (var child : cale.listFiles()) {
                showFolders(child, level + 1);
            }
        } else {
            System.out.printf("%s (%dk)%n", cale.getName(), cale.length() / 1024);
        }
    }

    public static void main(String[] args)
            throws IOException {

        // 1. Afișare recursivă director
        var rootFolder = new File(".");
        showFolders(rootFolder, 0);

        // 2. Manipulare directoare și fișiere
        var testFolder = new File(".\\Teste");
        if (testFolder.exists()) {

            for (var fisier : testFolder.listFiles()) {
                fisier.delete();
            }

            testFolder.delete();
        }

        testFolder.mkdirs();

        var testFile = new File(testFolder, "date.txt");
        testFile.createNewFile();
        testFile.renameTo(new File(testFolder, "alte_date.txt"));
    }
}
