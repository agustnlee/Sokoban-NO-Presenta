package modelo.entidades.movible.strategy.entidadmovible;

import modelo.entidades.Coordenada;
import modelo.entidades.Direccion;
import modelo.entidades.Tablero;

public class MovimientoResbaladizo implements ComportamientoEntidadMovible {

    @Override
    public Coordenada calcularSiguientePosicion(Coordenada posicionActual, Direccion dir, Tablero tablero) {
        return posicionActual.sumar(dir);
    }
}