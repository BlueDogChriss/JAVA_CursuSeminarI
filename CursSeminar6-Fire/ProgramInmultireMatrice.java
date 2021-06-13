class FirInmultire extends Thread {

    private double[][] matX, matY, matRez;
    private int indexLinieStart, indexColoanaStart, indexLinieEnd, indexColoanaEnd;

    public FirInmultire(double[][] matX, double[][] matY, double[][] matRez,
                        int indexLinieStart, int indexColoanaStart,
                        int indexLinieEnd, int indexColoanaEnd) {

        this.matX = matX;
        this.matY = matY;
        this.matRez = matRez;

        this.indexLinieStart = indexLinieStart;
        this.indexColoanaStart = indexColoanaStart;

        this.indexLinieEnd = indexLinieEnd;
        this.indexColoanaEnd = indexColoanaEnd;
    }

    @Override
    public void run() {
        for (int i = indexLinieStart; i <= indexLinieEnd; i++) {
            for (int j = indexColoanaStart; j <= indexColoanaEnd; j++) {
                matRez[i][j] = 0;
                for (int k = 0; k < matY.length; k++) {
                    matRez[i][j] += matX[i][k] * matY[k][j];
                }
            }
        }
    }
}

public class ProgramInmultireMatrice {
    public static void main(String[] args) throws InterruptedException {

        int n = 2000;
        int numarBlocuri = 4; // Numar de blocuri linie/coloana pentru matX/matY

        double[][] matX = new double[n][n],
                matY = new double[n][n],
                matRez = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matX[i][j] = Math.random() * 10 - 5;
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matY[i][j] = Math.random() * 10 - 5;
            }
        }

        long start = System.nanoTime();
        FirInmultire fir = new FirInmultire(matX, matY, matRez, 0, 0, n - 1, n - 1);
        System.out.println("Start inmultire matrice pe un singur fir.");
        fir.start();
        //fir.join();
        System.out.printf("Executie in %d milisecunde.%n", (System.nanoTime() - start) / 1000000);


        start = System.nanoTime();

        // Calcul numar linii pe un bloc in matricea matX
        // si numar coloane pe un bloc in matricea matY
        int numarLiniiPerBloc = n / numarBlocuri, numarColoanePerBloc = n / numarBlocuri;

        // Alocare masiv fire
        FirInmultire[][] fire = new FirInmultire[numarBlocuri][numarBlocuri];
        System.out.printf("%nStart inmultire matrice pe %d fire.%n", numarBlocuri * numarBlocuri);
        for (int i = 0; i < numarBlocuri; i++) {

            int indexLinieStart = i * numarLiniiPerBloc;
            int indexLinieEnd = (i + 1) * numarLiniiPerBloc - 1;

            // Ultimul bloc preia si liniile rest
            // Exemplu: n = 37, numarBlocuri = 3
            // - avem numarLiniiPerBloc = 37 / 3 = 10 și i cu valorile 0, 1, 2
            // - trebuie să obținem trei blocuri de dimensiuni: 10, 10, 17
            if (i == numarBlocuri - 1) {
                indexLinieEnd = n - 1;
            }
            for (int j = 0; j < numarBlocuri; j++) {
                int indexColoanaStart = j * numarColoanePerBloc;
                int indexColoanaEnd = (j + 1) * numarColoanePerBloc - 1;

                // Ultimul bloc preia si coloanele rest
                if (j == n - 1) {
                    indexColoanaEnd = n - 1;
                }

                // Alocare și pornire execuție fir
                fire[i][j] = new FirInmultire(matX, matY, matRez,
                        indexLinieStart, indexColoanaStart,
                        indexLinieEnd, indexColoanaEnd);
                fire[i][j].start();
            }
        }
        for (int i = 0; i < numarBlocuri; i++) {
            for (int j = 0; j < numarBlocuri; j++) {
                fire[i][j].join();
            }
        }

        System.out.printf("Executie in %d milisecunde.%n", (System.nanoTime() - start) / 1000000);
    }
}
