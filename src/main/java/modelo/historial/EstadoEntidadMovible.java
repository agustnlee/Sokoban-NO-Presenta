package modelo.historial;

import modelo.entidades.Coordenada;
import modelo.entidades.Tablero;
import modelo.entidades.movible.EntidadMovible;

public class EstadoEntidadMovible {

    protected final EntidadMovible entidad;
    private final Coordenada posicion;

    public EstadoEntidadMovible(EntidadMovible entidad, Coordenada posicion) {
        this.entidad = entidad;
        this.posicion = posicion;
    }

    public void restaurar(Tablero tablero) {
        tablero.restaurarPosicionMovible(entidad, posicion);
    }
}