package controlador;

import modelo.dto.DTOResultado;
import modelo.entidades.Direccion;
import vista.juego.PanelHUD;
import vista.juego.PanelMapa;
import vista.juego.PanelTransicionMano;
import vista.overlays.OverlayCuentaAtras;
import vista.paginas.PaginaJuego;

import java.util.function.Supplier;

public class ControladorPaginaJuego {

    private final ControladorJuego controladorJuego;
    private final PanelMapa panelMapa;
    private final PanelHUD panelHUD;
    private final PanelTransicionMano panelTransicion;
    private final OverlayCuentaAtras overlayCuentaAtras;

    public ControladorPaginaJuego(ControladorJuego controladorJuego,
                                   PaginaJuego paginaJuego,
                                   PanelMapa panelMapa,
                                   PanelTransicionMano panelTransicion,
                                   OverlayCuentaAtras overlayCuentaAtras) {
        this.controladorJuego = controladorJuego;
        this.panelMapa = panelMapa;
        this.panelHUD = paginaJuego.getPanelHUD();
        this.panelTransicion = panelTransicion;
        this.overlayCuentaAtras = overlayCuentaAtras;

        conectarInput();
    }

    private void conectarInput() {
        panelHUD.setOnArriba(() -> solicitarMovimiento(Direccion.ARRIBA));
        panelHUD.setOnAbajo(() -> solicitarMovimiento(Direccion.ABAJO));
        panelHUD.setOnIzquierda(() -> solicitarMovimiento(Direccion.IZQUIERDA));
        panelHUD.setOnDerecha(() -> solicitarMovimiento(Direccion.DERECHA));

        panelHUD.setOnUndo(this::solicitarUndo);
        panelHUD.setOnPausa(controladorJuego::mostrarPausa);
    }

    public void cargarNivel(int nivel) {
        DTOResultado resultado = controladorJuego.cargarNivel(nivel);
        panelHUD.setNivel(nivel);
        panelHUD.resetearContadores();
        actualizarVista(resultado);

        panelHUD.setInputHabilitado(false);
        overlayCuentaAtras.iniciar(() -> {
            panelHUD.setInputHabilitado(true);
            panelHUD.getPanelDirecciones().solicitarFoco();
        });
    }

    public void reiniciarNivel() {
        iniciarTransicion(controladorJuego::reiniciarJuego);
    }

    private void solicitarMovimiento(Direccion dir) {
        if (panelTransicion.estaActivo()) return;
        iniciarTransicion(() -> controladorJuego.mover(dir));
    }

    private void solicitarUndo() {
        if (panelTransicion.estaActivo()) return;
        iniciarTransicion(controladorJuego::undo);
    }

    private void iniciarTransicion(Supplier<DTOResultado> accion) {
        panelHUD.setInputHabilitado(false);

        panelTransicion.iniciar(
            () -> actualizarVista(accion.get()),
            () -> panelHUD.setInputHabilitado(true)
        );
    }

    private void actualizarVista(DTOResultado resultado) {
        panelMapa.aplicarEstado(resultado);
        panelHUD.actualizar(resultado);
    }
}