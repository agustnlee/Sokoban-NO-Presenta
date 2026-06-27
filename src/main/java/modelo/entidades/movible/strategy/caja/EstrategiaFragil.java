package modelo.entidades.movible.strategy.caja;

import modelo.entidades.TipoEntidad;

public class EstrategiaFragil implements ComportamientoCaja {

    @Override
    public TipoEntidad getTipoEntidad() {
        return TipoEntidad.CAJA;
    }
    
}
