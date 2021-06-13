import java.util.Scanner;

/**
 * Program Masive unidimensionale
 *
 * - citire valori de la consolă / redirectare flux standard
 * - lucrul cu masive bidimensionale
 */
public class ProgramMatrice {

    public static double[][] citire() {
        var scanner = new Scanner(System.in);
        var matrice = new double[scanner.nextInt()][scanner.nextInt()];
        for (int indexLinie = 0; indexLinie < matrice.length; indexLinie++) {
            for (int indexColoana = 0; indexColoana < matrice[indexLinie].length; indexColoana++) {{
                matrice[indexLinie][indexColoana] = scanner.nextDouble();
            }}
            System.out.println();
        }
        return matrice;
    }

    public static void afisare(String mesaj, double[][] matrice) {
        System.out.println(mesaj);
        for (int indexLinie = 0; indexLinie < matrice.length; indexLinie++) {
            for (int indexColoana = 0; indexColoana < matrice[indexLinie].length; indexColoana++) {{
                System.out.printf("%6.2f", matrice[indexLinie][indexColoana]);
            }}
            System.out.println();
        }
        System.out.println();
    }

    public static double[][] transpusa(double[][] matrice) {

        if (matrice.length == 0) {
            return new double[0][0];
        }

        var rezultat = new double[matrice[0].length][matrice.length];

        for (int indexLinie = 0; indexLinie < matrice.length; indexLinie++) {
            for (int indexColoana = 0; indexColoana < matrice[indexLinie].length; indexColoana++) {{
                rezultat[indexColoana][indexLinie] = matrice[indexLinie][indexColoana];
            }}
        }

        return rezultat;
    }

    public static void main(String[] args) {
        var m = citire();
        afisare("Matricea inițială:", m);
        afisare("Transpusa:", transpusa(m));
    }
}
