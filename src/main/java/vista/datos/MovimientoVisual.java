package vista.datos;

import java.util.Collections;
import java.util.List;

public class MovimientoVisual {

    private final int jugadorOrigenFila;
    private final int jugadorOrigenCol;
    private final int jugadorDestinoFila;
    private final int jugadorDestinoCol;

    // Deslizamientos encadenados: jugador, luego caja, luego hielo si aplica.
    // Puede estar vacío si solo se mueve el jugador.

    private final List<DeslizamientoVisual> deslizamientos;

    public MovimientoVisual(int jugadorOrigenFila, int jugadorOrigenCol, int jugadorDestinoFila, int jugadorDestinoCol, List<DeslizamientoVisual> deslizamientos) {

        this.jugadorOrigenFila  = jugadorOrigenFila;
        this.jugadorOrigenCol   = jugadorOrigenCol;
        this.jugadorDestinoFila = jugadorDestinoFila;
        this.jugadorDestinoCol  = jugadorDestinoCol;
        this.deslizamientos     = deslizamientos != null ? deslizamientos : Collections.emptyList();
    }

    // Constructor sin deslizamientos (jugador solo)
    public MovimientoVisual(int jugadorOrigenFila, int jugadorOrigenCol, int jugadorDestinoFila, int jugadorDestinoCol) {
        
        this(jugadorOrigenFila, jugadorOrigenCol, jugadorDestinoFila, jugadorDestinoCol, Collections.emptyList());
    }

    public int                      getJugadorOrigenFila()  { return jugadorOrigenFila; }
    public int                      getJugadorOrigenCol()   { return jugadorOrigenCol; }
    public int                      getJugadorDestinoFila() { return jugadorDestinoFila; }
    public int                      getJugadorDestinoCol()  { return jugadorDestinoCol; }
    public List<DeslizamientoVisual> getDeslizamientos()    { return deslizamientos; }

    public boolean tieneDeslizamientos() { return !deslizamientos.isEmpty(); }
}