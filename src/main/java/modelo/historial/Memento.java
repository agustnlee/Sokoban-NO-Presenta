package modelo.historial;

import java.util.List;

public class Memento {

    private final List<EstadoEntidadMovible> estados;
    private final EstadoCandado estadoCandado; 

    public Memento(List<EstadoEntidadMovible> estados, EstadoCandado estadoCandado) {
        this.estados = estados;
        this.estadoCandado = estadoCandado;
    }

    public void restaurar() {
        for (EstadoEntidadMovible estado : estados) {
            estado.restaurar();
        }
        if (estadoCandado != null) {
            estadoCandado.restaurar();
        }
    }
}