import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws IOException {
        boolean continuar = true;
        Usuari u = new Usuari();
        do {
            int opcio = Menus.mostrarMenuPrincipal();
            switch (opcio) {
                case 1:
                    u = Gestions.introduirDades(u);
                    if (u != null) {
                        FilesManager.guardarUsuariTxt(u);
                        System.out.printf("USUARI AFEGIT SATISFACTORIAMENT");
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
        Usuari usuariActual = iniciSessio();
        if(usuariActual.getNomUsuari()!=null)
        {
                espaiarElements(5);
                System.out.println("Sessió iniciada satisfactoriament");
                boolean continuar = true;
                do
                {
                    int opcio = Menus.mostrarMenuAplicacio();
                    switch (opcio) {
                        case 1:
                            jugar(usuariActual);
                            espaiarElements(5);
                            break;
                        case 2:
                            usuariActual = Gestions.gestionarJocs(usuariActual);
                            espaiarElements(5);
                            break;
                        case 3:
                            usuariActual = Gestions.modificarSaldo(usuariActual);
                            espaiarElements(5);
                            break;
                        case 4:
                            usuariActual = Gestions.modificarDadesUsuari(usuariActual);
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
            System.out.println("ERROR - L'usuari no existeix");
        }
    }
    public static Usuari iniciSessio()
    {
        System.out.print("Introdueix el teu nom d'usuari: ");
        String usuari = Keyboard.readString();

        System.out.print("Introdueix la teva contrasenya: ");
        String password = Keyboard.readString();
        return FilesManager.buscarUsuariTxt(usuari,password);
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
            if(GestioJocs.comprobarJocComprat(usuari,i))
            {
                System.out.println(i+1+" - Vols jugar a "+jocs.get(i).getNomJoc());
                jocsDisponibles.add(i);
            }
        }
        if(!jocsDisponibles.isEmpty())
        {
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
}