import java.util.*;

class Colectie <T> {
    private Object[] elemente = new Object[0];

    public void addElement(T element) {
        elemente = Arrays.copyOf(elemente, elemente.length + 1);
        elemente[elemente.length - 1] = element;
    }

    public T getElement(int index){
        return (T)elemente[index];
    }

    public int getNumarElemente(){
        return elemente.length;
    }
}

public class Program01_Generics
{
    public static <T> void afisareColectie(Colectie<T> colectie) {
        for (int index = 0; index < colectie.getNumarElemente(); index++) {
            System.out.println(colectie.getElement(index));
        }
    }

    public static <T> boolean existaElement(Colectie<T> colectie, T element) {
        for (int index = 0; index < colectie.getNumarElemente(); index++) {
            if (colectie.getElement(index).equals(element)){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {

        Colectie<String> v1 = new Colectie<>();
        v1.addElement("Ana");
        v1.addElement("are");
        v1.addElement("mere");
        afisareColectie(v1);

        if (existaElement(v1, "mere")){
            System.out.println("Are mere.");
        }

        if (!existaElement(v1, "pere")){
            System.out.println("Nu are pere.");
        }

        Colectie<Integer> v2 = new Colectie<>();
        v2.addElement(1);
        v2.addElement(2);
        v2.addElement(3);
        afisareColectie(v2);

        Colectie v3 = new Colectie();
        v3.addElement(7); v3.addElement("pitici");
        afisareColectie(v3);
    }
}



