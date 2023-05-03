package pro.projekt_graafiline;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage peaLava) {
        Scene stseen1 = new Scene(esimene_aken(), 535, 535, Color.SNOW);
        peaLava.setTitle("Avaleht");
        peaLava.setScene(stseen1);
        peaLava.show();
    }

    public static BorderPane esimene_aken(){
        BorderPane bp = new BorderPane();
        VBox tekstid = new VBox(10);
        tekstid.setPadding(new Insets(50, 10, 0, 10));
        Text tekst1 = new Text("Tere tulemast kasutama meie programmi!");
        Text tekst2 = new Text("Mida soovid teha?");
        tekst1.setFont(new Font(25));
        tekst2.setFont(new Font(25));
        tekstid.setAlignment(Pos.CENTER);
        tekstid.getChildren().addAll(tekst1, tekst2);
        bp.setTop(tekstid);
        BorderPane.setAlignment(tekstid,Pos.TOP_CENTER);
        VBox vbox = new VBox(25);
        Button tulud_kulud = new Button("Sisestada tulusid ja kulusid");
        tulud_kulud.setStyle("-fx-font-size:20");
        Button aktsiad = new Button("Uurida aktsiate kohta");
        aktsiad.setStyle("-fx-font-size:20");
        Button välju = new Button("Programmist väljuda");
        välju.setStyle("-fx-font-size:20");
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(tulud_kulud,aktsiad,välju);
        bp.setCenter(vbox);
        BorderPane.setAlignment(vbox,Pos.TOP_CENTER);
        return bp;
    }

    public static void main(String[] args) {
        launch();
    }
}