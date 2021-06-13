import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class TaskCautareInFisier implements Runnable {
    String caleFisier;
    String textDeCautat;

    public TaskCautareInFisier(String caleFisier, String textDeCautat) {
        this.caleFisier = caleFisier;
        this.textDeCautat = textDeCautat;
    }

    @Override
    public void run() {
        if (!caleFisier.endsWith(".java")) {
            return;
        }
        try (var fileIn = new BufferedReader(
                new FileReader(caleFisier)
        )) {
            int lineNumber = 1;
            String line;
            Thread.sleep(3);

            while ((line = fileIn.readLine()) != null) {
                if (line.contains(textDeCautat)) {
                    System.out.printf("%s:%d %s%n", caleFisier, lineNumber, line);
                }
                lineNumber++;
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }

    }
}

public class Program07_ThreadPool {

    static final int NUMAR_FIRE = 4;
    static ExecutorService threadPool = Executors.newFixedThreadPool(NUMAR_FIRE);

    public static void showFolders(File cale, String textDecautat) throws IOException {
        if (cale.isDirectory()) {
            for (var child : cale.listFiles()) {
                showFolders(child, textDecautat);
            }
        } else {
            threadPool.submit(
                new TaskCautareInFisier(cale.getCanonicalPath(), textDecautat)
            );
        }
    }

    public static void main(String[] args) throws Exception {
        var start = System.nanoTime();
        showFolders(new File("..\\..\\.."), "Persoana");
        threadPool.shutdown();
        threadPool.awaitTermination(30, TimeUnit.SECONDS);
        System.out.printf("Gata - %d ms", (System.nanoTime() - start) / 1000000);
    }
}
