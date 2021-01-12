package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Baza;
import model.Korisnik;
import model.Tablica;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginController {
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

                Parent root = FXMLLoader.load(getClass().getResource("../view/KorisniciView.fxml"));
                Stage stage = new Stage();
                stage.setTitle("");
                stage.setScene(new Scene(root, 300, 275));
                stage.show();

            } else {
                messageLbl.setText("Korisnički podaci su neispravni.");
            }
        } catch (Exception ex) {
            System.out.println("Nastala je greška: " + ex.getMessage());
            ex.printStackTrace();
        }

    }


}
