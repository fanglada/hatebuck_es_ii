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

        while (login(usuari)<0 && seguirIntentant) {
            System.out.println("Vols tornar a intentar-ho? (S/N)");
            String resposta = lector.nextLine();
            if(resposta.equals("N")) {
                seguirIntentant = false;
            }
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
                    int a = loginModerador();
                    if(a==1)
                    {
                        canviarTextUsuari();
                    }
                    break;
                case 3:

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
                //TODO: Agafar els Texts de l'usuari (cada simbol és diferent o han de ser el mateix (reutilitzar paraules))
                List<Text> texts = new ArrayList<>();

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
    private static int login(Usuari usu) {
        System.out.println("Entra el nom d'usuari: ");
        String username = lector.nextLine();

        Usuari u = buscarUsuari(username);
        if (u == null) {
            System.out.println("Usuari no trobat");
            return -1;
        }
        else
        {
            System.out.println("Entra la contrasenya: ");
            String password = lector.nextLine();
            if (u.getContrasenya().equals(password)) {
                usu = u;
                return 1;
            }
            else
            {
                System.out.println("Contrasenya incorrecta");
                return -2;
            }
        }
    }

    //retorna -1 si l'usuari no existeix
    //retorna -2 si la contrasenya és incorrecta
    //retorna 1 si tot ha anat bé
    private static int loginModerador() {
        int res = 0;
        System.out.println("Entra el nom d'usuari: ");
        String username = lector.nextLine();

        for (Moderador moderador : moderadors) {
            if (moderador.getNomUsuari().equals(username)) {
                System.out.println("Entra la contrasenya: ");
                String password = lector.nextLine();
                if (moderador.getContrasenya().equals(password)) {
                    res=1;
                }
                else
                {
                    System.out.println("Contrasenya incorrecta");
                    res=-2;
                }
            }
            else
            {
                System.out.println("Usuari no trobat");
                res=-1;
            }
        }
        return res;
    }

    private static Usuari buscarUsuari(String nomUsuari) {
        for (Usuari usuari : usuaris) {
            if (usuari.getNomUsuari().equals(nomUsuari)) {
                return usuari;
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

        System.out.println("Missatge enviat correctament");
        m.contingut();
    }

    private static Missatge entrarMissatge() {

        System.out.println("Entra el missatge paraula a paraula: (simbols també)");
        Missatge m = new Missatge();
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
}