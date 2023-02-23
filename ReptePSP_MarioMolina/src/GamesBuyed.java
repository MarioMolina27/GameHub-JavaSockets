public class GamesBuyed {
    private String nomUsuari;
    private String nomJoc;

    public String getNomJoc() {
        return nomJoc;
    }

    public void setNomUsuari(String nomUsuari) {
        this.nomUsuari = nomUsuari;
    }

    public String getNomUsuari() {
        return nomUsuari;
    }

    public void setNomJoc(String nomJoc) {
        this.nomJoc = nomJoc;
    }

    public GamesBuyed(String nomUsuari, String nomJoc)
    {
        this.nomJoc = nomJoc;
        this.nomUsuari = nomUsuari;
    }
}
