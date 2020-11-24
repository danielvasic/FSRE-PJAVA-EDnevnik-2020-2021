package model;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Tablica {

    private static String dajImeTablice (Class cls){
        String [] dijeloviImenaKlase = cls.getName().split("\\.");
        return dijeloviImenaKlase[dijeloviImenaKlase.length-1].toLowerCase();
    }

    public static List<?> dohvatiSve(Class cls) throws Exception{
        String tablica = dajImeTablice(cls);
        String SQL = "SELECT * FROM " + tablica;
        Statement iskaz = Baza.KONEKCIJA.createStatement();
        ResultSet rezultat = iskaz.executeQuery(SQL);
        List<Object> lista = new ArrayList<>();
        while(rezultat.next()){
            Object objekt = Class.forName(cls.getName()).newInstance();
            for(Field f :  objekt.getClass().getDeclaredFields()){
                f.set(objekt, rezultat.getObject(f.getName()));
            }
            lista.add(objekt);
        }
        return lista;
    }
}
