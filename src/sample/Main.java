package sample;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage stage){
        stage.setTitle("RSA");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("rsa-view.fxml"));
            Region root = loader.load();
            RSAController ctrl = loader.getController();
            RSA rsa = new RSA();
            ctrl.setRSA(rsa);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }

//        RSA rsa = new RSA();
//        rsa.generateRandomPrimes();
//        System.out.println(rsa.decrypt("AYX_RLAGABAR"));
//
//        rsa.generateRandomPrimes();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
