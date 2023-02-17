import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Usuari {
    public static final String REGEX_EMAIL = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
    public static final String REGEX_IBAN = "^(?:(?:IT|SM)\\d{2}[A-Z]\\d{22}|CY\\d{2}[A-Z]\\d{23}|NL\\d{2}[A-Z]{4}\\d{10}|LV\\d{2}[A-Z]{4}\\d{13}|(?:BG|BH|GB|IE)\\d{2}[A-Z]{4}\\d{14}|GI\\d{2}[A-Z]{4}\\d{15}|RO\\d{2}[A-Z]{4}\\d{16}|KW\\d{2}[A-Z]{4}\\d{22}|MT\\d{2}[A-Z]{4}\\d{23}|NO\\d{13}|(?:DK|FI|GL|FO)\\d{16}|MK\\d{17}|(?:AT|EE|KZ|LU|XK)\\d{18}|(?:BA|HR|LI|CH|CR)\\d{19}|(?:GE|DE|LT|ME|RS)\\d{20}|IL\\d{21}|(?:AD|CZ|ES|MD|SA)\\d{22}|PT\\d{23}|(?:BE|IS)\\d{24}|(?:FR|MR|MC)\\d{25}|(?:AL|DO|LB|PL)\\d{26}|(?:AZ|HU)\\d{27}|(?:GR|MU)\\d{28})$";

    private static int SALDO_INICIAL = 20;
    private String nomUsuari;
    private String password;
    private String nom;
    private String cognoms;
    private String compteCorrent;
    private String email;
    private int saldo;
    private String jocsComprats;

    public String getCognoms() {
        return cognoms;
    }

    public String getJocsComprats() {
        return jocsComprats;
    }

    public int getSaldo() {
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

    public void setJocsComprats(String jocsComprats) {
        this.jocsComprats = jocsComprats;
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

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public void setNomUsuari(String nomUsuari) {
        this.nomUsuari = nomUsuari;
    }

    public Usuari(){}

    public Usuari(String nomUsuari,String password,String nom,String cognoms,String compteCorrent,String email)
    {
        this.nomUsuari=nomUsuari;
        this.nom=nom;
        this.password=password;
        this.cognoms=cognoms;
        this.compteCorrent=compteCorrent;
        this.email=email;
        this.saldo = SALDO_INICIAL;
        this.jocsComprats="000";
    }
    public Usuari(String nomUsuari,String password,String nom,String cognoms,String compteCorrent,String email,int saldo,String jocsComprats)
    {
        this.nomUsuari=nomUsuari;
        this.nom=nom;
        this.password=password;
        this.cognoms=cognoms;
        this.compteCorrent=compteCorrent;
        this.email=email;
        this.saldo = saldo;
        this.jocsComprats=jocsComprats;
    }

    public void canviarNom()
    {
        System.out.print("Introdueix el nou nom: ");
        this.setNom(Keyboard.readString());
    }

    public void canviarCognoms ()
    {
        System.out.print("Introdueix els nous cognoms: ");
        this.setCognoms(Keyboard.readString());
    }

    public void canviarEmail()
    {
        System.out.print("Introdueix el nou email: ");
        String email = Keyboard.readString();
        if(verifyString(email, REGEX_EMAIL))
        {
            this.setEmail(email);
        }
        else
        {
            System.out.println("ERROR - El format del email no es correcte");
        }
    }
    public void  cambiarCompteCorrent()
    {
        System.out.print("Introdueix el compte Corrent: ");
        String compteCorrent = Keyboard.readString();
        if(verifyString(compteCorrent, REGEX_IBAN))
        {
            this.setCompteCorrent(compteCorrent);
        }
        else
        {
            System.out.println("ERROR - El format del compte corrent no es correcte");
        }
    }

    public void canviarContrasenya()
    {
        System.out.print("Introdueix la nova contrasenya: ");
        String contrasenya = Keyboard.readString();
        System.out.print("Introdueix la contrasenya anterior: ");
        String antigaContrasenya = Keyboard.readString();
        if(Blowfish.checkPassword(this.getPassword(),antigaContrasenya))
        {
            this.setPassword(Blowfish.Encrypt(contrasenya));
        }
        else
        {
            System.out.println("ERROR - La contrasenya no coincideix");
        }

    }

    /**
     * Funció que verifica que un string segueix un patró concret
     * @return booleà que indica si la condició es compleix
     * @param IBAN codi IBAN introduit per l'usuari
     * @param regex patró a complir
     * */
    public static Boolean verifyString (String IBAN,String regex)
    {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(IBAN);

        return matcher.matches();
    }

    public  void retirarSaldo ()
    {
        System.out.print("Quant saldo vols retirar: ");
        int saldoRetirar = Keyboard.readInt();
        if(this.getSaldo()-saldoRetirar>=0)
        {
            this.setSaldo(this.getSaldo()-saldoRetirar);
        }
        else
        {
            System.out.println("ERROR - No es posible retirar aquest saldo perque el compte es quedaria en negatiu");
        }
    }
    public void afegirSaldo()
    {
        System.out.print("Quant saldo vols afegir: ");
        this.setSaldo(this.getSaldo()+Keyboard.readInt());
        System.out.println("SALDO AFEGIT CORRECTAMENT");
    }

    public void comprarJoc(List<Integer> jocsDisponibles, List<Joc> jocs)
    {
        System.out.print("Quin joc vols comprar: ");
        int opcio = Keyboard.readInt();
        if(jocsDisponibles.contains(opcio-1))
        {
            if(jocs.get(opcio-1).getPreuJoc()<=this.getSaldo())
            {
                this.modificarJocsComprats(opcio);
                this.setSaldo(this.getSaldo()-jocs.get(opcio-1).getPreuJoc());
            }
            else
            {
                System.out.println("ERROR - No tens suficient saldo per comprar el joc");
            }
        }
        else
        {
            System.out.println("ERROR - Aquest joc no està disponible");
        }
    }
    /**
     * Funció que modifica l'string dels jocs comprats per l'usuari, marcant que ha comprat el joc selecionat
     * Si el jugador té el joc concret el caràcter relacionat amb aquest es trobarà en 1 en cas que no el tingui serà 0.
     * @return Usuari modificat
     * @param posicio posicio del string del camp de jocs comprar a modificar per indicar que l'ha comprat
     * */
    public void  modificarJocsComprats (int posicio)
    {
        String newIdJocs = this.getJocsComprats().substring(0,posicio-1)+'1'+this.getJocsComprats().substring(posicio);
        this.setJocsComprats(newIdJocs);
    }

    public void demanarDades()
    {
        System.out.print("Introdueix el teu nom d'usuari: ");
        this.setNomUsuari(Keyboard.readString());
        System.out.print("Introdueix la teva contrasenya: ");
        this.setPassword(Keyboard.readString());
        System.out.print("Introdueix el teu nom: ");
        this.setNom(Keyboard.readString());
        System.out.print("Introdueix els teus cognoms: ");
        this.setCognoms(Keyboard.readString());
        System.out.print("Introdueix el teu compte corrent: ");
        this.setCompteCorrent(Keyboard.readString());
        System.out.print("Introdueix el teu email: ");
        this.setEmail(Keyboard.readString());
    }
}
