import model.Baza;
import model.Korisnik;
import model.Tablica;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Program {
    public static void main (String [] args){

        try {
            List<Korisnik> korisnici = (List<Korisnik>) Tablica.dohvatiSve(Korisnik.class);
            System.out.println(korisnici);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
