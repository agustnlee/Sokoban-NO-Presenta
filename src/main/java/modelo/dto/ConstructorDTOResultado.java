package modelo.dto;

import modelo.entidades.Coordenada;
import modelo.entidades.Tablero;
import modelo.entidades.TipoEntidad;
import modelo.entidades.movible.Caja;
import modelo.entidades.movible.EntidadMovible;
import modelo.entidades.nomovible.EntidadNoMovible;
import modelo.metricas.Puntaje;

import java.util.ArrayList;
import java.util.List;

public class ConstructorDTOResultado {

    public DTOResultado construir(Tablero tablero, boolean victoria, boolean derrota, Puntaje puntaje) {
        List<DTOCelda> celdas = new ArrayList<>();

        for (int fila = 0; fila < tablero.getFilaMax(); fila++) {
            for (int columna = 0; columna < tablero.getColumnaMax(); columna++) {
                celdas.add(construirCelda(new Coordenada(fila, columna), tablero));
            }
        }

        return new DTOResultado(
                celdas, victoria, derrota,
                puntaje.getPuntaje(), puntaje.getUndos(), puntaje.getMovimientos()
        );
    }

    private DTOCelda construirCelda(Coordenada c, Tablero tablero) {
        EntidadNoMovible casilla = tablero.getNoMovible(c);
        EntidadMovible movible = tablero.getMovible(c);

        TipoEntidad tipoCasilla = casilla.getTipoEntidad();
        TipoEntidad tipoEntidad = (movible != null) ? movible.getTipoEntidad() : null;

        if (movible instanceof Caja) {
            Caja caja = (Caja) movible;
            return new DTOCelda(c, tipoEntidad, tipoCasilla, caja.getEmpujesRestantes(), caja.estaBloqueada());
        }

        return new DTOCelda(c, tipoEntidad, tipoCasilla);
    }
}