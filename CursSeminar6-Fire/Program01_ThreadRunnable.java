// Varianta 1: Derivare clasa Thread și suprascriere metodă run
class FirExecutie1 extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.print("A");
        }
    }
}

// Varianta 2: Implementare interfață Runnable și utilizarea
// constructorului clasei Thread pentru construirea obiectului
class FirExecutie2 implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.print("B");
        }
    }
}

public class Program01_ThreadRunnable {
    public static void main(String[] args) throws Exception {
        System.out.println("Start MAIN");
        // Pornire fir de execuție - metoda start
        Thread fir1 = new FirExecutie1();
        fir1.start();
        new Thread(new FirExecutie2()).start();

        new Thread(() -> {
                for (int i = 0; i < 100; i++) {
                    System.out.print("C");
                }
            }).start();

        fir1.join();
        System.out.println("Sfarsit MAIN");
    }
}
