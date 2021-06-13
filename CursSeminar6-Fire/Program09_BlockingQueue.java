import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.*;

public class Program09_BlockingQueue {

    private final static int NUMAR_MAXIM = 10;
    private final static BlockingQueue<String> coadaFisiere = new ArrayBlockingQueue<>(NUMAR_MAXIM);

    public static void showFolders(File cale) throws IOException {

        if (cale.isDirectory()) {
            for (var child : cale.listFiles()) {
                showFolders(child);
            }
        } else {
            try {
                coadaFisiere.put(cale.getCanonicalPath());
                System.out.printf("Adăugare %s%n", cale.getName());
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws Exception {

        var start = System.nanoTime();

        var thread1 = new Thread(new TaskCautareInFisier(coadaFisiere, "Persoana"));
        thread1.setDaemon(true);
        thread1.start();
        var thread2 = new Thread(new TaskCautareInFisier(coadaFisiere, "Persoana"));
        thread2.setDaemon(true);
        thread2.start();

        showFolders(new File(".."));

        while (coadaFisiere.size() > 0) {
            Thread.sleep(10);
        }

        System.out.printf("Gata - %d ms", (System.nanoTime() - start) / 1000000);
    }

    static class TaskCautareInFisier implements Runnable {
        private final BlockingQueue<String> coadaFisiere;
        String textDeCautat;

        public TaskCautareInFisier(BlockingQueue<String> coadaFisiere, String textDeCautat) {
            this.coadaFisiere = coadaFisiere;
            this.textDeCautat = textDeCautat;
        }

        @Override
        public void run() {

            while (true) {

                try {

                    var caleFisier = coadaFisiere.take();
                    System.out.printf("Procesare %d: %s%n", Thread.currentThread().getId(), caleFisier);

                    if (!caleFisier.endsWith(".java")) {
                        continue;
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
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
}