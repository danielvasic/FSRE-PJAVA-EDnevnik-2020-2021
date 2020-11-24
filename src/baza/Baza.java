package baza;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Baza {
    private String racunalo;
    private String korisnik;
    private String lozinka;
    private String baza;

    private String url;

    public static Connection konekcija;

    public Baza(String racunalo, String korisnik, String lozinka, String baza) {
        this.racunalo = racunalo;
        this.korisnik = korisnik;
        this.lozinka = lozinka;
        this.baza = baza;
        this.spoji();
    }

    public Baza() {
        this.racunalo = "localhost";
        this.korisnik = "root";
        this.lozinka = "";
        this.baza = "eskole";
        this.spoji();
    }

    private void spoji (){
        this.url = "jdbc:mysql://" + this.racunalo + ":3306/" + this.baza;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Baza.konekcija = DriverManager.getConnection(
                    this.url, this.korisnik, this.lozinka
            );
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
