package stocuri;

import java.time.LocalDate;

public class Tranzactie {
    private TipTranzactie tip;
    private LocalDate data;
    private int codProdus;
    private int cantitate;

    public Tranzactie(TipTranzactie tip, LocalDate data, int codProdus, int cantitate) {
        this.tip = tip;
        this.data = data;
        this.codProdus = codProdus;
        this.cantitate = cantitate;
    }

    public TipTranzactie getTip() {
        return tip;
    }

    public void setTip(TipTranzactie tip) {
        this.tip = tip;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getCodProdus() {
        return codProdus;
    }

    public void setCodProdus(int codProdus) {
        this.codProdus = codProdus;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    @Override
    public String toString() {
        return "Tranzactie{" +
                "tip=" + tip +
                ", data=" + data +
                ", codProdus=" + codProdus +
                ", cantitate=" + cantitate +
                '}';
    }
}
