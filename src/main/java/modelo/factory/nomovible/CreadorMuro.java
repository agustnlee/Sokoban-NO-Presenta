package modelo.factory.nomovible;

import modelo.entidades.nomovible.EntidadNoMovible;
import modelo.entidades.nomovible.Muro;

public class CreadorMuro implements CreadorNoMovible {

    @Override
    public EntidadNoMovible crear() {
        return (EntidadNoMovible) new Muro();
    }
    
}

