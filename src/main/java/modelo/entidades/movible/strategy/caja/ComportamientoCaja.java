package modelo.entidades.movible.strategy.caja;

import modelo.entidades.Tablero;
import modelo.entidades.TipoEntidad;
import modelo.entidades.movible.Caja;

public interface ComportamientoCaja {
    void reaccionarLlegada(Caja c, Tablero tablero);
    boolean cuentaParaVictoria();
    boolean estaEnEstadoDerrota();
    default int getEmpujesRestantes() { return -1; }
    default void restaurarEmpujesRestantes(int restantes) { }
    TipoEntidad getTipoEntidad();  
}