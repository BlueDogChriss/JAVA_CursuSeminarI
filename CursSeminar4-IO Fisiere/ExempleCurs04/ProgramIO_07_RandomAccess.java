import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

public class ProgramIO_07_RandomAccess {

    static final String dataFilePath = ".\\data\\vector.dat";

    public static void main(String[] args) throws IOException {

        double[] v = {999, Math.E, Math.PI, -10, 20, 40, 5, 7};
        System.out.println(Arrays.toString(v));

        // 1. Scriere în fișier
        try (var file = new RandomAccessFile(dataFilePath, "rw")) {
            for (double o : v) {
                file.writeDouble(o);
            }
        }

        // 2. Sortare fișier
        try (var file = new RandomAccessFile(dataFilePath, "rw")) {
            int n = (int) file.length() / Double.BYTES;
            int pozitieSortare = n - 1, pozitieInterschimb;
            do {
                pozitieInterschimb = 0;
                for (int i = 0; i < pozitieSortare; i++) {
                    file.seek(i * Double.BYTES);
                    double v1 = file.readDouble(), v2 = file.readDouble();
                    if (v1 > v2) {
                        file.seek(i * Double.BYTES);
                        file.writeDouble(v2);
                        file.writeDouble(v1);
                        pozitieInterschimb = i;
                    }
                }
                pozitieSortare = pozitieInterschimb;
            }
            while (pozitieSortare != 0);
        }

        // 3. Citire fișier
        try (var file = new RandomAccessFile(dataFilePath, "rw")) {
            int n = (int) file.length() / Double.BYTES;
            for (int i = 0; i < n - 1; i++) {
                System.out.print(file.readDouble() + " ");
            }
            System.out.println(file.readDouble());
        }
    }
}
