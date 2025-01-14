import java.sql.*;
import java.util.*;

public class Main {
    static Scanner lector = new Scanner(System.in);
    static List<Usuari> usuaris = new ArrayList<>();
    static List<Moderador> moderadors = new ArrayList<>();

    public static void main(String[] args) {

        if(inicialitzar() == -1) {
            System.out.println("Error inicialitzant la base de dades");
            return;
        }

        Usuari usuari = new Usuari("","", "", null);

        boolean seguirIntentant = true;

        usuari = login();
        while (usuari==null && seguirIntentant) {
            System.out.println("Vols tornar a intentar-ho? (S/N)");
            String resposta = lector.nextLine();
            if(resposta.equals("N")) seguirIntentant = false;
            else usuari = login();
        }

        if(seguirIntentant) {
            int opcio = menu();
            while (opcio != 0)
            {
                switch (opcio) {
                case 1:
                    enviarMissatgePrivat(usuari);
                    break;
                case 2:
                    Moderador moderador = loginModerador();
                    if(moderador != null)
                    {
                        canviarTextUsuari(moderador);
                    }
                    break;
                case 3:
                    canviarRelacioUsuari(usuari);
                    break;
                default:
                    System.out.println("Opció no vàlida");
                    break;
                }
                opcio = menu();
            }
        }

            System.out.println("Adéu!");
    }

