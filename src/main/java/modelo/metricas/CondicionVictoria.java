package modelo.metricas;

import modelo.entidades.Coordenada;
import modelo.entidades.Tablero;
import modelo.entidades.movible.EntidadMovible;

public class CondicionVictoria {

    public boolean evaluar(Tablero tablero) {
        for (int fila = 0; fila < tablero.getFilaMax(); fila++) {
            for (int columna = 0; columna < tablero.getColumnaMax(); columna++) {
                EntidadMovible entidad = tablero.getMovible(new Coordenada(fila, columna));
                if (entidad != null && entidad.cuentaParaVictoria() && !entidad.estaBloqueada()) {
                    return false;
                }
            }
        }
        return true;
    }
}