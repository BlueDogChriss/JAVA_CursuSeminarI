import java.lang.reflect.*;

public class Program01_ReflectionSimple {
    public static void main(String[] args)
            throws Exception {

        // 1. Obținere instanțe Class<T>
        Persoana ion = new Persoana(1, "Ion");
        Class<?> clasaPersoana = ion.getClass();    // dintr-un obiect existent
        Class<?> clasaString = Class.forName("java.lang.String");
        Class<?> clasaInt = int.class;  // pe baza tipului

        // 2. Obținere informații despre clasă
        System.out.println("Modificatori pentru " + clasaString.getName());
        int modificatori = clasaString.getModifiers();
        System.out.println(Modifier.toString(modificatori));

        // 3. Manipulare câmpuri

        for (Field camp : clasaPersoana.getDeclaredFields()) {
            // obligatoriu pentru campurile private
            camp.setAccessible(true);

            System.out.printf("%s %s %s = %s%n",
                    Modifier.toString(camp.getModifiers()),
                    camp.getType().getName(),
                    camp.getName(),
                    camp.get(ion));
        }

        var campCod = clasaPersoana.getDeclaredField("cod");
        campCod.setAccessible(true);
        campCod.set(ion, 3);
        System.out.println(ion);

        // 4. Utilizare metode

        // Obținere informații
        for (Method metoda : clasaPersoana.getDeclaredMethods()) {
            String modif = Modifier.toString(metoda.getModifiers());
            Class<?> tipReturnat = metoda.getReturnType();
            String denumire = metoda.getName();
            System.out.printf("%s %s %s, parametri: ",
                    modif, tipReturnat.getName(), denumire);
            for (Parameter param : metoda.getParameters()) {
                System.out.printf("%s ", param.getType().getName());
            }
            System.out.println();
        }

        // Invocare
        Method metodaSetCod = clasaPersoana
                .getDeclaredMethod("setCod", int.class);
        // pentru metode private: metodaSetCod.setAccessible(true);
        metodaSetCod.invoke(ion, 4);
        System.out.println(ion);

        // 5. Utilizare constructori

        // afișare
        for (Constructor<?> constructor : clasaPersoana.getConstructors()) {
            System.out.println("Constructor cu parametrii:");
            for (Parameter parametru : constructor.getParameters()) {
                System.out.println(parametru.getType().getName() + " " + parametru.getName());
            }
        }

        // construire obiect
        var constructor = clasaPersoana.getConstructor(int.class, String.class);
        Persoana maria = (Persoana) constructor.newInstance(2, "Maria");
        System.out.println(maria);
    }
}

class Persoana {
    public transient String nume;
    private int cod;

    public Persoana(int cod, String nume) {
        this.cod = cod;
        this.nume = nume;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Persoana{");
        sb.append("cod=").append(cod);
        sb.append(", nume='").append(nume).append('\'');
        sb.append('}');
        return sb.toString();
    }
}