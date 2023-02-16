import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static final String FILE_PATH_USERS = "admin\\users.txt";
    private static final String FILE_PATH_2 = "admin\\users2.txt";
    private static final String FILE_PATH_GAMES = "admin\\games.txt";
    private static final String REGEX_EMAIL = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
    private static final String REGEX_IBAN = "^(?:(?:IT|SM)\\d{2}[A-Z]\\d{22}|CY\\d{2}[A-Z]\\d{23}|NL\\d{2}[A-Z]{4}\\d{10}|LV\\d{2}[A-Z]{4}\\d{13}|(?:BG|BH|GB|IE)\\d{2}[A-Z]{4}\\d{14}|GI\\d{2}[A-Z]{4}\\d{15}|RO\\d{2}[A-Z]{4}\\d{16}|KW\\d{2}[A-Z]{4}\\d{22}|MT\\d{2}[A-Z]{4}\\d{23}|NO\\d{13}|(?:DK|FI|GL|FO)\\d{16}|MK\\d{17}|(?:AT|EE|KZ|LU|XK)\\d{18}|(?:BA|HR|LI|CH|CR)\\d{19}|(?:GE|DE|LT|ME|RS)\\d{20}|IL\\d{21}|(?:AD|CZ|ES|MD|SA)\\d{22}|PT\\d{23}|(?:BE|IS)\\d{24}|(?:FR|MR|MC)\\d{25}|(?:AL|DO|LB|PL)\\d{26}|(?:AZ|HU)\\d{27}|(?:GR|MU)\\d{28})$";

    public static void main(String[] args) throws IOException {
        boolean continuar = true;
        do {
            int opcio = mostrarMenuPrincipal();
            switch (opcio) {
                case 1:
                    Usuari u = introduirDades();
                    if (u != null) {
                        guardarUsuari(u);
                    }
                    espaiarElements(5);
                    break;
                case 2:
                    accedir();
                    espaiarElements(5);
                    break;
                case 0:
                    continuar=false;
                    System.out.println("Programa Finalitzat");
                    break;
                default:
                    System.out.println("ERROR - Opció de menú incorrecte");
                    break;
            }

        }while(continuar);
    }
    /**
     * Funció que demana les dades d'accés a l'usuari i en cas que siguin correctes et mostra el següent menú
     * @throws IOException Errors d'entrada o surtida de dades
     * */
    public static void accedir() throws IOException {
        System.out.print("Introdueix el teu nom d'usuari: ");
        String usuari = Keyboard.readString();

        System.out.print("Introdueix la teva contrasenya: ");
        String password = Keyboard.readString();

        Usuari usuariActual = retornarUsuari(usuari);
        if(usuariActual.getNomUsuari()!=null)
        {
            boolean contrasenyaCorrecte = Blowfish.checkPassword(usuariActual.getPassword(),password);
            if(contrasenyaCorrecte)
            {
                boolean continuar = true;
                espaiarElements(5);
                do
                {
                    int opcio = mostrarMenuAplicacio();
                    switch (opcio) {
                        case 1:
                            jugar(usuariActual);
                            espaiarElements(5);
                            break;
                        case 2:
                            usuariActual=gestionarJocs(usuariActual);
                            espaiarElements(5);
                            break;
                        case 3:
                            usuariActual = modificarSaldo(usuariActual);
                            espaiarElements(5);
                            break;
                        case 4:
                            usuariActual = modificarDades(usuariActual);
                            espaiarElements(5);
                            break;
                        case 0:
                            continuar=false;
                            System.out.println("Programa Finalitzat");
                            break;
                        default:
                            System.out.println("ERROR - Opció de menú incorrecte");
                            break;
                    }
                    modificarTXT(usuariActual);
                }while(continuar);
            }
            else
            {
                System.out.println("ERROR - Contrasenya incorrecte");
            }
        }
        else
        {
            System.out.println("ERROR - L'usuari no existeix");
        }
    }

    public static int mostrarMenuPrincipal() {

        System.out.println("ACCÉS A LA GESTIÓ D'ARXIUS:: ");
        System.out.println("  1- Registre");
        System.out.println("  2- Accedir");
        System.out.println("  0- Sortir");
        System.out.println("-------------");
        System.out.print("Opció: ");
        return Keyboard.readInt();
    }
    /**
     * Funció que demana totes les dades a l'usuari, te les guarda en un objecte Usuari
     * @return L'usuari amb les dades omplertes
     * */
    public static Usuari introduirDades () {
        String usuari, password, passwordR, nom, cognoms, compteCorrent, email;
        Usuari u = null;

        System.out.print("Introdueix el teu nom d'usuari: ");
        usuari = Keyboard.readString();
        System.out.print("Introdueix la teva contrasenya: ");
        password = Keyboard.readString();
        System.out.print("Repeteix la teva contrasenya: ");
        passwordR = Keyboard.readString();
        System.out.print("Introdueix el teu nom: ");
        nom = Keyboard.readString();
        System.out.print("Introdueix els teus cognoms: ");
        cognoms = Keyboard.readString();
        System.out.print("Introdueix el teu compte corrent: ");
        compteCorrent = Keyboard.readString();
        System.out.print("Introdueix el teu email: ");
        email = Keyboard.readString();

        if(verifyString(compteCorrent, REGEX_IBAN))
        {
            if(verifyString(email, REGEX_EMAIL))
            {
                if(!(comprobarExistenciaUsuari(usuari)))
                {
                    if(!comprobarExistenciaEmail(email))
                    {
                        if (password.equals(passwordR)) {
                            password = Blowfish.Encrypt(password);
                            u = new Usuari(usuari, password, nom, cognoms, compteCorrent, email);
                        }
                        else{
                            System.out.println("ERROR - Les contrasenyes no coincideixen");
                        }
                    }
                    else
                    {
                        System.out.println("ERROR - Aquest correu ja existeix");
                    }

                }
                else
                {
                    System.out.println("ERROR - Aquest usuari ja existeix");
                }
            }
            else
            {
                System.out.println("ERROR - El format del email es incorrecte");
            }
        }
        else
        {
            System.out.println("ERROR - El format del IBAN es incorrecte");
        }
        return u;
    }
    /**
     * Funció que fica un no determinat d'espais al terminal per tal de netegar-lo
     * @param num numero d'espais
     * */
    public static void espaiarElements(int num)
    {
        for (int i =0;i<num;i++)
        {
            System.out.println();
        }
    }

    public static int mostrarMenuAplicacio() {

        System.out.println("JOCS ONLINE: ");
        System.out.println("  1- Jugar");
        System.out.println("  2- Gestionar Jocs");
        System.out.println("  3- Gestionar Saldo");
        System.out.println("  4- Gestionar dades usuari");
        System.out.println("  0- Sortida al menú d'entrada");
        System.out.println("-------------");
        System.out.print("Opció: ");
        return Keyboard.readInt();
    }
    /**
     * Funció que comproba que a l'arxiu txt no existeix un usuari amb el mateix nickname
     * @return booleà que ens indica si l'usuari existeix
     * @param nomUsuari nom de l'usuari introduit per l'usuari
     * */
    public static boolean comprobarExistenciaUsuari(String nomUsuari)
    {
        boolean existeix=false;
        try
        {
            FileReader arxiu = new FileReader(FILE_PATH_USERS);
            BufferedReader br = new BufferedReader(arxiu);
            String s = br.readLine();

            while(s!=null&& !existeix)
            {
                int i =0;
                int finalIndex = -1;

                do{
                    String chr = s.substring(i,i+1);
                    if(chr.equals(":")){
                        finalIndex= i;
                    }
                    i++;
                }while(i<s.length()&&finalIndex==-1);
                String u3 = s.substring(0,finalIndex);
                if(u3.equals(nomUsuari))
                {
                    existeix=true;
                }
                s = br.readLine();
            }
            arxiu.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println(e.toString());
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        return existeix;
    }
    /**
     * Funció que comproba que a l'arxiu txt no existeix un usuari amb el correu
     * @return booleà que ens indica si existeix un altre usuari amb el mateix correu
     * @param email email introduit per l'usuari
     * */
    public static boolean comprobarExistenciaEmail(String email)
    {
        boolean existeix=false;
        try
        {
            FileReader arxiu = new FileReader(FILE_PATH_USERS);
            BufferedReader br = new BufferedReader(arxiu);
            String s = br.readLine();

            while(s!=null&& !existeix)
            {
                int startIndex = 0;
                int finalIndex =0;
                int i =0;
                int numSeparador=0; // Variable que ens indica en quin separador de camps de l'usuari ens trobem
                do{
                    String chr = s.substring(i,i+1);
                    if(chr.equals(":")){
                        numSeparador++;
                        if(numSeparador==3){
                            startIndex=i+1;
                        }
                        else if(numSeparador==4)
                        {
                            finalIndex=i;
                        }
                    }
                    i++;
                }while(startIndex==0||finalIndex==0);
                String u3 = s.substring(startIndex,finalIndex);
                if(u3.equals(email)){
                    existeix=true;
                }
                s = br.readLine();
            }
            arxiu.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println(e.toString());
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        return existeix;
    }
    /**
     * Funció que guarda al final de l'arxiu txt l'usuari introduit per l'usuari
     * @param u objecte usuari a guardar
     * @throws IOException Errors d'entrada o sortida de dades
     * */
    public static void guardarUsuari(Usuari u) throws IOException {
        String usuari = u.getNomUsuari()+":"+u.getNom()+":"+u.getCognoms()+":"+u.getEmail()+":"+u.getCompteCorrent()+":"+u.getPassword()+":"+u.getSaldo()+":"+u.getJocsComprats();
        FileWriter fw = new FileWriter(FILE_PATH_USERS, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(usuari + "\n");
        bw.close();
    }
    /**
     * Funció que busca a l'arxiu txt un usuari concret i te'l retorna com un objecte Usuari
     * @param usuari nom de l'usuari que s'ha de retornar
     * @return objecte Usuari
     * */
    public static Usuari retornarUsuari(String usuari)
    {
        Usuari u = new Usuari();
        String usuari1 = null;
        try
        {
            FileReader arxiu = new FileReader(FILE_PATH_USERS);
            BufferedReader br = new BufferedReader(arxiu);
            String  s = br.readLine();
            boolean existeix=false;
            int finalIndex;
            int i;
            while(s!=null&&!existeix)
            {
                finalIndex=-1;
                i=0;
                    do{
                        String chr = s.substring(i,i+1);
                        if(chr.equals(":")){
                            finalIndex= i;
                        }
                        i++;
                    }while(i<s.length()&&finalIndex==-1);
                    String nomUsuariComprobar = s.substring(0,finalIndex);
                    if(nomUsuariComprobar.equals(usuari))
                    {
                        usuari1=s;
                        existeix=true;
                    }
                    s = br.readLine();
            }
            arxiu.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println(e.toString());
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        if(usuari1!=null)
        {
            u = retornarObjecteUsuari(usuari1);
        }
        return u;
    }
    /**
     * Funció que agafa tots els camps de l'string complet de l'usuari i crea un objecte amb aquestes dades
     * @return Usuari
     * @param stringUsuari string amb totes les dades de l'usuari
     * */
    public static Usuari retornarObjecteUsuari(String stringUsuari)
    {
        String[] parts = stringUsuari.split(":");
        return new Usuari(parts[0],parts[5],parts[1],parts[2],parts[4],parts[3],Integer.parseInt(parts[6]),parts[7]);
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
    /**
     * Funció que l'enviem un usuari i rescriu l'arxiu txt en la linea on l'usuari coincideixi
     * @param u usuari a reescriure en el txt
     * @throws IOException Errors d'entrada o sortida de dades
     * */
    public static void modificarTXT(Usuari u) throws IOException {
        String stringUsuari = u.getNomUsuari()+":"+u.getNom()+":"+u.getCognoms()+":"+u.getEmail()+":"+u.getCompteCorrent()+":"+u.getPassword()+":"+u.getSaldo()+":"+u.getJocsComprats();
        int i, finalIndex;
        File oldUsuaris = new File(FILE_PATH_USERS);
        FileReader arxiu = new FileReader(FILE_PATH_USERS);
        BufferedReader br = new BufferedReader(arxiu);

        FileWriter arxiu2 = new FileWriter(FILE_PATH_2);
        BufferedWriter bw = new BufferedWriter(arxiu2);
        String s = br.readLine();
        while(s!=null)
        {
            finalIndex=-1;
            i=0;
            do{
                String chr = s.substring(i,i+1);
                if(chr.equals(":")){
                    finalIndex= i;
                }
                i++;
            }while(i<s.length()&&finalIndex==-1);
            String nomUsuariComprobar = s.substring(0,finalIndex);
            if(!nomUsuariComprobar.equals(u.getNomUsuari()))
            {
                bw.write(s+"\n");
            }
            else
            {
                bw.write(stringUsuari+"\n");
            }
            s = br.readLine();
        }
        bw.close();
        br.close();
        oldUsuaris.delete();
        reenombrarArxius();
    }
    /**
     * Funció que reescriu el nom de l'arxiu secundari que creem a l'hora de modificar un usuari amb el nom de l'arxiu original.
     * */
    public static void reenombrarArxius()
    {
        File oldfile = new File(FILE_PATH_2);
        File newfile = new File(FILE_PATH_USERS);
        oldfile.renameTo(newfile);
    }
    /**
     * Funció que permet a l'usuari modificar les dades del seu usuari
     * @return Usuari modificat
     * @param usuari usuari al qual li modificarem les dades
     * */
    public static Usuari modificarDades(Usuari usuari)
    {
        int opcio = mostrarMenuModDades();
        switch (opcio) {
            case 1:
                System.out.print("Introdueix el nou nom: ");
                usuari.setNom(Keyboard.readString());
                break;
            case 2:
                System.out.print("Introdueix els nous cognoms: ");
                usuari.setCognoms(Keyboard.readString());
                break;
            case 3:
                System.out.print("Introdueix el nou email: ");
                String email = Keyboard.readString();
                if(verifyString(email, REGEX_EMAIL))
                {
                    usuari.setEmail(email);
                }
                else
                {
                    System.out.println("ERROR - El format del email no es correcte");
                }
                break;
            case 4:
                System.out.print("Introdueix el compte Corrent: ");
                String compteCorrent = Keyboard.readString();
                if(verifyString(compteCorrent, REGEX_IBAN))
                {
                    usuari.setCompteCorrent(compteCorrent);
                }
                else
                {
                    System.out.println("ERROR - El format del compte corrent no es correcte");
                }
                break;
            case 5:
                System.out.print("Introdueix la nova contrasenya: ");
                String contrasenya = Keyboard.readString();
                System.out.print("Introdueix la contrasenya anterior: ");
                String antigaContrasenya = Keyboard.readString();
                if(Blowfish.checkPassword(usuari.getPassword(),antigaContrasenya))
                {
                    usuari.setPassword(Blowfish.Encrypt(contrasenya));
                }
                else
                {
                    System.out.println("ERROR - La contrasenya no coincideix");
                }
                break;
            default:
                System.out.println("ERROR - Opció de menú incorrecte");
                break;
        }
        return usuari;
    }

    public static int mostrarMenuModDades (){
        System.out.println("Quina dada vols modificar: ");
        System.out.println("  1- Nom");
        System.out.println("  2- Cognoms");
        System.out.println("  3- Email");
        System.out.println("  4- Compte Corrent");
        System.out.println("  5- Contrasenya");
        System.out.println("-------------");
        System.out.print("Opció: ");
        return Keyboard.readInt();
    }
    /**
     * Funció que permet a l'usuari afegir o eliminar saldo del seu compte
     * @return Usuari modificat
     * @param usuari usuari al qual li modificarem el saldo
     * */
    public static Usuari modificarSaldo (Usuari usuari)
    {
        System.out.print("Vols retirar o afegir saldo? (R/A)");
        char opcio = Keyboard.readChar();
        if(opcio == 'R'||opcio == 'r')
        {
            System.out.print("Quant saldo vols retirar: ");
            int saldoRetirar = Keyboard.readInt();
            if(usuari.getSaldo()-saldoRetirar>=0)
            {
                usuari.setSaldo(usuari.getSaldo()-saldoRetirar);
            }
            else
            {
                System.out.println("ERROR - No es posible retirar aquest saldo perque el compte es quedaria en negatiu");
            }
        }
        else if (opcio == 'A'||opcio == 'a')
        {
            System.out.print("Quant saldo vols afegir: ");
            usuari.setSaldo(usuari.getSaldo()+Keyboard.readInt());
            System.out.println("SALDO AFEGIT CORRECTAMENT");
        }
        else
        {
            System.out.println("ERROR - Opció incorrecte");
        }
        return usuari;
    }
    /**
     * Funció que modifica l'string dels jocs comprats per l'usuari, marcant que ha comprat el joc selecionat
     * Si el jugador té el joc concret el caràcter relacionat amb aquest es trobarà en 1 en cas que no el tingui serà 0.
     * @return Usuari modificat
     * @param usuari usuari a modificar
     * @param posicio posicio del string del camp de jocs comprar a modificar per indicar que l'ha comprat
     * */
    public static Usuari modificarJocsComprats (Usuari usuari,int posicio)
    {
        String newIdJocs = usuari.getJocsComprats().substring(0,posicio-1)+'1'+usuari.getJocsComprats().substring(posicio);
        usuari.setJocsComprats(newIdJocs);
        return usuari;
    }
    /**
     * Funció que busca en l'string dels jocs comprats si l'usuari té comprat el joc demanat
     *  @return boleà que indica si el jugador ha comprat el joc
     *  @param usuari usuari a comprovar
     *  @param posicio posicio del string del camp de jocs comprar per comprovar si l'ha comprat
     * */
    public static Boolean comprobarJocComprat(Usuari usuari,int posicio)
    {
        boolean comprat;
        char joc = usuari.getJocsComprats().charAt(posicio);
        if(joc=='0')
        {
            comprat=false;
        }
        else
        {
            comprat=true;
        }
        return comprat;
    }
    /**
     * Funció que permet a l'usuari comprar jocs
     * @return Usuari modificat
     * @throws FileNotFoundException No es troba l'arxiu a la ruta seleccionada
     * @param usuari usuari a modificar
     * */
    public static Usuari gestionarJocs(Usuari usuari) throws FileNotFoundException {
        List<Joc> jocs = llegirJocs(FILE_PATH_GAMES);
        List<Integer> jocsDisponibles = new ArrayList<>();
        for (int i =0;i<jocs.size();i++)
        {
            if(!comprobarJocComprat(usuari,i))
            {
                System.out.println(i+1+" - Nom del joc: "+jocs.get(i).getNomJoc()+" Preu: "+jocs.get(i).getPreuJoc());
                jocsDisponibles.add(i);
            }
        }
        System.out.print("Quin joc vols comprar: ");
        int opcio = Keyboard.readInt();
        if(jocsDisponibles.contains(opcio-1))
        {
            if(jocs.get(opcio-1).getPreuJoc()<=usuari.getSaldo())
            {
                usuari = modificarJocsComprats(usuari,opcio);
                usuari.setSaldo(usuari.getSaldo()-jocs.get(opcio-1).getPreuJoc());
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
        return usuari;
    }
    /**
     * Funció que llegeix tots els jocs del txt i els mostra per pantalla
     * @return Llista dels jocs de l'aplicació
     * @throws FileNotFoundException No es troba l'arxiu a la ruta seleccionada
     * @param fichero ruta de l'arxiu a llegir
     * */
    public static List<Joc> llegirJocs (String fichero) throws FileNotFoundException {
        Scanner s = new Scanner(new File(fichero));
        List<Joc> list = new ArrayList<>();
        while (s.hasNext()){
            String[] parts = s.next().split(":");
            list.add(new Joc(parts[0],parts[1]));
        }
        s.close();
        return list;
    }
    /**
     * Funció que permet a l'usuari jugar als jocs que ha comprat previament
     * @throws FileNotFoundException No es troba l'arxiu a la ruta seleccionada
     * @param usuari usuari actual que està jugant
     * */
    public static void jugar(Usuari usuari) throws FileNotFoundException {
        List<Integer> jocsDisponibles = new ArrayList<>();
        List<Joc> jocs = llegirJocs(FILE_PATH_GAMES);
        for(int i =0;i<usuari.getJocsComprats().length();i++)
        {
            if(comprobarJocComprat(usuari,i))
            {
                System.out.println(i+1+" - Vols jugar a "+jocs.get(i).getNomJoc());
                jocsDisponibles.add(i);
            }
        }
        System.out.print("Opció: ");
        int opcio = Keyboard.readInt();

        if (jocsDisponibles.contains(opcio-1))
        {
            System.out.println("ESTAS JUGANT A "+jocs.get(opcio-1).getNomJoc());
        }
        else
        {
            System.out.println("No tens disponible aquest joc");
        }
    }
}