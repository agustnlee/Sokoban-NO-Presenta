package modelo.factory.nomovible;

import modelo.entidades.nomovible.CasillaVacia;
import modelo.entidades.nomovible.EntidadNoMovible;

public class CreadorCasillaVacia implements CreadorNoMovible {

    @Override
    public EntidadNoMovible crear() {
        return new CasillaVacia();
    }
    
}