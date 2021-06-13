class TestIncrementareFaraSincronizare {
    private int n = 0;

    public void incrementare() {
        n++;    // -> race condition
        // Operațiile sunt de fapt:
        // O1) citire valoare n
        // O2) calcul valoare n + 1
        // O3) scriere valoare calculată

        //try { Thread.sleep(1); } catch (InterruptedException e) {}
    }

    public int get() {
        return n;
    }
}

class FirExecutieIncrementare extends Thread {

    TestIncrementareFaraSincronizare incrementare;

    public FirExecutieIncrementare(TestIncrementareFaraSincronizare incrementare) {
        this.incrementare = incrementare;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            incrementare.incrementare();
        }
    }
}

public class Program03_ProblemeLipsaSincronizare {
    public static void main(String[] args) throws Exception {
        var test = new TestIncrementareFaraSincronizare();

        // construim două fire de execuție; ambele primesc
        // o referință la același obiect (test)
        var fir1 = new FirExecutieIncrementare(test);
        var fir2 = new FirExecutieIncrementare(test);

        // pornim firele de execuție
        fir1.start();   // apelează test.incrementare() de 10000 ori
        fir2.start();   // apelează test.incrementare() de 10000 ori

        // așteptăm să se termine cele două fire de execuție
        fir1.join();
        fir2.join();

        // rezultatul corect este 20000; pentru că nu avem sincronizare
        // vom avea un rezultat mai mic pentru că avem situații de forma:
        // fir1: O1) citire valoare n
        // fir2: O1) citire valoare n
        // fir1: O2) calcul valoare n + 1
        // fir2: O2) calcul valoare n + 1
        // fir1: O3) scriere valoare calculată
        // fir2: O3) scriere valoare calculată
        // (avem două apeluri de incrementare(), dar valoarea lui n creste cu 1
        System.out.println(test.get());





    }
}
