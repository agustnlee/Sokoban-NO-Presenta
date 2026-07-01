package controlador;

import modelo.dto.ConstructorDTOResultado;
import modelo.dto.DTOResultado;
import modelo.entidades.Coordenada;
import modelo.entidades.Direccion;
import modelo.entidades.Tablero;
import modelo.entidades.movible.EntidadMovible;
import modelo.historial.EstadoEntidadMovible;
import modelo.entidades.nomovible.CasillaCandado;
import modelo.historial.EstadoCandado;
import modelo.historial.GestorHistorial;
import modelo.historial.Memento;
import modelo.metricas.CondicionDerrota;
import modelo.metricas.CondicionVictoria;
import modelo.metricas.Puntaje;
import modelo.persistencia.GestorArchivo;
import vista.MainVista;

import java.util.ArrayList;
import java.util.List;

public class ControladorJuego {

    private Tablero tablero;
    private final GestorHistorial historial;
    private final CondicionVictoria condicionVictoria;
    private final CondicionDerrota condicionDerrota;
    private final Puntaje puntaje;
    private final ConstructorDTOResultado constructorDTO;
    private final MainVista mainVista;
    private int nivel;

    public ControladorJuego(MainVista mainVista) {
        this.mainVista = mainVista;
        this.historial = new GestorHistorial();
        this.condicionVictoria = new CondicionVictoria();
        this.condicionDerrota = new CondicionDerrota();
        this.puntaje = new Puntaje();
        this.constructorDTO = new ConstructorDTOResultado();
    }

    public DTOResultado cargarNivel(int nivel) {
        this.nivel = nivel;
        this.tablero = GestorArchivo.getInstancia().cargarMapa(nivel);
        this.puntaje.resetPuntaje();

        Memento inicial = crearMemento();
        historial.guardar(inicial);

        return constructorDTO.construir(tablero, false, false, puntaje);
    }

    public DTOResultado reiniciarJuego() {
        return cargarNivel(this.nivel);
    }

    public DTOResultado mover(Direccion dir) {
        tablero.getJugador().mover(dir, tablero);

        puntaje.registrarMovimiento();
        historial.guardar(crearMemento());

        return evaluarYConstruir();
    }

    public DTOResultado undo() {
        Memento objetivo = historial.deshacer();
        if (objetivo != null) {
            objetivo.restaurar(tablero);
        }

        puntaje.registrarUndo();

        return evaluarYConstruir();
    }

    private DTOResultado evaluarYConstruir() {
        boolean victoria = condicionVictoria.evaluar(tablero);
        boolean derrota = condicionDerrota.evaluar(tablero);
        String motivoDerrota = null;

        if (derrota) {
            motivoDerrota = "¡Una caja se ha destruido!";
        } else if (victoria && puntaje.getPuntajeBruto() < 0) {
            victoria = false;
            derrota = true;
            motivoDerrota = "Te quedaste sin recursos para resolver el caso";
        }

        DTOResultado resultado = constructorDTO.construir(tablero, victoria, derrota, puntaje);

        if (victoria) {
            mostrarVictoria();
        } else if (derrota) {
            mostrarDerrota(motivoDerrota);
        }

        return resultado;
    }

    private Memento crearMemento() {
        List<EstadoEntidadMovible> estados = new ArrayList<>();

        for (int fila = 0; fila < tablero.getFilaMax(); fila++) {
            for (int columna = 0; columna < tablero.getColumnaMax(); columna++) {
                EntidadMovible entidad = tablero.getMovible(new Coordenada(fila, columna));
                if (entidad != null) {
                    estados.add(entidad.crearEstado());
                }
            }
        }

        CasillaCandado candado = tablero.getCandado();
        EstadoCandado estadoCandado = (candado != null)
                ? new EstadoCandado(candado, candado.getLlaveInsertada())
                : null;

        return new Memento(estados, estadoCandado);
    }

    public int getNivelActual() {
        return nivel;
    }

    public void comenzarJuego() {
        mostrarInicio();
    }

    public void mostrarInicio() { mainVista.mostrarInicio(); }
    public void mostrarNiveles() { mainVista.mostrarNiveles(); }
    public void mostrarPausa() { mainVista.mostrarPausa(); }

    public void mostrarVictoria() {
        mainVista.mostrarVictoria(puntaje.getPuntaje());
    }

    public void mostrarDerrota(String motivo) {
        mainVista.mostrarDerrota(motivo);
    }
}