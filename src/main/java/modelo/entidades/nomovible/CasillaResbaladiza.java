package modelo.entidades.nomovible;

import modelo.entidades.TipoEntidad;
import modelo.entidades.movible.EntidadMovible;

public class CasillaResbaladiza implements EntidadNoMovible {
    public CasillaResbaladiza(){
        // usar creador de casilla resbaladiza.

    }
    @Override
    public boolean recibirEntidadMovible(EntidadMovible e) {
        return true; // permite que la entidad movible entre.
    }
    @Override
    public void efectoAlEntrar(EntidadMovible e) {
        // e.moverEnDireccion(e.getDireccionActual()); // mueve a la entidad movible en la dirección actual.
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