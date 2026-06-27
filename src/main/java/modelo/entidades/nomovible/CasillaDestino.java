package modelo.entidades.nomovible;

import modelo.entidades.Tablero;
import modelo.entidades.TipoEntidad;
import modelo.entidades.movible.EntidadMovible;
import modelo.entidades.movible.strategy.entidadmovible.MovimientoNormal;
public class CasillaDestino implements EntidadNoMovible {

    public CasillaDestino() { 
        // Constructor vacío
    }

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
    public void saleEntidadMovible() { }

    @Override
    public boolean alterarDestino() {
        //
        return true;
    }

    @Override
    public TipoEntidad getTipoEntidad() {
        return TipoEntidad.DESTINO;
    }
}
