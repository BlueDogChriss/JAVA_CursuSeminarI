import stocuri.Produs;
import stocuri.TipTranzactie;
import stocuri.Tranzactie;

import java.time.LocalDate;
import java.util.*;


/*
* Cerințe:
*
* 1) Un enum TipTranzactie cu valorile INTRARE și IESIRE și o metodă semn care să dea semnul tranzacției (-1 sau 1).
*
* 2) O clasă imutabilă Produs (cantitate - intreg, denumire - string).
* Constructori care să primească toate valorile, respectiv doar codul.
* Două produse sunt considerate egale dacă au același cod.
*
* 3) O clasă Tranzacție (tip - tipul tranzacției, data - data tranzacției, codProdus și cantitate - intreg).
*
* 4) În clasa ProgramSotocuri:
* - să se definească un câmp static stocuri de tip dicționar care să asocieze fiecărui produs lista de tranzacții aferente.
* - să se definească o metodă statică AdaugaProdus(int cod, String denumire) care să adauge un produs nou în lista de stocuri. Produsul NU trebuie să existe în stocuri.
* - să se definească o metodă statică AdaugaTranzactie(TipTranzactie tip,LocalDate data,int codProdus,int cantitate) care să adauge o tranzacție nouă. Produsul trebuie să existe în stocuri.
* - să se definească o metodă statică AfisareStocuri() care să afișeze lista de produse cu: cod, denumire, stocCurent, data ultimei tranzacții
* */


public class ProgramStocuri {

    static Map<Produs, List<Tranzactie>> stocuri
            = new HashMap<>();

    static void AdaugaProdus(int cod, String denumire) {
        stocuri.put(
                new Produs(cod, denumire),
                new ArrayList<>());
    }

    static void AdaugaTranzactie(
            TipTranzactie tip,
            LocalDate data,
            int codProdus,
            int cantitate) {
        var produs = new Produs(codProdus);
        if (!stocuri.containsKey(produs)) {
            throw new IllegalArgumentException("Produsul #" + codProdus + " nu există.");
        }
        stocuri.get(produs).add(new Tranzactie(
                tip, data, codProdus, cantitate));
    }

    static void AfisareStocuri() {

        for (var element : stocuri.entrySet()) {
            var tranzactii = element.getValue();
            int stocTotal = 0;
            for (var tranzactie : tranzactii) {
                stocTotal += tranzactie.getTip().semn() * tranzactie.getCantitate();
            }

            String ultimaTranzactie = ", ultima tranzactie: ";
            if (!tranzactii.isEmpty()) {
                ultimaTranzactie += Collections.max(element.getValue(), new Comparator<Tranzactie>() {
                    @Override
                    public int compare(Tranzactie o1, Tranzactie o2) {
                        return o1.getData().compareTo(o2.getData());
                    }
                }).getData();
            }

            System.out.println(element.getKey() + " -> " + stocTotal + ultimaTranzactie);
        }
    }

    public static void main(String[] args) {

        stocuri = new HashMap<>();
        AdaugaProdus(1, "A");
        AdaugaProdus(0, "C");
        AdaugaProdus(2, "B");

        AdaugaTranzactie(TipTranzactie.INTRARE, LocalDate.of(2020,1,3), 1, 10);
        AdaugaTranzactie(TipTranzactie.INTRARE, LocalDate.of(2020,1,6), 2, 10);
        AdaugaTranzactie(TipTranzactie.IESIRE, LocalDate.of(2020,2,6), 1, 7);
        AdaugaTranzactie(TipTranzactie.INTRARE, LocalDate.of(2019,2,6), 1, 7);
        AfisareStocuri();
    }
}
