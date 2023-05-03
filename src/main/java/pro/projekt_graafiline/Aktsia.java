package pro.projekt_graafiline;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class Aktsia {
    private String nimi;
    private ArrayList<String> andmed;

    public Aktsia(String nimi, ArrayList<String> andmed) {
        this.nimi = nimi;
        this.andmed = andmed;
    }

    public String getNimi() {
        return nimi;
    }

    public ArrayList<String> getAndmed() {
        return andmed;
    }

    public void setAndmed(ArrayList<String> andmed) {
        this.andmed = andmed;
    }

    public double valuuta_vahetus(double dollarid, double kurss){
        return Math.round(dollarid * kurss);
    }

    public double viimane_hind(){ // viimane failis sisalduv hind
        String viimane = andmed.get(0);
        String[] osad = viimane.split(",");
        return Math.round(Double.parseDouble(osad[4]));
    }

    public double keskmine_hind(){ // keskmine üle kõigi faili hindade
        double summa = 0;
        for (int i = 0; i < andmed.size(); i++) {
            String väärtus = andmed.get(i).split(",")[4];
            summa += Double.parseDouble(väärtus);
        }
        return Math.round(summa/(andmed.size()-1));
    }

    public double hinnamuutus(String aeg_algus, String aeg_lõpp) throws ParseException {
        // kui palju aktsa hind mingil ajavahemikul muutus
        SimpleDateFormat aeg = new SimpleDateFormat("yyyy-MM-dd");
        double algus_summa = 0;
        double lõpp_summa = 0;
        if (aeg.parse(aeg_lõpp).compareTo(aeg.parse(aeg_algus)) >0 ){
            // algusaeg peab olema varasem kui lõppaeg
            for (String s : andmed) {
                String[] osad = s.split(",");
                if (osad[0].equals(aeg_algus)){
                    algus_summa = Double.parseDouble(osad[4]);
                }
                else if (osad[0].equals(aeg_lõpp)){
                    lõpp_summa = Double.parseDouble(osad[4]);}
            }
            return lõpp_summa - algus_summa;
        }
        else {
            System.out.println("Sobimatu ajavahemik");
            return Double.NaN;
        }
    }
}
