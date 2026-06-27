package modelo.entidades.nomovible;

import modelo.entidades.TipoEntidad;
import modelo.entidades.movible.EntidadMovible;

public class Pared implements EntidadNoMovible {

    public Pared() {  }

    @Override
    public boolean recibirEntidadMovible(EntidadMovible e) {
        return false; // Una pared nunca permite el paso
    }

    @Override
    public boolean efectoAlEntrar(EntidadMovible e) {
        return false;
     }

    @Override
    public TipoEntidad getTipoEntidad() {
        return TipoEntidad.PARED;
    }

}
