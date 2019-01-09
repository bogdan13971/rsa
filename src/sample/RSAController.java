package sample;

import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class RSAController {
    @FXML private TextArea plaintext;
    @FXML private TextArea ciphertext;
    @FXML private TextArea public_key;
    @FXML private TextArea private_key;
    @FXML private TextField ciphertext_block;
    @FXML private TextField plaintext_block;
    RSA rsa;

    @FXML
    public void initialize(){
        this.ciphertext.setText("");
        this.plaintext.setText("");
        this.public_key.setText("");
        this.private_key.setText("");
        this.ciphertext_block.setText("");
        this.plaintext_block.setText("");
        public_key.textProperty().addListener((obs, oldText, newText) -> {
            String new_value = public_key.getText();
            rsa.setPublicKey(new_value);
        });
        private_key.textProperty().addListener((obs, oldText, newText) -> {
            String new_value = private_key.getText();
            rsa.setPrivateKey(new_value);
        });
        ciphertext_block.textProperty().addListener((obs, oldText, newText) -> {
            Integer new_value = Integer.parseInt(ciphertext_block.getText());
            rsa.setL(new_value);
        });
        plaintext_block.textProperty().addListener((obs, oldText, newText) -> {
            Integer new_value = Integer.parseInt(plaintext_block.getText());
            rsa.setK(new_value);
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
        if (!validate_plaintext(plaintext.getText()))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "The plaintext given is incorrect.");
            alert.show();
            return;
        }
        String ciphertext = rsa.encrypt(plaintext.getText());
        this.ciphertext.setText(ciphertext);
    }

    public boolean validate_ciphertext(String message)
    {
        if(Pattern.matches("[A-Z_]+", message))
        {
            if (message.length()<rsa.getL() || message.length()<rsa.getK())
                return false;
            return true;
        }
        return false;
    }

    public boolean validate_plaintext(String message)
    {
        if(Pattern.matches("[a-z_]+", message))
        {
            if (message.length()<rsa.getL() || message.length()<rsa.getK())
                return false;
            return true;
        }
        return false;
    }

    @FXML
    public void decrypt()
    {
        if (!validate_ciphertext(ciphertext.getText()))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "The ciphertext given is incorrect.");
            alert.show();
            return;
        }
        String plaintext = rsa.decrypt(ciphertext.getText());
        this.plaintext.setText(plaintext);
    }

    public void setRSA(RSA rsa){
        this.rsa = rsa;
    }
}
