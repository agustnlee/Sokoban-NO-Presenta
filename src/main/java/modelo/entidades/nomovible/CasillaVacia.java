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
    public void efectoAlEntrar(EntidadMovible entidad) {
        entidad.setComportamientoMovible(new MovimientoNormal());
    }

    @Override
    public TipoEntidad getTipoEntidad() {
        return TipoEntidad.VACIA;
    }
}
