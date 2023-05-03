package pro.projekt_graafiline;
public class Kulu {
    private String kuluLiik;
    private double kulutatudSumma;

    public Kulu(String kuluLiik, double kulutatudSumma) {
        this.kuluLiik = kuluLiik;
        this.kulutatudSumma = kulutatudSumma;
    }

    public String getKuluLiik() {
        return kuluLiik;
    }

    public void setKuluLiik(String kuluLiik) {
        this.kuluLiik = kuluLiik;
    }

    public double getKulutatudSumma() {
        return kulutatudSumma;
    }

    public void setKulutatudSumma(double kulutatudSumma) {
        this.kulutatudSumma = kulutatudSumma;
    }

    @Override
    public String toString() {
        return "Kulud{" +
                "kuluLiik='" + kuluLiik + '\'' +
                ", kulutatudSumma=" + kulutatudSumma +
                '}';
    }
}
