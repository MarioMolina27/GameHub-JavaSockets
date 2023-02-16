public class Usuari {
    private static int SALDO_INICIAL = 20;
    private String nomUsuari;
    private String password;
    private String nom;
    private String cognoms;
    private String compteCorrent;
    private String email;
    private int saldo;
    private String jocsComprats;

    public String getCognoms() {
        return cognoms;
    }

    public String getJocsComprats() {
        return jocsComprats;
    }

    public int getSaldo() {
        return saldo;
    }

    public String getCompteCorrent() {
        return compteCorrent;
    }

    public String getEmail() {
        return email;
    }

    public String getNom() {
        return nom;
    }

    public String getNomUsuari() {
        return nomUsuari;
    }

    public String getPassword() {
        return password;
    }

    public void setCognoms(String cognoms) {
        this.cognoms = cognoms;
    }

    public void setJocsComprats(String jocsComprats) {
        this.jocsComprats = jocsComprats;
    }

    public void setCompteCorrent(String compteCorrent) {
        this.compteCorrent = compteCorrent;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public Usuari(){}

    public Usuari(String nomUsuari,String password,String nom,String cognoms,String compteCorrent,String email)
    {
        this.nomUsuari=nomUsuari;
        this.nom=nom;
        this.password=password;
        this.cognoms=cognoms;
        this.compteCorrent=compteCorrent;
        this.email=email;
        this.saldo = SALDO_INICIAL;
        this.jocsComprats="000";
    }
    public Usuari(String nomUsuari,String password,String nom,String cognoms,String compteCorrent,String email,int saldo,String jocsComprats)
    {
        this.nomUsuari=nomUsuari;
        this.nom=nom;
        this.password=password;
        this.cognoms=cognoms;
        this.compteCorrent=compteCorrent;
        this.email=email;
        this.saldo = saldo;
        this.jocsComprats=jocsComprats;
    }

}
