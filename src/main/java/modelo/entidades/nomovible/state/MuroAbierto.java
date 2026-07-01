package modelo.entidades.nomovible.state;

import modelo.entidades.TipoEntidad;
import modelo.entidades.nomovible.Muro;

public class MuroAbierto implements EstadoMuro {
 
    @Override
    public boolean esTransitable() {
        return true;
    }
 
    @Override
    public void evaluarEstado(Muro muro, boolean estadoCandado) {
        if (!estadoCandado) {
            muro.setEstado(new MuroCerrado());
        }
    }

    @Override
    public TipoEntidad getTipoEntidad() {
        return TipoEntidad.MURO_ABIERTO;
    }
}
 