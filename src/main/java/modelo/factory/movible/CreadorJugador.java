package modelo.factory.movible;

import modelo.entidades.movible.EntidadMovible;
import modelo.entidades.movible.Jugador;

public class CreadorJugador implements CreadorMovible {

    @Override
    public EntidadMovible crear() {
        return new Jugador();
    }
    
}
