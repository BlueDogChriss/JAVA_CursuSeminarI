import java.util.Scanner;

class TestSleepJoin extends Thread {
    @Override
    public void run() {
        // Afișăm două litere pe secundă până când suntem întrerupți
        try {
            while (true) {
                Thread.sleep(500);
                System.out.print("A");
            }
        } catch (InterruptedException e) {
            // când am fost întrerupți afișăm un mesaj
            System.out.println("Am fost întrerupt...");
        }
        // și continuăm cu alte operații
        System.out.println("Gata!");
    }
}

public class Program02_SleepJoin {
    public static void main(String[] args) throws Exception {

        // Pornim firul de execuție secundar
        var fir = new TestSleepJoin();
        fir.start();

        // când am citit un rând de la tastatură
        new Scanner(System.in).nextLine();
        // trimitem mesajul de întrerupere către firul de execuție secundar
        fir.interrupt();
        System.out.println("Am intrerupt firul secundar...");

        // așteptăm să se termine execuția firului secundar
        fir.join();
        System.out.println("Firul secundar s-a încheiat...");
    }
}
