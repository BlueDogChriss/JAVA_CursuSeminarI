import java.util.ArrayList;
import java.util.List;

interface Operatie {

    // Metodă statică - apelabilă prin Operatie.operatieIdentica
    static Operatie operatieIdentica() {
        // utilizăm o clasă anonimă
        return new Operatie() {
            @Override
            public int aplica(int valoare) {
                return valoare;
            }
        };
    }

    int aplica(int valoare);

    // Metodă default - implementată pe baza celorlalte metode din interfață
    default List<Integer> aplicaPeLista(List<Integer> lista) {
        var rezultat = new ArrayList<Integer>();
        for (var valoare : lista) {
            rezultat.add(this.aplica(valoare));
        }
        return rezultat;
    }
}

class Incrementare implements Operatie {

    int delta;

    public Incrementare(int delta) {
        this.delta = delta;
    }

    @Override
    public int aplica(int valoare) {
        return valoare + delta;
    }

    // nu este nevoie să implementăm metoda aplicaPeLista - va fi moștenită din interfață
}

public class Program01_InterfeteFunctionaleLambda {

    static void afisare(String mesaj, List<Integer> lista) {
        System.out.print(mesaj + ":");
        for (var valoare : lista) {
            System.out.print(" " + valoare);
        }
        System.out.println();
    }

    public static void main(String[] args) {

        var lista = List.of(1, 3, 5, 6);
        afisare("Initial", lista);

        // 1. Utilizare implementare din clasa Incrementare
        var incrementare5 = new Incrementare(5);
        afisare("Incrementare cu 5",
                incrementare5.aplicaPeLista(lista));

        // 2. Utilizare metodă statică din interfață
        afisare("Operatie identică",
                Operatie.operatieIdentica().aplicaPeLista(lista));

        // 3. Implementare interfață prin clasă anonimă
        Operatie incrementare3 = new Operatie() {
            public int aplica(int valoare) {
                return valoare + 3;
            }
        };
        afisare("Incrementare cu 3",
                incrementare3.aplicaPeLista(lista));

        // 4. Implementare interfață prin expresie lambda
        Operatie incrementare100 = valoare -> valoare + 100;
        afisare("Incrementare cu 100",
                incrementare100.aplicaPeLista(lista));

        // 5. Utilizare referință la metodă
        Operatie refMetoda = incrementare5::aplica;
        afisare("Incrementare cu 5",
                refMetoda.aplicaPeLista(lista));
    }
}