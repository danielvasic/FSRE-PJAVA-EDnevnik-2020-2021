package model;

public class Nastavnik extends Tablica {
    Long ID;
    String Ime;
    String Prezime;
    String JMBG;
    Long IDKorisnika;

    public Nastavnik(String ime, String prezime, String JMBG, Long IDKorisnika) {
        Ime = ime;
        Prezime = prezime;
        this.JMBG = JMBG;
        this.IDKorisnika = IDKorisnika;
    }
}
