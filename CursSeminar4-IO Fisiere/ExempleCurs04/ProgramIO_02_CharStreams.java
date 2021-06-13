import java.io.FileReader;
import java.io.IOException;

public class ProgramIO_02_CharStreams {
    static final String inputFilePath = ".\\src\\ProgramIO_02_CharStreams.java";

    public static void main(String[] args)
            throws IOException {

        try (var fileIn = new FileReader(inputFilePath))
        {
            int readChar;

            while ((readChar = fileIn.read()) != -1) {

                System.out.print((char)readChar);
            }
        }
    }
}
