package modelo.entidades.nomovible;

import modelo.entidades.TipoEntidad;
import modelo.entidades.movible.EntidadMovible;

public class Pared implements EntidadNoMovible {

    public Pared() { 
        
    }

    @Override
    public boolean recibirEntidadMovible(EntidadMovible e) {
        return false; // Una pared nunca permite el paso
    }

    @Override
    public void efectoAlEntrar(EntidadMovible e) {

     }

    @Override
    public void saleEntidadMovible() { 

    }

    @Override
    public boolean alterarCandado() {
        return false; // Una pared no puede alterar un candado
    }

    @Override
    public boolean alterarDestino() {
        return false; // Una pared no puede alterar un destino
    }   
    @Override
    public TipoEntidad getTipoEntidad() {
        return TipoEntidad.PARED;
    }

}
