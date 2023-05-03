package pro.projekt_graafiline;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Inimene {
    private String isikukood;
    private String isikunimi;
    private ArrayList<Tulu> inimeseTulud;
    private ArrayList<Kulu> inimeseKulud;

    public Inimene(String isikukood, String isikunimi) {
        this.isikukood = isikukood;
        this.isikunimi = isikunimi;
        this.inimeseTulud = new ArrayList<>();
        this.inimeseKulud = new ArrayList<>();
    }

    public Inimene(String isikukood, String isikunimi, ArrayList<Tulu> inimeseTulud, ArrayList<Kulu> inimeseKulud) {
        this.isikukood = isikukood;
        this.isikunimi = isikunimi;
        this.inimeseTulud = inimeseTulud;
        this.inimeseKulud = inimeseKulud;
    }

    public String getIsikukood() {
        return isikukood;
    }

    public String getIsikunimi() {
        return isikunimi;
    }

    public ArrayList<Tulu> getInimeseTulud() {
        return inimeseTulud;
    }

    public ArrayList<Kulu> getInimeseKulud() {
        return inimeseKulud;
    }

    public void setInimeseTulud(ArrayList<Tulu> inimeseTulud) {
        this.inimeseTulud = inimeseTulud;
    }

    public void setInimeseKulud(ArrayList<Kulu> inimeseKulud) {
        this.inimeseKulud = inimeseKulud;
    }
    
    public double bilanss(){ // tulud - kulud
        return tulu_summa() - kulu_summa();
    }

    public double tulu_summa(){ // leiab tulude kogusumma
        double tuludKokku = 0.0;
        for (Tulu tulu : inimeseTulud) {
            tuludKokku += tulu.getTuluSumma();}

        return (tuludKokku);
    }
    public double kulu_summa(){ // leiab kulude kogusumma
        double kuludKokku = 0.0;
        for (Kulu kulu : inimeseKulud) {
            kuludKokku += kulu.getKulutatudSumma();}
        return (kuludKokku);
    }

    public void kirjuta_faili(String fail) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(fail))) {
            dos.writeUTF(this.isikukood);
            dos.writeUTF(this.isikunimi);
            dos.writeInt(inimeseKulud.size());
            for (Kulu kulu : inimeseKulud) {
                dos.writeUTF(kulu.getKuluLiik());
                dos.writeDouble(kulu.getKulutatudSumma());
            }
            dos.writeInt(inimeseTulud.size());
            for (Tulu tulu : inimeseTulud) {
                dos.writeUTF(tulu.getTuluLiik());
                dos.writeDouble(tulu.getTuluSumma());
            }
        }
    }
    public Inimene loe_failist(File fail) throws Exception {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(fail))) {
            String isikukood = dis.readUTF();
            String nimi = dis.readUTF();
            ArrayList<Kulu> kulud = new ArrayList<>();
            ArrayList<Tulu> tulud = new ArrayList<>();
            int kulusid = dis.readInt();
            for (int i = 0; i < kulusid; i++) {
                String liik = dis.readUTF();
                double summa = dis.readDouble();
                kulud.add(new Kulu(liik,summa));
            }
            int tulusid = dis.readInt();
            for (int i = 0; i < tulusid; i++) {
                String liik = dis.readUTF();
                double summa = dis.readDouble();
                tulud.add(new Tulu(liik,summa));
            }
            return new Inimene(isikukood, nimi, tulud, kulud);
        }
    }

    @Override
    public String toString() {
        if (!(inimeseKulud.isEmpty()) & !(inimeseTulud.isEmpty())){
            return isikunimi + ", su pangakontol on " +bilanss()+ "€. \n" +
                    "Kulutasid " +kulu_summa()+ "€ ja teenisid " +tulu_summa()+ "€.";}
        if (!(inimeseKulud.isEmpty())){
            return isikunimi + ", kulutasid " +kulu_summa()+ "€.";
        }
        if (!(inimeseTulud.isEmpty())){
            return isikunimi + ", said tulu " +tulu_summa()+ "€.";
        }
        else return isikunimi + ", sul pole veel sisestatud kulusid ega tulusid";
    }
}
