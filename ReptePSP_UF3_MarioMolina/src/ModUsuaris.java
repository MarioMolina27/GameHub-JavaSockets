import javax.print.DocFlavor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ModUsuaris {
    /**
     * Funció que permet a l'usuari modificar les dades del seu usuari
     */
    public static Usuari modificarDadesUsuari(Usuari u) {
        int opcio = Menus.mostrarMenuModDades();
        switch (opcio) {
            case 1:
                u = canviarNom(u);
                break;
            case 2:
                u = canviarCognoms(u);
                break;
            case 3:
                u =canviarEmail(u);
                break;
            case 4:
                u = cambiarCompteCorrent(u);
                break;
            case 5:
                u = canviarContrasenya(u);
                break;
            default:
                System.out.println("ERROR - Opció de menú incorrecte");
                break;
        }

        return u;
    }

    /**
     * Funció que permet a l'usuari comprar jocs
     * @return Usuari modificat
     * @throws FileNotFoundException No es troba l'arxiu a la ruta seleccionada
     * */
    public static Usuari gestionarJocs(Usuari u) throws IOException {
        List<Joc> jocs = FilesManager.llegirJocs();
        List<GamesBuyed> jocsComprats = FilesManager.llegirJocsComprats(u.getNomUsuari());
        List<Joc> jocsDisponibles = ModUsuaris.retornarJocsDisponibles (jocs,jocsComprats);
        if(jocsDisponibles.size()>0)
        {
           comprarJoc(jocsDisponibles,u);
        }
        else
        {
            System.out.println("No tens cap joc disponible per comprar");
        }

        return u;
    }

    /**
     * Funció que permet a l'usuari afegir o eliminar saldo del seu compte
     */
    public static Usuari modificarSaldo(Usuari u) {
        System.out.println("El teu saldo actual es de "+u.getSaldo());
        System.out.print("Vols retirar o afegir saldo? (R/A)");
        char opcio = Keyboard.readChar();
        if (opcio == 'R' || opcio == 'r') {
            u = retirarSaldo(u);
        } else if (opcio == 'A' || opcio == 'a') {
            u = afegirSaldo(u);
        } else {
            System.out.println("ERROR - Opció incorrecte");
        }
        System.out.println("El teu saldo actual es de "+u.getSaldo());
        return u;
    }

    public static Usuari canviarNom(Usuari u) {
        System.out.print("Introdueix el nou nom: ");
        u.setNom(Keyboard.readString());
        return u;
    }

    public static Usuari canviarCognoms(Usuari u) {
        System.out.print("Introdueix els nous cognoms: ");
        u.setCognoms(Keyboard.readString());
        return u;
    }

    public static Usuari canviarEmail(Usuari u) {
        System.out.print("Introdueix el nou email: ");
        String email = Keyboard.readString();
        if (verifyString(email, Usuari.REGEX_EMAIL)) {
            u.setEmail(email);
        } else {
            System.out.println("ERROR - El format del email no es correcte");
        }
        return u;
    }

    public static Usuari cambiarCompteCorrent(Usuari u) {
        System.out.print("Introdueix el compte Corrent: ");
        String compteCorrent = Keyboard.readString();
        if (verifyString(compteCorrent, Usuari.REGEX_IBAN)) {
            u.setCompteCorrent(compteCorrent);
        } else {
            System.out.println("ERROR - El format del compte corrent no es correcte");
        }
        return u;
    }

    public static Usuari canviarContrasenya(Usuari u) {
        System.out.print("Introdueix la nova contrasenya: ");
        String contrasenya = Keyboard.readString();
        System.out.print("Introdueix la contrasenya anterior: ");
        String antigaContrasenya = Keyboard.readString();
        if (Blowfish.checkPassword(u.getPassword(), antigaContrasenya)) {
            u.setPassword(Blowfish.Encrypt(contrasenya));
        } else {
            System.out.println("ERROR - La contrasenya no coincideix");
        }
        return u;
    }

    /**
     * Funció que verifica que un string segueix un patró concret
     * @param IBAN  codi IBAN introduit per l'usuari
     * @param regex patró a complir
     */
    public static Boolean verifyString(String IBAN, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(IBAN);
        return matcher.matches();
    }

    public static Usuari introduirDades(Usuari u,MySocket client) throws Exception {
        String passwordR = client.recieveString();
        String msg = "";
        u = client.rebreUsuari();
        if (verifyString(u.getCompteCorrent(), Usuari.REGEX_IBAN)) {
            if (verifyString(u.getEmail(), Usuari.REGEX_EMAIL)) {
                if (!(FilesManager.existenciaUsuariTxt(u.getNomUsuari()))) {
                    if (!FilesManager.existenciaEmailTxt(u.getEmail())) {
                        if (u.getPassword().equals(passwordR)) {
                            u.setPassword(Blowfish.Encrypt(u.getPassword()));
                            msg = "TOT CORRECTE";
                        } else {
                            u = null;
                            msg = "ERROR - Les contrasenyes no coincideixen";
                            System.out.println(msg);
                        }
                    } else {
                        u = null;
                        msg = "ERROR - Aquest correu ja existeix";
                        System.out.println(msg);
                    }
                } else {
                    u = null;
                    msg = "ERROR - Aquest usuari ja existeix";
                    System.out.println(msg);
                }
            } else {
                u = null;
                msg = "ERROR - El format del email es incorrecte";
                System.out.println(msg);
            }
        } else {
            u = null;
            msg = "ERROR - El format del IBAN es incorrecte";
            System.out.println(msg);
        }
        return  u;
    }
    public static Usuari demanarDades(Usuari u) {
        System.out.print("Introdueix el teu nom d'usuari: ");
        u.setNomUsuari(Keyboard.readString());
        System.out.print("Introdueix el teu nom: ");
        u.setNom(Keyboard.readString());
        System.out.print("Introdueix els teus cognoms: ");
        u.setCognoms(Keyboard.readString());
        System.out.print("Introdueix el teu compte corrent: ");
        u.setCompteCorrent(Keyboard.readString());
        System.out.print("Introdueix el teu email: ");
        u.setEmail(Keyboard.readString());
        System.out.print("Introdueix la teva contrasenya: ");
        u.setPassword(Keyboard.readString());
        return u;
    }

    public static Usuari retirarSaldo(Usuari u) {
        System.out.print("Quant saldo vols retirar: ");
        double saldoRetirar = Keyboard.readDouble();
        if (u.getSaldo() - saldoRetirar >= 0) {
            u.setSaldo(u.getSaldo() - saldoRetirar);
        } else {
            System.out.println("ERROR - No es posible retirar aquest saldo perque el compte es quedaria en negatiu");
        }
        return u;
    }

    public static Usuari afegirSaldo(Usuari u) {
        System.out.print("Quant saldo vols afegir: ");
        double nouSaldo = Keyboard.readDouble();
        if(nouSaldo<=20)
        {
            u.setSaldo(u.getSaldo()+nouSaldo);
            System.out.println("SALDO AFEGIT CORRECTAMENT");
        }
        else
        {
            System.out.println("No es pot fer un ingrés de més de 20");
        }

        return u;
    }

    public static void comprarJoc(List<Joc> jocsDisponibles,Usuari u) throws IOException {
        System.out.println("Salari actual: "+u.getSaldo());
        mostrarLlistaJocs(jocsDisponibles);
        System.out.print("Quin joc vols comptar? ");
        int joc = Keyboard.readInt()-1;
        if(joc >=0 && joc < jocsDisponibles.size())
        {
            System.out.println("Vols comprar la tarifa plana o una partida (P/TP)");
            String tipusCompra = Keyboard.readString().toLowerCase();
            GamesBuyed game = ModUsuaris.obtenirJocCompratConcret(u.getNomUsuari(),jocsDisponibles.get(joc).getNomJoc());
            if(game == null)
            {
                game = new GamesBuyed();
                game.setNomUsuari(u.getNomUsuari());
                game.setNomJoc(jocsDisponibles.get(joc).getNomJoc());
                game.setPartidesComprades(0);
                game.setTarifaPlana(0);
            }

            if(tipusCompra.equals("tp"))
            {
                int preu = jocsDisponibles.get(joc).getPreuJoc();
                if(preu<u.getSaldo())
                {
                    game.setTarifaPlana(1);
                    FilesManager.modificarTxtJocsComprats(game);
                    u.setSaldo(u.getSaldo() - preu);
                    System.out.println("Joc comprat satisfactoriament");
                }
                else
                {
                    System.out.println("No tens prou salari");
                }
            }
            else
            {
                int preu = jocsDisponibles.get(joc).getPreuPartida();
                if(preu<u.getSaldo())
                {
                    game.setPartidesComprades(game.getPartidesComprades()+1);
                    FilesManager.modificarTxtJocsComprats(game);
                    u.setSaldo(u.getSaldo() - preu);
                    System.out.println("Joc comprat satisfactoriament");
                }
                else
                {
                    System.out.println("No tens prou salari");
                }
            }

        }
        else
        {
            System.out.println("Aquest joc no es pot comprar");
        }
    }

    public static void mostrarLlistaJocs(List<Joc> jocs)
    {
        for (int i = 0;i<jocs.size();i++)
        {
            System.out.println(i+1+" - "+jocs.get(i).getNomJoc()+ " -> "+jocs.get(i).getPreuPartida()+ " TARIFA PLANA: "+jocs.get(i).getPreuJoc());
        }
    }

    /**
     * Funció que retorna els jocs disponibles d'un usuari
     * @return Llista de jocs disponibles
     * */
    public static List<Joc> retornarJocsDisponibles (List<Joc> jocs,List<GamesBuyed> jocsComprats)
    {
        //Filtrar la llista dels jocs comprats per l'usuari per una llista de strings els cuals tenen una tarifa plana comprada
        Set<String> gamesWithTarifaPlana = jocsComprats.stream()
                .filter(game -> game.getTarifaPlana() == 1)
                .map(GamesBuyed::getNomJoc)
                .collect(Collectors.toSet());

        // Filtrar la llista de jocs eliminant els jocs que tenen tarifa plana
        List<Joc> juegosDisponibles = jocs.stream()
                .filter(juego -> !gamesWithTarifaPlana.contains(juego.getNomJoc()))
                .collect(Collectors.toList());

        System.out.println("Juegos disponibles: " + juegosDisponibles);
        return juegosDisponibles;
    }

    public static GamesBuyed obtenirJocCompratConcret(String nomUsuari,String nomJoc) throws FileNotFoundException {
        List<GamesBuyed> jocsComprats = FilesManager.llegirTotsJocsComprats();
        int i = 0;
        GamesBuyed game = null;
        while (i < jocsComprats.size() && game == null) {
            GamesBuyed currentGame = jocsComprats.get(i);
            if (currentGame.getNomUsuari().equals(nomUsuari) && currentGame.getNomJoc().equals(nomJoc)) {
                game = currentGame;
            }
            i++;
        }
        return game;
    }
}
