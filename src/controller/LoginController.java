package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Baza;
import model.Korisnik;
import model.Tablica;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    @FXML
    TextField usernameTxtFld;

    @FXML
    TextField passwordTxtFld;

    @FXML
    Label messageLbl;

    static Korisnik logiraniKorisnik;

    @FXML
    public void onClickLoginHandle (ActionEvent e){
        String korisnickoIme = usernameTxtFld.getText();
        String lozinka = passwordTxtFld.getText();

        try {
            PreparedStatement upit = Baza.KONEKCIJA.prepareStatement("SELECT * FROM Korisnik WHERE KorisnickoIme=? AND Lozinka=?");
            upit.setString(1, korisnickoIme);
            upit.setString(2, lozinka);
            ResultSet rezultat = upit.executeQuery();

            if (rezultat.next()){
                messageLbl.setText("Uspješno ste se prijavili na sustav.");
                LoginController.logiraniKorisnik = (Korisnik) Tablica.dohvati(Korisnik.class, rezultat.getInt("id"));
                String pogled = "../view/" + LoginController.logiraniKorisnik.Uloga + "View.fxml";

                Parent root = FXMLLoader.load(getClass().getResource(pogled));
                Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
                stage.setTitle("Dobrodošli na sustav");
                stage.setScene(new Scene(root, 640, 500));
                stage.show();

            } else {
                messageLbl.setText("Korisnički podaci su neispravni.");
            }
        } catch (Exception ex) {
            System.out.println("Nastala je greška: " + ex.getMessage());
            ex.printStackTrace();
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
