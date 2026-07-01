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
    private final PaginaJuego paginaJuego;
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
        this.paginaJuego = paginaJuego;
        this.panelMapa = panelMapa;
        this.panelHUD = paginaJuego.getPanelHUD();
        this.panelTransicion = panelTransicion;
        this.overlayCuentaAtras = overlayCuentaAtras;

        conectarInput();
    }

    private void conectarInput() {
        paginaJuego.getPanelDirecciones().setOnArriba(() -> solicitarMovimiento(Direccion.ARRIBA));
        paginaJuego.getPanelDirecciones().setOnAbajo(() -> solicitarMovimiento(Direccion.ABAJO));
        paginaJuego.getPanelDirecciones().setOnIzquierda(() -> solicitarMovimiento(Direccion.IZQUIERDA));
        paginaJuego.getPanelDirecciones().setOnDerecha(() -> solicitarMovimiento(Direccion.DERECHA));

        paginaJuego.getBtnUndo().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent e) { solicitarUndo(); }
        });
        paginaJuego.getBtnPausa().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent e) { controladorJuego.mostrarPausa(); }
        });
    }

    public void cargarNivel(int nivel) {
        DTOResultado resultado = controladorJuego.cargarNivel(nivel);
        panelHUD.setNivel(nivel);
        panelHUD.resetearContadores();
        actualizarVista(resultado);

        paginaJuego.setInputHabilitado(false);
        overlayCuentaAtras.iniciar(() -> {
            paginaJuego.setInputHabilitado(true);
            paginaJuego.getPanelDirecciones().solicitarFoco();
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
        paginaJuego.setInputHabilitado(false);

        panelTransicion.iniciar(
            () -> actualizarVista(accion.get()),
            () -> paginaJuego.setInputHabilitado(true)
        );
    }

    private void actualizarVista(DTOResultado resultado) {
        panelMapa.aplicarEstado(resultado);
        panelHUD.actualizar(resultado);
    }
}