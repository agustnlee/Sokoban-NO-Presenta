package vista.datos;

public class DeslizamientoVisual {

    private final TipoCeldaVisual tipo;
    private final int origenFila;
    private final int origenCol;
    private final int destinoFila;
    private final int destinoCol;

    public DeslizamientoVisual(TipoCeldaVisual tipo, int origenFila,  int origenCol, int destinoFila, int destinoCol) {
        this.tipo        = tipo;
        this.origenFila  = origenFila;
        this.origenCol   = origenCol;
        this.destinoFila = destinoFila;
        this.destinoCol  = destinoCol;
    }

    public TipoCeldaVisual getTipo()        { return tipo; }
    public int             getOrigenFila()  { return origenFila; }
    public int             getOrigenCol()   { return origenCol; }
    public int             getDestinoFila() { return destinoFila; }
    public int             getDestinoCol()  { return destinoCol; }
}