package controller;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import model.Korisnik;
import model.Tablica;

import java.net.URL;
import java.util.ResourceBundle;

public class KorisniciController implements Initializable {
    @FXML
    Label userDataLbl;

    @FXML
    private TableView<Object> KorisniciTbl;

    @FXML
    private TableColumn IDTblCol;

    @FXML
    private TableColumn KorisnickoImeTblCol;

    @FXML
    private TableColumn LozinkaTblCol;

    @FXML
    private TableColumn UlogaTblCol;

    @FXML
    private TextField KorisnickoImeTxt;

    @FXML
    private PasswordField LozinkaTxt;

    @FXML
    ChoiceBox<String> UlogaTxt;

    private Korisnik odabraniKorisnik;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> ulogeLista = FXCollections.observableArrayList();
        ulogeLista.addAll("", "Administrator", "Nastavnik", "Učenik");
        this.UlogaTxt.setItems(ulogeLista);

        userDataLbl.setText(LoginController.logiraniKorisnik.KorisnickoIme.toUpperCase());
        try {
            ObservableList<Object> listaKorisnika = Tablica.dohvatiSve(Korisnik.class);

            IDTblCol.setCellValueFactory(
                    (Callback<TableColumn.CellDataFeatures<Korisnik, Long>, SimpleLongProperty>) korisnikLongCellDataFeatures -> new SimpleLongProperty(korisnikLongCellDataFeatures.getValue().getID())
            );

            KorisnickoImeTblCol.setCellValueFactory(
                    (Callback<TableColumn.CellDataFeatures<Korisnik, String>, SimpleStringProperty>) korisnikLongCellDataFeatures -> new SimpleStringProperty(korisnikLongCellDataFeatures.getValue().KorisnickoIme)
            );

            LozinkaTblCol.setCellValueFactory(
                    (Callback<TableColumn.CellDataFeatures<Korisnik, String>, SimpleStringProperty>) korisnikLongCellDataFeatures -> new SimpleStringProperty(korisnikLongCellDataFeatures.getValue().Lozinka)
            );

            UlogaTblCol.setCellValueFactory(
                    (Callback<TableColumn.CellDataFeatures<Korisnik, String>, SimpleStringProperty>) korisnikLongCellDataFeatures -> new SimpleStringProperty(korisnikLongCellDataFeatures.getValue().Uloga)
            );
            KorisniciTbl.setItems(listaKorisnika);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void odaberiKorisnika (MouseEvent evt){
        this.odabraniKorisnik = (Korisnik) this.KorisniciTbl.getSelectionModel().getSelectedItem();
        this.KorisnickoImeTxt.setText(this.odabraniKorisnik.KorisnickoIme);
        this.LozinkaTxt.setText(this.odabraniKorisnik.Lozinka);
        this.UlogaTxt.setValue(this.odabraniKorisnik.Uloga);
    }

    @FXML
    public void vratiKorisnikaNaNull (MouseEvent evt){
        this.odabraniKorisnik = null;
        KorisnickoImeTxt.setText("");
        LozinkaTxt.setText("");
        UlogaTxt.setValue("");
        ObservableList<Object> listaKorisnika = null;
        try {
            listaKorisnika = Tablica.dohvatiSve(Korisnik.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        KorisniciTbl.setItems(listaKorisnika);
    }

    @FXML
    public void spasiKorisnika (ActionEvent evt){
        Korisnik k;
        if (this.odabraniKorisnik == null) {
            k = new Korisnik();
        } else {
            k = this.odabraniKorisnik;
        }
        k.KorisnickoIme = KorisnickoImeTxt.getText();
        k.Lozinka = LozinkaTxt.getText();
        k.Uloga = UlogaTxt.getValue();

        KorisnickoImeTxt.setText("");
        LozinkaTxt.setText("");
        UlogaTxt.setValue("");

        try {
            if (this.odabraniKorisnik == null)
                k.spasi();
            else
                k.uredi();
            ObservableList<Object> listaKorisnika = Tablica.dohvatiSve(Korisnik.class);
            KorisniciTbl.setItems(listaKorisnika);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void pobrisiKorisnika (ActionEvent evt) {
        if (this.odabraniKorisnik != null) {
            try {
                this.odabraniKorisnik.pobrisi();
                ObservableList<Object> listaKorisnika = Tablica.dohvatiSve(Korisnik.class);
                KorisniciTbl.setItems(listaKorisnika);
            } catch (Exception e) {
                System.out.println("Nastala je greška:" + e.getMessage());
            }
        }
    }
}
