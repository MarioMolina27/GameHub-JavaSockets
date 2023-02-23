import java.util.ArrayList;
import java.util.List;

public class GestioJocs
{
    /**
     *  Funció que busca en l'string dels jocs comprats si l'usuari té comprat el joc demanat
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
     * Funció que retorna els jocs disponibles d'un usuari
     * @return Llista de jocs disponibles
     * @param usuari usuari a verificar
     * */
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
}
