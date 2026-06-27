package modelo.entidades.nomovible;

import modelo.entidades.TipoEntidad;
import modelo.entidades.movible.EntidadMovible;
import modelo.entidades.movible.strategy.entidadmovible.MovimientoResbaladizo;

public class CasillaResbaladiza implements EntidadNoMovible {
    public CasillaResbaladiza(){
        // usar creador de casilla resbaladiza.

    }
    @Override
    public boolean recibirEntidadMovible(EntidadMovible e) {
        return true; // permite que la entidad movible entre.
    }
    @Override
    public boolean efectoAlEntrar(EntidadMovible e) {
        e.setComportamientoMovible(new MovimientoResbaladizo());
        return true;
    }
    @Override
    public void saleEntidadMovible() {
        // 
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
        return TipoEntidad.RESBALADIZA;
    }

}