package vista.paginas;

import vista.juego.ContenedorMapa;
import vista.juego.PanelHUD;
import vista.juego.PanelTransicionMano;
import vista.utils.ImagenFondo;
import modelo.entidades.DimensionTablero;

import javax.swing.JPanel;
import java.awt.Dimension;

public class PaginaJuego extends JPanel {

    private static final int ANCHO = 1920;
    private static final int ALTO  = 1080;

    private static final int MAPA_X = 160;
    private static final int MAPA_ANCHO = 1000;
    private static final int MAPA_ALTO  = 700;
    private static final int MAPA_Y = (ALTO - MAPA_ALTO) / 2;

    private static final int HUD_X     = MAPA_X + MAPA_ANCHO + 40;
    private static final int HUD_Y     = 0;
    private static final int HUD_ANCHO = ANCHO - HUD_X - 20;
    private static final int HUD_ALTO  = ALTO;

    private final ContenedorMapa contenedorMapa;
    private final PanelTransicionMano panelTransicion;
    private final PanelHUD panelHUD;

    public PaginaJuego() {
        setLayout(null);
        setPreferredSize(new Dimension(ANCHO, ALTO));
        setSize(ANCHO, ALTO);
        setOpaque(false);

        ImagenFondo fondo = new ImagenFondo("/imagenes/fondos/juego.png");
        fondo.setBounds(0, 0, ANCHO, ALTO);



        panelTransicion = new PanelTransicionMano();
        panelTransicion.setBounds(MAPA_X, MAPA_Y, MAPA_ANCHO, MAPA_ALTO);
        panelTransicion.setVisible(false);
        add(panelTransicion);

        panelHUD = new PanelHUD(HUD_ANCHO, HUD_ALTO);
        panelHUD.setBounds(HUD_X, HUD_Y, HUD_ANCHO, HUD_ALTO);
        add(panelHUD);

        contenedorMapa = new ContenedorMapa(DimensionTablero.ESTANDAR);
        contenedorMapa.setBounds(MAPA_X, MAPA_Y, MAPA_ANCHO, MAPA_ALTO);
        add(contenedorMapa);

        add(fondo);
    }

    public ContenedorMapa getContenedorMapa() { return contenedorMapa; }
    public PanelTransicionMano getPanelTransicion() { return panelTransicion; }
    public PanelHUD getPanelHUD() { return panelHUD; }
}