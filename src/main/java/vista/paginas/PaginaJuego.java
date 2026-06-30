package vista.paginas;

import vista.juego.ContenedorMapa;
import vista.juego.PanelDirecciones;
import vista.juego.PanelHUD;
import vista.juego.PanelTransicionMano;
import vista.utils.BotonImagen;
import vista.utils.ImagenFondo;
import modelo.entidades.DimensionTablero;

import javax.swing.JPanel;
import java.awt.Dimension;

public class PaginaJuego extends JPanel {

    private static final int ANCHO = 1920;
    private static final int ALTO  = 1080;

    private static final int MAPA_X = 380, MAPA_Y = 70, MAPA_ANCHO = 1050, MAPA_ALTO = 950;
    private static final int HUD_X = 1620, HUD_Y = 400, HUD_ANCHO = 260, HUD_ALTO = 420;
    private static final int TELEFONO_X = 5, TELEFONO_Y = 20;
    private static final int DIRECCIONES_X = 30, DIRECCIONES_Y = 700;

    private final ContenedorMapa contenedorMapa;
    private final PanelTransicionMano panelTransicion;
    private final PanelHUD panelHUD;
    private final PanelDirecciones panelDirecciones;
    private final BotonImagen btnPausa;
    private final BotonImagen btnUndo;

    public PaginaJuego() {
        setLayout(null);
        setPreferredSize(new Dimension(ANCHO, ALTO));
        setSize(ANCHO, ALTO);
        setOpaque(false);

        ImagenFondo fondo = new ImagenFondo("/imagenes/fondos/juego.png");
        fondo.setBounds(0, 0, ANCHO, ALTO);

        contenedorMapa = new ContenedorMapa(DimensionTablero.ESTANDAR);
        contenedorMapa.setBounds(MAPA_X, MAPA_Y, MAPA_ANCHO, MAPA_ALTO);
        add(contenedorMapa);

        panelTransicion = new PanelTransicionMano();
        panelTransicion.setBounds(MAPA_X, MAPA_Y, MAPA_ANCHO, MAPA_ALTO);
        panelTransicion.setVisible(false);
        add(panelTransicion);

        panelHUD = new PanelHUD(HUD_ANCHO, HUD_ALTO);
        panelHUD.setBounds(HUD_X, HUD_Y, HUD_ANCHO, HUD_ALTO);
        add(panelHUD);

        panelDirecciones = new PanelDirecciones();
        panelDirecciones.setBounds(DIRECCIONES_X, DIRECCIONES_Y, 260, 260);
        add(panelDirecciones);

        btnPausa = new BotonImagen("/imagenes/hud/pausa.png", "/imagenes/hud/pausa_hover.png");
        btnPausa.setBounds(TELEFONO_X + 10, TELEFONO_Y, 100, 100);
        add(btnPausa);

        btnUndo = new BotonImagen("/imagenes/hud/undo.png", "/imagenes/hud/undo_hover.png");
        btnUndo.setBounds(TELEFONO_X + 120, TELEFONO_Y, 100, 100);
        add(btnUndo);

        add(fondo); 

    }

    public ContenedorMapa getContenedorMapa() { return contenedorMapa; }
    public PanelTransicionMano getPanelTransicion() { return panelTransicion; }
    public PanelHUD getPanelHUD() { return panelHUD; }
    public PanelDirecciones getPanelDirecciones() { return panelDirecciones; }
    public BotonImagen getBtnPausa() { return btnPausa; }
    public BotonImagen getBtnUndo() { return btnUndo; }

    public void setInputHabilitado(boolean habilitado) {
        panelDirecciones.setEnabled(habilitado);
        btnUndo.setEnabled(habilitado);
    }
}