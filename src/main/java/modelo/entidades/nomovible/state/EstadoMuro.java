package modelo.entidades.nomovible.state;

import modelo.entidades.TipoEntidad;
import modelo.entidades.nomovible.Muro;

public interface EstadoMuro {
    boolean esTransitable();
    void evaluarEstado(Muro muro, boolean estadoCandado);
    TipoEntidad getTipoEntidad();
}