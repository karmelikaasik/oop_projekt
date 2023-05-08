package pro.projekt_graafiline;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Programm extends Application {
    @Override
    public void start(Stage peaLava) {
        Scene stseen1 = new Scene(esimene_aken(peaLava), 535, 535, Color.SNOW);
        peaLava.setTitle("Avaleht");
        peaLava.setScene(stseen1);
        peaLava.setResizable(false);
        peaLava.show();
    }

    private static BorderPane esimene_aken(Stage lava){
        BorderPane bp = new BorderPane();

        VBox tekstid = new VBox(10); // ülemine tekst
        tekstid.setPadding(new Insets(50, 10, 0, 10));
        Text tekst1 = new Text("Tere tulemast kasutama meie programmi!");
        Text tekst2 = new Text("Mida soovid teha?");
        tekst1.setFont(new Font(25));
        tekst2.setFont(new Font(25));
        tekstid.setAlignment(Pos.CENTER);
        tekstid.getChildren().addAll(tekst1, tekst2);
        bp.setTop(tekstid);
        BorderPane.setAlignment(tekstid,Pos.TOP_CENTER);

        VBox vbox = new VBox(25); // nupude hoidmiseks
        Button tulud_kulud = new Button("Sisestada tulusid ja kulusid");
        tulud_kulud.setStyle("-fx-font-size:20");
        tulud_kulud.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage uus = new Stage();
                try {
                    aken_kulud_tulud(uus, lava);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                lava.hide();
            }
        });

        Button aktsiad = new Button("Uurida aktsiate kohta");
        aktsiad.setStyle("-fx-font-size:20");
        aktsiad.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage uus = new Stage();
                aken_aktsiad(uus,lava);
                lava.hide();
            }
        });

        Button välju = new Button("Programmist väljuda");
        välju.setStyle("-fx-font-size:20");
        välju.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lava.hide();
            }
        });

        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(tulud_kulud,aktsiad,välju);
        bp.setCenter(vbox);
        BorderPane.setAlignment(vbox,Pos.TOP_CENTER);
        return bp;
    }

    private static void aken_kulud_tulud(Stage uus, Stage vana) throws Exception { // kulude ja tulude sisestamise aken
        BorderPane bp = new BorderPane();
        Button tagasi = new Button("Tagasi");
        bp.setBottom(tagasi);
        BorderPane.setAlignment(tagasi,Pos.TOP_LEFT);
        tagasi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                uus.hide();
                vana.show();
            }
        });

        //kõigepealt sisselogimise aken, kus inimene sisestab enda nime ja isikukoodi
        VBox aken = new VBox(15);
        aken.setPadding(new Insets(10, 20, 5, 20));

        HBox hb1 = new HBox();
        hb1.setSpacing(10);
        HBox hb2 = new HBox();
        hb1.setSpacing(10);

        Label tutvustus = new Label("Esmalt kirjuta, kes sa oled.");
        tutvustus.setStyle("-fx-font-size:16");

        Label nimi = new Label("Nimi: ");
        TextField nime_koht = new TextField();
        Label id = new Label("Isikukood: ");
        TextField id_koht = new TextField();

        hb1.getChildren().addAll(nimi, nime_koht);
        hb2.getChildren().addAll(id,id_koht);
        hb1.setAlignment(Pos.CENTER);
        hb2.setAlignment(Pos.CENTER);

        Button edasi = new Button("Edasi");

        //Kasutaja võib enter vajutada nii nime kui ka id kasti peal, oluline on et mõlemad kastid oleksid täidetud
        id_koht.setOnKeyPressed(new EventHandler<KeyEvent>() {
            // tekstikasti sees enteri vajutamine teeb sama asja, mida allolevale nupule vajutamine
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    sisselogimise_reageering(id_koht, uus, nime_koht);
                }

            }
        });

        nime_koht.setOnKeyPressed(new EventHandler<KeyEvent>() {
            // viimase tekstikasti sees enteri vajutamine teeb sama asja, mida allolevale nupule vajutamine
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    sisselogimise_reageering(id_koht, uus, nime_koht);
                }

            }
        });

        edasi.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                sisselogimise_reageering(id_koht, uus, nime_koht);
            }
        });

        aken.getChildren().addAll(tutvustus, hb1, hb2, edasi);
        bp.setCenter(aken);
        aken.setAlignment(Pos.CENTER);
        Scene stseen = new Scene(bp, 535, 535, Color.SNOW);
        uus.setResizable(false);
        uus.setScene(stseen);
        uus.show();
    }

    private static void kulu_tulu_sisestuse_aken(Stage uus, Stage vana, TextField nime_koht, TextField id_koht) throws Exception {
        BorderPane bp2 = new BorderPane(); // peamine paigutusvahend
        Button tagasi = new Button("Tagasi");
        bp2.setLeft(tagasi);
        BorderPane.setAlignment(tagasi,Pos.BOTTOM_LEFT);
        tagasi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                uus.hide();
                vana.show();
            }
        });

        ArrayList<Inimene> inimesed = new ArrayList<>(); // arraylist Inimene objektide hoidmiseks
        Peaklass.loe_failid(inimesed);
        Map<String,String> isikud = new HashMap<>();
        for (Inimene inimene : inimesed) {
            isikud.put(inimene.getIsikukood(),inimene.getIsikunimi());
        }

        VBox kasutajaga_suhtlemine = new VBox();
        if (nime_koht.getText().equals("") || id_koht.getText().equals("")) vea_aken("Kumbki väli ei tohi olla tühi.", Alert.AlertType.ERROR);
        else if (isikud.containsKey(id_koht.getText()) && !(isikud.get(id_koht.getText()).equals(nime_koht.getText()))){
            // kui isikukoodile ei vasta sisestatud nimi, siis anname vastava teate
            vea_aken("Sellise isikukoodiga on seotud teine nimi.", Alert.AlertType.ERROR);
        }
        else {
            Label tere = new Label();
            tere.setStyle("-fx-font-size:16");
            Inimene kasutaja = new Inimene(id_koht.getText(), nime_koht.getText());
            boolean olemas = false;
            for (Inimene isik : inimesed) {
                if (isik.getIsikunimi().equals(kasutaja.getIsikunimi()) && isik.getIsikukood().equals(kasutaja.getIsikukood())) {
                    // kui kahel Inimene objektil on sama nimi ja isikukood siis on nad samad
                    kasutaja = isik;
                    olemas = true;
                    tere.setText("Tere taas, "+kasutaja.getIsikunimi());
                }
            }
            if (!olemas) {
                inimesed.add(kasutaja); // kui kasutajat veel pole, siis lisame
                tere.setText("Tere, "+kasutaja.getIsikunimi());
            }

            Label küsimus_sisestusViis = new Label( "Kas soovid enda tulusid ja kulusid sisestada failist või käsitsi?");
            küsimus_sisestusViis.setStyle("-fx-font-size:16");
            kasutajaga_suhtlemine.getChildren().addAll(tere, küsimus_sisestusViis);

            ChoiceBox<String> cb2 = new ChoiceBox<>(); //valikukast "käsitsi" või "failist"
            cb2.getItems().addAll("Käsitsi", "Failist");

            final Inimene finalKasutaja = kasutaja; // anonüümne klass soovib final-tüüpi Inimene-objekti
            cb2.setOnAction((event2) -> {
                if (cb2.getValue().equals("Käsitsi")){ //kui käsitsi soovib sisestada
                    VBox käsitsi = new VBox();
                    käsitsi.setPadding(new Insets(20,60,20,0));
                    käsitsi.setSpacing(15);
                    HBox hb4 = new HBox(5); // liigi jaoks
                    HBox hb5 = new HBox(5); // summa jaoks
                    HBox hb6 = new HBox(5); // märksõna jaoks

                    Label liik = new Label("Liik");
                    Label summa = new Label("Summa");
                    Label selgitus = new Label("Selgitus");
                    TextField liigi_koht = new TextField();
                    liigi_koht.setPromptText("kulu/tulu");
                    TextField summa_koht = new TextField();
                    Random rand = new Random();
                    Double suvaarv = rand.nextDouble(10,100);
                    summa_koht.setPromptText(String.valueOf(suvaarv));
                    TextField selgituse_koht = new TextField();
                    selgituse_koht.setPromptText("üür");

                    Button kuva = new Button("Salvesta ja kuva mu rahaline seis!");

                    Label raha = new Label();
                    raha.setFont(new Font(16));
                    //Enter vajutamine ükskõik millise kasti peal teeb sama asja, mida nupu 'kuva' vajutamine,
                    // oluline ainult, et kõik kastid oleksid täidetud

                   liigi_koht.setOnKeyPressed(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent keyEvent) {
                            if (keyEvent.getCode() == KeyCode.ENTER) {
                                käsitsi_sisestus_regeering(liigi_koht,summa_koht,selgituse_koht,finalKasutaja,raha);
                            }

                        }
                    });
                    summa_koht.setOnKeyPressed(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent keyEvent) {
                            if (keyEvent.getCode() == KeyCode.ENTER) {
                                käsitsi_sisestus_regeering(liigi_koht,summa_koht,selgituse_koht,finalKasutaja,raha);
                            }

                        }
                    });
                    selgituse_koht.setOnKeyPressed(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent keyEvent) {
                            if (keyEvent.getCode() == KeyCode.ENTER) {
                                käsitsi_sisestus_regeering(liigi_koht,summa_koht,selgituse_koht,finalKasutaja,raha);
                            }

                        }
                    });
                    kuva.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            käsitsi_sisestus_regeering(liigi_koht,summa_koht, selgituse_koht, finalKasutaja,raha);
                        }
                    });

                    hb4.getChildren().addAll(liik, liigi_koht);
                    hb5.getChildren().addAll(summa, summa_koht);
                    hb6.getChildren().addAll(selgitus, selgituse_koht);
                    hb4.setAlignment(Pos.CENTER);
                    hb5.setAlignment(Pos.CENTER);
                    hb6.setAlignment(Pos.CENTER);

                    käsitsi.getChildren().addAll(hb4, hb5, hb6, kuva, raha);
                    bp2.setCenter(käsitsi);
                    käsitsi.setAlignment(Pos.TOP_CENTER);
                    BorderPane.setAlignment(käsitsi,Pos.CENTER);
                }
                else { //kui failist
                    VBox failist = new VBox();
                    failist.setPadding(new Insets(20,60,20,0));
                    failist.setSpacing(15);
                    HBox hb3 = new HBox();
                    Label faili_naide = new Label("Sisesta failinimi kujul 'fail.txt': ");
                    Label fail = new Label("Failinimi: ");
                    TextField faili_koht = new TextField();
                    Button edasi2 = new Button("Edasi");
                    Label raha = new Label();
                    raha.setFont(new Font(16));
                    faili_koht.setOnKeyPressed(new EventHandler<KeyEvent>(){
                        public void handle(KeyEvent keyEvent) {
                            if (keyEvent.getCode() == KeyCode.ENTER) {
                                failist_sisestus_reageering(faili_koht,finalKasutaja,raha);
                            }
                        }
                    } );
                    edasi2.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            failist_sisestus_reageering(faili_koht, finalKasutaja, raha);
                        }
                    });

                    hb3.getChildren().addAll(fail, faili_koht);
                    hb3.setAlignment(Pos.CENTER);
                    BorderPane.setAlignment(hb3,Pos.CENTER);
                    failist.getChildren().addAll(faili_naide,hb3, edasi2, raha);
                    bp2.setCenter(failist);
                    failist.setAlignment(Pos.TOP_CENTER);
                }
            });
            kasutajaga_suhtlemine.getChildren().addAll(cb2);
            bp2.setTop(kasutajaga_suhtlemine);
            kasutajaga_suhtlemine.setAlignment(Pos.TOP_CENTER);
            vana.hide();
            Scene stseen2 = new Scene(bp2, 535, 535, Color.SNOW);
            uus.setScene(stseen2);
            uus.setResizable(false);
            uus.show();
        }
    }

    private static void sisselogimise_reageering(TextField id_koht, Stage uus, TextField nime_koht){
        Stage sisestus = new Stage();
        if (!id_koht.getText().equals("") && !nime_koht.getText().equals("")) {
            try {
                kulu_tulu_sisestuse_aken(sisestus, uus, nime_koht, id_koht);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else{
            vea_aken("Vigane sisestus, täida kõik lüngad!", Alert.AlertType.ERROR);
        }
    }

    private static void failist_sisestus_reageering(TextField faili_koht, Inimene finalKasutaja, Label raha){
        if (!faili_koht.getText().equals("")) { //kontrollib kas failinimi on sisestatud
            try {
                Fail.andmed_isikule(faili_koht.getText(), finalKasutaja);
                raha.setText(finalKasutaja.toString());
                try {
                    finalKasutaja.kirjuta_faili("kasutajad\\"+finalKasutaja.getIsikunimi()+finalKasutaja.getIsikukood()+".bin");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } catch (Exception e) {
                vea_aken("Vigane failinimi", Alert.AlertType.ERROR);
            }
        } else{
            vea_aken("Vigane sisestus, täida kõik lüngad!", Alert.AlertType.ERROR);
        }

    }

    private static void käsitsi_sisestus_regeering(TextField liigi_koht, TextField summa_koht, TextField selgituse_koht, Inimene finalKasutaja, Label raha){
        if (liigi_koht.getText().equalsIgnoreCase("kulu") && !summa_koht.getText().equals("") && !selgituse_koht.getText().equals("")){
            try {
                Kulu kulu = new Kulu(liigi_koht.getText(), Double.parseDouble(summa_koht.getText()));
                ArrayList<Kulu> kulud = finalKasutaja.getInimeseKulud();
                kulud.add(kulu);
                finalKasutaja.setInimeseKulud(kulud);
            }
            catch (NumberFormatException e){
                vea_aken("Summa ei ole numbriline.", Alert.AlertType.ERROR);
            }
        }
        else if (liigi_koht.getText().equalsIgnoreCase("tulu") && !summa_koht.getText().equals("") && !selgituse_koht.getText().equals("")){
            try{
                Tulu tulu = new Tulu(liigi_koht.getText(), Double.parseDouble(summa_koht.getText()));
                ArrayList<Tulu> tulud = finalKasutaja.getInimeseTulud();
                tulud.add(tulu);
                finalKasutaja.setInimeseTulud(tulud);
            }
            catch (NumberFormatException e){
                vea_aken("Summa ei ole numbriline.", Alert.AlertType.ERROR);
            }
        }
        else vea_aken("Vale või puuduv sisend!", Alert.AlertType.ERROR);
        raha.setText(finalKasutaja.toString());
        try {
            finalKasutaja.kirjuta_faili("kasutajad\\"+finalKasutaja.getIsikunimi()+finalKasutaja.getIsikukood()+".bin");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void aken_aktsiad(Stage uus, Stage vana){ // ahtsiate kohta uurimise aken
        BorderPane bp = new BorderPane();
        Button tagasi = new Button("Tagasi");
        bp.setBottom(tagasi);
        BorderPane.setAlignment(tagasi,Pos.TOP_LEFT);
        tagasi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                uus.hide();
                vana.show();
            }
        });

        VBox vali_aktsiad = new VBox(15);
        vali_aktsiad.setPadding(new Insets(10, 20, 5, 20));
        Label vali = new Label("Vali endale huvipakkuv aktsia järgmisest listist:");
        vali.setStyle("-fx-font-size:16");
        ChoiceBox<String> cb = new ChoiceBox<>();
        cb.getItems().addAll("AAPL","AMZN","EOLS","GOOG", "IBM", "META", "NVDA", "SNAP", "SPOT", "TSLA");

        cb.setOnAction((event) -> {
            try { // loeme sisse aktsiate andmed failist ja kuvame infot aktsiate kohta
                VBox aktsia_info = new VBox();
                Label viim = new Label(); // viimase hinna kuvamine
                viim.setPadding(new Insets(10,10,0,10));
                Label kesk = new Label(); // keskmise hinna uvamine
                Aktsia aktsia = Fail.failist(cb.getValue(),new Aktsia(cb.getValue(),new ArrayList<>()));
                double viimane = aktsia.viimane_hind();
                double keskmine = aktsia.keskmine_hind();
                // kuvame viimase ja keskmise hinna
                viim.setText("Selle aktsia viimane hind oli " + viimane + " dollarit ehk " + aktsia.valuuta_vahetus(viimane, 0.91) + " eurot.");
                kesk.setText("Keskmine hind viimase aasta jooksul on " + aktsia.keskmine_hind() + " dollarit ehk " +
                        aktsia.valuuta_vahetus(keskmine, 0.91) + " eurot.");
                viim.setFont(Font.font(15));
                kesk.setFont(Font.font(15));
                aktsia_info.getChildren().addAll(viim,kesk);
                bp.setCenter(aktsia_info);
                aktsia_info.setAlignment(Pos.TOP_CENTER);

                Label tutvustus = new Label("Kui soovid näha hinna muutumist mingis vahemikus, " +
                        "siis sisesta vahemiku algus- ja lõppkuupäev kujul '2022-06-12'. " +
                        "Andmed algavad kuupäevast 2022-03-07 ja lõppevad 2023-03-03. " +
                        "Mõned kuupäevad võivad olla puudu ning programm võib " +
                        "seetõttu anda valesid tulemusi.");
                tutvustus.setWrapText(true);
                tutvustus.setPadding(new Insets(10,20,10,20));
                tutvustus.setFont(Font.font(15));
                aktsia_info.getChildren().add(tutvustus);
                Label algus_tekst = new Label("Alguskuupäev: ");
                TextField algus_kp = new TextField();
                algus_kp.setPromptText("2022-03-07");
                Label lõpp_tekst = new Label("Lõpukuupäev: ");
                TextField lõpp_kp = new TextField();
                lõpp_kp.setPromptText("2023-03-03");
                HBox hb1 = new HBox();
                HBox hb2 = new HBox();
                hb1.getChildren().addAll(algus_tekst,algus_kp);
                hb2.getChildren().addAll(lõpp_tekst,lõpp_kp);
                aktsia_info.getChildren().addAll(hb1,hb2);
                hb1.setAlignment(Pos.CENTER);
                hb2.setAlignment(Pos.CENTER);

                Button kuva_muutus = new Button("Näita!");
                aktsia_info.getChildren().add(kuva_muutus);
                Label muut = new Label();
                aktsia_info.getChildren().add(muut);

                //enteri vajutamine edasi liikumiseks toimib mõlema kasti peal
                //oluline, et mõlemad kastid oleksid täidetud ning et õigel kujul
                algus_kp.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (event.getCode() == KeyCode.ENTER){
                            aktsiate_muutumine_regeering(aktsia,algus_kp,lõpp_kp,muut);
                        }
                    }
                });
                lõpp_kp.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (event.getCode() == KeyCode.ENTER){
                            aktsiate_muutumine_regeering(aktsia,algus_kp,lõpp_kp,muut);
                        }
                    }
                });
                kuva_muutus.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        aktsiate_muutumine_regeering(aktsia,algus_kp,lõpp_kp,muut);
                    }
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        vali_aktsiad.getChildren().addAll(vali, cb);
        bp.setTop(vali_aktsiad);
        vali_aktsiad.setAlignment(Pos.CENTER);
        Scene stseen = new Scene(bp, 535, 535, Color.SNOW);
        uus.setScene(stseen);
        uus.setResizable(false);
        uus.show();
    }

    private static void aktsiate_muutumine_regeering(Aktsia aktsia, TextField algus_kp,TextField lõpp_kp, Label muut) {
        if (!algus_kp.getText().equals("") && !lõpp_kp.getText().equals("")) {
            try {
                double muutus = aktsia.hinnamuutus(algus_kp.getText(), lõpp_kp.getText());
                muut.setText("Hind muutus ajavahemikul " + algus_kp.getText() +
                        " kuni " + lõpp_kp.getText() + "\n" + muutus + " dollarit.");
                muut.setFont(new Font(15));
                muut.setPadding(new Insets(10, 20, 10, 20));
                muut.setWrapText(true);
            } catch (ParseException e) {
                vea_aken("Kuupäev ei ole kujul 'YYYY-MM-DD'", Alert.AlertType.ERROR);
            } catch (ValeKuupäevException e) {
                vea_aken(e.getMessage(), Alert.AlertType.ERROR);
            }
         }
        else{
            vea_aken("Vigane sisestus, täida kõik lüngad!", Alert.AlertType.ERROR);
        }
    }

    private static void vea_aken(String sõnum, Alert.AlertType tüüp){ // errorite kuvamiseks kasutajale
        Alert viga = new Alert(tüüp);
        viga.setContentText(sõnum);
        viga.show();
    }

    private static void main(String[] args) {
        launch();
    }
}
