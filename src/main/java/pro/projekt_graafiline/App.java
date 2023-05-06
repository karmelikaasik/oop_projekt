package pro.projekt_graafiline;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.text.ParseException;
import java.util.ArrayList;

public class App extends Application {
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
                aken_kulud_tulud(uus, lava);
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

    private static void aken_kulud_tulud(Stage uus, Stage vana){ // kulude ja tulude sisestamise aken
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
        Scene stseen = new Scene(bp, 535, 535, Color.SNOW);
        uus.setScene(stseen);
        uus.show();
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

                kuva_muutus.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            double muutus = aktsia.hinnamuutus(algus_kp.getText(),lõpp_kp.getText());
                            muut.setText("Hind muutus ajavahemikul " + algus_kp.getText() +
                                    " kuni " + lõpp_kp.getText() + "\n" + muutus + " dollarit.");
                            muut.setFont(new Font(15));
                            muut.setPadding(new Insets(10,20,10,20));
                            muut.setWrapText(true);
                        }
                        catch (ParseException e){
                            vea_aken("Kuupäev ei ole kujul 'YYYY-MM-DD'", Alert.AlertType.ERROR);
                        }
                        catch (ValeKuupäevException e){
                            vea_aken(e.getMessage(), Alert.AlertType.ERROR);
                        }
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
        uus.show();
    }

    public static void vea_aken(String sõnum, Alert.AlertType tüüp){ // errorite kuvamiseks kasutajale
        Alert viga = new Alert(tüüp);
        viga.setContentText(sõnum);
        viga.show();
    }

    public static void main(String[] args) {
        launch();
    }
}