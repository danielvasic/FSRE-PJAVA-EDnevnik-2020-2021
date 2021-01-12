package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class KorisniciController implements Initializable {
    @FXML
    Label userDataLbl;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userDataLbl.setText(LoginController.logiraniKorisnik.KorisnickoIme.toUpperCase());
    }
}
