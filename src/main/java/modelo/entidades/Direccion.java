package modelo.entidades;

public enum Direccion {
    ARRIBA(-1, 0),
    ABAJO(1, 0),
    IZQUIERDA(0, -1),
    DERECHA(0, 1);

    private final int direccionFila;
    private final int direccionColumna;

    Direccion(int direccionFila, int direccionColumna) {
        this.direccionFila = direccionFila;
        this.direccionColumna = direccionColumna;
    }

    public int getDireccionFila() { return direccionFila; }
    public int getDireccionColumna() { return direccionColumna; }

    public Direccion esOpuesta() {
        switch (this) {
            case ARRIBA: return ABAJO;
            case ABAJO: return ARRIBA;
            case IZQUIERDA: return DERECHA;
            case DERECHA: return IZQUIERDA;
            default: throw new IllegalStateException("Direccion No Existente");
        }
    }
}