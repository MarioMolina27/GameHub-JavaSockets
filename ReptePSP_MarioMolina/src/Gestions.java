import java.io.FileNotFoundException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Gestions {
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
    public static Usuari gestionarJocs(Usuari u) throws FileNotFoundException {
        List<Joc> jocs = FilesManager.llegirJocs();
        List<Integer> jocsDisponibles = GestioJocs.retornarJocsDisponibles (u,jocs);
        if(!jocsDisponibles.isEmpty())
        {
            u = comprarJoc(jocsDisponibles,jocs,u);
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

    public static Usuari introduirDades(Usuari u) {
        u = demanarDades(u);
        System.out.print("Repeteix la teva contrasenya: ");
        String passwordR = Keyboard.readString();
        if (verifyString(u.getCompteCorrent(), Usuari.REGEX_IBAN)) {
            if (verifyString(u.getEmail(), Usuari.REGEX_EMAIL)) {
                if (!(FilesManager.existenciaUsuariTxt(u.getNomUsuari()))) {
                    if (!FilesManager.existenciaEmailTxt(u.getEmail())) {
                        if (u.getPassword().equals(passwordR)) {
                            u.setPassword(Blowfish.Encrypt(u.getPassword()));
                        } else {
                            u = null;
                            System.out.println("ERROR - Les contrasenyes no coincideixen");
                        }
                    } else {
                        u = null;
                        System.out.println("ERROR - Aquest correu ja existeix");
                    }
                } else {
                    u = null;
                    System.out.println("ERROR - Aquest usuari ja existeix");
                }
            } else {
                u = null;
                System.out.println("ERROR - El format del email es incorrecte");
            }
        } else {
            u = null;
            System.out.println("ERROR - El format del IBAN es incorrecte");
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
        u.setJocsComprats("000");
        return u;
    }

    public static Usuari retirarSaldo(Usuari u) {
        System.out.print("Quant saldo vols retirar: ");
        int saldoRetirar = Keyboard.readInt();
        if (u.getSaldo() - saldoRetirar >= 0) {
            u.setSaldo(u.getSaldo() - saldoRetirar);
        } else {
            System.out.println("ERROR - No es posible retirar aquest saldo perque el compte es quedaria en negatiu");
        }
        return u;
    }

    public static Usuari afegirSaldo(Usuari u) {
        System.out.print("Quant saldo vols afegir: ");
        int nouSaldo = Keyboard.readInt();
        if(nouSaldo>20)
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

    public static Usuari comprarJoc(List<Integer> jocsDisponibles, List<Joc> jocs,Usuari u) {
        System.out.print("Quin joc vols comprar: ");
        int opcio = Keyboard.readInt();
        if (jocsDisponibles.contains(opcio - 1)) {
            if (jocs.get(opcio - 1).getPreuJoc() <= u.getSaldo()) {
                u.modificarJocsComprats(opcio);
                u.setSaldo(u.getSaldo() - jocs.get(opcio - 1).getPreuJoc());
            } else {
                System.out.println("ERROR - No tens suficient saldo per comprar el joc");
            }
        } else {
            System.out.println("ERROR - Aquest joc no està disponible");
        }
        return u;
    }
}
