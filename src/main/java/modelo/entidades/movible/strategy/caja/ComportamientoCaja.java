package modelo.entidades.movible.strategy.caja;

import modelo.entidades.Tablero;
import modelo.entidades.TipoEntidad;
import modelo.entidades.movible.Caja;

public interface ComportamientoCaja {
    void reaccionarLlegada(Caja c, Tablero tablero);
    boolean cuentaParaVictoria();
    boolean estaEnEstadoDerrota();
    TipoEntidad getTipoEntidad();
}