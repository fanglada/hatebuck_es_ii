import java.util.List;

public class Usuari {
    private String nomUsuari;
    private String mail;
    private String contrasenya;
    private List<Missatge> missatgesEnviats;
    private List<Missatge> missatgesRebuts;

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

    public void enviarMissatgePrivat(Usuari destinatari, Missatge missatge, boolean notificar) {
        destinatari.missatgesRebuts.add(missatge);
        missatgesEnviats.add(missatge);

    }
}
