package modelo.entidades.nomovible.state;

import modelo.entidades.nomovible.Muro;
import modelo.entidades.TipoEntidad;

public class MuroCerrado implements EstadoMuro {
    
    @Override
    public boolean esTransitable() {
        return false; // Funciona igual que una pared
    }

    @Override
    public void evaluarEstado(Muro muro, boolean estadoCandado) {
        // Si recibe 'true' (llave insertada), transiciona a abierto
        if (estadoCandado) {
            muro.setEstado(new MuroAbierto());
        }
    }

    @Override
    public TipoEntidad getTipoEntidad() {
        // Retornar la instancia actual que implementa EstadoMuro.
        return TipoEntidad.MURO_CERRADO;
    }
}
