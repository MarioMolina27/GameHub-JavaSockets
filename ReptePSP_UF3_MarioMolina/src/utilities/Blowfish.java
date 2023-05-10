package utilities;

import org.mindrot.jbcrypt.BCrypt;

import java.io.FileNotFoundException;

public class Blowfish {
    private static final int workload = 10;
    /**
     * Funció que encripta una contrasenya en utilities.Blowfish
     * @param password_plaintext contrasenya introduida per l'usuari
     * @return contrasenya encriptada
     * */
    public static String Encrypt (String password_plaintext){
        String salt = BCrypt.gensalt(workload);
        return(BCrypt.hashpw(password_plaintext, salt));
    }
    /**
     * Funció que comparà la contrasenya encriptada i l'introduida per l'usuari
     * @param hashed_password contrasenya encriptada
     * @param password contrasenya introduida per l'usuari
     * @return boleà que indica si les contrasenyes coincideixen
     * */
    public static boolean checkPassword(String hashed_password, String password) {
        boolean password_verified = BCrypt.checkpw(password, hashed_password);
        return password_verified;
    }
}
