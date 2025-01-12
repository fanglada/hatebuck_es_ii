public enum EstatRelacio {

    AMICS(1),
    CONEGUTS(2),
    SALUDATS(3),
    TOTHOM(4);

    private final int id;

    EstatRelacio(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static EstatRelacio getEstatRelacio(int id) {
        for (EstatRelacio estatRelacio : EstatRelacio.values()) {
            if (estatRelacio.getId() == id) {
                return estatRelacio;
            }
        }
        return null;
    }
}