public enum EstatUsuari {
    AMIC(1),
    CONEGUT(2),
    SALUDAT(3);

    private final int id;

    EstatUsuari(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static EstatUsuari getEstatUsuari(int id) {
        for (EstatUsuari estatUsuari : EstatUsuari.values()) {
            if (estatUsuari.getId() == id) {
                return estatUsuari;
            }
        }
        return null;
    }
}
