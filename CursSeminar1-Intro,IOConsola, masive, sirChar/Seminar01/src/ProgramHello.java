import java.util.Scanner;

/**
 * Program Hello
 *
 * - afișare la consolă
 * - citire de la consolă folosind clasa Scanner
 */
public class ProgramHello {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Afișare Hello Ion Popescu!
        System.out.print("Nume: ");
        String nume = scanner.nextLine();
        System.out.println("Hello " + nume + "!");

        // Suma a doi întregi
        System.out.print("a = ");
        int a = scanner.nextInt();

        System.out.print("b = ");
        int b = scanner.nextInt();

        System.out.printf("%d + %d = %d, a = %d, b = %d", a, b, a + b, a, b);
    }
}
