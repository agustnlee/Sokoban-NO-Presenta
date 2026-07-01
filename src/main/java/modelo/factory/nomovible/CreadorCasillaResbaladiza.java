package modelo.factory.nomovible;

import modelo.entidades.nomovible.CasillaResbaladiza;
import modelo.entidades.nomovible.EntidadNoMovible;

public class CreadorCasillaResbaladiza implements CreadorNoMovible {

    @Override
    public EntidadNoMovible crear() {
        return new CasillaResbaladiza();
    }
    
}
