import ro.ase.Persoana;

import java.util.Arrays;
import java.util.Comparator;

class ComparatorCod implements Comparator {

    @Override
    public int compare(Object o, Object t1) {
        Persoana p1 = (Persoana)o;
        Persoana p2 = (Persoana)t1;
        return Integer.compare(p1.getCod(), p2.getCod());
    }
}

public class ProgramPersoane {

    static void afisare(String mesaj, Persoana[] persoane) {
        System.out.println(mesaj);
        for (var persoana : persoane) {
            System.out.println("   " + persoana);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        var ion = new Persoana("Ion");
        var maria = new Persoana("Maria");
        var ana = new Persoana("Ana");

        var persoane = new Persoana[] {ion, maria, ana};
        afisare("Inițial", persoane);


        Arrays.sort(persoane, new Comparator(){
            public int compare(Object o, Object t1) {
                Persoana p1 = (Persoana)o;
                Persoana p2 = (Persoana)t1;
                return Integer.compare(p1.getCod(), p2.getCod());
            }
        });
        afisare("Sortat - cod", persoane);

        Arrays.sort(persoane);
        afisare("Sortat - nume", persoane);
    }
}
