import java.io.FileNotFoundException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Usuari {
    public static final String REGEX_EMAIL = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
    public static final String REGEX_IBAN = "^(?:(?:IT|SM)\\d{2}[A-Z]\\d{22}|CY\\d{2}[A-Z]\\d{23}|NL\\d{2}[A-Z]{4}\\d{10}|LV\\d{2}[A-Z]{4}\\d{13}|(?:BG|BH|GB|IE)\\d{2}[A-Z]{4}\\d{14}|GI\\d{2}[A-Z]{4}\\d{15}|RO\\d{2}[A-Z]{4}\\d{16}|KW\\d{2}[A-Z]{4}\\d{22}|MT\\d{2}[A-Z]{4}\\d{23}|NO\\d{13}|(?:DK|FI|GL|FO)\\d{16}|MK\\d{17}|(?:AT|EE|KZ|LU|XK)\\d{18}|(?:BA|HR|LI|CH|CR)\\d{19}|(?:GE|DE|LT|ME|RS)\\d{20}|IL\\d{21}|(?:AD|CZ|ES|MD|SA)\\d{22}|PT\\d{23}|(?:BE|IS)\\d{24}|(?:FR|MR|MC)\\d{25}|(?:AL|DO|LB|PL)\\d{26}|(?:AZ|HU)\\d{27}|(?:GR|MU)\\d{28})$";

    private static double SALDO_INICIAL = 20;
    private String nomUsuari;
    private String password;
    private String nom;
    private String cognoms;
    private String compteCorrent;
    private String email;
    private double saldo;


    public String getCognoms() {
        return cognoms;
    }

    public double getSaldo() {
        return saldo;
    }

    public String getCompteCorrent() {
        return compteCorrent;
    }

    public String getEmail() {
        return email;
    }

    public String getNom() {
        return nom;
    }

    public String getNomUsuari() {
        return nomUsuari;
    }

    public String getPassword() {
        return password;
    }

    public void setCognoms(String cognoms) {
        this.cognoms = cognoms;
    }

    public void setCompteCorrent(String compteCorrent) {
        this.compteCorrent = compteCorrent;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void setNomUsuari(String nomUsuari) {
        this.nomUsuari = nomUsuari;
    }

    public static void setSaldoInicial(int saldoInicial) {
        SALDO_INICIAL = saldoInicial;
    }

    public Usuari() {
        this.saldo = SALDO_INICIAL;
    }

    public Usuari(String nomUsuari, String password, String nom, String cognoms, String compteCorrent, String email) {
        this.nomUsuari = nomUsuari;
        this.nom = nom;
        this.password = password;
        this.cognoms = cognoms;
        this.compteCorrent = compteCorrent;
        this.email = email;
        this.saldo = SALDO_INICIAL;
    }

    public Usuari(String nomUsuari, String password, String nom, String cognoms, String compteCorrent, String email, double saldo) {
        this.nomUsuari = nomUsuari;
        this.nom = nom;
        this.password = password;
        this.cognoms = cognoms;
        this.compteCorrent = compteCorrent;
        this.email = email;
        this.saldo = saldo;
    }




}
