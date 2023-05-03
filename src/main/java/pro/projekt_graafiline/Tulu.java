package pro.projekt_graafiline;
public class Tulu {
    private String tuluLiik;
    private double tuluSumma;

    public Tulu(String tuluLiik, double tuluSumma) {
        this.tuluLiik = tuluLiik;
        this.tuluSumma = tuluSumma;
    }

    public String getTuluLiik() {
        return tuluLiik;
    }

    public void setTuluLiik(String tuluLiik) {
        this.tuluLiik = tuluLiik;
    }

    public double getTuluSumma() {
        return tuluSumma;
    }

    public void setTuluSumma(double tuluSumma) {
        this.tuluSumma = tuluSumma;
    }

    @Override
    public String toString() {
        return "Tulud{" +
                "tuluLiik='" + tuluLiik + '\'' +
                ", tuluSumma=" + tuluSumma +
                '}';
    }
}

