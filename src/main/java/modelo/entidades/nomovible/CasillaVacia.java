package modelo.entidades.nomovible;

import modelo.entidades.TipoEntidad;
import modelo.entidades.movible.EntidadMovible;
import modelo.entidades.movible.strategy.entidadmovible.MovimientoNormal;

public class CasillaVacia implements EntidadNoMovible {

    public CasillaVacia(){}

    @Override
    public boolean recibirEntidadMovible(EntidadMovible entidad) {
        return true;
    }

    @Override
    public boolean efectoAlEntrar(EntidadMovible entidad) {
        entidad.setComportamientoMovible(new MovimientoNormal());
        return false;
    }

    @Override
    public TipoEntidad getTipoEntidad() {
        return TipoEntidad.VACIA;
    }
}
