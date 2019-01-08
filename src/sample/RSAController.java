package sample;

import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

public class RSAController {
    @FXML private TextArea plaintext;
    @FXML private TextArea ciphertext;
    @FXML private TextArea public_key;
    @FXML private TextArea private_key;
    RSA rsa;

    @FXML
    public void initialize(){
        this.ciphertext.setText("");
        this.plaintext.setText("");
        this.public_key.setText("");
        this.private_key.setText("");
        public_key.textProperty().addListener((obs, oldText, newText) -> {
            String new_value = public_key.getText();
            rsa.setPublicKey(new_value);
            System.out.println("Text changed from "+oldText+" to "+newText);
        });
        private_key.textProperty().addListener((obs, oldText, newText) -> {
            String new_value = private_key.getText();
            rsa.setPrivateKey(new_value);
            System.out.println("Text changed from "+oldText+" to "+newText);
        });
    }

    @FXML
    public void generate_new_keys()
    {
        rsa.generateRandomPrimes();
        public_key.setText(rsa.getPublicKey());
        private_key.setText(rsa.getPrivateKey().toString());
    }

    @FXML
    public void encrypt()
    {
        String ciphertext = rsa.encrypt(plaintext.getText());
        if (!validate_ciphertext(ciphertext))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "The ciphertext given is incorrect.");
            alert.show();
            return;
        }
        this.ciphertext.setText(ciphertext);
    }

    public boolean validate_ciphertext(String message)
    {
        if(Pattern.matches("[A-Z_]+", message))
        {
            return true;
        }
        return false;
    }

    public boolean validate_plaintext(String message)
    {
        if(Pattern.matches("[a-z_]+", message))
        {
            System.out.println("of");
            return true;
        }
        return false;
    }

    @FXML
    public void decrypt()
    {
        String plaintext = rsa.decrypt(ciphertext.getText());
        if (!validate_plaintext(plaintext))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "The plaintext given is incorrect.");
            alert.show();
            return;
        }
        this.plaintext.setText(plaintext);
    }

    public void setRSA(RSA rsa){
        this.rsa = rsa;
    }
}
