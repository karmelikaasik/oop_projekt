package pro.projekt_graafiline;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage peaLava) {
        BorderPane bp = new BorderPane();
        Text tekst = new Text("Tere tulemast kasutama meie programmi!\n" +
                "               Mida soovid teha?");
        tekst.setFont(new Font(25));
        bp.setTop(tekst);
        bp.setAlignment(tekst,Pos.TOP_CENTER);
        Scene stseen1 = new Scene(bp, 535, 535, Color.SNOW);
        peaLava.setTitle("Avaleht");
        peaLava.setScene(stseen1);
        peaLava.show();
    }

    public static void main(String[] args) {
        launch();
    }
}