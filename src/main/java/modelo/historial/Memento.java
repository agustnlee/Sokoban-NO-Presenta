package modelo.historial;

import java.util.List;

import modelo.entidades.Coordenada;
import modelo.entidades.Tablero;

public class Memento {

    private final List<EstadoEntidadMovible> estados;
    private final EstadoCandado estadoCandado; 

    public Memento(List<EstadoEntidadMovible> estados, EstadoCandado estadoCandado) {
        this.estados = estados;
        this.estadoCandado = estadoCandado;
    }

    public void restaurar(Tablero tablero) {
        for (EstadoEntidadMovible estado : estados) {
            Coordenada posActual = estado.getEntidad().getPosicion();
            if (posActual != null) {
                tablero.limpiarMovible(posActual);
            }
        }
        for (EstadoEntidadMovible estado : estados) {
            estado.restaurar(tablero);
        }
        if (estadoCandado != null) {
            estadoCandado.restaurar();
        }
    }
}