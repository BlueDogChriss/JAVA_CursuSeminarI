// 1. Declarare adnotări

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@interface AdnotareSimpla {
}

@Retention(RetentionPolicy.RUNTIME)
@interface AltaAdnotareSimpla {
}

@interface AdnotareValoare {    // -> nu va exista la rulare
    String value();
}

@Retention(RetentionPolicy.RUNTIME)
@interface AdnotareValori {
    String mesaj();

    int repetari();
}

// 2. Aplicare adnotări
@AdnotareSimpla
@AdnotareValoare("Test")
@AdnotareValori(mesaj = "Test", repetari = 12)
class Test {
    @AdnotareSimpla
    int numar;
}

public class Program02_AdnotariSimple {
    // 3. Utilizare adnotări prin Reflection
    public static void main(String[] args)
            throws Exception {

        var clasaTest = Test.class;

        // Verificare prezenta adnotari
        if (clasaTest.isAnnotationPresent(AdnotareSimpla.class)) {
            System.out.println("Are AltaAdnotareSimpla");
        }

        if (clasaTest.isAnnotationPresent(AltaAdnotareSimpla.class)) {
            System.out.println("Nu are AltaAdnotareSimpla");
        }

        // Enumerare adnotari
        for (Annotation adnotare : clasaTest.getAnnotations()) {
            System.out.println(adnotare);
        }

        // Obtinere valori
        AdnotareValori adnotare = clasaTest.getAnnotation(AdnotareValori.class);
        System.out.printf("%s, %d%n", adnotare.mesaj(), adnotare.repetari());
    }
}
