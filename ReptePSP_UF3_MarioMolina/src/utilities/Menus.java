package utilities;

import utilities.Keyboard;

public class Menus {
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

    public static int mostrarMenuPrincipal() {

        System.out.println("ACCÉS A LA GESTIÓ D'ARXIUS:: ");
        System.out.println("  1- Registre");
        System.out.println("  2- Accedir");
        System.out.println("  0- Sortir");
        System.out.println("-------------");
        System.out.print("Opció: ");
        return Keyboard.readInt();
    }
}
