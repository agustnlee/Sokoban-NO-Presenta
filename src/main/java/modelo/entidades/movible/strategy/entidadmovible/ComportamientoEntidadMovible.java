package modelo.entidades.movible.strategy.entidadmovible;

import modelo.entidades.Coordenada;
import modelo.entidades.Direccion;
import modelo.entidades.Tablero;

public interface ComportamientoEntidadMovible {
    Coordenada calcularSiguientePosicion(Coordenada posicionActual, Direccion dir, Tablero tablero);
}