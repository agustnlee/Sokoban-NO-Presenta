package modelo.entidades.movible;

import modelo.entidades.TipoEntidad;

// Solamente utiliza los metodos de la clase abstracta, aca se podria agregar especificos de jugador como vida en un futuro

public class Jugador extends EntidadMovible {
    public Jugador() { }

    @Override
    public TipoEntidad getTipoEntidad() {
        return TipoEntidad.JUGADOR;
    }
    
}
