package vista.sonido;

public enum Sonido {
    FONDO_MISTERIO("/sonidos/fondo_misterio.wav", true), // Es loop
    FONDO_JUEGO("/sonidos/fondo_juego.wav", true),
    FONDO_NIVELES("/sonidos/fondo_niveles.wav", true),
    CLICK_BOTON("/sonidos/click.wav", false),
    HOVER_BOTON("/sonidos/hover.wav", false),
    CLICK_BOTON_NIVEL("/sonidos/click_nivel.wav", false),
    HOVER_BOTON_NIVEL("/sonidos/hover_nivel.wav", false),
    ESCRIBIR_LAPIZ("/sonidos/lapiz.wav", false),
    VICTORIA("/sonidos/victoria.wav", false),
    DERROTA("/sonidos/derrota.wav", false);

    private final String path;
    private final boolean loop;

    Sonido(String path, boolean loop) {
        this.path = path;
        this.loop = loop;
    }
    public String getPath() {
        return path;
    }
    public boolean isLoop() {
        return loop;
    }
    public static Sonido getSonido(String path) {
        for (Sonido s: Sonido.values()) {
            if (s.getPath().equals(path)) {
                return s;
            }
        }
            return  null;
    }
}
