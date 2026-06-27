package modelo.entidades.nomovible.state;

import modelo.entidades.nomovible.Muro;
import modelo.entidades.TipoEntidad;

public class MuroCerrado implements EstadoMuro {
    
    @Override
    public boolean esTransitable() {
        return false; 
    }

    @Override
    public void evaluarEstado(Muro muro, boolean estadoCandado) {
        if (estadoCandado) {
            muro.setEstado(new MuroAbierto());
        }
    }

    @Override
    public TipoEntidad getTipoEntidad() {
        return TipoEntidad.MURO_CERRADO;
    }
}
