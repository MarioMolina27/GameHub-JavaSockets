import java.util.ArrayList;
import java.util.List;

public class GestioJocs
{
    /**
     * Funció que retorna els jocs disponibles d'un usuari
     * @return Llista de jocs disponibles
     * */
    public static List<Joc> retornarJocsDisponibles (List<Joc> jocs,List<GamesBuyed> jocsComprats)
    {
        List<Joc> jocsDisponibles = new ArrayList<>();
        if(!jocsComprats.isEmpty())
        {
            for (int i =0;i<jocs.size();i++)
            {
                for (int j =0;j<jocsComprats.size();j++)
                {
                    if(!jocs.get(i).getNomJoc().equals(jocsComprats.get(j).getNomJoc()))
                    {
                        jocsDisponibles.add(jocs.get(i));
                    }
                }
            }
        }
        else
        {
            jocsDisponibles = jocs;
        }
        return jocsDisponibles;
    }

}
