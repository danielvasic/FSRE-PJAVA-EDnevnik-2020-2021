package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Baza {
    private static String racunalo = "localhost";
    private static String korisnik = "root";
    private static String lozinka = "";
    private static String baza = "eskole";

    public static Connection KONEKCIJA = null;

    static {
        String url = "jdbc:mysql://" + Baza.racunalo + ":3306/" + Baza.baza;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Baza.KONEKCIJA = DriverManager.getConnection(
                    url, Baza.korisnik, Baza.lozinka
            );
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
