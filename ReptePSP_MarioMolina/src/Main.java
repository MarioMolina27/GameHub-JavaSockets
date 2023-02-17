import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws IOException {
        boolean continuar = true;
        do {
            int opcio = Menus.mostrarMenuPrincipal();
            switch (opcio) {
                case 1:
                    Usuari u = introduirDades();
                    if (u != null) {
                        FilesManager.guardarUsuariTxt(u);
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

        Usuari usuariActual = FilesManager.buscarUsuariTxt(usuari);
        if(usuariActual.getNomUsuari()!=null)
        {
            boolean contrasenyaCorrecte = Blowfish.checkPassword(usuariActual.getPassword(),password);
            if(contrasenyaCorrecte)
            {
                boolean continuar = true;
                espaiarElements(5);
                do
                {
                    int opcio = Menus.mostrarMenuAplicacio();
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
                            usuariActual = modificarDadesUsuari(usuariActual);
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
                    FilesManager.modificarTXT(usuariActual);
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
    /**
     * Funció que demana totes les dades a l'usuari, te les guarda en un objecte Usuari
     * @return L'usuari amb les dades omplertes
     * */
    public static Usuari introduirDades () {
        Usuari u = null;
        u.demanarDades();
        System.out.print("Repeteix la teva contrasenya: ");
        String passwordR = Keyboard.readString();
        if(Usuari.verifyString(u.getCompteCorrent(), Usuari.REGEX_IBAN))
        {
            if(Usuari.verifyString(u.getEmail(), Usuari.REGEX_EMAIL))
            {
                if(!(FilesManager.existenciaUsuariTxt(u.getNomUsuari())))
                {
                    if(!FilesManager.existenciaEmailTxt(u.getEmail()))
                    {
                        if (u.getPassword().equals(passwordR)) {
                            u.setPassword(Blowfish.Encrypt(u.getPassword()));
                        }
                        else{
                            u = null;
                            System.out.println("ERROR - Les contrasenyes no coincideixen");
                        }
                    }
                    else
                    {
                        u = null;
                        System.out.println("ERROR - Aquest correu ja existeix");
                    }
                }
                else
                {
                    u = null;
                    System.out.println("ERROR - Aquest usuari ja existeix");
                }
            }
            else
            {
                u = null;
                System.out.println("ERROR - El format del email es incorrecte");
            }
        }
        else
        {
            u = null;
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

    /**
     * Funció que permet a l'usuari modificar les dades del seu usuari
     * @return Usuari modificat
     * @param usuari usuari al qual li modificarem les dades
     * */
    public static Usuari modificarDadesUsuari(Usuari usuari)
    {
        int opcio = Menus.mostrarMenuModDades();
        switch (opcio) {
            case 1:
                usuari.canviarNom();
                break;
            case 2:
                usuari.canviarCognoms();
                break;
            case 3:
                usuari.canviarEmail();
                break;
            case 4:
                usuari.cambiarCompteCorrent();
                break;
            case 5:
                usuari.canviarContrasenya();
                break;
            default:
                System.out.println("ERROR - Opció de menú incorrecte");
                break;
        }
        return usuari;
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
            usuari.retirarSaldo();
        }
        else if (opcio == 'A'||opcio == 'a')
        {
            usuari.afegirSaldo();
        }
        else
        {
            System.out.println("ERROR - Opció incorrecte");
        }
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
        List<Joc> jocs = FilesManager.llegirJocs();
        List<Integer> jocsDisponibles = retornarJocsDisponibles (usuari,jocs);
        usuari.comprarJoc(jocsDisponibles,jocs);
        return usuari;
    }

    public static List<Integer> retornarJocsDisponibles (Usuari usuari,List<Joc> jocs)
    {
        List<Integer> jocsDisponibles = new ArrayList<>();
        for (int i =0;i<jocs.size();i++)
        {
            if(!comprobarJocComprat(usuari,i))
            {
                System.out.println(i+1+" - Nom del joc: "+jocs.get(i).getNomJoc()+" Preu: "+jocs.get(i).getPreuJoc());
                jocsDisponibles.add(i);
            }
        }
        return jocsDisponibles;
    }
    /**
     * Funció que permet a l'usuari jugar als jocs que ha comprat previament
     * @throws FileNotFoundException No es troba l'arxiu a la ruta seleccionada
     * @param usuari usuari actual que està jugant
     * */
    public static void jugar(Usuari usuari) throws FileNotFoundException {
        List<Integer> jocsDisponibles = new ArrayList<>();
        List<Joc> jocs = FilesManager.llegirJocs();
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