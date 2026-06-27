package modelo.entidades.nomovible;

import modelo.entidades.TipoEntidad;
import modelo.entidades.movible.EntidadMovible;
import modelo.entidades.movible.strategy.entidadmovible.MovimientoNormal;
public class CasillaDestino implements EntidadNoMovible {

    public CasillaDestino() {  }

    @Override
    public boolean recibirEntidadMovible(EntidadMovible e) {
        return true;
    }

    @Override
    public boolean efectoAlEntrar(EntidadMovible e) {
        e.setComportamientoMovible(new MovimientoNormal());  
        return false; 
    }

 
    @Override
    public boolean alterarDestino() { 
        return true; 
    }

    @Override
    public TipoEntidad getTipoEntidad() {
        return TipoEntidad.DESTINO;
    }
}
