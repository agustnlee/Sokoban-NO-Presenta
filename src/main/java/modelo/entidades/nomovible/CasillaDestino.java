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
