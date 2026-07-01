package modelo.persistencia;

import modelo.entidades.movible.EntidadMovible;
import modelo.entidades.movible.Jugador;
import modelo.entidades.nomovible.CasillaCandado;
import modelo.entidades.nomovible.EntidadNoMovible;
import modelo.entidades.nomovible.Muro;
import modelo.factory.nomovible.*;
import modelo.factory.movible.*;
import modelo.entidades.Tablero;
import modelo.entidades.Coordenada;
import modelo.entidades.DimensionTablero;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
/**
 * Siglas no movibles: PA Pared, VA Vacia, DE Destino, RE Resbaladiza,
 *                      CA Candado, MU Muro
 * Siglas movibles:    JU Jugador, CB CajaNormal, CF CajaFragil, CL CajaLlave,
 *                      ES Escalera, NA (ninguna entidad movible en esa celda)
 * 
 * EJEMPLO
PA PA PA PA PA PA PA PA PA PA
PA VA VA VA DE VA VA VA MU PA
PA VA VA VA VA VA VA CA MU PA
PA VA VA VA DE VA VA VA MU PA
PA PA PA PA PA PA PA PA PA PA
---
NA NA NA NA NA NA NA NA NA NA
NA JU NA CB NA NA NA NA NA NA
NA NA NA CL NA NA NA NA NA NAd
NA NA NA NA NA NA NA NA NA NA
NA NA NA NA NA NA NA NA NA NA
 */

public class GestorArchivo {
 
    private static final String SIGLA_JUGADOR = "JU";
    private static final String SIGLA_NADA = "NA";
    private static final String SIGLA_MURO = "MU";
    private static final String SIGLA_CANDADO = "CA";
    private static final String SEPARADOR = "---";
 
    private static GestorArchivo instancia;
 
    private final Map<String, CreadorMovible> creadoresMovibles;
    private final Map<String, CreadorNoMovible> creadoresNoMovibles;
 
    private GestorArchivo() {
        creadoresMovibles = new HashMap<>();
        creadoresMovibles.put(SIGLA_JUGADOR, new CreadorJugador());
        creadoresMovibles.put("CB", new CreadorCajaNormal());
        creadoresMovibles.put("CF", new CreadorCajaFragil());
        creadoresMovibles.put("CL", new CreadorCajaLlave());
        creadoresMovibles.put("ES", new CreadorEscalera());
 
        creadoresNoMovibles = new HashMap<>();
        creadoresNoMovibles.put("PA", new CreadorPared());
        creadoresNoMovibles.put("VA", new CreadorCasillaVacia());
        creadoresNoMovibles.put("DE", new CreadorCasillaDestino());
        creadoresNoMovibles.put("RE", new CreadorCasillaResbaladiza());
        creadoresNoMovibles.put(SIGLA_CANDADO, new CreadorCasillaCandado());
        creadoresNoMovibles.put(SIGLA_MURO, new CreadorMuro());
    }
 
    public static GestorArchivo getInstancia() {
        if (instancia == null) {
            instancia = new GestorArchivo();
        }
        return instancia;
    }
 
    public Tablero cargarMapa(int nivel) {
        List<String> lineas = leerArchivo("niveles/nivel" + nivel + ".txt");
 
        int filas = DimensionTablero.ESTANDAR.getFilas();
        int lineasEsperadas = filas * 2 + 1;
 
        if (lineas.size() < lineasEsperadas) {
            throw new RuntimeException("El archivo del nivel " + nivel + " tiene " + lineas.size()
                    + " lineas, se esperaban al menos " + lineasEsperadas);
        }
 
        Tablero tablero = new Tablero(); 
 
        List<String> lineasNoMovibles = lineas.subList(0, filas);
        construirNoMovibles(lineasNoMovibles, tablero);
 
        int indiceSeparador = filas;
        if (!lineas.get(indiceSeparador).trim().equals(SEPARADOR)) {
            throw new RuntimeException("Se esperaba la linea separadora '" + SEPARADOR
                    + "' en la linea " + (indiceSeparador + 1));
        }
 
        int inicioMovibles = indiceSeparador + 1;
        List<String> lineasMovibles = lineas.subList(inicioMovibles, inicioMovibles + filas);
        construirMovibles(lineasMovibles, tablero);
 
        return tablero;
    }
 
    private void construirNoMovibles(List<String> lineas, Tablero tablero) {
        CasillaCandado candadoDelNivel = null;
        List<Muro> murosDelNivel = new ArrayList<>();
 
        for (int fila = 0; fila < lineas.size(); fila++) {
            String[] siglas = lineas.get(fila).trim().split("\\s+");
            for (int columna = 0; columna < siglas.length; columna++) {
                String sigla = siglas[columna];
                CreadorNoMovible creador = creadoresNoMovibles.get(sigla);
                if (creador == null) {
                    throw new RuntimeException("Sigla de casilla desconocida: '" + sigla + "'");
                }
 
                EntidadNoMovible entidad = creador.crear();
                tablero.colocarNoMovible(entidad, new Coordenada(fila, columna));

                if (sigla.equals(SIGLA_MURO)) {
                    murosDelNivel.add((Muro) entidad);
                } else if (sigla.equals(SIGLA_CANDADO)) {
                    candadoDelNivel = (CasillaCandado) entidad;
                }
            }
        }
 
        vincularMurosAlCandado(candadoDelNivel, murosDelNivel);
        tablero.setCandado(candadoDelNivel);
    }
 
    private void vincularMurosAlCandado(CasillaCandado candado, List<Muro> muros) {
        if (candado == null) {
            return; 
        }
        for (Muro muro : muros) {
            candado.suscribir(muro);
        }
    }
 
    private void construirMovibles(List<String> lineas, Tablero tablero) {
        for (int fila = 0; fila < lineas.size(); fila++) {
            String[] siglas = lineas.get(fila).trim().split("\\s+");
            for (int columna = 0; columna < siglas.length; columna++) {
                String sigla = siglas[columna];
                if (sigla.equals(SIGLA_NADA)) {
                    continue; 
                }
 
                CreadorMovible creador = creadoresMovibles.get(sigla);
                if (creador == null) {
                    throw new RuntimeException("Sigla de entidad movible desconocida: '" + sigla + "'");
                }
 
                EntidadMovible entidad = creador.crear();
                tablero.colocarMovible(entidad, new Coordenada(fila, columna));
 
                if (sigla.equals(SIGLA_JUGADOR)) {
                    tablero.setJugador((Jugador) entidad);
                }
            }
        }
    }
 
    private List<String> leerArchivo(String ruta) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(ruta)) {

            if (is == null) {
                throw new RuntimeException("No se encontró el recurso: " + ruta);
            }

            return new BufferedReader(new InputStreamReader(is))
                    .lines()
                    .toList();

        } catch (IOException e) {
            throw new RuntimeException("No se pudo leer el recurso: " + ruta, e);
        }
    }
}