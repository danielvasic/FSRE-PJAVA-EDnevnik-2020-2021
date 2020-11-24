import baza.Baza;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Program {
    public static void main (String [] args){
        Baza baza = new Baza();
        try {
            Statement iskaz = Baza.konekcija.createStatement();
            ResultSet rezultat = iskaz.executeQuery("SELECT * FROM Korisnik");
            while(rezultat.next()){
                System.out.println(
                        rezultat.getInt(1) +" " +
                        rezultat.getString(2) + " " +
                        rezultat.getString(3) + " " +
                        rezultat.getString("Uloga")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
