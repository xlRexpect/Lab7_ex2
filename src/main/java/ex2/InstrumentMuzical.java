package ex2;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Objects;

enum Tip{
    CHITARA,
    SET_TOBE

}
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public abstract class  InstrumentMuzical {
    private String producator;
    private float pret;

    private Tip tip;


    public InstrumentMuzical() {
    }

    public InstrumentMuzical(String producator, float pret, Tip tip) {
        this.producator = producator;
        this.pret = pret;
        this.tip=tip;
    }

    @Override
    public String toString() {
        return "producator='" + producator + '\'' +
                ", pret=" + pret + ", tip="+tip;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstrumentMuzical that = (InstrumentMuzical) o;
        System.out.println("instrument deja in set");
        return Float.compare(pret, that.pret) == 0 && Objects.equals(producator, that.producator) && tip == that.tip;
    }

    @Override
    public int hashCode() {
        return Objects.hash(producator, pret, tip);
    }

    public String getProducator() {
        return producator;
    }

    public void setProducator(String producator) {
        this.producator = producator;
    }

    public float getPret() {
        return pret;
    }

    public void setPret(float pret) {
        this.pret = pret;
    }

    public Tip getTip() {
        return tip;
    }

    public void setTip(Tip tip) {
        this.tip = tip;
    }
}
