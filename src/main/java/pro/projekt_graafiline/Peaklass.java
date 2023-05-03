package pro.projekt_graafiline;
import java.io.File;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Peaklass {
    public static void main(String[] args) throws Exception{
        ArrayList<Inimene> inimesed = new ArrayList<>(); // arraylist Inimene objektide hoidmiseks
        loe_failid(inimesed); // loeb sisse varasemalt programmi kasutanud isikute andmed
        System.out.println("""
                Tere tulemast kasutama meie programmi!\s
                Mida soovid teha?\s
                Sisestada tulusid ja kulusid : kirjuta '1' \s
                Uurida aktate kohta : kirjuta '2' \s
                Programmist lahkumiseks vajuta 'enter' nuppu
                 """);
        peamine(inimesed);
    }

    public static void loe_failid(ArrayList<Inimene> inimesed) throws Exception {
        File folder = new File("kasutajad");
        // File folder = new File("kasutajad\\");
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            Inimene isik = new Inimene("nimi", "isikukood");
            isik = isik.loe_failist(file);
            inimesed.add(isik);
        }
    }

    public static void peamine(ArrayList<Inimene> inimesed) throws Exception {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Sisesta tegevuse number või vajuta 'enter' nuppu: ");
            String str = sc.nextLine();
            if (str.equals("1")) {
                System.out.println("Esmalt kirjuta, kes sa oled.");
                while (true){
                    System.out.print("Sisesta enda isikuandmed kujul 'nimi, isikukood': ");
                    try {
                        String isikuandmed = sc.nextLine();
                        if (isikuandmed.equals("")) break;
                        String[] andmed = isikuandmed.split(",");
                        if (andmed[0].equals("") || andmed[1].equals("")) continue;
                        Inimene kasutaja = new Inimene(andmed[1], andmed[0]);
                        boolean olemas = false; // false kui kasutaja ei ole veel inimeste listis, true kui on
                        for (Inimene isik : inimesed) {
                            if (isik.getIsikunimi().equals(kasutaja.getIsikunimi()) && isik.getIsikukood().equals(kasutaja.getIsikukood())) {
                                // kui kahel Inimene objektil on sama nimi ja isikukood siis on nad samad
                                kasutaja = isik;
                                olemas = true;
                                System.out.println("Tere taas, " + kasutaja + "! \n" +
                                        "Kas soovid enda tulusid ja kulusid sisestada failist või käsitsi?");
                            }
                        }
                        if (!olemas) {
                            inimesed.add(kasutaja); // kui kasutajat veel pole, siis lisame
                            System.out.println("Tere, " + kasutaja + "! \n" +
                                    "Kas soovid enda tulusid ja kulusid sisestada failist või käsitsi?");
                        }
                        System.out.print("Kirjuta 'K' kui käsitsi, 'F' kui failist, 'enter' väljumiseks: ");
                        String kuidas = sc.nextLine();
                        if (kuidas.equals("K")){
                            kulusisestus(kasutaja);
                        }
                        else if (kuidas.equals("F")){
                            // loeme kulud ja tulud failist
                            System.out.print("Sisesta failinimi kujul 'fail.txt': ");
                            String fail = sc.nextLine();
                            Fail.andmed_isikule(fail, kasutaja);
                            System.out.println(kasutaja); // väljastame kasutajale ta rahalise seisu
                        }
                        else if (kuidas.equals("")) break;
                        kasutaja.kirjuta_faili("kasutajad\\"+kasutaja.getIsikunimi()+".bin");
                    }
                    catch (Exception e) {
                        System.out.println("vale sisend, proovi uuesti!");
                    }
                }
            } else if (str.equals("2")) {
                aktsiad();
            }
            else if (str.equals("")) {
                System.out.println("Väljusite programmist.");

                break;
            }
            else System.out.println("Ei sobi valikuks");

            System.out.println("Kui soovid isestada tulusid ja kulusid, kirjuta '1' \n" +
                    "Kui uurida aktate kohta, kirjuta '2' \n" +
                    "Programmist lahkumiseks vajuta 'enter' nuppu");
        }
    }
    public static void kulusisestus(Inimene kasutaja){
        Scanner sc = new Scanner(System.in);
        while (true){
            Random rand = new Random();
            Double suvaarv = rand.nextDouble(10,100);
            System.out.println("Sisesta oma tulu või kulu kujul 'liik, summa, selgitus'. Väljumiseks 'enter'" +
                    " \n"+
                    "Näiteks 'kulu, "+suvaarv+", üür'");
            String sisestus = sc.nextLine();
            String[] tykid = sisestus.split(",");
            if (tykid[0].equals("kulu")){
                // lisame uue kulu kasutajale
                Kulu kulu = new Kulu(tykid[2], Double.parseDouble(tykid[1]));
                ArrayList<Kulu> kulud = kasutaja.getInimeseKulud();
                kulud.add(kulu);
                kasutaja.setInimeseKulud(kulud);
            }
            else if (tykid[0].equals("tulu")){
                // kasutajale uus tulu
                Tulu tulu = new Tulu(tykid[2], Double.parseDouble(tykid[1]));
                ArrayList<Tulu> tulud = kasutaja.getInimeseTulud();
                tulud.add(tulu);
                kasutaja.setInimeseTulud(tulud);
            }
            else break;
        }
        System.out.println(kasutaja); // väljastame kasutajale ta rahalise seisu
    }

    public static void aktsiad() throws Exception {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Vali endale huvipakkuv aktsia järgmisest listist: \n " +
                    "AAPL, AMZN, EOLS, GOOG, IBM, META, NVDA, SNAP, SPOT, TSLA");
            System.out.print("Sisesta aktsia nimi trükitähtedes: ");
            String lyhend = sc.nextLine();
            Aktsia aktsia = new Aktsia(lyhend, new ArrayList<>());
            aktsia = Fail.failist(lyhend, aktsia);
            double viimane = aktsia.viimane_hind();
            double keskmine = aktsia.keskmine_hind();
            System.out.println("Selle aktsia viimane hind oli " + viimane + " dollarit ehk " + aktsia.valuuta_vahetus(viimane, 0.93) + " eurot.");
            System.out.println("Keskmine hind viimase aasta jooksul on " + aktsia.keskmine_hind() + " dollarit ehk " +
                    aktsia.valuuta_vahetus(keskmine, 0.93) + " eurot.");
            System.out.println("Kui soovid näha hinna muutumist mingis vahemikus, siis sisesta vahemiku algus- ja lõppkuupäev, \n" +
                    "kujul '2022-06-12, 2023-01-26'. Andmed algavad kuupäevast 2022-03-07 ja lõppevad 2023-03-03. \n" +
                    "Mõned kuupäevad võivad olla puudu ning programm võib seetõttu anda valesid tulemusi.");
            String kp = sc.nextLine();
            if (!kp.isEmpty()) {
                String[] kuupaevad = kp.split(",");
                try{
                    System.out.println(aktsia.hinnamuutus(kuupaevad[0], kuupaevad[1]));}
                catch (ParseException e){
                    System.out.println("Valel kujul kuupäev.");
                }
            }
            else break;
        }
    }
}

