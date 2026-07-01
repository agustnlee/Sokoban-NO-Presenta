package modelo.entidades.nomovible;

import modelo.entidades.TipoEntidad;
import modelo.entidades.movible.EntidadMovible;
import modelo.entidades.movible.strategy.entidadmovible.MovimientoResbaladizo;

public class CasillaResbaladiza implements EntidadNoMovible {
    public CasillaResbaladiza(){ }

    @Override
    public boolean recibirEntidadMovible(EntidadMovible e) {
        return true; 
    }
    @Override
    public boolean efectoAlEntrar(EntidadMovible e) {
        e.setComportamientoMovible(new MovimientoResbaladizo());
        return true;
    }


    @Override
    public TipoEntidad getTipoEntidad() {
        return TipoEntidad.RESBALADIZA;
    }

}