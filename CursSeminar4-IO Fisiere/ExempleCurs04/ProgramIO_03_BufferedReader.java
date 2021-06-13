import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ProgramIO_03_BufferedReader {
    static final String inputFilePath = ".\\src\\ProgramIO_02_CharStreams.java";

    public static void main(String[] args)
            throws IOException {

        try (var fileIn = new BufferedReader(
                new FileReader(inputFilePath)
        )) {
            int lineNumber = 1;
            String line;

            while ((line = fileIn.readLine()) != null) {
                System.out.printf("%2d %s%s",
                        lineNumber++,
                        line,
                        System.lineSeparator());
            }
        }
    }
}