    public static Connection connect(String path) {
        try {
            return DriverManager.getConnection( "jdbc:sqlite:" + path);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    //retorna -1 si hi ha un error
    //retorna 0 si tot ha anat bé
    private static int inicialitzar()
    {
        Connection conn = connect("./db/hatebuck.db");
        ResultSet rs;
        if (conn == null) {
            return -1;
        }

        try {
            String sql = "SELECT * FROM Usuari";
            rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                List<Text> texts = new ArrayList<>();

                String sql2 = "SELECT * FROM Text WHERE idUsuari = '" + rs.getString("nomUsuari") + "'";
                ResultSet rs2 = conn.createStatement().executeQuery(sql2);
                while (rs2.next()) {
                    String sql3 = "SELECT * FROM ElementText et INNER JOIN Element e ON e.idElement=et.idElement WHERE idText = " + rs2.getInt("idText");
                    ResultSet rs3 = conn.createStatement().executeQuery(sql3);
                    ArrayList<Element> elements = new ArrayList<>();
                    while (rs3.next()) {
                        if(rs3.getInt("idTipusElement") == 1)
                            elements.add(new Paraula(rs3.getString("contingut")));
                        else
                            elements.add(new SignesPuntuacio(rs3.getString("contingut")));
                    }
                    texts.add(new Text(rs2.getInt("idText"), elements));
                }

                usuaris.add(new Usuari(rs.getString("nomUsuari"), rs.getString("email"), rs.getString("password"), texts));
            }

            for (Usuari u : usuaris) {
                sql = "SELECT * FROM RelacioUsuari WHERE idUsuari1 = '" + u.getNomUsuari() + "'";
                rs = conn.createStatement().executeQuery(sql);
                while (rs.next()) {
                    Usuari u2 = buscarUsuari(rs.getString("idUsuari2"));
                    EstatUsuari estat = EstatUsuari.getEstatUsuari(rs.getInt("idEstatAmic"));
                    u.assignarRelacio(u2, estat);
                }
            }

            sql = "SELECT * FROM Moderador";
            rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                moderadors.add(new Moderador(rs.getString("nomUsuari"), rs.getString("email"), rs.getString("password")));
            }

            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return 0;
    }

    private static int menu() {
        System.out.println("1. Enviar missatge privat");
        System.out.println("2. Modificar text penjat per un usuari");
        System.out.println("3. Canviar relació amb un usuari");
        System.out.println("0. Sortir");
        System.out.println("Escull una opció: ");

        int opcio = lector.nextInt();
        lector.nextLine();

        return opcio;
    }

    //retorna -1 si l'usuari no existeix
    //retorna -2 si la contrasenya és incorrecta
    //retorna 1 si tot ha anat bé
    private static Usuari login() {
        System.out.println("Entra el nom d'usuari: ");
        String username = lector.nextLine();

        Usuari u = buscarUsuari(username);
        if (u == null) {
            System.out.println("Usuari no trobat");
            return null;
        }
        else
        {
            System.out.println("Entra la contrasenya: ");
            String password = lector.nextLine();
            if (u.getContrasenya().equals(password)) {
                return u;
            }
            else
            {
                System.out.println("Contrasenya incorrecta");
                return null;
            }
        }
    }

    //retorna -1 si l'usuari no existeix
    //retorna -2 si la contrasenya és incorrecta
    //retorna 1 si tot ha anat bé
    private static Moderador loginModerador() {
        System.out.println("Entra el nom del moderador: ");
        String username = lector.nextLine();

        Moderador m = buscarModerador(username);
        if (m == null) {
            System.out.println("Moderador no trobat");
            return null;
        }
        else
        {
            System.out.println("Entra la contrasenya: ");
            String password = lector.nextLine();
            if (m.getContrasenya().equals(password)) {
                return m;
            }
            else
            {
                System.out.println("Contrasenya incorrecta");
                return null;
            }
        }
    }

    private static Usuari buscarUsuari(String nomUsuari) {
        for (Usuari usuari : usuaris) {
            if (usuari.getNomUsuari().equals(nomUsuari)) {
                return usuari;
            }
        }
        return null;
    }

    private static Moderador buscarModerador(String nomModerador)
    {
        for (Moderador m : moderadors) {
            if (m.getNomUsuari().equals(nomModerador)) {
                return m;
            }
        }
        return null;
    }

    private static void enviarMissatgePrivat(Usuari usuari)
    {
        System.out.println("Entra el nom de l'usuari al que vols enviar el missatge: ");
        String nomUsuari = lector.nextLine();
        Usuari destinatari = buscarUsuari(nomUsuari);

        Missatge m = entrarMissatge();

        usuari.enviarMissatgePrivat(destinatari, m, false);

        System.out.println("L'usuari " + usuari.getNomUsuari() + " ha enviat el missatge: "
                + m.contingut() + " a l'usuari " + destinatari.getNomUsuari() + "\n");
    }

    private static Missatge entrarMissatge() {

        System.out.println("Entra el missatge paraula a paraula: (simbols també)");
        Missatge m = new Missatge(-1);
        String tipus="";

        while(tipus.compareTo("F") != 0) {
            System.out.println("Vols entrar una paraula, un signe de puntuació o ja estas? (P/S/F)");
            tipus = lector.nextLine();

            switch (tipus)
            {
                case "P":
                    System.out.println("Entra la paraula: ");
                    Paraula p = new Paraula(lector.nextLine());
                    m.afegirElementAlFinal(p);
                    break;
                case "S":
                    System.out.println("Entra el signe de puntuació: ");
                    SignesPuntuacio s = new SignesPuntuacio(lector.nextLine());
                    m.afegirElementAlFinal(s);
                    break;
                case "F":
                    break;
                default:
                    System.out.println("Opció no vàlida");
                    break;
            }
        }
        return m;
    }

    private static void canviarRelacioUsuari(Usuari usuari) {
        System.out.println("Entra el nom de l'usuari amb el que vols canviar la relació: ");
        String nomUsuari = lector.nextLine();
        Usuari u = buscarUsuari(nomUsuari);

        if (u == null) {
            System.out.println("Usuari no trobat");
            return;
        }

        EstatUsuari estatAntic = usuari.obtenirRelacio(u);

        System.out.println("Entra el nou estat de la relació: ");
        System.out.println("1. Amic");
        System.out.println("2. Conegut");
        System.out.println("3. Saludat");
        int opcio = lector.nextInt();
        lector.nextLine();

        EstatUsuari estat = EstatUsuari.getEstatUsuari(opcio);

        usuari.assignarRelacio(u, estat);

        System.out.println("Relació amb "+nomUsuari+" canviada correctament ("+((estatAntic == null) ? "" : estatAntic) +" -> "+estat+")\n");
    }

    private static void canviarTextUsuari(Moderador m)
    {
        System.out.println("Entra el nom de l'usuari del text que vols modificar: ");
        String nomUsuari = lector.nextLine();
        Usuari u = buscarUsuari(nomUsuari);

        if (u == null) {
            System.out.println("Usuari no trobat");
            return;
        }

        System.out.println("Textos originals de l'usuari "+nomUsuari+": ");
        mostrarTextosUsuari(u);
        System.out.println("Entra el número del text que vols modificar: ");
        int numText = lector.nextInt();
        lector.nextLine();

        Text t = entrarText();

        u.canviarText(numText-1, t);

        System.out.println("Text modificat pel moderador: " + m.getNomUsuari());
        System.out.println("Nou text de l'usuari " + nomUsuari + ": " + t.contingut() + "\n");

    }

    public static void mostrarTextosUsuari(Usuari u)
    {
        int i=0;
        Iterator<Text> it = u.obtenirTextos();
        while(it.hasNext())
        {
            i++;
            Text t = it.next();
            System.out.println(i + ". " + t.contingutOriginal() + "\n");
        }
    }

    public static Text entrarText()
    {
        System.out.println("Entra el text paraula a paraula: (simbols també)");
        Text t = new Text(-1, new ArrayList<>());
        String tipus="";

        while(tipus.compareTo("F") != 0) {
            System.out.println("Vols entrar una paraula, un signe de puntuació o ja estas? (P/S/F)");
            tipus = lector.nextLine();

            switch (tipus)
            {
                case "P":
                    System.out.println("Entra la paraula: ");
                    Paraula p = new Paraula(lector.nextLine());
                    t.afegirElementAlFinal(p);
                    break;
                case "S":
                    System.out.println("Entra el signe de puntuació: ");
                    SignesPuntuacio s = new SignesPuntuacio(lector.nextLine());
                    t.afegirElementAlFinal(s);
                    break;
                case "F":
                    break;
                default:
                    System.out.println("Opció no vàlida");
                    break;
            }
        }
        return t;
    }

}
