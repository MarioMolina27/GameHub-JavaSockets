import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FilesManager {

    private static final String FILE_PATH_USERS = "admin\\users.txt";
    public static final String FILE_PATH_2 = "admin\\users2.txt";
    public static final String FILE_PATH_GAMES = "admin\\games.txt";

    /**
     * Funció que guarda al final de l'arxiu txt l'usuari introduit per l'usuari
     * @param u objecte usuari a guardar
     * @throws IOException Errors d'entrada o sortida de dades
     * */

    public static void guardarUsuariTxt(Usuari u) throws IOException {
        String usuari = u.getNomUsuari()+":"+u.getNom()+":"+u.getCognoms()+":"+u.getEmail()+":"+u.getCompteCorrent()+":"+u.getPassword()+":"+u.getSaldo()+":"+u.getJocsComprats();
        FileWriter fw = new FileWriter(FILE_PATH_USERS, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(usuari + "\n");
        bw.close();
    }
    /**
     * Funció que comproba que a l'arxiu txt no existeix un usuari amb el mateix nickname
     * @return booleà que ens indica si l'usuari existeix
     * @param nomUsuari nom de l'usuari introduit per l'usuari
     * */
    public static boolean existenciaUsuariTxt(String nomUsuari)
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

    public static boolean existenciaEmailTxt(String email)
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
     * Funció que busca a l'arxiu txt un usuari concret i te'l retorna com un objecte Usuari
     * @param usuari nom de l'usuari que s'ha de retornar
     * @return objecte Usuari
     * */
    public static Usuari buscarUsuariTxt(String usuari)
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
     * Funció que llegeix tots els jocs del txt i els mostra per pantalla
     * @return Llista dels jocs de l'aplicació
     * @throws FileNotFoundException No es troba l'arxiu a la ruta seleccionada
     * */
    public static List<Joc> llegirJocs () throws FileNotFoundException {
        Scanner s = new Scanner(new File(FILE_PATH_GAMES));
        List<Joc> list = new ArrayList<>();
        while (s.hasNext()){
            String[] parts = s.next().split(":");
            list.add(new Joc(parts[0],parts[1]));
        }
        s.close();
        return list;
    }
}
