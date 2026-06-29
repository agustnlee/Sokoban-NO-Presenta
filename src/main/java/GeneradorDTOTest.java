

import modelo.dto.DTOCelda;
import modelo.dto.DTOResultado;
import modelo.entidades.Coordenada;
import modelo.entidades.DimensionTablero;
import modelo.entidades.TipoEntidad;

import java.util.ArrayList;
import java.util.List;


public class GeneradorDTOTest {

    public static DTOResultado generarTableroDePrueba() {
        int filas = DimensionTablero.ESTANDAR.getFilas();
        int columnas = DimensionTablero.ESTANDAR.getColumnas();
        List<DTOCelda> celdas = new ArrayList<>();

        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                Coordenada coord = new Coordenada(f, c);
                TipoEntidad tipoCasilla = TipoEntidad.VACIA;
                TipoEntidad tipoEntidad = null;
                int empujes = -1;
                boolean bloqueada = false;

                // borde = pared
                if (f == 0 || f == filas - 1 || c == 0 || c == columnas - 1) {
                    tipoCasilla = TipoEntidad.PARED;
                }
                // jugador en una posición fija
                else if (f == 1 && c == 1) {
                    tipoEntidad = TipoEntidad.JUGADOR;
                }
                // caja normal
                else if (f == 2 && c == 3) {
                    tipoEntidad = TipoEntidad.CAJA;
                }
                // destino debajo de la caja
                else if (f == 2 && c == 5) {
                    tipoCasilla = TipoEntidad.DESTINO;
                }
                // caja fragil con empujes
                else if (f == 3 && c == 3) {
                    tipoEntidad = TipoEntidad.CAJA_FRAGIL;
                    empujes = 7;
                }
                // caja llave + candado
                else if (f == 3 && c == 6) {
                    tipoEntidad = TipoEntidad.CAJA_LLAVE;
                } else if (f == 1 && c == 6) {
                    tipoCasilla = TipoEntidad.CANDADO;
                }
                // muro cerrado
                else if (f == 2 && c == 7) {
                    tipoCasilla = TipoEntidad.MURO_CERRADO;
                }
                // resbaladiza
                else if (f == 3 && c == 1) {
                    tipoCasilla = TipoEntidad.RESBALADIZA;
                }
                // escalera
                else if (f == 1 && c == 8) {
                    tipoEntidad = TipoEntidad.ESCALERA;
                }

                if (tipoEntidad != null) {
                    celdas.add(new DTOCelda(coord, tipoEntidad, tipoCasilla, empujes, bloqueada));
                } else {
                    celdas.add(new DTOCelda(coord, null, tipoCasilla));
                }
            }
        }

        return new DTOResultado(celdas, false, false, 1000, 0, 0);
    }
}