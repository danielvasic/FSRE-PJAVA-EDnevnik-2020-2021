package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Tablica {

    public static Object dohvati (Class cls, int id) throws Exception{
        String tablica = dajImeTablice(cls);
        String SQL = "SELECT * FROM " + tablica + " WHERE id=" + String.valueOf(id);
        Statement upit = Baza.KONEKCIJA.createStatement();
        ResultSet rezultat = upit.executeQuery(SQL);
        if (rezultat.next()){
            Object obj = Class.forName(cls.getName()).newInstance();
            for (Field f: cls.getDeclaredFields()){
                f.set(obj, rezultat.getObject(f.getName()));
            }
            return obj;
        } else {
            throw new Exception("Entitet s tim ID-om ne postoji.");
        }
    }

    public void pobrisi() throws Exception {
        Class klasa = this.getClass();
        String tablica = dajImeTablice(klasa);
        Field id = klasa.getField("ID");
        String SQL = "DELETE FROM " + tablica + " WHERE id=?";
        PreparedStatement upit = Baza.KONEKCIJA.prepareStatement(SQL);
        upit.setObject(1, id.get(this));
        upit.executeUpdate();
    }

    public void uredi () throws Exception {
        String tablica = dajImeTablice(this.getClass());
        Field id = this.getClass().getField("ID");
        StringBuilder SQL = new StringBuilder();
        SQL.append("UPDATE ").append(tablica).append(" SET ");
        int index = 0;
        for (Field f : this.getClass().getDeclaredFields()){
            if (!f.getName().equals("ID")){
                SQL.append(f.getName()).append("=?");
            }
            index++;
            if (index > 1 && index < this.getClass().getDeclaredFields().length){
                SQL.append(", ");
            }
        }
        index = 1;
        SQL.append("WHERE id=").append(id.get(this));
        PreparedStatement upit = Baza.KONEKCIJA.prepareStatement(SQL.toString());
        for (Field f : this.getClass().getDeclaredFields()){
            if(!f.getName().equals("ID")){
                upit.setObject(index, f.get(this));
                index++;
            }
        }

        upit.executeUpdate();
    }

    public void spasi () throws Exception {
        StringBuilder INSERT_SQL_UPIT = new StringBuilder();
        String tablica = dajImeTablice(getClass());
        INSERT_SQL_UPIT.append("INSERT INTO ").append(tablica);
        INSERT_SQL_UPIT.append("(");
        int index = 0;
        for (Field f : getClass().getDeclaredFields()){
            if (!f.getName().equals("ID")) {
                INSERT_SQL_UPIT.append(f.getName());
            }
            index++;
            if (index > 1 && index < getClass().getDeclaredFields().length)
                INSERT_SQL_UPIT.append(", ");

        }
        INSERT_SQL_UPIT.append(") VALUES (");
        index = 0;
        for (Field f : getClass().getDeclaredFields()){
            if (!f.getName().equals("ID")) {
                INSERT_SQL_UPIT.append("?");
            }
            index++;
            if (index > 1 && index < getClass().getDeclaredFields().length)
                INSERT_SQL_UPIT.append(", ");

        }
        INSERT_SQL_UPIT.append(")");
        System.out.println(INSERT_SQL_UPIT);
        PreparedStatement upit = Baza.KONEKCIJA.prepareStatement(
                INSERT_SQL_UPIT.toString(), Statement.RETURN_GENERATED_KEYS
        );
        index = 1;
        for (Field f : getClass().getDeclaredFields()){
            if (!f.getName().equals("ID")){
                upit.setObject(index, f.get(this));
                index++;
            }
        }

        upit.executeUpdate();

        ResultSet rezultat = upit.getGeneratedKeys();

        if (rezultat.next()){
            getClass().getDeclaredField("ID")
                    .set(this, rezultat.getLong(1));
        }

    }

    private static String dajImeTablice (Class cls){
        String [] dijeloviImenaKlase = cls.getName().split("\\.");
        return dijeloviImenaKlase[dijeloviImenaKlase.length-1].toLowerCase();
    }

    public static ObservableList<Object> dohvatiSve(Class cls) throws Exception{
        String tablica = dajImeTablice(cls);
        String SQL = "SELECT * FROM " + tablica;
        Statement iskaz = Baza.KONEKCIJA.createStatement();
        ResultSet rezultat = iskaz.executeQuery(SQL);
        ObservableList<Object> lista = FXCollections.observableArrayList();
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
