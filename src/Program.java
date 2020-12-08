import model.Korisnik;
import model.Nastavnik;
import model.Tablica;
import java.util.List;

public class Program {
    public static void main (String [] args){
        /*
        Korisnik k = new Korisnik(
                "peroperic",
                "123456",
                "Administrator"
        );
        try {
            k.spasi();
            Nastavnik n = new Nastavnik(
                    "Pero",
                    "Perić",
                    "1234567",
                    k.getID()
            );

            n.spasi();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
         */
        try {
            Korisnik pero = (Korisnik) Tablica.dohvati(Korisnik.class, 9);
            pero.Uloga = "Nastavnik";
            pero.uredi();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
