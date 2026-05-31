package vista.juego.animacion;

import vista.datos.DeslizamientoVisual;
import vista.datos.EntidadVisual;
import vista.datos.EstadoVisualMapa;
import vista.datos.MovimientoVisual;

import java.util.ArrayDeque;
import java.util.Deque;

public class GestorAnimaciones {

    private final Deque<AnimacionDeslizamiento> cola = new ArrayDeque<>();
    private AnimacionDeslizamiento actual = null;
    private Runnable onTodasTerminadas;

    // tam de celda en px, necesario para convertir fila/col a px
    private final int tamCelda;

    public GestorAnimaciones(int tamCelda) {
        this.tamCelda = tamCelda;
    }

    /**
    Recibe un MovimientoVisual del controlador y encola todas las
     animaciones correspondientes: primero jugador, luego deslizamientos.
     Se ejecutan en secuencia, una a la vez.
     */
    public void encolar(MovimientoVisual mv, EstadoVisualMapa estado, Runnable onTodasTerminadas) {
        this.onTodasTerminadas = onTodasTerminadas;
        cola.clear();
        actual = null;

        // 1. Animación del jugador
        EntidadVisual jugador = estado.getEntidad(
            mv.getJugadorOrigenFila(), mv.getJugadorOrigenCol()
        );
        if (jugador != null) {
            cola.add(crearAnimacion(jugador,
                mv.getJugadorOrigenFila(),  mv.getJugadorOrigenCol(),
                mv.getJugadorDestinoFila(), mv.getJugadorDestinoCol(),
                null
            ));
        }

        // 2. Deslizamientos encadenados (caja empujada, etc.)
        for (DeslizamientoVisual d : mv.getDeslizamientos()) {
            EntidadVisual entidad = estado.getEntidad(d.getOrigenFila(), d.getOrigenCol());
            if (entidad != null) {
                cola.add(crearAnimacion(entidad,
                    d.getOrigenFila(),  d.getOrigenCol(),
                    d.getDestinoFila(), d.getDestinoCol(),
                    null
                ));
            }
        }

        avanzarCola();
    }

    /**
     * Llamado cada tick del Timer (~16ms).
     * Avanza la animación actual.
     */
    public void tick(float deltaMs) {
        if (actual == null) return;
        actual.tick(deltaMs);
        if (actual.isTerminada()) {
            avanzarCola();
        }
    }

    public boolean hayAnimacionActiva() {
        return actual != null && !actual.isTerminada();
    }

    // -------------------------------------------------------------------------

    private AnimacionDeslizamiento crearAnimacion(EntidadVisual entidad,
                                                   int origenFila,  int origenCol,
                                                   int destinoFila, int destinoCol,
                                                   Runnable extra) {
        float ox = origenCol  * tamCelda;
        float oy = origenFila * tamCelda;
        float dx = destinoCol  * tamCelda;
        float dy = destinoFila * tamCelda;
        return new AnimacionDeslizamiento(entidad, ox, oy, dx, dy, extra);
    }

    private void avanzarCola() {
        if (cola.isEmpty()) {
            actual = null;
            if (onTodasTerminadas != null) onTodasTerminadas.run();
            return;
        }
        actual = cola.poll();
    }
    
    
    public void cancelar() {
        cola.clear();
        if (actual != null) {
            actual.getEntidad().setAnimando(false);
            actual = null;
        }
        onTodasTerminadas = null;
    }
}