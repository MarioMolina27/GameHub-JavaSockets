public class Joc {
    private String nomJoc;
    private int preuJoc;

    public int getPreuJoc() {
        return preuJoc;
    }
    public String getNomJoc() {
        return nomJoc;
    }

    public Joc(String nomJoc,String preuJoc)
    {
        this.nomJoc=nomJoc;
        this.preuJoc= Integer.parseInt(preuJoc);
    }
}
