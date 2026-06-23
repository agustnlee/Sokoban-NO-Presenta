package modelo.entidades.nomovible;

import modelo.entidades.TipoEntidad;
import modelo.entidades.movible.EntidadMovible;
public class CasillaDestino implements EntidadNoMovible {

    public CasillaDestino() { 
        // Constructor vacío
    }

    @Override
    public boolean recibirEntidadMovible(EntidadMovible e) {
        return true;
    }

    @Override
    public void efectoAlEntrar(EntidadMovible e) {
        // La validación de bloqueo se delega al patrón Strategy de la caja (EstrategiaBloqueable).
        // La casilla en sí misma no hace nada activo aquí.

    }

    @Override
    public void saleEntidadMovible() {
        // Sin comportamiento especial al salir (si es que la entidad no estaba bloqueada).
    }

    @Override
    public boolean alterarCandado() {
        //
        return false;
    }

    @Override
    public boolean alterarDestino() {
        //
        return false;
    }

    @Override
    public TipoEntidad getTipoEntidad() {
        return TipoEntidad.DESTINO;
    }
}
