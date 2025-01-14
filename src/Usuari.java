import java.util.*;

public class Usuari {
    private String nomUsuari;
    private String mail;
    private String contrasenya;
    private List<Missatge> missatgesEnviats;
    private List<Missatge> missatgesRebuts;
    private Map<Usuari, EstatUsuari> relacions;
    private List<Text> texts;

    public Usuari(String nomUsuari, String mail, String contrasenya, List<Text> texts) {
        this.nomUsuari = nomUsuari;
        this.mail = mail;
        this.contrasenya = contrasenya;
        this.missatgesEnviats = new ArrayList<>();
        this.missatgesRebuts = new ArrayList<>();
        this.relacions = new HashMap<>();
        this.texts = texts;
    }

    public boolean compararNomUsuari(String n) {
        return nomUsuari.compareTo(n) == 0;
    }

    public boolean compararContrasenya(String c) {
        return contrasenya.compareTo(c) == 0;
    }

    public void assignarRelacio(Usuari usuari, EstatUsuari estat) {

        if(relacions.containsKey(usuari))
            relacions.replace(usuari, estat);
        else
            relacions.put(usuari, estat);
    }

    public void eliminarRelacio(Usuari usuari) {
        relacions.remove(usuari);
    }

    public EstatUsuari obtenirRelacio(Usuari usuari) {
        return relacions.get(usuari);
    }

    public void enviarMissatgePrivat(Usuari destinatari, Missatge missatge, boolean notificar) {
        destinatari.missatgesRebuts.add(missatge);
        missatgesEnviats.add(missatge);
    }

    public Iterator<Text> obtenirTextos()
    {
        return texts.iterator();
    }

    public void canviarText(int posicio, Text text)
    {
        texts.set(posicio, text);
    }

    @Override
    public String toString() {
        return nomUsuari;
    }
}
