package datamodels;

public class Joc {
    private String nomJoc;
    private int preuJoc;
    private int preuPartida;

    public Joc() {

    }

    public int getPreuJoc() {
        return preuJoc;
    }
    public String getNomJoc() {
        return nomJoc;
    }

    public int getPreuPartida() {
        return preuPartida;
    }

    public Joc(String nomJoc, String preuJoc, String preuPartida)
    {
        this.nomJoc=nomJoc;
        this.preuJoc= Integer.parseInt(preuJoc);
        this.preuPartida= Integer.parseInt(preuPartida);
    }
}
