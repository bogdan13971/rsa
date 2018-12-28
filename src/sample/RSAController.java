package sample;

import javafx.fxml.FXML;
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
    }

    @FXML
    public void generate_new_keys()
    {
        System.out.println("generate keys");
    }

    @FXML
    public void encrypt()
    {
        System.out.println("encrypt");
    }

    @FXML
    public void decrypt()
    {
        System.out.println("decrypt");
    }

    public void setRSA(RSA rsa){
        this.rsa = rsa;
    }
}
