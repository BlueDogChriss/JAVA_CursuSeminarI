import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

class Persoana {
    private int varsta;
    private String nume;

    public Persoana(String nume, int varsta) {
        this.varsta = varsta;
        this.nume = nume;
    }

    public int getVarsta() {
        return varsta;
    }

    public String getNume() {
        return nume;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Persoana{");
        sb.append("varsta=").append(varsta);
        sb.append(", nume='").append(nume).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

public class Program03_Streams {
    public static void main(String[] args) {
        var persoane = List.of(
                new Persoana("CATALIN ROSENTHAL", 51),
                new Persoana("DUMITRU PETROSANU", 56),
                new Persoana("IONUT RUSSI", 46),
                new Persoana("ANCA GEFRA", 46),
                new Persoana("MARINOF CURCAN", 13),
                new Persoana("ZAMFIRICA ARPICI", 22),
                new Persoana("BUNRAU BRACADA WISCONSIN", 30),
                new Persoana("CORNELIA SZEGEDI", 36),
                new Persoana("LICU ROMTELESERVICE", 42),
                new Persoana("STELICA INDUSTRIAL", 32),
                new Persoana("VICENTIU COPICESCU", 33),
                new Persoana("DANIEL FLUX", 46),
                new Persoana("BUNRAU SANDU", 25),
                new Persoana("DINU WATER", 40),
                new Persoana("PAUL RADULESCU", 53),
                new Persoana("MARIA MARINESCU", 12),
                new Persoana("LILIANA KRISZTINA", 34),
                new Persoana("MIREL IMNR", 33),
                new Persoana("PERSWALL MONICA", 54),
                new Persoana("MARINESCU MEFIN", 38),
                new Persoana("VICTORIA MARIAN", 46),
                new Persoana("PETRESCU KNACU", 45),
                new Persoana("IFTODI BARBU", 26),
                new Persoana("GEORGE THE", 26),
                new Persoana("TITI IANOSI", 24),
                new Persoana("DANIELA SCHULTZ", 41),
                new Persoana("MURESAN CERBEANU", 13),
                new Persoana("CRISTIAN CHIRITA", 41),
                new Persoana("DUMITRU ALF", 12),
                new Persoana("ANTON COZMA", 50),
                new Persoana("ROBIN GINELA", 34),
                new Persoana("VASILE ADAMITA", 36),
                new Persoana("BUNRAU RAUL", 56)
        );

        String[] numeSub30 = persoane.stream()          // construire stream
                .filter(item -> item.getVarsta() < 30)  // operație intermediară (expresie lambda)
                .map(Persoana::getNume)                 // operație intermediară (referință metodă)
                .toArray(String[]::new);                // operație terminală

        Arrays.stream(numeSub30)
                .forEach(item -> System.out.println(item));

        Consumer<Persoana> afisare = persoana -> System.out.println(persoana);

        persoane.stream()
                .sorted((a, b) -> a.getNume().compareTo(b.getNume()))
                .forEach(afisare);

        var numePersoaneMajore = persoane.stream()
                .filter(p -> p.getVarsta() >= 18)
                .map(Persoana::getNume)
                .collect(Collectors.toList());
        numePersoaneMajore.stream().forEach(System.out::println);

        int suma = persoane.stream()
                .limit(3)
                .map(Persoana::getVarsta)
                .reduce(1, (s, varsta) -> s + varsta);
        System.out.printf("Suma vârstelor pentru primii 3 este %d ani.%n", suma);

        class Medie {
            final int numar;
            final int suma;

            public Medie() {
                numar = 0;
                suma = 0;
            }

            public Medie(int numar, int suma) {
                this.numar = numar;
                this.suma = suma;
            }

            public double getMedie() {
                return numar > 0 ? (double)suma / numar : 0;
            }

            public Medie accepta(Persoana persoana) {
                return new Medie(numar + 1, suma + persoana.getVarsta());
            }

            public Medie combina(Medie medie){
                return new Medie(numar + medie.numar, suma + medie.suma);
            }
        };

        double medie = persoane.stream()
                .parallel()
                .reduce(new Medie(), Medie::accepta, Medie::combina)
                .getMedie();
        System.out.printf("Vârsta medie este %.2f ani.%n", medie);
    }
}
