public class GamesBuyed {
    private String nomUsuari;
    private String nomJoc;
    private int tarifaPlana;
    private int partidesComprades;

    public GamesBuyed() {}


    public String getNomJoc() {
        return nomJoc;
    }

    public void setTarifaPlana(int tarifaPlana) {
        this.tarifaPlana = tarifaPlana;
    }

    public int getTarifaPlana() {
        return tarifaPlana;
    }

    public void setPartidesComprades(int partidesComprades) {
        this.partidesComprades = partidesComprades;
    }

    public int getPartidesComprades() {
        return partidesComprades;
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

    public GamesBuyed(String nomUsuari, String nomJoc,int partidesComprades,int tarifaPlana)
    {
        this.nomJoc = nomJoc;
        this.nomUsuari = nomUsuari;
        this.partidesComprades = partidesComprades;
        this.tarifaPlana = tarifaPlana;
    }

    public GamesBuyed(String nomUsuari, String nomJoc)
    {
        this.nomJoc = nomJoc;
        this.nomUsuari = nomUsuari;
    }
}
