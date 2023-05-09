import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;


public class Main {
    private MySocket client;
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(MySocket.PORT);
        MySocket client = new MySocket(new Socket());
        try
        {
            System.out.println("Esperant la nova conexió del client");
            client.accept(server);
            System.out.println("Client conectat");
            boolean continuar = true;
            Usuari u = new Usuari();
            do {
                int opcio = client.recieveInt();
                switch (opcio) {
                    case 1:
                        u = ModUsuaris.introduirDades(u,client);
                        if (u != null) {
                            FilesManager.guardarUsuariTxt(u);
                            System.out.printf("USUARI AFEGIT SATISFACTORIAMENT");
                        }
                        espaiarElements(5);
                        break;
                    case 2:
                        String usuari = client.recieveString();
                        String password = client.recieveString();
                        accedir(client,usuari,password);
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
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    /**
     * Funció que demana les dades d'accés a l'usuari i en cas que siguin correctes et mostra el següent menú
     * @throws IOException Errors d'entrada o surtida de dades
     * */
    public static void accedir(MySocket client,String usuari,String password) throws Exception {

        Usuari usuariActual = iniciSessio(usuari,password);
        client.enviarUsuari(usuariActual);
        if(usuariActual.getNomUsuari()!=null)
        {
                espaiarElements(5);
                System.out.println("Sessió iniciada satisfactoriament");
                boolean continuar = true;
                do
                {
                    System.out.println("Usuari escollint la nova opció");
                    int opcio = client.recieveInt();
                    switch (opcio) {
                        case 1:
                            jugar(usuariActual);
                            break;
                        case 2:
                            usuariActual = ModUsuaris.gestionarJocs(usuariActual);
                            break;
                        case 3, 4:
                            usuariActual = client.rebreUsuari();
                            break;
                        case 0:
                            continuar=false;
                            System.out.println("Programa Finalitzat");
                            break;
                        default:
                            System.out.println("ERROR - Opció de menú incorrecte");
                            break;
                    }
                    FilesManager.modificarTxtUsuari(usuariActual);
                    System.out.println("Usuari modificat correctament");
                }while(continuar);

        }
        else
        {
            System.out.println("ERROR - L'usuari no existeix");
        }
    }
    public static Usuari iniciSessio(String usuari, String password)
    {
        return FilesManager.buscarUsuariTxt(usuari,password);
    }

    /**
     * Funció que permet a l'usuari jugar als jocs que ha comprat previament
     * @throws FileNotFoundException No es troba l'arxiu a la ruta seleccionada
     * @param usuari usuari actual que està jugant
     * */
    public static void jugar(Usuari usuari) throws IOException {
        List<GamesBuyed> jocsDisponibles = FilesManager.llegirJocsComprats(usuari.getNomUsuari());

        if(!jocsDisponibles.isEmpty())
        {
            mostrarLlistaJocs(jocsDisponibles);
            System.out.print("Opció: ");
            int opcio = Keyboard.readInt()-1;
            if(opcio >=0 && opcio < jocsDisponibles.size())
            {
                if(jocsDisponibles.get(opcio).getPartidesComprades()>0)
                {
                    System.out.println("Estas jugant a "+ jocsDisponibles.get(opcio).getNomJoc());
                    GamesBuyed game = jocsDisponibles.get(opcio);
                    game.setPartidesComprades(game.getPartidesComprades()-1);
                    FilesManager.modificarTxtJocsComprats(jocsDisponibles.get(opcio));
                }
            }
            else
            {
                System.out.println("No tens aquest joc disponible");
            }
        }
        else
        {
            System.out.println("No tens jocs per jugar, primer compra'n algún");
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
    public static void mostrarLlistaJocs(List<GamesBuyed> jocs)
    {
        for (int i = 0;i<jocs.size();i++)
        {
            if(jocs.get(i).getTarifaPlana() == 0)
            {
                if(jocs.get(i).getPartidesComprades()>0)
                {
                    System.out.println(i+1+" - "+jocs.get(i).getNomJoc() + "  Partides disponibles: "+ jocs.get(i).getPartidesComprades());
                }
            }
            else
            {
                System.out.println(i+1+" - "+jocs.get(i).getNomJoc() + "  Tens la Tarifa Plana");
            }

        }
    }
}