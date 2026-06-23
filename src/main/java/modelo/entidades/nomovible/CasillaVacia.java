package modelo.entidades.nomovible;

import modelo.entidades.TipoEntidad;
import modelo.entidades.movible.EntidadMovible;

public class CasillaVacia implements EntidadNoMovible {

    
    public CasillaVacia(){
        // usar creador de casilla vacia.

    }
    @Override
    public boolean recibirEntidadMovible(EntidadMovible e) {
        return true; // permite que la entidad movible entre.
    }
    @Override
    public void efectoAlEntrar(EntidadMovible e) {
        // no hace nada, es una casilla vacía.
    }
    @Override
    public void saleEntidadMovible() {
        // no hace nada, la entidad movible ya salió.
    }
    @Override
    public boolean alterarCandado() {
        // no hace nada, es una casilla vacía.
        return false;           
    }
    @Override
    public boolean alterarDestino() {
        // no hace nada, es una casilla vacía.
        return false;
    }

    @Override
    public TipoEntidad getTipoEntidad() {
        return TipoEntidad.VACIA;
    }
}
