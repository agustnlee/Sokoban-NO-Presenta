package controlador;

import vista.MainVista;
import vista.juego.ContenedorMapa;
import vista.juego.PanelTransicionMano;
import vista.overlays.OverlayCuentaAtras;

import java.util.List;

public class ControladorPrincipal {

    private static final int CANTIDAD_NIVELES = 10; // TODO: ajustar cantidad nieles
    private final MainVista mainVista;
    private final ControladorJuego controladorJuego;
    private final ControladorPaginaJuego controladorPaginaJuego;

    public ControladorPrincipal() {
        List<String> screenshots = List.of(
            "/imagenes/niveles/nivel1.png",
            "/imagenes/niveles/nivel2.png",
            "/imagenes/niveles/nivel3.png",
            "/imagenes/niveles/nivel4.png",
            "/imagenes/niveles/nivel5.png",
            "/imagenes/niveles/nivel6.png",
            "/imagenes/niveles/nivel7.png",
            "/imagenes/niveles/nivel8.png",
            "/imagenes/niveles/nivel9.png",
            "/imagenes/niveles/nivel10.png"
        ); // TODO: path SCREENSHOT

        this.mainVista = new MainVista(CANTIDAD_NIVELES, screenshots);
        this.controladorJuego = new ControladorJuego(mainVista);

        ContenedorMapa contenedorMapa = mainVista.getPaginaJuego().getContenedorMapa();
        PanelTransicionMano panelTransicion = mainVista.getPaginaJuego().getPanelTransicion();
        OverlayCuentaAtras overlayCuentaAtras = mainVista.getOverlayCuentaAtras();

        this.controladorPaginaJuego = new ControladorPaginaJuego(
                controladorJuego,
                mainVista.getPaginaJuego(),
                contenedorMapa.getPanelMapa(),
                panelTransicion,
                overlayCuentaAtras
        );

        conectarNavegacion();
    }

    private void conectarNavegacion() {
        mainVista.getPaginaInicio().setOnJugar(() -> {
            controladorPaginaJuego.cargarNivel(1);
            mainVista.mostrarJuego();
        });
        mainVista.getPaginaInicio().setOnNiveles(mainVista::mostrarNiveles);
        mainVista.getPaginaInicio().setOnSalir(() -> System.exit(0));

        mainVista.getPaginaNiveles().setOnVolver(mainVista::mostrarInicio);
        mainVista.getPaginaNiveles().setOnNivelSeleccionado(nivel -> {
            controladorPaginaJuego.cargarNivel(nivel);
            mainVista.mostrarJuego();
        });

        mainVista.getOverlayPausa().setOnReanudar(mainVista::ocultarPausa);
        mainVista.getOverlayPausa().setOnReiniciar(() -> {
            mainVista.ocultarPausa();
            controladorPaginaJuego.reiniciarNivel();
        });
        mainVista.getOverlayPausa().setOnMenu(() -> {
            mainVista.ocultarPausa();
            mainVista.mostrarInicio();
        });

        mainVista.getOverlayVictoria().setOnSiguiente(() -> {
            mainVista.ocultarVictoria();
            controladorPaginaJuego.cargarNivel(controladorJuego.getNivelActual() + 1);
        });
        mainVista.getOverlayVictoria().setOnMenu(() -> {
            mainVista.ocultarVictoria();
            mainVista.mostrarInicio();
        });

        mainVista.getOverlayDerrota().setOnReiniciar(() -> {
            mainVista.ocultarDerrota();
            controladorPaginaJuego.reiniciarNivel();
        });
        mainVista.getOverlayDerrota().setOnMenu(() -> {
            mainVista.ocultarDerrota();
            mainVista.mostrarInicio();
        });
    }
}