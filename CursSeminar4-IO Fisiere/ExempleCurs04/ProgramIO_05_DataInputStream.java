import java.io.*;

public class ProgramIO_05_DataInputStream {
    static final String inputFilePath = ".\\out\\production\\Scratchpad\\Nota.class";

    public static void main(String[] args)
            throws IOException {

        // vezi https://en.wikipedia.org/wiki/Java_class_file

        try (var fileIn = new DataInputStream(new FileInputStream(inputFilePath))) {
            int magicNumber = fileIn.readInt();
            short versionMinor = fileIn.readShort(),
                    versionMajor = fileIn.readShort(),
                    constantPoolCount = fileIn.readShort();

            System.out.printf("Magic number: %8X%n", magicNumber);
            System.out.printf("Class file format version: %d.%d%n", versionMajor, versionMinor);
            System.out.printf("Constant pool - number of entries: %d", constantPoolCount);
        }
    }
}
