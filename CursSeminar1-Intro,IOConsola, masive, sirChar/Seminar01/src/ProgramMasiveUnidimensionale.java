import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Program Masive unidimensionale
 *
 * - citire parametri din linia de comandă
 * - utilizare funcții din clasa String
 * - utilizare funcții de conversie din clase wrapper
 * - lucrul cu masive unidimensionale
 */
public class ProgramMasiveUnidimensionale {
    static void afisare(String mesaj, int[] vector) {
        System.out.print(mesaj + ": ");
        for (int element : vector) {
            System.out.print(element + " ");
        }
        System.out.println();
    }

    static void incrementare(int[] vector) {
        for (int i = 0; i < vector.length;i++) {
            vector[i] = vector[i] + 1;
        }
    }

    static int[] inserareInceput(int[] vector, int valoare) {
        var rezultat = new int[vector.length + 1];
        rezultat[0] = valoare;
        System.arraycopy(vector, 0, rezultat, 1, vector.length);
        return rezultat;
    }

    static int[] eliminareImpare(int[] vector) {
        int numarElemente = 0;
        for (int i = 0; i < vector.length;i++) {
            if (vector[i] % 2 == 0) {
                numarElemente++;
            }
        }

        var rezultat = new int[numarElemente];
        for (int i = 0, indexRezultat = 0; i < vector.length;i++) {
            if (vector[i] %2 == 0) {
                rezultat[indexRezultat] = vector[i];
                indexRezultat++;
            }
        }
        return rezultat;
    }

    public static void main(String[] args){
        String[] valoriString = args[0].split(",");
        int[] valori = new int[valoriString.length];
        for (int i = 0; i < valori.length; i++) {
            valori[i] = Integer.parseInt(valoriString[i].trim());
        }
        afisare("Initial", valori);

        incrementare(valori);
        afisare("După incrementare", valori);

        valori = inserareInceput(valori, 13);
        afisare("După inserare", valori);

        valori = eliminareImpare(valori);
        afisare("După eliminare elemente impare", valori);
    }
}
