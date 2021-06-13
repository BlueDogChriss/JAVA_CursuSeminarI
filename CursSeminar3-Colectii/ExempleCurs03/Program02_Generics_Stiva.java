/**
 * Exemplu clasă generică Stiva (implementată ca listă simplu înlănțuită cu adăugare / ștergere la începutul listei)
 * @param <T> Tipul de obiect stocat în stivă
 */
class Stiva<T> {

    /**
     * Clasă privată pentru elementele listei simplu înlănțuite
     * @param <T> Tipul de obiect stocat în stivă
     */
    private static class Nod<T> {
        T valoare;
        Nod<T> urmator;

        public Nod(T valoare, Nod<T> urmator) {
            this.valoare = valoare;
            this.urmator = urmator;
        }
    }

    /**
     * Stiva este considerată vidă dacă avem capul listei null. În cazul în care este ne-nul referă primul element
     * din stivă.
     */
    Nod cap = null;

    public void push(T valoare) {
        cap = new Nod(valoare, cap);
    }

    public T pop() {

        if (esteGoala()) {
            throw new UnsupportedOperationException("Stiva este goală.");
        }

        var temp = cap.valoare;
        cap = cap.urmator;
        return (T)temp;
    }

    public boolean esteGoala() {
        return cap == null;
    }
}

// *** Exemplu de utilizare ***
class Persoana {
    private int cod;
    private String nume;

    public Persoana(int cod, String nume) {
        this.cod = cod;
        this.nume = nume;
    }

    @Override
    public String toString() {
        return "Persoana{" +
                "cod=" + cod +
                ", nume='" + nume + '\'' +
                '}';
    }
}
public class Program02_Generics_Stiva {

    // Funcție generică pentru afișarea elementelor stivei.
    public static <T> void afisare(Stiva<T> stiva) {

        // Folosim o stivă temporară pentru păstrarea elementelor
        var temp = new Stiva<T>();
        while(!stiva.esteGoala()){
            var val = stiva.pop();
            temp.push(val);
            System.out.println(val);
        }

        // după afișare adăugăm la loc elementele în stiva originală
        while(!temp.esteGoala()){
            stiva.push(temp.pop());
        }
    }

    public static void main(String[] args) {

        var stivaPersoane = new Stiva<Persoana>();
        stivaPersoane.push(new Persoana(1, "Ion"));
        stivaPersoane.push(new Persoana(2, "Maria"));
        stivaPersoane.push(new Persoana(3, "Ana"));
        afisare(stivaPersoane);

        var stivaIntregi = new Stiva<Integer>();
        for (int i = 1; i <= 10; i++)
        {
            stivaIntregi.push(i);
        }
        afisare(stivaIntregi);
    }
}
