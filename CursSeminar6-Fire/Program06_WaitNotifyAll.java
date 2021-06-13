// Două fire de execuție:
// producătorul - generează date și le pune într-o zonă comună de dimensiune fixă (CoadaMesaje)
// consumatorul - extrage datele din zona comună și le procesează
//
// Dacă zona comună este goală atunci consumatorul trebuie să aștepte până la venirea următoarei valori, iar dacă
// zona este plină producătorul trebuie să aștepte până se eliberează spațiu.


class CoadaMesaje {                     //  zona comună de capacitate 1 element

    private String mesajCurent;
    private boolean areMesaj = false;

    public synchronized void adaugaMesaj(String mesaj) {

        // 1. Așteptâm până când nu mai avem mesaj
        while (areMesaj) {
            try {
                System.out.printf("adauga(%s): avem deja un mesaj. Așteptăm...%n", mesaj);
                wait(); // eliberăm monitorul și așteptăm
            } catch (InterruptedException e) {
            }
        }

        // 2. Salvăm mesajul primit
        mesajCurent = mesaj;
        areMesaj = true;
        System.out.printf("adauga(%s): am pus mesajul în coadă%n", mesaj);
        notifyAll();
    }

    public synchronized String extrageMesaj() {
        // 1. Așteptâm până când avem un mesaj
        while (!areMesaj) {
            try {
                System.out.printf("extrage(): nu avem un mesaj în coadă. Așteptăm...%n", mesajCurent);
                wait(); // eliberăm monitorul și așteptăm
            } catch (InterruptedException e) {
            }
        }

        // 2. Întoarcem mesajul primit și eliberăm zona
        areMesaj = false;
        System.out.printf("extrage(): am scos mesajul '%s' din coadă%n", mesajCurent);
        notifyAll();
        return mesajCurent;
    }
}

class Consumator extends Thread {

    private CoadaMesaje coadaMesaje;

    public Consumator(CoadaMesaje coadaMesaje) {
        this.coadaMesaje = coadaMesaje;
    }

    @Override
    public void run() {

        while (true) {
            var mesaj = coadaMesaje.extrageMesaj();

            if (mesaj.equals("STOP")) {
                break;
            }

            System.out.print("MESAJ: ");
            for (int index = 0; index < mesaj.length(); index++) {
                System.out.print(mesaj.charAt(index));
                // try { Thread.sleep(1000); } catch (InterruptedException e) { }
            }
            System.out.println();
        }
        System.out.println("Consumator - sfârșit");
    }
}

class Producator extends Thread {

    private CoadaMesaje coadaMesaje;

    public Producator(CoadaMesaje coadaMesaje) {
        this.coadaMesaje = coadaMesaje;
    }

    @Override
    public void run() {
        String[] mesaje = {"Ana", "are", "trei", "mere", "STOP"};
        for (var mesaj : mesaje) {
            coadaMesaje.adaugaMesaj(mesaj);
            try { Thread.sleep(1000); } catch (InterruptedException e) { }
        }
        System.out.println("Producator - sfârșit");
    }
}

public class Program06_WaitNotifyAll {
    public static void main(String[] args) throws Exception {
        var coadaMesaje = new CoadaMesaje();

        var producator = new Producator(coadaMesaje);
        var consumator = new Consumator(coadaMesaje);

        System.out.println("MAIN - Început execuție");

        producator.start();
        consumator.start();

        // așteptăm terminarea execuției
        producator.join();
        consumator.join();

        System.out.println("MAIN - Sfârșit execuție");
    }
}
