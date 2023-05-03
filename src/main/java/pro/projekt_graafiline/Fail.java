package pro.projekt_graafiline;
import pro.projekt_graafiline.Aktsia;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Fail {
   /* public static void main(String[] args) throws Exception {
        String aktsia = "AAPL";
        Aktsia apple = new Aktsia(aktsia,new ArrayList<String>());
        failist(aktsia, apple);
        System.out.println(apple.viimane_hind());
        System.out.println(apple.keskmine_hind());
        String kp = "2022-06-13, 2023-01-01";
        String[] kuupaevad = kp.split(",");
        System.out.println(apple.hinnamuutus(kuupaevad[0], kuupaevad[1]));
        //System.out.println(apple.hinnamuutus("2022-03-07", "2023-03-03"));
    } */

    public static Aktsia failist(String aktsia_nimi, Aktsia aktsia) throws Exception {
        ArrayList<String> data = new ArrayList<>();
        String nimi = "aktsiate_andmed\\"+aktsia_nimi+".csv";
        Scanner sc = new Scanner(new File(nimi));
        sc.next();
        sc.next();
        while (sc.hasNext())
        {
            data.add(sc.next());
        }
        sc.close();
        aktsia.setAndmed(data);
        return aktsia;
    }

    public static Inimene andmed_isikule(String failinimi, Inimene isik) throws Exception {

        File fail = new File(failinimi);
        try (Scanner sc = new Scanner(fail, "UTF-8")) {
            while (sc.hasNextLine()) {
                String rida = sc.nextLine();
                String[] tükid = rida.split(",");
                String summa = tükid[0];
                String liik = tükid[1];

                if (summa.charAt(0) == '+'){ //tegemist tuluga, loome Tulu klassi objekti
                    //võtan + märgi eest ära:
                    String summaIlmamärgita = summa.replace("+","");
                    double summaDouble = Double.parseDouble(summaIlmamärgita);
                    Tulu sissetulek = new Tulu(liik, summaDouble);
                    ArrayList<Tulu> tulud_siiani = isik.getInimeseTulud();
                    tulud_siiani.add(sissetulek);
                    isik.setInimeseTulud(tulud_siiani);
                }
                if (summa.charAt(0) == '-'){ //tegemist kuluga, loome Kulu klassi objekti
                    //võtan - märgi eest ära:
                    String summaIlmamärgita = summa.replace("-","");
                    double summaDouble = Double.parseDouble(summaIlmamärgita);
                    Kulu kulutus = new Kulu(liik, summaDouble);
                    ArrayList<Kulu> kulud_siiani = isik.getInimeseKulud();
                    kulud_siiani.add(kulutus);
                    isik.setInimeseKulud(kulud_siiani);
                }
            }
        }
        return(isik) ;
    }
}
