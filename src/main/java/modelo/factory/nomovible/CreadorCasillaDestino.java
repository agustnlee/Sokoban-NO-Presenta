package modelo.factory.nomovible;

import modelo.entidades.nomovible.CasillaDestino;
import modelo.entidades.nomovible.EntidadNoMovible;

public class CreadorCasillaDestino implements CreadorNoMovible {

    @Override
    public EntidadNoMovible crear() {
        return new CasillaDestino();
    }
    
}
