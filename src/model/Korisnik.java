package model;

public class Korisnik extends Tablica {
    public Korisnik() {}
    public Korisnik(String korisnickoIme, String lozinka, String uloga) {
        KorisnickoIme = korisnickoIme;
        Lozinka = lozinka;
        Uloga = uloga;
    }

    public Long ID;
    public String KorisnickoIme;
    public String Lozinka;
    public String Uloga;

    public Long getID() {
        return ID;
    }
}
