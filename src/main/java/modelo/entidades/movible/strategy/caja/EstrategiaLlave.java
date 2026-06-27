package modelo.entidades.movible.strategy.caja;

import modelo.entidades.TipoEntidad;

public class EstrategiaLlave implements ComportamientoCaja {

    @Override
    public TipoEntidad getTipoEntidad() {
        return TipoEntidad.CAJA_LLAVE;
    }
    
}
