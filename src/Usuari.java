public class Usuari {
    private String nomUsuari;
    private String mail;
    private String contrasenya;

    public Usuari(String nomUsuari, String mail, String contrasenya) {
        this.nomUsuari = nomUsuari;
        this.mail = mail;
        this.contrasenya = contrasenya;
    }

    public String getNomUsuari() {
        return nomUsuari;
    }

    public String getContrasenya() {
        return contrasenya;
    }
}
