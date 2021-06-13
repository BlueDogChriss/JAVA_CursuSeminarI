import java.io.FileInputStream;
import java.io.IOException;

public class ProgramIO_01_ByteStreams {

    static final String inputFilePath = ".\\out\\production\\Scratchpad\\Nota.class";

    public static void main(String[] args)
            throws IOException {

        try (var fileIn = new FileInputStream(inputFilePath))
        {
            int readByte, index = 1;

            while ((readByte = fileIn.read()) != -1) {

                System.out.printf("%02X ", (byte) readByte);

                if (index++ % 50 == 0) {
                    System.out.println();
                }
            }

            System.out.println();
        }
    }
}
